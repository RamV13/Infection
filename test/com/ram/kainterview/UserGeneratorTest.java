package com.ram.kainterview;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 * Test cases for the user base generation
 */
public class UserGeneratorTest {

	@Test
	public void testBasicUserGeneration() {
		List<User> users = UserGenerator.generateUsers(10, 10, 3);
		for (User user : users) {
			checkStudents(user,10,3);
		}
	}
	
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
	
	@Test
	public void testLargeGeneration() {
		UserGenerator.generateUsers(100, 100, 3);
	}
	
	@Test
	public void testSingleUser() {
		List<User> users = UserGenerator.generateUsers(1, 0, 3);
		assertEquals(users.size(),1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidMinimumUsers() {
		UserGenerator.generateUsers(0, 0, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidMaximumStudents() {
		UserGenerator.generateUsers(1, -1, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidLevels() {
		UserGenerator.generateUsers(1, 1, 0);
	}
	
}
