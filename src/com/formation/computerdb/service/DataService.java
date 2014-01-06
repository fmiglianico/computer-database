package com.formation.computerdb.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.computerdb.common.DBLogActionType;
import com.formation.computerdb.common.RC;
import com.formation.computerdb.dao.CompanyDAO;
import com.formation.computerdb.dao.ComputerDAO;
import com.formation.computerdb.dao.DAOFactory;
import com.formation.computerdb.dao.DBLogDAO;
import com.formation.computerdb.domain.Company;
import com.formation.computerdb.domain.Computer;
import com.formation.computerdb.domain.DBLog;
import com.formation.computerdb.domain.Page;

/**
 * Data service to access DAO functionalities
 * @author F. Miglianico
 *
 */
public class DataService {
	
	private static final DataService INSTANCE = new DataService();
	
	private static final Logger log = LoggerFactory.getLogger(DataService.class);

	private ComputerDAO computerDAO = null;
	private CompanyDAO companyDAO = null;
	private DBLogDAO dbLogDAO = null;
	
	private DataService() {
		computerDAO = DAOFactory.INSTANCE.getComputerDAO();
		companyDAO = DAOFactory.INSTANCE.getCompanyDAO();
		dbLogDAO = DAOFactory.INSTANCE.getDBLogDAO();
	}
	
	public static DataService getInstance() {
		return INSTANCE;
	}
	
	private Connection init() {
		Connection conn = DAOFactory.INSTANCE.getConn();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			log.error("Error while setting auto-commit to false : " + e.getMessage(), e);
		}
		return conn;
	}
	
	private void commit(Connection conn) {
		if(conn == null) {
			log.error("Connection is null");
		}
		
		try {
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("Cannot rollback transaction");
			}
			log.error("Cannot commit transaction");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Cannot close connection");
			}
		}
	}
	
	private void rollback(Connection conn) {
		if(conn == null) {
			log.error("Connection is null");
		}
		
		try {
			conn.rollback();
		} catch (SQLException e) {
			log.error("Cannot rollback transaction");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Cannot close connection");
			}
		}
	}
	
	public Computer getComputer(int id) {
		Connection conn = init();
		Computer computer = computerDAO.get(id, conn);
		commit(conn);
		
		return computer;
	}
	
	public RC createComputer(Computer computer) {
		Connection conn = init();
		
		try {
			computerDAO.create(computer, conn);
			DBLog dbLog;
			if(computer.getId() != null)
				dbLog = new DBLog(DBLogActionType.COMPUTER_ADDED, Calendar.getInstance().getTime(), computer.getId().toString());
			else
				dbLog = new DBLog(DBLogActionType.COMPUTER_ADDED, Calendar.getInstance().getTime(), "Unknown ID");
			
			dbLogDAO.create(dbLog, conn);
		} catch (SQLException e) {
			rollback(conn);
			log.error("An error occured while trying to create a computer. Transaction rollbacked.", e);
			return RC.FAILED;
		}
		
		commit(conn);
		return RC.OK;
	}
	
	public RC updateComputer(Computer computer) {
		Connection conn = init();
		
		DBLog dbLog = new DBLog(DBLogActionType.COMPUTER_UPDATED, Calendar.getInstance().getTime(), computer.getId().toString());
		try {
			computerDAO.update(computer, conn);
			dbLogDAO.create(dbLog, conn);
		} catch (SQLException e) {
			rollback(conn);
			log.error("An error occured while trying to update a computer. Transaction rollbacked.", e);
			return RC.FAILED;
		}
		
		commit(conn);
		return RC.OK;
	}
	
	public RC deleteComputer(int id) {
		Connection conn = init();
		
		DBLog dbLog = new DBLog(DBLogActionType.COMPUTER_DELETED, Calendar.getInstance().getTime(), "" + id);
		try {
			computerDAO.delete(id, conn);
			dbLogDAO.create(dbLog, conn);
		} catch (SQLException e) {
			rollback(conn);
			log.error("An error occured while trying to delete a computer. Transaction rollbacked.", e);
			return RC.FAILED;
		}
		
		commit(conn);
		return RC.OK;
	}
	
	public void fill(Page page) {
		Connection conn = init();
		computerDAO.fill(page, conn);
		commit(conn);
	}
	
	public int countComputers(String search) {
		Connection conn = init();
		int count = computerDAO.count(search, conn);
		commit(conn);
		
		return count;
	}
	
	public List<Company> getAllCompanies() {
		Connection conn = init();
		List<Company> companies = companyDAO.getAll(conn);
		commit(conn);
		
		return companies;
	}
	
	public Company getCompany(int id) {
		Connection conn = init();
		Company company = companyDAO.get(id, conn);
		commit(conn);
		
		return company;
	}
}
