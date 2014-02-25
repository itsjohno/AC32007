package io.github.itsjohno.blabby.libraries;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.net.URLDecoder;
import java.util.UUID;
import java.util.Date;

import javax.servlet.http.*;

public class Helper 
{
	static final long NUM_100NS_INTERVALS_SINCE_UUID_EPOCH = 0x01b21dd213814000L;
	
	/**
	 * 
	 */
	public static String hashPassword(String password)
	{		
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			password = Helper.byteArrayToString(digest.digest(convertToUTF8(password)));
		}
		catch (NoSuchAlgorithmException e)
		{ }
		
		return password;
	}
	
	/**
	 * convert from internal Java String format -> UTF-8
	 * @url http://fabioangelini.wordpress.com/2011/08/04/converting-java-string-fromto-utf-8/
	 */
    public static byte[] convertToUTF8(String s) 
    {
        String out = null;
        try
        {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
            return out.getBytes("UTF-8");
        }
        catch (java.io.UnsupportedEncodingException e)
        { 
        	return null;
        }
        
    }
	
	/**
	 * Taken from http://stackoverflow.com/questions/4895523/java-string-to-sha1
	 * Allows us to convert a SHA-1 Byte Array into a Hex String
	 */
	public static String byteArrayToString(byte[] b) 
	{
		String result = "";
		
		for (int i=0; i < b.length; i++) 
		{
			result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		
		return result;
	}
	
	/**
	 * Creates a UUID Object based on the current time, and returns it.
	 * @return UUID
	 */
	public static java.util.UUID getTimeUUID()
    {
    	return java.util.UUID.fromString(new com.eaio.uuid.UUID().toString());
    }
	
	public static String[] SplitRequestPath(HttpServletRequest request)
	{
		String args[] = null;
		 
		StringTokenizer st = SplitString(request.getRequestURI());
		args = new String[st.countTokens()];
		
		//Lets assume the number is the last argument
		int argv=0;
		
		while (st.hasMoreTokens ())
		{
			args[argv] = new String();
						
			args[argv] = st.nextToken();
			
			try
			{
				System.out.println("String was "+URLDecoder.decode(args[argv],"UTF-8"));
				args[argv]=URLDecoder.decode(args[argv],"UTF-8");
			}
			catch(Exception et)
			{
				System.out.println("Bad URL Encoding"+args[argv]);
			}
			
			argv++;
		} 

	//so now they'll be in the args array.  
	// argv[0] should be the user directory
	
		System.out.println("Number of Args: " + argv);
		
		return args;
	}
		
	private static StringTokenizer SplitString(String str)
	{
	  		return new StringTokenizer (str,"/");
	}
	
	/**
	 * Converts a passed UUID into a Date object containing the time (related to the UUID)
	 * @param uuid
	 * @return Date object containing timestamp from UUID
	 */
	public static String getStringFromUUID(UUID uuid) 
	{
		DateFormat df = new SimpleDateFormat("h:mma dd/MM/yyyy");
		
	    long time = getTimeFromUUID(uuid);
	    return df.format(new Date(time));
	}
	
	/**
	 * Converts a passed UUID into a Date object containing the time (related to the UUID)
	 * @param uuid
	 * @return Date object containing timestamp from UUID
	 */
	public static Date getTimestampFromUUID(UUID uuid) 
	{
	    long time = getTimeFromUUID(uuid);
	    return new Date(time);
	}
	
	private static long getTimeFromUUID(UUID uuid)
	{
	    return (uuid.timestamp() - NUM_100NS_INTERVALS_SINCE_UUID_EPOCH) / 10000;
	}
}
