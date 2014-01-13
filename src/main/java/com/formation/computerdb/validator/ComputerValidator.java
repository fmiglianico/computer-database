package com.formation.computerdb.validator;

import java.util.ResourceBundle;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.formation.computerdb.domain.Company;
import com.formation.computerdb.dto.ComputerDto;
import com.formation.computerdb.service.DataService;

/**
 * Validator for computer entities given through edit and add computer pages
 * @author F. Miglianico
 *
 */
@Component
public class ComputerValidator implements Validator {

	private static final DateTime firstValidDate = new DateTime(1970, 1, 1, 0, 0, 1);
	private static final DateTime lastValidDate = new DateTime(2038, 1, 19, 3, 14, 07);

	private final static ResourceBundle bundle = ResourceBundle.getBundle("messages");
	
	private final static Logger log = LoggerFactory.getLogger(ComputerValidator.class);
	
	@Autowired
	private DataService ds;

	@Override
	public boolean supports(Class<?> arg0) {
		return ComputerDto.class.equals(arg0);
	}

	/**
	 * Validates a ComputerDto
	 */
	@Override
	public void validate(Object obj, Errors e) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "form.name.empty");
		
		ComputerDto cdto = (ComputerDto)obj;
		
		if(bundle != null) {
			DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(bundle.getString("date.format")).toFormatter();
	
			// Introduced date
			String sIntroduced = cdto.getIntroduced();
			if(sIntroduced != null && !sIntroduced.isEmpty()) {
				try {
					DateTime introduced = DateTime.parse(sIntroduced, formatter);
					if(introduced != null) {
						if(introduced.isAfter(lastValidDate) || introduced.isBefore(firstValidDate))
							e.rejectValue("introduced", "form.introduced.date.invalid");
					}
				} catch(IllegalArgumentException iae) {
					e.rejectValue("introduced", "form.introduced.date.unparseable");
				}
			}
			
			// Discontinued date
			String sDiscontinued = cdto.getDiscontinued();
			if(sDiscontinued != null && !sDiscontinued.isEmpty()) {
				try {
					DateTime discontinued = DateTime.parse(sDiscontinued, formatter);
					if(discontinued != null) {
						if(discontinued.isAfter(lastValidDate) || discontinued.isBefore(firstValidDate))
							e.rejectValue("discontinued", "form.discontinued.date.invalid");
					}
				} catch(IllegalArgumentException iae) {
					e.rejectValue("discontinued", "form.discontinued.date.unparseable");
				}
			}
		} else {
			log.error("ResourceBundle not available for date validation");
		}
		
		
		// Verify that the company exists in DB
		Long companyId = cdto.getCompanyId();
		if(companyId != null) {
			Company company = ds.getCompany(companyId.intValue());
			if(company == null)
				e.rejectValue("companyId", "form.company.not.found");
		}
		
	}
}
