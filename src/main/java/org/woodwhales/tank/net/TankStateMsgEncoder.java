package org.woodwhales.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TankStateMsgEncoder extends MessageToByteEncoder<TankStateMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, TankStateMsg msg, ByteBuf buf) throws Exception {
        buf.writeBytes(msg.toBytes());
    }
}
