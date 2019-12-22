package org.woodwhales.tank;

import java.awt.Graphics;

import lombok.Data;

@Data
public class Explode extends GameObject {

	public static int WIDTH = ResourcesManager.explodes[0].getWidth();
	public static int HEIGHT = ResourcesManager.explodes[0].getHeight();

	private int x, y;

	private boolean living = true;
	
	private int step = 0;
	
	public Explode(int x, int y) {
		this.x = x;
		this.y = y;
		new Thread(()-> new Audio("audio/explode.wav").play()).start();
		GameModel.getInstance().add(this);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(ResourcesManager.explodes[step++], this.x, this.y, null);
		if(step >= ResourcesManager.explodes.length) {
			GameModel.getInstance().remove(this);
		}
	}

}
