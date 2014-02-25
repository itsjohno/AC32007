package io.github.itsjohno.blabby.servlets;

import io.github.itsjohno.blabby.libraries.*;
import io.github.itsjohno.blabby.dao.TweetDAO;
import io.github.itsjohno.blabby.dao.UserDAO;
import io.github.itsjohno.blabby.stores.TweetStore;
import io.github.itsjohno.blabby.stores.UserStore;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TweetServlet
 */
@WebServlet(name="TweetServlet", urlPatterns={"/tweet","/tweet/*"})
public class TweetServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    /**
	 * Create
	 * @field message is the tweet to be created
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String[] urlArgs = Helper.SplitRequestPath(request);
		
		if (urlArgs.length == 1)
		{
			HttpSession session = request.getSession();
			
			TweetDAO tDAO = new TweetDAO();
			UserStore user = (UserStore)session.getAttribute("user");
			
			if (user != null)
			{
				if (request.getParameter("message") != null)
				{
					tDAO.create(new TweetStore(user.getUsername(), request.getParameter("message"), Helper.getTimeUUID()));
					response.sendError(HttpServletResponse.SC_ACCEPTED);
				}
				else
				{
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Required field left empty");
				}
			}
			else
			{
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
		else
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
    
	/**
	 * Read
	 * Get a Tweet from the Database
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String[] urlArgs = Helper.SplitRequestPath(request);
		UserDAO uDAO = new UserDAO();
		
		if (urlArgs.length == 2)
		{
			boolean uuid = false;
			UserStore user = new UserStore(null, urlArgs[1], null, null, null);
			
			try
			{
				UUID.fromString(urlArgs[1]);
				uuid = true;
			}
			catch (Exception e)
			{
				uuid = false;
			}
			
			if (uDAO.retrieve(user) == null && uuid == true)
			{
				// Looks like we're trying to get a Tweet by UUID
				System.out.println("Retrieve Tweet: " + urlArgs[1]);
			
				try
				{
					TweetDAO tDAO = new TweetDAO();
					TweetStore tweet = tDAO.retrieve(UUID.fromString(urlArgs[1]));
					
					if (tweet == null)
					{
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
					else
					{  
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("application/json");
						response.getOutputStream().print(PojoMapper.toJson(tweet, true));
					}
				}
				catch (Exception e)
				{
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					e.printStackTrace();
				}
			}
			else if (uuid == false)
			{
				// Trying to get a Tweet by username are we?
				try
				{
					TweetDAO tDAO = new TweetDAO();
					LinkedList<TweetStore> tweetList = tDAO.retrieve(user);
					
					if (tweetList.peekFirst() == null)
					{
						response.sendError(HttpServletResponse.SC_NOT_FOUND);
					}
					else
					{
						Iterator<TweetStore> iterator;
						iterator = tweetList.iterator();  
						
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("application/json");
						response.getOutputStream().print("\"tweets\": [\n");
						while (iterator.hasNext())
						{
							response.getOutputStream().print(PojoMapper.toJson((TweetStore)iterator.next(), true));
							if (iterator.hasNext())
							{
								response.getOutputStream().print(",");
							}
							response.getOutputStream().print("\n");
						}
						response.getOutputStream().print("]");
					}
				}
				catch (Exception e)
				{
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					e.printStackTrace();
				}
			}
			else
			{
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else
		{
			// Just trying to get all tweets?
			try
			{
				TweetDAO tDAO = new TweetDAO();
				LinkedList<TweetStore> tweetList = tDAO.retrieve();
				
				if (tweetList.peekFirst() == null)
				{
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
				else
				{
					// We're wanting to get tweets for all users.
				}
			}
			catch (Exception e)
			{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Update
	 * It's not possible to perform an UPDATE (PUT) on a Tweet, so send a 405 Error.
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * Delete
	 * Delete a Tweet from the Database
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String[] urlArgs = Helper.SplitRequestPath(request);
		
		if (urlArgs.length == 2)
		{
			System.out.println("Retrieve Tweet: " + urlArgs[1]);
		
			try
			{
				TweetDAO tDAO = new TweetDAO();
				TweetStore tweet = tDAO.retrieve(UUID.fromString(urlArgs[1]));
				
				if (tweet == null)
				{
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
				else
				{
					HttpSession session = request.getSession();
					UserStore user = (UserStore)session.getAttribute("user");
					
					if (user != null && user.getUsername().equalsIgnoreCase(tweet.getUsername()))
					{
						tDAO.delete(tweet);
						response.sendError(HttpServletResponse.SC_NO_CONTENT);
					}
					else
					{
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorized to delete this tweet");
					}
				}
			}
			catch (Exception e)
			{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				System.out.println(sw.toString());
			}
		}
		else
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
