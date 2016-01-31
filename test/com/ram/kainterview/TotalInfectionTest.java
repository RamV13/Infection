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
import com.ram.kainterview.user.UserGeneratorTest;

/**
 * Test cases for total infection
 */
public class TotalInfectionTest {

	/**
	 * Tests infection of a single user
	 */
	@Test
	public void testSingleUserInfection() {
		List<User> users = UserGenerator.generateUsers(1, 0, 1);
		assertEquals(users.size(),1);
		User user = users.get(0);
		user.totalInfect(1);
		assertEquals(user.version(),1);
		assertTrue(user.coaches().isEmpty());
		assertTrue(user.students().isEmpty());
	}

	/**
	 * Generate specific user graph and test total infection
	 */
	@Test
	public void testSpecificInfection() {
		List<User> users1 = UserGeneratorTest.generateSpecificUsers();
		List<User> users2 = UserGeneratorTest.generateSpecificUsers();
		users1.get(0).totalInfect(1);
		users2.get(0).totalInfect(1);

		checkUsers(users1);
		checkUsers(users2);

		// not part of test (programmer check)
		assert users1.size() == users2.size();

		for (int i = 0; i < users1.size(); i++) {
			if (i < 3) {
				assertEquals(users1.get(i).version(),1);
				assertEquals(users2.get(i).version(),1);
			} else {
				assertEquals(users1.get(i).version(),0);
				assertEquals(users2.get(i).version(),0);
			}
		}

	}

	/**
	 * Generate random basic user graph and test total infection
	 */
	@Test
	public void testBasicInfection() {
		for (int i = 0; i < 100; i++) {
			List<User> users = UserGenerator.generateUsers(10, 10, 3);
			users.get((int) (Math.random()*users.size())).totalInfect(1);
			checkUsers(users);
		}
	}

	/**
	 * Checks the users' versions using the classInv() method (accesses the 
	 * private method using reflection)
	 * @param users the users to be checked
	 */
	private void checkUsers(List<User> users) {
		for (User user : users) {
			Method method;
			try {
				method = User.class.getDeclaredMethod("classInv");
				method.setAccessible(true);
				assertTrue((boolean) method.invoke(user));
			} catch (IllegalAccessException | IllegalArgumentException | 
					InvocationTargetException | NoSuchMethodException | 
					SecurityException e) {
				e.printStackTrace();
			}
		}
	}

}
