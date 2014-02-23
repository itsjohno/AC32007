package io.github.itsjohno.myblabby.servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.datastax.driver.core.Cluster;

import io.github.itsjohno.myblabby.lib.*;
import io.github.itsjohno.myblabby.models.*;
import io.github.itsjohno.myblabby.stores.*;
 
@WebServlet(name="Tweet", urlPatterns={"/tweet"})
public class Tweet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public Tweet()
	{
        super();
    }
	
	public void init(ServletConfig config) throws ServletException
	{
		// TODO Auto-generated method stub
	}
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	TweetModel tm = new TweetModel();
		response.getWriter().println("Something's going wrong");
		LinkedList<TweetStore> tweetList = tm.getTweets();
		request.setAttribute("Tweets", tweetList); //Set a bean with the list in it
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/getTweets.jsp"); 

		rd.forward(request, response);
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
         doGet(request,response);
    }

}
