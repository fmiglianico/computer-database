package com.formation.computerdb.ws.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.springframework.data.domain.Page;

import com.formation.computerdb.binding.mapper.ComputerMapper;
import com.formation.computerdb.core.common.ComputerPage;
import com.formation.computerdb.core.domain.Computer;
import com.formation.computerdb.core.dto.ComputerDto;
import com.formation.computerdb.service.service.DataService;
import com.formation.computerdb.ws.ComputerDatabaseWS;

@WebService(endpointInterface="com.formation.computerdb.ws.ComputerDatabaseWS")
public class ComputerDatabaseWSImpl implements ComputerDatabaseWS {

	private DataService ds;
	
	private ComputerMapper cm;

	/**
	 * @param ds the ds to set
	 */
	public void setDataService(DataService ds) {
		this.ds = ds;
	}


	/**
	 * @param cm the cm to set
	 */
	public void setComputerMapper(ComputerMapper cm) {
		this.cm = cm;
	}


	public List<ComputerDto> retrievePage(String search) {
		Page<Computer> page = ds.retrievePage(new ComputerPage(0, 15), search);
		if(page.hasContent()) {
			List<ComputerDto> dtos = new ArrayList<ComputerDto>();
			List<Computer> computers = page.getContent();
			for(Computer computer : computers) {
				dtos.add(cm.toDto(computer));
			}
			return dtos;
		}
		return null;
	}

}
