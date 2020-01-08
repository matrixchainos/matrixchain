package org.matrixchain.net.node;

import org.matrixchain.net.discover.DiscoveryEvent;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Consumer;

@Component
public class NodeManager {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("discover");
    Consumer<DiscoveryEvent> messageSender;

    private List<Node> bootNodes;

    public NodeManager(){

    }

    public void addMessageSender(Consumer<DiscoveryEvent> messageSender){
        this.messageSender = messageSender;
    }

    public void senderMessage(DiscoveryEvent discoveryEvent){
        messageSender.accept(discoveryEvent);
    }

    public void handleInbound(DiscoveryEvent discoveryEvent) {
        System.out.println(discoveryEvent.getMessage().toString());
    }
}
