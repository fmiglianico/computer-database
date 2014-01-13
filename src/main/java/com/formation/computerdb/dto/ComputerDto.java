package com.formation.computerdb.dto;



/**
 * Class representing a Computer as a Data Transport Object
 * @author F. Miglianico
 */
public class ComputerDto {
	
	/**
	 * Computer id
	 */
	private Long id;
	/**
	 * Computer name
	 */
	private String name;
	/**
	 * Introduction date of computer
	 */
	private String introduced;
	/**
	 * Discontinued date of computer
	 */
	private String discontinued;
	/**
	 * Company id
	 */
	private Long companyId;
	
	public ComputerDto() {
		this.id = null;
		this.name = null;
		this.introduced = null;
		this.discontinued = null;
		this.companyId = null;
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
	 * @return the introduced
	 */
	public String getIntroduced() {
		return introduced;
	}
	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	/**
	 * @return the discontinued
	 */
	public String getDiscontinued() {
		return discontinued;
	}
	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	/**
	 * @return the companyId
	 */
	public Long getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
