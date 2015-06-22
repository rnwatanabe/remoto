/*
 * Created on 08/12/2011
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package br.remoto.control.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.remoto.model.Current;
import br.remoto.model.Nerve;
import br.remoto.model.vo.MotorUnitVO;


/**
 * @author vitor chaud
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class EMGForm extends ActionForm
{
	private static final long serialVersionUID = 1L;
	
	private String cdEMGModel;
	
	private List motorunits;
	private List nuclei;
	private String cdNucleus;
	
	private int distribution;
	
	
    public EMGForm()
	{
		super();
		motorunits = new ArrayList();
	}

	public void reset(ActionMapping map, HttpServletRequest req)
	{
		super.reset(map, req);
		motorunits = new ArrayList();
	}
	
	public ActionErrors validate(ActionMapping map, HttpServletRequest req)
	{
		return super.validate(map, req);
	}

	public MotorUnitVO getMuType(int index)
    {
       while(motorunits.size() <= index)
       {
    	   motorunits.add(new MotorUnitVO());
       }

       return (MotorUnitVO) motorunits.get(index);
	}
    

	public List getMotorunits() {
		return motorunits;
	}

	public void setMotorunits(List motorunits) {
		this.motorunits = motorunits;
	}
	
	public String getCdEMGModel() {
		return cdEMGModel;
	}

	public void setCdEMGModel(String cdEMGModel) {
		this.cdEMGModel = cdEMGModel;
	}
    
	public int getDistribution() {
		return distribution;
	}


	public void setDistribution(int distribution) {
		this.distribution = distribution;
	}
	
	public String getCdNucleus() {
		return cdNucleus;
	}

	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}
	
	public List getNuclei() {
		return nuclei;
	}

	public void setNuclei(List nuclei) {
		this.nuclei = nuclei;
	}
}
