package org.matrixchain.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.matrixchain.crypto.ECKey;
import org.matrixchain.net.discover.DiscoveryEvent;
import org.matrixchain.net.discover.message.Message;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;

import static org.matrixchain.util.ByteUtil.toHexString;

public class DiscoverReceiver {

    private ECKey ecKey = ECKey.fromPrivate(new BigInteger("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620", 16));

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new PacketDecoder());
                        pipeline.addLast(new ReceiverHandler());
                    }
                });
        try {
            Channel channel = bootstrap.bind("192.168.130.9", 5023).sync().channel();

            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class PacketDecoder extends MessageToMessageDecoder<DatagramPacket> {
        private static final org.slf4j.Logger logger = LoggerFactory.getLogger("discover");

        @Override
        protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
            ByteBuf buf = packet.content();
            byte[] encoded = new byte[buf.readableBytes()];
            buf.readBytes(encoded);
            try {
                Message message = Message.decode(encoded);
                DiscoveryEvent event = new DiscoveryEvent(message, packet.sender());
                out.add(event);
            } catch (Exception e) {
                throw new RuntimeException("Exception processing inbound message from " + ctx.channel().remoteAddress() + ": " + toHexString(encoded), e);
            }
        }
    }

    public static class ReceiverHandler extends SimpleChannelInboundHandler<DiscoveryEvent> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("[Receiver channelActive - msg]: " + ctx.channel().remoteAddress());
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DiscoveryEvent msg) throws Exception {
            System.out.println("[Receiver channelRead0 - msg]: " + msg.getMessage().toString());
        }
    }
}
