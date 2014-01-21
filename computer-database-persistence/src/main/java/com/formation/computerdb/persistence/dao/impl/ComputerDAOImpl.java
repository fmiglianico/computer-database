package com.formation.computerdb.persistence.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.formation.computerdb.core.common.OrderByColumn;
import com.formation.computerdb.core.common.Page;
import com.formation.computerdb.core.domain.Company;
import com.formation.computerdb.core.domain.Computer;
import com.formation.computerdb.persistence.dao.ComputerDAO;

/**
 * Implementation of the interface @ComputerDAO
 * 
 * @author F. Miglianico
 * 
 */
@Repository
@Transactional(readOnly = true)
public class ComputerDAOImpl implements ComputerDAO {

	// private static Logger log =
	// LoggerFactory.getLogger(ComputerDAOImpl.class);

	@PersistenceContext(unitName="persistenceUnit")
	private EntityManager em;

	protected ComputerDAOImpl() {
	}

	/**
	 * Get a computer
	 * 
	 * @param id the id
	 */
	public Computer get(int id) {

		return em.find(Computer.class, new Long(id));
	}

	/**
	 * Create a computer in DB
	 */
	@Transactional(readOnly = false)
	public void create(Computer computer) {

		em.persist(computer);

	}

	/**
	 * Updates the computer in DB. The id needs to be set.
	 */
	@Transactional(readOnly = false)
	public void update(Computer computer) {

		em.merge(computer);
	}

	/**
	 * Delete computer with id set as parameter
	 * 
	 * @param id
	 *            the id
	 */
	@Transactional(readOnly = false)
	public void delete(int id) {
		
		Computer computer = em.find(Computer.class, new Long(id));
		em.remove(computer);
	}

	/**
	 * {@inheritDoc}
	 */
	public void fill(Page page) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
		
		Root<Computer> computer = cq.from(Computer.class);
		Join<Computer, Company> company = computer.join("company", JoinType.LEFT);
		
		// Search parameter
		String search = page.getSearch();
		
		if(search != null) {
			search = new StringBuilder("%").append(search).append("%").toString();
			cq.where(cb.or(cb.like(computer.get("name").as(String.class), search), cb.like(company.get("name").as(String.class), search)));
		}

		// Order By parameter
		OrderByColumn orderBy = page.getOrderBy();
		if (orderBy != null) {
			if(orderBy.getColNameShort().equals("company")) {
				if(orderBy.getDir().equals("asc"))
					cq.orderBy(cb.asc(company.get(orderBy.getColName())));
				else
					cq.orderBy(cb.desc(company.get(orderBy.getColName())));
			} else {
				if(orderBy.getDir().equals("asc"))
					cq.orderBy(cb.asc(computer.get(orderBy.getColName())));
				else
					cq.orderBy(cb.desc(computer.get(orderBy.getColName())));
				
			}
		}

		Query query = em.createQuery(cq);
		
		// Number of rows parameter
		Integer nbRows = page.getNbRows();
		if (nbRows != null) {
			query.setMaxResults(nbRows);
			Integer currPage = page.getCurrPage();
			if (currPage != null) {
				int offset = (currPage - 1) * nbRows;
				query.setFirstResult(offset);
			}
		}

		@SuppressWarnings("unchecked")
		List<Computer> computers = query.getResultList();

		// Store the resulting list of computers in the page
		page.setList(computers);

	}

	/**
	 * Counts the number of computers matching the search criteria. If search is
	 * empty, counts all the computers
	 */
	public int count(String search) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		// Count the total employees
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Computer> computer = cq.from(Computer.class);
		cq.select(cb.count(computer));

		if(search != null) {
			Join<Computer, Company> company = computer.join("company", JoinType.LEFT);
			
			search = new StringBuilder("%").append(search).append("%").toString();
			cq.where(cb.or(cb.like(computer.get("name").as(String.class), search), cb.like(company.get("name").as(String.class), search)));
		}
		
		Query query = em.createQuery(cq);
		
		return ((Long)query.getSingleResult()).intValue();

	}

}
