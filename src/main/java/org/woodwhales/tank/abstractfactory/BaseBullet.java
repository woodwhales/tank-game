package org.woodwhales.tank.abstractfactory;

import java.awt.Graphics;
import java.awt.Rectangle;

import org.woodwhales.tank.Dir;
import org.woodwhales.tank.Group;
import org.woodwhales.tank.TankFrame;

import lombok.Data;

@Data
public abstract class BaseBullet {
	
	protected int speed;
	
	protected Group group;
	
	protected Rectangle rectangle;

	protected Dir dir;
	
	public abstract void paint(Graphics g);

	public abstract void collideWith(BaseTank tank);
	
	protected TankFrame frame;
	
}
