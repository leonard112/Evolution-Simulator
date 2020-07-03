package evolution.simulator;

import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.ArrayList;
import static java.util.Collections.sort;
import static java.util.Collections.reverseOrder;
import static java.util.Collections.max;
import static java.util.Collections.min;
import static evolution.simulator.CreatureUtilities.*;

public class Main {
	
	public static DecimalFormat decimalFormat = new DecimalFormat("#.00");
	public static CreatureComparator creatureComparator = new CreatureComparator();
	public static EvolutionSimulator simulator;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String command[];
		String input;
		
		System.out.println(PURPLE_BOLD + "Wecome to the Evolution Simulator!\n" + RESET);
		int generationIncrement = getPositiveInteger("Please enter the number of generations you want to advance each time you let the creatures evolve: ", false, true);
		int creatureCount = getPositiveInteger("Please enter the number of creatures that you want (must be EVEN): ", true, true);
		double percentDie = getPercentageFromIntegerInput("Please Enter the percentage of creatures that you would like to die each generation (INTEGER 0-100): ");
		
		simulator = new EvolutionSimulator(creatureCount, generationIncrement, percentDie);
		
		boolean continueLoop = true;
		
		do {
			System.out.println();
			System.out.print(BLACK_BOLD + "COMMAND: " + RESET);
			input = scanner.nextLine();
			System.out.println();
			
			command = input.split(" ", 2);
			
			switch(command[0]) { 
				case "e":
					if (command.length == 1) {
						System.out.println("Advancing to generation " + (simulator.getGeneration() + simulator.getGenerationIncrement()) + " ...");
						for (int i = 0; i < simulator.getGenerationIncrement(); i++) { simulator.evolve(); }
						System.out.println("done.");
						System.out.println();
						showThisGeneration();
					}
					else { printRed("The \"e\" command takes no arguments."); }
					break;
				case "sc":
					if (command.length == 1) { showThisGeneration(); }
					else { printRed("The \"sc\" command takes no arguments."); }
					break;
				case "help":
					if (command.length == 1) { showCommands(); }
					else { printRed("The \"help\" command takes no arguments."); }
					break;
				case "va":
					if (command.length == 2) { viewAncestors(command[1]); }
					else { printRed("The \"va\" command requires a creature's name as an argument."); }
					break;
				case "avg":
					if (command.length == 1) {
						printHeader("GENERATION AVERAGE:", CYAN_BOLD);
						showAverage(simulator.getCreatures(), CYAN);
					}
					else { printRed("The \"avg\" command takes no arguments."); }
					break;
				case "ms":
					if (command.length == 1) {
						printHeader("ALL TIME MOST SUCCESSFUL:", GREEN_BOLD);
						printCreatureTableHeading(GREEN);
						showCreatures(simulator.getMostSuccessfulHistory(), GREEN);
					}
					else { printRed("The \"ms\" command takes no arguments."); }
					break;
				case "ls":
					if (command.length == 1) {
						printHeader("ALL TIME LEAST SUCCESSFUL: ", RED_BOLD);
						printCreatureTableHeading(RED);
						showCreatures(simulator.getLeastSuccessfulHistory(), RED);
					}
					else { printRed("The \"ls\" command takes no arguments."); }
					break;
				case "ft":
					if (command.length == 1) { showLimitedFamilyTree(); }
					else { printRed("The \"ft\" command takes no arguments."); }
					break;
				case "stats":
					if (command.length == 1) { showStatistics(); }
					else { printRed("The \"stats\" command takes no arguments."); }
					break;
				case "tf":
					if (command.length == 1) { 
						simulator.toggleFamine();
						System.out.println("Famine will be set to \"" +  BLACK_BOLD + simulator.getFamine() +  RESET + "\" for the generations following this one.");
					}
					else { printRed("The \"tf\" command takes no arguments."); }
					break;
				case "geninc":
					if (command.length == 2) { setGenerationIncrement(command[1]); }
					else { printRed("The \"geninc\" command requires exactly one NON-ZERO and NON-NEGATIVE integer paramter"); }
					break;
				case "reset":
					if (command.length == 1) {
						System.out.println("Not Implemented yet.");
					}
					else { printRed("The \"reset\" command takes no arguments."); }
				case "exit":
					if (command.length == 1) { continueLoop = false; }
					else { printRed("The \"exit\" command takes no arguments."); }
					break;
				case "":
					break;
				default:
					System.out.println("Command Not found.");
					break;
			}
		} while(continueLoop);
	}
	
	public static void setGenerationIncrement(String increment) {
		try {
			simulator.setGenerationIncrement(Integer.parseInt(increment));
		}
		catch(NumberFormatException numberFormatException) { 
			printRed("Generation increment must be a NON-ZERO and NON-NEGATIVE integer.");
		}
	}
	
	public static void showCommands() {
		printHeader("COMMANDS:", BLACK_BOLD);
		
		showCommand("avg", "See the averages for THIS generation.");
		showCommand("e", "Evolve you creatures by the generation increment you specified.");
		showCommand("ft", "show a limited family tree that goes back one generation.");
		showCommand("geninc <positive non-zero integer>", "Set generation increment to a NON-ZERO and NON-NEGATIVE integer.");
		showCommand("help", "Desribe and show available commands.");
		showCommand("ls", "See the all time LEAST successful creaures.");
		showCommand("ms", "See the all time MOST successful creatures.");		
		showCommand("sc", "Show the creatures that are part of this generation.");
		showCommand("stats", "Show statistics for all generations.");
		showCommand("tf", "Toggle famine. This affects all generations following the current one.");
		showCommand("va <creature-name>", "View all of a creatures ancestors.");
	}
	
	public static void showCommand(String command, String description) {
		String format = "%-60s%s";
		System.out.println(String.format(format, BLACK_BOLD + command + RESET, "") + description);
	}
	
	public static void showStatistics() {
		printHeader("STATISTICS:", BLUE_BOLD);
		System.out.println(BLUE + simulator.getStatistics().toString() + RESET);
	}
	
	public static void showCreatures(ArrayList<Creature> creatures, String color) {
		for (Creature creature : creatures) {
			System.out.println(color + creature.toString() + RESET);
		}
	}
	
	public static void showThisGeneration() {
		printHeader("GENERATION:" + simulator.getGeneration(), BLUE_BOLD);
		printCreatureTableHeading(BLUE_BOLD);
		showGenerationAndSurvival();
	}
	
	public static void showGenerationAndSurvival()
	{
		if (!simulator.getCreatures().isEmpty()) {
			for (Creature creature : simulator.getSuccessful()) {
				System.out.println(GREEN_BACKGROUND + BLACK_BOLD + creature.toString() + RESET);
			}
			for (Creature creature : simulator.getUnsuccessful()) {
				System.out.println(RED_BACKGROUND + WHITE_BOLD + creature.toString() + RESET);
			}
		}
		else printRed("No creatures to display... They all must have died.");
	}
	
	public static void showLimitedFamilyTree() {
		if (!simulator.getParents().isEmpty())  {
			printHeader("LIMITED FAMILY TREE: ", PURPLE);
			for (Creature parent : simulator.getParents()) {
				System.out.print(PURPLE + "Parent: " + parent.getName() + "\tChildren: " + RESET);
				for (int i = 0; i < simulator.getCreatureCount(); i++) {
					Creature creature = simulator.getCreatures().get(i);
					if (simulator.getCreatures().get(i).getAncestor().getName().equals(parent.getName())) {
						if (i < simulator.getCreatureCount()/2-1) {
							System.out.print(PURPLE + " " + GREEN + creature.getName()  + RESET);
						}
						else {
							System.out.print(PURPLE + " " + RED + creature.getName()  + RESET);
						}
					}
				}
				System.out.println();
			}
		}
		else { printRed("No family tree is available yet."); }
	}
	
	public static void viewAncestors(String name) {
		Creature creature = simulator.findCreature(name);
		if (creature != null) {
			printHeader("CREATURE:", BLUE_BOLD);
			System.out.println(BLUE + creature.toString() + RESET);
			printHeader("ANCESTORS:", PURPLE_BOLD);
			if (creature.hasAncestor()) {
				Creature ancestor = creature.getAncestor();
				while (ancestor != null) {
					System.out.println(PURPLE + ancestor.toString() + RESET);
					ancestor = ancestor.getAncestor();
				}
			}
			else { System.out.println(RED_BOLD + "Creature " + name + " has no ancestors." + RESET); }
		}
		else { System.out.println(RED_BOLD + "Invalid name entered." + RESET); }
	}
	
	public static void showAverage(ArrayList<Creature> creatures, String color) {
		int size = creatures.size();
		
		int totalWeight = 0;
		int totalCarnivores = 0;
		double totalIntelligence = 0;
		int totalSpeed = 0;
		int totalFood = 0;
		int totalHunger = 0;
		
		for (Creature creature : creatures) {
			totalWeight += creature.getWeight();
			if (creature.isCarnivore()) { totalCarnivores ++; }
			totalIntelligence += creature.getIntelligence();
			totalSpeed += creature.getSpeed();
			totalFood += creature.getFood();
			totalHunger += creature.getHunger();
		}
		
		int averageWeight = totalWeight/size;
		int averageIntelligence = (int)((totalIntelligence/size)*100);
		int averageSpeed = totalSpeed/size;
		int averageFood = totalFood/size;
		int averageHunger = totalHunger/size;
		System.out.println((double) averageFood/averageHunger);
		String averageFoodHungerRatio = decimalFormat.format((double) averageFood/averageHunger);
		
		Creature sample = max(creatures, creatureComparator);
		boolean isFamine = sample.isFamine();
		
		System.out.printf("%sFAMINE: %b | Avg Food/Hunger Ratio: %s | Avg Weight: %d lbs | Total Carnivores: %d | Avg Intelligence: %d IQ | Avg Speed: %d mi/hr | Avg Food: %d cal | Avg Hunger: %d cal%s%n",
				color, isFamine, averageFoodHungerRatio, averageWeight, totalCarnivores, averageIntelligence, averageSpeed, averageFood, averageHunger, RESET);
	}
}
