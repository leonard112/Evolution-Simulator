package evolution.simulator;

import static evolution.simulator.CreatureUtilities.createColumn;

import java.util.Scanner;

public class CreatureUtilities {
	public static Scanner scanner = new Scanner(System.in);
	public static final String RESET = "\u001B[0m";
	
	public static final String BLUE = "\u001B[34m";
	public static final String GREEN = "\u001B[32m";
	public static final String RED = "\u001B[31m";
	public static final String CYAN = "\u001B[36m";
	public static final String PURPLE = "\u001B[35m";
	
	public static final String BLUE_BOLD = "\033[1;34m";
	public static final String GREEN_BOLD = "\033[1;32m";
	public static final String RED_BOLD = "\033[1;31m";
	public static final String CYAN_BOLD = "\033[1;36m";
	public static final String PURPLE_BOLD = "\033[1;35m";
	public static final String WHITE_BOLD = "\033[1;37m";
	public static final String BLACK_BOLD = "\033[1;30m";
	
	public static final String GREEN_BACKGROUND = "\033[42m";
	public static final String RED_BACKGROUND = "\033[41m";
	public static final String BLUE_BACKGROUND = "\033[44m";
    public static final String PURPLE_BACKGROUND = "\033[45m";
    public static final String CYAN_BACKGROUND = "\033[46m";
	
	public static int getPositiveInteger(String prompt, boolean requireEvenOnly, boolean requireGreaterThanOne) {
		String input;
		int i = 0;
		boolean continueLoop = true;
		
		do {
			try {
				System.out.print(BLACK_BOLD + prompt + RESET);
				input = scanner.nextLine();
				i = Integer.parseInt(input); 
				if (i < 0) throw new IllegalArgumentException();
				if (requireGreaterThanOne) 
					if (i < 1) throw new IllegalArgumentException();
				if (requireEvenOnly) {
					if ((double)(i/2) != (double)i/2) throw new IllegalArgumentException();
				}
				continueLoop = false;
			}
			catch(NumberFormatException numberFormatException) { 
				printRed("Please only enter an INTEGER.");
			}
			catch(IllegalArgumentException IllegalArgumentException) { 
				if (requireEvenOnly) printRed("Please only enter a NON-ZERO, NON-NEGATIVE, and EVEN integer.");
				else if (requireGreaterThanOne) printRed("Please only enter an integer that is GREATER THAN 1.");
				else printRed("Please only enter a NON-NEGATIVE integer.");
			}
		} while(continueLoop);
		
		return i;
	}
	
	public static double getPercentageFromIntegerInput(String prompt) {
		int percent = 0;
		boolean continueLoop = true;
		
		do {
			try {
				percent = getPositiveInteger(prompt, false, false);
				if (percent < 0 || percent > 100) { throw new IllegalArgumentException(); }
				continueLoop = false;
			}
			catch(IllegalArgumentException IllegalArgumentException) { 
				printRed("Please only enter an integer between 0 and 100"); 
			}
		} while(continueLoop);
		
		return (double) percent/100;
	}
	
	public static void printHeader(String header, String color) {
		System.out.println(color + "===================================================================================================================================================================================================================================================================================================================================================================================================================");
		System.out.println(header);
		System.out.println("===================================================================================================================================================================================================================================================================================================================================================================================================================" + RESET);
	}
	
	public static void printCreatureTableHeading(String color) {
		System.out.println(color + createColumn("NAME", null, 70) +
				createColumn("FAMINE", null, 20) +
				createColumn("FOOD/HUNGER RAITO", null, 40) +
				createColumn("WEIGHT", null, 40) +
				createColumn("FOOD PREFERENCE", null, 30) +
				createColumn("INTELLIGENCE", null, 30) +
				createColumn("SPEED", null, 30) +
				createColumn("FOOD", null, 30) +
				createColumn("HUNGER", null, 30)); 
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" + RESET);
	}
	
	public static void printRed(String str) {
		System.out.println(RED_BOLD + str + RESET);
	}
	
	public static String createColumn(String value, String unit, int spacing) {
		String format = "%-"+ spacing + "s%s";
		if (unit == null)
			return String.format(format, value, "") + " | ";
		return String.format(format, value + " " + unit, "") + " | ";
	}
	
	public static String createColumn(String value, String unit, int spacing, String diff) {
		String format = "%-" + spacing + "s%s";
		if (unit == null)
			return String.format(format, value + " (" + diff + ")", "") + " | ";
		return String.format(format, value + " " + unit + " (" + diff + " " + unit + ")", "") + " | ";
	}
}
