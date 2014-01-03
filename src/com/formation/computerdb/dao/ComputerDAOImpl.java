package com.formation.computerdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.computerdb.common.OrderByColumn;
import com.formation.computerdb.domain.Company;
import com.formation.computerdb.domain.Computer;
import com.formation.computerdb.domain.Page;

/**
 * Implementation of the interface @ComputerDAO
 * @author F. Miglianico
 *
 */
public class ComputerDAOImpl implements ComputerDAO {
	
	private static Logger log = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	protected ComputerDAOImpl() {
	}

	/**
	 * Get a computer
	 * @param id the id
	 */
	public Computer get(int id) {

		Computer computer = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			// Get the connection
			conn = DAOFactory.INSTANCE.getConn();

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
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				log.error("Error in finally: " + e.getMessage());
				e.printStackTrace();
			}
		}

		return computer;
	}
	
	/**
	 * Create a computer in DB
	 */
	public void create(Computer computer) {
		
		Connection conn = null;

		try {
			conn = DAOFactory.INSTANCE.getConn();
			
			String query = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?)";
			
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, computer.getName());
			if(computer.getCompany() != null)
				statement.setLong(2, computer.getCompany().getId());
			else
				statement.setNull(2, java.sql.Types.BIGINT);
			
			if(computer.getIntroduced() != null)
				statement.setDate(3, new java.sql.Date(computer.getIntroduced().getTimeInMillis()));
			else
				statement.setNull(3, java.sql.Types.DATE);
			
			if(computer.getDiscontinued() != null)
				statement.setDate(4, new java.sql.Date(computer.getDiscontinued().getTimeInMillis()));
			else
				statement.setNull(4, java.sql.Types.DATE);
			
			statement.executeUpdate();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				log.error("Error in finally: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Updates the computer in DB.
	 * The id needs to be set.
	 */
	public void update(Computer computer) {
		
		if(computer.getId() == null) {
			log.error("Trying to update a computer without id : " + computer.toString());
			return;
		}

		Connection conn = null;

		try {
			
			// Get a connection
			conn = DAOFactory.INSTANCE.getConn();
			
			// Build query
			StringBuilder query = new StringBuilder("UPDATE computer SET name = ?, company_id = ?, introduced = ?, discontinued = ? ");
			query.append("WHERE id = ").append(computer.getId());
			
			// Create prepared statement
			PreparedStatement statement = conn.prepareStatement(query.toString());
			statement.setString(1, computer.getName());
			
			// Set variables
			if(computer.getCompany() != null)
				statement.setLong(2, computer.getCompany().getId());
			else
				statement.setNull(2, java.sql.Types.BIGINT);
			
			if(computer.getIntroduced() != null)
				statement.setDate(3, new java.sql.Date(computer.getIntroduced().getTimeInMillis()));
			else
				statement.setNull(3, java.sql.Types.DATE);
			
			if(computer.getDiscontinued() != null)
				statement.setDate(4, new java.sql.Date(computer.getDiscontinued().getTimeInMillis()));
			else
				statement.setNull(4, java.sql.Types.DATE);
			
			// Execute query
			statement.executeUpdate();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				log.error("Error in finally: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Delete computer with id set as parameter
	 * @param id the id
	 */
	public void delete(int id) {
		
		Connection conn = DAOFactory.INSTANCE.getConn();
			
		String query = "DELETE FROM computer WHERE id = " + id;

		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(query.toString());
		} catch (SQLException e1) {
			log.error("Error while executing query : " + query, e1);
		}
		
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			log.error("Error while closing connection : " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void fill(Page page) {

		List<Computer> computers = new ArrayList<Computer>();
		ResultSet rs = null;
		Connection conn = null;

		try {
			
			// Get a connection to DB
			conn = DAOFactory.INSTANCE.getConn();
			
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
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				log.error("Error in finally: " + e.getMessage());
				e.printStackTrace();
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

		ResultSet rs = null;
		Connection conn = null;
		Long count = null;

		try {
			
			// Get a connection to the DB
			conn = DAOFactory.INSTANCE.getConn();

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
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				log.error("Error in finally: " + e.getMessage());
				e.printStackTrace();
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
		Computer computer = new Computer();

		try {
			computer.setId(resultSet.getLong("cr.id"));
			computer.setName(resultSet.getString("cr.name"));
			computer.setIntroduced(resultSet.getDate("cr.introduced"));
			computer.setDiscontinued(resultSet.getDate("cr.discontinued"));
			
			Company company = new Company();

			company.setId(resultSet.getLong("cy.id"));
			company.setName(resultSet.getString("cy.name"));
			
			computer.setCompany(company);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return computer;
	}
}
