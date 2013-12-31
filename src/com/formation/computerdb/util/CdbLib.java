package com.formation.computerdb.util;

public class CdbLib {
	
	public static int getMiddleRangeStart(int currPage) {
		if(currPage < 5)
			return 3;
		else
			return currPage - 2;
	}
	
	public static int getMiddleRangeEnd(int currPage, int nbPages) {
		if(currPage > nbPages - 4)
			return nbPages - 2;
		else
			return currPage + 2;
	}
	
	public static String generateLink(Integer page, Integer max, String search, String orderBy, String dir) {
		StringBuilder ret = new StringBuilder("dashboard?");

		if(page != null && page != 0)
			ret.append("&page=").append(page);
		
		if(max != null)
			ret.append("&max=").append(max);
		
		if(search != null && search != "")
			ret.append("&search=").append(search);
		
		if(orderBy != null && orderBy != "")
			ret.append("&orderby=").append(orderBy);
		
		if(dir != null && dir != "")
			ret.append("&dir=").append(dir);
		
		return ret.toString();
	}
}
