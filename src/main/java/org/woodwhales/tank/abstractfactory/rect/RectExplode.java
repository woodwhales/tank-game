package org.woodwhales.tank.abstractfactory.rect;

import java.awt.Color;
import java.awt.Graphics;

import org.woodwhales.tank.TankFrame;
import org.woodwhales.tank.abstractfactory.BaseExplode;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RectExplode extends BaseExplode {

	public static int WIDTH = 5;
	public static int HEIGHT = 5;

	private int x, y;

	private TankFrame frame;
	
	private boolean living = true;
	
	private int step = 0;
	
	public RectExplode(int x, int y, TankFrame frame) {
		this.x = x;
		this.y = y;
		this.frame = frame;
		playAudio("audio/explode.wav");
	}

	@Override
	public void paint(Graphics g) {
		Color color = g.getColor();
		
		g.setColor(Color.RED);
		g.fillRect(x, y, 10 * step, 10 * step);
			
		step++;
		
		if(step >= 10) {
			this.frame.explodes.remove(this);
		}
		
		g.setColor(color);
	}

}
