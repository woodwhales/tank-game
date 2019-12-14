package org.woodwhales.tank;

import java.awt.Graphics;
import java.util.Random;

import lombok.Data;

/**
 * 将tank封装 因为有多人一起玩的时候，不可能在 TankFrame 里定义n多个位置变量、方向变量等， 
 * 这样会导致代码变得耦合度很高，也很臃肿。
 *
 */
@Data
public class Tank {

	private int x, y;

	private Dir dir = Dir.DOWN;

	private static final int SPEED = 1;
	
	public static int WIDTH = ResourcesManager.tankD.getWidth();
	public static int HEIGHT = ResourcesManager.tankD.getHeight();
	
	// 是否是存活的
	private boolean living = true;

	// tank是否为移动状态
	private boolean moving = true;

	private Random random = new Random();
	
	private Group group = Group.BAD;
	
	private TankFrame frame;

	public Tank(int x, int y, Dir dir, Group group, TankFrame frame) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.frame = frame;
	}

	public void paint(Graphics g) {
		if(!living) {
			frame.tanks.remove(this);
			return;
		}
		
		switch (dir) {
		case LEFT:
			g.drawImage(ResourcesManager.tankL, x, y, null);
			break;
		case UP:
			g.drawImage(ResourcesManager.tankU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(ResourcesManager.tankR, x, y, null);
			break;
		case DOWN:
			g.drawImage(ResourcesManager.tankD, x, y, null);
			break;
		}
		
		move();
	}

	private void move() {
		if (!moving) {
			return;
		}

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

		if(random.nextInt(10) > 8) {
			this.fire();
		}	
	}

	/**
	 * 发射子弹
	 */
	public void fire() {
		int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH;
		int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT;
		
		this.frame.bullets.add(new Bullet(bX, bY, this.dir, this.group, frame));
	}

	public void die() {
		this.living = false;
	}

}
