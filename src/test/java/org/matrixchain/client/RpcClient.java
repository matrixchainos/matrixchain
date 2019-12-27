package org.matrixchain.client;

import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import org.matrixchain.api.RpcApi;
import org.matrixchain.core.Block;
import org.matrixchain.core.BlockHeader;
import org.matrixchain.core.Transaction;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class RpcClient {

    private InetAddress bindAddress;
    private Socket socket;

    public static void main(String[] args) throws Throwable {

        RpcApi rpcApi = new RpcClient().getRpcApi();

        BlockHeader blockHeader = rpcApi.getBlockHeader();
        System.out.println("blockHeader--------------------: " + blockHeader.toString());

        Transaction transaction = rpcApi.getTransaction();
        System.out.println("transaction--------------------: " + transaction.toString());

        List<Transaction> transactionList = rpcApi.getTransactionList();
        System.out.println("transactionList--------------------: " + transactionList.toString());

        Block block = rpcApi.getBlock();
        System.out.println("block--------------------: " + JSONObject.toJSON(block));
    }

    public RpcApi getRpcApi() {
        RpcApi rpcApi = null;
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
        return rpcApi;
    }

    public void shutdown() {
        if (socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
