package org.matrixchain.net.discover;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.matrixchain.facade.Server;
import org.matrixchain.net.discover.handler.DiscoverChannelInitializer;
import org.springframework.stereotype.Component;

@Component
public class DiscoverListening implements Server {

    private EventLoopGroup worker;
    private ChannelFuture channelFuture;
    private int port;

    @Override
    public void init() {
        this.port = 50521;
        System.out.println("init discover listening.");
    }

    @Override
    public void start() {
        new Thread(() -> {
            try {
                worker = new NioEventLoopGroup();

                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(worker)
                        .channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler())
                        .childHandler(new DiscoverChannelInitializer());

                channelFuture = bootstrap.bind(port).sync();

                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                worker.shutdownGracefully();
            }
        }).start();
        System.out.println("started discover listening.");
    }

    @Override
    public void stop() {
        if (channelFuture != null) {
            try {
                channelFuture.channel().close().sync();
                System.out.println("stopped discover listening.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
