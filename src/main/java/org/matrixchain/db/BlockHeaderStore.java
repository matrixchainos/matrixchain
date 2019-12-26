package org.matrixchain.db;

import org.matrixchain.core.BlockHeader;

public class BlockHeaderStore {

    private Repository repository;

    public BlockHeaderStore() {
        repository = new RepositoryImpl("header");
    }

    public void put(BlockHeader header) {
        repository.put(header.getRow().getHeight(), header);
    }

    public BlockHeader get(long height) {
        return (BlockHeader) repository.get(height, BlockHeader.class);
    }

    public BlockHeader get(BlockHeader header) {
        return (BlockHeader) repository.get(header.getRow().getHeight(), BlockHeader.class);
    }

    public void delete(long height) {
        repository.delete(height);
    }

    public void delete(BlockHeader header) {
        repository.delete(header.getRow().getHeight());
    }

    public boolean flush() {
        return false;
    }

}
