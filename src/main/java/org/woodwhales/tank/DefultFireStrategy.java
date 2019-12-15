package org.woodwhales.tank;

import org.woodwhales.tank.abstractfactory.BaseTank;

public class DefultFireStrategy implements FireStrategy {
	
	@Override
	public void fire(BaseTank tank, int positionX, int positionY) {
		int bX = tank.getX() + positionX;
		int bY = tank.getY() + positionY;
		
		tank.getFrame().gameFactory.createBullet(bX, bY, tank.getDir(), tank.getGroup(), tank.getFrame());
		
		if (tank.getGroup() == Group.GOOD) {
			new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
		}
	}

}
