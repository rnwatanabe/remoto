package br.remoto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.remoto.model.ReMoto;


public class BasicDAO 
{
	protected static final String jdbcDriver = "org.hsqldb.jdbcDriver";
	protected static final String jdbcUser = "sa";
	protected static final String jdbcPassword = "";
	protected static String jdbcUrl;
	
	protected static Connection con;
	protected static boolean serverStarted = false;
	protected ResultSet rs;
	protected Statement st;
	protected PreparedStatement pst; 	
	
	
	public BasicDAO()
	{
	}
	

	public void connect()
	{
		try 
		{  System.out.println("con"+con);
			if( con == null )
			{
				Class.forName( jdbcDriver );
				System.out.println("remotopath:"+ReMoto.path);
				//jdbcUrl = "jdbc:hsqldb:file:" + ReMoto.path + "db/" + "remoto;shutdown=true";
				
				// Server start:
				// java -cp C:/java/Tomcat/webapps/remoto/WEB-INF/lib/hsqldb.jar org.hsqldb.Server -database.0 file:C:/java/Tomcat/webapps/remoto/db/remoto -dbname.0 remoto				
				// Can be stored on HKEY_LOCAL_MACHINESOFTWARE\Microsoft\Windows\CurrentVersion\Run
				
				jdbcUrl = "jdbc:hsqldb:hsql://143.107.162.166/remoto";
				//jdbcUrl = "jdbc:hsqldb:hsql://localhost/remoto";
				
				System.out.println("CONNECTING TO " + jdbcUrl);
				
				//jdbcUrl = "jdbc:hsqldb:hsql://143.107.162.139/remoto";
				
				con = DriverManager.getConnection( jdbcUrl, jdbcUser, jdbcPassword ); 
				
				//System.out.println("BasicDAO.connect");
			}
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println( e.getMessage() );
			System.out.println("ExceptionClass");
		} 
		catch (SQLException e) 
		{
			System.out.println( e.getMessage() ); 
			System.out.println( "Error SQL!" ); 
		}
		catch(Exception e) 
		{ 
			System.out.println( e.getMessage() ); 
			System.out.println("Exception E");
		} 
	}


	public void close()
	{
	}

	
	public void disconnect()
	{
		try 
		{
			if( rs != null ) rs.close();
			if( st != null ) st.close(); 
			if( pst != null ) pst.close(); 
			
			rs = null;
			st = null;
			pst = null;
			
			if( con != null ) con.close(); 
			
			con = null;
			//System.out.println("BasicDAO.disconnect");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
