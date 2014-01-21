package com.formation.computerdb.core.common;

/**
 * Enum describing the different types of sort possible
 * @author F. Miglianico
 *
 */
public enum OrderByColumn {

	COMPUTER_NAME_ASC("computer.name", "asc"),
	COMPUTER_NAME_DESC("computer.name", "desc"),
	INTRODUCED_DATE_ASC("computer.introduced", "asc"),
	INTRODUCED_DATE_DESC("computer.introduced", "desc"),
	DISCONTINUED_DATE_ASC("computer.discontinued", "asc"),
	DISCONTINUED_DATE_DESC("computer.discontinued", "desc"),
	COMPANY_NAME_ASC("computer.company.name", "asc"),
	COMPANY_NAME_DESC("computer.company.name", "desc");
	
	private String colName = "";
	private String dir = "asc";
	
	private OrderByColumn(String colName, String dir) {
		this.setColName(colName);
		this.setDir(dir);
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
