package org.woodwhales.tank.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.woodwhales.tank.Dir;
import org.woodwhales.tank.Group;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class TankMsgCodecTest {

    @Test
    public void testTankStateMsgEncoder() {
    	UUID id = UUID.randomUUID();
    	TankStateMsg tank = new TankStateMsg(10, 10, Dir.DOWN, false, Group.GOOD, id);
    	
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new TankStateMsgEncoder());
        
        channel.writeOutbound(tank);

        ByteBuf buf = (ByteBuf)channel.readOutbound();
        int x = buf.readInt(); // 4 字节
        int y = buf.readInt(); // 4 字节
        Dir dir = Dir.values()[buf.readInt()]; // 4 字节
        boolean moving = buf.readBoolean(); // 1 字节
        Group group = Group.values()[buf.readInt()]; // 4 字节
        UUID uuid = new UUID(buf.readLong(), buf.readLong()); // 16 字节
        
        assertEquals(10, x);
        assertEquals(10, y);
        assertEquals(Dir.DOWN, dir);
        assertEquals(false, moving);
        assertEquals(Group.GOOD, group);
        assertEquals(id, uuid);
    
        buf.release();
    }


    @Test
    public void testTankStateMsgDecoder() {
    	UUID id = UUID.randomUUID();
    	TankStateMsg msg = new TankStateMsg(5, 10, Dir.UP, false, Group.BAD, id);

        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new TankStateMsgDecoder());
        
        
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(msg.toBytes());
        channel.writeInbound(buf.duplicate());

        TankStateMsg tankStateMsg = (TankStateMsg)channel.readInbound();
        
        assertEquals(5, tankStateMsg.x);
        assertEquals(10, tankStateMsg.y);
        assertEquals(Dir.UP, tankStateMsg.dir);
        assertEquals(false, tankStateMsg.moving);
        assertEquals(Group.BAD, tankStateMsg.group);
        assertEquals(id, tankStateMsg.id);

    }
}
