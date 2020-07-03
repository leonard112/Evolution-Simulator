package evolution.simulator;

import java.util.ArrayList;
import static evolution.simulator.CreatureUtilities.*;

public class EvolutionStatisticsTracker {
	private int smallSurvivor = 0;
	private int mediumSurvivor = 0;
	private int largeSurvivor = 0;
	private int hugeSurvivor = 0;
	private int carnivoreSurvivor = 0;
	private int herbivoreSurvivor = 0;
	private int smartSurvivor = 0;
	private int dumbSurvivor = 0;
	private int fastSurvivor = 0;
	private int slowSurvivor = 0;
	
	private int smallDead = 0;
	private int mediumDead = 0;
	private int largeDead = 0;
	private int hugeDead = 0;
	private int carnivoreDead = 0;
	private int herbivoreDead = 0;
	private int smartDead = 0;
	private int dumbDead = 0;
	private int fastDead = 0;
	private int slowDead = 0;
	
	private void addSmallSurvivor() { this.smallSurvivor++; }
	private void addMediumSurvivor() { this.mediumSurvivor++; }
	private void addLargeSurvivor() { this.largeSurvivor++; }
	private void addHugeSurvivor() { this.hugeSurvivor++; }
	private void addCarnivoreSurvivor() { this.carnivoreSurvivor++; }
	private void addHerbivoreSurvivor() { this.herbivoreSurvivor++; }
	private void addSmartSurvivor() { this.smartSurvivor++; }
	private void addDumbSurvivor() { this.dumbSurvivor++; }
	private void addFastSurvivor() { this.fastSurvivor++; }
	private void addSlowSurvivor() { this.slowSurvivor++; }
	
	private void addSmallDead() { this.smallDead++; }
	private void addMediumDead() { this.mediumDead++; }
	private void addLargeDead() { this.largeDead++; }
	private void addHugeDead() { this.hugeDead++; }
	private void addCarnivoreDead() { this.carnivoreDead++; }
	private void addHerbivoreDead() { this.herbivoreDead++; }
	private void addSmartDead() { this.smartDead++; }
	private void addDumbDead() { this.dumbDead++; }
	private void addFastDead() { this.fastDead++; }
	private void addSlowDead() { this.slowDead++; }
	
	public void updateStatistics(ArrayList<Creature> creatures) {
		int creatureCount = creatures.size();
		for (int i = 0; i < creatureCount; i++) {
			if (i < creatureCount/2-1) { addSurvivorStatistic(creatures.get(i)); }
			else { addDeadStatistic(creatures.get(i)); }
		}
	}
	
	public void addSurvivorStatistic(Creature creature) {
		if (creature.getWeight() < 150) { this.addSmallSurvivor(); }
		else if (creature.getWeight() < 500) { this.addMediumSurvivor(); }
		else if (creature.getWeight() < 1000) { this.addLargeSurvivor(); }
		else { this.addHugeSurvivor(); }
		
		if (creature.isCarnivore()) { this.addCarnivoreSurvivor(); }
		else { this.addHerbivoreSurvivor(); }
		
		if (creature.getIntelligence() < 50) { this.addDumbSurvivor(); }
		else { this.addSmartSurvivor(); }
		
		if (creature.getSpeed() < 15) { this.addSlowSurvivor(); }
		else { this.addFastSurvivor(); }
	}
	
	public void addDeadStatistic(Creature creature) {
		if (creature.getWeight() < 150) { this.addSmallDead(); }
		else if (creature.getWeight() < 500) { this.addMediumDead(); }
		else if (creature.getWeight() < 1000) { this.addLargeDead(); }
		else { this.addHugeDead(); }
		
		if (creature.isCarnivore()) { this.addCarnivoreDead(); }
		else { this.addHerbivoreDead(); }
		
		if (creature.getIntelligence() < 50) { this.addDumbDead(); }
		else { this.addSmartDead(); }
		
		if (creature.getSpeed() < 15) { this.addSlowDead(); }
		else { this.addFastDead(); }
	}
	
	private String formatStatistic(String label, int survivor, int dead, int total) {
		String formatLabel = "%-20s";
		String formatStatistic = "%-60s";
		int survivorPercent = (int) (((double) survivor/total)*100);
		int deadPercent = (int) (((double) dead/total)*100);
		return String.format(formatLabel, label + GREEN) + String.format(formatStatistic, "✓:  " + survivor + " (" + survivorPercent + "%)" +
							 BLUE + " + " + RED + "X: " + dead +  " (" + deadPercent + "%) " + BLUE + " = " + total);
	}
	
	public String toString() {
		return formatStatistic("Small: ", this.smallSurvivor, this.smallDead, this.smallSurvivor + this.smallDead) + 
			   formatStatistic("| Carnivore: ", this.carnivoreSurvivor, this.carnivoreDead, this.carnivoreSurvivor + this.carnivoreDead) + 
			   formatStatistic("| Smart: ", this.smartSurvivor, this.smartDead, this.smartSurvivor + this.smartDead) + 
			   formatStatistic("| Fast: ", this.fastSurvivor, this.fastDead, this.fastSurvivor + this.fastDead) + "\n" +
			   formatStatistic("Medium: ", this.mediumSurvivor, this.mediumDead, this.mediumSurvivor + this.mediumDead) +
			   formatStatistic("| Herbivore: ", this.herbivoreSurvivor, this.herbivoreDead, this.herbivoreSurvivor + this.herbivoreDead) +
			   formatStatistic("| Dumb: ", this.dumbSurvivor, this.dumbDead, this.dumbSurvivor + this.dumbDead) + 
			   formatStatistic("| Slow: ", this.slowSurvivor, this.slowDead, this.slowSurvivor + this.slowDead) + "\n" +
			   formatStatistic("Large: ", this.largeSurvivor, this.largeDead, this.largeSurvivor + this.largeDead) + "\n" + 
			   formatStatistic("Huge: ", this.hugeSurvivor, this.hugeDead, this.hugeSurvivor + this.hugeDead);
	}
}
