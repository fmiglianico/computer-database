package com.formation.computerdb.dao;

import java.sql.SQLException;

import com.formation.computerdb.domain.Computer;
import com.formation.computerdb.domain.Page;

/**
 * Interface to access computers in the database
 * @author F. Miglianico
 */
public interface ComputerDAO {
	
	public Computer get(int id);
	/**
	 * Fills the list of computers in the page, 
	 * corresponding to the parameters set in the page
	 * @param page the page
	 */
	public void fill(Page page);
	
	public void create(Computer computer) throws SQLException;
	public void update(Computer computer) throws SQLException;
	public void delete(int id) throws SQLException;
	
	public int count(String search);
}
