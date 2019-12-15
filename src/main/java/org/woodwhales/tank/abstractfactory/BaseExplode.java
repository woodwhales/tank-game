package org.woodwhales.tank.abstractfactory;

import java.awt.Graphics;

import org.woodwhales.tank.Audio;

public abstract class BaseExplode {

	public abstract void paint(Graphics g);
	
	protected void playAudio(String fileName) {
		new Thread(()-> new Audio(fileName).play()).start();
	}

}
