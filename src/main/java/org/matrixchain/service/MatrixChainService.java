package org.matrixchain.service;

import org.matrixchain.core.Block;
import org.matrixchain.core.BlockHeader;
import org.matrixchain.core.Transaction;

import java.util.List;

public interface MatrixChainService {
    BlockHeader getBlockHeader();
    Transaction getTransaction();
    List<Transaction> getTransactionList();
    Block getBlock();
}
