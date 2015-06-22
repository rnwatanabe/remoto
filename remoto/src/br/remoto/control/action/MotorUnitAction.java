
package br.remoto.control.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.MotorUnitForm;
import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;
import br.remoto.model.vo.MotorUnitVO;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MotorUnitAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		MotorUnitForm muForm = (MotorUnitForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
			return af;
		}

		if( "save".equals(task) )
		{
			setSimProperties(conf, muForm);
		}

		setFormProperties(muForm, conf, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setSimProperties(Configuration conf, MotorUnitForm muForm)
	{
		conf.setListMotorUnitTypes( muForm.getMotorunits() );
		conf.setMuDistribution( muForm.getDistribution() );
		
		conf.setCdMuscleModel(muForm.getCdMuscleModel());
		
	}
	
	
	private void setFormProperties(MotorUnitForm muForm, Configuration conf, HttpServletRequest req)
	{
		List nuclei = conf.getNuclei( ReMoto.MNs );
		String cdNucleus = muForm.getCdNucleus();
		String cdMuscleModel = muForm.getCdMuscleModel();
		
		// Set default value
		if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) || cdNucleus.equals( ReMoto.IN_ext ) || cdNucleus.equals( ReMoto.IN_flex ) )
		{
			cdNucleus = (String)req.getSession().getAttribute( "cdNucleus" );
			
			if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) || cdNucleus.equals( ReMoto.IN_ext ) || cdNucleus.equals( ReMoto.IN_flex ) )
				cdNucleus = ReMoto.SOL;
		}
		else
			req.getSession().setAttribute("cdNucleus", cdNucleus);
		
		
		
		muForm.setNuclei( nuclei );
		muForm.setCdNucleus( cdNucleus );
		muForm.setMotorunits( conf.getMotorUnitTypes(cdNucleus) );
		muForm.setDistribution( conf.getMuDistribution() );
	}

}
