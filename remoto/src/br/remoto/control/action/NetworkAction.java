
package br.remoto.control.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.NetworkForm;
import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;
import br.remoto.model.vo.Nucleus;
import br.remoto.model.vo.NeuronVO;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NetworkAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		NetworkForm networkForm = (NetworkForm) form;
		Configuration conf = getConfiguration(req);
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
			return af;
		}

		String task = (String)req.getParameter("task");
		
		if( "save".equals(task) )
		{
			setConfProperties(conf, networkForm);
		}
		
		setFormProperties(conf, networkForm, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, NetworkForm networkForm)
	{
		conf.setChangedConfiguration( true );
		
		conf.setActiveAnkleExtensorMNs(networkForm.isActiveAnkleExtensorMNs());
		conf.setActiveAnkleExtensorINs(networkForm.isActiveAnkleExtensorINs());
		conf.setActiveAnkleExtensorSAs(networkForm.isActiveAnkleExtensorSAs());
		conf.setActiveAnkleFlexorMNs(networkForm.isActiveAnkleFlexorMNs());
		conf.setActiveAnkleFlexorINs(networkForm.isActiveAnkleFlexorINs());
		conf.setActiveAnkleFlexorSAs(networkForm.isActiveAnkleFlexorSAs());
		
		conf.setMiscellaneous( "xIniSOL", networkForm.getxIniSOL() );
		conf.setMiscellaneous( "xEndSOL", networkForm.getxEndSOL() );
		conf.setMiscellaneous( "xIniMG", networkForm.getxIniMG() );
		conf.setMiscellaneous( "xEndMG", networkForm.getxEndMG() );
		conf.setMiscellaneous( "xIniLG", networkForm.getxIniLG() );
		conf.setMiscellaneous( "xEndLG", networkForm.getxEndLG() );
		conf.setMiscellaneous( "xIniTA", networkForm.getxIniTA() );
		conf.setMiscellaneous( "xEndTA", networkForm.getxEndTA() );
		conf.setMiscellaneous( "xIniIN_ext", networkForm.getxIniIN_ext() );
		conf.setMiscellaneous( "xEndIN_ext", networkForm.getxEndIN_ext() );
		conf.setMiscellaneous( "xIniIN_flex", networkForm.getxIniIN_flex() );
		conf.setMiscellaneous( "xEndIN_flex", networkForm.getxEndIN_flex() );
		
		
		
		List neuronsForm = networkForm.getNeurons();
		List neuronTypes = conf.getNeuronTypes();

		// Alter active, quantity and indexStoreVm properties
		for(int i = 0; i < neuronTypes.size(); i++)
		{
			NeuronVO neuType = (NeuronVO)neuronTypes.get(i);
			
			for(int j = 0; j < neuronsForm.size(); j++)
			{
				NeuronVO neuForm = (NeuronVO)neuronsForm.get(j);
				
				System.out.println(neuForm.getCdNucleus() + "  -  " + neuForm.getType() + "  -  " + neuForm.isActive());
				
				if( neuType.getType().equals( neuForm.getType() ) && 
					neuType.getCdNucleus().equals( neuForm.getCdNucleus() ) )
				{
					neuType.setActive( neuForm.isActive() );
					
					if( neuForm.getQuantity() > ReMoto.maxNeuronsPerType )
						neuForm.setQuantity( ReMoto.maxNeuronsPerType );
					else if( neuForm.getQuantity() <= 0 )
						neuForm.setQuantity( 0 );
					
					neuType.setQuantity( neuForm.getQuantity() );

					// Reset indexStoreVm
					neuType.setIndexStoreVm1( -1 );
					
					neuronTypes.set(i, neuType);
					
					break;
				}
			}
		}
		
		conf.setListNeuronTypes( neuronTypes );
		
		
	}
	
	
	private void setFormProperties(Configuration conf, NetworkForm networkForm, HttpServletRequest req)
	{
		List nuclei = conf.getNuclei( ReMoto.MNs );
		nuclei.addAll( conf.getNuclei( ReMoto.INs ) );
		
		networkForm.setActiveAnkleExtensorMNs(conf.isActiveAnkleExtensorMNs());
		networkForm.setActiveAnkleExtensorINs(conf.isActiveAnkleExtensorINs());
		networkForm.setActiveAnkleExtensorSAs(conf.isActiveAnkleExtensorSAs());
		networkForm.setActiveAnkleFlexorMNs(conf.isActiveAnkleFlexorMNs());
		networkForm.setActiveAnkleFlexorINs(conf.isActiveAnkleFlexorINs());
		networkForm.setActiveAnkleFlexorSAs(conf.isActiveAnkleFlexorSAs());
		
		
				
		networkForm.setxIniSOL(conf.getMiscellaneous("xIniSOL"));
		networkForm.setxEndSOL(conf.getMiscellaneous("xEndSOL")) ;
		networkForm.setxIniMG(conf.getMiscellaneous("xIniMG")) ;
		networkForm.setxEndMG(conf.getMiscellaneous("xEndMG")) ;
		networkForm.setxIniLG(conf.getMiscellaneous("xIniLG")) ;
		networkForm.setxEndLG(conf.getMiscellaneous("xEndLG")) ;
		networkForm.setxIniTA(conf.getMiscellaneous("xIniTA")) ;
		networkForm.setxEndTA(conf.getMiscellaneous("xEndTA")) ;
		networkForm.setxIniIN_ext(conf.getMiscellaneous("xIniIN_ext")) ;
		networkForm.setxEndIN_ext(conf.getMiscellaneous("xEndIN_ext")) ;
		networkForm.setxIniIN_flex(conf.getMiscellaneous("xIniIN_flex")) ;
		networkForm.setxEndIN_flex(conf.getMiscellaneous("xEndIN_flex")) ;
		
		List neurons = new ArrayList();
		int numNeuronTypes = 0;
		
		for(int i = 0; i < nuclei.size(); i++)
		{
			Nucleus nucleus = (Nucleus)nuclei.get(i);
			String cdNucleus = nucleus.getCd();
			String nameNucleus = nucleus.getName();

			// Do not include descending tracts
			if( cdNucleus == null || cdNucleus.equals( ReMoto.DT ) )
				continue;
			
			List neuNucleus = new ArrayList();

			if( cdNucleus.equals( ReMoto.IN_ext ) || cdNucleus.equals( ReMoto.IN_flex ) )
			{
				neuNucleus.addAll( conf.getNeuronTypes(cdNucleus, ReMoto.IN) );
			}
			else
			{
				neuNucleus = conf.getNeuronTypes(cdNucleus, ReMoto.MN);
				neuNucleus.addAll( conf.getNeuronTypes(cdNucleus, ReMoto.AF) );
			}

			numNeuronTypes = neuNucleus.size();
			
			if( numNeuronTypes > 0 )
			{
				// Put nucleus name in the first neuron to be showed in the page
				((NeuronVO)neuNucleus.get(0)).setNameNucleus( nameNucleus );

				neurons.addAll( neuNucleus );
			}
		}
		
		// Put numNeurons in request to divide neurons in nuclei
		req.setAttribute("numNeurons", Integer.toString(numNeuronTypes));
		
		
		for(int j = 0; j < neurons.size(); j++)
		{
			NeuronVO neu = (NeuronVO)neurons.get(j);
			
			if(neu.isActive()){
				
				//In case of ankle joint
				if(neu.getCdNucleus().equals(ReMoto.TA) || neu.getCdNucleus().equals(ReMoto.IN_flex)){
					if(neu.getCategory().equals(ReMoto.MN))			networkForm.setActiveAnkleFlexorMNs(true);
					else if(neu.getCategory().equals(ReMoto.IN))	networkForm.setActiveAnkleFlexorINs(true);
					else if(neu.getCategory().equals(ReMoto.AF))	networkForm.setActiveAnkleFlexorSAs(true);
				}
				else{
					if(neu.getCategory().equals(ReMoto.MN))			networkForm.setActiveAnkleExtensorMNs(true);
					else if(neu.getCategory().equals(ReMoto.IN))	networkForm.setActiveAnkleExtensorINs(true);
					else if(neu.getCategory().equals(ReMoto.AF))	networkForm.setActiveAnkleExtensorSAs(true);
				}
			
			}
				
		}
		
		networkForm.setNeurons( neurons );
		
		networkForm.setCdJoint( conf.getCdJoint() );
		
	}

}
