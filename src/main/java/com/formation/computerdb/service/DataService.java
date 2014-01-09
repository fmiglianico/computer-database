package com.formation.computerdb.service;

import java.util.List;

import com.formation.computerdb.common.RC;
import com.formation.computerdb.domain.Company;
import com.formation.computerdb.domain.Computer;
import com.formation.computerdb.domain.Page;

public interface DataService {

	public abstract Computer getComputer(int id);

	public abstract RC createComputer(Computer computer);

	public abstract RC updateComputer(Computer computer);

	public abstract RC deleteComputer(int id);

	public abstract void fill(Page page);

	public abstract int countComputers(String search);

	public abstract List<Company> getAllCompanies();

	public abstract Company getCompany(int id);

}