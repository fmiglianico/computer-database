package com.formation.computerdb.service;

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
	
	public Computer getComputer(int id) {
		DAOFactory.INSTANCE.setConn();
		Computer computer = computerDAO.get(id);
		DAOFactory.INSTANCE.commit();
		
		return computer;
	}
	
	public RC createComputer(Computer computer) {
		
		DAOFactory.INSTANCE.setConn();
		
		try {
			computerDAO.create(computer);
			DBLog dbLog;
			if(computer.getId() != null)
				dbLog = new DBLog(DBLogActionType.COMPUTER_ADDED, Calendar.getInstance().getTime(), computer.getId().toString());
			else
				dbLog = new DBLog(DBLogActionType.COMPUTER_ADDED, Calendar.getInstance().getTime(), "Unknown ID");
			
			dbLogDAO.create(dbLog);
		} catch (SQLException e) {
			DAOFactory.INSTANCE.rollback();
			log.error("An error occured while trying to create a computer. Transaction rollbacked.", e);
			return RC.FAILED;
		}
		
		DAOFactory.INSTANCE.commit();
		return RC.OK;
	}
	
	public RC updateComputer(Computer computer) {
		DAOFactory.INSTANCE.setConn();
		
		DBLog dbLog = new DBLog(DBLogActionType.COMPUTER_UPDATED, Calendar.getInstance().getTime(), computer.getId().toString());
		try {
			computerDAO.update(computer);
			dbLogDAO.create(dbLog);
		} catch (SQLException e) {
			DAOFactory.INSTANCE.rollback();
			log.error("An error occured while trying to update a computer. Transaction rollbacked.", e);
			return RC.FAILED;
		}
		
		DAOFactory.INSTANCE.commit();
		return RC.OK;
	}
	
	public RC deleteComputer(int id) {
		DAOFactory.INSTANCE.setConn();
		
		DBLog dbLog = new DBLog(DBLogActionType.COMPUTER_DELETED, Calendar.getInstance().getTime(), "" + id);
		try {
			computerDAO.delete(id);
			dbLogDAO.create(dbLog);
		} catch (SQLException e) {
			DAOFactory.INSTANCE.rollback();
			log.error("An error occured while trying to delete a computer. Transaction rollbacked.", e);
			return RC.FAILED;
		}
		
		DAOFactory.INSTANCE.commit();
		return RC.OK;
	}
	
	public void fill(Page page) {
		DAOFactory.INSTANCE.setConn();
		computerDAO.fill(page);
		DAOFactory.INSTANCE.commit();
	}
	
	public int countComputers(String search) {
		DAOFactory.INSTANCE.setConn();
		int count = computerDAO.count(search);
		DAOFactory.INSTANCE.commit();
		
		return count;
	}
	
	public List<Company> getAllCompanies() {
		DAOFactory.INSTANCE.setConn();
		List<Company> companies = companyDAO.getAll();
		DAOFactory.INSTANCE.commit();
		
		return companies;
	}
	
	public Company getCompany(int id) {
		DAOFactory.INSTANCE.setConn();
		Company company = companyDAO.get(id);
		DAOFactory.INSTANCE.commit();
		
		return company;
	}
}
