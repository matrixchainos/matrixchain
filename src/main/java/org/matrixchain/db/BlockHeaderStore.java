package org.matrixchain.db;

import org.matrixchain.core.BlockHeader;
import org.springframework.stereotype.Component;

@Component
public class BlockHeaderStore {

    private Repository repository;

    public BlockHeaderStore() {
        repository = new RepositoryImpl("header");
    }

    public void put(BlockHeader header) {
        repository.put(header.getHeight(), header);
    }

    public BlockHeader get(long height) {
        return (BlockHeader) repository.get(height, BlockHeader.class);
    }

    public BlockHeader get(BlockHeader header) {
        return (BlockHeader) repository.get(header.getHeight(), BlockHeader.class);
    }

    public void delete(long height) {
        repository.delete(height);
    }

    public void delete(BlockHeader header) {
        repository.delete(header.getHeight());
    }

    public boolean flush() {
        return false;
    }

}
