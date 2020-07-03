package evolution.simulator;

import java.util.Random;
import java.text.DecimalFormat;
import static evolution.simulator.CreatureUtilities.*;

public class Creature implements java.lang.Comparable<Creature>{
	
	private Random random = new Random();
	private DecimalFormat decimalFormat = new DecimalFormat("#.00");
	
	private String name;
	private int id;
	private int generation;
	private int weight;
	private boolean carnivore;
	private double intelligence;
	private int speed;
	private int food;
	private int hunger;
	private boolean famine = false;
	private boolean hasAncestor;
	private Creature ancestor = null;
	
	public Creature() {  } //only for testing
	
	public Creature(int id) {
		this.id = id;
		this.generation = 1;
		this.weight = random.nextInt(2000)+1;
		this.carnivore = random.nextBoolean();
		this.intelligence = random.nextDouble();
		this.speed = random.nextInt(100)+1;
		
		setFood();
		setHunger();
		updatesBasedOnIntelligence();
		updatesBasedOnFoodPreference();
		this.hasAncestor = false;
		this.ancestor = null;
		generateName();
	}
	
	public Creature(String name, int id, int generation, int weight, boolean carnivore, double intelligence, int speed, int food, int hunger, boolean famine,
					boolean hasAncestor, Creature ancestor) {
		this.name = name;
		this.id = id;
		this.generation = generation;
		this.weight = weight;
		this.carnivore = carnivore;
		this.intelligence = intelligence;
		this.speed = speed;
		this.food = food;
		this.hunger = hunger;
		this.famine = famine;
		this.hasAncestor = hasAncestor;
		this.ancestor = ancestor;
	}
	
	public String getName() { return this.name; }
	public int getId() { return this.id; }
	public int getGeneration() { return this.generation; }
	public int getWeight() { return this.weight; }
	public boolean isCarnivore() { return this.carnivore; }
	public double getIntelligence() { return this.intelligence; }
	public int getSpeed() { return this.speed; }
	public int getFood() { return this.food; }
	public int getHunger() { return this.hunger; }
	public boolean hasAncestor() { return this.hasAncestor; }
	public Creature getAncestor() { return this.ancestor; }
	public boolean isFamine() { return this.famine; }
	
	public String getOldestAndParentNames()
	{
		String oldestAndParentNames = "";
		Creature ancestor = this.ancestor;
		Creature oldest = ancestor;
		
		if (ancestor != null) oldestAndParentNames = "Parent: " + ancestor.getName();
		
		while (ancestor != null) {
			oldest = ancestor;
			ancestor = ancestor.getAncestor();
		}
		
		oldestAndParentNames += " Oldest: " + oldest.getName();
		
		return oldestAndParentNames;
	}
	
	public void setFood() { 
		this.food = (int)(5*Math.sqrt(this.speed)*this.weight);
		if (this.famine) { 
			if (this.weight < 150) this.food = (int)(3*Math.sqrt(this.speed)*this.weight);
			this.food /= 2;
		}
		
	}
	public void setHunger() { this.hunger = (int)Math.pow(this.weight, 1.2) + (this.weight*this.speed)/2; }
	public void setFamine(boolean bool) { this.famine = bool; }
	
	public void evolve(int newId, boolean minimalMutation) {
		advanceGeneration(newId);
		if (minimalMutation) mutate(.25, random);
		else mutate(1, random);
	}
	
	public void advanceGeneration(int newId) {
		this.ancestor = this.copy();
		this.id = newId;
		this.hasAncestor = true;
		this.generation++;
	}
	
	public void mutate(double multiplier, Random random) {
		this.weight = randomIntegerMutation(multiplier, random, this.weight, 2000, (this.weight/4)+1);
		this.speed = randomIntegerMutation(multiplier, random, this.speed, 100, 5);
		setFood();
		setHunger();
		
		randomIntelligenceMutation(multiplier, random);
		updatesBasedOnIntelligence();
		randomCarnivoreMutation(multiplier, random);
		updatesBasedOnFoodPreference();
		
		generateName();
	}
	
	public int randomIntegerMutation(double multiplier, Random random, int value, int range, int plusMinus) {
		if (random.nextInt(100) < multiplier*4) { value = random.nextInt(range)+1; }
		else {
			int plus = (int) multiplier*random.nextInt(plusMinus);
			int minus = (int) multiplier*random.nextInt(plusMinus);
			value += plus - minus;
		}
		
		if (value < 1) value = 1;
		
		return value;
	}
	
	public void randomIntelligenceMutation(double multiplier, Random random) {
		if (random.nextInt(100) <= multiplier*5) this.intelligence = random.nextDouble();
		else {
			double plus = multiplier*(random.nextDouble()/100);
			double minus = multiplier*(random.nextDouble()/100);
			this.intelligence += plus - minus;
		}
		
		if (this.weight < 150) this.intelligence /= 2;
		if (this.weight > 500) this.intelligence *= 1.2;
		
		if (this.intelligence > 1) this.intelligence = 1;
		if (this.intelligence < 0) this.intelligence = 0;
	}
	
	public void randomCarnivoreMutation(double multiplier, Random random) {
		if (random.nextInt(100) <= multiplier*5) this.carnivore = !this.carnivore;
	}
	
	public void updatesBasedOnIntelligence()
	{
		this.food = (int) (this.food*(this.intelligence + 1));
	}
	
	public void updatesBasedOnFoodPreference()
	{
		if (this.carnivore) { 
			this.intelligence *= 1.2;
			if (this.intelligence > 1) this.intelligence = 1;
			this.speed += 5;
		}
	}
	
	public Creature copy() {
		return new Creature(this.name, this.id, this.generation, this.weight, this.carnivore, this.intelligence, 
				this.speed, this.food, this.hunger, this.famine, this.hasAncestor, this.ancestor); 
	}
	
	public void generateName()
	{
		this.name = "";
		
		this.name += getWeightLetter();
		this.name += getSpeedLetter();
		this.name += getIntelligenceLetter();
		this.name += getFoodPreferenceLetter();
		this.name += getFamineLetter();
		this.name += "-" + this.id + "-";
		this.name += this.generation;
	}
	
	public char getWeightLetter() {
		if (this.weight < 150) return 'S';
		else if (this.weight < 500) return 'M';
		else if (this.weight < 1000) return 'L';
		else return 'H';
	}
	
	public char getSpeedLetter() {
		if (this.speed < 15) return 'S';
		else return 'F';
	}
	
	public char getIntelligenceLetter() {
		if (this.intelligence < .33 ) return 'D';
		else if (this.intelligence >= .33 && this.intelligence < .66) return 'I';
		else return 'G';
	}
	
	public char getFoodPreferenceLetter() {
		if (this.carnivore) return 'C';
		else return 'H';
	}
	
	public char getFamineLetter() {
		if (this.famine) return 'F';
		else return 'P';
	}
	
	public int compareTo(Creature creature)
	{
		if ((double) this.getFood()/this.getHunger() > (double) creature.getFood()/creature.getHunger()) { return 1; }
		else if ((double) creature.getFood()/creature.getHunger() > (double) this.getFood()/this.getHunger()) { return -1; }
		return 0;
	}
	
	public String toString() {
		int intelligenceInt = (int) (this.intelligence*100);
		
		if (this.hasAncestor) {
			int ancestorIntelligenceInt = (int) (this.ancestor.getIntelligence()*100);
			
			return createColumn(this.name, null, 70, this.getOldestAndParentNames()) +
				   createColumn(Boolean.toString(this.famine), null, 20) +
				   createColumn(decimalFormat.format((double)this.food/this.hunger), null, 40,
						   decimalFormat.format((double)this.food/this.hunger-(double)this.ancestor.getFood()/this.ancestor.getHunger())) +
				   createColumn(Integer.toString(this.weight), "lbs", 40,
						   Integer.toString((this.weight-this.ancestor.getWeight()))) +
				   createColumn(Boolean.toString(this.carnivore), null, 30,
				   			Boolean.toString(this.ancestor.isCarnivore())) +
				   createColumn(Integer.toString(intelligenceInt), "IQ", 30,
							Integer.toString(intelligenceInt-ancestorIntelligenceInt)) +
				   createColumn(Integer.toString(this.speed), "mi/hr", 30,
						   Integer.toString(this.speed-this.ancestor.getSpeed())) +
				   createColumn(Integer.toString(this.food), "cal", 30,
						   Integer.toString(this.food-this.ancestor.getFood())) +
				   createColumn(Integer.toString(this.hunger), "cal", 30,
						   Integer.toString(this.hunger-this.ancestor.getHunger()));
		}
		
		return createColumn(this.name, null, 70) +
				   createColumn(Boolean.toString(this.famine), null, 20) +
				   createColumn(decimalFormat.format((double)this.food/this.hunger), null, 40) +
				   createColumn(Integer.toString(this.weight), "lbs", 40) +
				   createColumn(Boolean.toString(this.carnivore), null, 30) +
				   createColumn(Integer.toString(intelligenceInt), "IQ", 30) +
				   createColumn(Integer.toString(this.speed), "mi/hr", 30) +
				   createColumn(Integer.toString(this.food), "cal", 30) +
				   createColumn(Integer.toString(this.hunger), "cal", 30); 
					   
			   
	}

}
