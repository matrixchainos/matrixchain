package org.matrixchain.net.peer.message;

import org.matrixchain.util.ByteUtil;
import org.spongycastle.util.encoders.Hex;

public class HelloMessage extends P2pMessage {

    private byte p2pVersion;
    private String clientId;
    private int listenPort;
    private String peerId;

    public HelloMessage(byte[] encoded) {
        super(encoded);
    }

    public HelloMessage(byte p2pVersion, String clientId,
                        int listenPort, String peerId) {
        this.p2pVersion = p2pVersion;
        this.clientId = clientId;
        this.listenPort = listenPort;
        this.peerId = peerId;
        this.parsed = true;
    }

    private void parse() {
        byte p2pVersionB = encoded[0];
        byte[] clientIdB = new byte[64];
        System.arraycopy(encoded, 1, clientIdB, 0, 64);
        byte[] listenPortB = new byte[4];
        System.arraycopy(encoded, 65, listenPortB, 0, 4);
        byte[] peerIdB = new byte[64];
        System.arraycopy(encoded, 69, peerIdB, 0, 64);

        this.p2pVersion = p2pVersionB;
        this.clientId = Hex.toHexString(clientIdB);
        this.listenPort = ByteUtil.byteArrayToInt(listenPortB);
        this.peerId = Hex.toHexString(peerIdB);
        this.parsed = true;
    }

    private void encode() {
        // 133
        byte[] encodeB = new byte[1 + 64 + 4 + 64];
        encodeB[0] = p2pVersion;
        byte[] clientIdB = Hex.decode(clientId);
        System.arraycopy(clientIdB, 0, encodeB, 1, 64);
        byte[] listenPortB = ByteUtil.intToBytes(listenPort);
        System.arraycopy(listenPortB, 0, encodeB, 65, 4);
        byte[] peerIdB = Hex.decode(peerId);
        System.arraycopy(peerIdB, 0, encodeB, 69, 64);

        this.encoded = encodeB;
    }

    @Override
    public byte[] getEncoded() {
        if (encoded == null) encode();
        return encoded;
    }

    @Override
    public Class<?> getAnswerMessage() {
        return null;
    }

    @Override
    public P2pMessageCodes getCommand() {
        return P2pMessageCodes.HELLO;
    }

    public byte getP2pVersion() {
        return p2pVersion;
    }

    public String getClientId() {
        return clientId;
    }

    public int getListenPort() {
        return listenPort;
    }

    public String getPeerId() {
        return peerId;
    }

    public void setP2pVersion(byte p2pVersion) {
        this.p2pVersion = p2pVersion;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    @Override
    public String toString() {
        if (!parsed) parse();
        return "[" + this.getCommand().name() + " p2pVersion="
                + this.p2pVersion + " clientId=" + this.clientId
                + " peerPort=" + this.listenPort + " peerId="
                + this.peerId + "]";
    }
}
