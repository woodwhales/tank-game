package org.woodwhales.tank;

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

	private static final int SPEED = 10;

	// tank是否为移动状态
	private boolean moving = false;

	public Tank(int x, int y, Dir dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public void paint(Graphics g) {
		g.drawRect(x, y, 50, 50);

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

}
