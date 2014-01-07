package com.formation.computerdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.formation.computerdb.common.DBLogActionType;
import com.formation.computerdb.domain.DBLog;

/**
 * Implementation of DBLogDAO
 * Log user actions in DB in the db_log table
 * @author F. Miglianico
 *
 */
public class DBLogDAOImpl implements DBLogDAO {
	
	/**
	 * Get a log
	 */
	public DBLog get(int id) {
		DBLog dbLog = null;
		ResultSet rs = null;
		
		Connection conn = DAOFactory.INSTANCE.getConn();

		try {
			StringBuilder query = new StringBuilder("SELECT d.action, d.date, d.description FROM db_log AS d WHERE d.id = ").append(id);

			Statement statement = conn.createStatement();
			rs = statement.executeQuery(query.toString());

			// Traitement a faire ici sur le resultset
			if(rs != null && rs.next())
				dbLog = mapDBLog(rs);

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

		return dbLog;
	}
	
	/**
	 * Persist a log in DB
	 */
	public void create(DBLog dbLog) throws SQLException {
		
		Connection conn = DAOFactory.INSTANCE.getConn();
		
		String query = "INSERT INTO db_log (action, date, description) VALUES (?, ?, ?)";
		
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, dbLog.getActionType().toString());
		
		if(dbLog.getDate() != null)
			statement.setTimestamp(2, new java.sql.Timestamp(dbLog.getDate().getTime()));
		else
			statement.setNull(2, java.sql.Types.DATE);
		
		statement.setString(3, dbLog.getDescription());
		
		statement.executeUpdate();
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
			
			dbLog.setDate(resultSet.getDate("d.date"));
			dbLog.setDescription(resultSet.getString("d.description"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dbLog;
	}
}
