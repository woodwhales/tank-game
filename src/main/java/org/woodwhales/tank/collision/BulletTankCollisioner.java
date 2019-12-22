package org.woodwhales.tank.collision;

import org.woodwhales.tank.Bullet;
import org.woodwhales.tank.Explode;
import org.woodwhales.tank.GameObject;
import org.woodwhales.tank.Tank;

public class BulletTankCollisioner implements Collisioner {

	@Override
	public boolean collision(GameObject object1, GameObject object2) {
		if(object1 instanceof Bullet && object2 instanceof Tank) {
			Bullet bullet = (Bullet) object1;
			Tank tank = (Tank) object2;
			if(collideWith(bullet, tank)) {
				return false;
			}
			return true;
		}
		
		if(object1 instanceof Tank && object2 instanceof Bullet) {
			return collision(object2, object1);
		}
		
		return true;
	}
	
	/**
	 * 子弹做碰撞检测
	 * @param bullet
	 * @param tank
	 * @return 是否碰撞上了
	 */
	private boolean collideWith(Bullet bullet, Tank tank) {
		// 子弹和坦克类型一致，表示是自己的子弹或者同伴的
		if(bullet.getGroup() == tank.getGroup()) {
			return false;
		}
		
		int tankX = tank.getX();
		int tankY = tank.getY();
		
		if(bullet.getRectangle().intersects(tank.getRectangle())) {
			tank.die();
			bullet.die();
			
			int eX = tankX + Tank.WIDTH/2 - Explode.WIDTH/2;
			int eY = tankY + Tank.HEIGHT/2 - Explode.HEIGHT/2;
			bullet.getGameModel().add(new Explode(eX, eY, bullet.getGameModel()));
			return true;
		}
		
		return false;
	}

}
