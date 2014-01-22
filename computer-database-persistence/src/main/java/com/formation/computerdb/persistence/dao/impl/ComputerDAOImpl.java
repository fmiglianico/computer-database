package com.formation.computerdb.persistence.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.formation.computerdb.core.common.OrderByColumn;
import com.formation.computerdb.core.common.Page;
import com.formation.computerdb.core.domain.Computer;
import com.formation.computerdb.core.domain.QComputer;
import com.formation.computerdb.persistence.dao.ComputerDAO;
import com.mysema.query.jpa.impl.JPAQuery;

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
		
		JPAQuery query = new JPAQuery(em);

		QComputer computer = QComputer.computer;

		query = query.from(computer).leftJoin(computer.company);
		
		// Search parameter
		String search = page.getSearch();
		
		if(search != null) {
			search = new StringBuilder("%").append(search).append("%").toString();
			query = query.where(computer.name.like(search).or(computer.company.name.like(search)));
		}

		// Order By parameter
		OrderByColumn orderBy = page.getOrderBy();
		if (orderBy != null) {
			query.orderBy(orderBy.getValue());
		}
		
		// Number of rows parameter
		Integer nbRows = page.getNbRows();
		if (nbRows != null) {
			query = query.limit(nbRows);
			Integer currPage = page.getCurrPage();
			if (currPage != null) {
				int offset = (currPage - 1) * nbRows;
				query = query.offset(offset);
			}
		}

		// Store the resulting list of computers in the page
		page.setList(query.list(computer));

	}

	/**
	 * Counts the number of computers matching the search criteria. If search is
	 * empty, counts all the computers
	 */
	public int count(String search) {

		
		JPAQuery query = new JPAQuery(em);

		QComputer computer = QComputer.computer;

		query = query.from(computer).leftJoin(computer.company);

		if(search != null) {
			search = new StringBuilder("%").append(search).append("%").toString();
			query = query.where(computer.name.like(search).or(computer.company.name.like(search)));
		}
		
		return (new Long(query.count())).intValue();

	}

}
