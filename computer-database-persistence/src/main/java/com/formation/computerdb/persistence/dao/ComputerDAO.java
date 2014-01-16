package com.formation.computerdb.persistence.dao;

import com.formation.computerdb.core.common.Page;
import com.formation.computerdb.core.domain.Computer;


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
