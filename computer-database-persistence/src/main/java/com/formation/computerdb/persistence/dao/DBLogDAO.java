package com.formation.computerdb.persistence.dao;

import com.formation.computerdb.core.domain.DBLog;


public interface DBLogDAO {
	public DBLog get(int id);
	public void create(DBLog dbLog);
}