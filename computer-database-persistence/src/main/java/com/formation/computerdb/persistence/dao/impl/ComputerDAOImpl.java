package com.formation.computerdb.persistence.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CleanupFailureDataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.formation.computerdb.core.common.OrderByColumn;
import com.formation.computerdb.core.common.Page;
import com.formation.computerdb.core.domain.Company;
import com.formation.computerdb.core.domain.Computer;
import com.formation.computerdb.persistence.dao.BaseDAO;
import com.formation.computerdb.persistence.dao.ComputerDAO;


/**
 * Implementation of the interface @ComputerDAO
 * @author F. Miglianico
 *
 */
@Repository
@Transactional(readOnly=true)
public class ComputerDAOImpl extends BaseDAO implements ComputerDAO {
	
	private static Logger log = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	protected ComputerDAOImpl() {
	}

	/**
	 * Get a computer
	 * @param id the id
	 */
	public Computer get(int id) {
		
		Connection conn = getConnection();
		if(conn == null) {
			log.error("Cannot retrieve connection");
			return null;
		}

		Computer computer = null;
		ResultSet rs = null;

		try {

			// Build the query
			StringBuilder query = new StringBuilder("SELECT cr.id, cr.name, cr.introduced, cr.discontinued, cy.id, cy.name ");
			query.append("FROM computer AS cr ");
			query.append("LEFT JOIN company AS cy ON cr.company_id = cy.id ");
			query.append("WHERE cr.id = ").append(id);

			// Create hte statement
			Statement statement = conn.createStatement();
			rs = statement.executeQuery(query.toString());

			// Process the result
			if(rs.next())
				computer = mapComputer(rs);

		} catch (SQLException e) {
			throw new DataRetrievalFailureException("Could not retrieve computer", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				throw new CleanupFailureDataAccessException("Could not close connection", e);
			}
		}

		return computer;
	}
	
	/**
	 * Create a computer in DB
	 */
	@Transactional(readOnly=false)
	public void create(Computer computer) {
		
		Connection conn = getConnection();
		if(conn == null) {
			log.error("Cannot retrieve connection");
			return;
		}

		String query = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?)";
		
		
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, computer.getName());
			if(computer.getCompany() != null)
				statement.setLong(2, computer.getCompany().getId());
			else
				statement.setNull(2, java.sql.Types.BIGINT);
			
			if(computer.getIntroduced() != null)
				statement.setTimestamp(3, new Timestamp(computer.getIntroduced().getMillis()));
			else
				statement.setNull(3, java.sql.Types.TIMESTAMP);
			
			if(computer.getDiscontinued() != null)
				statement.setTimestamp(4, new Timestamp(computer.getDiscontinued().getMillis()));
			else
				statement.setNull(4, java.sql.Types.TIMESTAMP);
			
			statement.executeUpdate();
			
			rs = statement.getGeneratedKeys();
			Long id = null;
			
			if (rs.next()) {
		        id = rs.getLong(1);
		        computer.setId(id);
		    } else {
		    	log.error("Could not retrieve id after creating a computer");
		    }
		} catch (SQLException e) {
			throw new DataIntegrityViolationException("Could not create computer", e);
		} finally {
			try {
				if(rs != null)
					rs.close();
			} catch (SQLException e1) {
				throw new CleanupFailureDataAccessException("Could not close connection", e1);
			}
		    rs = null;
		}
	    
	}
	
	/**
	 * Updates the computer in DB.
	 * The id needs to be set.
	 */
	@Transactional(readOnly=false)
	public void update(Computer computer) {
		
		Connection conn = getConnection();
		if(conn == null) {
			log.error("Cannot retrieve connection");
			return;
		}
		
		if(computer.getId() == null) {
			log.error("Trying to update a computer without id : " + computer.toString());
			return;
		}
			
		// Build query
		StringBuilder query = new StringBuilder("UPDATE computer SET name = ?, company_id = ?, introduced = ?, discontinued = ? ");
		query.append("WHERE id = ").append(computer.getId());
		
		try {
			// Create prepared statement
			PreparedStatement statement = conn.prepareStatement(query.toString());
			statement.setString(1, computer.getName());
			
			// Set variables
			if(computer.getCompany() != null)
				statement.setLong(2, computer.getCompany().getId());
			else
				statement.setNull(2, java.sql.Types.BIGINT);
	
			if(computer.getIntroduced() != null)
				statement.setTimestamp(3, new Timestamp(computer.getIntroduced().getMillis()));
			else
				statement.setNull(3, java.sql.Types.TIMESTAMP);
			
			if(computer.getDiscontinued() != null)
				statement.setTimestamp(4, new Timestamp(computer.getDiscontinued().getMillis()));
			else
				statement.setNull(4, java.sql.Types.TIMESTAMP);
			
			// Execute query
			statement.executeUpdate();
			
		} catch(SQLException e) {
			throw new DataIntegrityViolationException("Could not create computer", e);
		}
	}
	
	/**
	 * Delete computer with id set as parameter
	 * @param id the id
	 */
	@Transactional(readOnly=false)
	public void delete(int id) {
		
		Connection conn = getConnection();
		if(conn == null) {
			log.error("Cannot retrieve connection");
			return;
		}
			
		String query = "DELETE FROM computer WHERE id = " + id;

		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			throw new DataAccessResourceFailureException("Could not delete computer", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void fill(Page page) {
		
		Connection conn = getConnection();
		if(conn == null) {
			log.error("Cannot retrieve connection");
			return;
		}

		List<Computer> computers = new ArrayList<Computer>();
		ResultSet rs = null;

		try {
			
			// Build query, depends on the page parameters
			StringBuilder query = new StringBuilder("SELECT cr.id, cr.name, cr.introduced, cr.discontinued, cy.id, cy.name ");
			query.append("FROM computer AS cr ");
			query.append("LEFT JOIN company AS cy ON cr.company_id = cy.id ");
			
				// Search parameter
			String search = page.getSearch();
			
			if(search != null) {
				query.append("WHERE cr.name LIKE '%").append(search).append("%' ");
				query.append("OR cy.name LIKE '%").append(search).append("%' ");
			}
			
				// Order By parameter
			OrderByColumn orderBy = page.getOrderBy();
			if(orderBy != null)
				query.append("ORDER BY ").append(orderBy.getColNumber()).append(" ").append(orderBy.getDir()).append(" ");
			
				// Number of rows parameter
			Integer nbRows = page.getNbRows();
			if(nbRows != null) {
				query.append("LIMIT ");
				Integer currPage = page.getCurrPage();
				if(currPage != null) {
					int offset = (currPage - 1) * nbRows;
					query.append(offset).append(", ");
				}
				query.append(nbRows);
			}

			// Create statement
			Statement statement = conn.createStatement();
			
			// Execute statement
			rs = statement.executeQuery(query.toString());

			// Traitement a faire ici sur le resultset
			while(rs.next())
				computers.add(mapComputer(rs));

		} catch (SQLException e) {
			throw new DataRetrievalFailureException("Could not retrieve computers to fill page", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e1) {
				throw new CleanupFailureDataAccessException("Could not close connection", e1);
			}
		}
		
		// Store the resulting list of computers in the page
		page.setList(computers);
	}
	
	/**
	 * Counts the number of computers matching the search criteria.
	 * If search is empty, counts all the computers
	 */
	public int count(String search) {
		
		Connection conn = getConnection();
		if(conn == null) {
			log.error("Cannot retrieve connection");
			return 0;
		}

		ResultSet rs = null;
		Long count = null;

		try {

			// Build query
			StringBuilder query = new StringBuilder();
			if(search == null) {
				query.append("SELECT COUNT(id) FROM computer");
			} else {
				query.append("SELECT COUNT(cr.id) ");
				query.append("FROM computer AS cr ");
				query.append("LEFT JOIN company AS cy ON cr.company_id = cy.id ");
				query.append("WHERE cr.name LIKE '%").append(search).append("%' ");
				query.append("OR cy.name LIKE '%").append(search).append("%'");
			}

			// Create statement
			Statement statement = conn.createStatement();
			
			// Execute query
			rs = statement.executeQuery(query.toString());

			// Traitement a faire ici sur le resultsets
			if(rs.next())
				count = rs.getLong(1);

		} catch (SQLException e) {
			throw new DataRetrievalFailureException("Could not count computers to fill page", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e1) {
				throw new CleanupFailureDataAccessException("Could not close connection", e1);
			}
		}
		return (count == null) ? 0 : count.intValue();
	}

	/**
	 * Map a resultset to a computer
	 * @param resultSet the resultset
	 * @return the computer
	 */
	private Computer mapComputer( ResultSet resultSet ) {
		
		if(resultSet == null)
			return null;
		
		Computer computer = new Computer();

		try {
			computer.setId(resultSet.getLong("cr.id"));
			computer.setName(resultSet.getString("cr.name"));
			Timestamp ts = resultSet.getTimestamp("cr.introduced");
			if(ts != null)
				computer.setIntroduced(new DateTime(ts));
			ts = resultSet.getTimestamp("cr.discontinued");
			if(ts != null)
				computer.setDiscontinued(new DateTime(ts));
			
			Company company = new Company();

			company.setId(resultSet.getLong("cy.id"));
			company.setName(resultSet.getString("cy.name"));
			
			computer.setCompany(company);

		} catch (SQLException e) {
			throw new DataRetrievalFailureException("Could not map resultSet with Computer", e);
		}

		return computer;
	}
}
