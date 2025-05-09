package br.remoto.model.vo;

import java.io.Serializable;


public class MuscleVO implements Serializable 
{
	private static final long serialVersionUID = 1L;

	protected String cdMuscle;
	protected double optimalLength;
    protected double viscosityCoeficient;
    protected double elasticCoeficientOfParallelElement;
    protected double c_T;
    protected double k_T;
    protected double Lr_T;
    protected double pennationAngle;
    protected double slackTendonLength;
    protected double maximumMuscleForce;
    protected double muscleMass;
    protected int index;
	
	public MuscleVO(){
	}


	public String getCdMuscle() {
		return cdMuscle;
	}


	public void setCdMuscle(String cdMuscle) {
		this.cdMuscle = cdMuscle;
	}


	public double getOptimalLength() {
		return optimalLength;
	}


	public void setOptimalLength(double optimalLength) {
		this.optimalLength = optimalLength;
	}


	public double getViscosityCoeficient() {
		return viscosityCoeficient;
	}


	public void setViscosityCoeficient(double viscosityCoeficient) {
		this.viscosityCoeficient = viscosityCoeficient;
	}


	public double getElasticCoeficientOfParallelElement() {
		return elasticCoeficientOfParallelElement;
	}


	public void setElasticCoeficientOfParallelElement(
			double elasticCoeficientOfParallelElement) {
		this.elasticCoeficientOfParallelElement = elasticCoeficientOfParallelElement;
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


	public double getMaximumMuscleForce() {
		return maximumMuscleForce;
	}


	public void setMaximumMuscleForce(double maximumMuscleForce) {
		this.maximumMuscleForce = maximumMuscleForce;
	}


	public double getMuscleMass() {
		return muscleMass;
	}


	public void setMuscleMass(double muscleMass) {
		this.muscleMass = muscleMass;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
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


	public double getLR_T() {
		return Lr_T;
	}


	public void setLR_T(double Lr_T) {
		this.Lr_T = Lr_T;
	}
	
	
}
