package org.woodwhales.tank.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.woodwhales.tank.Dir;
import org.woodwhales.tank.Group;
import org.woodwhales.tank.net.msg.BulletNewMsg;
import org.woodwhales.tank.net.msg.MsgDecoder;
import org.woodwhales.tank.net.msg.MsgEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class BulletNewMsgCodecTest {

	private MsgType msgType = MsgType.BulletNew;
	
    @Test
    public void testTankJoinMsgEncoder() {
    	UUID playerID = UUID.randomUUID();
    	UUID id = UUID.randomUUID();
    	int x = 20;
    	int y = 10;
    	Dir dir = Dir.DOWN; 
    	Group group = Group.GOOD;
    	
    	BulletNewMsg bullet = new BulletNewMsg(playerID, id, dir, x, y, group);
    	
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());
        
        channel.writeOutbound(bullet);

        ByteBuf buf = (ByteBuf)channel.readOutbound();
        
        MsgType msgType = MsgType.values()[buf.readInt()];
        
        assertEquals(msgType, msgType);
        
        int length = buf.readInt();
        
        assertEquals(48, length);
        
        UUID player_uuid = new UUID(buf.readLong(), buf.readLong()); // 16 字节
        int res_x = buf.readInt(); // 4 字节
        int res_y = buf.readInt(); // 4 字节
        Dir res_dir = Dir.values()[buf.readInt()]; // 4 字节
        Group res_group = Group.values()[buf.readInt()]; // 4 字节
        UUID uuid = new UUID(buf.readLong(), buf.readLong()); // 16 字节
        
        assertEquals(x, res_x);
        assertEquals(y, res_y);
        assertEquals(dir, res_dir);
        assertEquals(group, res_group);
        assertEquals(id, uuid);
        assertEquals(playerID, player_uuid);
    
        buf.release();
    }


    @Test
    public void testTankJoinMsgDecoder() {
    	UUID playerID = UUID.randomUUID();
    	UUID id = UUID.randomUUID();
    	int x = 40;
    	int y = 30;
    	Dir dir = Dir.UP; 
    	Group group = Group.BAD;
    	
    	BulletNewMsg bullet = new BulletNewMsg(playerID, id, dir, x, y, group);
    			
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgDecoder());
        
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(msgType.ordinal());
        byte[] bytes = bullet.toBytes();
        int length = bytes.length;
        
        buf.writeInt(length);
        buf.writeBytes(bytes);
        channel.writeInbound(buf.duplicate());

        BulletNewMsg bulletNewMsg = (BulletNewMsg)channel.readInbound();
        
        assertEquals(x, bulletNewMsg.getX());
        assertEquals(y, bulletNewMsg.getY());
        assertEquals(dir, bulletNewMsg.getDir());
        assertEquals(group, bulletNewMsg.getGroup());
        assertEquals(id, bulletNewMsg.getId());
        assertEquals(playerID, bulletNewMsg.getPlayerID());

    }
}
