
package br.remoto.control.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.MarkovForm;
import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;

/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

//Modified in 05-10-11 by L. A. Elias

public class MarkovAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		MarkovForm markovForm = (MarkovForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		String option = (String)req.getParameter("option");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
			return af;
		}

		if( "save".equals(task) )
		{
			// Save all properties
			setConfProperties(conf, markovForm);
		}

		setFormProperties(markovForm, conf, req, option);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, MarkovForm markovForm)
	{
		conf.setChangedConfiguration( true );
		conf.setListMarkovTypes( markovForm.getConductances() );
	}
	
	
	private void setFormProperties(MarkovForm markovForm, Configuration conf, HttpServletRequest req, String option)
	{
		List nuclei = conf.getNuclei( ReMoto.MNs );
		nuclei.addAll( conf.getNuclei( ReMoto.INs ) );

		String cdNucleus = markovForm.getCdNucleus();
		String cdNeuron = (String)req.getParameter("cdNeuron");

		// Do not include descending tracts
		if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) )
		{
			cdNucleus = (String)req.getSession().getAttribute( "cdNucleus" );
			
			if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) )
				cdNucleus = ReMoto.SOL;
		}
		else
			req.getSession().setAttribute("cdNucleus", cdNucleus);
		
		markovForm.setNuclei( nuclei );
		markovForm.setCdNucleus( cdNucleus );
		markovForm.setCdNeuron( cdNeuron );
		
		// Get conductances according to the selection of neuron
		if( option.equals(ReMoto.synaptic) )
		{
			markovForm.setConductances( conf.getSynapticDynamics( cdNucleus ) );
		}
		else
		{
			List conductances = new ArrayList();
			
			conductances.add( conf.getMarcovType(cdNucleus, ReMoto.gNaM + "-" + cdNeuron, ReMoto.excitatory) );
			conductances.add( conf.getMarcovType(cdNucleus, ReMoto.gNaH + "-" + cdNeuron, ReMoto.excitatory) );
			conductances.add( conf.getMarcovType(cdNucleus, ReMoto.gKN + "-" + cdNeuron, ReMoto.inhibitory) );
			conductances.add( conf.getMarcovType(cdNucleus, ReMoto.gKQ + "-" + cdNeuron, ReMoto.inhibitory) );
			conductances.add( conf.getMarcovType(cdNucleus, ReMoto.gCaP + "-" + cdNeuron, ReMoto.excitatory) );
						
			markovForm.setConductances( conductances );
		}
		
	}

}
