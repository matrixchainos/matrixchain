package org.matrixchain;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 这个地方的 必须和服务端对应上。否则无法正常解码和编码 服务端与客户端需保持一致
        ByteBuf delimiter = Unpooled.copiedBuffer("\t".getBytes());
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(2048,delimiter));

        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        // 客户端的handler
        // 先调用handler在ChannnelActive方法中调用fireChannelActive会激活handler1
        pipeline.addLast("handler", new ClientHandler());
    }
}
