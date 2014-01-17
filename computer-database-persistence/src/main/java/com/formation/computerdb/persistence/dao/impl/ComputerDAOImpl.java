package com.formation.computerdb.persistence.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.formation.computerdb.core.common.OrderByColumn;
import com.formation.computerdb.core.common.Page;
import com.formation.computerdb.core.domain.Computer;
import com.formation.computerdb.persistence.dao.BaseDAO;
import com.formation.computerdb.persistence.dao.ComputerDAO;
import com.formation.computerdb.persistence.mapper.ComputerRowMapper;

/**
 * Implementation of the interface @ComputerDAO
 * 
 * @author F. Miglianico
 * 
 */
@Repository
@Transactional(readOnly = true)
public class ComputerDAOImpl extends BaseDAO implements ComputerDAO {

	// private static Logger log =
	// LoggerFactory.getLogger(ComputerDAOImpl.class);

	protected ComputerDAOImpl() {
	}

	/**
	 * Get a computer
	 * 
	 * @param id
	 *            the id
	 */
	public Computer get(int id) {

		// Build the query
		StringBuilder query = new StringBuilder(
				"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name ");
		query.append("FROM computer ");
		query.append("LEFT JOIN company ON computer.company_id = company.id ");
		query.append("WHERE computer.id = ?");

		return jdbcTemplate.queryForObject(query.toString(), new Object[] {id},
				new ComputerRowMapper());
	}

	/**
	 * Create a computer in DB
	 */
	@Transactional(readOnly = false)
	public void create(Computer computer) {

		final Computer c = computer;

		final String query = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);

				ps.setString(1, c.getName());
				if (c.getCompany() != null)
					ps.setLong(2, c.getCompany().getId());
				else
					ps.setNull(2, java.sql.Types.BIGINT);

				if (c.getIntroduced() != null)
					ps.setTimestamp(3, new Timestamp(c.getIntroduced()
							.getMillis()));
				else
					ps.setNull(3, java.sql.Types.TIMESTAMP);

				if (c.getDiscontinued() != null)
					ps.setTimestamp(4, new Timestamp(c.getDiscontinued()
							.getMillis()));
				else
					ps.setNull(4, java.sql.Types.TIMESTAMP);

				return ps;
			}
		}, keyHolder);

		if (keyHolder.getKey() != null)
			computer.setId(keyHolder.getKey().longValue());

	}

	/**
	 * Updates the computer in DB. The id needs to be set.
	 */
	@Transactional(readOnly = false)
	public void update(final Computer computer) {

		// Build query
		StringBuilder query = new StringBuilder(
				"UPDATE computer SET name = ?, company_id = ?, introduced = ?, discontinued = ? ");
		query.append("WHERE id = ?");

		jdbcTemplate.update(query.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, computer.getName());

				// Set variables
				if (computer.getCompany() != null)
					ps.setLong(2, computer.getCompany().getId());
				else
					ps.setNull(2, java.sql.Types.BIGINT);

				if (computer.getIntroduced() != null)
					ps.setTimestamp(3, new Timestamp(computer.getIntroduced()
							.getMillis()));
				else
					ps.setNull(3, java.sql.Types.TIMESTAMP);

				if (computer.getDiscontinued() != null)
					ps.setTimestamp(4, new Timestamp(computer.getDiscontinued()
							.getMillis()));
				else
					ps.setNull(4, java.sql.Types.TIMESTAMP);
				
				ps.setLong(5, computer.getId());

			}
		});
	}

	/**
	 * Delete computer with id set as parameter
	 * 
	 * @param id
	 *            the id
	 */
	@Transactional(readOnly = false)
	public void delete(int id) {

		String query = "DELETE FROM computer WHERE id = ?";

		jdbcTemplate.update(query, new Object[] {id});
	}

	/**
	 * {@inheritDoc}
	 */
	public void fill(Page page) {
		
		List<Object> obj = new ArrayList<Object>();

		// Build query, depends on the page parameters
		StringBuilder query = new StringBuilder(
				"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name ");
		query.append("FROM computer ");
		query.append("LEFT JOIN company ON computer.company_id = company.id ");

		// Search parameter
		String search = page.getSearch();

		if (search != null) {
			query.append("WHERE computer.name LIKE '%?%' ");
			query.append("OR company.name LIKE '%?%' ");

			obj.add(search);
			obj.add(search);
			
		}

		// Order By parameter
		OrderByColumn orderBy = page.getOrderBy();
		if (orderBy != null) {
			query.append("ORDER BY ? ? ");
			
			obj.add(orderBy.getColNumber());
			obj.add(orderBy.getDir());
		}
		
		

		// Number of rows parameter
		Integer nbRows = page.getNbRows();
		if (nbRows != null) {
			query.append("LIMIT ?");
			Integer currPage = page.getCurrPage();
			if (currPage != null) {
				int offset = (currPage - 1) * nbRows;
				obj.add(offset);
				query.append(", ?");
			}
			obj.add(nbRows);
		}

		List<Computer> computers = jdbcTemplate.query(query.toString(), obj.toArray(),
				new ComputerRowMapper());

		// Store the resulting list of computers in the page
		page.setList(computers);

	}

	/**
	 * Counts the number of computers matching the search criteria. If search is
	 * empty, counts all the computers
	 */
	public int count(String search) {

		List<Object> obj = new ArrayList<Object>();

		// Build query
		StringBuilder query = new StringBuilder();
		if (search == null) {
			query.append("SELECT COUNT(id) FROM computer");
		} else {
			query.append("SELECT COUNT(cr.id) ");
			query.append("FROM computer AS cr ");
			query.append("LEFT JOIN company AS cy ON cr.company_id = cy.id ");
			query.append("WHERE cr.name LIKE '%?%' ");
			query.append("OR cy.name LIKE '%?%'");

			obj.add(search);
			obj.add(search);
		}

		return jdbcTemplate.queryForObject(query.toString(), obj.toArray(), Integer.class);

	}

}
