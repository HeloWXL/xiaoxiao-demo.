package xx.redis.chat.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.thymeleaf.util.StringUtils;
import  xx.redis.chat.constant.ConstantUtils;
import xx.redis.chat.entity.ChatMsg;
import  xx.redis.chat.util.RedisUtil;
import  xx.redis.chat.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xx.redis.chat.websocket.util.DecoderUtil;
import xx.redis.chat.websocket.util.EncoderUtil;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat/{userName}", encoders = {EncoderUtil.class}, decoders = {DecoderUtil.class})
@Component
@Slf4j
public class WebSocketServer {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 不能使用@AutoWire原因：发现注入不了redis，redis注入失败 可能是因为实例化的先后顺序吧，WebSocket先实例化了，  但是@Autowire是会触发getBean操作
     * 因为@ServerEndpoint不支持注入，所以使用SpringUtils获取IOC实例
     */
    private RedisUtil redisUtil = SpringUtils.getBean(RedisUtil.class);

    /**
     * 接收userId
     */
    private String userName = "";

    /**
     * @Description: 连接建立成功调用的方法，成功建立之后，将用户的userName 存储到redis
     * @params: [session, userId]
     * @return: void
     * @Author: wangxianlin
     * @Date: 2020/5/9 9:13 PM
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName) {
        this.session = session;
        this.userName = userName;
        webSocketMap.put(userName, this);
        addOnlineCount();
        log.info("用户连接:" + userName + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * @Description: 连接关闭调用的方法
     * @params: []
     * @return: void
     * @Author: wangxianlin
     * @Date: 2020/5/9 9:13 PM
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userName)) {
            webSocketMap.remove(userName);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:" + userName + ",当前在线人数为:" + getOnlineCount());
    }


    /**
     * @Description: 收到客户端消息后调用的方法,调用API接口 发送消息到
     * @params: [message, session]
     * @return: void
     * @Author: wangxianlin
     * @Date: 2020/5/9 9:13 PM
     */
    @OnMessage
    public void onMessage(ChatMsg chatMsg) {
        log.info("接收到客户端发送的消息：【{}】", chatMsg.toString());
        String receiver = chatMsg.getReceiver();
        if (StringUtils.isEmpty(receiver)) {
            log.info("接收人为空，无法推送消息");
        } else {
            chatMsg.setSender(userName);
            redisUtil.publish(ConstantUtils.TOPIC_MSG,JSON.toJSONString(chatMsg));
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userName + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 服务器主动推送
     */
    public static void oneToOne(String toUser, ChatMsg message) {
        WebSocketServer webSocketServer = webSocketMap.get(toUser);
        if(webSocketServer == null) {
            log.error("当前节点找不到此用户哦：[{}]", toUser);
            return;
        }
        Session session = webSocketServer.session;
        if (session != null && session.isOpen()) {
            try {
                // 为了避免并发情况下造成异常
                synchronized (session) {
                    session.getBasicRemote().sendObject(message);
                }
            } catch (IOException e) {
                log.error("websocket 消息发送异常");
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        } else {
            log.error("当前用户[{}]可能不在线，无法推送数据", toUser);
        }
    }


    /**
     * @Description: 获取在线人数
     * @params: []
     * @return: int
     * @Author: wangxianlin
     * @Date: 2020/5/9 9:09 PM
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * @Description: 在线人数+1
     * @params: []
     * @return: void
     * @Author: wangxianlin
     * @Date: 2020/5/9 9:09 PM
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * @Description: 在线人数-1
     * @params: []
     * @return: void
     * @Author: wangxianlin
     * @Date: 2020/5/9 9:09 PM
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
