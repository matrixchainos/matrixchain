package org.matrixchain.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Block {

    private static final Logger logger = LoggerFactory.getLogger("Block");

//    private String hash;

    private BlockHeader header;

    /* Transactions */
    private List<Transaction> transactions = new CopyOnWriteArrayList<>();

    private Block() {
    }

    public Block(BlockHeader header,
                 List<Transaction> transactions) {
        this.header = header;

        this.transactions = transactions;
        if (this.transactions == null)
            this.transactions = new CopyOnWriteArrayList<>();
    }

    public Block(long height, String parentHash, String txTrieRoot, String coinbase,
                 long difficulty,
                 long gasUsed, long timestamp,
                 String extraData, long nonce, String signature,
                 List<Transaction> transactions, String hash) {
        this.header = new BlockHeader(height, parentHash, txTrieRoot, coinbase,
                difficulty, gasUsed,
                timestamp, extraData, nonce, signature);

        this.transactions = transactions;
        if (this.transactions == null)
            this.transactions = new CopyOnWriteArrayList<>();
//        this.hash = hash;
    }

//    public void setHash(String hash) {
//        this.hash = hash;
//    }
//
//    public String getHash() {
//        return this.hash;
//    }

    public BlockHeader getHeader() {
        return header;
    }

    public void setHeader(BlockHeader header) {
        this.header = header;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Block{" +
//                "hash='" + hash + '\'' +
                ", header=" + header +
                ", transactions=" + transactions +
                '}';
    }
}
