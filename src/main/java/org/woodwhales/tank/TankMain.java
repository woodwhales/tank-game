package org.woodwhales.tank;

public class TankMain {

	public static void main(String[] args) throws InterruptedException {
		TankFrame tankFrame = new TankFrame();
		
		while(true) {
			Thread.sleep(50);
			
			// 不能调用paint()方法，是因为程序无法自己初始化一只画笔（Graphics）
			tankFrame.repaint();			
		}
	}

}
