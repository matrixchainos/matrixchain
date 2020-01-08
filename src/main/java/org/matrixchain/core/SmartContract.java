package org.matrixchain.core;

public class SmartContract {
    private String originAddress;
    private String contractAddress;
    private String abi;
    private String bytecode;
    private long value;
    private String name;

    private String codeHash;

    public SmartContract() {
    }

    public SmartContract(String originAddress, String contractAddress, String abi, String bytecode, String name,
                         String codeHash, long value) {
        this.contractAddress = contractAddress;
        this.originAddress = originAddress;
        this.abi = abi;
        this.bytecode = bytecode;
        this.name = name;
        this.codeHash = codeHash;
        this.value = value;
    }

    public static SmartContract create(String originAddress, String contractAddress, String abi, String bytecode,
                                       String name,String codeHash, long value) {
        return new SmartContract(originAddress, contractAddress, abi, bytecode, name, codeHash, value);
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
        return "SmartContract{" +
                "originAddress='" + originAddress + '\'' +
                ", contractAddress='" + contractAddress + '\'' +
                ", abi='" + abi + '\'' +
                ", bytecode='" + bytecode + '\'' +
                ", value=" + value +
                ", name='" + name + '\'' +
                ", codeHash='" + codeHash + '\'' +
                '}';
    }
}
