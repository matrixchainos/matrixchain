//package org.matrixchain.util;
//
//import org.matrixchain.core.BlockHeader;
//import org.matrixchain.core.Transaction;
//import org.matrixchain.crypto.ECKey;
//
//import java.math.BigInteger;
//
//import static org.apache.commons.codec.digest.DigestUtils.sha256;
//
//public class SignatureUtils {
//
//    public static void sign(String privateKey, Transaction transaction) {
//        sign(new BigInteger(privateKey, 16), transaction);
//    }
//
//    public static void sign(BigInteger privateKey, Transaction transaction) {
//        ECKey ecKey = ECKey.fromPrivate(privateKey);
//        sign(ecKey, transaction);
//    }
//
//    public static void sign(ECKey ecKey, Transaction transaction) {
//        if (transaction.getHash() == null)
//            return ;
//        byte[] hash = sha256(transaction.getContract().toString());
//        ECKey.ECDSASignature signature = ecKey.sign(hash);
//        transaction.setSignature(signature.toHex());
//    }
//
//    public static void sign(String privateKey, BlockHeader blockHeader) {
//        sign(new BigInteger(privateKey, 16), blockHeader);
//    }
//
//    public static void sign(BigInteger privateKey, BlockHeader blockHeader) {
//        ECKey ecKey = ECKey.fromPrivate(privateKey);
//        sign(ecKey, blockHeader);
//    }
//
//    public static void sign(ECKey ecKey, BlockHeader blockHeader) {
//        if (blockHeader.getRow() == null)
//            return ;
//        byte[] hash = sha256(blockHeader.getRow().toString());
//        ECKey.ECDSASignature signature = ecKey.sign(hash);
//        blockHeader.setSignature(signature.toHex());
//    }
//
//}
