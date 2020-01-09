package org.matrixchain.net.discover.message;

import org.matrixchain.crypto.ECKey;
import org.matrixchain.net.node.Node;
import org.matrixchain.util.ByteUtil;

public class PingMessage extends Message {
    private String toHost;
    private int toPort;
    private String fromHost;
    private int fromPort;
    private long expires;
    private int version;

    public static PingMessage create(Node toNode, Node fromNode, ECKey ecKey, int version) {
        long expiration = 90 * 60 + System.currentTimeMillis() / 1000;

        byte[] toNodeB = toNode.toBytes();
        byte[] fromNodeB = fromNode.toBytes();
        byte[] expiresB = ByteUtil.longToBytes(expiration);
        byte[] versionB = ByteUtil.intToBytes(version);
        byte[] data = ByteUtil.parseByteList(toNodeB, fromNodeB, expiresB, versionB);

        PingMessage pingMessage = new PingMessage();
        pingMessage.encode((byte) 1, data, ecKey);
        pingMessage.toHost = toNode.getHost();
        pingMessage.toPort = toNode.getPort();
        pingMessage.fromHost = fromNode.getHost();
        pingMessage.fromPort = fromNode.getPort();
        pingMessage.expires = expiration;
        pingMessage.version = version;

        return pingMessage;
    }

    public void parse(byte[] data) {
        byte[] toHost = new byte[4];
        System.arraycopy(data, 0, toHost, 0, 4);
        byte[] toPort = new byte[4];
        System.arraycopy(data, 4, toPort, 0, 4);
        byte[] fromHost = new byte[4];
        System.arraycopy(data, 8, fromHost, 0, 4);
        byte[] fromPort = new byte[4];
        System.arraycopy(data, 12, fromPort, 0, 4);

        byte[] expires = new byte[8];
        System.arraycopy(data, 16, expires, 0, 8);
        byte[] version = new byte[4];
        System.arraycopy(data, 24, version, 0, 4);

        this.toHost = ByteUtil.bytesToIp(toHost);
        this.toPort = ByteUtil.byteArrayToInt(toPort);
        this.fromHost = ByteUtil.bytesToIp(fromHost);
        this.fromPort = ByteUtil.byteArrayToInt(fromPort);
        this.expires = ByteUtil.byteArrayToLong(expires);
        this.version = ByteUtil.byteArrayToInt(version);
    }

    public String getToHost() {
        return toHost;
    }

    public int getToPort() {
        return toPort;
    }

    public String getFromHost() {
        return fromHost;
    }

    public int getFromPort() {
        return fromPort;
    }

    public long getExpires() {
        return expires;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "PingMessage{" +
                "toHost='" + toHost + '\'' +
                ", toPort=" + toPort +
                ", fromHost='" + fromHost + '\'' +
                ", fromPort=" + fromPort +
                ", expires=" + expires +
                ", version=" + version +
                '}';
    }
}
