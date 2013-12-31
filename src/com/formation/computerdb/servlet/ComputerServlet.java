package com.formation.computerdb.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.computerdb.domain.Computer;
import com.formation.computerdb.service.DataService;

@WebServlet("/dashboard")
public class ComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int MAX_COMPUTERS_PER_PAGE = 15;
	
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(ComputerServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		DataService ds = DataService.getInstance();

		String search = request.getParameter("search");
		String orderBy = request.getParameter("orderby");
		String dir = request.getParameter("dir");
		int nComputers = ds.countComputers(search);
			
		request.setAttribute("ncomputers", nComputers);
		
		String sPage = request.getParameter("page");
		int page = (sPage == null) ? 1 : Integer.valueOf(sPage);
		
		String sMaxComp = request.getParameter("max");
		int maxComp = (sMaxComp == null) ? MAX_COMPUTERS_PER_PAGE : Integer.valueOf(sMaxComp);
		if(maxComp <= 0)
			maxComp = MAX_COMPUTERS_PER_PAGE;

		request.setAttribute("nbPages", (int)(nComputers/maxComp) + ((nComputers%maxComp) == 0 ? 0 : 1));
		request.setAttribute("page", page);
		request.setAttribute("max", maxComp);
		request.setAttribute("search", search);
		request.setAttribute("orderby", orderBy);
		request.setAttribute("dir", dir);
		
		if(dir != null) {
			String oppDir = (dir.equals("asc") ? "desc" : "asc");
			request.setAttribute("oppdir", oppDir);
		}
		
		
		ArrayList<Computer> computers = null;
		
		if(search != null) {
			search = search.toLowerCase();
			computers = ds.searchComputer(search, (page-1)*maxComp, maxComp, orderBy, dir);
		} else
			computers = ds.getAllComputers((page-1)*maxComp, maxComp, orderBy, dir);

		request.setAttribute("computers", computers);

		request.getRequestDispatcher( "WEB-INF/dashboard.jsp" ).forward(request, response);
	}
}
