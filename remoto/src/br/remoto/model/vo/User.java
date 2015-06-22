package br.remoto.model.vo;

import java.io.Serializable;
import java.util.List;


public class User implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private String cdUser;
	private String name;
	private String email;
	private String institution;
	private String password;
	
	private List configurations;
	
	
	public User (String cdUser)
	{
		setCdUser(cdUser);
	}
	
	
	public GeneralVO getGeneralConfiguration( int idConfiguration )
	{
		GeneralVO gen = new GeneralVO();
		
		if( configurations != null )
		{
			for(int i = 0; i < configurations.size(); i++)
			{
				gen = (GeneralVO)configurations.get(i);
				
				if( idConfiguration == gen.getId() )
				{
					return gen;
				}
			}
		}
		
		return gen;
	}
	
	
	public GeneralVO getConfigurationDefault()
	{
		GeneralVO sim = null;
		
		if( configurations != null && configurations.size() > 0 )
		{
			sim = (GeneralVO)configurations.get(0);
		}
		
		return sim;
	}
	
	
	public void deleteConfiguration( int idConfiguration )
	{
		GeneralVO sim = new GeneralVO();
		
		if( configurations != null )
		{
			for(int i = 0; i < configurations.size(); i++)
			{
				sim = (GeneralVO)configurations.get(i);
				
				if( idConfiguration == sim.getId() )
				{
					configurations.remove(i);
					
					return;
				}
			}
		}
	}
	
	
	public int getIdConfigurationDefault()
	{
		int idConfiguration = -1;
		
		if( configurations != null && configurations.size() > 0 )
		{
			GeneralVO general = (GeneralVO)configurations.get(0);
			
			idConfiguration = general.getId();
		}
		
		return idConfiguration;
	}
	
	
	public void addConfiguration(GeneralVO conf)
	{
		configurations.add( conf );
	}
	
	
	public boolean isNewName( String name, int id )
	{
		if( name == null || name.equals("") )
			return false;
		
		GeneralVO general = new GeneralVO();
		
		if( configurations != null )
		{
			for(int i = 0; i < configurations.size(); i++)
			{
				general = (GeneralVO)configurations.get(i);
				
				if( name.equals( general.getName() ) && id != general.getId() )
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	public String getCdUser() {
		return cdUser;
	}
	public void setCdUser(String cdUser) {
		this.cdUser = cdUser;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List getConfigurations() {
		return configurations;
	}
	public void setConfigurations(List configurations) {
		this.configurations = configurations;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
