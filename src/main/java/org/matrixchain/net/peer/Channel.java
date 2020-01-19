package org.matrixchain.net.peer;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.matrixchain.net.node.Node;
import org.matrixchain.net.peer.handler.HandshakeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class Channel {

    private final static Logger logger = LoggerFactory.getLogger("channel");
    private Node node;

    @Autowired
    private HandshakeHandler handshakeHandler;
    private ChannelManager channelManager;
    private String remoteId;
    private boolean discoveryMode;
    private boolean isActive;
    private InetSocketAddress inetSocketAddress;

    public void init(ChannelPipeline pipeline, String remoteId, boolean discoveryMode, ChannelManager channelManager) {
        this.channelManager = channelManager;
        this.remoteId = remoteId;

        pipeline.addLast("", new ReadTimeoutHandler(3000, TimeUnit.MILLISECONDS));
        pipeline.addLast("", handshakeHandler);
        this.discoveryMode = discoveryMode;

        handshakeHandler.setRemoteId(remoteId, this);
    }

    public void initWithNode(String nodeId) {
        initWithNode(nodeId, inetSocketAddress);
    }

    public void initWithNode(String nodeId, InetSocketAddress inetSocketAddress) {
        node = new Node(nodeId, inetSocketAddress.getHostString(), inetSocketAddress.getPort());
    }

    public String getNodeId() {
        return node == null ? null : node.getId();
    }

    public void setInetSocketAddress(InetSocketAddress address) {
        this.inetSocketAddress = address;
    }

}
