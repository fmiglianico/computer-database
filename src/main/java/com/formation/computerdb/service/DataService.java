package com.formation.computerdb.service;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class DataService {
	
	private static final Logger log = LoggerFactory.getLogger(DataService.class);

	@Autowired
	private ComputerDAO computerDAO;

	@Autowired
	private CompanyDAO companyDAO;

	@Autowired
	private DBLogDAO dbLogDAO;
	
	@Autowired
	private DAOFactory daoFactory;
	
	public Computer getComputer(int id) {
		daoFactory.setConn();
		Computer computer = computerDAO.get(id);
		daoFactory.commit();
		
		return computer;
	}
	
	public RC createComputer(Computer computer) {
		
		daoFactory.setConn();
		
		try {
			computerDAO.create(computer);
			DBLog dbLog;
			if(computer.getId() != null)
				dbLog = new DBLog(DBLogActionType.COMPUTER_ADDED, Calendar.getInstance().getTime(), computer.getId().toString());
			else
				dbLog = new DBLog(DBLogActionType.COMPUTER_ADDED, Calendar.getInstance().getTime(), "Unknown ID");
			
			dbLogDAO.create(dbLog);
		} catch (SQLException e) {
			daoFactory.rollback();
			log.error("An error occured while trying to create a computer. Transaction rollbacked.", e);
			return RC.FAILED;
		}
		
		daoFactory.commit();
		return RC.OK;
	}
	
	public RC updateComputer(Computer computer) {
		daoFactory.setConn();
		
		DBLog dbLog = new DBLog(DBLogActionType.COMPUTER_UPDATED, Calendar.getInstance().getTime(), computer.getId().toString());
		try {
			computerDAO.update(computer);
			dbLogDAO.create(dbLog);
		} catch (SQLException e) {
			daoFactory.rollback();
			log.error("An error occured while trying to update a computer. Transaction rollbacked.", e);
			return RC.FAILED;
		}
		
		daoFactory.commit();
		return RC.OK;
	}
	
	public RC deleteComputer(int id) {
		daoFactory.setConn();
		
		DBLog dbLog = new DBLog(DBLogActionType.COMPUTER_DELETED, Calendar.getInstance().getTime(), "" + id);
		try {
			computerDAO.delete(id);
			dbLogDAO.create(dbLog);
		} catch (SQLException e) {
			daoFactory.rollback();
			log.error("An error occured while trying to delete a computer. Transaction rollbacked.", e);
			return RC.FAILED;
		}
		
		daoFactory.commit();
		return RC.OK;
	}
	
	public void fill(Page page) {
		daoFactory.setConn();
		computerDAO.fill(page);
		daoFactory.commit();
	}
	
	public int countComputers(String search) {
		daoFactory.setConn();
		int count = computerDAO.count(search);
		daoFactory.commit();
		
		return count;
	}
	
	public List<Company> getAllCompanies() {
		daoFactory.setConn();
		List<Company> companies = companyDAO.getAll();
		daoFactory.commit();
		
		return companies;
	}
	
	public Company getCompany(int id) {
		daoFactory.setConn();
		Company company = companyDAO.get(id);
		daoFactory.commit();
		
		return company;
	}
}
