package org.matrixchain.net.discover.message;

import org.matrixchain.crypto.ECKey;
import org.matrixchain.util.ByteUtil;

public class FindNodeMessage extends Message {
    private long expires;

    public static FindNodeMessage create(ECKey ecKey) {
        long expiration = 90 * 60 + System.currentTimeMillis() / 1000;

        byte[] data = ByteUtil.longToBytes(expiration);

        FindNodeMessage findNode = new FindNodeMessage();
        findNode.encode((byte)3, data, ecKey);
        findNode.expires = expiration;

        return findNode;
    }

    @Override
    public void parse(byte[] data) {
        this.expires = ByteUtil.byteArrayToLong(data);
    }

    public long getExpires() {
        return expires;
    }

    @Override
    public String toString() {
        return "FindNodeMessage{" +
                "expires=" + expires +
                '}';
    }
}
