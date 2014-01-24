package com.formation.computerdb.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Error controller
 * 
 * @author F. Miglianico
 * 
 */
@Controller
public class LoginController {
	
	private final static String ERROR_LABEL = "error";

	@RequestMapping(value = "/login")
	public String handleLogin(@RequestParam(value = ERROR_LABEL, required = false) String error, ModelMap model) {
		if(error != null)
			model.addAttribute(ERROR_LABEL, "error");
		return "login";
	}
}
