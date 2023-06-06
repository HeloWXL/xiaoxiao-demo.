package xx.call.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xx.call.dto.SignalEntity;
import xx.call.websocket.util.DecoderUtil;
import xx.call.websocket.util.EncoderUtil;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint(value = "/sip/{userId}", encoders = {EncoderUtil.class}, decoders = {DecoderUtil.class})
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
     * 接收userId
     */
    private String userId = "";



    /**
     * @Description: 连接建立成功调用的方法，成功建立之后，将用户的userName 存储到redis
     * @params: [session, userId]
     * @return: void
     * @Author: wangxianlin
     * @Date: 2020/5/9 9:13 PM
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        webSocketMap.put(userId, this);
        addOnlineCount();
        log.info("用户加入:{},当前在线人数为:{}", userId, getOnlineCount());
    }


    /**
     * @Description: 收到客户端消息后调用的方法, 调用API接口 发送消息到
     * @params: [message, session]
     * @return: void
     * @Author: wangxianlin
     * @Date: 2020/5/9 9:13 PM
     */
    @OnMessage
    public void onMessage(String message, @PathParam("userId") String userId) {
        log.info("用户消息:" + userId + ",报文:" + message);
        if (!("").equals(message)) {
            JSONObject jsonObject = JSON.parseObject(message);
            String type = jsonObject.getString("type");
            // offer
            if (Objects.equals(type, "offer")) {
                String targetUid = jsonObject.getString("targetUid");
                oneToOne(targetUid, new SignalEntity("offer", "rtc offer", 200, jsonObject));
            }
            // 远程呼叫
            else if (Objects.equals(type, "call")) {
                String targetUid = jsonObject.getString("targetUid");
                oneToOne(targetUid, new SignalEntity("call", "远程呼叫", 200, jsonObject));
            }
            // 对方已接听
            else if (Objects.equals(type, "accept")) {
                String targetUid = jsonObject.getString("targetUid");
                oneToOne(targetUid, new SignalEntity("accept", "对方已接听", 200, jsonObject));
            }
            // 对方拒绝通话
            else if (Objects.equals(type, "reject")) {
                String targetUid = jsonObject.getString("targetUid");
                oneToOne(targetUid, new SignalEntity("reject", "对方拒绝通话", 200, jsonObject));
            }
            // candidate
            else if (Objects.equals(type, "candidate")) {
                String targetUid = jsonObject.getString("targetUid");
                oneToOne(targetUid, new SignalEntity("candidate", "ice candidate", 200, jsonObject));
            }
            // answer
            else if (Objects.equals(type, "answer")) {
                String targetUid = jsonObject.getString("targetUid");
                oneToOne(targetUid, new SignalEntity("answer", "rtc answer", 200, jsonObject));
            }
        }
    }

    /**
     * 推送消息给个人
     *
     * @param to
     * @param data
     */
    public void oneToOne(String to, SignalEntity data) {
        Session session = webSocketMap.containsKey(to) ? webSocketMap.get(to).session : null;
        if (session != null && session.isOpen()) {
            try {
                // 为了避免并发情况下造成异常
                synchronized (session) {
                    session.getBasicRemote().sendObject(data);
                }
            } catch (IOException e) {
                log.error("消息发送IO异常：[{}]", e.toString());
            } catch (EncodeException e) {
                log.error("消息发送Encode异常：[{}]", e.toString());
            }
        } else {
            log.warn("用户：[{}]-->不在线", to);
        }
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
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
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
