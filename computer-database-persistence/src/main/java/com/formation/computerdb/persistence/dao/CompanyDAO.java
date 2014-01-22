package com.formation.computerdb.persistence.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formation.computerdb.core.domain.Company;


/**
 * Interface to access the companies in the Database
 * @author F. Miglianico
 */
@Repository
public interface CompanyDAO  extends CrudRepository<Company, Long> {
	
}
