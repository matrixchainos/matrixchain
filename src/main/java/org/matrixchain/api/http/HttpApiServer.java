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
    private BlockHeaderApi blockHeaderApi;
    @Autowired
    private TransactionApi transactionApi;
    @Autowired
    private TransactionListApi transactionListApi;
    @Autowired
    private BlockApi blockApi;

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
            context.setContextPath("/api");
            this.server.setHandler(context);
            context.addServlet(new ServletHolder(chainParamsApi), "/chain_params");
            context.addServlet(new ServletHolder(transactionApi), "/transaction");
            context.addServlet(new ServletHolder(blockHeaderApi), "/block_header");
            context.addServlet(new ServletHolder(blockApi), "/block");
            context.addServlet(new ServletHolder(transactionListApi), "/transaction_list");

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
