package com.formation.computerdb.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.formation.computerdb.common.RC;
import com.jolbox.bonecp.BoneCPDataSource;

/**
 * DAO Factory
 * Used to get the DAOs or a connection to the DB
 * @author F. Miglianico
 *
 */
@Component
public class DAOFactory {
	
	private static final Logger log = LoggerFactory.getLogger(DAOFactory.class);
	
	private static ThreadLocal<Connection> thConn = new ThreadLocal<Connection>();
	
	@Autowired
	private BoneCPDataSource ds;
	
	// The DAOs
	@Autowired
	private ComputerDAO computerDAO;
	
	@Autowired
	private CompanyDAO companyDAO;
	
	@Autowired
	private DBLogDAO dbLogDAO;
	
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
		
		if(ds == null) {
			log.error("DataSource is null");
			return RC.FAILED;
		}
		
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
			thConn.set(conn);
		} catch (SQLException e1) {
			log.error("Error while trying to fetch a connection : " + e1.getMessage());
			e1.printStackTrace();
			return RC.FAILED;
		}
	
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			log.error("Error while setting auto-commit to false : " + e.getMessage(), e);
			try {
				conn.close();
				conn = null;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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
