
package br.remoto.control.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.MiscellaneousForm;
import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MiscellaneousAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		MiscellaneousForm fiberForm = (MiscellaneousForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
			return af;
		}

		if( "save".equals(task) )
		{
			setConfProperties(conf, fiberForm);
		}

		setFormProperties(fiberForm, conf, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, MiscellaneousForm form)
	{
		conf.setChangedConfiguration( true );

		conf.setMiscellaneous( form.getMiscellaneous() );
		
		conf.setStoreSignals( form.isStoreSignals() );
    	conf.setKeepProperties( form.isKeepProperties() );
		
		
    	
		if( conf.getMiscellaneous( ReMoto.afAxonRefractoryPeriod ) < ReMoto.minRefractoryPeriod ) 
			conf.setMiscellaneous( ReMoto.afAxonRefractoryPeriod, ReMoto.minRefractoryPeriod );
		
		if( conf.getMiscellaneous( ReMoto.mnAxonRefractoryPeriod ) < ReMoto.minRefractoryPeriod ) 
			conf.setMiscellaneous( ReMoto.mnAxonRefractoryPeriod, ReMoto.minRefractoryPeriod );
		
		if( conf.getMiscellaneous( ReMoto.mnSomaRefractoryPeriod ) < ReMoto.minRefractoryPeriod ) 
			conf.setMiscellaneous( ReMoto.mnSomaRefractoryPeriod, ReMoto.minRefractoryPeriod );
		
		if( conf.getMiscellaneous( ReMoto.inRcSomaRefractoryPeriod ) < ReMoto.minRefractoryPeriod ) 
			conf.setMiscellaneous( ReMoto.inRcSomaRefractoryPeriod, ReMoto.minRefractoryPeriod );
		
		if( conf.getMiscellaneous( ReMoto.inIaSomaRefractoryPeriod ) < ReMoto.minRefractoryPeriod ) 
			conf.setMiscellaneous( ReMoto.inIaSomaRefractoryPeriod, ReMoto.minRefractoryPeriod );
		
		if( conf.getMiscellaneous( ReMoto.inIbSomaRefractoryPeriod ) < ReMoto.minRefractoryPeriod ) 
			conf.setMiscellaneous( ReMoto.inIbSomaRefractoryPeriod, ReMoto.minRefractoryPeriod );
		
		if( conf.getMiscellaneous( ReMoto.cellsPerSlice ) < ReMoto.minCellsPerSlice ) 
			conf.setMiscellaneous( ReMoto.cellsPerSlice, ReMoto.minCellsPerSlice );
		
		if( conf.getMiscellaneous( ReMoto.gammaCa ) < 0 )
			conf.setMiscellaneous( ReMoto.gammaCa, 0 );
		else if( conf.getMiscellaneous( ReMoto.gammaCa ) > 1 )
			conf.setMiscellaneous( ReMoto.gammaCa, 1 );
		
		if( conf.getMiscellaneous( ReMoto.gIISomaRefractoryPeriod ) < ReMoto.minRefractoryPeriod )
			conf.setMiscellaneous( ReMoto.gIISomaRefractoryPeriod, ReMoto.minRefractoryPeriod );
		
	}
	
	
	private void setFormProperties(MiscellaneousForm form, Configuration conf, HttpServletRequest req)
	{
		form.setMiscellaneous( conf.getMiscellaneous() );
		
		form.setStoreSignals( conf.isStoreSignals() );
		form.setKeepProperties( conf.isKeepProperties() );
	}

}
