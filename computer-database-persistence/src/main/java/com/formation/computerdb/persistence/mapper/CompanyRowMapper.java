package com.formation.computerdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.RowMapper;

import com.formation.computerdb.core.domain.Company;

public class CompanyRowMapper implements RowMapper<Company> {


	/**
	 * Creates a company from a resultset
	 * @param rs the resultset
	 * @return the company
	 */
	@Override
	public Company mapRow(ResultSet rs, int line) {
		
		Company company = new Company();

		try {
			company.setId(rs.getLong("company.id"));
			company.setName(rs.getString("company.name"));
		} catch (SQLException e) {
			throw new DataRetrievalFailureException("Could not map ResultSet to Company", e);
		}
	    
	    return company;
	}

}
