package com.formation.computerdb.dao;

import java.sql.Connection;

import com.formation.computerdb.domain.DBLog;

public interface DBLogDAO {
	public DBLog get(int id, Connection conn);
	public void create(DBLog dbLog, Connection conn);
}
