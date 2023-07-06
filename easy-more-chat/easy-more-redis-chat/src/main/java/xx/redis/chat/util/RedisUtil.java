package xx.redis.chat.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;




@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 发布消息
     *
     * @param key
     */
    public void publish(String key, String value) {
        stringRedisTemplate.convertAndSend(key, value);
    }
}
