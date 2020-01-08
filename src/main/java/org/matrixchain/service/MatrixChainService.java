package org.matrixchain.service;

import org.matrixchain.core.Block;
import org.matrixchain.core.BlockHeader;
import org.matrixchain.core.Transaction;

import java.util.List;

public interface MatrixChainService {
    BlockHeader getBlockHeaderByHeight(long height);
    Transaction getTransactionByHash(String hash);
    List<Transaction> getTransactionListByBlockHash(String hash);
    List<Transaction> getTransactionListByBlockHeight(long height);
    Block getBlockByHeight(long hash);
    Block getBlockByHash(String hash);
}
