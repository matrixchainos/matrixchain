package org.matrixchain.net.peer.handler;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.matrixchain.net.node.NodeManager;
import org.matrixchain.net.peer.Channel;
import org.matrixchain.net.peer.ChannelManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class PeerChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    private final static Logger logger = LoggerFactory.getLogger("");
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private ChannelManager channelManager;
    @Autowired
    private NodeManager nodeManager;

    private String remoteId;

    private boolean peerDiscoveryMode;

    public PeerChannelInitializer(String remoteId) {
        this.remoteId = remoteId;
    }

    @Override
    protected void initChannel(NioSocketChannel ch) {
        try {
            if (!peerDiscoveryMode) {
                logger.debug("");
            }

            if (notEligibleForIncomingConnection(ch)) {
                ch.disconnect();
                return;
            }

            final Channel channel = ctx.getBean(Channel.class);
            channel.setInetSocketAddress(ch.remoteAddress());
            channel.init(ch.pipeline(), remoteId, true, channelManager);

            if (!peerDiscoveryMode) {
                channelManager.add(channel);
            }
            // limit the size of receiving buffer to 1024
            ch.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(256 * 1024));
            ch.config().setOption(ChannelOption.SO_RCVBUF, 256 * 1024);
            ch.config().setOption(ChannelOption.SO_BACKLOG, 1024);

            ch.closeFuture().addListener((ChannelFutureListener) listener -> {
                channelManager.notifyDisconnect(channel);
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("init channel.");
        }
    }

    private boolean notEligibleForIncomingConnection(NioSocketChannel ch) {
        if (!isInbound()) return false;

        if (ch.remoteAddress() == null) {
            logger.debug("drop connection.");
            return true;
        }
        if (!channelManager.isAcceptingNewPeers()) {
            logger.debug("drop connection.");
            return true;
        }
//        if (!ch.remoteAddress().getAddress())
        return false;
    }

    private boolean isInbound() {
        return remoteId == null || remoteId.isEmpty();
    }

    public void setPeerDiscoveryMode(boolean peerDiscoveryMode) {
        this.peerDiscoveryMode = peerDiscoveryMode;
    }
}
