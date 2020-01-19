package org.matrixchain.net.node;

import org.matrixchain.net.discover.DiscoveryEvent;
import org.matrixchain.net.discover.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

public class NodeHandler {
    private final static Logger logger = LoggerFactory.getLogger("NodeHandler");

    public enum State {
        Discovered,

        Dead,

        Alive,

        Active,

        EvictCandidate,

        NonActive
    }

    private Node node;
    private NodeManager nodeManager;

    public NodeHandler(Node node, NodeManager nodeManager) {
        this.node = node;
        this.nodeManager = nodeManager;
        changeState(State.Discovered);
    }

    public InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(node.getHost(), node.getPort());
    }

    public void changeState(State state) {
        if (state == State.Discovered) {
            nodeManager.nodeTable.addNode(this.node);
            sendPing();
        }
//        else if (state == State.Active) {
//            nodeManager.nodeTable.addNode(this.node);
//        }
    }

    public void handlerPing() {
        logger.info("handler ping, {}", node.toString());
        nodeManager.nodeTable.addNode(this.node);
        sendPong();
    }

    public void handlerPong() {
        logger.info("handler pong, to state alive");
        changeState(State.Alive);
    }

    public void handlerFindNode(FindNodeMessage message) {
        logger.info("handler findNode, {}", message.getExpires());
        List<Node> closeNodes = nodeManager.nodeTable.getClosestNodes(message.getNodeId());
        sendNeighbours(closeNodes);
    }

    public void handlerNeighbours(NeighboursMessage message) {
        logger.info("handler neighbours, {}", message.getNodes().toString());
        for (Node node: message.getNodes()) {
            nodeManager.getNodeHandler(node);
        }
    }

    public void sendPing() {
        Message message = PingMessage.create(nodeManager.nodeTable.getNode(), getNode(), nodeManager.key);
        sendMessage(message);
    }

    public void sendPong() {
        Message message = PongMessage.create(nodeManager.key);
        sendMessage(message);
    }

    public void sendFindNode() {
        Message message = FindNodeMessage.create(nodeManager.key);
        sendMessage(message);
    }

    public void sendNeighbours(List<Node> nodes){
        Message message = NeighboursMessage.create(nodes, nodeManager.key);
        sendMessage(message);
    }

    public void sendMessage(Message message) {
        nodeManager.senderMessage(new DiscoveryEvent(message, getInetSocketAddress()));
    }

    public void handlerFindNode(byte[] nodeId) {

    }

    public Node getNode() {
        return node;
    }
}
