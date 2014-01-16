package com.formation.computerdb.webapp.util;

/**
 * Tags and functions for jsps
 * @author F. Miglianico
 *
 */
public class CdbLib {
	
	/**
	 * Pager is like [1 2 ... x-2 x-1 x x+1 x+2 ... n-1 n] 
	 * This function is used to compute the (x-2) value
	 * @param currPage
	 * @return
	 */
	public static int getMiddleRangeStart(int currPage) {
		if(currPage < 5)
			return 3;
		else
			return currPage - 2;
	}

	
	/**
	 * Pager is like [1 2 ... x-2 x-1 x x+1 x+2 ... n-1 n] 
	 * This function is used to compute the (x+2) value
	 * @param currPage
	 * @return
	 */
	public static int getMiddleRangeEnd(int currPage, int nbPages) {
		if(currPage > nbPages - 4)
			return nbPages - 2;
		else
			return currPage + 2;
	}
	
	/**
	 * Generates a link to the dashboard from get parameters passed
	 * by the user
	 * @param page the current page
	 * @param nbRows the number of rows displayed
	 * @param search the search parameter
	 * @param orderBy the sort parameter
	 * @param dir the direction of the sort
	 * @return the link
	 */
	public static String generateLink(Integer page, Integer nbRows, String search, String orderBy, String dir) {
		StringBuilder ret = new StringBuilder("dashboard?");

		if(page != null && page != 0)
			ret.append("&page=").append(page);
		
		if(nbRows != null)
			ret.append("&nbrows=").append(nbRows);
		
		if(search != null && search != "")
			ret.append("&search=").append(search);
		
		if(orderBy != null && orderBy != "")
			ret.append("&orderby=").append(orderBy);
		
		if(dir != null && dir != "")
			ret.append("&dir=").append(dir);
		
		return ret.toString();
	}
}
