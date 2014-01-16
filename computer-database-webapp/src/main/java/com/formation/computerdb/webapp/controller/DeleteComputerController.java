package com.formation.computerdb.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.formation.computerdb.service.service.DataService;

/**
 * Servlet called for deletion of a computer
 * @author F. Miglianico
 *
 */
@Controller
@RequestMapping("/deleteComputer")
public class DeleteComputerController {
	
	private static final String ID_LABEL = "id";
	
	private static final Logger log = LoggerFactory.getLogger(DeleteComputerController.class);

	@Autowired
	private DataService ds = null;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(@RequestParam(value = ID_LABEL, required = true) String sId) {

		// Id of computer
		Long id = Long.valueOf(sId);
		if(id == null) {
			return "dashboard";
		}
		
		ds.deleteComputer(id.intValue());
		
		log.info(new StringBuilder("Deletion of computer id=").append(id.intValue()).append(" successful").toString());

		return "redirect:dashboard?message=delete&status=success";
	}
}
