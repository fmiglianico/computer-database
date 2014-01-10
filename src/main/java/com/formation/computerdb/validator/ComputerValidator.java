package com.formation.computerdb.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.formation.computerdb.domain.Company;
import com.formation.computerdb.dto.ComputerDto;
import com.formation.computerdb.service.DataService;
import com.formation.computerdb.util.ComputerDBCatalog;

/**
 * Validator for computer entities given through edit and add computer pages
 * @author F. Miglianico
 *
 */
@Component
public class ComputerValidator implements Validator {

	private static final SimpleDateFormat sdf = new SimpleDateFormat(ComputerDBCatalog.STORED_DATE_PATTERN.getValue());
	
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
		
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "name.empty");
		
		ComputerDto cdto = (ComputerDto)obj;
		
		// Verify that dates are in range
		Calendar lastValidDate = Calendar.getInstance();
		lastValidDate.set(2100, 1, 1);
		Calendar firstValidDate = Calendar.getInstance();
		firstValidDate.set(1900, 1, 1);
		
			// Introduced date
		String introduction = cdto.getIntroduced();
		if(introduction != null && introduction != "") {
			try {
				Date d = sdf.parse(introduction);
				if(d.after(lastValidDate.getTime()) || d.before(firstValidDate.getTime())) {
					e.rejectValue("introduced", "introduced.date.invalid");
				}
			} catch (ParseException e1) {
				e.rejectValue("introduced", "introduced.date.unparseable");
			}
		}
		
			// Discontinued date
		String discontinued = cdto.getDiscontinued();
		if(discontinued != null && discontinued != "") {
			try {
				Date d = sdf.parse(discontinued);
				if(d.after(lastValidDate.getTime()) || d.before(firstValidDate.getTime())) {
					e.rejectValue("discontinued", "discontinued.date.invalid");
				}
			} catch (ParseException e1) {
				e.rejectValue("discontinued", "discontinued.date.unparseable");
			}
		}
		
		// Verify that the company exists in DB
		Long companyId = cdto.getCompanyId();
		if(companyId != null) {
			Company company = ds.getCompany(companyId.intValue());
			if(company == null)
				e.rejectValue("companyId", "company.not.found");
		}
		
	}
}
