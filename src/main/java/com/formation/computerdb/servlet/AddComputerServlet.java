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
 * Servlet called when the user wants to add a computer in DB
 * @author F. Miglianico
 *
 */
@WebServlet("/addComputer")
public class AddComputerServlet extends SpringInjectedServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(AddComputerServlet.class);
	
	@Autowired
	private DataService ds = null;

	@Autowired
	private ComputerMapper computerMapper = null;

	@Autowired
	private ComputerValidator computerValidator = null;

	/**
	 * @param ds the ds to set
	 */
	public void setComputerMapper(ComputerMapper computerMapper) {
		this.computerMapper = computerMapper;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		List<Company> companies = ds.getAllCompanies();

		// Send companies to jsp
		request.setAttribute("companies", companies);
		request.getRequestDispatcher( "WEB-INF/addComputer.jsp" ).forward(request, response);

	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// Creates DTO from parameters set by user
		ComputerDto cdto = new ComputerDto();
		cdto.setName(request.getParameter("name"));
		cdto.setIntroduced(request.getParameter("introduced"));
		cdto.setDiscontinued(request.getParameter("discontinued"));
		cdto.setCompanyId(Long.valueOf(request.getParameter("company")));

		if(cdto.getCompanyId() == 0)
			cdto.setCompanyId(null);
		
		// Validate computer - error code returned if not valid
		int retCode = computerValidator.isValid(cdto);
		if(retCode != 0) {
			request.setAttribute("retCode", retCode);
			request.setAttribute("cdto", cdto);
			
			List<Company> companies = ds.getAllCompanies();
			request.setAttribute("companies", companies);
			
			request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(request, response);
			return;
		}
		
		// ComputerDto is valid, conversion to Computer
		Computer computer = computerMapper.fromDto(cdto);
		
		// Creation in DB
		RC rc = ds.createComputer(computer);
		
		if(rc == RC.FAILED) {
			response.sendRedirect("dashboard?message=creationNOK");
			return;
		}
		
		log.info(new StringBuilder("Creation of computer \"").append(computer.getName()).append("\" successful").toString());

		response.sendRedirect("dashboard?message=creationOK");

	}
}
