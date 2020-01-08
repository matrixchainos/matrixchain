package org.matrixchain.core;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "Contract")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = Transfer.class, name = "Transfer"),
        @JsonSubTypes.Type(value = CreateSmartContract.class, name = "CreateSmartContract"),
        @JsonSubTypes.Type(value = TriggerSmartContract.class, name = "TriggerSmartContract"),
})
public class Contract {

    protected ContractType type;

    protected long gasPrice;

    protected long gasLimit;

    protected long nonce;

    protected long timestamp;

    protected String extraData;

    protected Contract() {

    }

    protected Contract(ContractType type, long gasPrice, long gasLimit, long nonce, String extraData) {
        this.type = type;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.nonce = nonce;
        this.timestamp = new Date().getTime();
        this.extraData = extraData;
    }

    public ContractType getType() {
        return type;
    }

    public long getGasPrice() {
        return gasPrice;
    }

    public long getGasLimit() {
        return gasLimit;
    }

    public long getNonce() {
        return nonce;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setType(ContractType type) {
        this.type = type;
    }

    public void setGasPrice(long gasPrice) {
        this.gasPrice = gasPrice;
    }

    public void setGasLimit(long gasLimit) {
        this.gasLimit = gasLimit;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "type=" + type +
                ", gasPrice=" + gasPrice +
                ", gasLimit=" + gasLimit +
                ", nonce=" + nonce +
                ", timestamp=" + timestamp +
                ", extraData='" + extraData + '\'' +
                '}';
    }
}
