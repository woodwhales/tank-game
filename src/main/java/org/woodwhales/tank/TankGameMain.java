package org.woodwhales.tank;

import org.woodwhales.tank.config.PropertiesManager;
import org.woodwhales.tank.enums.Dir;
import org.woodwhales.tank.enums.Group;

public class TankGameMain {

	public static void main(String[] args) throws InterruptedException {
		TankFrame tankFrame = new TankFrame();
		
		int intTankCount = Integer.parseInt((String)PropertiesManager.getValue("initTankCount"));
		
		for (int i = 0; i < intTankCount; i++) {
			tankFrame.tanks.add(tankFrame.gameFactory.createTank(50 + i*80, 200, Dir.DOWN, Group.BAD, tankFrame));
		}
		
		while(true) {
			Thread.sleep(50);
			
			// 不能调用paint()方法，是因为程序无法自己初始化一只画笔（Graphics）
			tankFrame.repaint();			
		}
	}

}
