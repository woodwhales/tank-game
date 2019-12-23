package org.woodwhales.tank.observer;

import org.woodwhales.tank.Tank;

public class FireEvent {

	private Tank tank;
	
	public Tank getSource() {
		return tank;
	}
	
	public FireEvent(Tank tank) {
		this.tank = tank;
	}
	
}
