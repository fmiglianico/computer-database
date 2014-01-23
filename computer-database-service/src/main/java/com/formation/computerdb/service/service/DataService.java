package com.formation.computerdb.service.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.formation.computerdb.core.domain.Company;
import com.formation.computerdb.core.domain.Computer;

public interface DataService {

	public Computer getComputer(int id);

	public void createComputer(Computer computer);

	public void updateComputer(Computer computer);

	public void deleteComputer(int id);

	public Page<Computer> retrievePage(Pageable pageable, String search);

	public List<Company> getAllCompanies();

	public Company getCompany(int id);

}