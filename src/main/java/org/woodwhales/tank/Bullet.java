package org.woodwhales.tank;

import java.awt.Graphics;
import java.awt.Rectangle;

import lombok.Data;

@Data
public class Bullet {

	private static final int SPEED = 10;
	public static int WIDTH = ResourcesManager.bulletD.getWidth();
	public static int HEIGHT = ResourcesManager.bulletD.getHeight();

	private int x, y;

	private Dir dir;
	
	private TankFrame frame;
	
	private boolean living = true;
	
	private Group group = Group.BAD;

	public Bullet(int x, int y, Dir dir, Group group, TankFrame frame) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.frame = frame;
	}

	public void paint(Graphics g) {
		if(!living) {
			frame.bullets.remove(this);
			return;
		}
		
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
			this.living = false;
		}
	}

	/**
	 * 子弹做碰撞检测
	 * @param tank
	 */
	public void collideWith(Tank tank) {
		if(this.group == tank.getGroup()) {
			return;
		}
		
		//TODO 用一个rect来记录子弹的位置
		Rectangle rectangle1 = new Rectangle(this.x, this.y, Bullet.WIDTH, Bullet.HEIGHT);
		Rectangle rectangle2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
		
		if(rectangle1.intersects(rectangle2)) {
			tank.die();
			this.die();
		}
	}

	private void die() {
		this.living = false;		
	}

}
