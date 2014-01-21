package com.formation.computerdb.persistence.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.formation.computerdb.core.common.OrderByColumn;
import com.formation.computerdb.core.common.Page;
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

	@Autowired
	private SessionFactory sessionFactory;

	protected ComputerDAOImpl() {
	}

	/**
	 * Get a computer
	 * 
	 * @param id the id
	 */
	public Computer get(int id) {
		
		Session session = sessionFactory.getCurrentSession();

		return (Computer) session.get(Computer.class, new Long(id));
	}

	/**
	 * Create a computer in DB
	 */
	@Transactional(readOnly = false)
	public void create(Computer computer) {

		Session session = sessionFactory.getCurrentSession();
		
		session.save(computer);

	}

	/**
	 * Updates the computer in DB. The id needs to be set.
	 */
	@Transactional(readOnly = false)
	public void update(Computer computer) {

		Session session = sessionFactory.getCurrentSession();
		
		session.update(computer);
	}

	/**
	 * Delete computer with id set as parameter
	 * 
	 * @param id
	 *            the id
	 */
	@Transactional(readOnly = false)
	public void delete(int id) {
		
		Computer computer = new Computer();
		computer.setId(new Long(id));
		
		Session session = sessionFactory.getCurrentSession();
		
		session.delete(computer);
	}

	/**
	 * {@inheritDoc}
	 */
	public void fill(Page page) {

		Session session = sessionFactory.getCurrentSession();
		
		// Build query, depends on the page parameters
		StringBuilder query = new StringBuilder("FROM Computer computer ");

		// Search parameter
		String search = page.getSearch();

		if (search != null) {
			query.append("WHERE computer.name LIKE :search ");
			query.append("OR computer.company.name LIKE :search ");
			
		}

		// Order By parameter
		OrderByColumn orderBy = page.getOrderBy();
		if (orderBy != null) {
			query.append("ORDER BY :col :dir ");
		}
		
		Query q = session.createQuery(query.toString());
		
		if(search != null)
			q.setString("search", "%" + search + "%");
		
		if(orderBy != null) {
			q.setString("col", orderBy.getColName());
			q.setString("dir", orderBy.getDir());
		}
		
		// Number of rows parameter
		Integer nbRows = page.getNbRows();
		if (nbRows != null) {
			q.setMaxResults(nbRows);
			Integer currPage = page.getCurrPage();
			if (currPage != null) {
				int offset = (currPage - 1) * nbRows;
				q.setFirstResult(offset);
			}
		}

		@SuppressWarnings("unchecked")
		List<Computer> computers = q.list();

		// Store the resulting list of computers in the page
		page.setList(computers);

	}

	/**
	 * Counts the number of computers matching the search criteria. If search is
	 * empty, counts all the computers
	 */
	public int count(String search) {

		Session session = sessionFactory.getCurrentSession();
		
		Query query = null;
		
		if(search == null)
			query = session.createQuery("select count(computer.id) from Computer computer");
		else {
			query = session.createQuery("select count(computer.id) from Computer computer where computer.name LIKE :search OR computer.company.name LIKE :search");
			query.setString("search", "%" + search + "%");
		}

		return ((Long)query.uniqueResult()).intValue();

	}

}
