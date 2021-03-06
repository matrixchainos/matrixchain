package org.matrixchain.api;

import org.matrixchain.core.Block;
import org.matrixchain.core.BlockHeader;
import org.matrixchain.core.Transaction;

import java.util.List;

public interface RpcApi {
    BlockHeader getBlockHeader();
    Transaction getTransaction();
    List<Transaction> getTransactionList();
    Block getBlock();
}
