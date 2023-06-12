package xx.call.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xx.call.dto.SignalEntity;
import xx.call.util.SpringUtils;
import xx.call.websocket.util.DecoderUtil;
import xx.call.websocket.util.EncoderUtil;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint(value = "/signal/{roomId}/{userId}/{pub}", encoders = {EncoderUtil.class}, decoders = {DecoderUtil.class})
public class WebSocketServer {
    /**
     * RedisTemplate
     */
    private RedisTemplate redisTemplate = (RedisTemplate) SpringUtils.getBean("redisTemplate");

    private static String ROOM_KEY = "meeting-room::";
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
     * 1 表示 主播 或者 会议主持人
     */
    private Integer pub = 0;

    /**
     * @Description: 连接建立成功调用的方法，成功建立之后，将用户的userName 存储到redis
     * @params: [session, userId]
     * @return: void
     * @Author: wangxianlin
     * @Date: 2020/5/9 9:13 PM
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId,
                       @PathParam("roomId") String roomId,
                       @PathParam("pub") Integer pub) {
        this.session = session;
        this.userId = userId;
        this.roomId = roomId;
        this.pub = pub;
        webSocketMap.put(userId, this);
        addOnlineCount();
        addUserToRoom(userId, roomId, pub);
        log.info("房间号：{} 用户加入:{},身份：{},当前在线人数为:{}", roomId, userId, pub, getOnlineCount());
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
            leaveRoom(roomId, userId);
        }
        log.info("房间号：" + roomId + ",用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
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
        if (!("").equals(message)) {
            JSONObject jsonObject = JSON.parseObject(message);
            String type = jsonObject.getString("type");
            if (Objects.equals(type, "offer")) {
                String targetUid = jsonObject.getString("targetUid");
                oneToOne(targetUid, new SignalEntity("offer", "rtc offer", 200, jsonObject));
            } else if (Objects.equals(type, "call")) {
                String targetUid = jsonObject.getString("targetUid");
                oneToOne(targetUid, new SignalEntity("call", "远程呼叫", 200, jsonObject));
            } else if (Objects.equals(type, "roomUserList")) {
                // 房间号
                String roomId = jsonObject.getString("roomId");
                oneToOne(userId, new SignalEntity("roomUserList", "房间人数", 200, getRoomUserList(roomId)));
            } else if (Objects.equals(type, "candidate")) {
                String targetUid = jsonObject.getString("targetUid");
                oneToOne(targetUid, new SignalEntity("candidate", "ice candidate", 200, jsonObject));
            } else if (Objects.equals(type, "answer")) {
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
     * 推送消息给房间所有人
     *
     * @param roomId
     */
    public void oneToRoom(String roomId, SignalEntity data) {
        Map<String, Object> res = getAllUserFromRoom(roomId);
        res.forEach((key, value) -> {
            oneToOne(key, data);
        });
    }

    /**
     * @param roomId
     * @return
     */
    public List<Object> getRoomUserList(String roomId) {
        List<Object> list = new ArrayList<>();
        Map<String, Object> res = getAllUserFromRoom(roomId);
        res.forEach((key, value) -> {
            list.add(value);
        });
        return list;
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
        jsonObject.put("nickName", userId);
        jsonObject.put("pub", pub);
        oneToRoom(roomId, new SignalEntity("leave", userId + " leave then room", 200, jsonObject));
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

    public void addUserToRoom(String userId, String roomId, Integer pub) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("roomId", roomId);
        jsonObject.put("nickName", userId);
        jsonObject.put("pub", pub);
        redisTemplate.opsForHash().put(ROOM_KEY + roomId, userId, jsonObject);
        oneToRoom(roomId, new SignalEntity("join", userId + " join then room", 200, jsonObject));
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
