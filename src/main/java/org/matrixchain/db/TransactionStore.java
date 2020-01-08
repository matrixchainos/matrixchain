package org.matrixchain.db;

import org.matrixchain.core.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionStore {

    private Repository repository;

    public TransactionStore() {
        repository = new RepositoryImpl("transaction");
    }

    public void put(Transaction transaction) {
        repository.put(transaction.getHash(), transaction);
    }

    public Transaction get(String hash) {
        return (Transaction) repository.get(hash, Transaction.class);
    }

    public Transaction get(Transaction transaction) {
        return (Transaction) repository.get(transaction.getHash(), Transaction.class);
    }

    public void delete(String hash) {
        repository.delete(hash);
    }

    public void delete(Transaction transaction) {
        repository.delete(transaction.getHash());
    }

    public boolean flush() {
        return false;
    }

}
