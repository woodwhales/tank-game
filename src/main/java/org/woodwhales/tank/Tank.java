package org.woodwhales.tank;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.woodwhales.tank.abstractfactory.BaseTank;
import org.woodwhales.tank.config.PropertiesManager;
import org.woodwhales.tank.config.ResourcesManager;
import org.woodwhales.tank.enums.Dir;
import org.woodwhales.tank.enums.Group;
import org.woodwhales.tank.strategy.FireStrategy;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 将tank封装 因为有多人一起玩的时候，不可能在 TankFrame 里定义n多个位置变量、方向变量等， 
 * 这样会导致代码变得耦合度很高，也很臃肿。
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Tank extends BaseTank {

	private static final int SPEED = 5;
	
	public static int WIDTH = ResourcesManager.badTankD.getWidth();
	public static int HEIGHT = ResourcesManager.badTankD.getHeight();
	
	private int x, y;
	
	public Tank(int x, int y, Dir dir, Group group, TankFrame frame) {
		this.x = x;
		this.y = y;
		this.frame = frame;
		this.dir = dir;
		this.speed = SPEED;
		this.group = group;
		this.width = WIDTH;
		this.height = HEIGHT;
		this.positionX = WIDTH / 2 - Bullet.WIDTH/2;
		this.positionY = HEIGHT / 2 - Bullet.HEIGHT/2;
		this.rectangle = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
		
		String fireStrategyClass = null;
		if(this.group == Group.GOOD) {
			fireStrategyClass = (String) PropertiesManager.getValue("goodFS");
		} else {
			fireStrategyClass = (String) PropertiesManager.getValue("badFS");
		}
		
		try {
			this.fireStrategy = (FireStrategy) Class.forName(fireStrategyClass).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			System.err.println("can not found this class file = " + fireStrategyClass);
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void paint(Graphics g) {
		if(!living) {
			frame.tanks.remove(this);
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
	
	public void move() {
		if (!moving) {
			return;
		}

		switch (dir) {
		case LEFT:
			x -= speed;
			break;
		case UP:
			y -= speed;
			break;
		case RIGHT:
			x += speed;
			break;
		case DOWN:
			y += speed;
			break;
		}
		
		if(this.group == Group.BAD && random.nextInt(100) > 95) {
			this.fireStrategy.fire(this, positionX, positionY);
		}
		
		if(this.group == Group.BAD && random.nextInt(100) > 95) {
			randomDir();
		}
		
		boundsCheck();
		
		this.rectangle.x = this.x;
		this.rectangle.y = this.y;
	}

	private void boundsCheck() {
		if(this.x < 0) {
			this.x = 0;
		}
		
		if(this.x > TankFrame.GAME_WIDTH - width) {
			this.x = TankFrame.GAME_WIDTH - width;
		}
		
		if(this.y < 25) {
			this.y = 25;
		}
		
		if(this.y > TankFrame.GAME_HEIGHT - height) {
			this.y = TankFrame.GAME_HEIGHT - height;
		}
	}

}
