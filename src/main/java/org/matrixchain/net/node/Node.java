package org.matrixchain.net.node;

import org.matrixchain.crypto.ECKey;
import org.matrixchain.util.ByteUtil;
import org.spongycastle.util.encoders.Hex;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Node implements Serializable {
    private static final long serialVersionUID = -4267600517925770636L;

    private byte[] id;
    private String host;
    private int port;

    public Node(byte[] id, String host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public Node(String enodeURL) {
        try {
            URI uri = new URI(enodeURL);
            if (!uri.getScheme().equals("enode")) {
                throw new RuntimeException("expecting URL in the format enode://PUBKEY@HOST:PORT");
            }
            this.id = Hex.decode(uri.getUserInfo());
            this.host = uri.getHost();
            this.port = uri.getPort();
        } catch (URISyntaxException e) {
            throw new RuntimeException("expecting URL in the format enode://PUBKEY@HOST:PORT", e);
        }
    }

    public byte[] toBytes(){
        byte[] host = ByteUtil.hostToBytes(this.host);
        byte[] port = ByteUtil.intToBytes(this.port);

        byte[] bytes = new byte[4 + 4];
        System.arraycopy(host, 0, bytes, 0, 4);
        System.arraycopy(port, 0, bytes, 4, 4);
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
        this.id = ECKey.fromNodeId(id).getNodeId();
    }

    public byte[] getId() {
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
                "id=" + Arrays.toString(id) +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
