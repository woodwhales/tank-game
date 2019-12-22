package org.woodwhales.tank;

import java.awt.Graphics;

import lombok.Data;

@Data
public abstract class GameObject {
	
	protected int x, y;
	
	public abstract void paint(Graphics g);

	public abstract int getWidth();

	public abstract int getHeight();
	
}
