package org.matrixchain.db;

import com.google.common.primitives.Longs;
import org.junit.Test;
import org.matrixchain.core.BlockHeader;
import org.spongycastle.util.encoders.Hex;

import static org.junit.Assert.*;

public class BlockHeaderStoreTest {

    @Test
    public void header() {
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
}