package org.matrixchain.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.matrixchain.crypto.ECKey;
import org.matrixchain.net.discover.DiscoveryEvent;
import org.matrixchain.net.discover.message.FindNodeMessage;
import org.matrixchain.net.discover.message.Message;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DiscoverSender {

    private static InetSocketAddress remoteAddress = new InetSocketAddress("192.168.130.9", 5021);

    public static void main(String[] args) {
        ECKey ecKey = ECKey.fromPrivate(new BigInteger("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620", 16));

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new PacketEncoder());
                        pipeline.addLast(new ClientHandler());
                    }
                });
        try {
            Channel channel = bootstrap.bind("192.168.130.9", 5023).sync().channel();
            group.scheduleAtFixedRate(() -> {
                //5.发送数据
                Message message = FindNodeMessage.create(ecKey);
                DatagramPacket packet = new DatagramPacket(Unpooled.wrappedBuffer(message.getPacket()), remoteAddress);
                System.out.println("[Sender msg]: " + message.toString());
                channel.writeAndFlush(packet);
            }, 2, 15, TimeUnit.SECONDS);

            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class PacketEncoder extends MessageToMessageEncoder<DiscoveryEvent> {
        private static final org.slf4j.Logger logger = LoggerFactory.getLogger("discover");

        @Override
        protected void encode(ChannelHandlerContext ctx, DiscoveryEvent event, List<Object> out) throws Exception {
            try {
                byte[] bytes = event.getMessage().getPacket();

                ByteBuf buf = ctx.alloc().buffer(bytes.length);
                buf.writeBytes(bytes);
                DatagramPacket packet = new DatagramPacket(buf, remoteAddress);
                out.add(packet);
            } catch (Exception e){
                throw new RuntimeException("Exception processing inbound message from " + ctx.channel().remoteAddress() + ": " + event.toString(), e);
            }
        }
    }

    public static class ClientHandler extends SimpleChannelInboundHandler<DiscoveryEvent> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("[Sender channelActive - msg]: " + ctx.channel().remoteAddress());
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DiscoveryEvent msg) throws Exception {
            System.out.println("[Sender channelRead0 - msg]: "+msg.getMessage().toString());
        }
    }
}
