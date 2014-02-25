package io.github.itsjohno.blabby.servlets;

import io.github.itsjohno.blabby.libraries.Helper;
import io.github.itsjohno.blabby.stores.UserStore;

import java.io.IOException;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PageServlet
 */
@WebServlet(name="PageServlet", urlPatterns={"/page/","/page/*"})
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher rd;
		String[] urlArgs = Helper.SplitRequestPath(request);
		
		HttpSession session = request.getSession();
		
		if (urlArgs.length == 2)
		{
			String url = "/WEB-INF/"+urlArgs[1]+".jsp";
			URL urlCheck = getServletContext().getResource(url);
			
			if (urlCheck != null)
			{
				rd = request.getRequestDispatcher(url); 
			}
			else if (session.getAttribute("user") != null && urlArgs[1].equalsIgnoreCase("login"))
			{
				UserStore us = (UserStore)session.getAttribute("user");
				if (us.getUsername() != null && us.getPassword() != null)
				{
					rd = request.getRequestDispatcher("/WEB-INF/main.jsp"); 
				}
				else
				{
					rd = request.getRequestDispatcher(url); 
				}
			}
			else
			{
				request.setAttribute("url",url);
				rd = request.getRequestDispatcher("/WEB-INF/404.jsp"); 
			}
		}
		else
		{
			rd = request.getRequestDispatcher("/WEB-INF/index.jsp"); 
		}
		
		rd.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher rd;
		String[] urlArgs = Helper.SplitRequestPath(request);
		
		if (urlArgs.length == 2)
		{
			String url = "/WEB-INF/"+urlArgs[1]+".jsp";
			URL urlCheck = getServletContext().getResource(url);
			
			if (urlCheck != null)
			{
				rd = request.getRequestDispatcher(url); 
			}
			else
			{
				request.setAttribute("url",url);
				rd = request.getRequestDispatcher("/WEB-INF/404.jsp"); 
			}
		}
		else
		{
			rd = request.getRequestDispatcher("/WEB-INF/index.jsp"); 
		}
		
		rd.forward(request, response);
	}
}
