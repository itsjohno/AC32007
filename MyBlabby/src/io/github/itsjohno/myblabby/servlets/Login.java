package io.github.itsjohno.myblabby.servlets;

import io.github.itsjohno.myblabby.lib.*;
import io.github.itsjohno.myblabby.models.UserModel;
import io.github.itsjohno.myblabby.stores.UserStore;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datastax.driver.core.Cluster;

/**
 * Servlet implementation class Login
 */
@WebServlet(name="Login", urlPatterns={"/login"})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException
	{
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp"); 
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		UserModel um = new UserModel();	
		UserStore us = null;
		RequestDispatcher rd;
		
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String password = Helper.byteArrayToString(digest.digest(request.getParameter("pass").getBytes("UTF-8")));
			us = um.verifyUser(request.getParameter("user"), password);
		}
		catch (NoSuchAlgorithmException e)
		{ 
			request.setAttribute("error", "NoSuchAlgo. Please <a href=\"contact.jsp\">contact us</a> if this error persists");
			rd = request.getRequestDispatcher("WEB-INF/login.jsp"); 
		}
		
		if (us == null)
		{
			request.setAttribute("error", "Incorrect username/password entered.");
			rd = request.getRequestDispatcher("WEB-INF/login.jsp"); 
		}
		else
		{
			// TODO Sign in the user here!
			// We have the user store here, so we need to use it
			
			request.setAttribute("error", "Logging in user: " + us.getUsername() + " / " + us.getUUID().toString());
			rd = request.getRequestDispatcher("WEB-INF/login.jsp"); 
		}
		
		rd.forward(request, response);
	}

}
