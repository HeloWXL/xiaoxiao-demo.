package xx.chat.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint("/chat/{userName}")
@Component
public class WebSocketServer {

    /**
     * 日志服务
     */
    private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
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
     * 接收userId
     */
    private String userName = "";

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     * @param userName
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName) {
        this.session = session;
        this.userName = userName;
        webSocketMap.put(userName, this);
        addOnlineCount();
        logger.info("用户 【{}】已连接,当前在线人数为:【{}】", userName, getOnlineCount());
    }

    /**
     * 接收客户端发送的消息
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        logger.info("接收到客户端发送的消息：【{}】", message);
        JSONObject jsonObject = JSON.parseObject(message);
        String receiver = jsonObject.getString("receiver");
        if (StringUtils.isEmpty(receiver)) {
            logger.info("接收人为空，无法推送消息");
        } else {
            jsonObject.put("sender", userName);
            oneToOne(receiver, jsonObject.toJSONString());
        }
    }

    /**
     * 服务器主动推送
     */
    public static void oneToOne(String toUser, String message) {
        WebSocketServer webSocketServer = webSocketMap.get(toUser);
        if(webSocketServer == null) {
            logger.error("当前节点找不到此用户哦：[{}]", toUser);
            return;
        }
        Session session = webSocketServer.session;
        if (session != null && session.isOpen()) {
            try {
                // 为了避免并发情况下造成异常
                synchronized (session) {
                    session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                logger.error("websocket 消息发送异常");
            }
        } else {
            logger.error("当前用户[{}]可能不在线，无法推送数据", toUser);
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userName)) {
            webSocketMap.remove(userName);
            //从set中删除
            subOnlineCount();
        }
    }

    /**
     * 获取在线人数
     *
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }


    /**
     * 在线人数+1
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }


    /**
     * 在线人数-1
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
