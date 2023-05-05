package xx.chat.ws;

import org.springframework.stereotype.Component;


import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint("/chat/{userName}")
@Component
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
    }

    /**
     * @Description: 收到客户端消息后调用的方法, 调用API接口 发送消息到
     * @params: [message, session]
     * @return: void
     * @Author: wangxianlin
     * @Date: 2020/5/9 9:13 PM
     */
    @OnMessage
    public void onMessage(String message) {

    }

    /**
     * 实现服务器主动推送
     */
    public static void sendMessage(String toUser, String message) throws IOException {

        Session session = null;
        WebSocketServer webSocketServer = webSocketMap.get(toUser);
        session = webSocketServer.session;
        if (session != null && session.isOpen()) {
            try {
                // 为了避免并发情况下造成异常
                synchronized (session) {
                    session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {

            }
        } else {

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
