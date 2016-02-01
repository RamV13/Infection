/**
 * Package for the total infection implementation for the Khan Academy interview
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

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

import com.ram.kainterview.user.User;
import com.ram.kainterview.user.UserGenerator;

/**
 * Test cases for strict limited infection
 */
public class StrictLimitedInfectionTest {

	/**
	 * Tests infection for single user
	 */
	@Test
	public void testSingleUserInfection() {
		User user = new User();
		assertEquals(user.graphSize(),1);
		
		user.totalInfect(1);
		assertEquals(user.version(),1);
		assertEquals(user.graphSize(),1);
		
		user.totalInfect(1);
	}
	
	/**
	 * Tests specific infection
	 */
	@Test
	public void testSpecificInfection() {
		User user = new User();
		addStudent(user, new User());
		addStudent(user, new User());
		addStudent(user, new User());
		
		addCoach(user, new User());
		addCoach(user, new User());
		addCoach(user, new User());
		
		assertEquals(user.graphSize(), 7);
		
		user.totalInfect(1);
	}
	
	/**
	 * Stress test random infection
	 */
	@Test
	public void testRandomInfection() {
		List<User> users = UserGenerator.generateUsers(15, 15, 3);
		for (User user : users)
			user.graphSize();
	}
	
	/**
	 * Adds a student to the given coach
	 * @param coach the coach
	 * @param student the student
	 */
	private void addStudent(User coach, User student) {
		Method method;
		try {
			method = User.class.getDeclaredMethod("addStudent", 
					new Class[]{ User.class });
			method.setAccessible(true);
			method.invoke(coach, student);
		} catch (IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException | NoSuchMethodException | 
				SecurityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a coach to the given student
	 * @param student the student
	 * @param coach the coach
	 */
	private void addCoach(User student, User coach) {
		Method method;
		try {
			method = User.class.getDeclaredMethod("addCoach", 
					new Class[]{ User.class });
			method.setAccessible(true);
			method.invoke(student, coach);
		} catch (IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException | NoSuchMethodException | 
				SecurityException e) {
			e.printStackTrace();
		}
	}
	
}
