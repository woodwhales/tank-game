package org.woodwhales.tank.net.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgEncoder extends MessageToByteEncoder<BaseMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, BaseMsg msg, ByteBuf buf) throws Exception {
    	buf.writeInt(msg.getMsgType().ordinal());
    	byte[] bytes = msg.toBytes();
    	buf.writeInt(bytes.length);
    	buf.writeBytes(bytes);
    }
}
