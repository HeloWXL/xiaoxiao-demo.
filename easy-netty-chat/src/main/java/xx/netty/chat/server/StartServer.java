package xx.netty.chat.server;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StartServer {

    @PostConstruct
   public void startNetty(){
       NettyHttpServer nettyHttpServer = new NettyHttpServer(1000);
        try {
            nettyHttpServer.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
