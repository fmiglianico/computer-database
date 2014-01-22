package com.formation.computerdb.core.common;

import com.formation.computerdb.core.domain.QComputer;
import com.mysema.query.types.OrderSpecifier;

/**
 * Enum describing the different types of sort possible
 * @author F. Miglianico
 *
 */
public enum OrderByColumn {

	COMPUTER_NAME_ASC(QComputer.computer.name.asc(), "name", "asc"),
	COMPUTER_NAME_DESC(QComputer.computer.name.desc(), "name", "desc"),
	INTRODUCED_DATE_ASC(QComputer.computer.introduced.asc(), "introduced", "asc"),
	INTRODUCED_DATE_DESC(QComputer.computer.introduced.desc(), "introduced", "desc"),
	DISCONTINUED_DATE_ASC(QComputer.computer.discontinued.asc(), "discontinued", "asc"),
	DISCONTINUED_DATE_DESC(QComputer.computer.discontinued.desc(), "discontinued", "desc"),
	COMPANY_NAME_ASC(QComputer.computer.company.name.asc(), "company", "asc"),
	COMPANY_NAME_DESC(QComputer.computer.company.name.desc(), "company", "desc");
	
	private OrderSpecifier<?> value;
	private String colName = "";
	private String dir = "asc";
	
	private OrderByColumn(OrderSpecifier<?> value, String colName, String dir) {
		this.setValue(value);
		this.setColName(colName);
		this.setDir(dir);
	}

	/**
	 * @return the colName
	 */
	public OrderSpecifier<?> getValue() {
		return value;
	}

	/**
	 * @param colName the colName to set
	 */
	private void setValue(OrderSpecifier<?> value) {
		this.value = value;
	}

	/**
	 * @return the colNameShort
	 */
	public String getColName() {
		return colName;
	}

	/**
	 * @param colNameShort the colNameShort to set
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
