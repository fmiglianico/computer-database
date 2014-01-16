package com.formation.computerdb.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.formation.computerdb.core.common.OrderByColumn;
import com.formation.computerdb.core.common.Page;
import com.formation.computerdb.service.service.DataService;

/**
 * Dashboard servlet
 * @author F. Miglianico
 *
 */
@Controller
@RequestMapping("/dashboard")
public class ComputerController {
	
	private static final int NB_ROWS_DEFAULT = 15;

	private final static String SEARCH_LABEL = "search";
	private final static String ORDER_BY_LABEL = "orderby";
	private final static String DIR_LABEL = "dir";
	private final static String CURR_PAGE_LABEL = "page";
	private final static String NB_ROWS_LABEL = "nbrows";
	private final static String MESSAGE_LABEL = "message";
	private final static String STATUS_LABEL = "status";

	private final static String PAGE_WRAPPER_LABEL = "wrap";
	
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(ComputerController.class);

	@Autowired
	private DataService ds = null;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(@RequestParam(value = SEARCH_LABEL, required = false) String search, 
			@RequestParam(value = ORDER_BY_LABEL, required = false) String orderBy, 
			@RequestParam(value = DIR_LABEL, required = false) String dir, 
			@RequestParam(value = CURR_PAGE_LABEL, required = false) String sPage, 
			@RequestParam(value = NB_ROWS_LABEL, required = false) String sNbRows, 
			@RequestParam(value = MESSAGE_LABEL, required = false) String message,
			@RequestParam(value = STATUS_LABEL, required = false) String status,
			ModelMap model) {

		// Get all the parameters of the page
		Page page = new Page();
		
			// search
		if(search != null && !search.isEmpty())
			page.setSearch(search.toLowerCase());
		
			// sort
		page.setOrderBy(OrderByColumn.get(orderBy, dir));
		
			// current page
		if(sPage != null && !sPage.isEmpty())
			page.setCurrPage(Integer.valueOf(sPage));
		else
			page.setCurrPage(1);
		
			// number of rows displayed
		int nbRows = (sNbRows == null) ? NB_ROWS_DEFAULT : Integer.valueOf(sNbRows);
		if(nbRows <= 0)
			nbRows = NB_ROWS_DEFAULT;
		page.setNbRows(nbRows);
		
		
		// Messages
		if(message != null && status != null) {
			model.addAttribute(MESSAGE_LABEL, message);
			model.addAttribute(STATUS_LABEL, status);
		}
		
		// Get computer list in page
		ds.fill(page);
		
		// pass as parameter to the jsp
		model.addAttribute(PAGE_WRAPPER_LABEL, page);
		
		return "dashboard";
	}
}
