
package br.remoto.control.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.dao.ConfigurationDAO;
import br.remoto.dao.UserDAO;
import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;
import br.remoto.model.Simulation;
import br.remoto.model.vo.User;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MainAction extends Action
{
	
	static protected User userGuest;
	
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	
	// Modified by Vitor - 16/04/11
	
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		Configuration conf = getConfiguration(req);
		
		/*
		try {
			String computerName = InetAddress.getLocalHost().getHostName();
			System.out.println("name: " + computerName);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		System.out.println("B");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
			System.out.println("A");
		}
		else
		{
			
			boolean simRunning = isThereSimulationRunning(req);
			
			System.out.println("Changing server: " + simRunning);
			
			if( simRunning == true )
				af = map.findForward( "running" );
			else
				af = map.findForward( "sucess" );
			
		}
		  
		return af;
	}
	
	
	public Configuration getConfiguration(HttpServletRequest req)
	{
		Configuration conf = (Configuration)req.getSession().getAttribute("configuration");
		
		if( conf == null )
		{
			ServletContext context = servlet.getServletContext();
			ReMoto.path = context.getRealPath("/"); 

			return loadConfiguration(req, -1);
		}
		
		return conf;
	}
	
	
	public User getUser(HttpServletRequest req)
	{
		User user = (User)req.getSession().getAttribute("user");
		
		if( user == null )
		{
			user = userGuest;
			
			if( user == null )
			{
				UserDAO userDAO = new UserDAO();
				user = userDAO.loadUser(ReMoto.cdGuestUser, ReMoto.cdGuestUser);

				userGuest = user;
			}
		}
		
		req.getSession().setAttribute("user", user);

		return user;
	}
	
	
	public void createSimulation(HttpServletRequest req, Configuration conf)
	{
		cleanSimulation(req);
		
		String cdSimulation = getCdSimulation(req, conf.getId());
		
		Simulation sim;
		
		// Create a new simulation if configurations have been changed
		if( conf.isChangedConfiguration() == true && conf.isKeepProperties() == false )
		{
			sim = new Simulation(conf, cdSimulation);
		}
		else
		{
			sim = (Simulation)ReMoto.simulations.get( cdSimulation );

			if( sim == null )
				sim = new Simulation(conf, cdSimulation);
		}

		new Thread(sim).start();
	}
	
	
	public Simulation getSimulation(HttpServletRequest req, int idConfiguration)
	{
		Simulation sim = null;
		
		String cdSimulation = getCdSimulation(req, idConfiguration);
		
		sim = (Simulation)ReMoto.simulations.get( cdSimulation );
		
		return sim;
		
	}


	public String getCdSimulation(HttpServletRequest req, int idConfiguration)
	{
		User user = getUser(req);
		
		if( user == null )
			return null;
		
		String cdUser = "";
		
		if( !user.getCdUser().equals( ReMoto.cdGuestUser ) )
			cdUser = user.getCdUser();
		else
			cdUser = req.getSession().getId();
		
		return cdUser + "_" + idConfiguration;
	}

	
	public void cleanSimulation(HttpServletRequest req)
	{
		User user = getUser(req);
		
		if( user == null )
			return;
		
		Iterator it = ReMoto.simulations.values().iterator();
		
		while( it.hasNext() )
		{
			Simulation sim = (Simulation)it.next();
			
			if( sim.getStatus() != Simulation.SIM_RUNNING )
			{
				Date dateSim = sim.getDateFinish();
				Date dateNow = new Date();
				
				
				// TRECHO PROVISORIAMENTE COMENTADO
				/*
				// Remove old simulations - time in milliseconds
				if( user.getCdUser().equals( ReMoto.cdGuestUser ) )
				{
					// Keep during 30 minutes
					if( dateNow.getTime() > dateSim.getTime() + 1000 * 60 * 30 )
						it.remove();
				}
				else
				{
					// Keep during 12 hours
					if( dateNow.getTime() > dateSim.getTime() + 1000 * 60 * 60 * 12 )
						it.remove();
				}
				*/
				
			}
		}
	}


	public Simulation recoverStoredSimulation(HttpServletRequest req, int idConfiguration)
	{
		if( idConfiguration == ReMoto.idGuestUser )
			return null;
		
		FileInputStream fis = null;
		ObjectInputStream in = null;
		Simulation sim = null;
		
		try
		{
			fis = new FileInputStream( ReMoto.path + "store/" + idConfiguration + ".sim" );
			in = new ObjectInputStream( fis );
			sim = (Simulation)in.readObject();
			in.close();
			
			String oldCdSimulation = sim.getCdSimulation();
			String newCdSimulation = getCdSimulation(req, idConfiguration);
			
			if( !oldCdSimulation.equals( newCdSimulation ) )
			{
				ReMoto.simulations.remove( oldCdSimulation );
			}

			sim.setCdSimulation( newCdSimulation );
			ReMoto.simulations.put( newCdSimulation, sim );
		}
		catch(FileNotFoundException e)
		{
			// Simulation was not stored
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		} 
		
		return sim;
	}


	public Configuration loadConfiguration(HttpServletRequest req, int idConfiguration)
	{
		User user = getUser(req);
		
		if( idConfiguration < 0 )
		{
			idConfiguration = user.getIdConfigurationDefault();
		}

		Simulation sim = getSimulation(req, idConfiguration);
		Configuration conf = null;
		
		if( sim != null )
			conf = sim.getConfiguration();
			
		// Load configuration properties
		if( conf == null )
		{
			ConfigurationDAO confDAO = new ConfigurationDAO();
			
			conf = confDAO.getConfiguration( idConfiguration );
		}
		
		req.getSession().setAttribute("configuration", conf);

		return conf;
	}


	public Configuration reloadConfiguration(HttpServletRequest req, int idConfiguration)
	{
		// Load configuration properties
		ConfigurationDAO confDAO = new ConfigurationDAO();
		
		Configuration conf = confDAO.getConfiguration( idConfiguration );
		
		req.getSession().setAttribute("configuration", conf);

		return conf;
	}

	
	public boolean saveUserConfiguration(HttpServletRequest req, Configuration conf, User user)
	{
		if( conf == null || user == null )
		{
			return false;
		}
		
		req.getSession().setAttribute("idSimulation", Integer.toString(conf.getId()));

		// Save configuration properties
		ConfigurationDAO simDAO = new ConfigurationDAO();
		
		return simDAO.saveConfiguration(conf, user);
	}

	
	// Modified by Vitor - 16/04/11
	
	public boolean isThereSimulationRunning(HttpServletRequest req)
	{
		int numSims = 0;
		Iterator it = ReMoto.simulations.values().iterator();
		
		while( it.hasNext() )
		{
			Simulation sim = (Simulation)it.next();
			
			if( sim.getStatus() == Simulation.SIM_RUNNING )
			{
				numSims = numSims + 1;
			}
			
		}
		
		System.out.println("numSims: " + numSims);
		
		if(numSims >= 4) return true;
		
		return false;
	}
	
}
