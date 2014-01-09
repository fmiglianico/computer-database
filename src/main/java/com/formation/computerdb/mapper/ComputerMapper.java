package com.formation.computerdb.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.formation.computerdb.domain.Computer;
import com.formation.computerdb.dto.ComputerDto;
import com.formation.computerdb.service.DataService;
import com.formation.computerdb.util.ComputerDBCatalog;

/**
 * Mapper @Computer/@ComputerDto
 * @author F. Miglianico
 *
 */
@Component
public class ComputerMapper {
	
	@Autowired
	private DataService ds;
	
	private final static Logger log = LoggerFactory.getLogger(ComputerMapper.class);
	
	/**
	 * Creates a ComputerDto from a Computer
	 * @param computer the computer
	 * @return the computerDto
	 */
	public static ComputerDto toDto(Computer computer) {
		
		ComputerDto cdto = new ComputerDto();
		
		cdto.setId(computer.getId());
		cdto.setName(computer.getName());
		cdto.setIntroduced(computer.getStoredIntroduced());
		cdto.setDiscontinued(computer.getStoredDiscontinued());
		cdto.setCompanyId(computer.getCompany() == null ? null : computer.getCompany().getId());
		
		return cdto;
		
	}

	/**
	 * Creates a Computer from a ComputerDto
	 * @param computer the computerDto
	 * @return the computer
	 */
	public Computer fromDto(ComputerDto cdto) {
		
		Computer computer = new Computer();
		
		computer.setId(cdto.getId());
		computer.setName(cdto.getName());
		
		SimpleDateFormat sdf = new SimpleDateFormat(ComputerDBCatalog.STORED_DATE_PATTERN.getValue());

		String sIntroduced = cdto.getIntroduced();
		String sDiscontinued = cdto.getDiscontinued();

		try {
			if(sIntroduced != null && !sIntroduced.isEmpty()) {
				Date introduced = sdf.parse(sIntroduced);
				computer.setIntroduced(introduced);
			}

			if(sDiscontinued != null && !sDiscontinued.isEmpty()) {
				Date discontinued = sdf.parse(sDiscontinued);
				computer.setDiscontinued(discontinued);
			}
		} catch (ParseException e) {
			log.error("Cannot parse date", e);
		}
		
		Long companyId = cdto.getCompanyId();
		if(companyId != null)
			computer.setCompany(ds.getCompany(companyId.intValue()));
		
		return computer;
		
	}
}
