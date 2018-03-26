package com.minpet.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet{

	private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());
	private static final long serialVersionUID = 1L;
	
	protected final void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException
	{
		logger.warning("invalidating session");
		boolean printJaasInfo = false;
        if(printJaasInfo){
            try{
                logger.info("LogoutServlet>request.getServletPath():"+request.getServletPath());//faces
                logger.info("LogoutServlet>request.getClass().getName():"+request.getClass().getName());//org.apache.catalina.connector.RequestFacade
                logger.info("LogoutServlet>isAdministrator:"+request.isUserInRole("authenticated"));//logged in with ybxiang:true
                logger.info("LogoutServlet>remoteUser:"+request.getRemoteUser());//ybxiang
                logger.info("LogoutServlet>userPrincipalName:"+(request.getUserPrincipal()==null?"null":request.getUserPrincipal().getName()));//ybxiang

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        //********************** log out(clean something) **********************//
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        //response.setHeader("Expires", new java.util.Date().toString());//http://www.coderanch.com/t/541412/Servlets/java/Logout-servlet-button 
        response.setHeader("Expires", "0");//http://www.coderanch.com/t/541412/Servlets/java/Logout-servlet-button
        //

        if(request.getSession(false)!=null){
            request.getSession(false).invalidate();//remove session.
        }
        if(request.getSession()!=null){
            request.getSession().invalidate();//remove session.
        }

        request.logout();//JAAS log out (from servlet specification)! It is a MUST!
        
        //********************** print JAAS info again **********************//
        if(printJaasInfo){
            try{
                logger.info("LogoutServlet>request.getServletPath():"+request.getServletPath());//faces
                logger.info("LogoutServlet>request.getClass().getName():"+request.getClass().getName());//org.apache.catalina.connector.RequestFacade
                logger.info("LogoutServlet>isAdministrator:"+request.isUserInRole("authenticated"));//logged in with ybxiang:true
                logger.info("LogoutServlet>remoteUser:"+request.getRemoteUser());//ybxiang
                logger.info("LogoutServlet>userPrincipalName:"+(request.getUserPrincipal()==null?"null":request.getUserPrincipal().getName()));//ybxiang
            }catch(Exception e){
                e.printStackTrace();
            }
        }
		response.sendRedirect("index.xhtml?faces-redirect=true");
	}
}
