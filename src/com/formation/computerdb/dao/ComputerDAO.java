package com.formation.computerdb.dao;

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
	
	public void create(Computer computer);
	public void update(Computer computer);
	public void delete(int id);
	
	public int count(String search);
}
