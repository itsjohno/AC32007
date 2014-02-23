package io.github.itsjohno.blabby.servlets;

import io.github.itsjohno.blabby.dao.UserDAO;
import io.github.itsjohno.blabby.stores.UserStore;

import io.github.itsjohno.blabby.libraries.Helper;
import io.github.itsjohno.blabby.libraries.PojoMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(name="UserServlet", urlPatterns={"/user","/user/*"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Create
	 * @field username is username of user to be created
	 * @field password is password of user to be created
	 * @field email is email of user to be created
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String[] urlArgs = Helper.SplitRequestPath(request);
		
		if (urlArgs.length == 2 && urlArgs[1].equalsIgnoreCase("register"))
		{
			UserDAO uDAO = new UserDAO();
			
			if (request.getParameter("username") != null && request.getParameter("password") != null && request.getParameter("email") != null)
			{
				String hPass = Helper.hashPassword(request.getParameter("password"));
				
				if (hPass.equals(request.getParameter("password")))
				{
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Could not hash password");
				}
				else
				{
					if (uDAO.retrieve(new UserStore(null, request.getParameter("username"), null, null)) == null)
					{
						uDAO.create(new UserStore(Helper.getTimeUUID(), request.getParameter("username"), hPass, request.getParameter("email")));
						response.sendError(HttpServletResponse.SC_CREATED);
					}
					else
					{
						response.sendError(HttpServletResponse.SC_CONFLICT, "Provided username already exists");
					}
				}
			}
			else
			{
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Required field left empty");
			}
		}
		else
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cannot post to this URL, perhaps you meant /user/register");
		}
	}
    
	/**
	 * Read
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}
	
	/**
	 * Update
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}

	/**
	 * Delete
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
	}
}
