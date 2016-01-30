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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Represents an individual user of the software
 * Invariant: version number of this user and all coaches and students are equal
 */
public class User {
	
	/**
	 * Version that this user sees (base version = 0)
	 */
	private int version;
	
	/**
	 * Unique identifier for this user
	 */
	private String id;
	
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
		id = UUID.randomUUID().toString();
		coaches = new LinkedList<User>();
		students = new LinkedList<User>();
	}
	
	/**
	 * Checks the invariant of this class that the version numbers should be
	 * equal across all coaches and students of this user
	 * @return true of the invariant holds, false otherwise
	 */
	private boolean classInv() {
		for (User coach : coaches)
			if (this.version != coach.version)
				return false;
		for (User student : students)
			if (this.version != student.version)
				return false;
		return true;
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
	
	/**
	 * Gets a read-only version of the list of coaches
	 * @return the list of coaches
	 */
	public List<User> coaches() {
		return Collections.unmodifiableList(coaches);
	}
	
	/**
	 * Gets a read-only version of the list of students
	 * @return the list of students
	 */
	public List<User> students() {
		return Collections.unmodifiableList(students);
	}
	
	/**
	 * Gets the number of coaches for this user
	 * @return the number of coaches
	 */
	protected int numCoaches() {
		return coaches.size();
	}
	
	/**
	 * Gets the number of students for this user
	 * @return the number of students
	 */
	protected int numStudents() {
		return students.size();
	}
	
	/**
	 * Gets the id of this user
	 * @return the id
	 */
	public String id() {
		return id;
	}
	
	/**
	 * Gets the version that this user sees
	 * @return the version number
	 */
	public int version() {
		return version;
	}
	
	/**
	 * Sets the version that this user sees
	 * @param version the new version number
	 */
	public void setVersion(int version) {
		assert classInv();
		this.version = version;
		infect();
		assert classInv();
	}
	
	/**
	 * Checks the invariant of this class that the version numbers should be
	 * equal across all coaches and students of this user and adjusts 
	 * accordingly by infecting the necessary users
	 */
	private void infect() {
		for (User coach : coaches)
			if (coach.version != version)
				coach.setVersion(version);
		for (User student : students)
			if (student.version != version)
				student.setVersion(version);
	}
	
}
