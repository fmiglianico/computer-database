package com.formation.computerdb.dao;

import java.util.ArrayList;

import com.formation.computerdb.domain.Computer;

public interface ComputerDAO {
	public ArrayList<Computer> getAll(int offset, int max, String orderBy, String dir);
	public Computer get(int id);
	public void create(Computer computer);
	public void update(Computer computer);
	public void delete(int id);
	public ArrayList<Computer> search(String search, int offset, int max, String orderBy, String dir);
	public int count(String search);
}
