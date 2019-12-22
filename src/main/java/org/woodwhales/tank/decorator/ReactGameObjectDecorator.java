package org.woodwhales.tank.decorator;

import java.awt.Color;
import java.awt.Graphics;

import org.woodwhales.tank.GameObject;

public class ReactGameObjectDecorator extends GameObjectDecorator {

	public ReactGameObjectDecorator(GameObject gameObject) {
		super(gameObject);
	}
	
	@Override
	public void paint(Graphics g) {
		this.x = getGameObject().getX();
		this.y = getGameObject().getY();
		getGameObject().paint(g);
		
		Color color = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawRect(getGameObject().getX(), getGameObject().getY(), super.getWidth()+2, super.getHeight()+2);
		g.setColor(color);
	}

}
