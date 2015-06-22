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
public class DynamicsForm extends ActionForm
{
	private static final long serialVersionUID = 1L;

	protected String cdConductanceType;
	protected String cdNucleus;
	protected String cdNucleusPre;
	protected String dynamicType;
	protected double tau;
	protected double variation;
	

    public DynamicsForm()
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

	public String getCdConductanceType() {
		return cdConductanceType;
	}

	public void setCdConductanceType(String cdConductanceType) {
		this.cdConductanceType = cdConductanceType;
	}

	public String getCdNucleus() {
		return cdNucleus;
	}

	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}

	public String getCdNucleusPre() {
		return cdNucleusPre;
	}

	public void setCdNucleusPre(String cdNucleusPre) {
		this.cdNucleusPre = cdNucleusPre;
	}

	public String getDynamicType() {
		return dynamicType;
	}

	public void setDynamicType(String dynamicType) {
		this.dynamicType = dynamicType;
	}

	public double getTau() {
		return tau;
	}

	public void setTau(double tau) {
		this.tau = tau;
	}

	public double getVariation() {
		return variation;
	}

	public void setVariation(double variation) {
		this.variation = variation;
	}


	
    
}
