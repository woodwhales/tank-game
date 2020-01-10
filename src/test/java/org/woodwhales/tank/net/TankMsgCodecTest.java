package org.woodwhales.tank.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.woodwhales.tank.Dir;
import org.woodwhales.tank.Group;
import org.woodwhales.tank.net.msg.TankJoinMsg;
import org.woodwhales.tank.net.msg.MsgDecoder;
import org.woodwhales.tank.net.msg.MsgEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class TankMsgCodecTest {

    @Test
    public void testTankJoinMsgEncoder() {
    	UUID id = UUID.randomUUID();
    	TankJoinMsg tank = new TankJoinMsg(10, 10, Dir.DOWN, false, Group.GOOD, id);
    	
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());
        
        channel.writeOutbound(tank);

        ByteBuf buf = (ByteBuf)channel.readOutbound();
        
        MsgType msgType = MsgType.values()[buf.readInt()];
        
        assertEquals(MsgType.TankJoin, msgType);
        
        int length = buf.readInt();
        
        assertEquals(33, length);
        
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
    public void testTankJoinMsgDecoder() {
    	UUID id = UUID.randomUUID();
    	TankJoinMsg msg = new TankJoinMsg(5, 10, Dir.UP, false, Group.BAD, id);

        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgDecoder());
        
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankJoin.ordinal());
        byte[] bytes = msg.toBytes();
        int length = bytes.length;
        
        buf.writeInt(length);
        buf.writeBytes(bytes);
        channel.writeInbound(buf.duplicate());

        TankJoinMsg tankStateMsg = (TankJoinMsg)channel.readInbound();
        
        assertEquals(5, tankStateMsg.x);
        assertEquals(10, tankStateMsg.y);
        assertEquals(Dir.UP, tankStateMsg.dir);
        assertEquals(false, tankStateMsg.moving);
        assertEquals(Group.BAD, tankStateMsg.group);
        assertEquals(id, tankStateMsg.id);

    }
}
