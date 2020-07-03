package evolution.simulator;

import static java.util.Collections.max;
import static java.util.Collections.min;
import static java.util.Collections.reverseOrder;
import static java.util.Collections.sort;
import java.util.Random;

import java.util.ArrayList;

public class EvolutionSimulator {
	private ArrayList<Creature> creatures = new ArrayList<Creature>();
	private ArrayList<Creature> successful;
	private ArrayList<Creature> unsuccessful;
	private ArrayList<Creature> parents = new ArrayList<Creature>();
	private ArrayList<Creature> mostSuccessfulHistory = new ArrayList<Creature>();
	private ArrayList<Creature> leastSuccessfulHistory = new ArrayList<Creature>();
	private ArrayList<ArrayList<Creature>> allThatExisted = new ArrayList<ArrayList<Creature>>();
	private EvolutionStatisticsTracker statistics = new EvolutionStatisticsTracker();
	private int generation = 1;
	private int generationIncrement;
	private int creatureCount;
	private int surviveCount;
	private int dieCount;
	private int creatureCounter;
	private boolean famine = false;
	
	private CreatureComparator creatureComparator = new CreatureComparator();
	private Random random = new Random();
	
	public EvolutionSimulator(int creatureCount, int generationIncrement, double percentDie) {
		this.creatureCount = creatureCount;
		createCreatures();
		initCommon(generationIncrement, percentDie);
	}
	
	public EvolutionSimulator(ArrayList<Creature> creatures, int generationIncrement, double percentDie) {
		this.creatureCount = creatures.size();
		this.creatures = creatures;
		initCommon(generationIncrement, percentDie);
	}
	
	public void initCommon(int generationIncrement, double percentDie) {
		this.generationIncrement = generationIncrement;
		setSurviveAndDieCount(percentDie);
		setSuccessful();
		setUnsuccessful();
		updateMostSuccessfulHistory();
		updateLeastSuccessfulHistory();
		statistics.updateStatistics(this.creatures);
	}
	
	public ArrayList<Creature> getCreatures() { return this.creatures; }
	public ArrayList<Creature> getSuccessful() { return this.successful; }
	public ArrayList<Creature> getUnsuccessful() { return this.unsuccessful; }
	public ArrayList<Creature> getParents() { return this.parents; }
	public ArrayList<Creature> getMostSuccessfulHistory() { return this.mostSuccessfulHistory; }
	public ArrayList<Creature> getLeastSuccessfulHistory() { return this.leastSuccessfulHistory; }
	public EvolutionStatisticsTracker getStatistics() { return this.statistics; }
	public int getGenerationIncrement() { return this.generationIncrement; }
	public int getGeneration() { return this.generation; }
	public int getCreatureCount() { return this.creatureCount; }
	public int getSurviveCount() {return this.surviveCount; }
	public int getDieCount() { return this.dieCount; }
	public boolean getFamine() { return this.famine; }
	
	public void setGenerationIncrement(int increment) { this.generationIncrement = increment; }
	
	private void setSurviveAndDieCount(double percentDie) {
		setDieCount(percentDie);
		setSurviveCount(1-percentDie);
		if (this.surviveCount + this.dieCount != this.creatureCount) this.surviveCount += 1;
	}
	
	private void setSurviveCount(double percent) {
		if (percent < 0 || percent > 1) throw new IllegalArgumentException("percentSurvive must be a floating point value between 0 and 1 inclusive.");
		else this.surviveCount = (int) (percent*this.creatureCount);
	}
	
	private void setDieCount(double percent) {
		if (percent < 0 || percent > 1) throw new IllegalArgumentException("percentDie must be a floating point value between 0 and 1 inclusive.");
		else this.dieCount = (int) (percent*this.creatureCount);
	}
	
	private void setSuccessful() {
		this.successful = new ArrayList<Creature>();
		ArrayList<Creature> copy = copy(this.creatures);
		
		for (int i = 0; i < this.surviveCount; i++) {
			this.successful.add(max(copy, this.creatureComparator));
			copy.remove(max(copy, this.creatureComparator));
		}
		sort(this.successful, reverseOrder(this.creatureComparator));
	}
	
	private void setUnsuccessful() {
		this.unsuccessful = new ArrayList<Creature>();
		ArrayList<Creature> copy = copy(this.creatures);
		
		for (int i = 0; i < this.dieCount; i++) {
			this.unsuccessful.add(min(copy, this.creatureComparator));
			copy.remove(min(copy, this.creatureComparator));
		}
		sort(this.unsuccessful, reverseOrder(this.creatureComparator));
	}
	
	private void createCreatures() {
		this.creatureCounter = 1;
		for (int i = 0; i < this.creatureCount; i++) {
			this.creatures.add(new Creature(this.creatureCounter));
			this.creatureCounter++;
		}
	}
	
	private void createNextGeneration() {
		this.creatures = new ArrayList<Creature>();
		if (!this.successful.isEmpty()) {
			Creature mostSuccessful = max(this.successful, this.creatureComparator);
			this.creatures.add(mostSuccessful.copy());
			this.creatures.add(mostSuccessful.copy());
			
			for (Creature creature : this.successful) {
				this.creatures.add(creature.copy());
				this.creatures.add(creature.copy());
			}
		
			while (this.creatures.size() < this.creatureCount) {
				this.creatures.add(successful.get(random.nextInt(successful.size())).copy());
			}
			
			while (this.creatures.size() > this.creatureCount) {
				this.creatures.remove(random.nextInt(this.creatures.size()));
			}
		}
		this.generation++;
	}
	
	private void updateMostSuccessfulHistory() {
		Creature mostSuccessfulCurrent = max(this.creatures, this.creatureComparator);
		if (!this.mostSuccessfulHistory.isEmpty()) {
			Creature allTimeMostSuccessful = max(this.mostSuccessfulHistory, this.creatureComparator);
			if (mostSuccessfulCurrent.compareTo(allTimeMostSuccessful) >= 1) { 
				this.mostSuccessfulHistory.add(mostSuccessfulCurrent); 
				sort(this.mostSuccessfulHistory, reverseOrder());
			}
		}
		else this.mostSuccessfulHistory.add(mostSuccessfulCurrent);
	}
	
	private void updateLeastSuccessfulHistory() {
		Creature leastSuccessfulCurrent = min(this.creatures, this.creatureComparator);
		if (!this.leastSuccessfulHistory.isEmpty()) {
			Creature allTimeLeastSuccessful = min(this.leastSuccessfulHistory, this.creatureComparator);
			if (leastSuccessfulCurrent.compareTo(allTimeLeastSuccessful) <= -1) { 
				this.leastSuccessfulHistory.add(leastSuccessfulCurrent); 
				sort(this.leastSuccessfulHistory);
			}
		}
		else this.leastSuccessfulHistory.add(leastSuccessfulCurrent);
	}
	
	public void evolve()
	{
		createNextGeneration();
		this.parents = this.successful;
		
		mutateAll();
		
		updateFamineForCreatures();
		setSuccessful();
		setUnsuccessful();
		this.allThatExisted.add(copy(this.creatures));
		updateMostSuccessfulHistory();
		updateLeastSuccessfulHistory();
		statistics.updateStatistics(this.creatures);
	}
	
	private void mutateAll() {
		for (int i = 0; i < this.creatureCount; i++) {
			if (i > 3)
				this.creatures.get(i).evolve(this.creatureCounter, false); 
			else 
				this.creatures.get(i).evolve(this.creatureCounter, true); 
			this.creatureCounter++;
		}
	}
	
	public void toggleFamine() { this.famine = !this.famine; }
	
	private void updateFamineForCreatures() { 
		for (Creature creature : this.creatures) creature.setFamine(this.famine); 
	}
	
	public Creature findCreature(String name) {
		for (ArrayList<Creature> creatures : this.allThatExisted) {
			for (Creature creature : creatures) {
				if (creature.getName().equals(name)) return creature;
			}
		}
		return null;
	}
	
	private ArrayList<Creature> copy(ArrayList<Creature> creatures) {
		ArrayList<Creature> creaturesCopy = new ArrayList<Creature>();
		
		for (Creature creature : creatures) {
			creaturesCopy.add(
				new Creature(creature.getName(), 
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
							 creature.getAncestor()
			));
		}
		
		return creaturesCopy;
	}
}
