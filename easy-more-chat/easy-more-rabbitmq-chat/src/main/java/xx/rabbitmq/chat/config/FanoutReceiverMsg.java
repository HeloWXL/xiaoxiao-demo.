package xx.rabbitmq.chat.config;



import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xx.rabbitmq.chat.constant.ConstantUtils;
import xx.rabbitmq.chat.websocket.WebSocketServer;

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
