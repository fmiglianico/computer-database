package com.formation.computerdb.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.computerdb.common.RC;
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
	private static final String DB_NAME = "computer-database-db?zeroDateTimeBehavior=convertToNull";
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
		if(thConn != null)
			conn = thConn.get();
		return conn;
	}
	
	/**
	 * Sets the connection in thread
	 * @return RC.OK if success, RC.FAILED otherwise
	 */
	public RC setConn() {
		
		if(connectionPool == null) {
			log.error("Connection pool is null");
			return RC.FAILED;
		}
		
		Connection conn = null;
		
		try {
			conn = connectionPool.getConnection();
			thConn.set(conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			log.error("Error while trying to fetch a connection : " + e1.getMessage());
			e1.printStackTrace();
			return RC.FAILED;
		}
	
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			log.error("Error while setting auto-commit to false : " + e.getMessage(), e);
			return RC.FAILED;
		}
		
		return RC.OK;
	}
	
	/**
	 * Commit a transaction in ThreadLocal connection
	 * @return RC.OK if success, RC.FAILED otherwise
	 */
	public RC commit() {
		Connection conn = thConn.get();
		if(conn == null) {
			log.error("Connection is null");
			return RC.FAILED;
		}
		
		try {
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("Cannot rollback transaction");
				return RC.FAILED;
			}
			log.error("Cannot commit transaction");
			return RC.FAILED;
		} finally {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				log.error("Cannot close connection");
				return RC.FAILED;
			}
		}
		return RC.OK;
	}

	/**
	 * Rollback a transaction in ThreadLocal connection
	 * @return RC.OK if success, RC.FAILED otherwise
	 */
	public RC rollback() {
		Connection conn = thConn.get();
		if(conn == null) {
			log.error("Connection is null");
			return RC.FAILED;
		}
		
		try {
			conn.rollback();
		} catch (SQLException e) {
			log.error("Cannot rollback transaction");
			return RC.FAILED;
		} finally {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				log.error("Cannot close connection");
				return RC.FAILED;
			}
		}
		return RC.OK;
	}
}
