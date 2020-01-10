package org.woodwhales.tank.net.msg;

import org.woodwhales.tank.net.MsgType;

public abstract class BaseMsg {
	
	public abstract void handle();
	
	public abstract byte[] toBytes();
	
	public abstract void parse(byte[] bytes);
	
	public abstract MsgType getMsgType();
	
}
