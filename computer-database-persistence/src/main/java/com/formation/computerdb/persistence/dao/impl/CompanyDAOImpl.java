package com.formation.computerdb.persistence.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CleanupFailureDataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.formation.computerdb.core.domain.Company;
import com.formation.computerdb.persistence.dao.BaseDAO;
import com.formation.computerdb.persistence.dao.CompanyDAO;


/**
 * Implementation of the interface @CompanyDAO
 * @author F. Miglianico
 *
 */
@Repository
@Transactional(readOnly=true)
public class CompanyDAOImpl extends BaseDAO implements CompanyDAO {
	
	private static Logger log = LoggerFactory.getLogger(CompanyDAOImpl.class);
	
	protected CompanyDAOImpl() {
	}
	
	/**
	 * Get all the companies in DB
	 */
	public List<Company> getAll() {
		
		Connection conn = getConnection();
		if(conn == null) {
			log.error("Cannot retrieve connection");
			return null;
		}

		List<Company> companies = new ArrayList<Company>();
		ResultSet rs = null;
		
		try {

			String query = "SELECT c.id, c.name FROM company AS c ";

			Statement statement = conn.createStatement();
			rs = statement.executeQuery(query.toString());

			// Traitement a faire ici sur le resultset
			while(rs.next())
				companies.add(mapCompany(rs));

		} catch (SQLException e) {
			throw new DataRetrievalFailureException("Could not retrieve companies", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				throw new CleanupFailureDataAccessException("Could not close connection", e);
			}
		}
		return companies;
	}

	/**
	 * get a the company with the id given as parameter
	 * @param id the id
	 */
	public Company get(int id) {
		
		Connection conn = getConnection();
		if(conn == null) {
			log.error("Cannot retrieve connection");
			return null;
		}

		Company company = null;
		ResultSet rs = null;

		try {
			StringBuilder query = new StringBuilder("SELECT c.id, c.name FROM company AS c WHERE c.id = ").append(id);

			Statement statement = conn.createStatement();
			rs = statement.executeQuery(query.toString());

			// Traitement a faire ici sur le resultset
			if(rs != null && rs.next())
				company = mapCompany(rs);

		} catch (SQLException e) {
			throw new DataRetrievalFailureException("Could not retrieve company", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				throw new CleanupFailureDataAccessException("Could not close connection", e);
			}
		}

		return company;
	}

	/**
	 * Creates a company from a resultset
	 * @param resultSet the resultset
	 * @return the company
	 */
	private Company mapCompany( ResultSet resultSet ) {
		Company company = new Company();

		try {
			company.setId(resultSet.getLong("c.id"));
			company.setName(resultSet.getString("c.name"));

		} catch (SQLException e) {
			throw new DataRetrievalFailureException("Could not map resultSet with Company", e);
		}

		return company;
	}
}
