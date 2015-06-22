/*
 * Created on 01/10/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package br.remoto.control.form;

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
public class SimulationForm extends ActionForm
{
	private static final long serialVersionUID = 1L;

	private String percentage;
	private String start;
	private String finish;
	private String status;
	private int statusInt;
	

	int i = 0;
	
	//testando
	private String cdSimulation = "vazio";
	

    public SimulationForm()
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
	
	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	

	public String getFinish() {
		return finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}
	
	//testando
	
	public String getCdSimulation() {
		return cdSimulation;
	}

	public void setCdSimulation(String cdSimulation) {
		this.cdSimulation = cdSimulation;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getStatusInt() {
		return statusInt;
	}

	public void setStatusInt(int statusInt) {
		this.statusInt = statusInt;
	}

}
