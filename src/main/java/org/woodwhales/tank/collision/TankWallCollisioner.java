package org.woodwhales.tank.collision;

import org.woodwhales.tank.GameObject;
import org.woodwhales.tank.Tank;
import org.woodwhales.tank.Wall;

public class TankWallCollisioner implements Collisioner {

	@Override
	public boolean collise(GameObject object1, GameObject object2) {
		if(object1 instanceof Tank && object2 instanceof Wall) {
			Tank tank = (Tank) object1;
			Wall wall = (Wall) object2;
			// 坦克之间碰撞，紧紧停止移动，不是撞死，所以必须返回true
			if(tank.getRectangle().intersects(wall.getRectangle())) {
				tank.back();
			}
		}
		
		if(object1 instanceof Wall && object2 instanceof Tank) {
			collise(object2, object1);
		}
		
		return true;
	}
}
