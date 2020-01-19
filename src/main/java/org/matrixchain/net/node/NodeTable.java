package org.matrixchain.net.node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class NodeTable {

    private static final Logger logger = LoggerFactory.getLogger("node table");
    private Node node;
    private List<Node> nodes = new ArrayList<>();

    public NodeTable(Node node, boolean isHomeNode) {
        this.node = node;
        if (!isHomeNode) {
            addNode(this.node);
        }
    }

    public Node getNode() {
        return node;
    }

    public List<Node> getClosestNodes(String nodeId) {
        List<Node> nodeList = new ArrayList<>();
        for (Node node : nodes) {
            if (!node.getId().equals(nodeId)) {
                nodeList.add(node);
            }
        }
        return nodeList;
    }

    public synchronized Node addNode(Node node) {
        if (!nodes.contains(node)){
            nodes.add(node);
            logger.info("add node, {}", node.toString());
        }
        return null;
    }

}
