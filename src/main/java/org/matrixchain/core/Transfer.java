package org.matrixchain.core;

public class Transfer extends Contract {

    private final static long DEFAULT_GAS_PRICE = 10000L;
    private final static long DEFAULT_GAS_LIMIT = 100000L;
    private final static long DEFAULT_NONCE = 123456789L;

    private String receiveAddress;
    private long amount;

    private Transfer() {
        super(ContractType.Transfer, DEFAULT_GAS_PRICE, DEFAULT_GAS_LIMIT, DEFAULT_NONCE, null);
    }

    private Transfer(String receiveAddress, long amount, long gasPrice, long gasLimit, long nonce, String extraData) {
        super(ContractType.Transfer, gasPrice, gasLimit, nonce, extraData);
        this.amount = amount;
        this.receiveAddress = receiveAddress;
    }

    public static Transfer create(String receiveAddress, long amount) {
        return create(receiveAddress, amount, null);
    }

    public static Transfer create(String receiveAddress, long amount, String extraData) {
        return create(receiveAddress, amount, DEFAULT_GAS_PRICE, DEFAULT_GAS_LIMIT, DEFAULT_NONCE, extraData);
    }

    public static Transfer create(String receiveAddress, long amount, long gasPrice, long gasLimit, long nonce) {
        return create(receiveAddress, amount, gasPrice, gasLimit, nonce, null);
    }

    public static Transfer create(String receiveAddress, long amount, long gasPrice, long gasLimit, long nonce, String extraData) {
        return new Transfer(receiveAddress, amount, gasPrice, gasLimit, nonce, extraData);
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
                ", extraData='" + extraData + '\'' +
                '}';
    }
}
