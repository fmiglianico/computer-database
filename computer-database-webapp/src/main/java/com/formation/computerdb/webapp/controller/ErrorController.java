package com.formation.computerdb.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Error controller
 * 
 * @author F. Miglianico
 * 
 */
@Controller
public class ErrorController {

	@RequestMapping(value = "/error/403.html")
	public String handle403() {
		return "error/403";
	}

	@RequestMapping(value = "/error/404.html")
	public String handle404() {
		return "error/404";
	}

	@RequestMapping(value = "/error/500.html")
	public String handle500() {
		return "error/500";
	}
}
