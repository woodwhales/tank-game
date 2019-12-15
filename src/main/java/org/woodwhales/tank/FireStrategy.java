package org.woodwhales.tank;

import org.woodwhales.tank.abstractfactory.BaseTank;

public interface FireStrategy {
	void fire(BaseTank tank, int positionX, int positionY);
}
