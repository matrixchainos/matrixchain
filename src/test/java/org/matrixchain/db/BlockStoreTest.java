package org.matrixchain.db;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.matrixchain.core.*;
import org.matrixchain.util.AccountUtil;

import java.util.ArrayList;
import java.util.List;

public class BlockStoreTest {

    private final static AccountUtil ACCOUNT_UTIL;

    static {
        ACCOUNT_UTIL = AccountUtil.create("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620");
    }

    @Test
    public void block() {

        // 0x4d9d3cb7c9a6a83b376958d49086fd90966aa485d2753f004e34e4810428d631

        Transfer transfer = Transfer.create("0xd92230002a341bdcc1088a976d608c6e35834993",
                100000000000L,
                "dfdasdfdsd");

        Transaction transaction = Transaction.create(ACCOUNT_UTIL.getAddress(), transfer);
        ACCOUNT_UTIL.signTransaction(transaction);

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        BlockHeader header = new BlockHeader(1,"0000000000ec7e29bf89a2b6fa71dd0e8185ac778e7a7e1fc1817a76bd7db5b4",
                "0000000000ec7e29bf89a2b6fa71dd0e8185ac778e7a7e1fc1817a76bd7db5b4",
                "0xd92230002a341bdcc1088a976d608c6e35834993",
                1747754727973896576L, 1576464924000L,
                100000000, "support Constant", 12647813);
        ACCOUNT_UTIL.signBlockHeader(header);

        Block block = new Block(
                header,
                transactionList
        );

        System.out.println("--------------------------------------------------------------------------");
        BlockStore store = new BlockStore();

        System.out.println(block.getHeader().getHeight());

        store.put(block);
        Block block2 = store.get(block.getHeader().getHash());

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