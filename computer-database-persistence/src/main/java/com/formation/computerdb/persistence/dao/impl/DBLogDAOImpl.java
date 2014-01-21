package com.formation.computerdb.persistence.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.formation.computerdb.core.domain.DBLog;
import com.formation.computerdb.persistence.dao.DBLogDAO;


/**
 * Implementation of DBLogDAO
 * Log user actions in DB in the db_log table
 * @author F. Miglianico
 *
 */
@Repository
public class DBLogDAOImpl implements DBLogDAO {
	
	//private final static Logger log = LoggerFactory.getLogger(DBLogDAOImpl.class);

	@PersistenceContext(unitName="persistenceUnit")
	private EntityManager em;
	
	/**
	 * Persist a log in DB
	 */
	public void create(DBLog dbLog) {
		
		em.persist(dbLog);
	}
}
