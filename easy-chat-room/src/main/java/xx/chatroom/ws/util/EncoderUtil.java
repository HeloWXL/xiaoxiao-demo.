package xx.chatroom.ws.util;

import com.alibaba.fastjson.JSONObject;
import xx.chatroom.entity.MsgEntity;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @Author: wu.shaoya
 * @Description: 编码器
 * @Date: 10:14 2019/10/31
 */
public class EncoderUtil implements Encoder.Text<MsgEntity> {

    @Override
    public String encode(MsgEntity message) throws EncodeException {
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

