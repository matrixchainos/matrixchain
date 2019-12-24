package org.matrixchain.util;

import org.matrixchain.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;

public class AccountUtils {

    public static String generateSignature(ECKey ecKey, String hash) {
        if (hash == null)
            return null;
        byte[] hashB = Hex.decode(hash);
        ECKey.ECDSASignature signature = ecKey.sign(hashB);
        return signature.toHex();
    }

    public static String generateSignature(ECKey ecKey, byte[] hash) {
        if (hash == null)
            return null;
        ECKey.ECDSASignature signature = ecKey.sign(hash);
        return signature.toHex();
    }

    public static String generateSignatureFromPrivate(String privateStr, byte[] hash) {
        if (hash == null)
            return null;
        ECKey ecKey = ECKey.fromPrivate(Hex.decode(privateStr));
        ECKey.ECDSASignature signature = ecKey.sign(hash);
        return signature.toHex();
    }

    public static String generateSignatureFromPrivate(BigInteger privateB, byte[] hash) {
        if (hash == null)
            return null;
        ECKey ecKey = ECKey.fromPrivate(privateB);
        ECKey.ECDSASignature signature = ecKey.sign(hash);
        return signature.toHex();
    }


}
