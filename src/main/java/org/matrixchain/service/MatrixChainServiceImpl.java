package org.matrixchain.service;

import org.matrixchain.core.*;
import org.matrixchain.db.BlockHeaderStore;
import org.matrixchain.db.BlockStore;
import org.matrixchain.db.TransactionStore;
import org.matrixchain.util.AccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatrixChainServiceImpl implements MatrixChainService {

    private final static AccountUtil accountUtil;
    @Autowired
    private TransactionStore transactionStore;
    @Autowired
    private BlockHeaderStore blockHeaderStore;
    @Autowired
    private BlockStore blockStore;

    static {
        accountUtil = AccountUtil.create("8b71752f9a06a5a3249d7900c91b833acf5d51b68ed3a2ce23a0cb142b72393b");
    }

    public BlockHeader getBlockHeaderByHeight(long height) {
        return blockHeaderStore.get(height);
    }

    public Transaction getTransactionByHash(String hash) {

        return transactionStore.get(hash);
    }

    public List<Transaction> getTransactionListByBlockHash(String hash) {
        Block block = getBlockByHash(hash);
        return block.getTransactions();
    }

    public List<Transaction> getTransactionListByBlockHeight(long height) {
        Block block = getBlockByHeight(height);
        return block.getTransactions();
    }

    public Block getBlockByHash(String hash) {
        return blockStore.get(hash);
    }

    public Block getBlockByHeight(long height) {
        String hash = blockHeaderStore.get(height).getHash();
        return getBlockByHash(hash);
    }

}
