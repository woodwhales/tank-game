package org.woodwhales.tank.abstractfactory.defaultskin;

import org.woodwhales.tank.Bullet;
import org.woodwhales.tank.Dir;
import org.woodwhales.tank.Explode;
import org.woodwhales.tank.Group;
import org.woodwhales.tank.TankFrame;
import org.woodwhales.tank.abstractfactory.BaseBullet;
import org.woodwhales.tank.abstractfactory.BaseExplode;
import org.woodwhales.tank.abstractfactory.BaseTank;
import org.woodwhales.tank.abstractfactory.GameFactory;

public class DefaultGameFactory extends GameFactory {

	@Override
	public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame frame) {
		// TODO Auto-generated method stub
		return null;
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
