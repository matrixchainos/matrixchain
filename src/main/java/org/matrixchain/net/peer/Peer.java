package org.matrixchain.net.peer;

import org.matrixchain.util.ByteUtil;
import org.spongycastle.util.encoders.Hex;

import java.net.InetAddress;

public class Peer {
    private final InetAddress address;
    private final int port;
    private final String peerId;

    public Peer(InetAddress address, int port, String peerId) {
        this.address = address;
        this.port = port;
        this.peerId = peerId;
    }

    public byte[] getEncoded() {
        byte[] encoded = new byte[4 + 4 + 64];
        byte[] addressB = this.address.getAddress();
        System.arraycopy(addressB, 0, encoded, 0, 4);
        byte[] portB = ByteUtil.intToBytes(port);
        System.arraycopy(addressB, 0, portB, 4, 4);
        byte[] peerIdB = Hex.decode(peerId);
        System.arraycopy(addressB, 0, peerIdB, 8, 64);

        return encoded;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getPeerId() {
        return peerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Peer)) {
            return false;
        }
        Peer o = (Peer) obj;
        return o.port == this.port
                && o.address.equals(this.address)
                && o.peerId.equals(this.peerId);
    }

    @Override
    public String toString() {
        return "Peer{" +
                "address=" + address +
                ", port=" + port +
                ", peerId='" + peerId + '\'' +
                '}';
    }
}
