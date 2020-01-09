package org.matrixchain.net.discover.message;

import org.matrixchain.crypto.ECKey;
import org.matrixchain.util.ByteUtil;

public class PongMessage extends Message {
    private long expires;

    public static PongMessage create(ECKey ecKey) {
        long expiration = 90 * 60 + System.currentTimeMillis() / 1000;

        byte[] expiresB = ByteUtil.longToBytes(expiration);
        byte[] data = ByteUtil.parseByteList(expiresB);

        PongMessage pongMessage = new PongMessage();
        pongMessage.encode((byte)3, data, ecKey);
        pongMessage.expires = expiration;

        return pongMessage;
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
        return "PongMessage{" +
                "expires=" + expires +
                '}';
    }
}
