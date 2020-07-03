package evolution.simulator.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import evolution.simulator.Creature;

public class TestCreatureStrings {
	
	Creature creature;
	
	@Test
	public void getWeightLetter_LessThan150_Returns_S() {
		int weight = 100;
		creature = new Creature("test", 2, 3, weight, true, .5, 15, 1000, 500, false, true, null);
		
		char letter = creature.getWeightLetter();
		assertEquals('S', letter);
	}
	
	@Test
	public void getWeightLetter_GreaterThanOrEqual150AndLessThan500_Returns_M() {
		int weight = 250;
		creature = new Creature("test", 2, 3, weight, true, .5, 15, 1000, 500, false, true, null);
		
		char letter = creature.getWeightLetter();
		assertEquals('M', letter);
	}
	
	@Test
	public void getWeightLetter_GreaterThanOrEqual500AndLessThan1000_Returns_L() {
		int weight = 800;
		creature = new Creature("test", 2, 3, weight, true, .5, 15, 1000, 500, false, true, null);
		
		char letter = creature.getWeightLetter();
		assertEquals('L', letter);
	}
	
	@Test
	public void getWeightLetter_GreaterThanOrEqual1000_Returns_H() {
		int weight = 1500;
		creature = new Creature("test", 2, 3, weight, true, .5, 15, 1000, 500, false, true, null);
		
		char letter = creature.getWeightLetter();
		assertEquals('H', letter);
	}
	
	@Test
	public void getSpeedLetter_LessThan15_Returns_S() {
		int speed = 10;
		creature = new Creature("test", 2, 3, 100, true, .5, speed, 1000, 500, false, true, null);
		
		char letter = creature.getSpeedLetter();
		assertEquals('S', letter);
	}
	
	@Test
	public void getSpeedLetter_GreaterThanOrEqual15_Returns_F() {
		int speed = 50;
		creature = new Creature("test", 2, 3, 100, true, .5, speed, 1000, 500, false, true, null);
		
		char letter = creature.getSpeedLetter();
		assertEquals('F', letter);
	}
	
	@Test
	public void getSpeedLetter_LessThan33_Returns_D() {
		double intelligence = .2;
		creature = new Creature("test", 2, 3, 100, true, intelligence, 10, 1000, 500, false, true, null);
		
		char letter = creature.getIntelligenceLetter();
		assertEquals('D', letter);
	}
	
	@Test
	public void getSpeedLetter_GreaterThanEqual33AndLessThan66_Returns_I() {
		double intelligence = .5;
		creature = new Creature("test", 2, 3, 100, true, intelligence, 10, 1000, 500, false, true, null);
		
		char letter = creature.getIntelligenceLetter();
		assertEquals('I', letter);
	}
	
	@Test
	public void getSpeedLetter_GreaterThanEqual66_Returns_G() {
		double intelligence = .8;
		creature = new Creature("test", 2, 3, 100, true, intelligence, 10, 1000, 500, false, true, null);
		
		char letter = creature.getIntelligenceLetter();
		assertEquals('G', letter);
	}
	
	@Test
	public void getFoodPreferenceLetter_Carnivore_Returns_C() {
		boolean carnivore = true;
		creature = new Creature("test", 2, 3, 100, carnivore, .5, 10, 1000, 500, false, true, null);
		
		char letter = creature.getFoodPreferenceLetter();
		assertEquals('C', letter);
	}
	
	@Test
	public void getFoodPreferenceLetter_Herbivore_Returns_H() {
		boolean carnivore = false;
		creature = new Creature("test", 2, 3, 100, carnivore, .5, 10, 1000, 500, false, true, null);
		
		char letter = creature.getFoodPreferenceLetter();
		assertEquals('H', letter);
	}
	
	@Test
	public void getFamineLetter_Famine_Returns_F() {
		boolean famine = true;
		creature = new Creature("test", 2, 3, 100, true, .5, 10, 1000, 500, famine, true, null);
		
		char letter = creature.getFamineLetter();
		assertEquals('F', letter);
	}
	
	@Test
	public void getFamineLetter_NoFamine_Returns_P() {
		boolean famine = false;
		creature = new Creature("test", 2, 3, 100, true, .5, 10, 1000, 500, famine, true, null);
		
		char letter = creature.getFamineLetter();
		assertEquals('P', letter);
	}
}
