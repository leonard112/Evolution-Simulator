package evolution.simulator.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import evolution.simulator.Creature;

public class TestCreatureGetters {
	
	Creature creature;
	Creature ancestor = new Creature();
	
	@Before
	public void before() {
		creature = new Creature("test", 2, 3, 100, true, .5, 15, 1000, 500, false, true, ancestor);
	}
	
	@Test public void testGetName() { Assert.assertEquals("test", creature.getName()); }
	@Test public void testGetId() { Assert.assertEquals(2, creature.getId()); }
	@Test public void testGetGeneration() { Assert.assertEquals(3, creature.getGeneration()); }
	@Test public void testGetWeight() { Assert.assertEquals(100, creature.getWeight()); }
	@Test public void testIsCarnivore() { Assert.assertEquals(true, creature.isCarnivore()); }
	@Test public void testGetIntelligence() { Assert.assertEquals(true, .5 == creature.getIntelligence()); }
	@Test public void testGetSpeed() { Assert.assertEquals(15, creature.getSpeed()); }
	@Test public void testGetFood() { Assert.assertEquals(1000, creature.getFood()); }
	@Test public void testGetHunger() { Assert.assertEquals(500, creature.getHunger()); }
	@Test public void testIsFamine() { Assert.assertEquals(false, creature.isFamine()); }
	@Test public void testHasAncestor() { Assert.assertEquals(true, creature.hasAncestor()); }
	@Test public void testGetAncestor() { Assert.assertEquals(ancestor, creature.getAncestor()); }	
	
	@Test
	public void testGetOldestAndParentNames() {
		creature = new Creature("child", 2, 3, 100, true, .5, 15, 1000, 500, false, true,
				   new Creature("parent", 2, 3, 100, true, .5, 15, 1000, 500, false, true,
				   new Creature("grandparent", 2, 3, 100, true, .5, 15, 1000, 500, false, false, null)));
		Assert.assertEquals("Parent: parent Oldest: grandparent", creature.getOldestAndParentNames());
	}
}
