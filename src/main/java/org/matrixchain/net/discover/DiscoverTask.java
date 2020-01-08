package org.matrixchain.net.discover;

import org.matrixchain.net.node.NodeManager;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class DiscoverTask implements Runnable {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("discover");
    NodeManager nodeManager;

    public DiscoverTask(NodeManager nodeManager) {
        this.nodeManager = nodeManager;
    }

    @Override
    public void run() {
        Message message = new Message(1, "kay");
        InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved("192.168.130.9", 5023);
        DiscoveryEvent discoveryEvent = new DiscoveryEvent(message, inetSocketAddress);
        nodeManager.senderMessage(discoveryEvent);
    }

    public void discover() {

    }
}
