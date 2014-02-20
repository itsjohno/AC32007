package io.github.itsjohno.myblabby.servlets;

import io.github.itsjohno.myblabby.libraries.Cassandra;
import io.github.itsjohno.myblabby.models.UserModel;

import java.io.IOException;

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserModel um = new UserModel();
		um.setCluster(cluster);
		
		request.setAttribute("error", "Incorrect username/password entered");
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/signup.jsp"); 
		rd.forward(request, response);
	}

}
