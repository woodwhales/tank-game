package org.woodwhales.tank.collision;

import org.woodwhales.tank.GameObject;
import org.woodwhales.tank.Tank;

public class TankTankCollisioner implements Collisioner {

	@Override
	public boolean collise(GameObject object1, GameObject object2) {
		if(object1 instanceof Tank && object2 instanceof Tank) {
			Tank tank1 = (Tank) object1;
			Tank tank2 = (Tank) object2;
			// 坦克之间碰撞，紧紧停止移动，不是撞死，所以必须返回true
			if(tank1.getRectangle().intersects(tank2.getRectangle())) {
				tank1.stop();
			}
		}
		
		return true;
		
	}
	
}
