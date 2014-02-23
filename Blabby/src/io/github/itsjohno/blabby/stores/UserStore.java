package io.github.itsjohno.blabby.stores;

import java.util.UUID;

public class UserStore
{
	//================================================================================
    // Properties
    //================================================================================
	
	UUID uuid;
	String username;
	String password;
	String email;
	
	//================================================================================
    // Constructors
    //================================================================================
	
	public UserStore()
	{ }
	
	public UserStore(UUID uuid, String username, String password, String email)
	{
		this.uuid = uuid;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	//================================================================================
    // Accessors
    //================================================================================
	
	public void populate(UUID uuid, String username, String password, String email)
	{
		this.uuid = uuid;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public void setUUID(UUID uuid)
	{
		this.uuid = uuid;
	}
	
	public UUID getUUID()
	{
		return this.uuid;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getEmail()
	{
		return this.email;
	}
}
