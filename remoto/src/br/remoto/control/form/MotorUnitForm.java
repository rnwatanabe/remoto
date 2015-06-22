/*
 * Created on 01/10/2004
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

import br.remoto.model.vo.MotorUnitVO;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MotorUnitForm extends ActionForm
{
	private static final long serialVersionUID = 1L;

	private List motorunits;
	private List nuclei;
	private String cdNucleus;
	private String cdJoint;
	private String cdMuscleModel;
	
	private int distribution;

    public MotorUnitForm()
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


	public int getDistribution() {
		return distribution;
	}


	public void setDistribution(int distribution) {
		this.distribution = distribution;
	}


	public String getCdJoint() {
		return cdJoint;
	}


	public void setCdJoint(String cdJoint) {
		this.cdJoint = cdJoint;
	}


	public String getCdMuscleModel() {
		return cdMuscleModel;
	}


	public void setCdMuscleModel(String cdMuscleModel) {
		this.cdMuscleModel = cdMuscleModel;
	}


}
