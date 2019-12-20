package org.matrixchain.core;

import org.matrixchain.crypto.ECKey;

import static org.apache.commons.codec.digest.DigestUtils.sha256;

public class BlockHeader {

    private String signature;

    private Row row;

    private BlockHeader() {
    }

    public BlockHeader(String parentHash, String coinbase, long difficulty, long timestamp,
                       long number, long gasUsed, String extraData, long nonce) {
        this(parentHash, coinbase, difficulty, timestamp,
                number, gasUsed, extraData, nonce,null);
    }

    public BlockHeader(Row row, String signature) {
        this(row.getParentHash(), row.getCoinbase(), row.getDifficulty(), row.getTimestamp(),
                row.getHeight(), row.getMiningRewards(), row.getExtraData(), row.getNonce(), signature);
    }

    public BlockHeader(String parentHash, String coinbase, long difficulty, long timestamp,
                       long number, long gasUsed, String extraData, long nonce, String signature) {
        this.row = new Row(parentHash, coinbase, difficulty, timestamp,
                number, gasUsed, extraData, nonce);

        this.signature = signature;
    }

    public String generateSignature(ECKey ecKey) {
        byte[] hash = sha256(this.row.toString());
        ECKey.ECDSASignature signature = ecKey.sign(hash);
        this.signature = signature.toHex();

        return signature.toHex();
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return this.signature;
    }

    public Row getRow() {
        return this.row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "BlockHeader{" +
                "signature='" + signature + '\'' +
                ", row=" + row +
                '}';
    }

    class Row {
        private String parentHash;
        private String coinbase;
        private long difficulty;
        private long timestamp;
        private long height;
        private long miningRewards;
        private String extraData;
        private long nonce;

        private Row() {
        }

        public Row(String parentHash, String coinbase, long difficulty, long timestamp, long height,
                   long miningRewards, String extraData, long nonce) {
            this.parentHash = parentHash;
            this.coinbase = coinbase;
            this.difficulty = difficulty;
            this.timestamp = timestamp;
            this.height = height;
            this.miningRewards = miningRewards;
            this.extraData = extraData;
            this.nonce = nonce;
        }

        public String getParentHash() {
            return parentHash;
        }

        public void setParentHash(String parentHash) {
            this.parentHash = parentHash;
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

        public long getMiningRewards() {
            return miningRewards;
        }

        public void setMiningRewards(long miningRewards) {
            this.miningRewards = miningRewards;
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
            return "Row{" +
                    "parentHash='" + parentHash + '\'' +
                    ", coinbase='" + coinbase + '\'' +
                    ", difficulty=" + difficulty +
                    ", timestamp=" + timestamp +
                    ", height=" + height +
                    ", miningRewards=" + miningRewards +
                    ", extraData='" + extraData + '\'' +
                    ", nonce=" + nonce +
                    '}';
        }
    }

}
