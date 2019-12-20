package org.matrixchain.net.discover;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DiscoveryExecutor {

    ScheduledExecutorService discover = Executors.newSingleThreadScheduledExecutor();

    public DiscoveryExecutor(){}

    public void rurn(){
        discover.scheduleWithFixedDelay(new DiscoverTask(),
                1, KademliaOptions.DISCOVER_CYCLE, TimeUnit.SECONDS);
    }
}
