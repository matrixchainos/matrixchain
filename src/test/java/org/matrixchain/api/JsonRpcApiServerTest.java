package org.matrixchain.api;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonRpcApiServerTest {



    @Test
    public void init() {
        JsonRpcApiServer server = new JsonRpcApiServer();
        server.init();
        server.start();
    }

    @Test
    public void start() {

    }

    @Test
    public void stop() {

    }
}