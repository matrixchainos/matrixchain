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
    public BlockHeader getBlockHeaderByHeight(long height) {
        return matrixChainService.getBlockHeaderByHeight(height);
    }

    @Override
    public Transaction getTransactionByHash(String hash) {
        return matrixChainService.getTransactionByHash(hash);
    }

    @Override
    public List<Transaction> getTransactionListByBlockHeight(long height) {
        return matrixChainService.getTransactionListByBlockHeight(height);
    }

    @Override
    public List<Transaction> getTransactionListByBlockHash(String hash) {
        return matrixChainService.getTransactionListByBlockHash(hash);
    }

    @Override
    public Block getBlock(long height) {
        return matrixChainService.getBlockByHeight(height);
    }

    @Override
    public Block getBlock(String hash) {
        return matrixChainService.getBlockByHash(hash);
    }
}
