package com.formation.computerdb.persistence.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.formation.computerdb.core.domain.Company;
import com.formation.computerdb.persistence.dao.CompanyDAO;


/**
 * Implementation of the interface @CompanyDAO
 * @author F. Miglianico
 *
 */
@Repository
@Transactional(readOnly=true)
public class CompanyDAOImpl implements CompanyDAO {
	
	//private static Logger log = LoggerFactory.getLogger(CompanyDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected CompanyDAOImpl() {
	}
	
	/**
	 * Get all the companies in DB
	 */
	public List<Company> getAll() {
		
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Company.class);
		
		@SuppressWarnings("unchecked")
		List<Company> companies = criteria.list();
			
		return companies;
	}

	/**
	 * get a the company with the id given as parameter
	 * @param id the id
	 */
	public Company get(int id) {

		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Company.class).add(Restrictions.eq("id", new Long(id)));
		
		return (Company) criteria.uniqueResult();
	}
}
