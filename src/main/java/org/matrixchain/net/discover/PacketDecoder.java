package org.matrixchain.net.discover;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.matrixchain.util.ByteUtil.toHexString;

public class PacketDecoder extends MessageToMessageDecoder<DatagramPacket> {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("discover");
    private final static ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
        System.out.println("=========================decode==============================");
        ByteBuf buf = packet.content();
        byte[] encoded = new byte[buf.readableBytes()];
        buf.readBytes(encoded);
        try {
            Message msg = (Message) mapper.readValue(encoded, Message.class);
            System.out.println("---msgmsg----: " + msg.toString());
            DiscoveryEvent event = new DiscoveryEvent(msg, packet.sender());
            out.add(event);
        } catch (Exception e) {
            throw new RuntimeException("Exception processing inbound message from " + ctx.channel().remoteAddress() + ": " + toHexString(encoded), e);
        }
    }
}
