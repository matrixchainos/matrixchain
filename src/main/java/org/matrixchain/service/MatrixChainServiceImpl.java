package org.matrixchain.service;

import org.matrixchain.core.*;
import org.matrixchain.core.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatrixChainServiceImpl implements MatrixChainService {

    private final static Account account;

    static {
        account = Account.create("8b71752f9a06a5a3249d7900c91b833acf5d51b68ed3a2ce23a0cb142b72393b");
    }

    public BlockHeader getBlockHeader() {
        BlockHeader header = new BlockHeader("0000000000ec7e29bf89a2b6fa71dd0e8185ac778e7a7e1fc1817a76bd7db5b4",
                account.getAddress(),
                55642216L,
                1000L,
                10000L,
                1576464924176L,
                "supportconstant",
                12647813L);
        account.signBlockHeader(header);
        return header;
    }

    public Transaction getTransaction() {

        Transfer transfer = Transfer.create(Account.ZERO_ADDRESS,
                10000L,
                "I am reward to kay, for thank him help me.");

        Transaction transaction = new Transaction(account.getAddress(), transfer);

        account.signTransaction(transaction);

        return transaction;
    }

    public List<Transaction> getTransactionList() {
        List<Transaction> transactionList = new ArrayList<>();

        Transfer transfer = Transfer.create(Account.ZERO_ADDRESS,
                10000L,
                "kay, for thank him help me.");

        Transaction transaction = new Transaction(account.getAddress(), transfer);

        account.signTransaction(transaction);

        transactionList.add(transaction);
        transactionList.add(getTransaction());
        return transactionList;
    }

    public Block getBlock() {
        List<Transaction> transactionList = getTransactionList();

        BlockHeader blockHeader = getBlockHeader();
        Block block = new Block(
                blockHeader,
                transactionList
        );
        block.setHash(block.generateHash());

        return block;
    }

}
