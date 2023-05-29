package xx.netty.chat.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义handler类:Socket拦截器，用于处理客户端的行为
 * TextWebSocketFrame 在netty中，是用于websocket 专门处理文本的对象，frame 是消息的载体
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //用于记录和管理所有客户端的channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();


    /**
     * 读取到客户端发来的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            // 获取连接时候的url地址  /chat?userId=halo
            String uri = request.uri();
            Map<String, Object> paramMap = getUrlParams(uri);
            String name = (String) paramMap.get("userId");
            channelMap.put(name, ctx.channel());
            //如果url包含参数，需要处理
            if (uri.contains("?")) {
                String newUri = uri.substring(0, uri.indexOf("?"));
                request.setUri(newUri);
            }
        } else if (msg instanceof TextWebSocketFrame) {
            //正常的TEXT消息类型
            TextWebSocketFrame frame = (TextWebSocketFrame) msg;
            //客户端传递过来的消息
            String content = frame.text();
            log.info("接收到了客户端的消息是:[{}]" , content);
            // 将字符串转成json
            JSONObject jsonObject = JSON.parseObject(content);
            // 获取接收人
            String receiver = jsonObject.getString("receiver");
//            sendMsg(receiver, content);
            sendAllMsg( content);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {

    }

    /**
     * 推送消息给个人
     */
    public void sendMsg(String toUser, String content) {
        Channel channel = channelMap.get(toUser);
        if (channel == null) {
            log.info("用户[{}] 不在线哦", toUser);
        } else {
            channel.writeAndFlush(content);
        }
    }

    /**
     * 群发消息
     */
    public void sendAllMsg(String content) {
        clients.writeAndFlush(new TextWebSocketFrame("[服务器接收到了客户端的消息:]" + LocalDateTime.now() + ",消息为:" + content));
    }

    /**
     * 通过URL获取参数
     *
     * @param url
     * @return
     */
    private static Map getUrlParams(String url) {
        Map<String, String> map = new HashMap<>();
        url = url.replace("?", ";");
        if (!url.contains(";")) {
            return map;
        }
        if (url.split(";").length > 0) {
            String[] arr = url.split(";")[1].split("&");
            for (String s : arr) {
                String key = s.split("=")[0];
                String value = s.split("=")[1];
                map.put(key, value);
            }
            return map;

        } else {
            return map;
        }
    }


    /**
     * 客户端创建的时候触发，当客户端连接上服务端之后，就可以获取该channel，然后放到channelGroup中进行统一管理
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("新的客户端链接：" + ctx.channel().id().asShortText());
        clients.add(ctx.channel());
    }

    /**
     * 客户端销毁的时候触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 下面这一步是多余的,当handlerRemoved 被触发时候，channelGroup会自动移除对应的channel
        // clients.remove(ctx.channel());
        System.out.println("客户端断开，当前被移除的channel的短ID是：" + ctx.channel().id().asShortText());
    }

    /**
     * 发生异常时触发
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
        clients.remove(ctx.channel());
    }
}
