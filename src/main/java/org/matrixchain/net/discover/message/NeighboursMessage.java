package org.matrixchain.net.discover.message;

import org.matrixchain.crypto.ECKey;
import org.matrixchain.net.node.Node;
import org.matrixchain.util.ByteUtil;

import java.util.ArrayList;
import java.util.List;

public class NeighboursMessage extends Message {

    private List<Node> nodes;
    private long expires;

    public static NeighboursMessage create(List<Node> nodes, ECKey ecKey) {
        long expiration = 90 * 60 + System.currentTimeMillis() / 1000;

        byte[] nodesB = new byte[nodes.size() * 72];
        for (int i = 0; i < nodes.size(); i++) {
            byte[] nodeBytes = nodes.get(i).toBytes();
            System.arraycopy(nodeBytes, 0, nodesB, 72 * i, nodeBytes.length);
        }
        byte[] expiresB = ByteUtil.longToBytes(expiration);
        byte[] data = ByteUtil.parseByteList(nodesB, expiresB);

        NeighboursMessage neighboursMessage = new NeighboursMessage();
        neighboursMessage.encode((byte) 4, data, ecKey);
        neighboursMessage.nodes = nodes;
        neighboursMessage.expires = expiration;

        return neighboursMessage;
    }

    @Override
    public void parse(byte[] data) {
        List<Node> nodeList = new ArrayList<>();
        int len = data.length / 72;
        for (int i = 0; i < len; i++) {
            byte[] nodeBytes = new byte[72];
            System.arraycopy(data, i * 72, nodeBytes, 0, 72);
            nodeList.add(new Node(nodeBytes));
        }
        this.nodes = nodeList;

        byte[] expiresB = new byte[8];
        System.arraycopy(data, len * 72, expiresB, 0, 8);
        this.expires = ByteUtil.byteArrayToLong(expiresB);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public long getExpires() {
        return expires;
    }

    @Override
    public String toString() {
        return "NeighboursMessage{" +
                "nodes=" + nodes +
                ", expires=" + expires +
                '}';
    }

}
