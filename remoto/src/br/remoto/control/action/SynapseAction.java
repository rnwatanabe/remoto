
package br.remoto.control.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.SynapseForm;
import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;
import br.remoto.model.vo.NeuronVO;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SynapseAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		SynapseForm synapseForm = (SynapseForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
			return af;
		}

		if( "save".equals(task) )
		{
			// Save all properties
			setConfProperties(conf, synapseForm);
		}

		setFormProperties(synapseForm, conf, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, SynapseForm synapseForm)
	{
		conf.setChangedConfiguration( true );
		conf.setSynapseTypes( synapseForm.getConductances() );
	}
	
	
	private void setFormProperties(SynapseForm synapseForm, Configuration conf, HttpServletRequest req)
	{
		List nuclei = conf.getNuclei( ReMoto.MNs );
		nuclei.addAll( conf.getNuclei( ReMoto.INs ) );
		
		String cdNucleus = synapseForm.getCdNucleus();
		
		// Set default value
		if( cdNucleus == null )
		{
			cdNucleus = conf.getCdNucleus();
		}
		else{
			conf.setCdNucleus(synapseForm.getCdNucleus());
			req.getSession().setAttribute("cdNucleus", cdNucleus);
		}
		if(cdNucleus.equals( ReMoto.DT )){
			cdNucleus = ReMoto.SOL;
			conf.setCdNucleus(cdNucleus);
			req.getSession().setAttribute("cdNucleus", cdNucleus);
		}
		
		/*
		// Do not include descending tracts
		if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) )
		{
			cdNucleus = (String)req.getSession().getAttribute( "cdNucleus" );
			
			if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) )
				cdNucleus = ReMoto.SOL;
		}

		req.getSession().setAttribute("cdNucleus", cdNucleus);
		*/
		
		
		String neuronTypePre = synapseForm.getTypePre();
		String neuronTypePos = synapseForm.getTypePos();
		
		// Modified by L. A. Elias in 05-10-11
		
		if( neuronTypePre == null )
		{
			neuronTypePre = (String)req.getSession().getAttribute( "neuronTypePre" );
			
			if( neuronTypePre == null )
				neuronTypePre = ReMoto.ACTIVE;
		}
		
		if( neuronTypePos == null )
		{
			neuronTypePos = (String)req.getSession().getAttribute( "neuronTypePos" );
			
			if( neuronTypePos == null )
				neuronTypePos = ReMoto.ACTIVE;
		}

		req.getSession().setAttribute("neuronTypePre", neuronTypePre);
		req.getSession().setAttribute("neuronTypePos", neuronTypePos);

		synapseForm.setTypePre( neuronTypePre );
		synapseForm.setTypePos( neuronTypePos );

		synapseForm.setNuclei( nuclei );
		synapseForm.setCdNucleus( cdNucleus );
		
		List typesPre = new ArrayList();
		List typesPos = new ArrayList();

		
		// First element is "all neuron types"
		NeuronVO all = new NeuronVO();
		all.setType( ReMoto.ALL );
		typesPre.add(all);
		typesPos.add(all);
		
		// Second element is "actives neuron types"
		NeuronVO actives = new NeuronVO();
		actives.setType( ReMoto.ACTIVE );
		typesPre.add(actives);
		typesPos.add(actives);
		
		
		
		// Other neurons

		if( cdNucleus.equals( ReMoto.IN_ext ) || cdNucleus.equals( ReMoto.IN_flex ) )
		{
			// It doesn´t matter to get from ext or flex
			typesPos.addAll( conf.getNeuronTypes(ReMoto.IN_ext, ReMoto.IN) );
			
			if( neuronTypePos.indexOf( ReMoto.S ) > 0 || neuronTypePos.indexOf( ReMoto.FR ) > 0 || neuronTypePos.indexOf( ReMoto.FF ) > 0 )
				neuronTypePos = ReMoto.ALL;
		}
		else
		{
			typesPos.addAll( conf.getNeuronTypes(cdNucleus, ReMoto.MN) );
			
			if( neuronTypePos.indexOf( ReMoto.RC ) > 0 || neuronTypePos.indexOf( ReMoto.IaIn ) > 0 || neuronTypePos.indexOf( ReMoto.IbIn ) > 0 || neuronTypePos.indexOf( ReMoto.gII ) > 0 )
				neuronTypePos = ReMoto.ALL;
		}
		
		synapseForm.setTypesPre(typesPre);
		synapseForm.setTypesPos(typesPos);

		// Get conductances according to the selections pre and pos
		synapseForm.setConductances( conf.getSynapticConductances( cdNucleus, neuronTypePre, neuronTypePos ) );
		
		
		synapseForm.setCdJoint( conf.getCdJoint() );
	}

}
