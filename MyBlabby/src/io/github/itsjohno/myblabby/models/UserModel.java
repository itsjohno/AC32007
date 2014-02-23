package io.github.itsjohno.myblabby.models;

import com.datastax.driver.core.*;

import io.github.itsjohno.myblabby.lib.*;
import io.github.itsjohno.myblabby.stores.UserStore;

public class UserModel
{	
	public UserModel()
	{ }
	
	/**
	 * Creates a new user within the Cassandra DB. Returns the UserStore of the user that has just been created
	 * so that we can log them in (and set some cookies so that their sessions persists)
	 * @return User store of created user
	 */
	public UserStore createUser(String username, String password, String email)
	{
		UserStore us = null;
		
		java.util.UUID uuid = Helper.getTimeUUID();
		
		BoundStatement boundStatement = Cassandra.createBoundStatement("INSERT INTO users (uuid, username, password, email, created) VALUES (?, ?, ?, ?, dateof(now()))");	
		Cassandra.getSession().execute(boundStatement.bind(uuid, username, password, email));
		
		if (checkForUser(username))
		{
			us = new UserStore();
			us.setUsername(username);
			us.setUUID(uuid);
		}
		
		return us;
	}
	
	/**
	 * Verify's login credentials of the specified user, returns the user store of the user to be logged in. Null if incorrect details
	 * are supplied.
	 * @return User store of user to be logged in.
	 */
	public UserStore verifyUser(String username, String password)
	{
		UserStore us = null;
		
		BoundStatement boundStatement = Cassandra.createBoundStatement("SELECT * FROM users WHERE username = ?");	
		ResultSet rs = Cassandra.getSession().execute(boundStatement.bind(username));
		
		if (!rs.isExhausted())
		{
			Row row = rs.one();
			
			if (row.getString("password").equalsIgnoreCase(password))
			{
				us = new UserStore();
				us.setUsername(row.getString("username"));
				us.setUUID(row.getUUID("uuid"));
			}
		}
		
		return us;
	}
	
	public boolean checkForUser(String username)
	{
		boolean found = false;
		
		BoundStatement boundStatement = Cassandra.createBoundStatement("SELECT username FROM users WHERE username = ?");	
		ResultSet rs = Cassandra.getSession().execute(boundStatement.bind(username));
		
		if (!rs.all().isEmpty())
		{
			found = true;
		}
		
		return found;
	}
}