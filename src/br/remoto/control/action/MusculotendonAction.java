
package br.remoto.control.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import br.remoto.control.form.MusculotendonForm;
import br.remoto.control.form.StimulationForm;
import br.remoto.model.Configuration;
import br.remoto.model.Current;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.Nerve;
import br.remoto.model.ReMoto;
import br.remoto.model.vo.MotorUnitVO;
import br.remoto.model.vo.MuscleVO;


public class MusculotendonAction extends MainAction
{
	
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		MusculotendonForm musculotendonForm = (MusculotendonForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
		}
		
		if( "save".equals(task) )
		{
			setConfProperties(conf, musculotendonForm);
		}

		setFormProperties(musculotendonForm, conf, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, MusculotendonForm musculotendonForm)
	{
		
		conf.setTwitchParameters( musculotendonForm.getMotorunits() );
		conf.setMuDistribution( musculotendonForm.getDistribution() );
		
		MuscleVO vo = new MuscleVO();
		
		vo.setCdMuscle(musculotendonForm.getCdNucleus());
		
		vo.setOptimalLength(musculotendonForm.getOptimalLength());
		vo.setMaximumMuscleForce(musculotendonForm.getMaximumMuscleForce());
		vo.setMuscleMass(musculotendonForm.getMass());
		vo.setViscosityCoeficient(musculotendonForm.getViscosityCoeficient());
		vo.setElasticCoeficientOfParallelElement(musculotendonForm.getElasticCoeficientParallelElement());
		vo.setC_T(musculotendonForm.getC_T());
		vo.setK_T(musculotendonForm.getK_T());
		vo.setLR_T(musculotendonForm.getLr_T());
		vo.setPennationAngle(musculotendonForm.getPennationAngle());
		vo.setSlackTendonLength(musculotendonForm.getSlackTendonLength());
		
		conf.setMuscle(musculotendonForm.getCdNucleus(), "hill", vo);
		
		
		//conf.setCdMuscleModel(musculotendonForm.getCdMuscleModel());
		//MotorUnitVO aux = (MotorUnitVO) musculotendonForm.getMotorunits().get(0);
		
		//System.out.println(aux.getTwitchTension1());
		//System.out.println("aux.getTwitchTension1Raikova(): " + aux.getTwitchTension1Raikova());
	}
	
	
	private void setFormProperties(MusculotendonForm musculotendonForm, Configuration conf, HttpServletRequest req)
	{
		List nuclei = conf.getNuclei( ReMoto.MNs );
		String cdNucleus = musculotendonForm.getCdNucleus();
		//String cdMuscleModel = musculotendonForm.getCdMuscleModel();
		
		// Set default value
		if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) || cdNucleus.equals( ReMoto.IN_ext ) || cdNucleus.equals( ReMoto.IN_flex ) )
		{
			cdNucleus = (String)req.getSession().getAttribute( "cdNucleus" );
			
			if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) || cdNucleus.equals( ReMoto.IN_ext ) || cdNucleus.equals( ReMoto.IN_flex ) )
				cdNucleus = ReMoto.SOL;
		}
		else
			req.getSession().setAttribute("cdNucleus", cdNucleus);
		
		
		
		MuscleVO vo = conf.getMuscle(cdNucleus, "hill");
		
		musculotendonForm.setOptimalLength(vo.getOptimalLength());
		musculotendonForm.setMaximumMuscleForce(vo.getMaximumMuscleForce());
		musculotendonForm.setMass(vo.getMuscleMass());
		musculotendonForm.setViscosityCoeficient(vo.getViscosityCoeficient());
		musculotendonForm.setElasticCoeficientParallelElement(vo.getElasticCoeficientOfParallelElement());
		musculotendonForm.setC_T(vo.getC_T());
		musculotendonForm.setK_T(vo.getK_T());
		musculotendonForm.setLr_T(vo.getLR_T());
		
		musculotendonForm.setPennationAngle(vo.getPennationAngle());
		musculotendonForm.setSlackTendonLength(vo.getSlackTendonLength());
		
		
		musculotendonForm.setCdMuscleModel(conf.getCdMuscleModel());
		musculotendonForm.setCdJoint( conf.getCdJoint() );
		musculotendonForm.setNuclei( nuclei );
		musculotendonForm.setCdNucleus( cdNucleus );
		musculotendonForm.setMotorunits( conf.getMotorUnitTypes(cdNucleus) );
		musculotendonForm.setDistribution( conf.getMuDistribution() );
	}
	
}
