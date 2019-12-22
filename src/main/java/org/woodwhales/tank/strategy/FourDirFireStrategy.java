package org.woodwhales.tank.strategy;

import org.woodwhales.tank.Audio;
import org.woodwhales.tank.Bullet;
import org.woodwhales.tank.Dir;
import org.woodwhales.tank.Group;
import org.woodwhales.tank.Tank;

public class FourDirFireStrategy implements FireStrategy {

	@Override
	public void fire(Tank tank) {
		int bX = tank.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
		int bY = tank.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
		
		Dir[] dirs = Dir.values();
		
		for (Dir dir : dirs) {
			new Bullet(bX, bY, dir, tank.getGroup(), tank.getGameModel());
		}
		
		if(tank.getGroup() == Group.GOOD) {
			new Thread(()-> new Audio("audio/tank_fire.wav").play()).start();
		}
	}

}
