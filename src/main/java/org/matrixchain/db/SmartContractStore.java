package org.matrixchain.db;

import org.matrixchain.core.SmartContract;

public class SmartContractStore {

    private Repository repository;

    public SmartContractStore() {
        repository = new RepositoryImpl("SmartContract");
    }

    public void put(SmartContract smartContract) {
        repository.put(smartContract.getContractAddress(), smartContract);
    }

    public SmartContract get(String contractAddress) {
        return (SmartContract) repository.get(contractAddress,
                SmartContract.class);
    }

    public SmartContract get(SmartContract smartContract) {
        return (SmartContract) repository.get(smartContract.getContractAddress(), SmartContract.class);
    }

    public void delete(String hash) {
        repository.delete(hash);
    }

    public void delete(SmartContract smartContract) {
        repository.delete(smartContract.getContractAddress());
    }

    public boolean flush() {
        return false;
    }

}
