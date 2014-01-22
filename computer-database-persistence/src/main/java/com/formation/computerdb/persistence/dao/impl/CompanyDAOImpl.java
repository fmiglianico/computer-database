package com.formation.computerdb.persistence.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.formation.computerdb.core.domain.Company;
import com.formation.computerdb.core.domain.QCompany;
import com.formation.computerdb.persistence.dao.CompanyDAO;
import com.mysema.query.jpa.impl.JPAQuery;


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
	
	/**
	 * Get all the companies in DB
	 */
	public List<Company> getAll() {
		
		JPAQuery query = new JPAQuery(em);
		
		QCompany company = QCompany.company;
			
		return query.from(company).list(company);
	}

	/**
	 * get a the company with the id given as parameter
	 * @param id the id
	 */
	public Company get(int id) {
		
		return em.find(Company.class, new Long(id));
	}
}
