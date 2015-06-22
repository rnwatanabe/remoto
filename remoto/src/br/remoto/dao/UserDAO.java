package br.remoto.dao;

//import sun.security.util.Password;
import br.remoto.model.vo.User;


public class UserDAO extends BasicDAO
{
	
    public User loadUser(String cdUser, String password)
	{
    	connect();
    	
    	User user = null;
    	
    	try
    	{
    		String cripto = "";
    		
    		for(int i = 0; i < password.length();i++)
    		{
    			char c = password.charAt(i);
    			
    			int dig = c * c;
    			
    			cripto += dig;
    		}
    		
			// -----------------------------------------
			// Load availables simulations for that user

    		String sql = "select nm_user, email, institution" +
    			  		 " from user" +
    			  		 " where cd_user = '" + cdUser + "'" +
    			  		 " and password = '" + cripto + "'";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	if( rs.next() )
        	{
        		user = new User(cdUser);
        		
        		user.setName( rs.getString(1) );
        		user.setEmail( rs.getString(2) );
        		user.setInstitution( rs.getString(3) );
        	}
        	else
        	{
        		return null;
        	}

        	ConfigurationDAO confDAO = new ConfigurationDAO();
        	
        	user.setConfigurations( confDAO.loadConfigurations( cdUser ) );

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	disconnect();
    	
    	//System.out.println("UserDAO.loadUser");
    	
    	return user;
	}
	
    
    public boolean userExist(String cdUser)
	{
    	connect();
    	
    	boolean ret = false;
    	
    	try
    	{
			// -----------------------------------------
			// Load availables simulations for that user

    		String sql = "select nm_user" +
    			  		 " from user where cd_user = '" + cdUser + "'";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	if( rs.next() )
        	{
        		ret = true;        	
       		}
        	else
        	{
        		ret = false;
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	disconnect();
    	
    	return ret;
	}
	
    
    public boolean createUser(User user)
	{
    	connect();
    	
    	try
    	{
    		String cripto = "";
    		
    		for(int i = 0; i < user.getPassword().length();i++)
    		{
    			char c = user.getPassword().charAt(i);
    			
    			int dig = c * c;
    			
    			cripto += dig;
    		}

    		String sql = "insert into user (nm_user, email, institution," +
    					 " cd_user, password) values" +
    					 "(?, ?, ?, ?, ?)";

    		pst = con.prepareStatement(sql);
        	
        	pst.setString(1, user.getName());
        	pst.setString(2, user.getEmail());
        	pst.setString(3, user.getInstitution());
        	pst.setString(4, user.getCdUser());
        	pst.setString(5, cripto);

        	pst.executeUpdate();

        	pst.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	disconnect();
    	
    	return true;
	}
	
    
    public boolean deleteUser(String cdUser)
	{
    	connect();
    	
    	try
    	{
    		String sql = "delete from user where cd_user = ?";

    		pst = con.prepareStatement(sql);
        	
        	pst.setString(1, cdUser);

        	pst.executeUpdate();

        	pst.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	disconnect();
    	
    	return true;
	}

    
}
