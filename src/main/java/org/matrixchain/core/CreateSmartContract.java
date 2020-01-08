package org.matrixchain.core;

public class CreateSmartContract extends Contract {

    private final static long DEFAULT_GAS_PRICE = 20000L;
    private final static long DEFAULT_GAS_LIMIT = 200000L;
    private final static long DEFAULT_NONCE = 123456789L;

    private String originAddress;
    private String contractAddress;
    private String abi;
    private String bytecode;
    private long value;
    private String name;

    private String codeHash;

    private CreateSmartContract() {
        super(ContractType.Transfer, DEFAULT_GAS_PRICE, DEFAULT_GAS_LIMIT, DEFAULT_NONCE, null);
    }

    private CreateSmartContract(String originAddress, String contractAddress, String abi, String bytecode, String name,
                                String codeHash, long value, long gasPrice, long gasLimit, long nonce,
                                String extraData) {
        super(ContractType.CreateSmartContract, gasPrice, gasLimit, nonce, extraData);
        this.contractAddress = contractAddress;
        this.originAddress = originAddress;
        this.abi = abi;
        this.bytecode = bytecode;
        this.name = name;
        this.codeHash = codeHash;
        this.value = value;
    }

    public static CreateSmartContract create(String originAddress, String contractAddress, String abi, String bytecode,
                                             String name, String codeHash, long value) {
        return create(originAddress, contractAddress, abi, bytecode, name, codeHash, value, null);
    }

    public static CreateSmartContract create(String originAddress, String contractAddress, String abi, String bytecode,
                                             String name, String codeHash, long value, String extraData) {
        return create(originAddress, contractAddress, abi, bytecode, name, codeHash, value,
                DEFAULT_GAS_PRICE, DEFAULT_GAS_LIMIT, DEFAULT_NONCE, extraData);
    }

    public static CreateSmartContract create(String originAddress, String contractAddress, String abi, String bytecode,
                                             String name, String codeHash, long value, long gasPrice, long gasLimit,
                                             long nonce) {
        return create(originAddress, contractAddress, abi, bytecode, name, codeHash, value,
                gasPrice, gasLimit, nonce, null);
    }

    public static CreateSmartContract create(String originAddress, String contractAddress, String abi, String bytecode,
                                             String name, String codeHash, long value, long gasPrice, long gasLimit,
                                             long nonce, String extraData) {
        return new CreateSmartContract(originAddress, contractAddress, abi, bytecode, name, codeHash, value,
                gasPrice, gasLimit, nonce, extraData);
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getAbi() {
        return abi;
    }

    public void setAbi(String abi) {
        this.abi = abi;
    }

    public String getBytecode() {
        return bytecode;
    }

    public void setBytecode(String bytecode) {
        this.bytecode = bytecode;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeHash() {
        return codeHash;
    }

    public void setCodeHash(String codeHash) {
        this.codeHash = codeHash;
    }

    @Override
    public String toString() {
        return "CreateSmartContract{" +
                "originAddress='" + originAddress + '\'' +
                ", contractAddress='" + contractAddress + '\'' +
                ", abi='" + abi + '\'' +
                ", bytecode='" + bytecode + '\'' +
                ", value=" + value +
                ", name='" + name + '\'' +
                ", codeHash='" + codeHash + '\'' +
                ", type=" + type +
                ", gasPrice=" + gasPrice +
                ", gasLimit=" + gasLimit +
                ", nonce=" + nonce +
                ", timestamp=" + timestamp +
                ", extraData='" + extraData + '\'' +
                '}';
    }
}
