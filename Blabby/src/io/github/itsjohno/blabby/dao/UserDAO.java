package io.github.itsjohno.blabby.dao;

import io.github.itsjohno.blabby.libraries.Cassandra;
import io.github.itsjohno.blabby.stores.UserStore;

import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

public class UserDAO
{
	/**
	 * Performs the C(reate) of CRUD. Creates a User within the Database
	 * @param user to be stored in the database
	 * @return If the create was performed successfully
	 */
	public boolean create(UserStore user)
	{
		try
		{
			BoundStatement boundStatement = Cassandra.createBoundStatement("INSERT INTO users (uuid, username, email, password, sessionid) VALUES (?, ?, ?, ?, ?)");	
			Cassandra.getSession().execute(boundStatement.bind(user.getUUID(), user.getUsername(), user.getEmail(), user.getPassword(), user.getSessionID()));
			
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Performs the R(etrieve) of CRUD. Retrieves a User from the Database
	 * @param UUID of user to be fetched
	 * @return UserStore of the user to be retrieved
	 */
	public UserStore retrieve(UUID uuid)
	{
		try
		{
			BoundStatement boundStatement = Cassandra.createBoundStatement("SELECT * FROM users WHERE uuid = ? LIMIT 1 ALLOW FILTERING");	
			ResultSet rs = Cassandra.getSession().execute(boundStatement.bind(uuid));
			
			Row row = rs.one();
			if (row != null)
			{
				return new UserStore(row.getUUID("uuid"), row.getString("username"), row.getString("password"), row.getString("email"), row.getString("sessionid"));
			}
			else
			{
				return null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Performs the R(etrieve) of CRUD. Retrieves a User from the Database
	 * @param UUID of user to be fetched
	 * @return UserStore of the user to be retrieved
	 */
	public UserStore retrieve(UserStore user)
	{
		try
		{
			BoundStatement boundStatement = Cassandra.createBoundStatement("SELECT * FROM users WHERE username = ? LIMIT 1 ALLOW FILTERING");	
			ResultSet rs = Cassandra.getSession().execute(boundStatement.bind(user.getUsername()));
			
			Row row = rs.one();
			if (row != null)
			{
				user.populate(row.getUUID("uuid"), row.getString("username"), row.getString("password"), row.getString("email"), row.getString("sessionid"));
				return user;
			}
			else
			{
				return null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Performs the R(etrieve) of CRUD. Retrieves a User from the Database
	 * @param UUID of user to be fetched
	 * @return UserStore of the user to be retrieved
	 */
	public UserStore retrive(UUID uuid)
	{
		try
		{
			BoundStatement boundStatement = Cassandra.createBoundStatement("SELECT * FROM users WHERE uuid = ? LIMIT 1 ALLOW FILTERING");	
			ResultSet rs = Cassandra.getSession().execute(boundStatement.bind(uuid));
			
			Row row = rs.one();
			if (row != null)
			{
				return new UserStore(row.getUUID("uuid"), row.getString("username"), row.getString("password"), row.getString("email"), row.getString("sessionid"));
			}
			else
			{
				return null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Performs the U(pdate) of CRUD. Updates a User in the Database
	 * @param UserStore containing updated user details
	 */
	public void update(UserStore user)
	{
		BoundStatement boundStatement = Cassandra.createBoundStatement("UPDATE users SET username = ?, password = ?, email = ?, sessionid = ? WHERE uuid = ?");	
		Cassandra.getSession().execute(boundStatement.bind(user.getUsername(), user.getPassword(), user.getEmail(), user.getSessionID(), user.getUUID()));
	}
	
	/**
	 * Performs the D(elete) of CRUD. Deletes a User from the Database
	 * @param uuid of the user to be deleted
	 */
	public void delete(UserStore user)
	{
		BoundStatement boundStatement = Cassandra.createBoundStatement("DELETE FROM users WHERE uuid = ?");	
		Cassandra.getSession().execute(boundStatement.bind(user.getUUID()));
	}

}
