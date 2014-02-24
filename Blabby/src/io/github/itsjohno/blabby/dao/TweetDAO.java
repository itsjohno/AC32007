package io.github.itsjohno.blabby.dao;

import java.util.LinkedList;
import java.util.UUID;

import com.datastax.driver.core.*;

import io.github.itsjohno.blabby.libraries.Cassandra;
import io.github.itsjohno.blabby.stores.TweetStore;
import io.github.itsjohno.blabby.stores.UserStore;

/**
 * Tweet DAO implements CRUD for the TweetModel
 * UPDATE is not possible on a Tweet, once created a Tweet is not editable. Must be instead be deleted.
 * @author Johnathan
 *
 */
public class TweetDAO
{
	/**
	 * Performs the C(reate) of CRUD. Creates a Tweet within the Database
	 * @param tweet to be stored in the database
	 * @return If the create was performed successfully
	 */
	public boolean create(TweetStore tweet)
	{
		try
		{
			BoundStatement boundStatement = Cassandra.createBoundStatement("INSERT INTO tweets (username, tweet, interaction_time) VALUES (?, ?, ?)");	
			Cassandra.getSession().execute(boundStatement.bind(tweet.getUsername(), tweet.getTweet(), tweet.getUUID()));
			
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Performs the R(etrieve) of CRUD. Retrieves a single Tweet from the Database
	 * @param uuid of the tweet to be retrieved
	 * @return TweetStore containing retrieved tweet
	 */
	public TweetStore retrieve(UUID uuid)
	{
		try
		{
			BoundStatement boundStatement = Cassandra.createBoundStatement("SELECT * FROM tweets WHERE interaction_time = ? LIMIT 1 ALLOW FILTERING");	
			ResultSet rs = Cassandra.getSession().execute(boundStatement.bind(uuid));
			
			Row row = rs.one();
			if (row != null)
			{
				return new TweetStore(row.getString("username"), row.getString("tweet"), row.getUUID("interaction_time"));
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
	
	public LinkedList<TweetStore> retrieve(UserStore user)
	{
		LinkedList<TweetStore> tweetList = new LinkedList<TweetStore>();
		
		try
		{
			BoundStatement boundStatement = Cassandra.createBoundStatement("SELECT * FROM tweets WHERE username = ?");	
			ResultSet rs = Cassandra.getSession().execute(boundStatement.bind(user.getUsername()));
			
			if (rs.isExhausted())
			{
				System.out.println("No Tweets returned");
			} 
			else 
			{
				for (Row row : rs) 
				{
					TweetStore ts = new TweetStore();
					ts.setTweet(row.getString("tweet"));
					ts.setUsername(row.getString("username"));
					ts.setUUID(row.getUUID("interaction_time"));
					tweetList.add(ts);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return tweetList;
	}
	
	/**
	 * Performs the D(elete) of CRUD. Deletes a Tweet from the Database
	 * @param uuid of the tweet to be deleted
	 */
	public void delete(UUID uuid)
	{
		BoundStatement boundStatement = Cassandra.createBoundStatement("DELETE FROM tweets WHERE interaction_time = ?");	
		Cassandra.getSession().execute(boundStatement.bind(uuid));
	}
	
	/**
	 * Performs the D(elete) of CRUD. Deletes a Tweet from the Database
	 * @param tweet to be deleted
	 * 
	 */
	public void delete(TweetStore tweet)
	{
		BoundStatement boundStatement = Cassandra.createBoundStatement("DELETE FROM tweets WHERE interaction_time = ?");	
		Cassandra.getSession().execute(boundStatement.bind(tweet.getUUID()));
	}
}
