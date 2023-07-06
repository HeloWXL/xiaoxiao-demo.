package xx.rocketmq.chat.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import xx.redis.chat.websocket.RoomSocketServer;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static xx.redis.chat.constant.ConstantUtils.ROOM_KEY;

@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 发布消息
     *
     * @param key
     */
    public void publish(String key, String value) {
        stringRedisTemplate.convertAndSend(key, value);
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

    public List<String> getRoomUserList(String roomId) {
        Map<String, Object> map = redisTemplate.opsForHash().entries(ROOM_KEY + roomId);
        List<String> userList = new ArrayList();
        map.forEach((key, value) -> {
            userList.add(key);
        });
        return userList;
    }

    /**
     * 用户加入房间
     *
     * @param userId
     * @param roomId
     */
    public void joinRoom(String userId, String roomId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("roomId", roomId);
        redisTemplate.opsForHash().put(ROOM_KEY + roomId, userId, jsonObject);
        String msg = "用户" + userId + ",已加入" + roomId + "房间";
        log.info(msg);
        JSONObject msgObject = new JSONObject();
        msgObject.put("type","join");
        msgObject.put("data",msg);
        List<String> userList = getRoomUserList(roomId);
        for (String u : userList
        ) {
            try {
                RoomSocketServer.sendMessageToOne(u, msgObject.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 用户离开房间
     *
     * @param roomId
     * @param userId
     */
    public void leaveRoom(String roomId, String userId) {
        redisTemplate.opsForHash().delete(ROOM_KEY + roomId, userId);
        String msg = "用户" + userId + ",已离开" + roomId + "房间";
        log.info(msg);
        List<String> userList = getRoomUserList(roomId);
        JSONObject msgObject = new JSONObject();
        msgObject.put("type","leave");
        msgObject.put("data",msg);
        for (String u : userList
        ) {
            try {
                RoomSocketServer.sendMessageToOne(u, msgObject.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
