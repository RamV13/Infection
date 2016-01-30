package com.ram.kainterview;

/**
 * List of potential errors from invalid arguments
 */
public enum Error {
	MIN_USERS("<MINIMUM_USERS> must be > 0"),
	MAX_STUDENTS("<MAXIMUM_STUDENTS> must be >= 0"),
	LEVELS("<LEVELS> must be > 0");
	
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
