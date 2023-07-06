package xx.rocketmq.chat.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import xx.redis.chat.util.RedisUtil;
import xx.redis.chat.util.SpringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ServerEndpoint("/room/{roomId}/{userId}")
@Component
@Slf4j
public class RoomSocketServer {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, RoomSocketServer> webSocketMap = new ConcurrentHashMap<>();
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
    private String userId = "";

    private String roomId = "";

    /**
     * @Description: 连接建立成功调用的方法，成功建立之后，将用户的userName 存储到redis
     * @params: [session, userId]
     * @return: void
     * @Author: wangxianlin
     * @Date: 2020/5/9 9:13 PM
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId, @PathParam("userId") String userId) {
        this.session = session;
        this.roomId = roomId;
        this.userId = userId;
        webSocketMap.put(userId, this);
        redisUtil.joinRoom(userId, roomId);
        addOnlineCount();
        log.info("用户:" + userId + "进入房间" + roomId + ",当前在线人数为:" + getOnlineCount());
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
        log.info("用户消息:" + userId + ",报文:" + message);
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject msgObject = JSON.parseObject(message);
                String type = msgObject.getString("type");
                // 获取房间所有人
                if ("roomList".equals(type)) {
                    // 获取房间的所有人
                    List<Object> list = getRoomUserList(roomId);
                    JSONObject object = new JSONObject();
                    object.put("type", "roomList");
                    object.put("data", list);
                    sendMessageToOne(userId, object.toString());
                }
                // 文本消息
                else if ("text".equals(type)) {
                    JSONObject object = new JSONObject();
                    //追加发送人(防止串改)
                    object.put("type", "text");
                    object.put("sender", this.userId);
                    object.put("msg", msgObject.getString("data"));
                    List<String> userList = redisUtil.getRoomUserList(roomId);
                    List<String> newUserList =  userList.stream().filter(u->!u.equals(userId)).collect(Collectors.toList());
                    newUserList.stream().forEach(nu->{
                        try {
                            sendMessageToOne(nu, object.toString());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 消息发送给个人
     *
     * @param toUser
     * @param message
     * @throws IOException
     */
    public static void sendMessageToOne(String toUser, String message) throws IOException {
        Session session = webSocketMap.containsKey(toUser) ? webSocketMap.get(toUser).session : null;
        if (session != null && session.isOpen()) {
            try {
                // 为了避免并发情况下造成异常
                synchronized (session) {
                    session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                log.error("消息发送IO异常：[{}]", e.toString());
            }
        } else {
            log.warn("用户：[{}]-->不在线", toUser);
        }
    }


    /**
     * 获取房间所有人
     *
     * @param roomId
     * @return
     */
    public List<Object> getRoomUserList(String roomId) {
        List<Object> list = new ArrayList<>();
        Map<String, Object> res = redisUtil.getAllUserFromRoom(roomId);
        res.forEach((key, value) -> {
            list.add(value);
        });
        return list;
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
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
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
            redisUtil.leaveRoom(roomId, userId);
        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
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
        RoomSocketServer.onlineCount++;
    }

    /**
     * @Description: 在线人数-1
     * @params: []
     * @return: void
     * @Author: wangxianlin
     * @Date: 2020/5/9 9:09 PM
     */
    public static synchronized void subOnlineCount() {
        RoomSocketServer.onlineCount--;
    }

}
