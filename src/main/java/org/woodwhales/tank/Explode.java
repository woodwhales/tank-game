package org.woodwhales.tank;

import java.awt.Graphics;

import lombok.Data;

@Data
public class Explode {

	public static int WIDTH = ResourcesManager.explodes[0].getWidth();
	public static int HEIGHT = ResourcesManager.explodes[0].getHeight();

	private int x, y;

	private TankFrame frame;
	
	private boolean living = true;
	
	private int step = 0;
	
	public Explode(int x, int y, TankFrame frame) {
		this.x = x;
		this.y = y;
		this.frame = frame;
		new Thread(()-> new Audio("audio/explode.wav").play()).start();
	}

	public void paint(Graphics g) {
		g.drawImage(ResourcesManager.explodes[step++], this.x, this.y, null);
		if(step >= ResourcesManager.explodes.length) {
			this.frame.explodes.remove(this);
		}
	}

}
