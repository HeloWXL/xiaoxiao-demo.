package xx.log.monitor.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Slf4j
@Component
@ServerEndpoint("/logUtil")

public class LogWebSocket {

    private static Session session;

    @OnOpen
    public void onOpen(Session session) {
        LogWebSocket.session = session;
    }

    @OnClose
    public void onClose() {

    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            sendMessage(message + "：成功收到");
        } catch (Exception e) {
            log.error("发送异常");
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {

    }

    public static void sendMessage(String message) throws Exception {
        if (session != null) {
            if (LogWebSocket.session.isOpen()) {
                LogWebSocket.session.getBasicRemote().sendText(message);
            }
        }
    }
}

