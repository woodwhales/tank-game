package org.woodwhales.tank.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.woodwhales.tank.net.msg.MsgDecoder;
import org.woodwhales.tank.net.msg.MsgEncoder;
import org.woodwhales.tank.net.msg.TankStopMsg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class TankStopMsgCodecTest {

    @Test
    public void testTankStopMsgEncoder() {
    	UUID id = UUID.randomUUID();
    	int x = 40;
    	int y = 10;
    	
    	TankStopMsg tank = new TankStopMsg(x, y, id);
    	
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());
        
        channel.writeOutbound(tank);

        ByteBuf buf = (ByteBuf)channel.readOutbound();
        
        MsgType msgType = MsgType.values()[buf.readInt()];
        
        assertEquals(MsgType.TankStop, msgType);
        
        int length = buf.readInt();
        
        assertEquals(24, length);
        
        UUID uuid = new UUID(buf.readLong(), buf.readLong()); // 16 字节
        int res_x = buf.readInt(); // 4 字节
        int res_y = buf.readInt(); // 4 字节
        
        assertEquals(x, res_x);
        assertEquals(y, res_y);
        assertEquals(id, uuid);
    
        buf.release();
    }


    @Test
    public void testTankStopMsgDecoder() {
    	UUID id = UUID.randomUUID();
    	int x = 5;
    	int y = 10;
    	
    	TankStopMsg msg = new TankStopMsg(x, y, id);

        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgDecoder());
        
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankStop.ordinal());
        byte[] bytes = msg.toBytes();
        int length = bytes.length;
        
        buf.writeInt(length);
        buf.writeBytes(bytes);
        channel.writeInbound(buf.duplicate());

        TankStopMsg tankStopMsg = (TankStopMsg)channel.readInbound();
        
        assertEquals(x, tankStopMsg.getX());
        assertEquals(y, tankStopMsg.getY());
        assertEquals(id, tankStopMsg.getId());

    }
}
