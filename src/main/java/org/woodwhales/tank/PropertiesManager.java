package org.woodwhales.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

	private static Properties props = new Properties();

	static {
		try {
			props.load(PropertiesManager.class.getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object getValue(String key) {
		if(props == null) {
			return null;
		}
		
		return props.get(key);
	}
}
