/**
 * 
 */
package com.formation.computerdb.domain;

import java.util.Date;

/**
 * @author fmiglianico
 *
 */
public class Computer {
	private Long id;
	private String name;
	private Company company;
	private Date introduced;
	private Date discontinued;

	public Computer() {
		this.id = null;
		this.name = null;
		this.company = null;
		this.introduced = null;
		this.discontinued = null;
	}
	
	public Computer(Long id, String name, Company company, Date introduced, Date discontinued) {
		this.id = id;
		this.name = name;
		this.company = company;
		this.introduced = introduced;
		this.discontinued = discontinued;
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
	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
	/**
	 * @return the introduced
	 */
	public Date getIntroduced() {
		return introduced;
	}
	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}
	/**
	 * @return the discontinued
	 */
	public Date getDiscontinued() {
		return discontinued;
	}
	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Computer [id =" + id + ", name=" + name + ", company=" + company
				+ ", introduced=" + introduced + ", discontinued="
				+ discontinued + "]";
	}
}
