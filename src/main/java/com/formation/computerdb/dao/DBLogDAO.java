package com.formation.computerdb.dao;

import com.formation.computerdb.domain.DBLog;

public interface DBLogDAO {
	public DBLog get(int id);
	public void create(DBLog dbLog);
}
