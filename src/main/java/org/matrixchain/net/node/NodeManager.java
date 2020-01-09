package org.matrixchain.net.node;

import org.matrixchain.crypto.ECKey;
import org.matrixchain.net.discover.DiscoveryEvent;
import org.matrixchain.net.discover.message.Message;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class NodeManager {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("discover");
    Consumer<DiscoveryEvent> messageSender;

    private List<Node> bootNodes;
    private Map<String, NodeHandler> nodeHandlerMap = new HashMap<>();

    final ECKey key;

    public NodeManager(){
        this.key = ECKey.fromPrivate(new BigInteger("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620", 16));
    }

    public void addMessageSender(Consumer<DiscoveryEvent> messageSender){
        this.messageSender = messageSender;
    }

    public void senderMessage(DiscoveryEvent discoveryEvent){
        messageSender.accept(discoveryEvent);
    }

    public void addNodeHandler(NodeHandler nodeHandler){
        String key = getKey(nodeHandler.getNode());
        if (!nodeHandlerMap.containsKey(key)){
            nodeHandlerMap.put(key, nodeHandler);
        }
    }

    private String getKey(Node n) {
        return getKey(new InetSocketAddress(n.getHost(), n.getPort()));
    }

    private String getKey(InetSocketAddress address) {
        InetAddress addr = address.getAddress();
        // addr == null if the hostname can't be resolved
        return (addr == null ? address.getHostString() : addr.getHostAddress()) + ":" + address.getPort();
    }

    public void handleInbound(DiscoveryEvent discoveryEvent) {
        Message message = discoveryEvent.getMessage();
        InetSocketAddress address = discoveryEvent.getAddress();
        Node node = new Node(message.getNodeId(), address.getHostName(), address.getPort());

        NodeHandler handler = new NodeHandler(node);

        byte type = message.getType();
        switch (type){
            case 1:
                handler.handlerPing();
                break;
            case 2:
                handler.handlerPing();
                break;
            case 3:
                handler.handlerPing();
                break;
        }
    }
}
