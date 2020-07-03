package evolution.simulator.evolution.simulator.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import static java.util.Collections.shuffle;

import evolution.simulator.EvolutionSimulator;
import evolution.simulator.Creature;

public class TestEvolutionSimulatorSettersAndMutators {
	
	public static EvolutionSimulator simulator;
	public static ArrayList<Creature> creatures;
	
	@Before
	public void before() {
		creatures = new ArrayList<Creature>();
		creatures.add(new Creature("1", 1, 1, 1, true, 1, 1, 1, 5, false, true, null));
		creatures.add(new Creature("2", 2, 1, 1, true, 1, 1, 2, 5, false, true, null));
		creatures.add(new Creature("3", 3, 1, 1, true, 1, 1, 3, 5, false, true, null));
		creatures.add(new Creature("4", 4, 1, 1, true, 1, 1, 4, 5, false, true, null));
		creatures.add(new Creature("5", 5, 1, 1, true, 1, 1, 5, 5, false, true, null));
		creatures.add(new Creature("6", 6, 1, 1, true, 1, 1, 6, 5, false, true, null));
		creatures.add(new Creature("7", 7, 1, 1, true, 1, 1, 7, 5, false, true, null));
		creatures.add(new Creature("8", 8, 1, 1, true, 1, 1, 8, 5, false, true, null));
		creatures.add(new Creature("9", 9, 1, 1, true, 1, 1, 9, 5, false, true, null));
		creatures.add(new Creature("10", 10, 1, 1, true, 1, 1, 10, 5, false, true, null));
		shuffle(creatures);
	}
	
	@Test
	public void initializesWithTenNewCreatures() {
		simulator = new EvolutionSimulator(10, 1, .66);
		assertEquals(10, simulator.getCreatures().size());
	}
	
	@Test
	public void initializesWithCorrectSurviveCount() {
		simulator = new EvolutionSimulator(10, 1, .66);
		assertEquals(4, simulator.getSurviveCount());
	}
	
	@Test
	public void initializesWithCorrectDieCount() {
		simulator = new EvolutionSimulator(10, 1, .66);
		assertEquals(6, simulator.getDieCount());
	}
	
	@Test
	public void initializesWithCorrect50percentSuccessfulSorted() {
		simulator = new EvolutionSimulator(creatures, 1, .5);
		
		assertEquals(10, simulator.getSuccessful().get(0).getId());
		assertEquals(9, simulator.getSuccessful().get(1).getId());
		assertEquals(8, simulator.getSuccessful().get(2).getId());
		assertEquals(7, simulator.getSuccessful().get(3).getId());
		assertEquals(6, simulator.getSuccessful().get(4).getId());
		
		assertEquals(5, simulator.getSuccessful().size());
	}
	
	@Test
	public void initializesWithCorrect50percentUnsuccessfulSorted() {
		simulator = new EvolutionSimulator(creatures, 1, .5);
		
		assertEquals(5, simulator.getUnsuccessful().get(0).getId());
		assertEquals(4, simulator.getUnsuccessful().get(1).getId());
		assertEquals(3, simulator.getUnsuccessful().get(2).getId());
		assertEquals(2, simulator.getUnsuccessful().get(3).getId());
		assertEquals(1, simulator.getUnsuccessful().get(4).getId());
		
		assertEquals(5, simulator.getUnsuccessful().size());
	}
	
	@Test
	public void initializesWithCorrect33percentSuccessfulSorted() {
		simulator = new EvolutionSimulator(creatures, 1, .66);
		
		assertEquals(10, simulator.getSuccessful().get(0).getId());
		assertEquals(9, simulator.getSuccessful().get(1).getId());
		assertEquals(8, simulator.getSuccessful().get(2).getId());
		assertEquals(7, simulator.getSuccessful().get(3).getId());
		
		assertEquals(4, simulator.getSuccessful().size());
	}
	
	@Test
	public void initializesWithCorrect66percentUnsuccessfulSorted() {
		simulator = new EvolutionSimulator(creatures, 1, .66);
		
		assertEquals(6, simulator.getUnsuccessful().get(0).getId());
		assertEquals(5, simulator.getUnsuccessful().get(1).getId());
		assertEquals(4, simulator.getUnsuccessful().get(2).getId());
		assertEquals(3, simulator.getUnsuccessful().get(3).getId());
		assertEquals(2, simulator.getUnsuccessful().get(4).getId());
		assertEquals(1, simulator.getUnsuccessful().get(5).getId());
		
		assertEquals(6, simulator.getUnsuccessful().size());
	}
	
	@Test
	public void correctlyUpdatesMostSuccessfulHistoryOnInit() {
		simulator = new EvolutionSimulator(creatures, 1, .66);
		
		assertEquals(10, simulator.getMostSuccessfulHistory().get(0).getId());
	}
	
	@Test
	public void correctlyUpdatesLeastSuccessfulHistoryOnInit() {
		simulator = new EvolutionSimulator(creatures, 1, .66);
		
		assertEquals(1, simulator.getLeastSuccessfulHistory().get(0).getId());
	}
	
	@Test
	public void evolvingCreatesNewCreatures() {
		simulator = new EvolutionSimulator(10, 1, .66);
		
		simulator.evolve();
		ArrayList<Creature> newCreatures = simulator.getCreatures();
		for (Creature creature : newCreatures) {
			assertTrue(creature.getId() > 10 && creature.getId() <= 20);
		}
	}
	
	@Test
	public void evolvingCreaturesGivesNewCreaturesCorrectGenerationNumber66() {
		simulator = new EvolutionSimulator(10, 1, .66);
		
		simulator.evolve();
		for (Creature creature : simulator.getCreatures()) {
			assertEquals(2, creature.getGeneration());
		}
	}
	
	@Test
	public void evolvingCreaturesCreatesTenUniqueCreaturesWithCorrectIds66() {
		simulator = new EvolutionSimulator(10, 1, .66);
		int idCounter = 0;
		
		simulator.evolve();
		for (Creature creature : simulator.getCreatures()) {
			for (int i = 10; i < 21; i++) {
				if (creature.getId() == i) idCounter++;
			}
		}
		assertEquals(10, idCounter);
	}
	
	@Test
	public void evolvingCreaturesGivesNewCreaturesCorrectGenerationNumber22() {
		simulator = new EvolutionSimulator(10, 1, .22);
		
		simulator.evolve();
		for (Creature creature : simulator.getCreatures()) {
			assertEquals(2, creature.getGeneration());
		}
	}
	
	@Test
	public void evolvingCreaturesCreatesTenUniqueCreaturesWithCorrectIds22() {
		simulator = new EvolutionSimulator(10, 1, .22);
		int idCounter = 0;
		
		simulator.evolve();
		for (Creature creature : simulator.getCreatures()) {
			for (int i = 10; i < 21; i++) {
				if (creature.getId() == i) idCounter++;
			}
		}
		assertEquals(10, idCounter);
	}
}
