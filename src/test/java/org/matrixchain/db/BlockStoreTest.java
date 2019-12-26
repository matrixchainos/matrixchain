package org.matrixchain.db;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.matrixchain.core.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BlockStoreTest {

    private final static Account account;

    static {
        account = Account.create("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620");
    }

    @Test
    public void block() {

        Transfer transfer = Transfer.create("0xd92230002a341bdcc1088a976d608c6e35834993",
                100000000000L,
                "dfdasdfdsd");

        Transaction transaction = Transaction.create(account.getAddress(), transfer);
        account.signTransaction(transaction);

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        BlockHeader header = new BlockHeader("0000000000ec7e29bf89a2b6fa71dd0e8185ac778e7a7e1fc1817a76bd7db5b4",
                "0xd92230002a341bdcc1088a976d608c6e35834993",
                55642216L,
                1000L,
                10000L,
                1576464924176L,
                "supportconstant",
                12647813L);
        account.signBlockHeader(header);

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

    @Test
    public void put() {

    }

    @Test
    public void get() {
    }

    @Test
    public void get1() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void delete1() {
    }

    @Test
    public void flush() {
    }
}