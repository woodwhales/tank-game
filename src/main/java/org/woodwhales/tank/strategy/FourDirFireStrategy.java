package org.woodwhales.tank.strategy;

import org.woodwhales.tank.abstractfactory.BaseTank;
import org.woodwhales.tank.enums.Dir;
import org.woodwhales.tank.enums.Group;
import org.woodwhales.tank.utils.Audio;

public class FourDirFireStrategy implements FireStrategy {

	@Override
	public void fire(BaseTank tank, int positionX, int positionY) {
		int bX = tank.getX() + positionX;
		int bY = tank.getY() + positionY;
		
		Dir[] dirs = Dir.values();
		
		for (Dir dir : dirs) {
			tank.getFrame().gameFactory.createBullet(bX, bY, dir, tank.getGroup(), tank.getFrame());
		}
		
		if(tank.getGroup() == Group.GOOD) {
			new Thread(()-> new Audio("audio/tank_fire.wav").play()).start();
		}
	}

}
