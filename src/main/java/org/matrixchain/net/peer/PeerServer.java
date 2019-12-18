package org.matrixchain.net.peer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.matrixchain.facade.Server;
import org.matrixchain.net.peer.handler.PeerChannelInitializer;
import org.springframework.stereotype.Component;

@Component
public class PeerServer implements Server {

    EventLoopGroup worker;
    EventLoopGroup boss;
    ChannelFuture channelFuture;
    private int port;

    @Override
    public void init() {
        this.port = 50525;
        System.out.println("init peer server.");
    }

    @Override
    public void start() {
        new Thread(() -> {
            try {
                worker = new NioEventLoopGroup();
                boss = new NioEventLoopGroup();

                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(boss, worker)
                        .channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler())
                        .childHandler(new PeerChannelInitializer());
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
