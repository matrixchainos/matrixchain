package org.matrixchain.net.node;

import org.matrixchain.config.SystemProperties;
import org.matrixchain.crypto.ECKey;
import org.matrixchain.net.discover.DiscoveryEvent;
import org.matrixchain.net.discover.message.FindNodeMessage;
import org.matrixchain.net.discover.message.Message;
import org.matrixchain.net.discover.message.NeighboursMessage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class NodeManager {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("NodeManager");

    private SystemProperties config;

    Consumer<DiscoveryEvent> messageSender;

    public Node homeNode;
    public NodeTable nodeTable;
    private List<Node> bootNodes;
    Map<String, NodeHandler> nodeHandlerMap = new HashMap<>();

    public ECKey key;

    @Autowired
    public NodeManager(final SystemProperties config) {
        this.config = config;
        this.key = config.getMyKey();
                this.homeNode = new Node(config.nodeId(), config.externalIp(), config.getPeerListenPort());
        bootNodes = new ArrayList<>();
        for (String boot : config.bootIpList()) {
            // since discover IP list has no NodeIds we will generate random but persistent
            bootNodes.add(Node.instanceOf(boot));
        }
        this.nodeTable = new NodeTable(homeNode, true);
    }

    public void addMessageSender(Consumer<DiscoveryEvent> messageSender) {
        this.messageSender = messageSender;
    }

    public void senderMessage(DiscoveryEvent discoveryEvent) {
        messageSender.accept(discoveryEvent);
    }

    public void addNodeHandler(NodeHandler nodeHandler) {
        String key = getKey(nodeHandler.getNode());
        if (!nodeHandlerMap.containsKey(key)) {
            nodeHandlerMap.put(key, nodeHandler);
        }
    }

    public void channelActivated(){
        for (Node node: bootNodes) {
            getNodeHandler(node);
        }
    }

    public NodeHandler getNodeHandler(Node node) {
        String key = getKey(node);
        NodeHandler handler = nodeHandlerMap.get(key);

        if (handler == null) {
            handler = new NodeHandler(node, this);
            logger.info("handler is null, create handler {}", handler.getNode().toString());
            nodeHandlerMap.put(key, handler);
            if (!node.isDiscoveryNode() && !node.getId().equals(homeNode.getId())) {
//                ethereumListener.onNodeDiscovered(ret.getNode());
            }
        } else if (!node.isDiscoveryNode() && !node.getId().equals(homeNode.getId())) {
            logger.info("handler is not null, {}", handler.getNode().toString());
//            ethereumListener.onNodeDiscovered(ret.getNode());
        }
        return handler;
    }

    private String getKey(Node n) {
        return getKey(new InetSocketAddress(n.getHost(), n.getPort()));
    }

    private String getKey(InetSocketAddress address) {
        InetAddress addr = address.getAddress();
        // addr == null if the hostname can't be resolved
        return (addr == null ? address.getHostString() : addr.getHostAddress()) + ":" + address.getPort();
    }

    public void handleInbound(DiscoveryEvent discoveryEvent) {
        Message message = discoveryEvent.getMessage();
        InetSocketAddress address = discoveryEvent.getAddress();
        Node node = new Node(message.getNodeId(), address.getHostName(), address.getPort());

        NodeHandler handler = getNodeHandler(node);

        byte type = message.getType();
        logger.info("----------------------type: {}", type);
        switch (type) {
            case 1:
                logger.info("----------------------1");
                handler.handlerPing();
                break;
            case 2:
                logger.info("----------------------2");
                handler.handlerPong();
                break;
            case 3:
                logger.info("----------------------3");
                handler.handlerFindNode((FindNodeMessage) message);
                break;
            case 4:
                logger.info("----------------------4, {}", message.toString());
                handler.handlerNeighbours((NeighboursMessage) message);
                break;
        }
    }
}
