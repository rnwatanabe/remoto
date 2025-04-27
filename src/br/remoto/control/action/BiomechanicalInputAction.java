
package br.remoto.control.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import br.remoto.control.form.BiomechanicalInputForm;
import br.remoto.control.form.InjectedCurrentForm;
import br.remoto.control.form.StimulationForm;
import br.remoto.model.BiomechanicalInput;
import br.remoto.model.Configuration;
import br.remoto.model.Current;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.Nerve;
import br.remoto.model.ReMoto;


public class BiomechanicalInputAction extends MainAction
{
	
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		BiomechanicalInputForm biomechanicalInputForm = (BiomechanicalInputForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
		}

		
		if( "saveStimulus".equals(task) )
		{
			setConfProperties(conf, biomechanicalInputForm);
		}
		
		setFormProperties(biomechanicalInputForm, conf, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, BiomechanicalInputForm biomechanicalInputForm)
	{
		List biomechanicalInputs = biomechanicalInputForm.getBiomechanicalInputs();
	
		for(int i = 0; i < biomechanicalInputs.size(); i++)
		{
			BiomechanicalInput inj = (BiomechanicalInput)biomechanicalInputs.get(i);
			
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
		
		
		conf.setListBiomechanicalInputs( biomechanicalInputs );
		
		conf.setJointVelocity(biomechanicalInputForm.getStretchVelocity());
		conf.setJointAngle(biomechanicalInputForm.getJointAngle());
		conf.setKneeAngle(biomechanicalInputForm.getKneeAngle());
		
	}
	
	private void setFormProperties(BiomechanicalInputForm biomechanicalInputForm, Configuration conf, HttpServletRequest req)
	{
		List nuclei = conf.getNuclei( ReMoto.MNs );
		nuclei.addAll( conf.getNuclei( ReMoto.INs ) );

		String cdNucleus = biomechanicalInputForm.getCdNucleus();
		
		// Set default value
		if( cdNucleus == null || cdNucleus.equals( "" ) || cdNucleus.equals( ReMoto.DT ) )
		{
			cdNucleus = conf.getCdNucleus();
		}
		else
			req.getSession().setAttribute("cdNucleus", cdNucleus);
		
		biomechanicalInputForm.setNuclei( nuclei );
		biomechanicalInputForm.setCdNucleus( cdNucleus );
		biomechanicalInputForm.setBiomechanicalInputs( conf.getBiomechanicalInputs(cdNucleus) );
		
		biomechanicalInputForm.setCdJoint( conf.getCdJoint() );
		
		biomechanicalInputForm.setStretchVelocity(conf.getJointVelocity());
		biomechanicalInputForm.setJointAngle(conf.getJointAngle());
		biomechanicalInputForm.setKneeAngle(conf.getKneeAngle());
	}
	
}
