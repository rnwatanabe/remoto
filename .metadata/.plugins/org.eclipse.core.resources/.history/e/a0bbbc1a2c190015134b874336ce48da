

package br.remoto.control.action;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.data.xy.XYSeriesCollection;

import br.remoto.control.form.InputForm;
import br.remoto.control.form.ResultsForm;
import br.remoto.model.Configuration;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.ReMoto;
import br.remoto.model.vo.NeuronVO;


public class InputAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		
		ActionForward af = null;
		InputForm inputForm = (InputForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
		}
		
		if( "change".equals( task ) )
		{
			setFormProperties(inputForm, conf, req);
			
			setConfProperties(conf, inputForm);
			req.getSession().setAttribute("task", task);
			System.out.println("task: " + task);
		}
		else if( "saveStimulus".equals(task) )
		{
			setConfProperties(conf, inputForm);
		}

		setFormProperties(inputForm, conf, req);
		
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, InputForm inputForm)
	{
		conf.setChangedConfiguration( true );

		List inputs = inputForm.getInputs();
		List noises = inputForm.getNoises();
		
		List modulations = inputForm.getModulations();
		
		for(int i = 0; i < inputs.size(); i++)
		{
			NeuronVO neu = (NeuronVO)inputs.get(i);
			
			
			if( neu.isActive() )
			{
				// Some validations
				if( neu.getMean() < ReMoto.minISImean )
					neu.setMean( ReMoto.minISImean );

				if( neu.getQuantity() > ReMoto.maxNeuronsPerType )
					neu.setQuantity( ReMoto.maxNeuronsPerType );
				
				ModulatingSignal signal = new ModulatingSignal();
				
				signal.setAmp( neu.getAmp() );
				signal.setCdSignal( neu.getCdSignal() );
				signal.setFreq( neu.getFreq() );
				signal.setTini( neu.getIni() );
				signal.setTfin( neu.getFin() );
				signal.setWidth( neu.getWidth() );
				signal.setCdNucleus(neu.getCdNucleus());
				signal.setCdNeuronType(neu.getType());
        		signal.setModType( neu.getModType() );
        		signal.setDelay( neu.getDelay() );
        		
				modulations.add(signal);
				
			}
		}
		
		conf.setDescendingCommands(modulations);
		
		for(int i = 0; i < noises.size(); i++)
		{
			NeuronVO neu = (NeuronVO)noises.get(i);
			
			// Validation
			if( neu.getMean() < ReMoto.minISImean / 10 )
				neu.setMean( ReMoto.minISImean / 10 );
		}
		
		conf.setListNeuronTypes( inputs );
		conf.setListNeuronTypes( noises );
	}
	
	
	private void setFormProperties(InputForm inputForm, Configuration conf, HttpServletRequest req)
	{
		inputForm.setNoises( conf.getNeuronTypes(ReMoto.DT, ReMoto.Noise) );

		List inputs = conf.getNeuronTypes(ReMoto.DT, ReMoto.TR);
		
		Collections.sort( inputs );
		
		inputForm.setInputs( inputs );
	}

}
