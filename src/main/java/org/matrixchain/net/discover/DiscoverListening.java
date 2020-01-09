package org.matrixchain.net.discover;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import org.matrixchain.facade.Server;
import org.matrixchain.net.discover.handler.DiscoverHandler;
import org.matrixchain.net.node.NodeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class DiscoverListening implements Server {

    private EventLoopGroup group;
    private ChannelFuture channelFuture;
    private String host;
    private int port;
    @Autowired
    private NodeManager nodeManager;

    @Override
    public void init() {
        this.port = 5021;
        this.host = "192.168.130.9";
        System.out.println("init discover listening.");
    }

    @Override
    public void start() {
        DiscoveryExecutor discoveryExecutor = new DiscoveryExecutor(nodeManager);
        discoveryExecutor.run();

        new Thread(() -> {
            try {
                group = new NioEventLoopGroup();

                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(NioDatagramChannel.class)
                        .handler(new ChannelInitializer<NioDatagramChannel>() {
                            @Override
                            protected void initChannel(NioDatagramChannel ch) throws Exception {
                                ch.pipeline().addLast(new PacketDecoder());
                                DiscoverHandler discoverHandler = new DiscoverHandler(ch, nodeManager);
                                nodeManager.addMessageSender(discoverHandler);
                                ch.pipeline().addLast(discoverHandler);
                            }
                        });
                channelFuture = bootstrap.bind(host, port).sync();

                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
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
