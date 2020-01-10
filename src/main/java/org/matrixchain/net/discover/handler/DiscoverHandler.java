package org.matrixchain.net.discover.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.matrixchain.net.discover.DiscoveryEvent;
import org.matrixchain.net.discover.message.Message;
import org.matrixchain.net.node.NodeManager;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.function.Consumer;

public class DiscoverHandler extends SimpleChannelInboundHandler<DiscoveryEvent> implements Consumer<DiscoveryEvent> {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("discover");

    private Channel channel;
    NodeManager nodeManager;

    public DiscoverHandler(NioDatagramChannel ch, NodeManager nodeManager) {
        this.channel = ch;
        this.nodeManager = nodeManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // udp no connection
        logger.info("channel active - ");
        nodeManager.channelActivated();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DiscoveryEvent msg) throws Exception {
        logger.info("fromClient - " + msg.getMessage().toString());
        nodeManager.handleInbound(msg);
    }

    @Override
    public void accept(DiscoveryEvent discoveryEvent) {
        InetSocketAddress address = discoveryEvent.getAddress();
        sendPacket(discoveryEvent.getMessage(), address);
    }

    public void sendPacket(Message message, InetSocketAddress address) {
        logger.info("sendPacket - " + message.toString() + ", address: " + address);
        DatagramPacket packet = new DatagramPacket(Unpooled.wrappedBuffer(message.getPacket()), address);
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
