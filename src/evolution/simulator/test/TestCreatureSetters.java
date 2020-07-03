package evolution.simulator.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import evolution.simulator.Creature;

public class TestCreatureSetters {
	
	Creature creature;
	
	@Before
	public void before() {
		creature = new Creature("test", 2, 3, 100, true, 50, 15, 1000, 500, false,
								true, new Creature());
	}
	
	@Test
	public void testSetFamine() {
		creature.setFamine(true);
		Assert.assertEquals(true, creature.isFamine());
	}
	
	@Test
	public void testSetFoodNoFamine() {
		creature.setFood();
		Assert.assertEquals(1936, creature.getFood());
	}
	
	@Test
	public void testSetFoodFamineAndLightWeight() {
		creature.setFamine(true);
		creature.setFood();
		Assert.assertEquals(580, creature.getFood());
	}
	
	@Test
	public void testSetHunger() {
		creature.setHunger();
		Assert.assertEquals(1001, creature.getHunger());
	}
}
