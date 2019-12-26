package org.matrixchain.util;

import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import org.matrixchain.api.RpcApi;
import org.matrixchain.core.Block;
import org.matrixchain.core.BlockHeader;
import org.matrixchain.core.Transaction;

import java.net.InetAddress;
import java.net.Socket;

public class RpcClient {

    public static void main(String[] args) throws Throwable {

        RpcClient client = new RpcClient();
        client.getBlock();
    }

    public void getBlock() throws Throwable {
        InetAddress bindAddress = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(bindAddress, 5060);
        RpcApi service = ProxyUtil.createClientProxy(
                this.getClass().getClassLoader(),
                RpcApi.class,
                new JsonRpcClient(),
                socket);

//        BlockHeader blockHeader = service.getBlockHeader();
//        System.out.println("blockHeader--------------------: " + blockHeader.toString());
        Transaction transaction = service.getTransaction();
        System.out.println("transaction--------------------: " + transaction.toString());
//        List<Transaction> transactionList = service.getTransactionList();
//        System.out.println("transactionList--------------------: " + transactionList.toString());
//        Block block = service.getBlock();
//        System.out.println("block--------------------: " + block.toString());
//        socket.close();
    }
}
