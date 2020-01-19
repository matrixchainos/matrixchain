package org.matrixchain.net.node;

import org.matrixchain.crypto.ECKey;
import org.matrixchain.util.ByteUtil;
import org.spongycastle.util.encoders.Hex;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.matrixchain.crypto.Hash.sha3;

public class Node implements Serializable {
    private static final long serialVersionUID = -4267600517925770636L;

    private String id;
    private String host;
    private int port;

    private boolean isFakeNodeId = false;

    public Node(String id, String host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public boolean isDiscoveryNode() {
        return isFakeNodeId;
    }

    public void isDiscoveryNode(boolean isFakeNodeId) {
        this.isFakeNodeId = isFakeNodeId;
    }

    public static Node instanceOf(String addressOrEnode) {
        try {
            URI uri = new URI(addressOrEnode);
            if (uri.getScheme().equals("enode")) {
                return new Node(addressOrEnode);
            }
        } catch (URISyntaxException e) {
            // continue
        }

        final ECKey generatedNodeKey = ECKey.fromPrivate(sha3(addressOrEnode.getBytes()));
        final String generatedNodeId = Hex.toHexString(generatedNodeKey.getNodeId());
        final Node node = new Node("enode://" + generatedNodeId + "@" + addressOrEnode);
        return node;
    }

    public Node(String enodeURL) {
        try {
            URI uri = new URI(enodeURL);
            if (!uri.getScheme().equals("enode")) {
                throw new RuntimeException("expecting URL in the format enode://PUBKEY@HOST:PORT");
            }
            this.id = uri.getUserInfo();
            this.host = uri.getHost();
            this.port = uri.getPort();
        } catch (URISyntaxException e) {
            throw new RuntimeException("expecting URL in the format enode://PUBKEY@HOST:PORT", e);
        }
    }

    public byte[] toBytes(){
        byte[] id = ByteUtil.hexStringToBytes(this.id);
        byte[] host = ByteUtil.hostToBytes(this.host);
        byte[] port = ByteUtil.intToBytes(this.port);

        byte[] bytes = new byte[64 + 4 + 4];
        System.arraycopy(id, 0, bytes, 0, 64);
        System.arraycopy(host, 0, bytes, 64, 4);
        System.arraycopy(port, 0, bytes, 68, 4);
        return bytes;
    }

    public Node(byte[] data){
        byte[] id = new byte[64];
        System.arraycopy(data, 0, id, 0, 64);
        byte[] host = new byte[15];
        System.arraycopy(data, 64, host, 0, 4);
        byte[] port = new byte[4];
        System.arraycopy(data, 68, port, 0, 4);

        this.port = ByteUtil.byteArrayToInt(port);
        this.host = ByteUtil.bytesToIp(host);
        this.id = Hex.toHexString(id);
    }

    public String getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (o instanceof Node) {
            return ((Node) o).getId().equals(this.getId());
        }
        return false;
    }
}
