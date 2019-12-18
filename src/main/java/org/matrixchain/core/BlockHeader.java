package org.matrixchain.core;

import java.io.Serializable;

public class BlockHeader implements Serializable {

    /* The SHA3 256-bit hash of the parent block, in its entirety */
    private String hash;
    /* The 160-bit address to which all fees collected from the
     * successful mining of this block be transferred; formally */
    private String coinbase;
    /* A scalar value corresponding to the difficulty level of this block.
     * This can be calculated from the previous block’s difficulty level
     * and the timestamp */
    private long difficulty;
    /* A scalar value equal to the reasonable output of Unix's time()
     * at this block's inception */
    private long timestamp;
    /* A scalar value equal to the number of ancestor blocks.
     * The genesis block has a number of zero */
    private long number;
    /* A scalar value equal to the total gas used in transactions in this block */
    private long gasUsed;

    /* An arbitrary byte array containing data relevant to this block.
     * With the exception of the genesis block, this must be 32 bytes or fewer */
    private String extraData;
    /* A 256-bit hash which proves that a sufficient amount
     * of computation has been carried out on this block */
    private long nonce;

    public BlockHeader() {
    }

    public BlockHeader(String hash, String coinbase, long difficulty, long timestamp, long number,
                       long gasUsed, String extraData, long nonce) {
        this.hash = hash;
        this.coinbase = coinbase;
        this.difficulty = difficulty;
        this.timestamp = timestamp;
        this.number = number;
        this.gasUsed = gasUsed;
        this.extraData = extraData;
        this.nonce = nonce;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCoinbase() {
        return coinbase;
    }

    public void setCoinbase(String coinbase) {
        this.coinbase = coinbase;
    }

    public long getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(long difficulty) {
        this.difficulty = difficulty;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(long gasUsed) {
        this.gasUsed = gasUsed;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "BlockHeader{" +
                "hash='" + hash + '\'' +
                ", coinbase='" + coinbase + '\'' +
                ", difficulty=" + difficulty +
                ", timestamp=" + timestamp +
                ", number=" + number +
                ", gasUsed=" + gasUsed +
                ", extraData='" + extraData + '\'' +
                ", nonce=" + nonce +
                '}';
    }
}
