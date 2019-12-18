package org.matrixchain.service;

import org.matrixchain.core.Block;
import org.matrixchain.core.BlockHeader;
import org.matrixchain.core.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatrixChainServiceImpl implements MatrixChainService {

    public BlockHeader getBlockHeader(){
        return new BlockHeader("0810861a42ef3be9af05fa386",
                "155661",
                1001, 1576464924176L,  1,
                100000000, "supportConstant", 12647813);
    }

    public Transaction getTransaction(){
        Transaction transaction = new Transaction("12324334", "kayfhan", "zeyu", 1000);

        return transaction;
    }

    public List<Transaction> getTransactionList(){
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction2 = new Transaction("affdadfffffffffff", "aeee", "zeyu", 1000);
        transactionList.add(transaction2);
        Transaction transaction = getTransaction();
        transactionList.add(transaction);

        return transactionList;
    }

    public Block getBlock(){
        List<Transaction> transactionList = getTransactionList();
        BlockHeader blockHeader = getBlockHeader();
        Block block = new Block(blockHeader, transactionList);
        return block;
    }

}
