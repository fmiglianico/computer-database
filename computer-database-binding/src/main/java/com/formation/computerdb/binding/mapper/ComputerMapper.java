package com.formation.computerdb.binding.mapper;

import java.util.ResourceBundle;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.formation.computerdb.core.domain.Computer;
import com.formation.computerdb.core.dto.ComputerDto;
import com.formation.computerdb.persistence.dao.CompanyDAO;

/**
 * Mapper @Computer/@ComputerDto
 * @author F. Miglianico
 *
 */
@Component
public class ComputerMapper {
	
	@Autowired
	private CompanyDAO companyDAO;
	
	private final static ResourceBundle bundle = ResourceBundle.getBundle("messages");
	
	private final static Logger log = LoggerFactory.getLogger(ComputerMapper.class);
	
	/**
	 * Creates a ComputerDto from a Computer
	 * @param computer the computer
	 * @return the computerDto
	 */
	public ComputerDto toDto(Computer computer) {
		
		if(bundle == null) {
			log.error("Cannot find resource bundle");
			return null;
		}
		
		String pattern = bundle.getString("date.format");
		
		ComputerDto cdto = new ComputerDto();
		
		cdto.setId(computer.getId());
		cdto.setName(computer.getName());
		
		if(computer.getIntroduced() != null)
			cdto.setIntroduced(computer.getIntroduced().toString(pattern));
		if(computer.getDiscontinued() != null)
			cdto.setDiscontinued(computer.getDiscontinued().toString(pattern));
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
		

		String sIntroduced = cdto.getIntroduced();
		String sDiscontinued = cdto.getDiscontinued();
		
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(bundle.getString("date.format")).toFormatter();

		try {
			if(sIntroduced != null && !sIntroduced.isEmpty()) {
				computer.setIntroduced(DateTime.parse(sIntroduced, formatter));
			}
	
			if(sDiscontinued != null && !sDiscontinued.isEmpty()) {
				computer.setDiscontinued(DateTime.parse(sDiscontinued, formatter));
			}
		} catch(IllegalArgumentException e) {
			log.error("Cannot parse date : " + e.getMessage(), e);
		}
		
		Long companyId = cdto.getCompanyId();
		if(companyId != null)
			computer.setCompany(companyDAO.findOne(companyId));
		
		return computer;
		
	}
}
