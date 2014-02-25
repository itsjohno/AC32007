package io.github.itsjohno.blabby.servlets;

import io.github.itsjohno.blabby.dao.TweetDAO;
import io.github.itsjohno.blabby.dao.UserDAO;
import io.github.itsjohno.blabby.stores.TweetStore;
import io.github.itsjohno.blabby.stores.UserStore;
import io.github.itsjohno.blabby.libraries.Helper;
import io.github.itsjohno.blabby.libraries.PojoMapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
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
		
		UserDAO uDAO = new UserDAO();
		
		if (urlArgs.length == 2 && urlArgs[1].equalsIgnoreCase("logout"))
		{
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
			UserStore user = uDAO.retrieve(new UserStore(null, urlArgs[1], null, null, null));
			
			if (user != null)
			{
				try
				{
					TweetDAO tDAO = new TweetDAO();
					LinkedList<TweetStore> tweetList = tDAO.retrieve(user);
					
					request.setAttribute("tweets", tweetList);
					request.setAttribute("user", user.getUsername());
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/user.jsp");
					
					rd.forward(request, response);
				}
				catch (Exception e)
				{
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					e.printStackTrace();
				}
			}
			else
			{
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/404.jsp");
				request.setAttribute("url","/user/"+urlArgs[1]);
				rd.forward(request, response);
			}
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
				System.out.println("Username: " + request.getParameter("username"));
				System.out.println("Password: " + request.getParameter("password"));
				System.out.println("HPass: " + hPass);
				
				UserStore userStore = uDAO.retrieve(new UserStore(null, request.getParameter("username"), null, null, null));
				
				if (userStore != null)
				{
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
