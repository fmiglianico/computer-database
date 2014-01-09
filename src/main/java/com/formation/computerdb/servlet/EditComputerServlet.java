package com.formation.computerdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
@WebServlet("/editComputer")
public class EditComputerServlet extends SpringInjectedServlet {
	
	private static final long serialVersionUID = 1L;
	private Long id = null;
	
	private static Logger log = LoggerFactory.getLogger(EditComputerServlet.class);

	@Autowired
	private DataService ds = null;

	@Autowired
	private ComputerMapper computerMapper = null;

	@Autowired
	private ComputerValidator computerValidator = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// Get companies		
		List<Company> companies = ds.getAllCompanies();
		request.setAttribute("companies", companies);
		
		// Get computer to be edited
		id = Long.valueOf((String)request.getParameter("id"));
		if(id == null)
			response.sendRedirect("dashboard");
		
		Computer computer = ds.getComputer(id.intValue());
		ComputerDto cdto = ComputerMapper.toDto(computer);
		request.setAttribute("cdto", cdto);

		request.getRequestDispatcher( "WEB-INF/editComputer.jsp" ).forward(request, response);

	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// Get Computer attribute into Dto
		ComputerDto cdto = new ComputerDto();
		cdto.setId(id);
		cdto.setName(request.getParameter("name"));
		cdto.setIntroduced(request.getParameter("introduced"));
		cdto.setDiscontinued(request.getParameter("discontinued"));
		cdto.setCompanyId(Long.valueOf(request.getParameter("company")));
		
		if(cdto.getCompanyId() == 0)
			cdto.setCompanyId(null);
		
		// Validate values
		int retCode = computerValidator.isValid(cdto);
		
		// if computer is not valid, notify user
		if(retCode != 0) {
			request.setAttribute("retCode", retCode);
			request.setAttribute("cdto", cdto);
			
			List<Company> companies = ds.getAllCompanies();
			request.setAttribute("companies", companies);
			
			request.getRequestDispatcher("WEB-INF/editComputer.jsp").forward(request, response);
			
			return;
		}

		// Else, update computer in DB
		Computer computer = computerMapper.fromDto(cdto);

		RC rc = ds.updateComputer(computer);
		
		if(rc == RC.FAILED) {
			response.sendRedirect("dashboard?message=editNOK");
			return;
		}
		
		log.info(new StringBuilder("Edition of computer \"").append(computer.getName()).append("\" successful").toString());

		response.sendRedirect("dashboard?message=editOK");
		//request.getRequestDispatcher( "WEB-INF/dashboard.jsp").forward(request, response);

	}
}
