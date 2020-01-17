package org.woodwhales.tank.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.woodwhales.tank.Dir;
import org.woodwhales.tank.net.msg.MsgDecoder;
import org.woodwhales.tank.net.msg.MsgEncoder;
import org.woodwhales.tank.net.msg.TankStartMovingMsg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class TankStartMovingMsgCodecTest {
	
	private MsgType msgType = MsgType.TankStartMoving;

    @Test
    public void testTankStartMovingMsgEncoder() {
    	UUID id = UUID.randomUUID();
    	int x = 20;
    	int y = 10;
    	Dir dir = Dir.LEFT;
    	
    	TankStartMovingMsg msg = new TankStartMovingMsg(id, x, y, dir);
    			
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());
        
        channel.writeOutbound(msg);

        ByteBuf buf = (ByteBuf)channel.readOutbound();
        
        MsgType msgType = MsgType.values()[buf.readInt()];
        
        assertEquals(msgType, msgType);
        
        int length = buf.readInt();
        
        assertEquals(msg.toBytes().length, length);
        
        UUID uuid = new UUID(buf.readLong(), buf.readLong()); // 16 字节
        int res_x = buf.readInt(); // 4 字节
        int res_y = buf.readInt(); // 4 字节
        Dir res_dir = Dir.values()[buf.readInt()]; // 4 字节
        
        assertEquals(x, res_x);
        assertEquals(y, res_y);
        assertEquals(dir, res_dir);
        assertEquals(id, uuid);
    
        buf.release();
    }


    @Test
    public void testTankStartMovingMsgDecoder() {
    	UUID id = UUID.randomUUID();
    	int x = 5;
    	int y = 10;
    	Dir dir = Dir.DOWN;
    	
    	TankStartMovingMsg msg = new TankStartMovingMsg(id, x, y, dir);

        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgDecoder());
        
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(msgType.ordinal());
        byte[] bytes = msg.toBytes();
        int length = bytes.length;
        
        buf.writeInt(length);
        buf.writeBytes(bytes);
        channel.writeInbound(buf.duplicate());

        TankStartMovingMsg tankStartMovingMsg = (TankStartMovingMsg)channel.readInbound();
        
        assertEquals(x, tankStartMovingMsg.getX());
        assertEquals(y, tankStartMovingMsg.getY());
        assertEquals(dir, tankStartMovingMsg.getDir());
        assertEquals(id, tankStartMovingMsg.getId());

    }
}
