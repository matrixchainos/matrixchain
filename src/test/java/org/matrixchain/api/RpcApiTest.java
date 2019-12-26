package org.matrixchain.api;

import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import org.junit.*;
import org.matrixchain.core.Block;
import org.matrixchain.core.BlockHeader;
import org.matrixchain.core.Transaction;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class RpcApiTest {

    private InetAddress bindAddress;
    private Socket socket;
    RpcApi rpcApi;

    static {

    }

    @Before
    public void rpcApi() {
        try {
            bindAddress = InetAddress.getByName("127.0.0.1");
            socket = new Socket(bindAddress, 5060);

            rpcApi = ProxyUtil.createClientProxy(
                    this.getClass().getClassLoader(),
                    RpcApi.class,
                    new JsonRpcClient(),
                    socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBlockHeader() {
        BlockHeader blockHeader = rpcApi.getBlockHeader();
        System.out.println(blockHeader);
    }

    @Test
    public void getTransaction() {
        Transaction transaction = rpcApi.getTransaction();
        System.out.println(transaction);
    }

    @Test
    public void getTransactionList() {
    }

    @Test
    public void getBlock() {
        Block block = rpcApi.getBlock();
        System.out.println(block);
    }

    @After
    public  void shutdown() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}