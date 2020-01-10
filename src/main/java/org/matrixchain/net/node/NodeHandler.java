package org.matrixchain.net.node;

import org.matrixchain.net.discover.DiscoveryEvent;
import org.matrixchain.net.discover.message.FindNodeMessage;
import org.matrixchain.net.discover.message.Message;
import org.matrixchain.net.discover.message.PingMessage;
import org.matrixchain.net.discover.message.PongMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

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
        logger.info("discover handler ping, {}", node.toString());
        nodeManager.nodeTable.addNode(this.node);
//        sendPong();
    }

    public void handlerPong() {

    }

    public void handlerFindNode() {

    }

    public void sendPing() {
        Message ping = PingMessage.create(nodeManager.nodeTable.getNode(), getNode(), nodeManager.key);
        sendMessage(ping);
    }

    public void sendPong() {
        Message pong = PongMessage.create(nodeManager.key);
        sendMessage(pong);
    }

    public void sendFindNode() {
        Message pong = FindNodeMessage.create(nodeManager.key);
        sendMessage(pong);
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
