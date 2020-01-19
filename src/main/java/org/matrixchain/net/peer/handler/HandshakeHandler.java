package org.matrixchain.net.peer.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.matrixchain.config.SystemProperties;
import org.matrixchain.crypto.ECKey;
import org.matrixchain.net.node.NodeManager;
import org.matrixchain.net.peer.Channel;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetSocketAddress;
import java.util.List;

public class HandshakeHandler extends ByteToMessageDecoder {

    private SystemProperties config;
    private NodeManager nodeManager;
    private Channel channel;
    private EncryptionHandshake handshake;

    private String nodeId;
    private String remoteId;
    private boolean isHandshakeDone;
    private final ECKey myKey;

    @Autowired
    public HandshakeHandler(final SystemProperties config, final NodeManager nodeManager) {
        this.config = config;
        this.nodeManager = nodeManager;

        myKey = config.getMyKey();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        channel.setInetSocketAddress((InetSocketAddress) ctx.channel().remoteAddress());
        if (remoteId.length() == 64) {
            channel.initWithNode(remoteId);
            initiate(ctx);
        } else {
            handshake = new EncryptionHandshake();
            nodeId = Hex.toHexString(myKey.getNodeId());
        }
    }

    public void initiate(ChannelHandlerContext ctx) {
        nodeId = Hex.toHexString(myKey.getNodeId());
        handshake = new EncryptionHandshake(ECKey.fromNodeId(Hex.decode(this.nodeId)).getPubKeyPoint());

        Object msg;

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

        decodeHandshake(ctx, in);
        if (isHandshakeDone) {
            ctx.pipeline().remove(this);
        }
    }

    public void decodeHandshake(ChannelHandlerContext ctx, ByteBuf in) {

    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setRemoteId(String remoteId, Channel channel) {
        this.remoteId = remoteId;
        this.channel = channel;
    }

    public String getRemoteId() {
        return remoteId;
    }
}
