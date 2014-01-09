package com.formation.computerdb.util;

public enum ComputerDBCatalog {
	DISPLAYED_DATE_PATTERN("dd/MM/yyyy"),
	STORED_DATE_PATTERN("yyyy-MM-dd");
	
	private String value;
	
	private ComputerDBCatalog(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
