package com.formation.computerdb.persistence.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

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
	
	@PersistenceContext(unitName="persistenceUnit")
	private EntityManager em;
	
	protected CompanyDAOImpl() {
	}
	
	/**
	 * Get all the companies in DB
	 */
	public List<Company> getAll() {
		
		CriteriaQuery<Company> criteria = em.getCriteriaBuilder().createQuery(Company.class);
		criteria.from(Company.class);
		
		List<Company> companies = em.createQuery(criteria).getResultList();
			
		return companies;
	}

	/**
	 * get a the company with the id given as parameter
	 * @param id the id
	 */
	public Company get(int id) {
		
		return em.find(Company.class, new Long(id));
	}
}
