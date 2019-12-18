package org.woodwhales.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * 由GameModel 来管理 所有的 tank bullet 的生命周期（碰撞关系）
 *
 */
public class GameModel {
	
	List<Bullet> bullets = new ArrayList<>();
	List<Tank> tanks = new ArrayList<>();
	List<Explode> explodes = new ArrayList<>();
	
	Tank myTank = new Tank(200, 400, Dir.RIGHT, Group.GOOD, this);

	public GameModel() {
		int intTankCount = Integer.parseInt((String)PropertiesManager.getValue("initTankCount"));
		
		for (int i = 0; i < intTankCount; i++) {
			this.tanks.add(new Tank(50 + i*80, 200, Dir.DOWN, Group.BAD, this));
		}
	}
	
	public void paint(Graphics g) {
		Color color = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("bullets size = " + bullets.size() ,10, 50);
		g.drawString("enemies size = " + tanks.size() ,10, 60);
		g.drawString("explodes size = " + explodes.size() ,10, 70);
		g.setColor(color);
		
		g.drawImage(ResourcesManager.missileLD, 10, 90, null);
		
		myTank.paint(g);
		
		for(int i = 0; i < tanks.size(); i++) {
			tanks.get(i).paint(g);
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paint(g);
		}
		
		for (int i = 0; i < explodes.size(); i++) {
			explodes.get(i).paint(g);
		}
		
		for (int i = 0; i < bullets.size(); i++) {
			for (int j = 0; j < tanks.size(); j++) {
				bullets.get(i).collideWith(tanks.get(j));
			}
		}
	}

	public Tank getMainTank() {
		return myTank;
	}

}
