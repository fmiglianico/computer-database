package com.formation.computerdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.RowMapper;

import com.formation.computerdb.core.domain.Company;
import com.formation.computerdb.core.domain.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet rs, int line) {

		Computer computer = new Computer();

		try {
			computer.setId(rs.getLong("computer.id"));
			computer.setName(rs.getString("computer.name"));
			Timestamp ts = rs.getTimestamp("computer.introduced");
			if (ts != null)
				computer.setIntroduced(new DateTime(ts));
			ts = rs.getTimestamp("computer.discontinued");
			if (ts != null)
				computer.setDiscontinued(new DateTime(ts));

			Company company = new Company();

			company.setId(rs.getLong("company.id"));
			company.setName(rs.getString("company.name"));
			
			computer.setCompany(company);
			
		} catch (SQLException e) {
			throw new DataRetrievalFailureException("Could not map ResultSet to Computer", e);
		}

		return computer;
	}
}
