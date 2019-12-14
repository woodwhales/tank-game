package org.woodwhales.tank;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourcesManager {

	public static BufferedImage tankL, tankU, tankR, tankD;
	public static BufferedImage bulletL, bulletU, bulletR, bulletD;
	public static final String imagesPath = "";
	
	static {
		ClassLoader classLoader = ResourcesManager.class.getClassLoader();
		try {
			tankL = ImageIO.read(classLoader.getResourceAsStream("images/tankL.gif"));
			tankU = ImageIO.read(classLoader.getResourceAsStream("images/tankU.gif"));
			tankR = ImageIO.read(classLoader.getResourceAsStream("images/tankR.gif"));
			tankD = ImageIO.read(classLoader.getResourceAsStream("images/tankD.gif"));
			
			bulletL = ImageIO.read(classLoader.getResourceAsStream("images/bulletL.gif"));
			bulletU = ImageIO.read(classLoader.getResourceAsStream("images/bulletU.gif"));
			bulletR = ImageIO.read(classLoader.getResourceAsStream("images/bulletR.gif"));
			bulletD = ImageIO.read(classLoader.getResourceAsStream("images/bulletD.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
