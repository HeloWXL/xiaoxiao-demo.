package xx.call.websocket.util;


import com.alibaba.fastjson.JSON;
import xx.call.dto.SignalEntity;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @Author: wu.shaoya
 * @Description: 解码器
 * @Date: 10:14 2019/10/31
 */
public class DecoderUtil implements Decoder.Text<SignalEntity> {

    @Override
    public SignalEntity decode(String jsonMessage) throws DecodeException {
        return JSON.parseObject(jsonMessage, SignalEntity.class);

    }

    @Override
    public boolean willDecode(String jsonMessage) {
       /* try {
            // Check if incoming message is valid JSON
           // JSON.createReader(new StringReader(jsonMessage)).readObject();
            //检查是否是合法的json字符串
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonMessage);
            return true;
        } catch (Exception e) {
            return false;
        }*/
       return true;
    }

    @Override
    public void init(EndpointConfig ec) {
        //System.out.println("MessageDecoder -init method called");
    }

    @Override
    public void destroy() {
        //System.out.println("MessageDecoder - destroy method called");
    }

}

