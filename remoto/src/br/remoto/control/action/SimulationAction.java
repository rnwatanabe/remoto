
package br.remoto.control.action;

import java.io.IOException;
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
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	
	boolean test = true;
	
	// Modified by Vitor 04/20/11
	
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		
		ActionForward af = null;
		SimulationForm simulationForm = (SimulationForm) form;
		String task = (String)req.getParameter("task");
		
		Configuration conf = getConfiguration(req);
		
		//System.out.println("task: " + task);
		
		if( conf == null )
		{
			req.setAttribute("msg", "No simulation configuration found.");
			af = map.findForward( "noConfiguration" );
			return af;
		}
		
		
		if( "new".equals(task) )
		{
			af = newSim(map, simulationForm, req, conf);
		}
		else if ("simulating".equals(task) || "cancel".equals(task))
		{
			af = simulatingSim(map, simulationForm, req, res, conf);
		}
			
		return af;
		
	}
	
	
	
	private ActionForward newSim(ActionMapping map, SimulationForm simulationForm, HttpServletRequest req, Configuration conf){

		String step = (String)req.getParameter("step");
		conf.setStep( Double.parseDouble(step) );
		
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
		
		return map.findForward( "wait" );
	}
	
	
	private ActionForward simulatingSim(ActionMapping map, SimulationForm simulationForm, HttpServletRequest req, HttpServletResponse res, Configuration conf){
		
		String task = (String)req.getParameter("task");
		
		Simulation sim = getSimulation(req, conf.getId());
		
		((SimulationForm) simulationForm).setCdSimulation( sim.getCdSimulation() );
		
		simulationForm.setStatus( "running" );
		
		if( sim == null )
		{
			req.setAttribute("msg", "No simulation ID found.");
			return map.findForward( "noConfiguration" );
		}
		else if( sim.getStatus() == Simulation.SIM_RUNNING )
		{
			
			simulationForm.setStart( sim.getStart() );
			
		}
		
		
		if( "cancel".equals(task) )
		{
			sim.finishSimulation();
			
			simulationForm.setStart( sim.getFinish() );
			
			req.getSession().setAttribute("start", sim.getStart());
			req.getSession().setAttribute("finish", sim.getFinish());
			req.getSession().setAttribute("duration",  String.valueOf( sim.getConfiguration().getTFin() ) );
			req.getSession().setAttribute("step",  String.valueOf( sim.getConfiguration().getStep() ) );
			try {
				res.sendRedirect("/remoto/jsp/finish.jsp?status=canceled");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		return map.findForward( "wait" );
	}
	
	
}
