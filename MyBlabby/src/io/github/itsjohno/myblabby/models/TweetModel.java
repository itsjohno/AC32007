package io.github.itsjohno.myblabby.models;

import com.datastax.driver.core.*;
import java.util.LinkedList;

import io.github.itsjohno.myblabby.libraries.*;
import io.github.itsjohno.myblabby.stores.TweetStore;

/*
 * Expects a cassandra columnfamily defined as
 * use keyspace2;
  CREATE TABLE Tweets (
  user varchar,
  interaction_time timeuuid,
   tweet varchar,
   PRIMARY KEY (user,interaction_time)
  ) WITH CLUSTERING ORDER BY (interaction_time DESC);
 * To manually generate a UUID use:
 * http://www.famkruithof.net/uuid/uuidgen
 */

// Testing :)

public class TweetModel
{
	Cluster cluster;
	public TweetModel(){
		
	}

	public void setCluster(Cluster cluster){
		this.cluster=cluster;
	}
	
	public LinkedList<TweetStore> getTweets() {
		LinkedList<TweetStore> tweetList = new LinkedList<TweetStore>();
		Session session = cluster.connect("blabby");

		PreparedStatement statement = session.prepare("SELECT * FROM tweets");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement);
		if (rs.isExhausted()) {
			System.out.println("No Tweets returned");
		} else {
			for (Row row : rs) {
				TweetStore ts = new TweetStore();
				ts.setTweet(row.getString("tweet"));
				ts.setUser(row.getString("user"));
				tweetList.add(ts);
				System.out.println("Got a Tweet");
			}
		}
		session.close();
		return tweetList;
	}
}
