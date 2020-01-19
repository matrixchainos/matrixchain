package org.matrixchain.net.peer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class ChannelManager {
    private final static Logger logger = LoggerFactory.getLogger("channel");

    private final Map<String, Channel> activePeers = new ConcurrentHashMap<>();

    private List<Channel> newPeers = new CopyOnWriteArrayList<>();

    private ScheduledExecutorService mainWorker = Executors.newSingleThreadScheduledExecutor();

    private PeerServer peerServer;

    @Autowired
    public ChannelManager(final PeerServer peerServer){
        this.peerServer = peerServer;
    }

    public synchronized void add(Channel peer) {
        logger.debug("New peer in ChannelManager {}", peer);
        newPeers.add(peer);
    }

    public boolean isAcceptingNewPeers(){
        //
        return true;
//        return newPeers.size() < Math.max(config.maxActivePeers(), MAX_NEW_PEERS);
    }

    public void notifyDisconnect(Channel channel){

    }

    public List<Channel> getNewPeers(){
        return newPeers;
    }

    public void addActivePeers(Channel peer){
        activePeers.put(peer.getNodeId(), peer);
    }

}
