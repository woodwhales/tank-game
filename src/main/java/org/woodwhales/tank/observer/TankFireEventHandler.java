package org.woodwhales.tank.observer;

import org.woodwhales.tank.Tank;

public class TankFireEventHandler implements FireEventObserver {

	@Override
	public void actionOnFire(FireEvent event) {
		Tank tank = event.getSource();
		tank.fire();
	}

}
