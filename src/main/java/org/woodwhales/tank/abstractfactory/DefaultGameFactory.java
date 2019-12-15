package org.woodwhales.tank.abstractfactory;

import org.woodwhales.tank.Dir;
import org.woodwhales.tank.Explode;
import org.woodwhales.tank.Group;
import org.woodwhales.tank.TankFrame;

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
		// TODO Auto-generated method stub
		return null;
	}

}
