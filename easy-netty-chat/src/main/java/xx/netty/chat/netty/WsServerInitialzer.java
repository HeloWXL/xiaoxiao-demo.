package xx.netty.chat.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

/**
 * 自定义初始化处理器  Socket 初始化器，每一个Channel进来都会调用这里的 InitChannel 方法
 */
@Component
public class WsServerInitialzer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 通过SocketChannel 去获取对应的管道pipeline
        ChannelPipeline pipeline = ch.pipeline();
        // 通过管道，添加handler,HttpServerCodec 是netty自己提供的助手类
        // 当请求到达服务端的时候，我们需要解码，响应到客户端时需要做编码
        //websocket基于http协议，所以需要http编解码器
        pipeline.addLast(new HttpServerCodec());
        //添加对于读写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对HttpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        // 几乎在Netty编程中，都会用到这个handle
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        // ================= 上述是用于支持http协议的 ==============
        //自定义handler
        pipeline.addLast(new ChatHandler());
        //websocket 服务器处理的协议，用于给指定的客户端进行连接访问的路由地址
        //比如处理一些握手动作(ping,pong)
        pipeline.addLast(new WebSocketServerProtocolHandler("/chat","WebSocket",true, 65536 * 10));

    }
}
