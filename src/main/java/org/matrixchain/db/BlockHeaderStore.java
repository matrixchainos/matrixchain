package org.matrixchain.db;

import org.matrixchain.core.BlockHeader;

public class BlockHeaderStore {

    private Repository repository;

    public BlockHeaderStore() {
        repository = new RepositoryImpl("header");
    }

    public void put(String key, BlockHeader val) {
        repository.put(key, val);
    }

    public BlockHeader get(String key) {
        return (BlockHeader) repository.get(key, BlockHeader.class);
    }

    public void delete(String key) {
        repository.delete(key);
    }

    public boolean flush() {
        return false;
    }

    public static void main(String[] args) {
        BlockHeader blockHeader = new BlockHeader("0000000000ec7e29bf89a2b6fa71dd0e8185ac778e7a7e1fc1817a76bd7db5b4",
                "TCEo1hMAdaJrQmvnGTCcGT2LqrGU4N7Jqf",
                1001, 1576464924176L, 1,
                100000000, "support Constant", 12647813);

//        BlockHeaderStore store = new BlockHeaderStore();
//        store.put(blockHeader.getParentHash(), blockHeader);
//        BlockHeader blockHeader1 = store.get(blockHeader.getParentHash());
//        System.out.println(blockHeader1);
    }
}
