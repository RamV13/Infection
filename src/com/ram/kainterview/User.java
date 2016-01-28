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

import java.util.LinkedList;
import java.util.List;

/**
 * Represents an individual user of the software
 */
public class User {
	
	/**
	 * Version that this user sees (base version = 0)
	 */
	private int version;
	
	/**
	 * Contains all of the coaches of this user.
	 * This List is empty if the user is not a student of any coaches.
	 */
	private List<User> coaches;
	
	/**
	 * Contains all the students of this user.
	 * This List is empty if this user is not a coach of any students.
	 */
	private List<User> students;
	
	/**
	 * Default constructor of a user with the base version and no students or 
	 * coaches
	 */
	public User() {
		version = 0;
		coaches = new LinkedList<User>();
		students = new LinkedList<User>();
	}
	
	/**
	 * Adds a coach to this user
	 * @param coach the coach
	 */
	protected void addCoach(User coach) {
		coaches.add(coach);
	}
	
	/**
	 * Adds a student to this user
	 * @param student the student
	 */
	protected void addStudent(User student) {
		students.add(student);
	}
	
}
