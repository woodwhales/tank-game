package org.woodwhales.tank;

import org.junit.jupiter.api.Test;

class PropertiesManagerTest {

	@Test
	void test() {
		Object value = PropertiesManager.getValue("tanksCount");
		System.out.println(value);
	}

}
