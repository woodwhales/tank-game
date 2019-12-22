package org.woodwhales.tank.decorator;

import java.awt.Graphics;

import org.woodwhales.tank.GameObject;

import lombok.Data;


@Data
public abstract class GameObjectDecorator extends GameObject {

	private GameObject gameObject;
	
	public GameObjectDecorator(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	@Override
	public abstract void paint(Graphics g);

	@Override
	public int getWidth() {
		return this.gameObject.getWidth();
	}

	@Override
	public int getHeight() {
		return this.gameObject.getHeight();
	}

}
