package org.matrixchain.core;

import org.matrixchain.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;

import static org.apache.commons.codec.digest.DigestUtils.sha256;

public class Account {

    private ECKey ecKey;
    public static final int LENGTH = 42;
    public static final String PREFIX = "0x";
    public static final String ZERO_ADDRESS = "0x0000000000000000000000000000000000000000";

    public String getAddress(){
        return PREFIX + Hex.toHexString(ecKey.getAddress());
    }

    public byte[] getAddressBytes(){
        return ecKey.getAddress();
    }

    private Account(ECKey ecKey){
        this.ecKey = ecKey;
    }

    private Account(BigInteger privateKey){
//        System.out.println(Hex.toHexString(ECKey.fromPrivate(privateKey).getAddress()));
        this.ecKey = ECKey.fromPrivate(privateKey);
    }

    public static Account create(ECKey ecKey){
        return new Account(ecKey);
    }

    public static Account create(byte[] privateKey){
        return create(ECKey.fromPrivate(privateKey));
    }

    public static Account create(BigInteger privateKey){
        return new Account(privateKey);
    }

    public static Account create(String privateKey){
        return create(new BigInteger(privateKey, 16));
    }

//    public static Account create(String privateKey){
//        return create(new BigInteger(privateKey, 16));
//    }

    public void signTransaction(Transaction transaction) {
        if (transaction.getHash() == null)
            return ;
        transaction.setSignature(sign(transaction.getContract().toString()));
    }

    public void signBlockHeader(BlockHeader blockHeader) {
        if (blockHeader.getRow() == null)
            return ;
        blockHeader.setSignature(sign(blockHeader.getRow().toString()));
    }

    private String sign(String hash){
        return this.ecKey.sign(sha256(hash)).toHex();
    }

//    public ECKey getKey() {
//        byte[] hash = getRawHash();
//        return ECKey.recoverFromSignature(signature.v, signature, hash);
//    }

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

    public static void main(String[] args) {
        Account account = Account.create("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620");
        Account account1 = Account.create("a284c5935e33ec2c363213b6cf628da5c81defc2f96afb64690ae7a2f5535620");
        System.out.println(account.getAddress());
        System.out.println(account1.getAddress());
        System.out.println(account.equals(account1));


        Contract transfer = Transfer.create(Account.ZERO_ADDRESS,
                10000L,
                "I am reward to kay, for thank him help me.");

        Transaction transaction = new Transaction(account.getAddress(), transfer);

        account.signTransaction(transaction);

        ECKey ecKey = ECKey.fromPrivate(new BigInteger("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620", 16));
        ECKey.ECDSASignature signature = ecKey.sign(sha256(transaction.getContract().toString()));
        String sign = signature.toHex();

//        ECKey.ECDSASignature signature1 = new ECKey.ECDSASignature(Hex.decode(sign));
//        System.out.println(signature1.toHex());
    }

}
