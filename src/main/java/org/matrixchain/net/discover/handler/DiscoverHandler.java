package org.matrixchain.net.discover.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import org.matrixchain.net.discover.DiscoveryEvent;
import org.matrixchain.net.discover.Message;
import org.matrixchain.net.node.NodeManager;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.function.Consumer;

public class DiscoverHandler extends SimpleChannelInboundHandler<DiscoveryEvent> implements Consumer<DiscoveryEvent> {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("discover");

    private final static ObjectMapper mapper = new ObjectMapper();
    private Channel channel;
    NodeManager nodeManager;

    public DiscoverHandler(NioDatagramChannel ch, NodeManager nodeManager){
        this.channel = ch;
        this.nodeManager = nodeManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[discover] channel active - " + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DiscoveryEvent msg) throws Exception {
        System.out.println("[discover] channel read - ");
        System.out.println(msg.getMessage().getMsg());
        nodeManager.handleInbound(msg);
    }

    @Override
    public void accept(DiscoveryEvent discoveryEvent) {
        InetSocketAddress address = discoveryEvent.getAddress();
        System.out.println(address.toString());
        sendPacket(discoveryEvent.getMessage(), address);
    }

    public void sendPacket(Message message, InetSocketAddress address) {
        System.out.println("[discover] sendPacket - " + message);
        byte[] msg = null;
        try {
            msg = mapper.writeValueAsBytes(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        DatagramPacket packet = new DatagramPacket(Unpooled.wrappedBuffer(msg), address);
        channel.write(packet);
        channel.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
