package com.formation.computerdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.formation.computerdb.common.RC;
import com.formation.computerdb.service.DataService;

/**
 * Servlet called for deletion of a computer
 * @author F. Miglianico
 *
 */
@Controller
@RequestMapping("/deleteComputer")
public class DeleteComputerController {
	
	private static final String ID_LABEL = "id";

	@Autowired
	private DataService ds = null;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(@RequestParam(value = ID_LABEL, required = true) String sId) {

		// Id of computer
		Long id = Long.valueOf(sId);
		if(id == null) {
			return "dashboard";
		}
		
		RC rc = ds.deleteComputer(id.intValue());
		
		if(rc == RC.FAILED) {
			return "redirect:dashboard?message=delete&status=danger";
		}

		return "redirect:dashboard?message=delete&status=success";
	}
}
