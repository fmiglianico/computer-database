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
	
	private Logger log = LoggerFactory.getLogger(DAOFactory.class);
	
	private BoneCP connectionPool = null;
	private static ThreadLocal<Connection> thConn = new ThreadLocal<Connection>();

	//Static final attributes
	private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/";
	private static final String DB_NAME = "computer-database-db";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	private final static int MAX_CONN_PER_PARTITION = 5;
	
	// The DAOs
	private ComputerDAO computerDAO;
	private CompanyDAO companyDAO;
	private DBLogDAO dbLogDAO;
	
	/**
	 * Initializes the connection pool and the DAOs
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
			log.error("Error while loading JDBC driver : " + e.getMessage(), e);
		}
		
		try {
			connectionPool = new BoneCP(config);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			log.error("Error while init of connection pool : " + e1.getMessage(), e1);
		}
		
		computerDAO = new ComputerDAOImpl();
		companyDAO = new CompanyDAOImpl();
		dbLogDAO = new DBLogDAOImpl();
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
	 * @return the companyDAO
	 */
	public DBLogDAO getDBLogDAO() {
		return dbLogDAO;
	}
	
	/**
	 * 
	 * @return a connection to the DB
	 */
	public Connection getConn() {
		Connection conn = null;
	
		try {
			conn = connectionPool.getConnection();
			thConn.set(conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			log.error("Error while trying to fetch a connection : " + e1.getMessage());
			e1.printStackTrace();
		}
		
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			log.error("Error while setting auto-commit to false : " + e.getMessage(), e);
		}
		//log.debug("getConn()");
		return conn;
	}
	
	/**
	 * Commit a transaction in ThreadLocal connection
	 */
	public void commit() {
		Connection conn = thConn.get();
		if(conn == null) {
			log.error("Connection is null");
		}
		
		try {
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("Cannot rollback transaction");
			}
			log.error("Cannot commit transaction");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Cannot close connection");
			}
		}
	}

	/**
	 * Rollback a transaction in ThreadLocal connection
	 */
	public void rollback() {
		Connection conn = thConn.get();
		if(conn == null) {
			log.error("Connection is null");
		}
		
		try {
			conn.rollback();
		} catch (SQLException e) {
			log.error("Cannot rollback transaction");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Cannot close connection");
			}
		}
	}
}
