package io.github.itsjohno.blabby.stores;

import java.util.UUID;

public class TweetStore
{
	//================================================================================
    // Properties
    //================================================================================
	
	String username;
	String tweet;
	UUID uuid;
	
	//================================================================================
    // Constructors
    //================================================================================
	
	public TweetStore()
	{ }
	
	public TweetStore(String username, String tweet, UUID uuid)
	{
		this.username = username;
		this.tweet = tweet;
		this.uuid = uuid;
	}
	
	//================================================================================
    // Accessors
    //================================================================================
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public void setTweet(String tweet)
	{
		this.tweet = tweet;
	}
	
	public String getTweet()
	{
		return this.tweet;
	}
	
	public void setUUID(UUID uuid)
	{
		this.uuid = uuid;
	}
	
	public UUID getUUID()
	{
		return this.uuid;
	}
}
