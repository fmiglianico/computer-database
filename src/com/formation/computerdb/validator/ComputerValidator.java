package com.formation.computerdb.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ComputerValidator {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static boolean isValid(String name, String introduction, String discontinued) {
		if(name == null)
			return false;
		
		if(introduction != null) {
			try {
				sdf.parse(introduction);
			} catch (ParseException e) {
				return false;
			}
		}
		
		if(discontinued != null) {
			try {
				sdf.parse(discontinued);
			} catch (ParseException e) {
				return false;
			}
		}
		
		return true;	
	}
}
