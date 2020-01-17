package org.woodwhales.tank.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.woodwhales.tank.Dir;
import org.woodwhales.tank.Group;
import org.woodwhales.tank.net.msg.MsgDecoder;
import org.woodwhales.tank.net.msg.MsgEncoder;
import org.woodwhales.tank.net.msg.TankJoinMsg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class TankJoinMsgCodecTest {

	private MsgType msgType = MsgType.TankJoin;
	
    @Test
    public void testTankJoinMsgEncoder() {
    	UUID id = UUID.randomUUID();
    	int x = 20;
    	int y = 10;
    	Dir dir = Dir.LEFT;
    	boolean moving = false;
    	Group group = Group.GOOD;
    	
    	TankJoinMsg tank = new TankJoinMsg(x, y, dir, moving, group, id);
    	
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());
        
        channel.writeOutbound(tank);

        ByteBuf buf = (ByteBuf)channel.readOutbound();
        
        MsgType msgType = MsgType.values()[buf.readInt()];
        
        assertEquals(msgType, msgType);
        
        int length = buf.readInt();
        
        assertEquals(33, length);
        
        int res_x = buf.readInt(); // 4 字节
        int res_y = buf.readInt(); // 4 字节
        Dir res_dir = Dir.values()[buf.readInt()]; // 4 字节
        boolean res_moving = buf.readBoolean(); // 1 字节
        Group res_group = Group.values()[buf.readInt()]; // 4 字节
        UUID uuid = new UUID(buf.readLong(), buf.readLong()); // 16 字节
        
        assertEquals(x, res_x);
        assertEquals(y, res_y);
        assertEquals(dir, res_dir);
        assertEquals(moving, res_moving);
        assertEquals(group, res_group);
        assertEquals(id, uuid);
    
        buf.release();
    }


    @Test
    public void testTankJoinMsgDecoder() {
    	UUID id = UUID.randomUUID();
    	int x = 5;
    	int y = 10;
    	Dir dir = Dir.DOWN;
    	boolean moving = false;
    	Group group = Group.BAD;
    	
    	TankJoinMsg msg = new TankJoinMsg(x, y, dir, moving, group, id);

        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgDecoder());
        
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(msgType.ordinal());
        byte[] bytes = msg.toBytes();
        int length = bytes.length;
        
        buf.writeInt(length);
        buf.writeBytes(bytes);
        channel.writeInbound(buf.duplicate());

        TankJoinMsg tankJoinMsg = (TankJoinMsg)channel.readInbound();
        
        assertEquals(x, tankJoinMsg.getX());
        assertEquals(y, tankJoinMsg.getY());
        assertEquals(dir, tankJoinMsg.getDir());
        assertEquals(moving, tankJoinMsg.isMoving());
        assertEquals(group, tankJoinMsg.getGroup());
        assertEquals(id, tankJoinMsg.getId());

    }
}
