
package br.remoto.control.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.DynamicsForm;
import br.remoto.model.Configuration;
import br.remoto.model.vo.DynamicVO;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DynamicsAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		DynamicsForm dynamicsForm = (DynamicsForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( conf == null )
		{
			af = map.findForward( "noConfiguration" );
			return af;
		}

		if( "save".equals(task) )
		{
			setConfProperties(conf, dynamicsForm);
		}

		setFormProperties(dynamicsForm, conf, req);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, DynamicsForm dynamicsForm)
	{
		conf.setChangedConfiguration( true );

		DynamicVO vo = new DynamicVO();
		
		vo.setCdConductanceType( dynamicsForm.getCdConductanceType() );
		vo.setCdNucleus( dynamicsForm.getCdNucleus() );
		vo.setCdNucleusPre( dynamicsForm.getCdNucleusPre() );
		vo.setDynamicType( dynamicsForm.getDynamicType() );
		vo.setTau( dynamicsForm.getTau() );
		vo.setVariation( dynamicsForm.getVariation() );
		
		conf.setDynamicType( vo );
	}
	
	
	private void setFormProperties(DynamicsForm dynamicsForm, Configuration conf, HttpServletRequest req)
	{
		String cdConductanceType = (String)req.getParameter("cdConductanceType");
		String cdNucleusPre = (String)req.getParameter("cdNucleusPre");
		String cdNucleus = (String)req.getParameter("cdNucleus");
		
		DynamicVO vo = conf.getDynamicType( cdConductanceType, cdNucleusPre, cdNucleus );
		
		if( vo == null )
			return;
		
		dynamicsForm.setCdConductanceType( vo.getCdConductanceType() );
		dynamicsForm.setCdNucleus( vo.getCdNucleus() );
		dynamicsForm.setCdNucleusPre( vo.getCdNucleusPre() );
		dynamicsForm.setDynamicType( vo.getDynamicType() );
		dynamicsForm.setTau( vo.getTau() );
		dynamicsForm.setVariation( vo.getVariation() );
	}

}
