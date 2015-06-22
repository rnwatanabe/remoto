
package br.remoto.control.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.ProprioceptorsForm;
import br.remoto.control.form.StimulationForm;
import br.remoto.model.Configuration;
import br.remoto.model.Current;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.Nerve;
import br.remoto.model.ReMoto;


/**
 * @author vitor chaud
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ProprioceptorsAction extends MainAction
{
	
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		ProprioceptorsForm proprioceptorsForm = (ProprioceptorsForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
		}
		
		if( "saveStimulus".equals(task) )
		{
			setConfProperties(conf, proprioceptorsForm);
		}

		setFormProperties(proprioceptorsForm, conf, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, ProprioceptorsForm proprioceptorsForm)
	{
		
		
		
		
	}
	
	
	private void setFormProperties(ProprioceptorsForm proprioceptorsForm, Configuration conf, HttpServletRequest req)
	{
		
	}
	
}
