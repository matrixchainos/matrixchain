package org.matrixchain.core;

import org.matrixchain.crypto.ECKey;
import org.matrixchain.crypto.Sha256Hash;
import org.matrixchain.util.ByteArray;
import org.spongycastle.util.encoders.Hex;

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
        this.hash = Hex.toHexString(sha256(this.contract.toString()));
        this.signature = signature;
    }

    public static Transaction create(String sendAddress, Contract contract) {
        return new Transaction(sendAddress, contract,  null);
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
                ", signature='" + signature + '\'' +
                ", hash='" + hash + '\'' +
                ", transactionReceipt=" + transactionReceipt +
                '}';
    }
}
