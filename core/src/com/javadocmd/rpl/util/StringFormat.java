package com.javadocmd.rpl.util;

/** Substitutions for String.format() because GWT doesn't support it. */
public class StringFormat {

	public static String percent(float value) {
		String s = Float.toString(Math.min(999f, value * 100f));
		
		int end = s.indexOf(".");
		end = (end > -1) ? end : s.length();
		
		s = s.substring(0, end);
		for (int i = s.length(); i < 3; i++)
			s = " " + s;
		
		return s;
	}
	
	public static String decimal(float value) {
		String s = Float.toString(Math.min(999f, value * 1000f));
		
		int end = s.indexOf(".");
		end = (end > -1) ? end : s.length();
		
		s = s.substring(0, end);
		for (int i = s.length(); i < 3; i++)
			s = "0" + s;
		
		return s;
	}
}
