package org.woodwhales.tank.abstractfactory.defaultskin;

import org.woodwhales.tank.Bullet;
import org.woodwhales.tank.Explode;
import org.woodwhales.tank.Tank;
import org.woodwhales.tank.TankFrame;
import org.woodwhales.tank.abstractfactory.BaseBullet;
import org.woodwhales.tank.abstractfactory.BaseExplode;
import org.woodwhales.tank.abstractfactory.BaseTank;
import org.woodwhales.tank.abstractfactory.GameFactory;
import org.woodwhales.tank.enums.Dir;
import org.woodwhales.tank.enums.Group;

public class DefaultGameFactory extends GameFactory {
	
	public DefaultGameFactory() {
		this.bulletWidth = Bullet.WIDTH;
		this.bulletHeight = Bullet.HEIGHT;
	}

	@Override
	public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame frame) {
		return new Tank(x, y, dir, group, frame);
	}

	@Override
	public BaseExplode createExplode(int x, int y, TankFrame frame) {
		return new Explode(x, y, frame);
	}

	@Override
	public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame frame) {
		return new Bullet(x, y, dir, group, frame);
	}

}
