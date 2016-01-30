/**
 * Package for the infection implementations for the Khan Academy interview
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 Ram Vellanki
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 */
package com.ram.kainterview;

/**
 * Initializes the application, reads user arguments, and performs the 
 * appropriate infection type (total/limited)
 */
public class Main {

	/**
	 * Prints the usage info (i.e. the runtime arguments)
	 */
	private static void printUsage() {
		System.out.println("Usage: java -jar <JAR> <INFECTION_TYPE> "
				+ "<MINIMUM_USERS> <MAXIMUM_STUDENTS> <LEVELS>");
		System.out.println("For more help: java -jar <JAR> --help");
	}
	
	/**
	 * Prints the help info
	 */
	private static void printHelp() {
		System.out.println("Usage: java -jar <JAR> <INFECTION_TYPE> "
				+ "<MINIMUM_USERS> <MAXIMUM_STUDENTS> <LEVELS>");
		System.out.println("<INFECTION_TYPE>: the type of infection "
				+ "(i.e. \"total_infection\" or \"limited_infection\")");
		System.out.println("<MINIMUM_USERS>: the minimum number of users in the"
				+ " user base");
		System.out.println("<MAXIMUM_STUDENTS>: the maximum number of students "
				+ "per user");
		System.out.println("<LEVELS>: the levels of coach-student relationships"
				+ " to generate");
	}
	
	/**
	 * Main method which takes in parameters for the type of infection as well 
	 * as extra parameters for user-base generation
	 * @param args the runtime arguments
	 */
	public static void main(String[] args) {
		boolean totalInfection;
		try {
			if (args[0].equals("total_infection"))
				totalInfection = true;
			else if (args[0].equals("limited_infection"))
				totalInfection = false; // redundancy for code clarity
			else if (args[0].equals("--help") && args.length == 1) {
				printHelp();
				return;
			} else {
				printUsage();
				return;
			}
		} catch (IndexOutOfBoundsException e) {
			printUsage();
			return;
		}
		
		String title = totalInfection ? "Total Infection" : "Limited Infection";
		GraphView view = new InfectionView(title);
		GraphController controller = new InfectionController();
		view.initController(controller);
	}
	
}
