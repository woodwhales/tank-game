package org.woodwhales.tank;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.woodwhales.tank.observer.FireEvent;
import org.woodwhales.tank.observer.FireEventObserver;
import org.woodwhales.tank.observer.TankFireEventHandler;
import org.woodwhales.tank.strategy.FireStrategy;

import lombok.Data;

/**
 * 将tank封装 因为有多人一起玩的时候，不可能在 TankFrame 里定义n多个位置变量、方向变量等， 
 * 这样会导致代码变得耦合度很高，也很臃肿。
 *
 */
@Data
public class Tank extends GameObject {

	private int oldX, oldY;
	
	private Dir dir = Dir.DOWN;

	private static final int SPEED = 5;
	
	public static int WIDTH = ResourcesManager.badTankD.getWidth();
	public static int HEIGHT = ResourcesManager.badTankD.getHeight();
	
	// 是否是存活的
	private boolean living = true;

	// tank是否为移动状态
	private boolean moving = true;

	private Random random = new Random();
	
	private Group group = Group.BAD;
	
	private Rectangle rectangle;
	
	private FireStrategy fireStrategy;
	
	public Tank(int x, int y, Dir dir, Group group) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.rectangle = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
		
		String fireStrategyClass = null;
		if(this.group == Group.GOOD) {
			fireStrategyClass = (String) PropertiesManager.getValue("goodFS");
		} else {
			fireStrategyClass = (String) PropertiesManager.getValue("badFS");
		}
		
		try {
			this.fireStrategy = (FireStrategy) Class.forName(fireStrategyClass).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print("load fireStrategyClass from config happend error!");
			System.exit(0);
		}
		
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
		// 记录移动之前的位置
		this.oldX = x;
		this.oldY = y;
		
		if (!moving) {
			this.x = oldX;
			this.y = oldY;
			this.rectangle.x = oldX;
			this.rectangle.y = oldY;
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
			this.fireStrategy.fire(this);
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
		
		if(this.x > TankFrame.GAME_WIDTH - Tank.WIDTH) {
			this.x = TankFrame.GAME_WIDTH - Tank.WIDTH;
		}
		
		if(this.y < 25) {
			this.y = 25;
		}
		
		if(this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT) {
			this.y = TankFrame.GAME_HEIGHT - Tank.HEIGHT;
		}
	}

	private void randomDir() {
		this.dir = Dir.values()[random.nextInt(4)];
	}

	/**
	 * 发射子弹
	 * 方法参数：作用域安全，但是每次传参需要new，因此 FireStrategy 最好做成单例
	 * 成员变量：该属性的作用域是整个类，导致类的结构更加复杂
	 */
	public void fire() {
		fireStrategy.fire(this);
	}

	public void die() {
		this.living = false;
	}

	public void back() {
		this.x = oldX;
		this.y = oldY;
	}
	
	public void stop() {
		this.moving = false;
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	private List<FireEventObserver> fireEventObservers = Arrays.asList(new TankFireEventHandler()); 
	public void handleFireKey() {
		FireEvent event = new FireEvent(this);
		for (FireEventObserver fireEventObserver : fireEventObservers) {
			fireEventObserver.actionOnFire(event);
		}
	}
	
}
