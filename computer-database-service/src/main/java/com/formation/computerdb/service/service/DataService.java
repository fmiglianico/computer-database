package com.formation.computerdb.service.service;

import java.util.List;

import com.formation.computerdb.core.common.Page;
import com.formation.computerdb.core.domain.Company;
import com.formation.computerdb.core.domain.Computer;

public interface DataService {

	public abstract Computer getComputer(int id);

	public abstract void createComputer(Computer computer);

	public abstract void updateComputer(Computer computer);

	public abstract void deleteComputer(int id);

	public abstract void fill(Page page);

	public abstract List<Company> getAllCompanies();

	public abstract Company getCompany(int id);

}