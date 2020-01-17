package org.woodwhales.tank.net.msg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import org.woodwhales.tank.Dir;
import org.woodwhales.tank.Tank;
import org.woodwhales.tank.TankFrame;
import org.woodwhales.tank.net.MsgType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class TankDirChangedMsg extends BaseMsg {
    private int x, y;
    private Dir dir;
    private UUID id;
    
    public TankDirChangedMsg(Tank tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
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
		if(TankFrame.INSTANCE.getMainTank().getId().equals(this.id)) {
			return;
		}

		Tank tank = TankFrame.INSTANCE.findByUIUID(this.id);
		
		if(Objects.nonNull(tank)) {
			tank.setMoving(true);
			tank.setX(this.x);
			tank.setY(this.y);
			tank.setDir(this.dir);
        }
		
	}

	@Override
	public void parse(byte[] bytes) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		
		try {
			this.x = dis.readInt();
			this.y = dis.readInt();
			this.dir = Dir.values()[dis.readInt()];
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
		return MsgType.TankDirChanged;
	}

}
