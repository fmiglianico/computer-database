package com.formation.computerdb.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * Class representing a Computer
 * @author F. Miglianico
 *
 */
@Entity
@Table(name="computer")
public class Computer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne(targetEntity=Company.class)
	@JoinColumn(name="company_id")
	private Company company;

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name="introduced")
	private DateTime introduced;
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name="discontinued")
	private DateTime discontinued;

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
	public Computer(Long id, String name, Company company, DateTime introduced, DateTime discontinued) {
		this.setId(id);
		this.setName(name);
		this.setCompany(company);
		this.setIntroduced(introduced);
		this.setDiscontinued(discontinued);

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
	public DateTime getIntroduced() {
		return introduced;
	}
	
	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(DateTime introduced) {
			this.introduced = introduced;
	}
	/**
	 * @return the discontinued
	 */
	public DateTime getDiscontinued() {
		return discontinued;
	}
	
	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(DateTime discontinued) {
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
