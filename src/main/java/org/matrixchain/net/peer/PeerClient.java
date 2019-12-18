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
public class PeerClient implements Server {

    EventLoopGroup group;
    ChannelFuture channelFuture;
    private int port;

    @Override
    public void init() {
        this.port = 50523;
        System.out.println("init peer client.");
    }

    @Override
    public void start() {
        new Thread(() -> {
            try {
                group = new NioEventLoopGroup();

                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(group)
                        .channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler())
                        .childHandler(new PeerChannelInitializer());

                channelFuture = bootstrap.bind(port).sync();

                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }
        }).start();
        System.out.println("started peer client.");
    }

    @Override
    public void stop() {
        if (channelFuture != null) {
            try {
                System.out.println("stopped peer client.");
                channelFuture.channel().close().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
