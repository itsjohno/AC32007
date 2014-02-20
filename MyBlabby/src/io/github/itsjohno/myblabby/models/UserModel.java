package io.github.itsjohno.myblabby.models;

import com.datastax.driver.core.Cluster;
import io.github.itsjohno.myblabby.libraries.*;
import io.github.itsjohno.myblabby.stores.UserStore;

public class UserModel {
	
	Cluster cluster;
	
	public void setCluster(Cluster cluster){
		this.cluster=cluster;
	}
	
	/**
	 * Creates a new user within the Cassandra DB. Returns the UserStore of the user that has just been created
	 * so that we can log them in (and set some cookies so that their sessions persists)
	 * @return User store of created user
	 */
	public UserStore createUser()
	{
		return null;
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
}