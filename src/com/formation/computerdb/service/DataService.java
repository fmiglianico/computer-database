package com.formation.computerdb.service;

import java.util.List;

import com.formation.computerdb.dao.CompanyDAO;
import com.formation.computerdb.dao.ComputerDAO;
import com.formation.computerdb.dao.DAOFactory;
import com.formation.computerdb.domain.Company;
import com.formation.computerdb.domain.Computer;
import com.formation.computerdb.domain.Page;

/**
 * Data service to access DAO functionalities
 * @author F. Miglianico
 *
 */
public class DataService {
	
	private static final DataService INSTANCE = new DataService();

	private ComputerDAO computerDAO = null;
	private CompanyDAO companyDAO = null;
	
	private DataService() {
		computerDAO = DAOFactory.INSTANCE.getComputerDAO();
		companyDAO = DAOFactory.INSTANCE.getCompanyDAO();
	}
	
	public static DataService getInstance() {
		return INSTANCE;
	}
	
	public Computer getComputer(int id) {
		return computerDAO.get(id);
	}
	
	public void createComputer(Computer computer) {
		computerDAO.create(computer);
	}
	
	public void updateComputer(Computer computer) {
		computerDAO.update(computer);
	}
	
	public void deleteComputer(int id) {
		computerDAO.delete(id);
	}
	
	public void fill(Page page) {
		computerDAO.fill(page);
	}
	
	public int countComputers(String search) {
		return computerDAO.count(search);
	}
	
	public List<Company> getAllCompanies() {
		return companyDAO.getAll();
	}
	
	public Company getCompany(int id) {
		return companyDAO.get(id);
	}
}
