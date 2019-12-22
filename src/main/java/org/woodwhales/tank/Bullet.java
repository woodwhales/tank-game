package org.woodwhales.tank;

import java.awt.Graphics;
import java.awt.Rectangle;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Bullet extends GameObject {

	private static final int SPEED = 10;
	public static int WIDTH = ResourcesManager.bulletD.getWidth();
	public static int HEIGHT = ResourcesManager.bulletD.getHeight();

	private Rectangle rectangle;
	
	private Dir dir;
	
	private boolean living = true;
	
	private Group group = Group.BAD;

	public Bullet(int x, int y, Dir dir, Group group) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.rectangle = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
		GameModel.getInstance().add(this);
	}

	@Override
	public void paint(Graphics g) {
		if(!living) {
			GameModel.getInstance().remove(this);
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
		
		if(x < 0 || y < 25 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
			this.living = false;
		}
		
		this.rectangle.x = this.x;
		this.rectangle.y = this.y;
	}

	public void die() {
		this.living = false;
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

}
