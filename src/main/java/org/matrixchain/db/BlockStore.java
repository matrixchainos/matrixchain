package org.matrixchain.db;

import org.matrixchain.core.*;

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

}
