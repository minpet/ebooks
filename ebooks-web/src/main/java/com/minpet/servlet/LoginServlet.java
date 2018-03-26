package com.minpet.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getName()); 
	
	protected final void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException
	{
		StringBuilder builder = new StringBuilder();
		Enumeration<String> keys = request.getSession().getAttributeNames();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			builder.append(key);
			builder.append(";");
		}
		
		logger.warning(builder.toString());
		
		logger.warning("HEEEERE");
		response.sendRedirect("index.xhtml");
	}
}
