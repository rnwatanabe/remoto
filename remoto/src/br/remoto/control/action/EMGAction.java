
package br.remoto.control.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import br.remoto.control.form.BiomechanicalElementsForm;
import br.remoto.control.form.EMGForm;
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
public class EMGAction extends MainAction
{
	
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		EMGForm emgForm = (EMGForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
		}
		
		if( "saveStimulus".equals(task) )
		{
			setConfProperties(conf, emgForm);
		}

		setFormProperties(emgForm, conf, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, EMGForm emgForm)
	{
		conf.setEMGParameters( emgForm.getMotorunits() );
		conf.setMuDistribution( emgForm.getDistribution() );
		conf.setCdEMGModel(emgForm.getCdEMGModel());
	}
	
	
	private void setFormProperties(EMGForm emgForm, Configuration conf, HttpServletRequest req)
	{
		List nuclei = conf.getNuclei( ReMoto.MNs );
		
		//List nuclei = null;
		
		//nuclei.add(ReMoto.ALL_RBI_flex);
		
		String cdNucleus = emgForm.getCdNucleus();
		
		// Set default value
		if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) || cdNucleus.equals( ReMoto.IN_ext ) || cdNucleus.equals( ReMoto.IN_flex ) )
		{
			cdNucleus = (String)req.getSession().getAttribute( "cdNucleus" );
			
			if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) || cdNucleus.equals( ReMoto.IN_ext ) || cdNucleus.equals( ReMoto.IN_flex ) )
				cdNucleus = ReMoto.SOL;
		}
		else
			req.getSession().setAttribute("cdNucleus", cdNucleus);
		
		emgForm.setNuclei( nuclei );
		emgForm.setCdNucleus( cdNucleus );
		emgForm.setMotorunits( conf.getMotorUnitTypes(cdNucleus) );
		emgForm.setDistribution( conf.getMuDistribution() );
		emgForm.setCdEMGModel( conf.getCdJoint() );
	}
	
}
