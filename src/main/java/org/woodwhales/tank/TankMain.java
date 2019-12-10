package org.woodwhales.tank;

public class TankMain {

	public static void main(String[] args) throws InterruptedException {
		TankFrame tankFrame = new TankFrame();
		
		while(true) {
			Thread.sleep(50);
			tankFrame.repaint();			
		}
	}

}
