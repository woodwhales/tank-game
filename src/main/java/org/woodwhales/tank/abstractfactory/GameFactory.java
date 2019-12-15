package org.woodwhales.tank.abstractfactory;

import org.woodwhales.tank.Dir;
import org.woodwhales.tank.Group;
import org.woodwhales.tank.TankFrame;

import lombok.Getter;

@Getter
public abstract class GameFactory {
	
	protected int bulletWidth;
	
	protected int bulletHeight;
	
	public abstract BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame frame);

	public abstract BaseExplode createExplode(int x, int y, TankFrame frame);
	
	public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame frame);
	
}
