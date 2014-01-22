package com.formation.computerdb.service.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formation.computerdb.core.common.DBLogActionType;
import com.formation.computerdb.core.common.Page;
import com.formation.computerdb.core.domain.Company;
import com.formation.computerdb.core.domain.Computer;
import com.formation.computerdb.core.domain.DBLog;
import com.formation.computerdb.persistence.dao.CompanyDAO;
import com.formation.computerdb.persistence.dao.ComputerDAO;
import com.formation.computerdb.persistence.dao.DBLogDAO;
import com.formation.computerdb.service.service.DataService;
import com.google.common.collect.Lists;

/**
 * Data service to access DAO functionalities
 * 
 * @author F. Miglianico
 * 
 */
@Service
@Transactional(readOnly = true)
public class DataServiceImpl implements DataService {

	// private static final Logger log =
	// LoggerFactory.getLogger(DataServiceImpl.class);

	@Autowired
	private ComputerDAO computerDAO;

	@Autowired
	private CompanyDAO companyDAO;

	@Autowired
	private DBLogDAO dbLogDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.formation.computerdb.service.DataServiceI#getComputer(int)
	 */
	@Override
	public Computer getComputer(int id) {
		return computerDAO.findOne(new Long(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.formation.computerdb.service.DataServiceI#createComputer(com.formation
	 * .computerdb.domain.Computer)
	 */
	@Override
	@Transactional(readOnly = false)
	public void createComputer(Computer computer) {

		computerDAO.save(computer);
		DBLog dbLog;
		if (computer.getId() != null)
			dbLog = new DBLog(DBLogActionType.COMPUTER_ADDED, new DateTime(),
					"id = " + computer.getId().toString());
		else
			dbLog = new DBLog(DBLogActionType.COMPUTER_ADDED, new DateTime(),
					"Unknown ID");

		dbLogDAO.save(dbLog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.formation.computerdb.service.DataServiceI#updateComputer(com.formation
	 * .computerdb.domain.Computer)
	 */
	@Override
	@Transactional(readOnly = false)
	public void updateComputer(Computer computer) {

		DBLog dbLog = new DBLog(DBLogActionType.COMPUTER_UPDATED,
				new DateTime(), "id = " + computer.getId().toString());

		computerDAO.save(computer);
		dbLogDAO.save(dbLog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.formation.computerdb.service.DataServiceI#deleteComputer(int)
	 */
	@Override
	@Transactional(readOnly = false)
	public void deleteComputer(int id) {

		Computer computer = computerDAO.findOne(new Long(id));

		if (computer != null) {
			DBLog dbLog = new DBLog(DBLogActionType.COMPUTER_DELETED,
					new DateTime(), "id = " + id);
			computerDAO.delete(computer);
			dbLogDAO.save(dbLog);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.formation.computerdb.service.DataServiceI#fill(com.formation.computerdb
	 * .domain.Page)
	 */
	@Override
	public void fill(Page page) {

		Sort order = page.getOrderBy() == null ? null : page.getOrderBy()
				.getValue();

		// getCurrPage - 1 because pages are indexed [1...n] on client side and
		// [0...n-1] on Repository side
		PageRequest pageRequest = new PageRequest(page.getCurrPage() - 1,
				page.getNbRows(), order);

		String search = page.getSearch();
		org.springframework.data.domain.Page<Computer> result = null;

		if (search == null)
			result = computerDAO.findAll(pageRequest);
		else {
			search = new StringBuilder("%").append(search).append("%").toString();
			result = computerDAO.findAll(search, pageRequest);
		}

		page.setList(result.getContent());
		page.setNbComputers((int) result.getTotalElements());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.formation.computerdb.service.DataServiceI#getAllCompanies()
	 */
	@Override
	public List<Company> getAllCompanies() {
		Iterable<Company> companies = companyDAO.findAll();
		return Lists.newArrayList(companies);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.formation.computerdb.service.DataServiceI#getCompany(int)
	 */
	@Override
	public Company getCompany(int id) {

		return companyDAO.findOne(new Long(id));
	}
}
