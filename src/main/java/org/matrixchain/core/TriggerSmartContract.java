package org.matrixchain.core;

public class TriggerSmartContract extends Contract {

    private final static long DEFAULT_GAS_PRICE = 20000L;
    private final static long DEFAULT_GAS_LIMIT = 200000L;
    private final static long DEFAULT_NONCE = 123456789L;

    private String contractAddress;
    private String data;
    private long value;

    private TriggerSmartContract() {
        super(ContractType.Transfer, DEFAULT_GAS_PRICE, DEFAULT_GAS_LIMIT, DEFAULT_NONCE, null);
    }

    private TriggerSmartContract(String contractAddress, String data, long value, long gasPrice, long gasLimit,
                                 long nonce, String extraData) {
        super(ContractType.TriggerSmartContract, gasPrice, gasLimit, nonce, extraData);
        this.contractAddress = contractAddress;
        this.data = data;
        this.value = value;
    }

    public static TriggerSmartContract create(String contractAddress, String data, long value) {
        return create(contractAddress, data, value, null);
    }

    public static TriggerSmartContract create(String contractAddress, String data, long value, String extraData) {
        return create(contractAddress, data, value, DEFAULT_GAS_PRICE, DEFAULT_GAS_LIMIT, DEFAULT_NONCE, extraData);
    }

    public static TriggerSmartContract create(String contractAddress, String data, long value, long gasPrice,
                                              long gasLimit, long nonce) {
        return create(contractAddress, data, value, gasPrice, gasLimit, nonce, null);
    }

    public static TriggerSmartContract create(String contractAddress, String data, long value, long gasPrice,
                                              long gasLimit, long nonce, String extraData) {
        return new TriggerSmartContract(contractAddress, data, value, gasPrice, gasLimit, nonce, extraData);
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TriggerSmartContract{" +
                "contractAddress='" + contractAddress + '\'' +
                ", data='" + data + '\'' +
                ", value=" + value +
                ", type=" + type +
                ", gasPrice=" + gasPrice +
                ", gasLimit=" + gasLimit +
                ", nonce=" + nonce +
                ", timestamp=" + timestamp +
                ", extraData='" + extraData + '\'' +
                '}';
    }
}
