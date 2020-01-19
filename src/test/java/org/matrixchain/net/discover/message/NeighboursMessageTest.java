package org.matrixchain.net.discover.message;

import org.junit.Test;
import org.matrixchain.crypto.ECKey;
import org.matrixchain.net.node.Node;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class NeighboursMessageTest {
    private final static ECKey ecKey = ECKey.fromPrivate(new BigInteger("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620", 16));
    private final static String url = "enode://6332792c4a00e3e4ee0926ed89e0d27ef985424d97b6a45bf0f23e51f0dcb5e66b875777506458aea7af6f9e4ffb69f43f3778ee73c81ed9d34c51c4b16b0b0f@52.232.243.152:30303";

    @Test
    public void create() {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new Node(url));

        Message message = NeighboursMessage.create(nodeList, ecKey);
        System.out.println(message.toString());

        byte[] pack = message.getPacket();
        Message message1 = Message.decode(pack);
        System.out.println(message1.toString());
    }
}