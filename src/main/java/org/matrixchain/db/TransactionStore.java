package org.matrixchain.db;

import org.matrixchain.core.Transaction;

public class TransactionStore {

    private Repository repository;

    public TransactionStore() {
        repository = new RepositoryImpl("transaction");
    }

    public void put(String key, Transaction val) {
        repository.put(key, val);
    }

    public Transaction get(String key) {
        return (Transaction) repository.get(key, Transaction.class);
    }

    public void delete(String key) {
        repository.delete(key);
    }

    public boolean flush() {
        return false;
    }

    public static void main(String[] args) {
//        Transaction transaction = new Transaction("425f2cd0972b6934ba65823db2f4347991281d451b0d0968f675ed59c5762748", 901576744455L, 10000000L,
//                "TBVyYctLxkLyaFtTP7jw5dx3h9sMhmii9C", 10000L, "Transfer", "3ebc94896d822372e01a0101a6564683eaa0b687de5315b511660db91dd7b5593062ba44ddb6abd9d61ba58bbed7dd0e7788be7d31f5042dc88611f6c6f5e34600",
//                "TJzFW1P1TMHoqptErUazE28LDcSBwXHV4S");

//        TransactionStore store = new TransactionStore();
//        store.put(transaction.getHash(), transaction);
//        Transaction transaction1 = store.get(transaction.getHash());
//        System.out.println(transaction1);
    }
}
