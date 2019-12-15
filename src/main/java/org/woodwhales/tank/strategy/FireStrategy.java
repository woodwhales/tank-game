package org.woodwhales.tank.strategy;

import org.woodwhales.tank.abstractfactory.BaseTank;

public interface FireStrategy {
	void fire(BaseTank tank, int positionX, int positionY);
}
