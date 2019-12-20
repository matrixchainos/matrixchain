package org.matrixchain.core;

import org.matrixchain.crypto.ECKey;
import org.matrixchain.crypto.Sha256Hash;
import org.matrixchain.util.ByteArray;
import org.spongycastle.util.encoders.Hex;

import static org.apache.commons.codec.digest.DigestUtils.sha256;

public class Transaction {

    private String hash;

    private String signature;

    private Row row;

    private TransactionReceipt transactionReceipt;

    public Transaction() {
    }

    public Transaction(Row row) {
        this(null, null, row, null);
    }

    public Transaction(String hash, String signature, Row row) {
        this(hash, signature, row, null);
    }

    public Transaction(String hash, String signature, Row row, TransactionReceipt transactionReceipt) {

        this(hash, signature, row.getValue(), row.getToAddress(), row.gasUsed, row.getData(),
                row.getFromAddress(), row.getNonce(),transactionReceipt);
    }

    public Transaction(String hash, String signature, long value, String receiveAddress, long gasUsed,
                       String data, String sendAddress, long nonce, TransactionReceipt transactionReceipt) {
        this.hash = hash;
        this.signature = signature;
        this.row = new Row(value, receiveAddress, gasUsed, data,sendAddress, nonce);
        this.transactionReceipt = transactionReceipt;
    }

    public Transaction(long value, String receiveAddress, long gasUsed,
                       String data, String sendAddress, long nonce) {
        this.hash = null;
        this.signature = null;
        this.row = new Row(value, receiveAddress, gasUsed, data,sendAddress, nonce);
        this.transactionReceipt = null;
    }

    public String generateSignature(ECKey ecKey) {
        byte[] hash = sha256(this.row.toString());
        ECKey.ECDSASignature signature = ecKey.sign(hash);
        this.signature = signature.toHex();
        return signature.toHex();
    }

    public String generateHash() {
        this.hash = Hex.toHexString(Sha256Hash.hash(ByteArray.fromString(this.row.toString())));
        return this.hash;
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

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
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
                "hash='" + hash + '\'' +
                ", signature='" + signature + '\'' +
                ", row=" + row +
                ", transactionReceipt=" + transactionReceipt +
                '}';
    }

    class Row {
        private long value;
        private String toAddress;
        private long gasUsed;
        private String data;
        private String fromAddress;
        private long nonce;

        public Row() {
        }

        public Row(long value, String toAddress, long gasUsed, String data, String fromAddress, long nonce) {
            this.value = value;
            this.toAddress = toAddress;
            this.gasUsed = gasUsed;
            this.data = data;
            this.fromAddress = fromAddress;
            this.nonce = nonce;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }

        public String getToAddress() {
            return toAddress;
        }

        public void setToAddress(String toAddress) {
            this.toAddress = toAddress;
        }

        public long getGasUsed() {
            return gasUsed;
        }

        public void setGasUsed(long gasUsed) {
            this.gasUsed = gasUsed;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getFromAddress() {
            return fromAddress;
        }

        public void setFromAddress(String fromAddress) {
            this.fromAddress = fromAddress;
        }

        public long getNonce() {
            return nonce;
        }

        public void setNonce(long nonce) {
            this.nonce = nonce;
        }

        @Override
        public String toString() {
            return "Row{" +
                    "value=" + value +
                    ", toAddress='" + toAddress + '\'' +
                    ", gasUsed=" + gasUsed +
                    ", data='" + data + '\'' +
                    ", fromAddress='" + fromAddress + '\'' +
                    ", nonce=" + nonce +
                    '}';
        }
    }

}
