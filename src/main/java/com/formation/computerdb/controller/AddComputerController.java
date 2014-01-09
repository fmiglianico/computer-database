package com.formation.computerdb.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.formation.computerdb.common.RC;
import com.formation.computerdb.domain.Company;
import com.formation.computerdb.domain.Computer;
import com.formation.computerdb.dto.ComputerDto;
import com.formation.computerdb.mapper.ComputerMapper;
import com.formation.computerdb.service.DataService;
import com.formation.computerdb.validator.ComputerValidator;

/**
 * Servlet called when the user wants to add a computer in DB
 * @author F. Miglianico
 *
 */
@Controller
@RequestMapping("/addComputer")
public class AddComputerController {
	
	private final static String NAME_LABEL = "name";
	private final static String INTRODUCED_LABEL = "introduced";
	private final static String DISCONTINUED_LABEL = "discontinued";
	private final static String COMPANY_LABEL = "company";
	private final static String COMPANIES_LABEL = "companies";
	private final static String RETURN_CODE_LABEL = "retCode";
	private final static String COMPUTER_DTO_LABEL = "cdto";
	
	private static Logger log = LoggerFactory.getLogger(AddComputerController.class);
	
	@Autowired
	private DataService ds = null;

	@Autowired
	private ComputerMapper computerMapper = null;

	@Autowired
	private ComputerValidator computerValidator = null;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(ModelMap model) {
		
		List<Company> companies = ds.getAllCompanies();

		// Send companies to jsp
		model.addAttribute(COMPANIES_LABEL, companies);
		return "addComputer";

	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@RequestParam(value = NAME_LABEL, required = true) String name, 
			@RequestParam(value = INTRODUCED_LABEL, required = false) String introduced, 
			@RequestParam(value = DISCONTINUED_LABEL, required = false) String discontinued, 
			@RequestParam(value = COMPANY_LABEL, required = false) String company, 
			ModelMap model) {
		
		// Creates DTO from parameters set by user
		ComputerDto cdto = new ComputerDto();
		cdto.setName(name);
		cdto.setIntroduced(introduced);
		cdto.setDiscontinued(discontinued);
		cdto.setCompanyId(Long.valueOf(company));

		if(cdto.getCompanyId() == 0)
			cdto.setCompanyId(null);
		
		// Validate computer - error code returned if not valid
		int retCode = computerValidator.isValid(cdto);
		if(retCode != 0) {
			model.addAttribute(RETURN_CODE_LABEL, retCode);
			model.addAttribute(COMPUTER_DTO_LABEL, cdto);
			
			List<Company> companies = ds.getAllCompanies();
			model.addAttribute(COMPANIES_LABEL, companies);
			
			return "addComputer";
		}
		
		// ComputerDto is valid, conversion to Computer
		Computer computer = computerMapper.fromDto(cdto);
		
		// Creation in DB
		RC rc = ds.createComputer(computer);
		
		if(rc == RC.FAILED) {
			return "redirect:dashboard?message=creationNOK";
		}
		
		log.info(new StringBuilder("Creation of computer \"").append(computer.getName()).append("\" successful").toString());

		return "redirect:dashboard?message=creationOK";

	}
}
