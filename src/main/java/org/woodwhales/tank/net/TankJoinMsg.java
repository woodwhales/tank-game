package org.woodwhales.tank.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import org.woodwhales.tank.Dir;
import org.woodwhales.tank.Group;
import org.woodwhales.tank.Tank;
import org.woodwhales.tank.TankFrame;

public class TankJoinMsg extends BaseMsg {
    public int x, y;
    public Dir dir;
    public boolean moving;
    public Group group;
    public UUID id;
    
    public TankJoinMsg(Tank tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
        this.moving = tank.isMoving();
        this.group = tank.getGroup();
        this.id = tank.getId();
    }
    
    @Override
    public byte[] toBytes() {
    	ByteArrayOutputStream byteArrayOutputStream = null;
    	DataOutputStream dataOutputStream = null;
    	byte[] bytes = null;
    	
    	try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			dataOutputStream = new DataOutputStream(byteArrayOutputStream);
			
			dataOutputStream.writeInt(x);
			dataOutputStream.writeInt(y);
			dataOutputStream.writeInt(dir.ordinal());
			dataOutputStream.writeBoolean(moving);
			dataOutputStream.writeInt(group.ordinal());
			dataOutputStream.writeLong(id.getMostSignificantBits());
			dataOutputStream.writeLong(id.getLeastSignificantBits());
			dataOutputStream.flush();
			bytes = byteArrayOutputStream.toByteArray();
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			if(Objects.nonNull(byteArrayOutputStream)) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(Objects.nonNull(dataOutputStream)) {
				try {
					dataOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
    	
    	return bytes;
    }
    
	public TankJoinMsg() {
		super();
	}

	public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.moving = moving;
		this.group = group;
		this.id = id;
	}

	@Override
	public String toString() {
		return "TankStateMsg [x=" + x + ", y=" + y + ", dir=" + dir + ", moving=" + moving + ", group=" + group
				+ ", id=" + id + "]";
	}

	@Override
	public void handle() {
		// 接收到的消息是client自己发送的消息
        // 或者接收到的client已经加入了 TankFrame的 敌人tank列表里
		if(TankFrame.INSTANCE.getMainTank().getId().equals(this.id)
        		|| Objects.nonNull(TankFrame.INSTANCE.findByUIUID(this.id))) {
        	return;
        }
        
        Tank tank = new Tank(this);
        TankFrame.INSTANCE.addTank(tank);
        Client.getInstance().send(this);
		
	}

}
