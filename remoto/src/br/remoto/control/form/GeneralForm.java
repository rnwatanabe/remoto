/*
 * Created on 01/10/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package br.remoto.control.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GeneralForm extends ActionForm
{
	private static final long serialVersionUID = 1L;

	private int id;
	private boolean storeResults;

	private String dsConfiguration;
	private String nmConfiguration;
	private String cdUser;

	private List configurations;
	

    public GeneralForm()
	{
		super();
	}

	public void reset(ActionMapping map, HttpServletRequest req)
	{
		super.reset(map, req);
	}
	
	public ActionErrors validate(ActionMapping map, HttpServletRequest req)
	{
		return super.validate(map, req);
	}

	public int getId() {
		return id;
	}

	public void setId(int idSimulation) {
		this.id = idSimulation;
	}

	public List getConfigurations() {
		return configurations;
	}

	public void setConfigurations(List simulations) {
		this.configurations = simulations;
	}

	public String getDsConfiguration() {
		return dsConfiguration;
	}

	public void setDsConfiguration(String dsSimulation) {
		this.dsConfiguration = dsSimulation;
	}

	public String getNmConfiguration() {
		return nmConfiguration;
	}

	public void setNmConfiguration(String nmSimulation) {
		this.nmConfiguration = nmSimulation;
	}

	public boolean isStoreResults() {
		return storeResults;
	}

	public void setStoreResults(boolean store) {
		this.storeResults = store;
	}

	public String getCdUser() {
		return cdUser;
	}

	public void setCdUser(String user) {
		this.cdUser = user;
	}


}
