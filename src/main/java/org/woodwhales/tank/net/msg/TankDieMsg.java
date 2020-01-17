package org.woodwhales.tank.net.msg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

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
public class TankDieMsg extends BaseMsg {

	private UUID id;
	
	public TankDieMsg(Tank tank) {
		this.id = tank.getId();
	}
	
	@Override
	public void handle() {
		Tank tank = TankFrame.INSTANCE.findByUUID(this.id);
		if (Objects.nonNull(tank)) {
			tank.die();
			TankFrame.INSTANCE.removeTank(tank);
		}
	}
	
	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream byteArrayOutputStream = null;
    	DataOutputStream dataOutputStream = null;
    	byte[] bytes = null;
    	
    	try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			dataOutputStream = new DataOutputStream(byteArrayOutputStream);
			
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
	public void parse(byte[] bytes) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		
		try {
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
		return MsgType.TankDie;
	}
}
