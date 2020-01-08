package org.matrixchain.api.http;

import org.matrixchain.facade.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpApiServer implements Server {

    private int port;

    private org.eclipse.jetty.server.Server server;

    @Autowired
    private ChainParamsApi chainParamsApi;
    @Autowired
    private BlockHeaderByHeightApi blockHeaderByHeightApi;
    @Autowired
    private TransactionApi transactionApi;
    @Autowired
    private TransactionsByBlockHeightApi transactionsByBlockHeightApi;
    @Autowired
    private TransactionsByBlockHashApi transactionsByBlockHashApi;
    @Autowired
    private BlockByHashApi blockByHashApi;
    @Autowired
    private BlockByHeightApi blockByHeightApi;

    @Override
    public void init() {
        this.port = 5050;
        System.out.println("init http api service.");
    }

    @Override
    public void start() {
        try {
            this.server = new org.eclipse.jetty.server.Server(this.port);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/api/v1");
            this.server.setHandler(context);
            context.addServlet(new ServletHolder(chainParamsApi), "/chain_params");
            context.addServlet(new ServletHolder(blockHeaderByHeightApi), "/blockheader_by_height");
            context.addServlet(new ServletHolder(transactionApi), "/transaction");
            context.addServlet(new ServletHolder(blockByHashApi), "/block_by_hash");
            context.addServlet(new ServletHolder(blockByHeightApi), "/block_by_height");
            context.addServlet(new ServletHolder(transactionsByBlockHashApi), "/transactions_by_block_hash");
            context.addServlet(new ServletHolder(transactionsByBlockHeightApi), "/transactions_by_block_height");

            this.server.start();
//                this.server.join(); // thread stop
            System.out.println("started http api service.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            if (this.server != null) {
                this.server.stop();
                System.out.println("stopped http api service.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
