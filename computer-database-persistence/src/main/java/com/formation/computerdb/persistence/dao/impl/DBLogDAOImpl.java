package com.formation.computerdb.persistence.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * Persist a log in DB
	 */
	public void create(DBLog dbLog) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(dbLog);
	}
}
