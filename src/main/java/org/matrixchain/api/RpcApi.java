package org.matrixchain.api;

import org.matrixchain.core.Block;
import org.matrixchain.core.BlockHeader;
import org.matrixchain.core.Transaction;

import java.util.List;

public interface RpcApi {
    public BlockHeader getBlockHeaderByHeight(long height);

    public Transaction getTransactionByHash(String hash);

    public List<Transaction> getTransactionListByBlockHeight(long height);

    public List<Transaction> getTransactionListByBlockHash(String hash);

    public Block getBlock(long height);

    public Block getBlock(String hash);
}
