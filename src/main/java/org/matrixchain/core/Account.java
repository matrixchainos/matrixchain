package org.matrixchain.core;

import org.matrixchain.crypto.ECKey;
import org.matrixchain.crypto.Sign;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.util.Arrays;

import static org.apache.commons.codec.digest.DigestUtils.sha256;

public class Account {

    private ECKey ecKey;
    public static final int LENGTH = 42;
    public static final String PREFIX = "0x";
    public static final String ZERO_ADDRESS = "0x0000000000000000000000000000000000000000";

    public String getAddress() {
        return PREFIX + Hex.toHexString(ecKey.getAddress());
    }

    public byte[] getAddressBytes() {
        return ecKey.getAddress();
    }

    private Account(ECKey ecKey) {
        this.ecKey = ecKey;
    }

    private Account(BigInteger privateKey) {
        this.ecKey = ECKey.fromPrivate(privateKey);
    }

    public static Account create(ECKey ecKey) {
        return new Account(ecKey);
    }

    public static Account create(byte[] privateKey) {
        return create(ECKey.fromPrivate(privateKey));
    }

    public static Account create(BigInteger privateKey) {
        return new Account(privateKey);
    }

    public static Account create(String privateKey) {
        return create(new BigInteger(privateKey, 16));
    }

    public void signTransaction(Transaction transaction) {
        if (transaction.getHash() == null)
            return;
        transaction.setSignature(sign(transaction.getContract().toString()));
    }

    public void signBlockHeader(BlockHeader blockHeader) {
        if (blockHeader.getRow() == null)
            return;
        blockHeader.setSignature(sign(blockHeader.getRow().toString()));
    }

    private String sign(String hash) {
        return this.ecKey.sign(sha256(hash)).toHex();
    }

    public ECKey.ECDSASignature getECDSASignature(String signature, byte[] msgHash) {
        byte[] signatureBytes = Hex.decode(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }

        Sign.SignatureData sd =
                new Sign.SignatureData(
                        v,
                        (byte[]) Arrays.copyOfRange(signatureBytes, 0, 32),
                        (byte[]) Arrays.copyOfRange(signatureBytes, 32, 64));

        return new ECKey.ECDSASignature(
                new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;

        return this.ecKey.getPrivKey() != null ? this.ecKey.getPrivKey().equals(account.ecKey.getPrivKey()) : account.ecKey == null;
    }

    @Override
    public int hashCode() {
        return ecKey != null ? ecKey.hashCode() : 0;
    }

}
