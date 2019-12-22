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
	
//	List<Bullet> bullets = new ArrayList<>();
//	List<Tank> tanks = new ArrayList<>();
//	List<Explode> explodes = new ArrayList<>();

	List<GameObject> objects = new ArrayList<>();
	
	Tank myTank = new Tank(200, 400, Dir.RIGHT, Group.GOOD, this);
	
//	Collisioner collisioner1 = new BulletTankCollisioner();
//	Collisioner collisioner2 = new TankTankCollisioner();
	CollisionerChain collisionerChain = new CollisionerChain();

	public GameModel() {
		int intTankCount = Integer.parseInt((String)PropertiesManager.getValue("initTankCount"));
		
		for (int i = 0; i < intTankCount; i++) {
			add(new Tank(50 + i*80, 200, Dir.DOWN, Group.BAD, this));
		}
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
//		g.drawString("bullets size = " + bullets.size() ,10, 50);
//		g.drawString("enemies size = " + tanks.size() ,10, 60);
//		g.drawString("explodes size = " + explodes.size() ,10, 70);
		g.setColor(color);
		
//		g.drawImage(ResourcesManager.missileLD, 10, 90, null);
		
		myTank.paint(g);
		
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).paint(g);
		}
		
		// 相互碰撞
		for (int i = 0; i < objects.size(); i++) {
			for (int j = i+1; j < objects.size(); j++) {
				GameObject object1 = objects.get(i);
				GameObject object2 = objects.get(j);
//				这种方式不利于扩展，所以使用责任链模式
//				collisioner1.collision(object1, object2);
//				collisioner2.collision(object1, object2);
				collisionerChain.collise(object1, object2);
			}
		}
		
//		for (int i = 0; i < bullets.size(); i++) {
//			for (int j = 0; j < tanks.size(); j++) {
//				bullets.get(i).collideWith(tanks.get(j));
//			}
//		}
	}

	public Tank getMainTank() {
		return myTank;
	}

}
