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
package com.ram.kainterview.total_infection;

import com.ram.kainterview.User;

/**
 * Models total infection
 */
public class TotalInfection {
	
	/**
	 * Perform total infection with the new version starting from the given user
	 * @param user the user
	 * @param version the new version
	 */
	public static void run(User user, int version) {
		user.setVersion(version);
	}
	
}
