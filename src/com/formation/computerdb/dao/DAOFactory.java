package com.formation.computerdb.dao;

public enum DAOFactory {
	INSTANCE;
	
	private ComputerDAO computerDAO;
	private CompanyDAO companyDAO;
	
	private DAOFactory() {
		computerDAO = new ComputerDAOImpl();
		companyDAO = new CompanyDAOImpl();
	}
	
	public ComputerDAO getComputerDAO() {
		return computerDAO;
	}
	
	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}
	
}
