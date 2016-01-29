package com.ram.kainterview;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

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
		UserGenerator.generateUsers(100, 100, 3);
	}
	
	/**
	 * Ensures functionality for single user generation (edge case)
	 */
	@Test
	public void testSingleUser() {
		List<User> users = UserGenerator.generateUsers(1, 0, 3);
		assertEquals(users.size(),1);
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
	
}
