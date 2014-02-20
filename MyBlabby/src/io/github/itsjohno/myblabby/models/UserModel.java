package io.github.itsjohno.myblabby.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

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
		Session session = cluster.connect("blabby");
		PreparedStatement statement = session.prepare("INSERT INTO users (uuid, username, password, email, created, last_login) VALUES (?, ?, ?, ?, dateof(now()), dateof(now()))");
		BoundStatement boundStatement = new BoundStatement(statement);
		
		session.execute(boundStatement.bind(Conversion.getTimeUUID(), username, password, email));
		
		if (checkForUser(username))
		{
			UserStore us = new UserStore();
			us.setUsername(username);
			return us;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Logs in the specified user, returns the user store of the user to be logged in. Null if incorrect details
	 * are supplied.
	 * @return User store of logged in user.
	 */
	public UserStore loginUser()
	{
		return null;
	}
	
	public boolean checkForUser(String username)
	{
		boolean found = false;
		
		Session session = cluster.connect("blabby");
		PreparedStatement statement = session.prepare("SELECT username FROM users WHERE username = ?");
		BoundStatement boundStatement = new BoundStatement(statement);
		
		ResultSet rs = session.execute(boundStatement.bind(username));
		
		if (!rs.all().isEmpty())
		{
			found = true;
		}
		
		return found;
	}
}