
package br.remoto.control.action;


import java.lang.Double;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.SimulationForm;
import br.remoto.model.Configuration;
import br.remoto.model.Simulation;
import br.remoto.model.vo.User;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SimulationAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se n�o houver eventos para a data especificada, retorna um formul�rio em branco.
	 */
	
	
	
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		SimulationForm simulationForm = (SimulationForm) form;
		String task = (String)req.getParameter("task");
		
		Configuration conf = getConfiguration(req);
		
		
		if( conf == null )
		{
			req.setAttribute("msg", "No simulation configuration found.");
			af = map.findForward( "noConfiguration" );
			return af;
		}
		
		if( "new".equals(task) )
		{
String tFin = (String)req.getParameter("duration");
			
			if( tFin != null ) 
				conf.setTFin( Double.parseDouble(tFin) );
			
			conf.getResult().getCoordenate().setXfin( conf.getTFin() );
			
			if( conf.isStoreResults() == true )
			{
				User user = getUser(req);

				// Save configuration in DB as well
				saveUserConfiguration(req, conf, user);
			}

			createSimulation(req, conf);
			
			af = map.findForward( "wait" );
		}
		else
		{
			Simulation sim = getSimulation(req, conf.getId());
			
			if( sim == null )
			{
				req.setAttribute("msg", "No simulation ID found.");
				af = map.findForward( "noConfiguration" );
				return af;
			}
			else if( sim.getStatus() == Simulation.SIM_RUNNING )
			{
				if( "cancel".equals(task) )
				{
					sim.finishSimulation();
					
					af = map.findForward( "finish" );
				}
				else
				{
					simulationForm.setPercentage( String.valueOf(sim.getPercentageInt()));
					simulationForm.setStart( sim.getStart() );
					
					af = map.findForward( "wait" );
				}
			}
			else if( sim.getStatus() == Simulation.SIM_FINISH )
			{
				req.getSession().setAttribute("start", sim.getStart());
				req.getSession().setAttribute("finish", sim.getFinish());
				req.getSession().setAttribute("elapsedTime", sim.getElapsedTime());
				req.getSession().setAttribute("duration",  String.valueOf( sim.getConfiguration().getTFin() ) );

				af = map.findForward( "finish" );
			}
		}

		return af;
	}

}
		
			