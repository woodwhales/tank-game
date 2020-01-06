package org.woodwhales.tank;

import org.woodwhales.tank.net.Client;

public class TankMain {

	public static void main(String[] args) throws InterruptedException {
		TankFrame tankFrame = TankFrame.INSTANCE;
		tankFrame.setVisible(true);
		
//		int intTankCount = Integer.parseInt((String)PropertiesManager.getValue("initTankCount"));
//		
//		for (int i = 0; i < intTankCount; i++) {
//			tankFrame.tanks.add(new Tank(50 + i*80, 200, Dir.DOWN, Group.BAD, tankFrame));
//		}
		
		new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// 不能调用paint()方法，是因为程序无法自己初始化一只画笔（Graphics）
				tankFrame.repaint();			
			}
		}).start();
		
		Client client = new Client();
		client.connect();
	}

}
