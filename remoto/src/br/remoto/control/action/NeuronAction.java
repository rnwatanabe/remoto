
package br.remoto.control.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.NeuronForm;
import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NeuronAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		NeuronForm neuronForm = (NeuronForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		String cdNucleus = neuronForm.getCdNucleus();

		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
			return af;
		}

		if( "saveMN".equals(task) )
		{
			conf.setChangedConfiguration( true );
			conf.setListNeuronTypes( neuronForm.getMotoneurons() );

			// Modified on 17feb2008: always assume as merged
			// This is no more used
			conf.setMerge( true );
			
			task = "MN";
		}
		else if( "saveIN".equals(task) )
		{
			conf.setChangedConfiguration( true );
			conf.setListNeuronTypes( neuronForm.getInterneurons() );
			
			task = "IN";
		}
		else if( "saveAF".equals(task) )
		{
			conf.setChangedConfiguration( true );
			conf.setListNeuronTypes( neuronForm.getSensories() );
			
			task = "AF";
		}

		if( "MN".equals(task) || "AF".equals(task) )
		{
			if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) || cdNucleus.equals( ReMoto.IN_ext ) || cdNucleus.equals( ReMoto.IN_flex ) )
			{
				cdNucleus = (String)req.getSession().getAttribute( "cdNucleus" );
				
				if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) || cdNucleus.equals( ReMoto.IN_ext ) || cdNucleus.equals( ReMoto.IN_flex ) )
					cdNucleus = ReMoto.SOL;
			}

			List nuclei = conf.getNuclei( ReMoto.MNs );
			neuronForm.setNuclei( nuclei );
		}
		else if( "IN".equals(task) )
		{
			if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) || cdNucleus.equals( ReMoto.SOL ) || cdNucleus.equals( ReMoto.MG ) || cdNucleus.equals( ReMoto.LG ) || cdNucleus.equals( ReMoto.TA ) )
			{
				cdNucleus = (String)req.getSession().getAttribute( "cdNucleus" );
				
				if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) || cdNucleus.equals( ReMoto.SOL ) || cdNucleus.equals( ReMoto.MG ) || cdNucleus.equals( ReMoto.LG ) || cdNucleus.equals( ReMoto.TA ) )
					cdNucleus = ReMoto.IN_ext;
			}

			List nuclei = conf.getNuclei( ReMoto.INs );
			neuronForm.setNuclei( nuclei );
		}

		req.getSession().setAttribute("cdNucleus", cdNucleus);

		setFormProperties(neuronForm, conf, req, cdNucleus);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setFormProperties(NeuronForm neuronForm, Configuration conf, HttpServletRequest req, String cdNucleus)
	{
		neuronForm.setCdNucleus( cdNucleus );
		neuronForm.setMotoneurons( conf.getNeuronTypes( cdNucleus, ReMoto.MN ) );
		neuronForm.setInterneurons( conf.getNeuronTypes( cdNucleus, ReMoto.IN ) );
		neuronForm.setMerge( conf.isMerge() );
		neuronForm.setSensories( conf.getNeuronTypes( cdNucleus, ReMoto.AF ) );
	}

}
