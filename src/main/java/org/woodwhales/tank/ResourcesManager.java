package org.woodwhales.tank;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourcesManager {

	public static BufferedImage badTankL, badTankU, badTankR, badTankD;
	public static BufferedImage goodTankL, goodTankU, goodTankR, goodTankD;
	public static BufferedImage bulletL, bulletU, bulletR, bulletD;
	public static BufferedImage missileLU, missileLD, missileRU, missileRD;
	public static BufferedImage[] explodes = new BufferedImage[16];
	
	public static final String imagesPath = "";
	
	static {
		ClassLoader classLoader = ResourcesManager.class.getClassLoader();
		try {
			badTankU = ImageIO.read(classLoader.getResourceAsStream("images/BadTank1.png"));
			badTankL = ImageUtil.rotateImage(badTankU, -90);
			badTankR = ImageUtil.rotateImage(badTankU, 90);
			badTankD = ImageUtil.rotateImage(badTankU, 180);
			
			goodTankU = ImageIO.read(classLoader.getResourceAsStream("images/GoodTank1.png"));
			goodTankL = ImageUtil.rotateImage(goodTankU, -90);
			goodTankR = ImageUtil.rotateImage(goodTankU, 90);
			goodTankD = ImageUtil.rotateImage(goodTankU, 180);
			
			bulletU = ImageIO.read(classLoader.getResourceAsStream("images/bulletU.png"));
			bulletL = ImageUtil.rotateImage(bulletU, -90);
			bulletR = ImageUtil.rotateImage(bulletU, 90);
			bulletD = ImageUtil.rotateImage(bulletU, 180);
			
			missileLU = ImageIO.read(classLoader.getResourceAsStream("images/missileLU.gif"));
			missileLD = ImageIO.read(classLoader.getResourceAsStream("images/missileLD.gif"));
			missileRU = ImageIO.read(classLoader.getResourceAsStream("images/missileRU.gif"));
			missileRD = ImageIO.read(classLoader.getResourceAsStream("images/missileLD.gif"));
			
			for (int i = 0; i< 16; ) {
				explodes[i] = ImageIO.read(classLoader.getResourceAsStream("images/e"+ (++i) +".gif"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
