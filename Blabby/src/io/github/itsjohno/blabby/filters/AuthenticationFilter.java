package io.github.itsjohno.blabby.filters;

import io.github.itsjohno.blabby.dao.UserDAO;
import io.github.itsjohno.blabby.stores.UserStore;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter(filterName="AuthenticationFilter", urlPatterns={"/tweet", "/page/main", "/page/logout"})
public class AuthenticationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthenticationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		UserStore user = (UserStore)session.getAttribute("user");
		
		if (user != null)
		{
			UserDAO uDAO = new UserDAO();
			
			UserStore auth = uDAO.retrieve(user.getUUID());
			
			if (auth != null)
			{
				if (auth.getSessionID().equals(user.getSessionID()) && auth.getPassword().equals(user.getPassword()))
				{
					chain.doFilter(request, response);
				}
				else
				{
					RequestDispatcher rd = request.getRequestDispatcher("/page/login"); 
					request.setAttribute("error", "You need to be logged in to perform that action");
					rd.forward(request, response);
				}
			}
			else
			{
				RequestDispatcher rd = request.getRequestDispatcher("/page/login"); 
				request.setAttribute("error", "You need to be logged in to perform that action");
				rd.forward(request, response);
			}
		}
		else
		{
			RequestDispatcher rd = request.getRequestDispatcher("/page/login"); 
			request.setAttribute("error", "You need to be logged in to perform that action");
			rd.forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
