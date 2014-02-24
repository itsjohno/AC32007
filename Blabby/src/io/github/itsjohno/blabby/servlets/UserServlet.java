package io.github.itsjohno.blabby.servlets;

import io.github.itsjohno.blabby.dao.UserDAO;
import io.github.itsjohno.blabby.stores.UserStore;
import io.github.itsjohno.blabby.libraries.Helper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession session = request.getSession();
		String[] urlArgs = Helper.SplitRequestPath(request);
		
		if (session.getAttribute("user") != null)
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cannot perform user action if currently logged in");
		}
		else if (urlArgs.length == 2 && urlArgs[1].equalsIgnoreCase("login"))
		{
			// As HTML does not support PUT, we'll feign this using POST and check if we're intending to login.
			doPut(request, response);
		}
		else if (urlArgs.length == 2 && urlArgs[1].equalsIgnoreCase("register"))
		{
			UserDAO uDAO = new UserDAO();
			
			if (request.getParameter("password").equals(request.getParameter("password-confirm")))
			{
				if (request.getParameter("username") != null && request.getParameter("password") != null && request.getParameter("password-confirm") != null && request.getParameter("email") != null)
				{
					String hPass = Helper.hashPassword(request.getParameter("password"));
					
					if (hPass.equals(request.getParameter("password")))
					{
						response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Could not hash password");
					}
					else
					{
						if (uDAO.retrieve(new UserStore(null, request.getParameter("username"), null, null, null)) == null)
						{
							UserStore userStore = new UserStore(Helper.getTimeUUID(), request.getParameter("username"), hPass, request.getParameter("email"), session.getId());
							
							uDAO.create(userStore);
							session.setAttribute("user", userStore);
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
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Passwords do not match");
			}
		}
		else
		{
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Cannot POST to this URL, perhaps you meant /user/register");
		}
	}
    
	/**
	 * Read
	 * We're gonna want to either view a user (i.e /user/#username) or logout a user (i.e. /user/logout)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String[] urlArgs = Helper.SplitRequestPath(request);
		
		if (urlArgs.length == 2 && urlArgs[1].equalsIgnoreCase("logout"))
		{
			UserDAO uDAO = new UserDAO();
			UserStore user = (UserStore)session.getAttribute("user");
			
			if (user != null)
			{
				user.setSessionID(null);
				uDAO.update(user);
				
				session.setAttribute("user", null);
				
				response.sendError(HttpServletResponse.SC_NO_CONTENT);
			}
			else
			{
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
		else
		{
			// Attempt to find the user with the username held within urlArgs[1]
		}
	}
	
	/**
	 * Update
	 * Login the User! 
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String[] urlArgs = Helper.SplitRequestPath(request);
		
		if (urlArgs.length == 2 && urlArgs[1].equalsIgnoreCase("login"))
		{
			UserDAO uDAO = new UserDAO();
			
			String hPass = Helper.hashPassword(request.getParameter("password"));
			
			if (hPass.equals(request.getParameter("password")))
			{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Could not hash password");
			}
			else
			{
				UserStore userStore = uDAO.retrieve(new UserStore(null, request.getParameter("username"), null, null, null));
				
				if (userStore.getPassword().equals(hPass))
				{
					userStore.setSessionID(session.getId());
					
					uDAO.update(userStore);
					
					session.setAttribute("user", userStore);
					response.sendError(HttpServletResponse.SC_OK);
				}
				else
				{
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials supplied");
				}
			}
		}
		else
		{
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Cannot PUT to this URL, did you mean /user/login");
		}
	}

	/**
	 * Delete
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		UserDAO uDAO = new UserDAO();
		UserStore user = (UserStore)session.getAttribute("user");
		
		if (user != null)
		{
			uDAO.delete(user);
			session.setAttribute("user", null);
			response.sendError(HttpServletResponse.SC_NO_CONTENT);
		}
		else
		{
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

	}
}
