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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for generating a random artificial user base
 */
public class UserGenerator {
	
	/**
	 * List of ALL student users (i.e. the non-top-level users)
	 */
	private static List<User> students;
	
	/**
	 * Number of random attempts before simply iterating through the student 
	 * list (affects performance/randomness)
	 */
	private static final int ITERATION_NUMBER = 10;
	
	// prevents instantiation
	private UserGenerator() { }
	
	// TODO: max is not high enough to have 1.0 probability???
	
	/**
	 * Generates a random artificial user base given the specifications such as
	 * the number of top-level users
	 * @param numTopLevel the number of top-level users (requires: >= 1)
	 * @param maxNumStudents the maximum number of students per user 
	 * (requires: >= 0)
	 * @param maxNumCoaches the maximum number of coaches per user 
	 * (requires: >=0)
	 * @param probability the probability of a user's student already being that
	 *  of another coach (requires: 0.0 <= probability <= 1.0)
	 * @return the top-level users in the graph of the user base
	 */
	public static List<User> generateUsers(int numTopLevel, int maxNumStudents,
			int maxNumCoaches, double probability) {
		if (numTopLevel < 1)
			throw new IllegalArgumentException("numTopLevel must be > 0");
		else if (maxNumStudents < 0)
			throw new IllegalArgumentException("maxNumStudents must be >= 0");
		else if (maxNumCoaches  < 0)
			throw new IllegalArgumentException("maxNumCoaches must be >= 0");
		else if (probability < 0 || probability > 1)
			throw new IllegalArgumentException(
					"probability must be within 0 and 1 inclusive");

		List<User> users = new LinkedList<User>(); // top-level users
		students = new ArrayList<User>(); // student users
		
		for (int i = 0; i < numTopLevel; i++) {
			User user = new User(); // create new top-level user
			users.add(user);
			
			// populate students for this top-level user
			int numStudents = (int) Math.round(Math.random()*maxNumStudents);
			for (int j = 0; j < numStudents; j++) {
				if (Math.random() > probability) { // use new student
					User student = new User();
					user.addStudent(student);
					student.addCoach(user);
				} else {                          // use existing student
					// generate first student if none exist
					if (students.isEmpty())
						students.add(new User());
					
					// select random student that will not exceed maxNumCoaches
					User student = null;
					int count = 0;
					boolean selected = false;
					while (count < ITERATION_NUMBER) {
						student = students.get(
								(int) (Math.random()*students.size()));
						if (student.numCoaches()+1 < maxNumCoaches) {
							selected = true;
							break;
						}
						count++;
					}
					
					if (!selected) {
						// iterate through to find a valid student
						for (int k = 0; k < students.size(); k++) {
							student = students.get(k);
							if (student.numCoaches()+1 < maxNumCoaches) {
								selected = true;
								break;
							}
						}
					}
					
					if (selected) {
						user.addStudent(student);
						student.addCoach(user);
					} else {
						throw new IllegalArgumentException("Parameters "
								+ "conflict and there is no arrangement that "
								+ "can satisfy all specifications"); 
					}
				}
			}
		}
		
		return users;
	}
	
}
