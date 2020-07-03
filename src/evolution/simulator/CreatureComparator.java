package evolution.simulator;

import java.util.Comparator;

public class CreatureComparator implements Comparator<Creature>{
	public int compare(Creature a, Creature b) {
		if ((double) a.getFood()/a.getHunger() > (double) b.getFood()/b.getHunger()) { return 1; }
		else if ((double) b.getFood()/b.getHunger() > (double) a.getFood()/a.getHunger()) { return -1; }
		return 0;
	}
}
