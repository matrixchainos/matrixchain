package org.matrixchain.db;

import com.alibaba.fastjson.JSONObject;
import org.matrixchain.core.Block;
import org.matrixchain.core.BlockHeader;
import org.matrixchain.core.Transaction;
import org.matrixchain.crypto.ECKey;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BlockStore {

    private Repository repository;

    public BlockStore() {
        repository = new RepositoryImpl("block");
    }

    public void put(Block block) {
        repository.put(block.getHash(), block);
    }

    public Block get(String hash) {
        return (Block) repository.get(hash, Block.class);
    }

    public Block get(Block block) {
        return (Block) repository.get(block.getHash(), Block.class);
    }

    public void delete(String hash) {
        repository.delete(hash);
    }

    public void delete(Block block) {
        repository.delete(block.getHash());
    }

    public boolean flush() {
        return false;
    }

    public static void main(String[] args) {
        ECKey ecKey = ECKey.fromPrivate(new BigInteger("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620", 16));

        Transaction transaction = new Transaction(
                10000000L,
                "TBVyYctLxkLyaFtTP7jw5dx3h9sMhmii9C",
                10000L,
                "Transfer",
                "TJzFW1P1TMHoqptErUazE28LDcSBwXHV4S",
                8234683746L
        );
        transaction.generateSignature(ecKey);
        transaction.generateHash();

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        BlockHeader header = new BlockHeader("0000000000ec7e29bf89a2b6fa71dd0e8185ac778e7a7e1fc1817a76bd7db5b4",
                "TCEo1hMAdaJrQmvnGTCcGT2LqrGU4N7Jqf",
                55642216L,
                1000L,
                10000L,
                1576464924176L,
                "supportconstant",
                12647813L);
        header.setSignature(header.generateSignature(ecKey));

        Block block = new Block(
                header,
                transactionList
        );
        block.setHash(block.generateHash());

        System.out.println("--------------------------------------------------------------------------");
        BlockStore store = new BlockStore();

        System.out.println(block.getHeader().getRow().getHeight());

        store.put(block);
        Block block2 = store.get(block.getHash());

        System.out.println("value store: " + JSONObject.toJSON(block2));
        System.out.println("--------------------------------------------------------------------------");
    }

}
