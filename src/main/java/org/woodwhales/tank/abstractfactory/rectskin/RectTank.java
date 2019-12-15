package org.woodwhales.tank.abstractfactory.rectskin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import org.woodwhales.tank.Dir;
import org.woodwhales.tank.FireStrategy;
import org.woodwhales.tank.Group;
import org.woodwhales.tank.PropertiesManager;
import org.woodwhales.tank.TankFrame;
import org.woodwhales.tank.abstractfactory.BaseTank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 将tank封装 因为有多人一起玩的时候，不可能在 TankFrame 里定义n多个位置变量、方向变量等， 
 * 这样会导致代码变得耦合度很高，也很臃肿。
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RectTank extends BaseTank {

	private static final int SPEED = 5;
	
	public static int WIDTH = 30;
	public static int HEIGHT = 30;
	
	public RectTank(int x, int y, Dir dir, Group group, TankFrame frame) {
		this.x = x;
		this.y = y;
		this.frame = frame;
		this.dir = dir;
		this.group = group;
		this.speed = SPEED;
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
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		if(!living) {
			frame.tanks.remove(this);
			return;
		}
		
		Color color = g.getColor();
		g.setColor(Color.GREEN);
		g.fillRect(this.x, this.y, WIDTH, HEIGHT);
		g.setColor(color);
		
		move();
	}
	
	

}
