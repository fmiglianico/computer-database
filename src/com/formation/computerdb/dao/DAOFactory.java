package com.formation.computerdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * DAO Factory
 * Used to get the DAOs or a connection to the DB
 * @author F. Miglianico
 *
 */
public enum DAOFactory {
	/**
	 * The instance (Singleton)
	 */
	INSTANCE;

	//Static final attributes
	private static final String DB_NAME = "computer-database-db";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	
	private Properties connectionProps = null;
	
	// The DAOs
	private ComputerDAO computerDAO;
	private CompanyDAO companyDAO;
	
	/**
	 * Initializes the connection and the DAOs
	 */
	private DAOFactory() {
		connectionProps = new Properties();
		connectionProps.put("user", DB_USER);
		connectionProps.put("password", DB_PASSWORD);
		connectionProps.put("zeroDateTimeBehavior", "convertToNull");

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.err.println("Error in DBHandler constructor: " + e.getMessage());
			e.printStackTrace();
		}
		
		computerDAO = new ComputerDAOImpl();
		companyDAO = new CompanyDAOImpl();
	}
	
	/**
	 * 
	 * @return the computerDAO
	 */
	public ComputerDAO getComputerDAO() {
		return computerDAO;
	}
	
	/**
	 * 
	 * @return the companyDAO
	 */
	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}
	
	/**
	 * 
	 * @return a connection to the DB
	 */
	public Connection getConn() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"
					+ DB_NAME, connectionProps);
		} catch (Exception e) {
			System.err.println("Error in getConn: " + e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
}
