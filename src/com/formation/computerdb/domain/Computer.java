/**
 * 
 */
package com.formation.computerdb.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.formation.computerdb.util.ComputerDBCatalog;

/**
 * Class representing a Computer
 * @author fmiglianico
 *
 */
public class Computer {
	private Long id;
	private String name;
	private Company company;
	private Calendar introduced;
	private Calendar discontinued;

	private static final SimpleDateFormat displayedSDF = new SimpleDateFormat(ComputerDBCatalog.DISPLAYED_DATE_PATTERN.getValue());
	private static final SimpleDateFormat storedSDF = new SimpleDateFormat(ComputerDBCatalog.STORED_DATE_PATTERN.getValue());

	/**
	 * Empty constructor
	 */
	public Computer() {
		this.id = null;
		this.name = null;
		this.company = null;
		this.introduced = null;
		this.discontinued = null;
	}
	
	/**
	 * Constructor with all parameters
	 * @param id
	 * @param name
	 * @param company
	 * @param introduced
	 * @param discontinued
	 */
	public Computer(Long id, String name, Company company, Date introduced, Date discontinued) {
		this.id = id;
		this.name = name;
		this.company = company;
		
		if(introduced != null) {
			this.introduced = Calendar.getInstance();
			this.introduced.setTime(introduced);
		}
		if(discontinued != null) {
			this.discontinued = Calendar.getInstance();
			this.discontinued.setTime(discontinued);
		}
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
	public Calendar getIntroduced() {
		return introduced;
	}
	
	/**
	 * @return the introduced date as a string
	 */
	public String getDisplayedIntroduced() {
		if(this.introduced == null)
			return "";
		
		return displayedSDF.format(this.introduced.getTime());
	}
	
	/**
	 * @return the introduced date as a string
	 */
	public String getStoredIntroduced() {
		if(this.introduced == null)
			return "";
		
		return storedSDF.format(this.introduced.getTime());
	}
	
	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(Date introduced) {
		if(introduced != null) {
			if(this.introduced == null)
				this.introduced = Calendar.getInstance();
			this.introduced.setTime(introduced);
		}
	}
	/**
	 * @return the discontinued
	 */
	public Calendar getDiscontinued() {
		return discontinued;
	}
	
	/**
	 * @return the introduced date as a string
	 */
	public String getDisplayedDiscontinued() {
		if(this.discontinued == null)
			return "";
		
		return displayedSDF.format(this.discontinued.getTime());
	}
	
	/**
	 * @return the introduced date as a string
	 */
	public String getStoredDiscontinued() {
		if(this.discontinued == null)
			return "";
		
		return storedSDF.format(this.discontinued.getTime());
	}
	
	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(Date discontinued) {
		if(discontinued != null) {
			if(this.discontinued == null)
				this.discontinued = Calendar.getInstance();
			this.discontinued.setTime(discontinued);
		}
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
