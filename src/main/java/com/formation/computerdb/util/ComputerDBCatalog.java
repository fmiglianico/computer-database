package com.formation.computerdb.util;

public enum ComputerDBCatalog {
	DISPLAYED_DATE_PATTERN("dd/MM/YYYY"),
	STORED_DATE_PATTERN("YYYY-MM-dd");
	
	private String value;
	
	private ComputerDBCatalog(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
