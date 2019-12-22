package org.woodwhales.tank.collision;

import java.util.LinkedList;
import java.util.List;

import org.springframework.util.StringUtils;
import org.woodwhales.tank.GameObject;
import org.woodwhales.tank.PropertiesManager;

public class CollisionerChain implements Collisioner {
	
	private List<Collisioner> collisioners = new LinkedList<>();

	public CollisionerChain() {
		
		String collisionerStrings = (String) PropertiesManager.getValue("collisioners");
		
		if(StringUtils.isEmpty(collisioners)) {
			System.err.print("can not found any collisioners from config");
			System.exit(0);
		}
		
		String[] collisionerClassList = collisionerStrings.split(",");
		
		try {
			for (String collisionerClass : collisionerClassList) {
				add((Collisioner) Class.forName(collisionerClass).newInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print("load collisionerClass from config happend error!");
			System.exit(0);
		}
	}
	
	public void add(Collisioner collisioner) {
		collisioners.add(collisioner);
	}

	@Override
	public boolean collise(GameObject object1, GameObject object2) {
		for (Collisioner collisioner : collisioners) {
			if(!collisioner.collise(object1, object2)) {
				return false; 
			}
		}
		return true;
	}
	
}
