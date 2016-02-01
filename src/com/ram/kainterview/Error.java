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

/**
 * List of potential errors from invalid user inputs
 */
public enum Error {
	MIN_USERS("<MINIMUM_USERS> must be an integer > 0"),
	MAX_STUDENTS("<MAXIMUM_STUDENTS> must be an integer >= 0"),
	LEVELS("<LEVELS> must be an integer > 0"),
	NUM_USERS("Number of users must be an integer >= 0"),
	NO_STRICT("Not possible to infect exactly this number of users");
	
	/**
	 * Message for the error
	 */
	private String msg;
	
	private Error(String msg) {
		this.msg = msg;
	}
	
	public String toString() {
		return msg;
	}
}
