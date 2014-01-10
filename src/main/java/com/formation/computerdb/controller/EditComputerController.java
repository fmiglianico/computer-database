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
import org.springframework.web.bind.annotation.RequestParam;

import com.formation.computerdb.common.RC;
import com.formation.computerdb.domain.Company;
import com.formation.computerdb.domain.Computer;
import com.formation.computerdb.dto.ComputerDto;
import com.formation.computerdb.mapper.ComputerMapper;
import com.formation.computerdb.service.DataService;
import com.formation.computerdb.validator.ComputerValidator;

/**
 * Computer edition servlet
 * @author F. Miglianico
 *
 */
@Controller
@RequestMapping("/editComputer")
public class EditComputerController {
	
	private final static String COMPANIES_LABEL = "companies";
	private final static String COMPUTER_DTO_LABEL = "cdto";
	private static final String ID_LABEL = "id";
	private static final String RESULT_LABEL = "result";
	
	private Long id = null;
	
	private static Logger log = LoggerFactory.getLogger(EditComputerController.class);

	@Autowired
	private DataService ds = null;

	@Autowired
	private ComputerMapper computerMapper = null;

	@Autowired
	private ComputerValidator computerValidator = null;
	
	/**
	 * GET requests treatment
	 * @param sId the id as a String of the computer to edit
	 * @param model the model passed to the page
	 * @return the redirection/page to generate
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(@RequestParam(value = ID_LABEL, required = true) String sId,  
				ModelMap model) {
		
		// Get companies		
		List<Company> companies = ds.getAllCompanies();
		model.addAttribute(COMPANIES_LABEL, companies);
		
		// Get computer to be edited
		id = Long.valueOf(sId);
		if(id == null)
			return "redirect:dashboard";
		
		Computer computer = ds.getComputer(id.intValue());
		ComputerDto cdto = ComputerMapper.toDto(computer);
		model.addAttribute(COMPUTER_DTO_LABEL, cdto);

		return "editComputer";

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
			return "editComputer";
			
		} else {
		
			// Set ID of ComputerDTO
			cdto.setId(id);
	
			// Update computer in DB
			Computer computer = computerMapper.fromDto(cdto);
			RC rc = ds.updateComputer(computer);
			if(rc == RC.FAILED) {
				return "redirect:dashboard?message=editNOK";
			}
			
			log.info(new StringBuilder("Edition of computer \"").append(computer.getName()).append("\" successful").toString());
			
			return "redirect:dashboard?message=editOK";
		}

	}
}
