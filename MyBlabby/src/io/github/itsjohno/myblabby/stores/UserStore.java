package io.github.itsjohno.myblabby.stores;

import java.util.UUID;

public class UserStore
{
	private String username;
	private UUID uuid;
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public UUID getUUID()
	{
		return uuid;
	}
	
	public void setUUID(UUID uuid)
	{
		this.uuid = uuid;
	}
}
