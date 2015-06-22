
package br.remoto.control.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.StartForm;
import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;
import br.remoto.model.Simulation;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StartAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		StartForm startForm = (StartForm) form;
		Configuration conf = getConfiguration(req);

		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
			return af;
		}
		
		Simulation sim = getSimulation(req, conf.getId());
		
		if( sim != null && sim.getStatus() == Simulation.SIM_RUNNING )
		{
			af = map.findForward( "running" );
			return af;
		}
		
		else if( (sim != null) && (sim.getStatus() == Simulation.SIM_FINISH || sim.getStatus() == Simulation.SIM_CANCEL))
		{
			try 
			{
				if (sim.getStatus() == Simulation.SIM_FINISH)
					res.sendRedirect("/remoto/jsp/finish.jsp?status=finished");
				else if (sim.getStatus() == Simulation.SIM_CANCEL)
					res.sendRedirect("/remoto/jsp/finish.jsp?status=canceled");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			return null;
		}
		
		startForm.setDuration( conf.getTFin() );
		
		//testing
		
		if( conf.getStep() < ReMoto.minStep ){ 
			conf.setStep( ReMoto.minStep );
			startForm.setStep( ReMoto.minStep  );
		}
		else startForm.setStep( conf.getStep() );
		
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
}
