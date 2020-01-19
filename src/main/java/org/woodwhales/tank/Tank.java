package org.woodwhales.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.UUID;

import org.woodwhales.tank.net.Client;
import org.woodwhales.tank.net.msg.BulletNewMsg;
import org.woodwhales.tank.net.msg.TankJoinMsg;

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

	private static final int SPEED = 5;
	
	public static int WIDTH = ResourcesManager.badTankD.getWidth();
	public static int HEIGHT = ResourcesManager.badTankD.getHeight();
	
	// 是否是存活的
	private boolean living = true;

	// tank是否为移动状态
	private boolean moving = false;

	private Random random = new Random();
	
	private Group group = Group.BAD;
	
	private TankFrame frame;

	private Rectangle rectangle;
	
	private UUID id = UUID.randomUUID(); 
	
	public Tank(int x, int y, Dir dir, Group group, TankFrame frame) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.frame = frame;
		this.rectangle = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
	}

	public Tank(TankJoinMsg msg) {
		this.x = msg.getX();
		this.y = msg.getY();
		this.dir = msg.getDir();
		this.moving = msg.isMoving();
		this.group = msg.getGroup();
		this.id = msg.getId();
		this.rectangle = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
	}

	public void paint(Graphics g) {
		Color color = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawString("live = " + living, this.x, this.y - 10);
		g.drawString(id.toString(), this.x, this.y - 20);
		g.setColor(color);
		
		if(!living) {
			moving = false;
			Color colors = g.getColor();
			g.setColor(Color.WHITE);
			g.drawRect(x, y, WIDTH, HEIGHT);
			g.setColor(colors);
			return;
		}

		switch (dir) {
		case LEFT:
			g.drawImage(getTankL(), x, y, null);
			break;
		case UP:
			g.drawImage(getTankU(), x, y, null);
			break;
		case RIGHT:
			g.drawImage(getTankR(), x, y, null);
			break;
		case DOWN:
			g.drawImage(getTankD(), x, y, null);
			break;
		}
		
		move();
	}
	
	private BufferedImage getTankL() {
		if(this.group == Group.GOOD) {
			return ResourcesManager.goodTankL;
		}
		return ResourcesManager.badTankL;
	}
	
	private BufferedImage getTankU() {
		if(this.group == Group.GOOD) {
			return ResourcesManager.goodTankU;
		}
		return ResourcesManager.badTankU;
	}
	
	private BufferedImage getTankR() {
		if(this.group == Group.GOOD) {
			return ResourcesManager.goodTankR;
		}
		return ResourcesManager.badTankR;
	}
	
	private BufferedImage getTankD() {
		if(this.group == Group.GOOD) {
			return ResourcesManager.goodTankD;
		}
		return ResourcesManager.badTankD;
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
		
		if(this.group == Group.BAD && random.nextInt(100) > 95) {
			this.fire();
		}
		
		if(this.group == Group.BAD && random.nextInt(100) > 95) {
			randomDir();
		}
		
		boundsCheck();
		
		this.rectangle.x = this.x;
		this.rectangle.y = this.y;
	}

	private void boundsCheck() {
		if(this.x < 2) {
			this.x = 2;
		}
		
		if(this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) {
			this.x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
		}
		
		if(this.y < 28) {
			this.y = 28;
		}
		
		if(this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) {
			this.y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
		}
	}

	private void randomDir() {
		this.dir = Dir.values()[random.nextInt(4)];
	}

	/**
	 * 发射子弹
	 */
	public void fire() {
		int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
		int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
		
		Bullet bullet = new Bullet(id, bX, bY, this.dir, this.group, frame);
		this.frame.bullets.add(bullet);
		Client.INSTANCE.send(new BulletNewMsg(bullet));
	}

	public void die() {
		this.living = false;
		
		int eX = this.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
		int eY = this.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
		TankFrame.INSTANCE.explodes.add(new Explode(eX, eY));
		
	}

}
