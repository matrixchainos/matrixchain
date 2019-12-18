package org.matrixchain.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ServicesContainer {

    private final static Logger logger = LoggerFactory.getLogger("ServicesContainer");

    private ArrayList<Server> servers;

    public ServicesContainer() {
        this.servers = new ArrayList<>();
    }

    public void initServices() {
        for (Server server : this.servers) {
            server.init();
            logger.info("init Server, ", server.getClass().getSimpleName());
        }
    }

    public void startupServices() {
        for (Server server : this.servers) {
            server.start();
            logger.info("start Server, ", server.getClass().getSimpleName());
        }
    }

    public void shutdownServices() {
        for (Server server : this.servers) {
            server.stop();
            logger.info("Shutdown Server, ", server.getClass().getSimpleName());
        }
    }

    public void addService(Server server) {
        logger.info("Add Server, ", server.getClass().getSimpleName());
        this.servers.add(server);
    }

}
