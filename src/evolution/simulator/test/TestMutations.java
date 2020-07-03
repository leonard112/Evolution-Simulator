package evolution.simulator.test;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import evolution.simulator.Creature;

public class TestMutations {
	
	public class MockRandom extends Random{
		private int hardCodedInt = 1;
		private double hardCodedDouble = .5;
		
		public MockRandom() { }
		
		public int nextInt(int i) { return this.hardCodedInt; }
		public void nextIntReturns(int i) { this.hardCodedInt = i; }
		public double nextDouble() { return this.hardCodedDouble; }
		public void nextDoubleReturns(double i) { this.hardCodedDouble = i; }
	}
	
	MockRandom mockRandom = new MockRandom();
	
	Creature creature;
	 
	@Before
	public void before() {
		creature = new Creature("test", 2, 3, 100, true, .5, 15, 1000, 500, false, true, null);
	}
	 
	@Test
	public void testRandomIntegerMutationNormal() {
		double multiplier = 1;
		mockRandom.nextIntReturns(100);
		
		int value = creature.randomIntegerMutation(multiplier, mockRandom, 100, 1000, 10);
		Assert.assertEquals(100, value);
	}
	
	@Test
	public void testRandomIntegerMutationSignificant() {
		double multiplier = 1;
		mockRandom.nextIntReturns(0);
		
		int value = creature.randomIntegerMutation(multiplier, mockRandom, 100, 1000, 10);
		Assert.assertEquals(1, value);
	}
	
	@Test
	public void testRandomIntelligenceMutationLight() {
		double multiplier = 1;
		mockRandom.nextIntReturns(100);
		
		creature.randomIntelligenceMutation(multiplier, mockRandom);
		Assert.assertEquals(true, .25 == creature.getIntelligence());
	}
	
	@Test
	public void testRandomIntelligenceMutationMedium() {
		creature = new Creature("test", 2, 3, 250, true, .5, 15, 1000, 500, false, true, null);
		double multiplier = 1;
		mockRandom.nextIntReturns(100);
		
		creature.randomIntelligenceMutation(multiplier, mockRandom);
		Assert.assertEquals(true, .5 == creature.getIntelligence());
	}
	
	@Test
	public void testRandomIntelligenceMutationHeavy() {
		creature = new Creature("test", 2, 3, 1000, true, .5, 15, 1000, 500, false, true, null);
		
		double multiplier = 1;
		mockRandom.nextIntReturns(100);
		
		creature.randomIntelligenceMutation(multiplier, mockRandom);
		Assert.assertEquals(true, .6 == creature.getIntelligence());
	}
	
	@Test
	public void testRandomIntelligenceMutationSignificant() {
		creature = new Creature("test", 2, 3, 250, true, .5, 15, 1000, 500, false, true, null);
		
		double multiplier = 1;
		mockRandom.nextIntReturns(0);
		mockRandom.nextDoubleReturns(.9);
		
		creature.randomIntelligenceMutation(multiplier, mockRandom);
		Assert.assertEquals(true, .9 == creature.getIntelligence());
	}
	
	@Test
	public void testRandomIntelligenceMutationIsNotLessThanZero() {
		creature = new Creature("test", 2, 3, 100, true, .5, 15, 1000, 500, false, true, null);
		
		double multiplier = 1;
		mockRandom.nextIntReturns(0);
		mockRandom.nextDoubleReturns(-.1);
		
		creature.randomIntelligenceMutation(multiplier, mockRandom);
		Assert.assertEquals(true, 0 == creature.getIntelligence());
	}
	
	@Test
	public void testRandomIntelligenceMutationIsNotGreaterThanOne() {
		creature = new Creature("test", 2, 3, 250, true, .5, 15, 1000, 500, false, true, null);
		
		double multiplier = 1;
		mockRandom.nextIntReturns(0);
		mockRandom.nextDoubleReturns(1.1);
		
		creature.randomIntelligenceMutation(multiplier, mockRandom);
		Assert.assertEquals(true, 1 == creature.getIntelligence());
	}
	
	@Test 
	public void testRandomCarnivoreMutationOccurs() {
		double multiplier = 1;
		mockRandom.nextIntReturns(0);
		
		creature.randomCarnivoreMutation(multiplier, mockRandom);
		Assert.assertEquals(false, creature.isCarnivore());
	}
	
	@Test 
	public void testRandomCarnivoreMutationDoesntOccur() {
		double multiplier = 1;
		mockRandom.nextIntReturns(100);
		
		creature.randomCarnivoreMutation(multiplier, mockRandom);
		Assert.assertEquals(true, creature.isCarnivore());
	}
	
	@Test
	public void advanceGenerationIdGetSetToParameter() {
		creature.advanceGeneration(3);
		Assert.assertEquals(3, creature.getId());
	}
	
	@Test
	public void advanceGenerationTurnsCurrentCreatureIntoAncestor() {
		Creature ancestor = new Creature(creature.getName(), 
							 			 creature.getId(),
							 			 creature.getGeneration(),
							 			 creature.getWeight(),
							 			 creature.isCarnivore(),
							 			 creature.getIntelligence(),
							 			 creature.getSpeed(),
							 			 creature.getFood(),
							 			 creature.getHunger(),
							 			 creature.isFamine(),
							 			 creature.hasAncestor(),
							 			 creature.getAncestor());
		
		creature.advanceGeneration(3);
		Assert.assertEquals(ancestor.getName(), creature.getAncestor().getName());
		Assert.assertEquals(ancestor.getId(), creature.getAncestor().getId());
		Assert.assertEquals(ancestor.getGeneration(), creature.getAncestor().getGeneration());
		Assert.assertEquals(ancestor.getWeight(), creature.getAncestor().getWeight());
		Assert.assertEquals(ancestor.isCarnivore(), creature.getAncestor().isCarnivore());
		Assert.assertEquals(true, ancestor.getIntelligence() == creature.getAncestor().getIntelligence());
		Assert.assertEquals(ancestor.getSpeed(), creature.getAncestor().getSpeed());
		Assert.assertEquals(ancestor.getFood(), creature.getAncestor().getFood());
		Assert.assertEquals(ancestor.getHunger(), creature.getAncestor().getHunger());
		Assert.assertEquals(ancestor.isFamine(), creature.getAncestor().isFamine());
		Assert.assertEquals(ancestor.hasAncestor(), creature.getAncestor().hasAncestor());
		Assert.assertEquals(ancestor.getAncestor(), creature.getAncestor().getAncestor());
	}
	
	@Test
	public void advanceGenerationSetsHasAncestorTrue() {
		creature.advanceGeneration(3);
		Assert.assertEquals(true, creature.hasAncestor());
	}
	
	@Test
	public void advanceGenerationIncementsGenerationByOne() {
		creature.advanceGeneration(3);
		Assert.assertEquals(4, creature.getGeneration());
	}
	
	@Test
	public void testUpdatesBasedOnIntelligenceGreaterThan100() {
		creature.updatesBasedOnIntelligence();
		Assert.assertEquals(1500, creature.getFood());
	}
	
	@Test
	public void testUpdatesBasedOnFoodPreferenceForCarnivore() {
		creature.updatesBasedOnFoodPreference();
		Assert.assertEquals(true, .6 == creature.getIntelligence());
		Assert.assertEquals(20, creature.getSpeed());
	}
	
	@Test
	public void testUpdatesBasedOnFoodPreferenceForHerbivore() {
		creature = new Creature("test", 2, 3, 100, false, .5, 15, 1000, 500, false, true, null);
		
		creature.updatesBasedOnFoodPreference();
		Assert.assertEquals(true, .5 == creature.getIntelligence());
		Assert.assertEquals(15, creature.getSpeed());
	}
	
	@Test
	public void testUpdatesBasedOnFoodPreferenceForCarnivoreIntelligenceNotGreaterThanOne() {
		creature = new Creature("test", 2, 3, 100, true, 1.1, 15, 1000, 500, false, true, null);
		
		creature.updatesBasedOnFoodPreference();
		Assert.assertEquals(true, 1 == creature.getIntelligence());
		Assert.assertEquals(20, creature.getSpeed());
	}
}
