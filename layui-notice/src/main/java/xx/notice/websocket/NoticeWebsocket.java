//package xx.notice.websocket;
//
//import com.alibaba.fastjson.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import usi.chat.biz.notice.dto.NoticeDto;
//import usi.chat.websocket.util.DecoderUtil;
//import usi.chat.websocket.util.EncoderUtil;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author - Jianghj
// * @since - 2020-07-09 17:47
// * 通知相关的webSocket，用于主动向客户端推送通知公告
// */
//@Component
//@ServerEndpoint(value="/noticeServer/{userId}",encoders = {EncoderUtil.class}, decoders = {DecoderUtil.class})
//public class NoticeWebsocket {
//    /** 日志 */
//    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeWebsocket.class);
//    /** 当前webSocket类型 */
//    private static final String TYPE = "Notice";
//    /** 连接webSocket客户端数量 */
//    private static int onlineCount = 0;
//    /** 所有连接的websocket客户端 */
//    private static Map<String, NoticeWebsocket> clients = new ConcurrentHashMap<String, NoticeWebsocket>();
//
//    /** webSocket 客户端连接会话 */
//    private Session session;
//    /** 客户端用户id */
//    private String userId;
//
//    /**
//     * webSocket 客户端连接时
//     * - 每次连接，多会创建一个 ChatNoticeWebsocket 实例
//     *
//     * @param userId
//     * @param session
//     * @throws IOException
//     */
//    @OnOpen
//    public void onOpen(@PathParam("userId") String userId, Session session) throws IOException {
//        if (StringUtils.isEmpty(userId)) {
//            return;
//        }
//        this.userId = userId;
//        this.session = session;
//        addOnlineCount();
//        clients.put(this.userId, this);
//        LOGGER.info("用户[{}][{}] webSocket 连接成功", this.userId, TYPE);
//    }
//
//    /**
//     * webSocket 客户端断开连接时
//     *
//     * @throws IOException
//     */
//    @OnClose
//    public void onClose() {
//        clients.remove(userId);
//        subOnlineCount();
//        LOGGER.info("用户[{}][{}] webSocket 已断开连接", this.userId, TYPE);
//    }
//
//    /**
//     * webSocket 客户端连接出错时
//     *
//     * @param session
//     * @param error
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        LOGGER.error("websocket 连接发生错误：[{}]", error.getMessage());
//    }
//
//    /**
//     * webSocket 服务端接收到客户端的消息时
//     *
//     * @param message
//     * @throws IOException
//     */
//    @OnMessage
//    public void onMessage(String message) throws IOException {
//        System.out.println(message);
//    }
//
//    /**
//     * 主动向指定员工发送通知
//     *
//     * @param notice
//     * @param userId
//     */
//    public static void sendNotice(NoticeDto notice, String userId) {
//        NoticeWebsocket client = clients.get(userId);
//        if (client != null) {
//            final Session session = client.session;
//            if (session.isOpen()) {
//                String noticeStr = JSONObject.toJSON(notice).toString();
//                session.getAsyncRemote().sendText(noticeStr);
//            }
//        }
//    }
//
//    public static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//
//    public static synchronized void addOnlineCount() {
//        NoticeWebsocket.onlineCount++;
//    }
//
//    public static synchronized void subOnlineCount() {
//        NoticeWebsocket.onlineCount--;
//    }
//
//    public static synchronized Map<String, NoticeWebsocket> getClients() {
//        return clients;
//    }
//
//}
