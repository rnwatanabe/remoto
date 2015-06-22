
package br.remoto.control.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.MuscleSpindleForm;
import br.remoto.control.form.StimulationForm;
import br.remoto.model.Configuration;
import br.remoto.model.Current;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.Nerve;
import br.remoto.model.ReMoto;


public class MuscleSpindleAction extends MainAction
{
	
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		MuscleSpindleForm muscleSpindleForm = (MuscleSpindleForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
		}
		
		if( "saveStimulus".equals(task) )
		{
			setConfProperties(conf, muscleSpindleForm);
		}

		setFormProperties(muscleSpindleForm, conf, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, MuscleSpindleForm muscleSpindleForm)
	{
		
		//if(Math.round(muscleSpindleForm.getPrimaryBag1Gain()) == 0)	muscleSpindleForm.setPrimaryBag1Gain(20000);
		//if(Math.round(muscleSpindleForm.getPrimaryBag2AndChainGain()) == 0)	muscleSpindleForm.setPrimaryBag2AndChainGain(10000);
		
		conf.setCdSpindleModel(muscleSpindleForm.getCdType());
		
		conf.setGammaDynamic(muscleSpindleForm.getGammaDynamic());
		conf.setGammaStatic(muscleSpindleForm.getGammaStatic());
		
		conf.setPrimaryBag1Gain(muscleSpindleForm.getPrimaryBag1Gain());
		conf.setPrimaryBag2AndChainGain(muscleSpindleForm.getPrimaryBag2AndChainGain());
		conf.setSecondaryBag2AndChainGain(muscleSpindleForm.getSecondaryBag2AndChainGain());
		
		
	}
	
	
	private void setFormProperties(MuscleSpindleForm muscleSpindleForm, Configuration conf, HttpServletRequest req)
	{
		muscleSpindleForm.setCdType(conf.getCdSpindleModel());
		
		muscleSpindleForm.setGammaDynamic(conf.getGammaDynamic());
		muscleSpindleForm.setGammaStatic(conf.getGammaStatic());
		
		muscleSpindleForm.setPrimaryBag1Gain(conf.getPrimaryBag1Gain());
		muscleSpindleForm.setPrimaryBag2AndChainGain(conf.getPrimaryBag2AndChainGain());
		muscleSpindleForm.setSecondaryBag2AndChainGain(conf.getSecondaryBag2AndChainGain());
	}
	
}
