package com.formation.computerdb.core.common;

import org.springframework.data.domain.Sort;

/**
 * Enum describing the different types of sort possible
 * 
 * @author F. Miglianico
 * 
 */
public enum OrderBy {

	COMPUTER_NAME_ASC(new Sort(Sort.Direction.ASC, "name"), "name", "asc"), 
	COMPUTER_NAME_DESC(new Sort(Sort.Direction.DESC, "name"), "name", "desc"), 
	INTRODUCED_DATE_ASC(new Sort(Sort.Direction.ASC, "introduced"), "introduced", "asc"), 
	INTRODUCED_DATE_DESC(new Sort(Sort.Direction.DESC, "introduced"), "introduced", "desc"), 
	DISCONTINUED_DATE_ASC(new Sort(Sort.Direction.ASC, "discontinued"), "discontinued", "asc"), 
	DISCONTINUED_DATE_DESC(new Sort(Sort.Direction.DESC, "discontinued"), "discontinued","desc"), 
	COMPANY_NAME_ASC(new Sort(Sort.Direction.ASC,"company.name"), "company", "asc"),
	COMPANY_NAME_DESC(new Sort(Sort.Direction.DESC, "company.name"), "company", "desc");

	private Sort value;
	private String colName = "";
	private String dir = "asc";

	private OrderBy(Sort value, String colName, String dir) {
		this.setValue(value);
		this.setColName(colName);
		this.setDir(dir);
	}

	/**
	 * @return the colName
	 */
	public Sort getValue() {
		return value;
	}

	/**
	 * @param colName
	 *            the colName to set
	 */
	private void setValue(Sort value) {
		this.value = value;
	}

	/**
	 * @return the colNameShort
	 */
	public String getColName() {
		return colName;
	}

	/**
	 * @param colNameShort
	 *            the colNameShort to set
	 */
	public void setColName(String colName) {
		this.colName = colName;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir
	 *            the dir to set
	 */
	private void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * Get the enum value for the parameters
	 * 
	 * @param colName
	 *            the name of the column
	 * @param dir
	 *            the direction of sort
	 * @return the enum value corresponding to the parameters
	 */
	public static OrderBy get(String colName, String dir) {
		if (colName == null)
			return null;

		if (dir == null)
			dir = "asc";

		OrderBy[] list = OrderBy.values();

		for (OrderBy col : list) {
			if (col.getColName().equals(colName) && col.getDir().equals(dir))
				return col;
		}
		return null;
	}

	/**
	 * Used to generate the link when clicking on the headers of the table
	 * 
	 * @param col
	 *            the column for which the link is being generated
	 * @return the direction, "asc" or "desc"
	 */
	public String getDirForCol(String col) {

		if (this.getColName().equals(col) && this.getDir().equals("asc"))
			return "desc";

		return "asc";
	}

	public static OrderBy getOrderByFromSort(Sort sort) {
		if (sort == null)
			return null;

		OrderBy[] list = OrderBy.values();

		for (OrderBy ob : list) {
			if (ob.getValue().equals(sort))
				return ob;
		}
		return null;
	}

}
