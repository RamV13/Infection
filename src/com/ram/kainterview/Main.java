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

import java.util.List;

import com.ram.kainterview.user.User;
import com.ram.kainterview.user.UserGenerator;

/**
 * Initializes the application, reads user arguments, and performs the 
 * appropriate infection type (total/limited)
 */
public class Main {

	private static final String USAGE = "Usage: java -jar <JAR> <MINIMUM_USERS>"
			+ " <MAXIMUM_STUDENTS> <LEVELS>";
	
	/**
	 * Prints the usage info (i.e. the runtime arguments)
	 */
	private static void printUsage() {
		System.out.println(USAGE);
		System.out.println("For more help: java -jar <JAR> --help");
	}
	
	/**
	 * Prints the help info
	 */
	private static void printHelp() {
		System.out.println(USAGE);
		System.out.println();
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
		int minUsers;
		int maxStudents;
		int levels;
		
		try {
			if (args[0].equals("--help") && args.length == 1) {
				printHelp();
				return;
			} else {
				minUsers = Integer.parseInt(args[0]);
				maxStudents = Integer.parseInt(args[1]);
				levels = Integer.parseInt(args[2]);
			}
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			printUsage();
			return;
		}
		
		InfectionView view = new InfectionViewImpl("Infection");
		
		List<User> users;
		try {
			users = UserGenerator.generateUsers(minUsers, maxStudents, levels);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.println();
			printUsage();
			return;
		}
		
		GraphController controller = new InfectionControllerImpl(users);
		view.initController(controller);
	}
	
}
