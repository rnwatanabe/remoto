
package br.remoto.model.vo;

import java.io.Serializable;



public class MotorUnitVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	protected double maxTension1;
	protected double maxTension2;
	protected double twitchTension1;
	protected double twitchTension2;
	protected double contractionTime1;
	protected double contractionTime2;
	protected double ampEMG1;
	protected double ampEMG2;
	protected double lambdaEMG1;
	protected double lambdaEMG2;
	protected boolean active;
	protected String type;
	protected String cdNucleus;
	
	
	protected double twitchTension1Raikova;
	protected double twitchTension2Raikova;
	protected double contractionTime1Raikova;
	protected double contractionTime2Raikova;
	protected double halfRelaxationTimeRaikova1;
	protected double halfRelaxationTimeRaikova2;
	
	protected double b1;
	protected double b2;
	protected double twTet1;
	protected double twTet2;
	
	protected double bRaikova1;
	protected double bRaikova2;
	protected double twTetRaikova1;
	protected double twTetRaikova2;
	
	
	public MotorUnitVO()
	{
	}


	public double getAmpEMG1() {
		return ampEMG1;
	}
	public void setAmpEMG1(double ampEMG1) {
		this.ampEMG1 = ampEMG1;
	}
	public double getAmpEMG2() {
		return ampEMG2;
	}
	public void setAmpEMG2(double ampEMG2) {
		this.ampEMG2 = ampEMG2;
	}
	public double getContractionTime1() {
		return contractionTime1;
	}
	public void setContractionTime1(double contractionTime1) {
		this.contractionTime1 = contractionTime1;
	}
	public double getContractionTime2() {
		return contractionTime2;
	}
	public void setContractionTime2(double contractionTime2) {
		this.contractionTime2 = contractionTime2;
	}
	public double getLambdaEMG1() {
		return lambdaEMG1;
	}
	public void setLambdaEMG1(double lambdaEMG1) {
		this.lambdaEMG1 = lambdaEMG1;
	}
	public double getLambdaEMG2() {
		return lambdaEMG2;
	}
	public void setLambdaEMG2(double lambdaEMG2) {
		this.lambdaEMG2 = lambdaEMG2;
	}
	public double getTwitchTension1() {
		return twitchTension1;
	}
	public void setTwitchTension1(double twitchTension1) {
		this.twitchTension1 = twitchTension1;
	}
	public double getTwitchTension2() {
		return twitchTension2;
	}
	public void setTwitchTension2(double twitchTension2) {
		this.twitchTension2 = twitchTension2;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getCdNucleus() {
		return cdNucleus;
	}
	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}
	public double getMaxTension1() {
		return maxTension1;
	}
	public void setMaxTension1(double maxTension1) {
		this.maxTension1 = maxTension1;
	}
	public double getMaxTension2() {
		return maxTension2;
	}
	public void setMaxTension2(double maxTension2) {
		this.maxTension2 = maxTension2;
	}


	public double getTwitchTension1Raikova() {
		return twitchTension1Raikova;
	}


	public void setTwitchTension1Raikova(double twitchTension1Raikova) {
		this.twitchTension1Raikova = twitchTension1Raikova;
	}


	public double getTwitchTension2Raikova() {
		return twitchTension2Raikova;
	}


	public void setTwitchTension2Raikova(double twitchTension2Raikova) {
		this.twitchTension2Raikova = twitchTension2Raikova;
	}


	public double getContractionTime1Raikova() {
		return contractionTime1Raikova;
	}


	public void setContractionTime1Raikova(double contractionTime1Raikova) {
		this.contractionTime1Raikova = contractionTime1Raikova;
	}


	public double getContractionTime2Raikova() {
		return contractionTime2Raikova;
	}


	public void setContractionTime2Raikova(double contractionTime2Raikova) {
		this.contractionTime2Raikova = contractionTime2Raikova;
	}


	public double getHalfRelaxationTimeRaikova1() {
		return halfRelaxationTimeRaikova1;
	}


	public void setHalfRelaxationTimeRaikova1(double halfRelaxationTimeRaikova1) {
		this.halfRelaxationTimeRaikova1 = halfRelaxationTimeRaikova1;
	}


	public double getHalfRelaxationTimeRaikova2() {
		return halfRelaxationTimeRaikova2;
	}


	public void setHalfRelaxationTimeRaikova2(double halfRelaxationTimeRaikova2) {
		this.halfRelaxationTimeRaikova2 = halfRelaxationTimeRaikova2;
	}
	
	public double getb1() {
		//System.out.println("b1: " + b1);
		return b1;
	}
	
	public void setb1(double b1) {
		this.b1 = b1;
	}
	
	public double getb2() {
		return b2;
	}
	
	public void setb2(double b2) {
		this.b2 = b2;
	}
	
	public double gettwTet1() {
		return twTet1;
	}
	
	public void settwTet1(double twTet1) {
		this.twTet1 = twTet1;
	}
	
	public double gettwTet2() {
		return twTet2;
	}
	
	public void settwTet2(double twTet2) {
		this.twTet2 = twTet2;
	}
	
	public double getbRaikova1() {
		return bRaikova1;
	}
	
	public void setbRaikova1(double bRaikova1) {
		this.bRaikova1 = bRaikova1;
	}
	
	public double getbRaikova2() {
		return bRaikova2;
	}
	
	public void setbRaikova2(double bRaikova2) {
		this.bRaikova2 = bRaikova2;
	}
	
	public double gettwTetRaikova1() {
		return twTetRaikova1;
	}
	
	public void settwTetRaikova1(double twTetRaikova1) {
		this.twTetRaikova1 = twTetRaikova1;
	}
	
	public double gettwTetRaikova2() {
		return twTetRaikova2;
	}
	
	public void settwTetRaikova2(double twTetRaikova2) {
		this.twTetRaikova2 = twTetRaikova2;
	}
	
	
	
}
	
