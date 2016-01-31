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
 * Lists the various infection types
 * TOTAL = total infection
 * LIMITED = limited infection
 * STRICT = limited infection with an EXACT number of users
 */
public enum InfectionType {
	TOTAL("Total Infection"), LIMITED("Limited Infection"), 
	STRICT("Strict Limited Infection");
	
	private String s;
	private InfectionType(String s) {
		this.s = s;
	}
	public String toString() {
		return s;
	}
}
