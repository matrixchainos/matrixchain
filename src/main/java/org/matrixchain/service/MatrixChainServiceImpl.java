package org.matrixchain.service;

import org.matrixchain.core.*;
import org.matrixchain.crypto.ECKey;
import org.matrixchain.util.AccountUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class MatrixChainServiceImpl implements MatrixChainService {

    private final static ECKey ecKey;

    static {
        ecKey = ECKey.fromPrivate(new BigInteger("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620", 16));
    }

    public BlockHeader getBlockHeader() {
        BlockHeader header = new BlockHeader("0000000000ec7e29bf89a2b6fa71dd0e8185ac778e7a7e1fc1817a76bd7db5b4",
                "TCEo1hMAdaJrQmvnGTCcGT2LqrGU4N7Jqf",
                55642216L,
                1000L,
                10000L,
                1576464924176L,
                "supportconstant",
                12647813L);
        header.generateSignature(ecKey);
        return header;

    }

    public Transaction getTransaction() {

        Contract transfer = Transfer.create("TBVyYctLxkLyaFtTP7jw5dx3h9sMhmii9C",
                10000L,
                "I am reward to kay, for thank him help me.");

        Transaction transaction = new Transaction("TBVyYctLxkLyaFtTP7jw5dx3h9sMhmii9C",transfer);

        String signature = AccountUtils.generateSignature(ecKey, transaction.getHash());
        transaction.setSignature(signature);

        return transaction;
    }

    public List<Transaction> getTransactionList() {
        List<Transaction> transactionList = new ArrayList<>();
        ECKey ecKey = ECKey.fromPrivate(new BigInteger("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620", 16));

        Contract transfer = Transfer.create("TBVyYctLxkLyaFtTP7jw5dx3h9sMhmii9C",
                10000L,
                "kay, for thank him help me.");

        Transaction transaction = new Transaction("TBVyYctLxkLyaFtTP7jw5dx3h9sMhmii9C",transfer);

        String signature = AccountUtils.generateSignature(ecKey, transaction.getHash());
        transaction.setSignature(signature);

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
