package com.formation.computerdb.persistence.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formation.computerdb.core.domain.DBLog;

@Repository
public interface DBLogDAO extends CrudRepository<DBLog, Long> {
}
