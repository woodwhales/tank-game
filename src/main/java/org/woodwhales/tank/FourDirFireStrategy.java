package org.woodwhales.tank;

import org.woodwhales.tank.abstractfactory.BaseTank;

public class FourDirFireStrategy implements FireStrategy {

	@Override
	public void fire(BaseTank tank) {
		int bX = tank.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
		int bY = tank.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
		
		Dir[] dirs = Dir.values();
		
		for (Dir dir : dirs) {
			tank.getFrame().gameFactory.createBullet(bX, bY, dir, tank.getGroup(), tank.getFrame());
		}
		
		if(tank.getGroup() == Group.GOOD) {
			new Thread(()-> new Audio("audio/tank_fire.wav").play()).start();
		}
	}

}
