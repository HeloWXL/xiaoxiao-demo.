package xx.netty.chat.run;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import xx.netty.chat.netty.WebSocketServer;

import javax.annotation.Resource;

/**
 * 监听Spring容器启动完成，完成后启动Netty服务器
 *
 * @author Gjing
 **/
@Component
public class NettyStartListener implements ApplicationRunner {

    @Resource
    WebSocketServer webSocketServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        webSocketServer.start();
    }
}
