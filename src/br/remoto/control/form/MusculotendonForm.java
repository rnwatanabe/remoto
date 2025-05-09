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

public class MusculotendonForm extends ActionForm
{
	private static final long serialVersionUID = 1L;
	
	private List motorunits;
	private List nuclei;
	private String cdNucleus;
	private String cdJoint;
	private String cdMuscleModel;
	
	private double optimalLength;
	private double maximumMuscleForce;
	private double mass;
	private double viscosityCoeficient;
	private double elasticCoeficientParallelElement;
	private double c_T;
	private double k_T;
	private double lr_T;
	private double pennationAngle;
	private double slackTendonLength;
	
	private int distribution;

    public MusculotendonForm()
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

	public double getOptimalLength() {
		return optimalLength;
	}

	public void setOptimalLength(double optimalLength) {
		this.optimalLength = optimalLength;
	}

	public double getMaximumMuscleForce() {
		return maximumMuscleForce;
	}

	public void setMaximumMuscleForce(double maximumMuscleForce) {
		this.maximumMuscleForce = maximumMuscleForce;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getViscosityCoeficient() {
		return viscosityCoeficient;
	}

	public void setViscosityCoeficient(double viscosityCoeficient) {
		this.viscosityCoeficient = viscosityCoeficient;
	}

	public double getElasticCoeficientParallelElement() {
		return elasticCoeficientParallelElement;
	}

	public void setElasticCoeficientParallelElement(
			double elasticCoeficientParallelElement) {
		this.elasticCoeficientParallelElement = elasticCoeficientParallelElement;
	}

	

	public double getPennationAngle() {
		return pennationAngle;
	}

	public void setPennationAngle(double pennationAngle) {
		this.pennationAngle = pennationAngle;
	}

	public double getSlackTendonLength() {
		return slackTendonLength;
	}

	public void setSlackTendonLength(double slackTendonLength) {
		this.slackTendonLength = slackTendonLength;
	}

	public double getC_T() {
		return c_T;
	}

	public void setC_T(double c_T) {
		this.c_T = c_T;
	}

	public double getK_T() {
		return k_T;
	}

	public void setK_T(double k_T) {
		this.k_T = k_T;
	}

	public double getLr_T() {
		return lr_T;
	}

	public void setLr_T(double lr_T) {
		this.lr_T = lr_T;
	}
	
}
