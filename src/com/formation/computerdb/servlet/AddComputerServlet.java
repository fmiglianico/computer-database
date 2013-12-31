package com.formation.computerdb.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.computerdb.domain.Company;
import com.formation.computerdb.domain.Computer;
import com.formation.computerdb.service.DataService;
import com.formation.computerdb.validator.ComputerValidator;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static Logger log = LoggerFactory.getLogger(AddComputerServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		DataService ds = DataService.getInstance();
		
		ArrayList<Company> companies = ds.getAllCompanies();

		request.setAttribute("companies", companies);
		request.getRequestDispatcher( "WEB-INF/addComputer.jsp" ).forward(request, response);

	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		
		String name = request.getParameter("name");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("company");
		
		if(!ComputerValidator.isValid(name, introduced, discontinued))
			response.sendRedirect("dashboard");
		
		Date introducedDate = null, discontinuedDate = null;
		
		try {

			introducedDate = sdf.parse(introduced);
			discontinuedDate = sdf.parse(discontinued);
		} catch (ParseException e) {
			log.error("Warning : " + e.getMessage());
		}

		Long id = Long.valueOf(companyId);
		
		DataService ds = DataService.getInstance();
		
		Company company = ds.getCompany(id.intValue());
		
		Computer computer = new Computer(null, name, company, introducedDate, discontinuedDate);

		ds.createComputer(computer);

		response.sendRedirect("dashboard");
		//request.getRequestDispatcher( "WEB-INF/dashboard.jsp").forward(request, response);

	}
}
