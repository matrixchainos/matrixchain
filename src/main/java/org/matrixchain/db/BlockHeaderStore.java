package org.matrixchain.db;

import com.google.common.primitives.Longs;
import org.matrixchain.core.BlockHeader;
import org.spongycastle.util.encoders.Hex;

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

    public static void main(String[] args) {

        long height = 1223372036854775807L;
        System.out.println(Hex.toHexString(Longs.toByteArray(height)));
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
