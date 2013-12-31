package com.formation.computerdb.dao;

import java.util.ArrayList;

import com.formation.computerdb.domain.Company;

public interface CompanyDAO {
	public ArrayList<Company> getAll();
	public Company get(int id);
}
