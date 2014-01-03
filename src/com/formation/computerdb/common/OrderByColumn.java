package com.formation.computerdb.common;

/**
 * Enum describing the different types of sort possible
 * @author F. Miglianico
 *
 */
public enum OrderByColumn {

	COMPUTER_NAME_ASC("name", "asc", 2),
	COMPUTER_NAME_DESC("name", "desc", 2),
	INTRODUCED_DATE_ASC("introduced", "asc", 3),
	INTRODUCED_DATE_DESC("introduced", "desc", 3),
	DISCONTINUED_DATE_ASC("discontinued", "asc", 4),
	DISCONTINUED_DATE_DESC("discontinued", "desc", 4),
	COMPANY_NAME_ASC("company", "asc", 6),
	COMPANY_NAME_DESC("company", "desc", 6);
	
	private String colName = "";
	private String dir = "asc";
	private int colNumber = 1;
	
	private OrderByColumn(String colName, String dir, int colNumber ) {
		this.setColName(colName);
		this.setDir(dir);
		this.setColNumber(colNumber);
	}

	/**
	 * @return the colName
	 */
	public String getColName() {
		return colName;
	}

	/**
	 * @param colName the colName to set
	 */
	private void setColName(String colName) {
		this.colName = colName;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir the dir to set
	 */
	private void setDir(String dir) {
		this.dir = dir;
	}
	
	/**
	 * @return the colNumber
	 */
	public int getColNumber() {
		return colNumber;
	}

	/**
	 * @param colNumber the colNumber to set
	 */
	public void setColNumber(int colNumber) {
		this.colNumber = colNumber;
	}
	
	/**
	 * Get the enum value for the parameters
	 * @param colName the name of the column
	 * @param dir the direction of sort
	 * @return the enum value corresponding to the parameters
	 */
	public static OrderByColumn get(String colName, String dir) {
		if(colName == null)
			return null;
		
		if(dir == null)
			dir = "asc";

		OrderByColumn[] list = OrderByColumn.values();
		
		for(OrderByColumn col : list) {
			if(col.getColName().equals(colName) && col.getDir().equals(dir))
				return col;
		}
		return null;
	}
	
	/**
	 * Used to generate the link when clicking on the headers of the table
	 * @param col the column for which the link is being generated
	 * @return the direction, "asc" or "desc"
	 */
	public String getDirForCol(String col) {
		
		if(this.getColName().equals(col) && this.getDir().equals("asc"))
			return "desc";
		
		return "asc";
	}
	
}
