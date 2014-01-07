package com.formation.computerdb.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.formation.computerdb.common.ValidationError;
import com.formation.computerdb.domain.Company;
import com.formation.computerdb.dto.ComputerDto;
import com.formation.computerdb.service.DataService;
import com.formation.computerdb.util.ComputerDBCatalog;

/**
 * Validator for computer entities given through edit and add computer pages
 * @author F. Miglianico
 *
 */
public class ComputerValidator {

	private static final SimpleDateFormat sdf = new SimpleDateFormat(ComputerDBCatalog.STORED_DATE_PATTERN.getValue());
	
	/**
	 * Validates a ComputerDto
	 * @param cdto the ComputerDto 
	 * @return
	 */
	public static int isValid(ComputerDto cdto) {
		
		int retCode = 0;
		
		// Verify if name is null or empty
		String name = cdto.getName();
		if(name == null || name.equals(""))
			retCode |= ValidationError.NULL_NAME.getFlag();

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
					retCode |= ValidationError.UNPARSEABLE_INTRODUCED_DATE.getFlag();
				}
			} catch (ParseException e) {
				retCode |= ValidationError.UNPARSEABLE_INTRODUCED_DATE.getFlag();
			}
		}
		
			// Discontinued date
		String discontinued = cdto.getDiscontinued();
		if(discontinued != null && discontinued != "") {
			try {
				Date d = sdf.parse(discontinued);
				if(d.after(lastValidDate.getTime()) || d.before(firstValidDate.getTime())) {
					retCode |= ValidationError.UNPARSEABLE_DISCONTINUED_DATE.getFlag();
				}
			} catch (ParseException e) {
				retCode |= ValidationError.UNPARSEABLE_DISCONTINUED_DATE.getFlag();
			}
		}
		
		// Verify that the company exists in DB
		Long companyId = cdto.getCompanyId();
		if(companyId != null) {
			Company company = DataService.getInstance().getCompany(companyId.intValue());
			if(company == null)
				retCode |= ValidationError.UNKNOWN_COMPANY.getFlag();
		}
		
		return retCode;	
	}
}
