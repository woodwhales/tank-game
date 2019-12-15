package org.woodwhales.tank.abstractfactory;

import java.awt.Graphics;

import lombok.Data;

@Data
public abstract class BaseBullet {
	
	protected int WIDTH;
	protected int HEIGHT;

	public abstract void paint(Graphics g);

	public abstract void collideWith(BaseTank tank);

}
