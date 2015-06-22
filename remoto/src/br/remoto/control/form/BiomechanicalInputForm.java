
package br.remoto.control.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.remoto.model.BiomechanicalInput;
import br.remoto.model.Current;
import br.remoto.model.Nerve;


public class BiomechanicalInputForm extends ActionForm
{
	private static final long serialVersionUID = 1L;
	
	private String cdJoint;
	private List biomechanicalInputs;
	private List nuclei;
	private String cdNucleus;
	
	private double stretchVelocity;
	private double jointAngle;
	private double kneeAngle;

    public double getJointAngle() {
		return jointAngle;
	}

	public void setJointAngle(double jointAngle) {
		this.jointAngle = jointAngle;
	}

	public double getKneeAngle() {
		return kneeAngle;
	}

	public void setKneeAngle(double kneeAngle) {
		this.kneeAngle = kneeAngle;
	}

	public BiomechanicalInputForm()
	{
		super();
		biomechanicalInputs = new ArrayList();
	}

	public void reset(ActionMapping map, HttpServletRequest req)
	{
		super.reset(map, req);
		biomechanicalInputs = new ArrayList();
	}
	
	public ActionErrors validate(ActionMapping map, HttpServletRequest req)
	{
		return super.validate(map, req);
	}
	
	public BiomechanicalInput getBiomechanicalInput(int index)
    {
        while(biomechanicalInputs.size() <= index)
        {
        	biomechanicalInputs.add(new BiomechanicalInput());
        }

        return (BiomechanicalInput) biomechanicalInputs.get(index);
    }
	

	public List getBiomechanicalInputs() {
		return biomechanicalInputs;
	}

	public void setBiomechanicalInputs(List biomechanicalInputs) {
		this.biomechanicalInputs = biomechanicalInputs;
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

	public String getCdJoint() {
		return cdJoint;
	}

	public void setCdJoint(String cdJoint) {
		this.cdJoint = cdJoint;
	}

	public double getStretchVelocity() {
		return stretchVelocity;
	}

	public void setStretchVelocity(double stretchVelocity) {
		this.stretchVelocity = stretchVelocity;
	}

	
}
