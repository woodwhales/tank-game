package org.woodwhales.tank.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.woodwhales.tank.net.msg.MsgDecoder;
import org.woodwhales.tank.net.msg.MsgEncoder;
import org.woodwhales.tank.net.msg.TankDieMsg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class TankDieMsgCodecTest {

	private MsgType msgType = MsgType.TankDie;
	
    @Test
    public void testTankDieMsgEncoder() {
    	UUID bulletId = UUID.randomUUID();
    	UUID id = UUID.randomUUID();
    	
    	TankDieMsg tank = new TankDieMsg(bulletId, id);
    	
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());
        
        channel.writeOutbound(tank);

        ByteBuf buf = (ByteBuf)channel.readOutbound();
        
        MsgType msgType = MsgType.values()[buf.readInt()];
        
        assertEquals(msgType, msgType);
        
        int length = buf.readInt();
        
        assertEquals(32, length);
        
        UUID bullet_uuid = new UUID(buf.readLong(), buf.readLong()); // 16 字节
        UUID uuid = new UUID(buf.readLong(), buf.readLong()); // 16 字节
        
        assertEquals(bulletId, bullet_uuid);
        assertEquals(id, uuid);
    
        buf.release();
    }


    @Test
    public void testTankDieMsgDecoder() {
    	UUID bulletId = UUID.randomUUID();
    	UUID id = UUID.randomUUID();
    	
    	TankDieMsg msg = new TankDieMsg(bulletId, id);

        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgDecoder());
        
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(msgType.ordinal());
        byte[] bytes = msg.toBytes();
        int length = bytes.length;
        
        buf.writeInt(length);
        buf.writeBytes(bytes);
        channel.writeInbound(buf.duplicate());

        TankDieMsg tankDieMsg = (TankDieMsg)channel.readInbound();
        
        assertEquals(id, tankDieMsg.getId());
        assertEquals(bulletId, tankDieMsg.getBulletId());

    }
}
