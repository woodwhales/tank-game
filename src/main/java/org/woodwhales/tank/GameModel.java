package org.woodwhales.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.woodwhales.tank.collision.CollisionerChain;

/**
 * 由GameModel 来管理 所有的 tank bullet 的生命周期（碰撞关系）
 *
 */
public class GameModel {
	
	private static final GameModel INSTANCE = new GameModel();
	
	List<GameObject> objects = new ArrayList<>();
	
	Tank myTank = new Tank(200, 400, Dir.RIGHT, Group.GOOD, this);

	CollisionerChain collisionerChain = new CollisionerChain();

	public static GameModel getInstance() {
		return INSTANCE;
	}
	
	private GameModel() {
		int intTankCount = Integer.parseInt((String)PropertiesManager.getValue("initTankCount"));
		
		for (int i = 0; i < intTankCount; i++) {
			add(new Tank(50 + i*80, 200, Dir.DOWN, Group.BAD, this));
		}
		
		// 初始化墙
		add(new Wall(150, 150, 200, 50));
		add(new Wall(550, 150, 200, 50));
		add(new Wall(300, 300, 50, 200));
		add(new Wall(550, 400, 50, 200));
		
	}
	
	public void add(GameObject gameObject) {
		this.objects.add(gameObject);
	}
	
	public void remove(GameObject gameObject) {
		this.objects.remove(gameObject);
	}
	
	public void paint(Graphics g) {
		Color color = g.getColor();
		g.setColor(Color.WHITE);
		g.setColor(color);
		
		myTank.paint(g);
		
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).paint(g);
		}
		
		// 相互碰撞
		for (int i = 0; i < objects.size(); i++) {
			for (int j = i+1; j < objects.size(); j++) {
				GameObject object1 = objects.get(i);
				GameObject object2 = objects.get(j);
				collisionerChain.collise(object1, object2);
			}
		}
		
	}

	public Tank getMainTank() {
		return myTank;
	}

}
