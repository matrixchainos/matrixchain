package org.matrixchain.db;

import org.junit.Test;
import org.matrixchain.util.AccountUtil;
import org.matrixchain.core.Transaction;
import org.matrixchain.core.Transfer;

public class TransactionStoreTest {

    private final static AccountUtil ACCOUNT_UTIL;

    static {
        ACCOUNT_UTIL = AccountUtil.create("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620");
    }

    @Test
    public void transaction(){

        // 0x6a4a22e182a01474bbce801e00af0962d007aa7b80c83917ead4ca150c2513af
        Transfer transfer = Transfer.create("0xd92230002a341bdcc1088a976d608c6e35834993",
                100000000000L,
                "dfdasdfdsd");

        org.matrixchain.core.Transaction transaction = org.matrixchain.core.Transaction.create(ACCOUNT_UTIL.getAddress(), transfer);
        ACCOUNT_UTIL.signTransaction(transaction);

        TransactionStore store = new TransactionStore();
        store.put(transaction);
        Transaction transaction1 = store.get(transaction.getHash());

        System.out.println(transaction1);
    }

    @Test
    public void put() {
    }

    @Test
    public void get() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void flush() {
    }

}