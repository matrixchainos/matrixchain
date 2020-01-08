package org.matrixchain.db;

import com.google.common.primitives.Longs;
import org.junit.Before;
import org.junit.Test;
import org.matrixchain.util.AccountUtil;
import org.matrixchain.core.BlockHeader;
import org.spongycastle.util.encoders.Hex;

public class BlockHeaderStoreTest {
    private AccountUtil accountUtil;

    @Before
    public void init(){
        accountUtil = AccountUtil.create("8b71752f9a06a5a3249d7900c91b833acf5d51b68ed3a2ce23a0cb142b72393b");
    }

    @Test
    public void header() {

        // 0x4d9d3cb7c9a6a83b376958d49086fd90966aa485d2753f004e34e4810428d631
        long height = 1223372036854775807L;
        System.out.println(Hex.toHexString(Longs.toByteArray(height)));

        BlockHeader blockHeader = new BlockHeader(1,"0000000000ec7e29bf89a2b6fa71dd0e8185ac778e7a7e1fc1817a76bd7db5b4",
                "0000000000ec7e29bf89a2b6fa71dd0e8185ac778e7a7e1fc1817a76bd7db5b4",
                "0xd92230002a341bdcc1088a976d608c6e35834993",
                1747754727973896576L, 1576464924000L,
                100000000, "support Constant", 12647813);
        accountUtil.signBlockHeader(blockHeader);

        BlockHeaderStore store = new BlockHeaderStore();
        store.put(blockHeader);
        BlockHeader blockHeader1 = store.get(blockHeader.getHeight());
        System.out.println(blockHeader1);
    }

    @Test
    public void put() {
    }

    @Test
    public void get() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void flush() {
    }
}