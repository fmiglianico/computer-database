package com.formation.computerdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.formation.computerdb.common.OrderByColumn;
import com.formation.computerdb.domain.Page;
import com.formation.computerdb.service.DataService;

/**
 * Dashboard servlet
 * @author F. Miglianico
 *
 */
@WebServlet("/dashboard")
public class ComputerServlet extends SpringInjectedServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int NB_ROWS_DEFAULT = 15;

	private final static String SEARCH_LABEL = "search";
	private final static String ORDER_BY_LABEL = "orderby";
	private final static String DIR_LABEL = "dir";
	private final static String CURR_PAGE_LABEL = "page";
	private final static String NB_ROWS_LABEL = "nbrows";

	private final static String PAGE_WRAPPER_LABEL = "wrap";
	
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(ComputerServlet.class);

	@Autowired
	private DataService ds = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		// Get all the parameters of the page
		Page page = new Page();
		
			// search
		String search = request.getParameter(SEARCH_LABEL);
		if(search != null && !search.isEmpty())
			page.setSearch(search.toLowerCase());
		
			// sort
		String orderBy = request.getParameter(ORDER_BY_LABEL);
		String dir = request.getParameter(DIR_LABEL);
		page.setOrderBy(OrderByColumn.get(orderBy, dir));
		
			// number of computers
		page.setNbComputers(ds.countComputers(page.getSearch()));
		
			// current page
		String sPage = request.getParameter(CURR_PAGE_LABEL);
		if(sPage != null && !sPage.isEmpty())
			page.setCurrPage(Integer.valueOf(sPage));
		else
			page.setCurrPage(1);
		
			// number of rows displayed
		String sNbRows = request.getParameter(NB_ROWS_LABEL);
		int nbRows = (sNbRows == null) ? NB_ROWS_DEFAULT : Integer.valueOf(sNbRows);
		if(nbRows <= 0)
			nbRows = NB_ROWS_DEFAULT;
		page.setNbRows(nbRows);
		
		
		// Messages
		String message = request.getParameter("message");
		if(message != null) {
			if(message.equals("creationOK")) {
				request.setAttribute("message", "Creation of computer successful");
				request.setAttribute("messageHeader", "Well done!");
				request.setAttribute("status", "success");
			} else if(message.equals("editOK")){
				request.setAttribute("messageHeader", "Well done!");
				request.setAttribute("message", "Computer edited successfully");
				request.setAttribute("status", "success");
			} else if(message.equals("deleteOK")){
				request.setAttribute("messageHeader", "Well done!");
				request.setAttribute("message", "Computer deleted successfully");
				request.setAttribute("status", "success");
			} else if(message.equals("creationNOK")){
				request.setAttribute("messageHeader", "Error!");
				request.setAttribute("message", "An error occured while trying to create the computer. Please try again later.");
				request.setAttribute("status", "danger");
			} else if(message.equals("editNOK")){
				request.setAttribute("messageHeader", "Error!");
				request.setAttribute("message", "An error occured while trying to edit the computer. Please try again later.");
				request.setAttribute("status", "danger");
			} else if(message.equals("deleteNOK")){
				request.setAttribute("messageHeader", "Error!");
				request.setAttribute("message", "An error occured while trying to delete the computer. Please try again later.");
				request.setAttribute("status", "danger");
			}
		}
		
		// Get computer list in page
		ds.fill(page);
		
		// pass as parameter to the jsp
		request.setAttribute(PAGE_WRAPPER_LABEL, page);
		request.getRequestDispatcher( "WEB-INF/dashboard.jsp" ).forward(request, response);
	}
}
