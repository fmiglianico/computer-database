package com.formation.computerdb.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

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
	
	private static final Logger log = LoggerFactory.getLogger(DAOFactory.class);
	
	private BoneCP connectionPool = null;

	//Static final attributes
	private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/";
	private static final String DB_NAME = "computer-database-db";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	private final static int MAX_CONN_PER_PARTITION = 5;
	
	// The DAOs
	private ComputerDAO computerDAO;
	private CompanyDAO companyDAO;
	
	/**
	 * Initializes the connection and the DAOs
	 */
	private DAOFactory() {
		
		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl(JDBC_URL + DB_NAME);
		config.setUsername(DB_USER);
		config.setPassword(DB_PASSWORD);
		config.setMaxConnectionsPerPartition(MAX_CONN_PER_PARTITION);

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.err.println("Error while loading JDBC driver : " + e.getMessage());
			e.printStackTrace();
		}
		
		try {
			connectionPool = new BoneCP(config);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.err.println("Error while init of connection pool : " + e1.getMessage());
			e1.printStackTrace();
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
			conn = connectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			log.error("Error while trying to fetch a connection : " + e1.getMessage());
			e1.printStackTrace();
		}
		log.debug("getConn()");
		return conn;
	}
}
