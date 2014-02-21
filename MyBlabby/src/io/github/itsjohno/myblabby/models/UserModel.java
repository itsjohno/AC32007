package io.github.itsjohno.myblabby.models;

import com.datastax.driver.core.*;

import io.github.itsjohno.myblabby.libraries.*;
import io.github.itsjohno.myblabby.stores.UserStore;

public class UserModel
{
	Cluster cluster;
	
	public UserModel()
	{ }
	
	public void setCluster(Cluster cluster){
		this.cluster=cluster;
	}
	
	/**
	 * Creates a new user within the Cassandra DB. Returns the UserStore of the user that has just been created
	 * so that we can log them in (and set some cookies so that their sessions persists)
	 * @return User store of created user
	 */
	public UserStore createUser(String username, String password, String email)
	{
		java.util.UUID uuid = Conversion.getTimeUUID();
		
		Session session = cluster.connect("blabby");
		PreparedStatement statement = session.prepare("INSERT INTO users (uuid, username, password, email, created) VALUES (?, ?, ?, ?, dateof(now()))");
		BoundStatement boundStatement = new BoundStatement(statement);
		
		session.execute(boundStatement.bind(uuid, username, password, email));
		
		if (checkForUser(username))
		{
			UserStore us = new UserStore();
			us.setUsername(username);
			us.setUUID(uuid);
			return us;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Verify's login credentials of the specified user, returns the user store of the user to be logged in. Null if incorrect details
	 * are supplied.
	 * @return User store of user to be logged in.
	 */
	public UserStore verifyUser(String username, String password)
	{
		UserStore us = null;
		
		Session session = cluster.connect("blabby");
		PreparedStatement statement = session.prepare("SELECT * FROM users WHERE username = ?");
		BoundStatement boundStatement = new BoundStatement(statement);
		
		ResultSet rs = session.execute(boundStatement.bind(username));
		
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
		
		Session session = cluster.connect("blabby");
		PreparedStatement statement = session.prepare("SELECT username FROM users WHERE username = ? AND password = ?");
		BoundStatement boundStatement = new BoundStatement(statement);
		
		ResultSet rs = session.execute(boundStatement.bind(username));
		
		if (!rs.all().isEmpty())
		{
			found = true;
		}
		
		return found;
	}
}