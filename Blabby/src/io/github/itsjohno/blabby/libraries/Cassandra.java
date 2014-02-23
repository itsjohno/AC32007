package io.github.itsjohno.blabby.libraries;

import com.datastax.driver.core.*;

import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**********************************************************
 *Hosts are 
 * 192.168.2.10  Seed for Vagrant hosts 
 */

public final class Cassandra
{
	//================================================================================
    // Constructors
    //================================================================================
	public Cassandra()
	{
		
	}
	
	//================================================================================
    // Properties
    //================================================================================
	
	private static String keyspace = null;
	private static Cluster cluster = null;
	private static Session session = null;
	static String Host = "127.0.0.1";
	
	//================================================================================
    // Accessors
    //================================================================================
	
	public static void createCluster()
	{
		if (cluster == null)
		{
			cluster = setupCluster();
		}
	}
	
	public static void createSession(String newKeyspace)
	{
		createCluster();
		keyspace = newKeyspace;
		session = cluster.connect(keyspace);
	}
	
	public static void closeCluster()
	{
		if (cluster != null)
			cluster.close();
	}
	
	public static void closeSession()
	{
		if (session != null)
			session.close();
	}
	
	public static String getKeyspace()
	{
		return keyspace;
	}
	
	public static Session getSession()
	{
		return session;
	}
	
	//================================================================================
    // Connection Methods
    //================================================================================
	
	public static String[] getHosts(Cluster cluster)
	{
		if (cluster==null)
		{
			System.out.println("Creating cluster connection");
			cluster = Cluster.builder().addContactPoint(Host).build();
		}
  
		System.out.println("Cluster Name " + cluster.getClusterName());
		Metadata mdata = cluster.getMetadata();
		Set<Host> hosts = mdata.getAllHosts();
		String sHosts[] = new String[hosts.size()];
			
		Iterator<Host> it =hosts.iterator();
		int i=0;
   
		while (it.hasNext()) 
		{
			Host ch=it.next();
			sHosts[i]=(String)ch.getAddress().toString();
     
			System.out.println("Hosts"+ch.getAddress().toString());
			i++;
		}
		
		return sHosts;
	}
	
	public static Cluster setupCluster()	
	{
		System.out.println("getCluster");
		cluster = Cluster.builder().addContactPoint(Host).build();
		getHosts(cluster);
		return cluster;
	}	

	//================================================================================
    // Query Methods
    //================================================================================
	
	public static BoundStatement createBoundStatement(String query)
	{
		PreparedStatement statement = session.prepare(query);
		BoundStatement boundStatement = new BoundStatement(statement);
		
		return boundStatement;
	}
}