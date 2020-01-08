package org.woodwhales.tank.net;

public abstract class BaseTankStateMsg {
	
	public abstract void handle();
	
	public abstract byte[] toBytes();
	
	
}
