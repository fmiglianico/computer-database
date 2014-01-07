package com.formation.computerdb.common;

/**
 * Enum describing the types of errors when
 * a user is creating or updating a computer
 * @author F. Miglianico
 *
 */
public enum ValidationError {
	NULL_NAME(1),
	UNPARSEABLE_INTRODUCED_DATE(1<<1),
	UNPARSEABLE_DISCONTINUED_DATE(1<<2),
	UNKNOWN_COMPANY(1<<3);
	
	/**
	 * flag value
	 */
	private int bit;
	
	/**
	 * default constructor
	 * @param bit the flag
	 */
	private ValidationError(int bit) {
		this.bit = bit;
	}
	
	/**
	 * @return the flag value
	 */
	public int getFlag() {
		return bit;
	}
}
