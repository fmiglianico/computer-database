package com.formation.computerdb.persistence.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
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

		return (Computer) session.createCriteria(Computer.class).add(Restrictions.idEq(new Long(id))).uniqueResult();
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
		
		Criteria criteria = session.createCriteria(Computer.class);

		// Search parameter
		String search = page.getSearch();

		if (search != null) {
			criteria.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN);
			
			search = new StringBuilder("%").append(search).append("%").toString();
			
			criteria.add(Restrictions.or(Restrictions.like("this.name", search), Restrictions.like("company.name", search)));
		}

		// Order By parameter
		OrderByColumn orderBy = page.getOrderBy();
		if (orderBy != null) {
			
			if(search == null && orderBy.getColNameShort().equals("company"))
				criteria.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN);
			
			if(orderBy.getDir().equals("asc"))
				criteria.addOrder(Order.asc(orderBy.getColName()));
			else
				criteria.addOrder(Order.desc(orderBy.getColName()));
		}
		
		// Number of rows parameter
		Integer nbRows = page.getNbRows();
		if (nbRows != null) {
			criteria.setMaxResults(nbRows);
			Integer currPage = page.getCurrPage();
			if (currPage != null) {
				int offset = (currPage - 1) * nbRows;
				criteria.setFirstResult(offset);
			}
		}

		@SuppressWarnings("unchecked")
		List<Computer> computers = criteria.list();

		// Store the resulting list of computers in the page
		page.setList(computers);

	}

	/**
	 * Counts the number of computers matching the search criteria. If search is
	 * empty, counts all the computers
	 */
	public int count(String search) {

		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Computer.class).setProjection(Projections.rowCount());
		
		if(search != null) {
			criteria.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN);
			
			search = new StringBuilder("%").append(search).append("%").toString();
			
			criteria.add(Restrictions.or(Restrictions.like("this.name", search), Restrictions.like("company.name", search)));
		}

		return ((Long)criteria.uniqueResult()).intValue();

	}

}
