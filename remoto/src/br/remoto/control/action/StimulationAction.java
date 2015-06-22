
package br.remoto.control.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.StimulationForm;
import br.remoto.model.Configuration;
import br.remoto.model.Current;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.Nerve;
import br.remoto.model.ReMoto;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StimulationAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		StimulationForm stimulationForm = (StimulationForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
		}
		
		if( "saveStimulus".equals(task) )
		{
			setConfProperties(conf, stimulationForm);
		}

		setFormProperties(stimulationForm, conf, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, StimulationForm stimulationForm)
	{
		
		
		List nerves = stimulationForm.getNerves();
	
	
		for(int i = 0; i < nerves.size(); i++)
		{
			Nerve nerve = (Nerve)nerves.get(i);
			
			ModulatingSignal signal = new ModulatingSignal();
			
			signal.setAmp( nerve.getModulating_amp());
			signal.setCdSignal( nerve.getCdSignal() );
			signal.setFreq( nerve.getModulating_freq());
			signal.setTini( nerve.getModulating_tini() );
			signal.setTfin( nerve.getModulating_tfin() );
			signal.setWidth( nerve.getWidth() );
			
			nerve.setSignal( signal );
			
			
			if (nerve.getTfin() > conf.getTFin()){
				conf.setTFin(nerve.getTfin());
			}
		}
		
		conf.setNerves( stimulationForm.getNerves() );
		conf.setRecruitmentOrderFES(stimulationForm.getRecruitmentOrderFES());
	}
	
	
	private void setFormProperties(StimulationForm stimulationForm, Configuration conf, HttpServletRequest req)
	{
		System.out.println("1");
		
		stimulationForm.setNerves( conf.getNerves() );
		
		System.out.println("2");
	}

}
