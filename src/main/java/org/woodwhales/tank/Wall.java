package org.woodwhales.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import lombok.Data;

@Data
public class Wall extends GameObject {
	
	private int width, height;
	
	private Rectangle rectangle;

	public Wall(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rectangle = new Rectangle(x, y, width, height);
	}

	@Override
	public void paint(Graphics g) {
		Color color = g.getColor();
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, width, height);
		g.setColor(color);
	}

}
