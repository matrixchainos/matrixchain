package org.matrixchain.net.discover;

import org.matrixchain.net.node.NodeManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DiscoveryExecutor {

    ScheduledExecutorService discover = Executors.newSingleThreadScheduledExecutor();

    NodeManager nodeManager;

    public DiscoveryExecutor(NodeManager nodeManager) {
        this.nodeManager = nodeManager;
    }

    public void run() {
        discover.scheduleWithFixedDelay(new DiscoverTask(nodeManager),
                1, KademliaOptions.DISCOVER_CYCLE, TimeUnit.SECONDS);
    }
}
