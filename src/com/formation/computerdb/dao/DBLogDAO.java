package com.formation.computerdb.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.formation.computerdb.domain.DBLog;

public interface DBLogDAO {
	public DBLog get(int id, Connection conn);
	public void create(DBLog dbLog, Connection conn) throws SQLException;
}
