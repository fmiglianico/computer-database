package com.formation.computerdb.persistence.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.formation.computerdb.core.domain.Computer;


/**
 * Interface to access computers in the database
 * @author F. Miglianico
 */
@Repository
public interface ComputerDAO extends PagingAndSortingRepository<Computer, Long> {

	public static final String SELECT_QUERY = "select c from Computer c left join c.company company where c.name like :search or company.name like :search";
	public static final String COUNT_QUERY = "select count(*) from Computer c left join c.company company where c.name like :search or company.name like :search";
	
	@Query(value = SELECT_QUERY, countQuery = COUNT_QUERY)
	public Page<Computer> findAll(@Param("search") String search, Pageable pageable);
}
