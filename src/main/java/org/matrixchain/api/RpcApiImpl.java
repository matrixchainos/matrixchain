package org.matrixchain.api;

import org.matrixchain.core.Block;
import org.matrixchain.core.BlockHeader;
import org.matrixchain.core.Transaction;
import org.matrixchain.service.MatrixChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RpcApiImpl implements RpcApi {

    @Autowired
    private MatrixChainService matrixChainService;

    @Override
    public BlockHeader getBlockHeader() {
        return matrixChainService.getBlockHeader();
    }

    @Override
    public Transaction getTransaction() {
        return matrixChainService.getTransaction();
    }

    @Override
    public List<Transaction> getTransactionList() {
        return matrixChainService.getTransactionList();
    }

    @Override
    public Block getBlock() {
        return matrixChainService.getBlock();
    }
}
