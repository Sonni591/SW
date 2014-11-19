package datasource;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbConnector {

	private static Connection connection = null; 
	
	public static Connection connectSqLite()
	{  
	     try 
	     {  
	         Class.forName("org.sqlite.JDBC");  
	         connection = DriverManager.getConnection("jdbc:sqlite:SwDB.db");  
	         return connection;
	     } 
	     catch (Exception e) 
	     {  
	         e.printStackTrace();  
	         return null;
	     }
	}
	
	public static void closeSqLiteConnection()
	{
		try
		{
			connection.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
