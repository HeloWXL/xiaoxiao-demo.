package xx.redis.chat.config;

import  xx.redis.chat.entity.ChatMsg;
import  xx.redis.chat.util.JacksonUtil;
import  xx.redis.chat.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Description 消息监听器
 * @Author wxl
 * @Date 2020/3/29 15:07
 */
@Slf4j
@Component
public class ChatMessageListener implements MessageListener {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisSerializer<String> valueSerializer = stringRedisTemplate.getStringSerializer();
        String value = valueSerializer.deserialize(message.getBody());
        log.info("监听消息--- {}", value);
        ChatMsg dto = null;
        if (StringUtils.isNotBlank(value)) {
            try {
                dto = JacksonUtil.json2pojo(value, ChatMsg.class);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("消息格式转换异常：{}", e.toString());
            }
            WebSocketServer.oneToOne(dto.getReceiver(),dto);
        }
    }

}
