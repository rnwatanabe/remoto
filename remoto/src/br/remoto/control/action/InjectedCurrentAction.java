
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
public class InjectedCurrentAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		InjectedCurrentForm injectedCurrentForm = (InjectedCurrentForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
		}

		
		if( "saveStimulus".equals(task) )
		{
			setConfProperties(conf, injectedCurrentForm);
		}
		
		setFormProperties(injectedCurrentForm, conf, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	
	private void setConfProperties(Configuration conf, InjectedCurrentForm injectedCurrentForm)
	{
		List currents = injectedCurrentForm.getCurrents();
	
		
		for(int i = 0; i < currents.size(); i++)
		{
			Current inj = (Current)currents.get(i);
			
			// Some validations
			if( inj.getAmp() > ReMoto.maxAmpCurrent )
				inj.setAmp( ReMoto.maxAmpCurrent );
			
			if( inj.getImax() > ReMoto.maxAmpCurrent )
				inj.setImax( ReMoto.maxAmpCurrent );
			
			
			ModulatingSignal signal = new ModulatingSignal();
			
			signal.setAmp( inj.getAmp() );
			signal.setCdSignal( inj.getCdSignal() );
			signal.setFreq( inj.getFreq() );
			signal.setTini( inj.getIni() );
			signal.setTfin( inj.getFin() );
			signal.setWidth( inj.getWidth() );
			signal.setDelay( inj.getDelay());
			inj.setSignal( signal );
			
			
		}
		
		
		conf.setListInjectedCurrents( currents );
	}
	
	
	private void setFormProperties(InjectedCurrentForm injectedCurrentForm, Configuration conf, HttpServletRequest req)
	{
		List nuclei = conf.getNuclei( ReMoto.MNs );
		nuclei.addAll( conf.getNuclei( ReMoto.INs ) );

		String cdNucleus = injectedCurrentForm.getCdNucleus();
		
		// Set default value
		if( cdNucleus == null || cdNucleus.equals( "" ) || cdNucleus.equals( ReMoto.DT ) )
		{
			cdNucleus = conf.getCdNucleus();
		}
		else
			req.getSession().setAttribute("cdNucleus", cdNucleus);
		
		injectedCurrentForm.setNuclei( nuclei );
		injectedCurrentForm.setCdNucleus( cdNucleus );
		injectedCurrentForm.setCurrents( conf.getInjectedCurrents(cdNucleus) );
		
		injectedCurrentForm.setCdJoint( conf.getCdJoint() );
	}

}
