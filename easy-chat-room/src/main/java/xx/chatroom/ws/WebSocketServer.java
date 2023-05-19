package xx.chatroom.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import xx.chatroom.entity.MsgEntity;
import xx.chatroom.util.SpringUtils;
import xx.chatroom.ws.util.DecoderUtil;
import xx.chatroom.ws.util.EncoderUtil;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint(value = "/chat/{roomId}/{userId}", encoders = {EncoderUtil.class}, decoders = {DecoderUtil.class})
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
    private String userId = "";

    /**
     * 房间号
     */
    private String roomId = "";

    /**
     * RedisTemplate
     */
    private RedisTemplate redisTemplate = (RedisTemplate) SpringUtils.getBean("redisTemplate");


    private static String ROOM_KEY = "chat-room:";

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     * @param userId
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId, @PathParam("roomId") String roomId) {
        this.session = session;
        this.roomId = roomId;
        this.userId = userId;
        webSocketMap.put(userId, this);
        addOnlineCount();
        joinToRoom(userId, roomId);
        logger.info("房间号：[{}] ,用户 [{}] 已连接,当前在线人数为:[{}]", roomId, userId, getOnlineCount());
    }

    /**
     * 接收客户端发送的消息
     *
     * @param msgEntity
     */
    @OnMessage
    public void onMessage(MsgEntity msgEntity) {
        logger.info("接收到客户端发送的消息：【{}】", msgEntity.toString());
        if ("text".equals(msgEntity.getSysMsgType())) {
            msgEntity.setSender(userId);
            msgEntity.setRoomId(roomId);
            // 推送消息给房间所有人
            oneToRoom(roomId, msgEntity);
        }
    }

    /**
     * 加入房间 、 创建房间
     *
     * @param userId
     * @param roomId
     */
    public void joinToRoom(String userId, String roomId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("roomId", roomId);
        redisTemplate.opsForHash().put(ROOM_KEY + roomId, userId, jsonObject);
        oneToRoom(roomId, new MsgEntity("sys", userId + " join then room"));
    }

    /**
     * 推送消息给房间所有人
     *
     * @param roomId
     */
    public void oneToRoom(String roomId, MsgEntity data) {
        Map<String, Object> res = getAllUserFromRoom(roomId);
        res.forEach((key, value) -> {
            if (!userId.equals(key)) {
                oneToOne(key, data);
            }
        });
    }

    /**
     * 根据房间号获取人员信息
     *
     * @param roomId
     */
    public Map<String, Object> getAllUserFromRoom(String roomId) {
        Map<String, Object> map = redisTemplate.opsForHash().entries(ROOM_KEY + roomId);
        return map;
    }


    /**
     * 服务器主动推送
     */
    public static void oneToOne(String toUser, MsgEntity message) {
        WebSocketServer webSocketServer = webSocketMap.get(toUser);
        Session session = webSocketServer.session;
        if (session != null && session.isOpen()) {
            try {
                // 为了避免并发情况下造成异常
                synchronized (session) {
                    session.getBasicRemote().sendObject(message);
                }
            } catch (IOException e) {
                logger.error("websocket 消息发送异常");
            } catch (EncodeException e) {
                e.printStackTrace();
                logger.error("消息编码异常");
            }
        } else {
            logger.error("当前用户[{}]可能不在线，无法推送数据", toUser);
        }
    }

    /**
     * 用户断开连接 离开房间
     *
     * @param roomId
     * @param userId
     */
    public void leaveRoom(String roomId, String userId) {
        redisTemplate.opsForHash().delete(ROOM_KEY + roomId, userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("roomId", roomId);
        oneToRoom(roomId, new MsgEntity("sys", userId + " leave then room"));
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
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
            leaveRoom(roomId, userId);
        }
        log.info("房间号：" + roomId + ",用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
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
