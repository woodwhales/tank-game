package org.woodwhales.tank;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.UUID;

import org.woodwhales.tank.net.Client;
import org.woodwhales.tank.net.msg.TankDieMsg;

import lombok.Data;

@Data
public class Bullet {

	private static final int SPEED = 10;
	public static int WIDTH = ResourcesManager.bulletD.getWidth();
	public static int HEIGHT = ResourcesManager.bulletD.getHeight();

	private Rectangle rectangle;
	
	private int x, y;

	private Dir dir;
	
	private TankFrame frame;
	
	private boolean living = true;
	
	private Group group = Group.BAD;
	
	private UUID id = UUID.randomUUID();
	
	private UUID playerId;

	public Bullet(UUID playerId, int x, int y, Dir dir, Group group, TankFrame frame) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.frame = frame;
		this.playerId = playerId;
		this.rectangle = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
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
	public void collideWith(Tank tank) {
		if(this.playerId.equals(tank.getId())) {
			return;
		}
		
		int tankX = tank.getX();
		int tankY = tank.getY();
		
		if(this.isLiving() && tank.isLiving() && this.rectangle.intersects(tank.getRectangle())) {
			tank.die();
			this.die();
			Client.INSTANCE.send(new TankDieMsg(this.id, tank.getId()));
		}
	}

	public void die() {
		this.living = false;
	}

}
