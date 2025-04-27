
package br.remoto.control.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.ConductanceForm;
import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;
import br.remoto.model.vo.NeuronVO;

//Modified in 05-10-11 by L. A. Elias

/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ConductanceAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		ConductanceForm conductanceForm = (ConductanceForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
			return af;
		}

		if( "save".equals(task) )
		{
			setConfProperties(conf, conductanceForm);
		}

		setFormProperties(conductanceForm, conf, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, ConductanceForm conductanceForm)
	{
		conf.setChangedConfiguration( true );
		conf.setNeuronIonicConductances(conductanceForm.getConductances());
	}
	
	
	private void setFormProperties(ConductanceForm conductanceForm, Configuration conf, HttpServletRequest req)
	{
		List nuclei = conf.getNuclei( ReMoto.MNs );
		nuclei.addAll( conf.getNuclei( ReMoto.INs ) );

		String cdNucleus = conductanceForm.getCdNucleus();
		String cdNeuronType = conductanceForm.getType();

		// Do not include descending tracts
		if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) )
		{
			cdNucleus = (String)req.getSession().getAttribute( "cdNucleus" );
			
			if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) )
				cdNucleus = ReMoto.SOL;
		}
		
		req.getSession().setAttribute("cdNucleus", cdNucleus);
		
		List types = new ArrayList();

		types.addAll( conf.getNeuronTypes(cdNucleus, ReMoto.MN) );
		types.addAll( conf.getNeuronTypes(cdNucleus, ReMoto.IN) );
		
		if( cdNeuronType == null && types.size() > 0 )
		{
			cdNeuronType = (String)req.getSession().getAttribute( "cdNeuronType" );
			
			if( cdNeuronType == null )
				cdNeuronType = ((NeuronVO)types.get(0)).getType();
		}

		if( cdNucleus.equals( ReMoto.IN_ext ) || cdNucleus.equals( ReMoto.IN_flex ) )
		{
			if( cdNeuronType != null && (cdNeuronType.indexOf( ReMoto.S ) >= 0 || cdNeuronType.indexOf( ReMoto.FR ) >= 0 || cdNeuronType.indexOf( ReMoto.FF ) >= 0) )
				cdNeuronType = ReMoto.gII;
		}
		else
		{
			if( cdNeuronType != null && (cdNeuronType.indexOf( ReMoto.RC ) >= 0 || cdNeuronType.indexOf( ReMoto.IaIn ) >= 0 || cdNeuronType.indexOf( ReMoto.IbIn ) >= 0 || cdNeuronType.indexOf( ReMoto.gII ) >= 0) )
				cdNeuronType = ReMoto.S;
		}

		req.getSession().setAttribute("cdNeuronType", cdNeuronType);

		conductanceForm.setNuclei( nuclei );
		conductanceForm.setCdNucleus( cdNucleus );
		conductanceForm.setTypes( types );
		conductanceForm.setType( cdNeuronType );
		
		List conductances = new ArrayList();
		
		conductances.add( conf.getMarcovType(cdNucleus, ReMoto.gNaM + "-" + cdNeuronType, ReMoto.excitatory ) );
		conductances.add( conf.getMarcovType(cdNucleus, ReMoto.gNaH + "-" + cdNeuronType, ReMoto.excitatory ) );
		conductances.add( conf.getMarcovType(cdNucleus, ReMoto.gKN + "-" + cdNeuronType, ReMoto.inhibitory ) );
		conductances.add( conf.getMarcovType(cdNucleus, ReMoto.gKQ + "-" + cdNeuronType, ReMoto.inhibitory ) );
		conductances.add( conf.getMarcovType(cdNucleus, ReMoto.gCaP + "-" + cdNeuronType, ReMoto.excitatory ) );
		
		conductanceForm.setConductances( conductances );
	}

}
