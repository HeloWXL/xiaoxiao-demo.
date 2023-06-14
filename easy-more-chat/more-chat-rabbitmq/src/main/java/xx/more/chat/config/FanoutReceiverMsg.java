package xx.more.chat.config;


import com.chat.constant.ConstantUtils;
import com.chat.websocket.WebSocketServer;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RabbitListener(queues = ConstantUtils.FANOUT_QUEUE_MSG)
public class FanoutReceiverMsg {
    @RabbitHandler
    public void process(Map msg) throws IOException {
        if (msg !=null){
            WebSocketServer.sendMessage((String)msg.get("receiver"),msg.toString());
        }
    }
}
