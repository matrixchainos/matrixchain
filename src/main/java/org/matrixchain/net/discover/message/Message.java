package org.matrixchain.net.discover.message;

import org.matrixchain.crypto.ECKey;
import org.matrixchain.util.FastByteComparisons;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.encoders.Hex;

import java.security.SignatureException;

import static org.matrixchain.crypto.Hash.sha3;
import static org.matrixchain.util.ByteUtil.merge;

public abstract class Message {
    private byte[] wire;
    private byte[] mdc;
    private byte[] signature;

    private byte type;
    private byte[] data;

    public static Message decode(byte[] wire) {
        if (wire.length < 98) throw new RuntimeException("Bad message");

        byte[] mdc = new byte[32];
        System.arraycopy(wire, 0, mdc, 0, 32);

        byte[] signature = new byte[65];
        System.arraycopy(wire, 32, signature, 0, 65);

        byte[] type = new byte[1];
        type[0] = wire[97];

        byte[] data = new byte[wire.length - 98];
        System.arraycopy(wire, 98, data, 0, data.length);

        byte[] mdcCheck = sha3(wire, 32, wire.length - 32);

        int check = FastByteComparisons.compareTo(mdc, 0, mdc.length, mdcCheck, 0, mdcCheck.length);

        if (check != 0) throw new RuntimeException("MDC check failed");

        Message message;
        if (type[0] == 1) {
            message = new PingMessage();
        }else if (type[0] == 2){
            message = new PongMessage();
        }else if (type[0] == 3){
            message = new FindNodeMessage();
        }else{
            message = new NeighboursMessage();
        }
        message.parse(data);

        message.mdc = mdc;
        message.signature = signature;
        message.data = data;
        message.type = type[0];
        message.wire = wire;

        return message;
    }

    public void encode(byte type, byte[] data, ECKey ecKey) {

        byte[] payload = new byte[data.length + 1];
        payload[0] = type;
        System.arraycopy(data, 0, payload, 1, data.length);

        byte[] forSig = sha3(payload);

        /* [2] Crate signature*/
        ECKey.ECDSASignature signature = ecKey.sign(forSig);

        signature.v -= 27;

        byte[] sigBytes =
                merge(BigIntegers.asUnsignedByteArray(32, signature.r),
                        BigIntegers.asUnsignedByteArray(32, signature.s), new byte[]{signature.v});

        // [3] calculate MDC
        byte[] forSha = merge(sigBytes, new byte[]{type}, data);
        byte[] mdc = sha3(forSha);

        // wrap all the data in to the packet
        this.mdc = mdc;
        this.signature = sigBytes;
        this.type = type;
        this.data = data;
        this.wire = merge(this.mdc, this.signature, new byte[]{this.type}, this.data);
    }

    public ECKey getKey() {

        byte[] r = new byte[32];
        byte[] s = new byte[32];
        byte v = signature[64];

        // todo: remove this when cpp conclude what they do here
        if (v == 1) v = 28;
        if (v == 0) v = 27;

        System.arraycopy(signature, 0, r, 0, 32);
        System.arraycopy(signature, 32, s, 0, 32);

        ECKey.ECDSASignature signature = ECKey.ECDSASignature.fromComponents(r, s, v);
        byte[] msgHash = sha3(wire, 97, wire.length - 97);

        ECKey outKey = null;
        try {
            outKey = ECKey.signatureToKey(msgHash, signature);
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        return outKey;
    }

    public String getNodeId() {
        return Hex.toHexString(getKey().getNodeId());
    }

    public byte[] getPacket() {
        return wire;
    }

    public byte getType() {
        return type;
    }

    public byte[] getData() {
        return data;
    }

    public abstract void parse(byte[] data);
}
