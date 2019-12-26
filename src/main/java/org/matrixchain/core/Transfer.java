package org.matrixchain.core;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "transfer")
public class Transfer extends Contract {

    private final static long DEFAULT_GAS_PRICE = 10000L;
    private final static long DEFAULT_GAS_LIMIT = 100000L;
    private final static long DEFAULT_NONCE = 123456789L;

    private String receiveAddress;
    private long amount;

    private Transfer() {
    }

    private Transfer(String receiveAddress, long amount, long gasPrice, long gasLimit, long nonce, String data) {
        super(ContractType.TRANSFER, gasPrice, gasLimit, nonce, data);
        this.amount = amount;
        this.receiveAddress = receiveAddress;
    }

    public static Transfer create(long amount, String receiveAddress) {
        return create(receiveAddress, amount, null);
    }

    public static Transfer create(String receiveAddress, long amount, String data) {
        return create(receiveAddress, amount, DEFAULT_GAS_PRICE, DEFAULT_GAS_LIMIT, DEFAULT_NONCE, data);
    }

    public static Transfer create(String receiveAddress, long amount, long gasPrice, long gasLimit, long nonce) {
        return create(receiveAddress, amount, gasPrice, gasLimit, nonce, null);
    }

    public static Transfer create(String receiveAddress, long amount, long gasPrice, long gasLimit, long nonce, String data) {
        return new Transfer(receiveAddress, amount, gasPrice, gasLimit, nonce, data);
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public long getAmount() {
        return amount;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "receiveAddress='" + receiveAddress + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", gasPrice=" + gasPrice +
                ", gasLimit=" + gasLimit +
                ", nonce=" + nonce +
                ", timestamp=" + timestamp +
                ", data='" + data + '\'' +
                '}';
    }
}
