package org.matrixchain.net.peer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.matrixchain.net.peer.message.Frame;
import org.matrixchain.net.peer.message.Message;

import java.util.List;

public class MessageCodec extends MessageToMessageCodec<Frame, Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Frame msg, List<Object> out) throws Exception {

    }
}
