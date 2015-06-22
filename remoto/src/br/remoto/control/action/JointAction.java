
package br.remoto.control.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import br.remoto.control.form.JointForm;
import br.remoto.control.form.StimulationForm;
import br.remoto.model.Configuration;
import br.remoto.model.Current;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.Nerve;
import br.remoto.model.ReMoto;

public class JointAction extends MainAction
{
	
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		JointForm jointForm = (JointForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
		}
		
		if( "saveStimulus".equals(task) )
		{
			setConfProperties(conf, jointForm);
		}

		setFormProperties(jointForm, conf, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, JointForm jointForm)
	{
		
		//conf.setCdJoint(jointForm.getCdJoint());
		
		conf.setJointAngle(jointForm.getJointAngle());
		conf.setKneeAngle(jointForm.getKneeAngle());
		
	}
	
	
	private void setFormProperties(JointForm jointForm, Configuration conf, HttpServletRequest req)
	{
		jointForm.setCdJoint( conf.getCdJoint() );
		jointForm.setCdJointModel( conf.getCdJointModel() );
		
		System.out.println("conf.getJointAngle(): " + conf.getJointAngle());
		
		//jointForm.setJointAngle(conf.getJointAngle());
		//jointForm.setKneeAngle(conf.getKneeAngle());
		
	}
	
}
