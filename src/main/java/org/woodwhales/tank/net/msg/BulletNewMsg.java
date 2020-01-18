package org.woodwhales.tank.net.msg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import org.woodwhales.tank.Bullet;
import org.woodwhales.tank.Dir;
import org.woodwhales.tank.Group;
import org.woodwhales.tank.TankFrame;
import org.woodwhales.tank.net.MsgType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BulletNewMsg extends BaseMsg {
	
	private UUID playerID;
	private UUID id;
	private Dir dir;
	private int x, y;
	private Group group;
	
	public BulletNewMsg(Bullet bullet) {
		this.playerID = TankFrame.INSTANCE.getMainTank().getId();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.dir = bullet.getDir();
        this.group = bullet.getGroup();
        this.id = bullet.getId();
    }
	
	@Override
    public byte[] toBytes() {
    	ByteArrayOutputStream byteArrayOutputStream = null;
    	DataOutputStream dataOutputStream = null;
    	byte[] bytes = null;
    	
    	try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			dataOutputStream = new DataOutputStream(byteArrayOutputStream);
			
			dataOutputStream.writeLong(playerID.getMostSignificantBits());
			dataOutputStream.writeLong(playerID.getLeastSignificantBits());
			dataOutputStream.writeInt(x);
			dataOutputStream.writeInt(y);
			dataOutputStream.writeInt(dir.ordinal());
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
    
	@Override
	public void handle() {
		if(this.playerID.equals(TankFrame.INSTANCE.getMainTank().getId())) {
			return;
		}
		
		Bullet bullet = new Bullet(this.playerID, x, y, dir, group, TankFrame.INSTANCE);
		bullet.setId(id);
		TankFrame.INSTANCE.addBullet(bullet);
	}

	@Override
	public void parse(byte[] bytes) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		
		try {
			this.playerID = new UUID(dis.readLong(), dis.readLong());
			this.x = dis.readInt();
			this.y = dis.readInt();
			this.dir = Dir.values()[dis.readInt()];
			this.group = Group.values()[dis.readInt()];
			this.id = new UUID(dis.readLong(), dis.readLong());
		} catch (IOException exception) {
			exception.printStackTrace();
		} finally {
			try {
				dis.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		
	}

	@Override
	public MsgType getMsgType() {
		return MsgType.BulletNew;
	}
}
