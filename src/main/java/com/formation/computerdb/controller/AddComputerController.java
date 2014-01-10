package com.formation.computerdb.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	private final static String COMPANIES_LABEL = "companies";
	private final static String COMPUTER_DTO_LABEL = "cdto";
	private final static String RESULT_LABEL = "result";
	
	private static Logger log = LoggerFactory.getLogger(AddComputerController.class);
	
	@Autowired
	private DataService ds = null;

	@Autowired
	private ComputerMapper computerMapper = null;

	@Autowired
	private ComputerValidator computerValidator = null;

	/**
	 * GET requests treatment
	 * @param model the model passed to the page
	 * @return the page to generate
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(ModelMap model) {
		
		List<Company> companies = ds.getAllCompanies();

		// Send companies to jsp
		model.addAttribute(COMPANIES_LABEL, companies);
		model.addAttribute(COMPUTER_DTO_LABEL, new ComputerDto());
		return "addComputer";

	}
	
	/**
	 * POST requests treatment
	 * @param model the model passed to the jsp page
	 * @param cdto the computer
	 * @param result the result of the validation
	 * @return the page/redirection
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String doPost(ModelMap model, @ModelAttribute("cdto") ComputerDto cdto,
			BindingResult result) {
		
		computerValidator.validate(cdto, result);
		
		if(result.hasErrors()) {
			// Get companies		
			List<Company> companies = ds.getAllCompanies();
			model.addAttribute(COMPANIES_LABEL, companies);
			model.addAttribute(RESULT_LABEL, result);
			return "addComputer";
			
		} else {
	
			// Else, update computer in DB
			Computer computer = computerMapper.fromDto(cdto);

			RC rc = ds.createComputer(computer);
			
			if(rc == RC.FAILED) {
				return "redirect:dashboard?message=creationNOK";
			}

			log.info(new StringBuilder("Creation of computer \"").append(computer.getName()).append("\" successful").toString());
			
			return "redirect:dashboard?message=creationOK";
		}

	}
}
