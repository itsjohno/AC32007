package io.github.itsjohno.blabby.servlets;

import io.github.itsjohno.blabby.stores.UserStore;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SetSession
 */
@WebServlet("/Session")
public class Session extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Session() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		UserStore uS = (UserStore)session.getAttribute("user");
		
		if (uS != null)
		{
			response.getWriter().println("Username: " + uS.getUsername());
			response.getWriter().println("Password: " + uS.getPassword());
			response.getWriter().println("E-Mail: " + uS.getEmail());
			response.getWriter().println("JSession ID: " + session.getId());
		}
		else
		{
			response.getWriter().println("No user bean set for current session");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
