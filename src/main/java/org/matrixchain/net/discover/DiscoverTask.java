package org.matrixchain.net.discover;

import org.matrixchain.crypto.ECKey;
import org.matrixchain.net.node.Node;
import org.matrixchain.net.node.NodeManager;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DiscoverTask implements Runnable {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("discover");
    private ECKey ecKey = ECKey.fromPrivate(new BigInteger("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620", 16));

    NodeManager nodeManager;
    String nodeId;

    public DiscoverTask(NodeManager nodeManager) {
        this.nodeManager = nodeManager;
        this.nodeId = nodeManager.homeNode.getId();
    }

    @Override
    public void run() {
//        Node toNode = new Node("enode://04c18727a23ed1f5d95508c81e926ee021a1b240fd0a902ff483a50cfa7977cf60ca96653ab622d50310a89617620fc18fa0516a7251751dc5b71ac7d3557998b2@192.168.130.9:5021");
//        Node fromNode = new Node("enode://04595853a0ad8948ed9a98ae2ddcf35a72c1367317e242a9c98c87e97e04c6a6399a6b7c10e06b88ac137abd5c5b945a646847c55517c8eb2b9086390c5df86410@192.168.130.9:5023");
//        Message message = PingMessage.create(toNode, fromNode, ecKey, 1);
//        InetSocketAddress inetSocketAddress = new InetSocketAddress("192.168.130.9", 5021);
//        DiscoveryEvent discoveryEvent = new DiscoveryEvent(message, inetSocketAddress);
//        nodeManager.senderMessage(discoveryEvent);
//
//        nodeManager.addNodeHandler(new NodeHandler(toNode));
        discover(nodeId, 0, new ArrayList<Node>());
    }

    public void discover(String nodeId, int round, List<Node> preNodeList) {
        try {
            if (round == KademliaOptions.MAX_STEPS) {
//                logger.info("discover, round {}", round);
                return;
            }

            List<Node> nodeList = nodeManager.nodeTable.getClosestNodes(nodeId);
//            logger.info("discover, nodeList {}", nodeList.toString());
            List<Node> tryNodeList = new ArrayList<>();

            for (Node node : nodeList) {
                if (!tryNodeList.contains(node) && !preNodeList.contains(node)) {
                    nodeManager.getNodeHandler(node).sendFindNode();
                    tryNodeList.add(node);

                    Thread.sleep(50);
                }
                if (tryNodeList.size() == KademliaOptions.ALPHA) {
                    break;
                }
            }
            if (tryNodeList.isEmpty()) {
//                logger.info("discover tryNodeList, {}", preNodeList.toString());
                return;
            }

            tryNodeList.addAll(preNodeList);
            discover(nodeId, round + 1, tryNodeList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620
        // f20f297d76cc0666b5840f16fb2b9d807297ed6f811b2a72feb78bec384c6864
        ECKey ecKey = ECKey.fromPrivate(new BigInteger("f20f297d76cc0666b5840f16fb2b9d807297ed6f811b2a72feb78bec384c6864", 16));
        System.out.println(Hex.toHexString(ecKey.getPubKey()));
    }
}
