package org.matrixchain.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.concurrent.ScheduledFuture;
import org.matrixchain.net.discover.DiscoveryEvent;
import org.matrixchain.net.discover.Message;
import org.matrixchain.net.discover.PacketDecoder;
import org.spongycastle.util.encoders.Hex;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class DiscoverClient {

    private final static ObjectMapper mapper = new ObjectMapper();

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
                        pipeline.addLast(new ClientHandler(ch));
                    }
                });
        try {
            Channel channel = bootstrap.bind("192.168.130.9", 5023).sync().channel();
            group.scheduleAtFixedRate(() -> {
                //5.发送数据
                Message message = new Message(1, "----------kay-----------");
                InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved("192.168.130.9", 5021);
                byte[] msg = null;
                try {
                    msg = mapper.writeValueAsBytes(message);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                System.out.println("msg ----- " + Hex.toHexString(msg));
                DatagramPacket packet = new DatagramPacket(Unpooled.wrappedBuffer(msg), inetSocketAddress);

                channel.writeAndFlush(packet);
                System.out.println(packet);
            }, 2, 15, TimeUnit.SECONDS);

            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class ClientHandler extends SimpleChannelInboundHandler<DiscoveryEvent> {

        private Channel channel;

        public ClientHandler(NioDatagramChannel ch) {
            this.channel = ch;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("------------------channelActive---------------------");

//            Message message = new Message(1, "----------kay-----------");
//            InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved("127.0.0.1", 50521);
//            DiscoveryEvent discoveryEvent = new DiscoveryEvent(message, inetSocketAddress);
//
//            DatagramPacket packet = new DatagramPacket(Unpooled.wrappedBuffer(discoveryEvent.getMessage().toString().getBytes()), inetSocketAddress);
//            channel.writeAndFlush(packet);
//            System.out.println("-----------------------------------");
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DiscoveryEvent msg) throws Exception {
            System.out.println("------------------channelRead0---------------------");
            System.out.println(msg.getMessage().toString());
        }
    }
}
