package org.woodwhales.tank;

import java.awt.Graphics;
import java.awt.Rectangle;

import org.woodwhales.tank.abstractfactory.BaseBullet;
import org.woodwhales.tank.abstractfactory.BaseTank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Bullet extends BaseBullet {

	private static final int SPEED = 10;
	public static int WIDTH = ResourcesManager.bulletD.getWidth();
	public static int HEIGHT = ResourcesManager.bulletD.getHeight();

	private Rectangle rectangle;
	
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
		this.rectangle = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
		this.frame.bullets.add(this);
	}

	@Override
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
		
		if(x < 0 || y < 25 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
			this.living = false;
		}
		
		this.rectangle.x = this.x;
		this.rectangle.y = this.y;
	}

	/**
	 * 子弹做碰撞检测
	 * @param tank
	 */
	public void collideWith(BaseTank tank) {
		if(this.group == tank.getGroup()) {
			return;
		}
		
		int tankX = tank.getX();
		int tankY = tank.getY();
		
		if(this.rectangle.intersects(tank.getRectangle())) {
			tank.die();
			this.die();
			
			int eX = tankX + Tank.WIDTH/2 - Explode.WIDTH/2;
			int eY = tankY + Tank.HEIGHT/2 - Explode.HEIGHT/2;
			this.frame.explodes.add(this.frame.gameFactory.createExplode(eX, eY, frame));
		}
	}

	private void die() {
		this.living = false;
	}

}
