package com.formation.computerdb.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.computerdb.common.DBLogActionType;
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
	
	private Connection initTransaction() {
		Connection conn = DAOFactory.INSTANCE.getConn();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			log.error("Error while setting auto-commit to false : " + e.getMessage(), e);
		}
		return conn;
	}
	
	private void commitTransaction(Connection conn) {
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
	
	public Computer getComputer(int id) {
		Connection conn = initTransaction();
		Computer computer = computerDAO.get(id, conn);
		commitTransaction(conn);
		
		return computer;
	}
	
	public void createComputer(Computer computer) {
		Connection conn = initTransaction();
		
		computerDAO.create(computer, conn);
		DBLog dbLog;
		if(computer.getId() != null)
			dbLog = new DBLog(DBLogActionType.COMPUTER_ADDED, Calendar.getInstance().getTime(), computer.getId().toString());
		else
			dbLog = new DBLog(DBLogActionType.COMPUTER_ADDED, Calendar.getInstance().getTime(), "Unknown ID");
		
		dbLogDAO.create(dbLog, conn);
		
		commitTransaction(conn);
	}
	
	public void updateComputer(Computer computer) {
		Connection conn = initTransaction();
		
		DBLog dbLog = new DBLog(DBLogActionType.COMPUTER_UPDATED, Calendar.getInstance().getTime(), computer.getId().toString());
		computerDAO.update(computer, conn);
		dbLogDAO.create(dbLog, conn);
		
		commitTransaction(conn);
	}
	
	public void deleteComputer(int id) {
		Connection conn = initTransaction();
		
		DBLog dbLog = new DBLog(DBLogActionType.COMPUTER_DELETED, Calendar.getInstance().getTime(), "" + id);
		computerDAO.delete(id, conn);
		dbLogDAO.create(dbLog, conn);
		
		commitTransaction(conn);
	}
	
	public void fill(Page page) {
		Connection conn = initTransaction();
		computerDAO.fill(page, conn);
		commitTransaction(conn);
	}
	
	public int countComputers(String search) {
		Connection conn = initTransaction();
		int count = computerDAO.count(search, conn);
		commitTransaction(conn);
		
		return count;
	}
	
	public List<Company> getAllCompanies() {
		Connection conn = initTransaction();
		List<Company> companies = companyDAO.getAll(conn);
		commitTransaction(conn);
		
		return companies;
	}
	
	public Company getCompany(int id) {
		Connection conn = initTransaction();
		Company company = companyDAO.get(id, conn);
		commitTransaction(conn);
		
		return company;
	}
}
