package org.matrixchain.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Block {

    private static final Logger logger = LoggerFactory.getLogger("block");

    private BlockHeader header;

    /* Transactions */
    private List<Transaction> transactionsList = new CopyOnWriteArrayList<>();

    private Block() {
    }

    public Block(BlockHeader header, List<Transaction> transactionsList) {

        this(header.getHash(),
                header.getCoinbase(),
                header.getDifficulty(),
                header.getNumber(),
                header.getGasUsed(),
                header.getTimestamp(),
                header.getExtraData(),
                header.getNonce(),
                transactionsList);
    }

    public Block(String hash, String coinbase,
                 long difficulty, long number,
                 long gasUsed, long timestamp,
                 String extraData, long nonce,
                 List<Transaction> transactionsList) {
        this.header = new BlockHeader(hash, coinbase,
                difficulty, number,  gasUsed,
                timestamp, extraData, nonce);

        this.transactionsList = transactionsList;
        if (this.transactionsList == null) {
            this.transactionsList = new CopyOnWriteArrayList<>();
        }

    }

    public BlockHeader getHeader() {
        return this.header;
    }

//    public String gethash() {
//        return this.header.getHash();
//    }

//    public String getCoinbase() {
//        return this.header.getCoinbase();
//    }
//
//    public long getDifficulty() {
//        return this.header.getDifficulty();
//    }
//
//    public long getCumulativeDifficulty() {
//        return 121223L;
//    }
//
//    public long getTimestamp() {
//        return this.header.getTimestamp();
//    }
//
//    public long getNumber() {
//        return this.header.getNumber();
//    }
//
//    public long getGasUsed() {
//        return this.header.getGasUsed();
//    }
//
//
//    public String getExtraData() {
//        return this.header.getExtraData();
//    }
//
//    public long getNonce() {
//        return this.header.getNonce();
//    }
//
//    public void setNonce(long nonce) {
//        this.header.setNonce(nonce);
//    }
//
//    public void setExtraData(String data) {
//        this.header.setExtraData(data);
//    }
//
//    public List<Transaction> getTransactionsList() {
//        return transactionsList;
//    }

}
