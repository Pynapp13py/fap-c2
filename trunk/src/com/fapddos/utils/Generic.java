package com.fapddos.utils;

/**
 * Generic tools that are one off's
 * @author Rev. H. Helix
 *
 */
public class Generic {

	/**
	 *  A constructor
	 */
	public Generic() {
		
	}

	/**
	 * Generate a UUID
	 * @return A UUID
	 */
	public static String createGUID() {
		String retVal = null;
		retVal = java.util.UUID.randomUUID().toString();
		return retVal;
	}
}
