package com.formation.computerdb.service;

import java.util.ArrayList;

import com.formation.computerdb.dao.CompanyDAO;
import com.formation.computerdb.dao.ComputerDAO;
import com.formation.computerdb.dao.DAOFactory;
import com.formation.computerdb.domain.Company;
import com.formation.computerdb.domain.Computer;

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
	
	public ArrayList<Computer> getAllComputers(int offset, int max, String orderBy, String dir) {
		return computerDAO.getAll(offset, max, orderBy, dir);
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
	
	public ArrayList<Computer> searchComputer(String search, int offset, int max, String orderBy, String dir) {
		return computerDAO.search(search, offset, max, orderBy, dir);
	}
	
	public int countComputers(String search) {
		return computerDAO.count(search);
	}
	
	public ArrayList<Company> getAllCompanies() {
		return companyDAO.getAll();
	}
	
	public Company getCompany(int id) {
		return companyDAO.get(id);
	}
}
