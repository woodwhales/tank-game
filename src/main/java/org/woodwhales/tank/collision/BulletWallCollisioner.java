package org.woodwhales.tank.collision;

import org.woodwhales.tank.Bullet;
import org.woodwhales.tank.GameObject;
import org.woodwhales.tank.Wall;

public class BulletWallCollisioner implements Collisioner {

	@Override
	public boolean collise(GameObject object1, GameObject object2) {
		if(object1 instanceof Bullet && object2 instanceof Wall) {
			Bullet bullet = (Bullet) object1;
			Wall wall = (Wall) object2;
			if(bullet.getRectangle().intersects(wall.getRectangle())) {
				bullet.die();
				return true;
			}
			return false;
		}
		
		if(object1 instanceof Wall && object2 instanceof Bullet) {
			return collise(object2, object1);
		}
		
		return true;
	}

}
