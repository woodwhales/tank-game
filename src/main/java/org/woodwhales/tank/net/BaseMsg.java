package org.woodwhales.tank.net;

public abstract class BaseMsg {
	
	public abstract void handle();
	
	public abstract byte[] toBytes();
	
	public abstract void parse(byte[] bytes);
	
	public abstract MsgType getMsgType();
	
}
