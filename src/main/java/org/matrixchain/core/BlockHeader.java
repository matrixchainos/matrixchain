package org.matrixchain.core;

import org.spongycastle.util.encoders.Hex;

import static org.apache.commons.codec.digest.DigestUtils.sha256;

public class BlockHeader {

    private String signature;

    private String hash;

    private String parentHash;
    private String txTrieRoot;
    private String coinbase;
    private long difficulty;
    private long timestamp;
    private long height;
    private long gasUsed;
    private String extraData;
    private long nonce;

    private BlockHeader() {
    }

    public BlockHeader(long height, String parentHash, String txTrieRoot,
                       String coinbase, long difficulty, long timestamp, long gasUsed, String extraData,
                       long nonce) {
        this(height, parentHash, txTrieRoot, coinbase, difficulty, timestamp,
                gasUsed, extraData, nonce, null);
    }

    /*
    new BlockHeader(1,"0000000000ec7e29bf89a2b6fa71dd0e8185ac778e7a7e1fc1817a76bd7db5b4",
    "0000000000ec7e29bf89a2b6fa71dd0e8185ac778e7a7e1fc1817a76bd7db5b4",
                "0xd92230002a341bdcc1088a976d608c6e35834993",
                1747754727973896576L, 1576464924000L,
                100000000, "support Constant", 12647813);
     */
    public BlockHeader(long height, String parentHash, String txTrieRoot,
                       String coinbase, long difficulty, long timestamp, long gasUsed, String extraData,
                       long nonce, String signature) {
//        this.signature = signature;
        this.height = height;
        this.parentHash = parentHash;
        this.txTrieRoot = txTrieRoot;
        this.coinbase = coinbase;
        this.difficulty = difficulty;
        this.timestamp = timestamp;
        this.gasUsed = gasUsed;
        this.extraData = extraData;
        this.nonce = nonce;
        this.hash = encode();
        this.signature = signature;
    }

    private String encode() {
        StringBuilder codeStr = new StringBuilder().append(this.height)
                .append(this.parentHash)
                .append(this.txTrieRoot)
                .append(this.coinbase)
                .append(this.difficulty)
                .append(this.timestamp)
                .append(this.gasUsed)
                .append(this.extraData)
                .append(this.nonce);
        return "0x" + Hex.toHexString(sha256(codeStr.toString()));
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return this.signature;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getParentHash() {
        return parentHash;
    }

    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

    public String getTxTrieRoot() {
        return txTrieRoot;
    }

    public void setTxTrieRoot(String txTrieRoot) {
        this.txTrieRoot = txTrieRoot;
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

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
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
                "signature='" + signature + '\'' +
                ", hash='" + hash + '\'' +
                ", parentHash='" + parentHash + '\'' +
                ", txTrieRoot='" + txTrieRoot + '\'' +
                ", coinbase='" + coinbase + '\'' +
                ", difficulty=" + difficulty +
                ", timestamp=" + timestamp +
                ", height=" + height +
                ", gasUsed=" + gasUsed +
                ", extraData='" + extraData + '\'' +
                ", nonce=" + nonce +
                '}';
    }
}
