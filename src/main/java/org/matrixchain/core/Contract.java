package org.matrixchain.core;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "Contract")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = Transfer.class, name = "Transfer"),
})
@JSONType(seeAlso = {Transfer.class})
public class Contract {

    protected ContractType type;

    protected long gasPrice;

    protected long gasLimit;

    protected long nonce;

    protected long timestamp;

    protected String data;

    protected Contract() {

    }

    protected Contract(ContractType type, long gasPrice, long gasLimit, long nonce, String data) {
        this.type = type;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.nonce = nonce;
        this.timestamp = new Date().getTime();
        this.data = data;
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

    public String getData() {
        return data;
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

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "type=" + type +
                ", gasPrice=" + gasPrice +
                ", gasLimit=" + gasLimit +
                ", nonce=" + nonce +
                ", timestamp=" + timestamp +
                ", data='" + data + '\'' +
                '}';
    }
}
