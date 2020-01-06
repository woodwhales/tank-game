package org.woodwhales.tank.net;

import java.util.List;
import java.util.UUID;

import org.woodwhales.tank.Dir;
import org.woodwhales.tank.Group;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TankStateMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println(in.readableBytes());

        if(in.readableBytes() < 33) {
            return;
        }

        TankStateMsg tankStateMsg = new TankStateMsg();
        
        // in.markReaderIndex();
        tankStateMsg.x = in.readInt(); // 4 字节
        tankStateMsg.y = in.readInt(); // 4 字节
        tankStateMsg.dir = Dir.values()[in.readInt()]; // 4 字节
        tankStateMsg.moving = in.readBoolean(); // 1 字节
        tankStateMsg.group = Group.values()[in.readInt()]; // 4 字节
        tankStateMsg.id = new UUID(in.readLong(), in.readLong()); // 16 字节
        
        out.add(tankStateMsg);
    }
}
