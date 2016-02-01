/**
 * Package for the users in the infection implementations for the Khan Academy 
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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.ram.kainterview.user.User;
import com.ram.kainterview.user.UserGenerator;

/**
 * Test cases for the user base generation
 */
public class UserGeneratorTest {

	/**
	 * Tests standard user generation and ensures no duplication and checks 
	 * maximum student size restriction
	 */
	@Test
	public void testBasicUserGeneration() {
		List<User> users = UserGenerator.generateUsers(10, 10, 3);
		for (User user : users)
			checkStudents(user,10,3);
	}
	
	/**
	 * Checks maximum student size restriction and ensures no duplication
	 * @param user the first user to check and descend down from
	 * @param maxStudents maximum number of students per user
	 * @param levels number of levels of coach-student relationships
	 */
	private void checkStudents(User user, int maxStudents, int levels) {
		if (levels == 0)
			return;
		for (User student : user.students()) {
			int size = user.students().size();
			assertTrue(size <= maxStudents);
			
			for (int i = 0; i < size; i++) {
				List<User> students = new LinkedList<User>();
				students.addAll(user.students());
				
				User cur = students.remove(i);
				assertFalse(students.contains(cur));
			}
			checkStudents(student,maxStudents,levels-1);
		}
	}
	
	/**
	 * Stress test for a large user generation
	 */
	@Test
	public void testLargeGeneration() {
		UserGenerator.generateUsers(50, 50, 3);
	}
	
	/**
	 * Ensures functionality for single user generation (edge case)
	 */
	@Test
	public void testSingleUser() {
		List<User> users = UserGenerator.generateUsers(1, 0, 3);
		assertEquals(users.size(),1);
		assertTrue(users.get(0).coaches().isEmpty());
		assertTrue(users.get(0).students().isEmpty());
	}
	
	/**
	 * Checks illegal parameter for minimum number of users
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidMinimumUsers() {
		UserGenerator.generateUsers(0, 0, 1);
	}
	
	/**
	 * Checks illegal parameter for maximum number of students
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidMaximumStudents() {
		UserGenerator.generateUsers(1, -1, 1);
	}
	
	/**
	 * Checks illegal parameter for number of levels
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidLevels() {
		UserGenerator.generateUsers(1, 1, 0);
	}
	
	/**
	 * Generates a specific user graph
	 * 
	 *   	coach1				  	  coach2
	 * student1 student2  		student3 student4	 
	 * 
	 * @return the list of all users in the graph
	 */
	public static List<User> generateSpecificUsers() {
		List<User> users = new ArrayList<>(6);
		
		User coach1 = new User();
		User student1 = new User();
		User student2 = new User();
		coach1.addStudent(student1);
		coach1.addStudent(student2);
		
		User coach2 = new User();
		User student3 = new User();
		User student4 = new User();
		coach2.addStudent(student3);
		coach2.addStudent(student4);
		
		users.add(coach1);
		users.add(student1);
		users.add(student2);
		users.add(coach2);
		users.add(student3);
		users.add(student4);
		return users;
	}
	
}
