package org.matrixchain.core;

import org.matrixchain.crypto.Sha256Hash;
import org.matrixchain.util.ByteArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Block {

    private static final Logger logger = LoggerFactory.getLogger("Block");

    private String hash;

    private BlockHeader header;

    /* Transactions */
    private List<Transaction> transactions = new CopyOnWriteArrayList<>();

    private Block() {
    }

    public Block(BlockHeader header, List<Transaction> transactions) {

        this(header.getRow(),
                header.getSignature(),
                transactions,
                null);
    }

    public Block(BlockHeader.Row row, String signature,
                 List<Transaction> transactions, String hash) {
        this.header = new BlockHeader(row, signature);

        this.transactions = transactions;
        if (this.transactions == null)
            this.transactions = new CopyOnWriteArrayList<>();

        this.hash = hash;
    }

    public Block(String parentHash, String coinbase,
                 long difficulty, long number,
                 long gasUsed, long timestamp,
                 String extraData, long nonce, String signature,
                 List<Transaction> transactions, String hash) {
        this.header = new BlockHeader(parentHash, coinbase,
                difficulty, number, gasUsed,
                timestamp, extraData, nonce, signature);

        this.transactions = transactions;
        if (this.transactions == null)
            this.transactions = new CopyOnWriteArrayList<>();
        this.hash = hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return this.hash;
    }

    public String generateHash() {
        this.hash = Hex.toHexString(Sha256Hash.hash(ByteArray.fromString(this.header.getRow().toString())));
        return this.hash;
    }

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
                "hash='" + hash + '\'' +
                ", header=" + header +
                ", transactions=" + transactions +
                '}';
    }
}
