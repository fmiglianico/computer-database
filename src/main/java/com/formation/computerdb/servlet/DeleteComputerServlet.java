package com.formation.computerdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.formation.computerdb.common.RC;
import com.formation.computerdb.service.DataService;

/**
 * Servlet called for deletion of a computer
 * @author F. Miglianico
 *
 */
@WebServlet("/deleteComputer")
public class DeleteComputerServlet extends SpringInjectedServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private DataService ds = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		// Id of computer
		Long id = Long.valueOf(request.getParameter("id"));
		if(id == null) {
			response.sendRedirect("dashboard");
			return;
		}
		
		RC rc = ds.deleteComputer(id.intValue());
		
		if(rc == RC.FAILED) {
			response.sendRedirect("dashboard?message=deleteNOK");
			return;
		}

		response.sendRedirect("dashboard?message=deleteOK");

	}
}
