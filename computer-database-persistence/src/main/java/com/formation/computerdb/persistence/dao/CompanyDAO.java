package com.formation.computerdb.persistence.dao;

import java.util.List;

import com.formation.computerdb.core.domain.Company;


/**
 * Interface to access the companies in the Database
 * @author F. Miglianico
 */
public interface CompanyDAO {
	public List<Company> getAll();
	public Company get(int id);
}
