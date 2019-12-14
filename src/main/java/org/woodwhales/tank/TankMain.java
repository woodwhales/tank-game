package org.woodwhales.tank;

public class TankMain {

	public static void main(String[] args) throws InterruptedException {
		TankFrame tankFrame = new TankFrame();
		
		for (int i = 0; i < 5; i++) {
			tankFrame.tanks.add(new Tank(50 + i*80, 200, Dir.DOWN, tankFrame));
		}
		
		while(true) {
			Thread.sleep(50);
			
			// 不能调用paint()方法，是因为程序无法自己初始化一只画笔（Graphics）
			tankFrame.repaint();			
		}
	}

}
