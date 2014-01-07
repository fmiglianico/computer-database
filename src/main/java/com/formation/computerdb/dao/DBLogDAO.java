package com.formation.computerdb.dao;

import java.sql.SQLException;

import com.formation.computerdb.domain.DBLog;

public interface DBLogDAO {
	public DBLog get(int id);
	public void create(DBLog dbLog) throws SQLException;
}
