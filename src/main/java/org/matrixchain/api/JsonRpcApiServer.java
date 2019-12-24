package org.matrixchain.api;

import com.googlecode.jsonrpc4j.JsonRpcBasicServer;
import com.googlecode.jsonrpc4j.StreamServer;
import org.matrixchain.facade.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

@Component
public class JsonRpcApiServer implements Server {

    private JsonRpcBasicServer jsonRpcServer = null;
    private StreamServer streamServer = null;
    private InetAddress bindAddress = null;
    private ServerSocket serverSocket = null;
    private int maxThreads;
    private int port;
    private int backlog;
    private String address;

    @Autowired
    private RpcApi rpcApi;

    @Override
    public void init() {
        this.maxThreads = 1;
        this.port = 5060;
        this.backlog = 1;
        this.address = "127.0.0.1";

        System.out.println("init rpc api server.");
    }

    @Override
    public void start() {

        this.jsonRpcServer = new JsonRpcBasicServer(rpcApi, RpcApi.class);

        try {
            bindAddress = InetAddress.getByName(address);
            serverSocket = new ServerSocket(port, backlog, bindAddress);

            this.streamServer = new StreamServer(this.jsonRpcServer, maxThreads, serverSocket);

            // start it, this method doesn't block
            this.streamServer.start();
            System.out.println("started rpc api server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (this.streamServer != null) {
            try {
                this.streamServer.stop();
                System.out.println("stopped rpc api server.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
