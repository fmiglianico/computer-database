package com.formation.computerdb.persistence.dao;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.jolbox.bonecp.BoneCPDataSource;

public class BaseDAO {
	@Autowired
    private BoneCPDataSource dataSource;

    public Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
}
