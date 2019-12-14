package org.woodwhales.tank;

import java.awt.Graphics;

import lombok.Data;

@Data
public class Bullet {

	private static final int SPEED = 10;
	private static int WIDTH = 20, HEIGHT = 20;

	private int x, y;

	private Dir dir;
	
	private TankFrame frame;
	
	private boolean live = true;

	public Bullet(int x, int y, Dir dir, TankFrame frame) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.frame = frame;
	}

	public void paint(Graphics g) {
//		if(!live) {
//			this.frame.bullets.remove(this);
//		}
		
		switch (dir) {
		case LEFT:
			g.drawImage(ResourcesManager.bulletL, x, y, null);
			break;
		case UP:
			g.drawImage(ResourcesManager.bulletU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(ResourcesManager.bulletR, x, y, null);
			break;
		case DOWN:
			g.drawImage(ResourcesManager.bulletD, x, y, null);
			break;
		}
		
		move();
	}

	private void move() {
		switch (dir) {
		case LEFT:
			x -= SPEED;
			break;
		case UP:
			y -= SPEED;
			break;
		case RIGHT:
			x += SPEED;
			break;
		case DOWN:
			y += SPEED;
			break;
		}
		
		if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
			this.live = false;
		}
	}

}
