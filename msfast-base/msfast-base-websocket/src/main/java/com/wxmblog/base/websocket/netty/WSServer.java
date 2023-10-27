package com.wxmblog.base.websocket.netty;

import com.wxmblog.base.common.constant.ConfigConstants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @ClassName：WSServer
 * @Description: 初始化程序后加载此方法
 * @Author: liuhm
 * @Date: 2020/3/10  上午 10:49
 */
@Component
@Slf4j
public class WSServer {

    /**
     * 定义一对线程组
     * 主线程组, 用于接受客户端的连接，但是不做任何处理，跟老板一样，不做事
     */
    private EventLoopGroup mainGroup;
    /**
     * 从线程组, 老板线程组会把任务丢给他，让手下线程组去做任务
     */
    private EventLoopGroup subGroup;

    /**
     * netty服务器的创建, ServerBootstrap 是一个启动类
     */
    private ServerBootstrap server;
    private ChannelFuture future;


    public WSServer() {
		/*mainGroup = new NioEventLoopGroup();
		subGroup = new NioEventLoopGroup();*/
        mainGroup = new NioEventLoopGroup(2, new DefaultThreadFactory("server1", true));
        subGroup = new NioEventLoopGroup(4, new DefaultThreadFactory("server2", true));
        server = new ServerBootstrap();
        // 设置主从线程组
        server.group(mainGroup, subGroup)
                // 设置nio的双向通道
                .channel(NioServerSocketChannel.class)
                // 子处理器，用于处理workerGroup
                .childHandler(new WSServerInitialzer());
    }

    public void start() {
        log.info("启动前");
        this.future = server.bind(ConfigConstants.WEB_SOCKET_PORT());
        log.info("netty websocket server  启动完毕..端口是" + ConfigConstants.WEB_SOCKET_PORT());
    }

    public void restart() throws Exception {
        shutdown();
        start();
    }

    public void shutdown() {
        if (future != null) {
            future.channel().close().syncUninterruptibly();
        }
        if (mainGroup != null) {
            mainGroup.shutdownGracefully();
        }
        if (subGroup != null) {
            subGroup.shutdownGracefully();
        }
    }
}
