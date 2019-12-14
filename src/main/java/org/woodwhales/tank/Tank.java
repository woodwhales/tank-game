package org.woodwhales.tank;

import java.awt.Color;
import java.awt.Graphics;

import lombok.Data;

/**
 * 将tank封装 因为有多人一起玩的时候，不可能在 TankFrame 里定义n多个位置变量、方向变量等， 这样会导致代码变得耦合度很高，也很臃肿。
 * 
 * 
 *
 */
@Data
public class Tank {

	private int x, y;

	private Dir dir = Dir.DOWN;

	private static final int SPEED = 5;

	// tank是否为移动状态
	private boolean moving = false;

	private TankFrame frame;
	
	public Tank(int x, int y, Dir dir, TankFrame frame) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.frame = frame;
	}

	public void paint(Graphics g) {
		Color color = g.getColor();
		
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, 50, 50);
		g.setColor(color);
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
	}

	/**
	 * 发射子弹
	 */
	public void fire() {
		this.frame.bullet = new Bullet(this.x, this.y, this.dir);
	}

}
