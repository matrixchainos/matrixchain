package org.matrixchain.facade;

import org.matrixchain.api.http.HttpApiServer;
import org.matrixchain.api.JsonRpcApiServer;
import org.matrixchain.net.discover.DiscoverListening;
import org.matrixchain.net.peer.PeerClient;
import org.matrixchain.net.peer.PeerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationImpl implements Application {

    private ServicesContainer container = new ServicesContainer();

    @Autowired
    private HttpApiServer httpApiServer;
    @Autowired
    private JsonRpcApiServer jsonRpcApiServer;
    @Autowired
    private DiscoverListening discoverListening;
    @Autowired
    private PeerServer peerServer;
    @Autowired
    private PeerClient peerClient;

    @Override
    public void init() {
        this.container.addService(jsonRpcApiServer);
        this.container.addService(httpApiServer);
        this.container.addService(discoverListening);
        this.container.addService(peerServer);
        this.container.addService(peerClient);

        this.container.initServices();
    }

    @Override
    public void startup() {
        this.container.startupServices();
    }

    @Override
    public void shutdown() {
        this.container.shutdownServices();
    }

}
