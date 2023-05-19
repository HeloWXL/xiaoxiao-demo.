package xx.netty.chat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class WebSocketServer {
    @Resource
    private WsServerInitialzer wsServerInitialzer;

    @Getter
    private ServerBootstrap serverBootstrap;

    /**
     * netty服务监听端口
     */
    @Value("${netty.port:8088}")
    private int port;
    /**
     * 主线程组数量
     */
    @Value("${netty.bossThread:1}")
    private int bossThread;


    /**
     * 启动netty服务器
     */
    public void start() {
        try {
            this.init();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Netty started on port: {} (TCP) with boss thread {}", this.port, this.bossThread);
    }

    /**
     * 初始化netty配置
     */
    public  void init() throws InterruptedException {
        // 创建两个线程组，bossGroup为接收请求的线程组，一般1-2个就行 主线程 用于接收客户端的连接请求，不做任何处理
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(this.bossThread);
        // 实际工作的线程组  从线程组 主线程组把任务丢给线程组，让线程组去处理
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            this.serverBootstrap = new ServerBootstrap();
            //  绑定线程池 两个线程组加入进来
            this.serverBootstrap.group(bossGroup, workerGroup)
                    // 配置为nio类型
                    .channel(NioServerSocketChannel.class)
                    // 加入自己的初始化器
                    .childHandler(this.wsServerInitialzer);
            // 服务器异步创建绑定
            ChannelFuture cf = this.serverBootstrap.bind(port).sync();
            System.out.println(WebSocketServer.class + "已启动，正在监听： " + cf.channel().localAddress());
            // 关闭服务器通道
            cf.channel().closeFuture().sync();
        } finally {
            // 释放线程池资源
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }
    }
}
