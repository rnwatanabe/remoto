
package br.remoto.control.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.InjectedCurrentForm;
import br.remoto.model.Configuration;
import br.remoto.model.Current;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.ReMoto;


/**
 * @author vitor
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ScenarioHReflexAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
		}
		
		
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, InjectedCurrentForm injectedCurrentForm)
	{
		
	}
	
	
	private void setFormProperties(InjectedCurrentForm injectedCurrentForm, Configuration conf, HttpServletRequest req)
	{
		
	}

}
