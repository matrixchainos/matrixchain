package org.matrixchain.net.peer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultMessageSizeEstimator;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.matrixchain.config.SystemProperties;
import org.matrixchain.facade.Server;
import org.matrixchain.net.peer.handler.PeerChannelInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PeerServer implements Server {

    private SystemProperties config;
    private ApplicationContext ctx;
    private PeerChannelInitializer peerChannelInitializer;

    EventLoopGroup worker;
    EventLoopGroup boss;
    ChannelFuture channelFuture;
    private int port;

    @Autowired
    public PeerServer(final SystemProperties config, final ApplicationContext ctx) {
        this.ctx = ctx;
        this.config = config;
        this.port = config.getPeerListenPort();
    }

    @Override
    public void init() {
//        this.port = 50525;
        System.out.println("init peer server.");
    }

    @Override
    public void start() {
        peerChannelInitializer = ctx.getBean(PeerChannelInitializer.class);
        new Thread(() -> {
            try {
                worker = new NioEventLoopGroup();
                boss = new NioEventLoopGroup();

                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(boss, worker)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .option(ChannelOption.MESSAGE_SIZE_ESTIMATOR, DefaultMessageSizeEstimator.DEFAULT)
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                        .handler(new LoggingHandler())
                        .childHandler(peerChannelInitializer);
                channelFuture = bootstrap.bind(port).sync();

                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                worker.shutdownGracefully();
                boss.shutdownGracefully();
            }
        }).start();
        System.out.println("started peer server.");
    }

    @Override
    public void stop() {
        if (channelFuture != null) {
            try {
                channelFuture.channel().close().sync();
                System.out.println("stopped peer server.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
