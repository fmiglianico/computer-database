package com.formation.computerdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.formation.computerdb.domain.Company;

/**
 * Implementation of the interface @CompanyDAO
 * @author F. Miglianico
 *
 */
public class CompanyDAOImpl implements CompanyDAO {
	
	protected CompanyDAOImpl() {
	}
	
	/**
	 * Get all the companies in DB
	 */
	public List<Company> getAll() {
		
		Connection conn = DAOFactory.INSTANCE.getConn();

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
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.err.println("Error in finally: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return companies;
	}

	/**
	 * get a the company with the id given as parameter
	 * @param id the id
	 */
	public Company get(int id) {
		
		Connection conn = DAOFactory.INSTANCE.getConn();

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
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.err.println("Error in finally: " + e.getMessage());
				e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return company;
	}
}
