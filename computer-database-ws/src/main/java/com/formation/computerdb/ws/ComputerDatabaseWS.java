package com.formation.computerdb.ws;

import java.util.List;

import javax.jws.WebService;

import com.formation.computerdb.core.dto.ComputerDto;

@WebService
public interface ComputerDatabaseWS {
	
	List<ComputerDto> retrievePage(String search);
}
