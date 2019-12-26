package org.matrixchain.db;

import org.junit.Test;
import org.matrixchain.core.Account;
import org.matrixchain.core.Transaction;
import org.matrixchain.core.Transfer;

public class TransactionStoreTest {

    @Test
    public void transaction(){
        Account account = Account.create("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620");

        Transfer transfer = Transfer.create("TBVyYctLxkLyaFtTP7jw5dx3h9sMhmii9C",
                100000000000L,
                "dfdasdfdsd");

        org.matrixchain.core.Transaction transaction = org.matrixchain.core.Transaction.create(account.getAddress(), transfer);
        account.signTransaction(transaction);

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

    @Test
    public void main() {
    }
}