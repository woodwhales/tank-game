package org.woodwhales.tank.abstractfactory;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import org.woodwhales.tank.Dir;
import org.woodwhales.tank.FireStrategy;
import org.woodwhales.tank.Group;
import org.woodwhales.tank.TankFrame;

import lombok.Data;

@Data
public abstract class BaseTank {
	
	protected int speed;
	
	protected int width;
	
	protected int height;
	
	protected int positionX;
	
	protected int positionY;
	
	protected TankFrame frame;
	
	protected Dir dir = Dir.DOWN;
	
	protected Group group = Group.BAD;
	
	protected Rectangle rectangle;
	
	protected Random random = new Random();
	
	protected FireStrategy fireStrategy;
	
	// tank是否为移动状态
	protected boolean moving = true;
	
	// 是否是存活的
	protected boolean living = true;

	public abstract void paint(Graphics g);
	
	public void die() {
		this.living = false;
	}
	
	/**
	 * 发射子弹
	 * 方法参数：作用域安全，但是每次传参需要new，因此 FireStrategy 最好做成单例
	 * 成员变量：该属性的作用域是整个类，导致类的结构更加复杂
	 */
	public void fire() {
		fireStrategy.fire(this, positionX, positionY);
	}
	
	protected void randomDir() {
		this.dir = Dir.values()[random.nextInt(4)];
	}

	public abstract int getX();
	
	public abstract int getY();
}
