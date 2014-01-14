package com.formation.computerdb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CleanupFailureDataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;

import com.formation.computerdb.common.DBLogActionType;
import com.formation.computerdb.dao.BaseDAO;
import com.formation.computerdb.dao.DBLogDAO;
import com.formation.computerdb.domain.DBLog;

/**
 * Implementation of DBLogDAO
 * Log user actions in DB in the db_log table
 * @author F. Miglianico
 *
 */
@Repository
public class DBLogDAOImpl extends BaseDAO implements DBLogDAO {
	
	private final static Logger log = LoggerFactory.getLogger(DBLogDAOImpl.class);
	
	/**
	 * Get a log
	 */
	public DBLog get(int id) {
		
		Connection conn = getConnection();
		if(conn == null) {
			log.error("Cannot retrieve connection");
			return null;
		}
		
		DBLog dbLog = null;
		ResultSet rs = null;
		

		try {
			StringBuilder query = new StringBuilder("SELECT d.action, d.date, d.description FROM db_log AS d WHERE d.id = ").append(id);

			Statement statement = conn.createStatement();
			rs = statement.executeQuery(query.toString());

			// Traitement a faire ici sur le resultset
			if(rs != null && rs.next())
				dbLog = mapDBLog(rs);

		} catch (SQLException e) {
			throw new DataRetrievalFailureException("Could not retrieve log", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				throw new CleanupFailureDataAccessException("Could not close connection", e);
			}
		}

		return dbLog;
	}
	
	/**
	 * Persist a log in DB
	 */
	public void create(DBLog dbLog) {
		
		Connection conn = getConnection();
		if(conn == null) {
			log.error("Cannot retrieve connection");
			return;
		}
		
		String query = "INSERT INTO db_log (action, date, description) VALUES (?, ?, ?)";
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, dbLog.getActionType().toString());
			
			if(dbLog.getDate() != null)
				statement.setTimestamp(2, new java.sql.Timestamp(dbLog.getDate().getMillis()));
			else
				statement.setNull(2, java.sql.Types.TIMESTAMP);
			
			statement.setString(3, dbLog.getDescription());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DataIntegrityViolationException("Could not insert log in DBLog.create", e);
		}
	}
	
	/**
	 * Creates a DBLog from a resultset
	 * @param resultSet the resultset
	 * @return the DBLog
	 */
	private DBLog mapDBLog( ResultSet resultSet ) {
		DBLog dbLog = new DBLog();

		try {
			String action = resultSet.getString("d.action");
			DBLogActionType actionType = DBLogActionType.valueOf(action);
			dbLog.setActionType(actionType);
			
			dbLog.setDate(new DateTime(resultSet.getDate("d.date")));
			dbLog.setDescription(resultSet.getString("d.description"));

		} catch (SQLException e) {
			throw new DataRetrievalFailureException("Could not map resultSet with DBLog", e);
		}

		return dbLog;
	}
}
