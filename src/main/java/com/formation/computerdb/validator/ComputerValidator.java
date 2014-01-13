package com.formation.computerdb.validator;

import org.joda.time.DateTime;
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

		// Introduced date
		String sIntroduced = cdto.getIntroduced();
		if(sIntroduced != null && !sIntroduced.isEmpty()) {
			try {
				DateTime introduced = DateTime.parse(sIntroduced);
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
				DateTime discontinued = DateTime.parse(sDiscontinued);
				if(discontinued != null) {
					if(discontinued.isAfter(lastValidDate) || discontinued.isBefore(firstValidDate))
						e.rejectValue("discontinued", "form.discontinued.date.invalid");
				}
			} catch(IllegalArgumentException iae) {
				e.rejectValue("discontinued", "form.discontinued.date.unparseable");
			}
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
