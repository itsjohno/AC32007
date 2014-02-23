package io.github.itsjohno.blabby.servlets;

import io.github.itsjohno.blabby.libraries.*;
import io.github.itsjohno.blabby.dao.TweetDAO;
import io.github.itsjohno.blabby.stores.TweetStore;

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
			TweetDAO tDAO = new TweetDAO();
			
			request.getParameter("message");
		}
		else
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
    
	/**
	 * Read
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String[] urlArgs = Helper.SplitRequestPath(request);
		
		if (urlArgs.length > 1)
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
		else
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	/**
	 * It's not possible to perform an UPDATE (PUT) on a Tweet, so send a 405 Error.
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * Delete
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String[] urlArgs = Helper.SplitRequestPath(request);
		
		if (urlArgs.length > 1)
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
					System.out.println("Delete Tweet: " + tweet.getUUID().toString());
					tDAO.delete(tweet);
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
