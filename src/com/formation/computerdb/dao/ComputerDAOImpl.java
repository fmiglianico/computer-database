package com.formation.computerdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.computerdb.domain.Company;
import com.formation.computerdb.domain.Computer;

public class ComputerDAOImpl implements ComputerDAO {
	
	private static DBHandler handler = null;
	private Logger log;
	
	protected ComputerDAOImpl() {
		log = LoggerFactory.getLogger(this.getClass());
	}

	public ArrayList<Computer> getAll(int offset, int max, String orderBy, String dir) {
		if(handler == null) {
			handler = DBHandler.getInstance();
		}

		ArrayList<Computer> computers = new ArrayList<Computer>();
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = handler.getConn();
			
			int orderCol = 1;
			if(orderBy != null) {
				if(orderBy.equals("name"))
					orderCol = 2;
				else if(orderBy.equals("intro"))
					orderCol = 3;
				else if(orderBy.equals("disco"))
					orderCol = 4;
				else if(orderBy.equals("company"))
					orderCol = 6;
			}
			
			String dirString = "ASC";
			if(dir != null) {
				dir = dir.toLowerCase();
				if(dir.equals("desc"))
					dirString = "DESC";
			}
			
			String query = "SELECT cr.id, cr.name, cr.introduced, cr.discontinued, cy.id, cy.name "
					+ "FROM computer AS cr "
					+ "LEFT JOIN company AS cy ON cr.company_id = cy.id "
					+ "ORDER BY " + orderCol + " " + dirString + " "
					+ "LIMIT " + offset + ", " + max;
			rs = handler.executeQueryRS(conn, query);

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
		return computers;
	}

	public Computer get(int id) {

		if(handler == null) {
			handler = DBHandler.getInstance();
		}

		Computer computer = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = handler.getConn();

			String query = "SELECT cr.id, cr.name, cr.introduced, cr.discontinued, cy.id, cy.name "
					+ "FROM computer AS cr "
					+ "LEFT JOIN company AS cy ON cr.company_id = cy.id "
					+ "WHERE cr.id = " + id;
			rs = handler.executeQueryRS(conn, query);

			// Traitement a faire ici sur le resultset
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
	
	public void create(Computer computer) {

		if(handler == null) {
			handler = DBHandler.getInstance();
		}
		
		Connection conn = null;

		try {
			conn = handler.getConn();
			
			String query = "INSERT INTO computer (name, company_id, introduced, discontinued) "
					+ "VALUES (?, ?, ?, ?)";
			
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, computer.getName());
			if(computer.getCompany() != null)
				statement.setLong(2, computer.getCompany().getId());
			else
				statement.setNull(2, java.sql.Types.BIGINT);
			
			if(computer.getIntroduced() != null)
				statement.setDate(3, new java.sql.Date(computer.getIntroduced().getTime()));
			else
				statement.setNull(3, java.sql.Types.DATE);
			
			if(computer.getDiscontinued() != null)
				statement.setDate(4, new java.sql.Date(computer.getDiscontinued().getTime()));
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
	
	public void update(Computer computer) {

		if(handler == null) {
			handler = DBHandler.getInstance();
		}
		
		Connection conn = null;

		try {
			conn = handler.getConn();
			
			String query = "UPDATE computer SET name = ?, company_id = ?, introduced = ?, discontinued = ? "
					+ "WHERE id = " + computer.getId();
			
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, computer.getName());
			
			if(computer.getCompany() != null)
				statement.setLong(2, computer.getCompany().getId());
			else
				statement.setNull(2, java.sql.Types.BIGINT);
			
			if(computer.getIntroduced() != null)
				statement.setDate(3, new java.sql.Date(computer.getIntroduced().getTime()));
			else
				statement.setNull(3, java.sql.Types.DATE);
			
			if(computer.getDiscontinued() != null)
				statement.setDate(4, new java.sql.Date(computer.getDiscontinued().getTime()));
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
	
	public void delete(int id) {
		
		if(handler == null) {
			handler = DBHandler.getInstance();
		}
		
		Connection conn = handler.getConn();
			
		String query = "DELETE FROM computer WHERE id = " + id;
		
		handler.executeQuery(conn, query);
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			log.error("Error while closing connection : " + e.getMessage());
			e.printStackTrace();
		}
	}

	public ArrayList<Computer> search(String search, int offset, int max, String orderBy, String dir) {
		if(handler == null) {
			handler = DBHandler.getInstance();
		}

		ArrayList<Computer> computers = new ArrayList<Computer>();
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = handler.getConn();


			int orderCol = 1;
			if(orderBy != null) {
				if(orderBy.equals("name"))
					orderCol = 2;
				else if(orderBy.equals("intro"))
					orderCol = 3;
				else if(orderBy.equals("disco"))
					orderCol = 4;
				else if(orderBy.equals("company"))
					orderCol = 6;
			}
			
			String dirString = "ASC";
			if(dir != null) {
				dir = dir.toLowerCase();
				if(dir.equals("desc"))
					dirString = "DESC";
			}
			
			String query = "SELECT cr.id, cr.name, cr.introduced, cr.discontinued, cy.id, cy.name "
					+ "FROM computer AS cr "
					+ "LEFT JOIN company AS cy ON cr.company_id = cy.id "
					+ "WHERE cr.name LIKE '%" + search + "%' "
					+ "OR cy.name LIKE '%" + search + "%' "
					+ "ORDER BY " + orderCol + " " + dirString + " "
					+ "LIMIT " + offset + ", " + max;
			rs = handler.executeQueryRS(conn, query);

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
		return computers;
	}
	
	public int count(String search) {
		if(handler == null) {
			handler = DBHandler.getInstance();
		}

		ResultSet rs = null;
		Connection conn = null;
		Long count = null;

		try {
			conn = handler.getConn();

			String query = null;
			
			if(search == null) {
				query = "SELECT COUNT(id) FROM computer";
			} else {
				query = "SELECT COUNT(cr.id) "
					+ "FROM computer AS cr "
					+ "LEFT JOIN company AS cy ON cr.company_id = cy.id "
					+ "WHERE cr.name LIKE '%" + search + "%' "
					+ "OR cy.name LIKE '%" + search + "%'";
			}
			
			rs = handler.executeQueryRS(conn, query);

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
