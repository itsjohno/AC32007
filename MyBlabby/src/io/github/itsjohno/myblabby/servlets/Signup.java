package io.github.itsjohno.myblabby.servlets;

import io.github.itsjohno.myblabby.libraries.Cassandra;
import io.github.itsjohno.myblabby.libraries.Conversion;
import io.github.itsjohno.myblabby.models.*;
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
@WebServlet(name="Signup", urlPatterns={"/signup"})
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException
	{
		cluster = Cassandra.getCluster();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/signup.jsp"); 
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher rd;
		
		if (request.getParameter("pass").length() < 5)
		{
			request.setAttribute("error", "Password must be 5 characters or more");
			rd = request.getRequestDispatcher("WEB-INF/signup.jsp"); 
		}
		else if (!request.getParameter("pass").equals(request.getParameter("pass-confirm")))
		{
			request.setAttribute("error", "Provided passwords did not match!");
			rd = request.getRequestDispatcher("WEB-INF/signup.jsp"); 
		}
		else
		{
			UserModel um = new UserModel();
			um.setCluster(cluster);
			
			// Check that username is not already taken.
			if (um.checkForUser(request.getParameter("user")))
			{
				// Username already exists
				request.setAttribute("error", "That username is already taken");
				rd = request.getRequestDispatcher("WEB-INF/signup.jsp"); 
			}
			else
			{
				UserStore us = null;
				
				try
				{
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					String password = Conversion.byteArrayToString(digest.digest(request.getParameter("pass").getBytes("UTF-8")));
					us = um.createUser(request.getParameter("user"), password, request.getParameter("email"));
					
				}
				catch (NoSuchAlgorithmException e)
				{ }
				
				if (us == null)
				{
					request.setAttribute("error", "An error occurred when trying to create the user. Please <a href=\"contact.jsp\">contact us</a> if this error persists");
					rd = request.getRequestDispatcher("WEB-INF/signup.jsp"); 
				}
				else
				{
					// TODO Sign in the user here!
					// We have the User Store here, so we need to use it.
					
					request.setAttribute("error", "User Created!: " + us.getUsername() + " / " + us.getUUID().toString());
					rd = request.getRequestDispatcher("WEB-INF/signup.jsp"); 
				}
			}
		}
		
		rd.forward(request, response);
	}

}
