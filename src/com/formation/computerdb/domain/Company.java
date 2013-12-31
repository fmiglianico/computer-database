package com.formation.computerdb.domain;

/**
 * @author fmiglianico
 *
 */
public class Company {
	private Long id;
	private String name;

	public Company() {
		this.id = null;
		this.name = null;
	}
	
	public Company(String name) {
		this.id = null;
		this.name = name;
	}
	
	public Company(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
