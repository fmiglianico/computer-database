package com.formation.computerdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.formation.computerdb.domain.Company;

public class CompanyDAOImpl implements CompanyDAO {
	
	private static DBHandler handler = null;
	
	protected CompanyDAOImpl() {
	}
	
	public ArrayList<Company> getAll() {

		if(handler == null) {
			handler = DBHandler.getInstance();
		}

		ArrayList<Company> companies = new ArrayList<Company>();
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = handler.getConn();

			String query = "SELECT c.id, c.name "
					+ "FROM company AS c ";
			rs = handler.executeQueryRS(conn, query);

			// Traitement a faire ici sur le resultset
			while(rs.next())
				companies.add(mapCompany(rs));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.err.println("Error in finally: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return companies;
	}

	public Company get(int id) {

		if(handler == null) {
			handler = DBHandler.getInstance();
		}

		Company company = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = handler.getConn();

			String query = "SELECT c.id, c.name FROM company AS c WHERE c.id = " + id;
			rs = handler.executeQueryRS(conn, query);

			// Traitement a faire ici sur le resultset
			if(rs != null && rs.next())
				company = mapCompany(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.err.println("Error in finally: " + e.getMessage());
				e.printStackTrace();
			}
		}

		return company;
	}

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
