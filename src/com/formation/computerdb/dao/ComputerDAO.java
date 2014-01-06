package com.formation.computerdb.dao;

import java.sql.Connection;

import com.formation.computerdb.domain.Computer;
import com.formation.computerdb.domain.Page;

/**
 * Interface to access computers in the database
 * @author F. Miglianico
 */
public interface ComputerDAO {
	
	public Computer get(int id, Connection conn);
	/**
	 * Fills the list of computers in the page, 
	 * corresponding to the parameters set in the page
	 * @param page the page
	 * @param conn TODO
	 */
	public void fill(Page page, Connection conn);
	
	public void create(Computer computer, Connection conn);
	public void update(Computer computer, Connection conn);
	public void delete(int id, Connection conn);
	
	public int count(String search, Connection conn);
}
