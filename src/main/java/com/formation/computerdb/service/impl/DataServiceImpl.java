package com.formation.computerdb.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
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
import com.formation.computerdb.service.DataService;

/**
 * Data service to access DAO functionalities
 * @author F. Miglianico
 *
 */
@Service
public class DataServiceImpl implements DataService {
	
	private static final Logger log = LoggerFactory.getLogger(DataServiceImpl.class);

	@Autowired
	private ComputerDAO computerDAO;

	@Autowired
	private CompanyDAO companyDAO;

	@Autowired
	private DBLogDAO dbLogDAO;
	
	@Autowired
	private DAOFactory daoFactory;
	
	/* (non-Javadoc)
	 * @see com.formation.computerdb.service.DataServiceI#getComputer(int)
	 */
	@Override
	public Computer getComputer(int id) {
		daoFactory.setConn();
		Computer computer = computerDAO.get(id);
		daoFactory.commit();
		
		return computer;
	}
	
	/* (non-Javadoc)
	 * @see com.formation.computerdb.service.DataServiceI#createComputer(com.formation.computerdb.domain.Computer)
	 */
	@Override
	public RC createComputer(Computer computer) {
		
		daoFactory.setConn();
		
		try {
			computerDAO.create(computer);
			DBLog dbLog;
			if(computer.getId() != null)
				dbLog = new DBLog(DBLogActionType.COMPUTER_ADDED, new DateTime(), computer.getId().toString());
			else
				dbLog = new DBLog(DBLogActionType.COMPUTER_ADDED, new DateTime(), "Unknown ID");
			
			dbLogDAO.create(dbLog);
		} catch (SQLException e) {
			daoFactory.rollback();
			log.error("An error occured while trying to create a computer. Transaction rollbacked.", e);
			return RC.FAILED;
		}
		
		daoFactory.commit();
		return RC.OK;
	}
	
	/* (non-Javadoc)
	 * @see com.formation.computerdb.service.DataServiceI#updateComputer(com.formation.computerdb.domain.Computer)
	 */
	@Override
	public RC updateComputer(Computer computer) {
		daoFactory.setConn();
		
		DBLog dbLog = new DBLog(DBLogActionType.COMPUTER_UPDATED, new DateTime(), computer.getId().toString());
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
	
	/* (non-Javadoc)
	 * @see com.formation.computerdb.service.DataServiceI#deleteComputer(int)
	 */
	@Override
	public RC deleteComputer(int id) {
		daoFactory.setConn();
		
		DBLog dbLog = new DBLog(DBLogActionType.COMPUTER_DELETED, new DateTime(), "" + id);
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
	
	/* (non-Javadoc)
	 * @see com.formation.computerdb.service.DataServiceI#fill(com.formation.computerdb.domain.Page)
	 */
	@Override
	public void fill(Page page) {
		daoFactory.setConn();
		computerDAO.fill(page);
		daoFactory.commit();
	}
	
	/* (non-Javadoc)
	 * @see com.formation.computerdb.service.DataServiceI#countComputers(java.lang.String)
	 */
	@Override
	public int countComputers(String search) {
		daoFactory.setConn();
		int count = computerDAO.count(search);
		daoFactory.commit();
		
		return count;
	}
	
	/* (non-Javadoc)
	 * @see com.formation.computerdb.service.DataServiceI#getAllCompanies()
	 */
	@Override
	public List<Company> getAllCompanies() {
		daoFactory.setConn();
		List<Company> companies = companyDAO.getAll();
		daoFactory.commit();
		
		return companies;
	}
	
	/* (non-Javadoc)
	 * @see com.formation.computerdb.service.DataServiceI#getCompany(int)
	 */
	@Override
	public Company getCompany(int id) {
		daoFactory.setConn();
		Company company = companyDAO.get(id);
		daoFactory.commit();
		
		return company;
	}
}
