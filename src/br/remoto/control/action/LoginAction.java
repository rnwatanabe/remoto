
package br.remoto.control.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.LoginForm;
import br.remoto.dao.ConfigurationDAO;
import br.remoto.dao.UserDAO;
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

// Changed by L. A. Elias in 05-06-11.

public class LoginAction extends MainAction
{	
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se não houver eventos para a data especificada, retorna um formulário em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		LoginForm loginForm = (LoginForm) form;
		Configuration conf = getConfiguration(req);
		
		String task = (String)req.getParameter("task");
		
		if( task == null )
		{
			af = map.findForward( "login" );
		}
		else if( task.equals( "login" ) || task.equals( "logout" ) || task.equals( "exclude" ) )
		{
			if( task.equals( "logout" ) )
			{
				loginForm.setCdUser( ReMoto.cdGuestUser );
				loginForm.setPassword( ReMoto.cdGuestUser );
				
				task = "login";
				
				af = map.findForward( "login" );
			}

			UserDAO userDAO = new UserDAO();

			User user = userDAO.loadUser(loginForm.getCdUser(), loginForm.getPassword());

			if( user == null )
			{
				req.setAttribute("msg", "Username or password incorrect.");
				af = map.findForward( "login" );

				return af;
			}

			if( task.equals( "login" ) )
			{
				conf = loadConfiguration(req, -1);
				conf.setGeneral( user.getConfigurationDefault() );

				req.getSession().setAttribute("user", user);
				
				af = map.findForward( "login" );
			}
			else if( task.equals( "exclude" ) )
			{
				if( loginForm.getCdUser() != null && loginForm.getCdUser().equals( ReMoto.cdGuestUser ) )
				{
					req.setAttribute("msg", "Username incorrect.");
					af = map.findForward( "login" );

					return af;
				}

				ConfigurationDAO simDAO = new ConfigurationDAO();
				
				// First, delete all user's configurations 
				for(int i = 0; i < user.getConfigurations().size(); i++)
				{
					GeneralVO sim = (GeneralVO)user.getConfigurations().get(i);
					
					if( sim.getCdUser().equals( user.getCdUser() ) )
					{
						simDAO.deleteConfiguration( sim.getId() );
					}
				}
				
				// Then, delete user
				if( userDAO.deleteUser( user.getCdUser() ) == false )
				{
					req.setAttribute("msg", "Error deleting user in database.");

					af = map.findForward( "login" );
				}
				else
				{
					// User was already excluded from DB, exclude also from session
					req.getSession().setAttribute("user", null);

					// Load default configurations
					conf = loadConfiguration(req, -1);
					
					af = map.findForward( "login" );
				}
			}
		}
		else if( task.equals( "register" ) )
		{
			if( loginForm.getCdUser() == null || loginForm.getCdUser().equals( "" ) || loginForm.getCdUser().equals( ReMoto.cdGuestUser ))
			{
				req.setAttribute("msg", "Error - invalid username.");
				af = map.findForward( "login" );
			}
			else if( loginForm.getPassword() == null || loginForm.getPassword().equals( "" ) || loginForm.getPassword2() == null || loginForm.getPassword2().equals( "" ) )
			{
				req.setAttribute("msg", "Error - invalid password.");
				af = map.findForward( "login" );
			}
			else if( loginForm.getPassword().equals(loginForm.getPassword2()) )
			{
				UserDAO userDAO = new UserDAO();

				// Verify if username already exist
				if( userDAO.userExist( loginForm.getCdUser() ) )
				{
					req.setAttribute("msg", "Please, choose another username.");
					af = map.findForward( "login" );
				}
				else
				{
					User user = new User( loginForm.getCdUser() );
					
					user.setEmail( loginForm.getEmail() );
					user.setInstitution( loginForm.getInstitution() );
					user.setName( loginForm.getNmUser() );
					user.setPassword( loginForm.getPassword() );

					if( userDAO.createUser( user ) == false )
					{
						req.setAttribute("msg", "Error creating user in database.");
						af = map.findForward( "login" );
					}
					else
					{
			        	ConfigurationDAO simDAO = new ConfigurationDAO();
			        	
			        	user.setConfigurations( simDAO.loadConfigurations( ReMoto.cdGuestUser ) );

			        	req.getSession().setAttribute("user", user);
			        	
						af = map.findForward( "login" );
					}
				}
			}
			else
			{
				req.setAttribute("msg", "Error - password does not match the confirm password");
				af = map.findForward( "login" );	
			}
		}
		else if( task.equals( "new" ) )
		{
			req.setAttribute("msg", "");
			loginForm.setRegister( true );
			af = map.findForward( "login" );
		}

		loginForm.setPassword( "" );

		return af;
	}
	
}
