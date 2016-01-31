/**
 * Package for the users of the infection implementations for the Khan Academy 
 * interview
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
package com.ram.kainterview.user;

import java.util.ArrayList;
import java.util.List;

import com.ram.kainterview.Error;

/**
 * Class for generating a random artificial user base
 */
public class UserGenerator {

	/**
	 * General probability of using an existing user versus creating a new user
	 * (higher value leads to increased randomness but less performance)
	 */
	private static final double probability = 0.1;

	// prevents instantiation
	private UserGenerator() { }

	/**
	 * Generates a random artificial user base given the specifications such as
	 * the number of top-level users
	 * @param min minimum number of users (requires: >= 1)
	 * @param maxStudents maximum number of direct students per user
	 * @param levels number of levels of coach-student relationships to generate
	 * @return list of all users in the graph of the user base
	 */
	public static List<User> generateUsers(int min, int maxStudents, int levels) {
		if (min < 1)
			throw new IllegalArgumentException(Error.MIN_USERS.toString());
		else if (maxStudents < 0)
			throw new IllegalArgumentException(Error.MAX_STUDENTS.toString());
		else if (levels < 1)
			throw new IllegalArgumentException(Error.LEVELS.toString());

		List<User> users = new ArrayList<>(); // all users

		for (int i = 0; i < min; i++) {
			User user = new User(); // create new user
			users.add(user);
			populateUser(users,user,maxStudents,levels);
		}

		return users;
	}

	/**
	 * Populates the students of this user recursively
	 * @param users the list of ALL users in the user base graph
	 * @param user the current user to populate
	 * @param maxStudents the maximum number of direct students per user
	 * @param levels the number of levels of coach-student relationships 
	 * (i.e. the number of levels to descend recursively)
	 */
	private static void populateUser(List<User> users, User user, 
			int maxStudents, int levels) {
		if (levels == 0)
			return;
		
		int numStudents = (int) Math.round(Math.random()*maxStudents);
		// populate students for this top-level user
		for (int j = 0; j < numStudents; j++) {
			User student;
			boolean newStudent = true;
			if (Math.random() > probability) // use new student
				student = new User();
			else {                           // use existing student
				student = users.get((int) (Math.random()*users.size()));
				if (user.students().contains(student) || user.equals(student))
					student = new User();
				else
					newStudent = false;
			}
			
			// set up coach-student relation
			user.addStudent(student);
			student.addCoach(user);
			
			// only populate the new students
			if (newStudent) {
				users.add(student);
				populateUser(users,student,maxStudents,levels-1);
			}
		}
	}
	
}
