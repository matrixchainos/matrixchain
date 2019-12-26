package org.matrixchain.core;

import com.alibaba.fastjson.JSONObject;
import org.matrixchain.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;

import static org.apache.commons.codec.digest.DigestUtils.sha256;

public class Transaction {

    private Contract contract;

    private String ownerAddress;

    private String signature;

    private String hash;

    private TransactionReceipt transactionReceipt;

    public Transaction() {
    }

    public Transaction(String ownerAddress, Contract contract) {
        this(ownerAddress, contract, null);
    }

    public Transaction(String ownerAddress, Contract contract,
                       String signature) {
        this.ownerAddress = ownerAddress;
        this.contract = contract;
        this.hash = "0x" + Hex.toHexString(sha256(this.contract.toString()));
        this.signature = signature;
    }

    public static Transaction create(String sendAddress, Contract contract) {
        return new Transaction(sendAddress, contract, null);
    }

    public static Transaction create(String sendAddress, Contract contract, String signature) {
        return new Transaction(sendAddress, contract, signature);
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public TransactionReceipt getTransactionReceipt() {
        return transactionReceipt;
    }

    public void setTransactionReceipt(TransactionReceipt transactionReceipt) {
        this.transactionReceipt = transactionReceipt;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "contract=" + contract +
                ", ownerAddress='" + ownerAddress + '\'' +
                ", sign='" + signature + '\'' +
                ", hash='" + hash + '\'' +
                ", transactionReceipt=" + transactionReceipt +
                '}';
    }

    public static void main(String[] args) {
        Contract transfer = Transfer.create("lll",
                10000L,
                "I am reward to kay, for thank him help me.");

        Transaction transaction = new Transaction("dsfasdf", transfer);

        ECKey ecKey = ECKey.fromPrivate(new BigInteger("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620", 16));
        byte[] hash = sha256(transaction.getContract().toString());

        ECKey.ECDSASignature signature = ecKey.sign(hash);
        transaction.setSignature(signature.toHex());

        System.out.println(JSONObject.toJSON(transaction));
        System.out.println(transaction.toString());

        System.out.println(Hex.toHexString(ecKey.getAddress()));
        System.out.println(signature.v);

        int v = signature.v;
        if (v >= 31) {
            // compressed
            v -= 4;
        }
        v = (byte)(v - 27);
        System.out.println(v);
        byte[] address = ECKey.recoverAddressFromSignature(v, signature, hash);

//        System.out.println(Hex.toHexString(key.getPrivKeyBytes()));
        if (address != null)
            System.out.println(Hex.toHexString(address));
    }
}
