package com.formation.computerdb.persistence.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.formation.computerdb.core.domain.DBLog;
import com.formation.computerdb.persistence.dao.BaseDAO;
import com.formation.computerdb.persistence.dao.DBLogDAO;


/**
 * Implementation of DBLogDAO
 * Log user actions in DB in the db_log table
 * @author F. Miglianico
 *
 */
@Repository
public class DBLogDAOImpl extends BaseDAO implements DBLogDAO {
	
	//private final static Logger log = LoggerFactory.getLogger(DBLogDAOImpl.class);
	
	/**
	 * Persist a log in DB
	 */
	public void create(final DBLog dbLog) {
		
		String query = "INSERT INTO db_log (action, date, description) VALUES (?, ?, ?)";
		
		jdbcTemplate.update(query, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, dbLog.getActionType().toString());
				
				if(dbLog.getDate() != null)
					ps.setTimestamp(2, new java.sql.Timestamp(dbLog.getDate().getMillis()));
				else
					ps.setNull(2, java.sql.Types.TIMESTAMP);
				
				ps.setString(3, dbLog.getDescription());
				
			}
		});
	}
}
