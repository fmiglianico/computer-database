package com.formation.computerdb.dao;

import java.sql.Connection;
import java.util.List;

import com.formation.computerdb.domain.Company;

/**
 * Interface to access the companies in the Database
 * @author F. Miglianico
 */
public interface CompanyDAO {
	public List<Company> getAll(Connection conn);
	public Company get(int id, Connection conn);
}
