package com.formation.computerdb.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.formation.computerdb.core.domain.Company;
import com.formation.computerdb.persistence.dao.BaseDAO;
import com.formation.computerdb.persistence.dao.CompanyDAO;
import com.formation.computerdb.persistence.mapper.CompanyRowMapper;


/**
 * Implementation of the interface @CompanyDAO
 * @author F. Miglianico
 *
 */
@Repository
@Transactional(readOnly=true)
public class CompanyDAOImpl extends BaseDAO implements CompanyDAO {
	
	//private static Logger log = LoggerFactory.getLogger(CompanyDAOImpl.class);
	
	protected CompanyDAOImpl() {
	}
	
	/**
	 * Get all the companies in DB
	 */
	public List<Company> getAll() {
		
		String query = "SELECT company.id, company.name FROM company";
		
		List<Company> companies = jdbcTemplate.query(query, new CompanyRowMapper());
			
		return companies;
	}

	/**
	 * get a the company with the id given as parameter
	 * @param id the id
	 */
	public Company get(int id) {

		String query = new StringBuilder("SELECT company.id, company.name FROM company WHERE company.id = ?").toString();

		return jdbcTemplate.queryForObject(query, new Object[] {id}, new CompanyRowMapper());
	}
}
