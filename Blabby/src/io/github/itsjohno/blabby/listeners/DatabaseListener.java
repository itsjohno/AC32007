package io.github.itsjohno.blabby.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import io.github.itsjohno.blabby.libraries.*;

/**
 * Listener detects servlet context being initialised and begins our connection to the database.
 */
@WebListener
public class DatabaseListener implements ServletContextListener
{
    /**
     * Default constructor. 
     */
    public DatabaseListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent servletContextEvent)
    {
    	String keyspace = "blabby";
    	ServletContext ctx = servletContextEvent.getServletContext();
    	
    	Cassandra.createSession(keyspace);
    	ctx.setAttribute("CassandraSession", Cassandra.getSession());
    	
    	System.out.println("Cassandra session created using keyspace " + keyspace + " for application");
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {
        Cassandra.closeSession();
        Cassandra.closeCluster();
        System.out.println("Database connection closed for Application.");
    }
}
