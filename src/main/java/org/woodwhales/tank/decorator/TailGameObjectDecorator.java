package org.woodwhales.tank.decorator;

import java.awt.Color;
import java.awt.Graphics;

import org.woodwhales.tank.GameObject;

public class TailGameObjectDecorator extends GameObjectDecorator {

	public TailGameObjectDecorator(GameObject gameObject) {
		super(gameObject);
	}
	
	@Override
	public void paint(Graphics g) {
		this.x = getGameObject().getX();
		this.y = getGameObject().getY();
		getGameObject().paint(g);
		
		Color color = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawLine(getGameObject().getX(), getGameObject().getY(), getGameObject().getX() + getWidth(), getGameObject().getY() + getHeight());
		g.setColor(color);
	}

}
