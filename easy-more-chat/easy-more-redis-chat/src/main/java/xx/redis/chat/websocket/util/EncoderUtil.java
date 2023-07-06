package xx.redis.chat.websocket.util;

import com.alibaba.fastjson.JSONObject;
import xx.redis.chat.entity.ChatMsg;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @Description: 编码器
 * @Date: 10:14 2019/10/31
 */
public class EncoderUtil implements Encoder.Text<ChatMsg> {

    @Override
    public String encode(ChatMsg message) throws EncodeException {
        return JSONObject.toJSONString(message);

    }

    @Override
    public void init(EndpointConfig ec) {
        //System.out.println("MessageEncoder - init method called");
    }



    @Override
    public void destroy() {
        //System.out.println("MessageEncoder - destroy method called");
    }

}

