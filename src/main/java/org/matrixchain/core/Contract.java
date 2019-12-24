package org.matrixchain.core;

import java.util.Date;

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

    public long getTimestamp() {
        return timestamp;
    }

    public String getData() {
        return data;
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

}
