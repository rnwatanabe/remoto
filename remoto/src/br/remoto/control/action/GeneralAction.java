
package br.remoto.control.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.GeneralForm;
import br.remoto.dao.ConfigurationDAO;
import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;
import br.remoto.model.vo.GeneralVO;
import br.remoto.model.vo.User;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GeneralAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		GeneralForm generalForm = (GeneralForm) form;
		Configuration conf = getConfiguration(req);

		User user = getUser(req);
		
		String tabIndex = (String)req.getParameter("tabIndex");
		String task = (String)req.getParameter("task");
		
		
		//System.out.println("task: " + task);
		//System.out.println("tabIndex: " + tabIndex);
		
		
		if( conf == null || user == null )
		{
			af = map.findForward( "noConfiguration" );
			return af;
		}
		
		generalForm.setCdUser( user.getCdUser() );
		
		
		
		if( "load".equals(task) )
		{
			conf = loadConfiguration(req, generalForm.getId());
		}
		if( "reload".equals(task) )
		{
			conf = reloadConfiguration(req, generalForm.getId());
		}
		else if( "new".equals(task) )
		{
			if( user.getCdUser().equals( ReMoto.cdGuestUser ) )
			{
				req.setAttribute("msg", "You must login in order to create a new scenario.");
			}
			else
			{
				setConfProperties(conf, generalForm);
				
				GeneralVO confModel = user.getGeneralConfiguration( generalForm.getId() );
				GeneralVO confNew = new GeneralVO();
				
				Calendar dia = Calendar.getInstance();
			    SimpleDateFormat formatter = new SimpleDateFormat( "MM/dd/yyyy - hh:mm:ss" );
			    String date = formatter.format( dia.getTime() );

	    		confNew.setId( -1 );
				confNew.setName( "New simulation - " + date );
				confNew.setCdUser( user.getCdUser() );
				confNew.setTFin( confModel.getTFin() );
				//testing
				confNew.setStep( confModel.getStep() );
				
				confNew.setMerge( confModel.isMerge() );
				confNew.setKeepProperties( confModel.isKeepProperties() );
				confNew.setStoreResults( confModel.isStoreResults() );
				
				conf.setGeneral( confNew );
				user.addConfiguration( confNew );
				
				saveUserConfiguration(req, conf, user);
			}
		}
		else if( "delete".equals(task) )
		{
			if( conf.getGeneral().getCdUser().equals( ReMoto.cdGuestUser ) )
			{
				req.setAttribute("msg", "Default configurations cannot be deleted.");
			}
			else
			{
				ConfigurationDAO simDAO = new ConfigurationDAO();

				simDAO.deleteConfiguration(generalForm.getId());
		    	user.deleteConfiguration(generalForm.getId());
		    	conf = loadConfiguration(req, -1);
			}
		}
		else if( "save".equals(task) )
		{
			if( conf.getCdUser().equals( ReMoto.cdGuestUser ) )
			{
				req.setAttribute("msg", "Default configurations cannot be stored, create a new one.");
			}
			else
			{
				if( !user.isNewName( generalForm.getNmConfiguration(), generalForm.getId() ) )
				{
					req.setAttribute("msg", "Choose a new name for the configuration.");
				}
				else
				{
					setConfProperties(conf, generalForm);
					
					if( saveUserConfiguration(req, conf, user) == false )
					{
						req.setAttribute("msg", "Error saving simulation parameters in database.");
					}
					else
					{
						req.setAttribute("msg", "Configuration saved.");
					}
				}
			}
		}

		setFormProperties(generalForm, conf, user);
		
		af = map.findForward( "configuration" );
		  
		return af;
	}
	
	
	private void setConfProperties(Configuration conf, GeneralForm generalForm)
	{
    	conf.setName( generalForm.getNmConfiguration() );
    	conf.setDescription( generalForm.getDsConfiguration() );
    	conf.setStoreResults( generalForm.isStoreResults() );
	}
	
	
	private void setFormProperties(GeneralForm generalForm, Configuration conf, User user)
	{
		generalForm.setConfigurations( user.getConfigurations() );
		generalForm.setId( conf.getId() );

		generalForm.setNmConfiguration( conf.getName() );
		generalForm.setDsConfiguration( conf.getDescription() );
		generalForm.setStoreResults( conf.isStoreResults() );
	}
	
}
