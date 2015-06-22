package br.remoto.model.Neuron;

import java.io.Serializable;

//Modified in 05-10-11 by L. A. Elias

public class Miscellaneous implements Serializable
{
	private static final long serialVersionUID = 1L;

	private double gammaCa;
	private double step;
	private double stepByTwo;
	private double stepBySix;
	private double mnSomaRefractoryPeriod;  	
	private double mnAxonRefractoryPeriod; 	
	private double rcSomaRefractoryPeriod; 	
	private double iaInSomaRefractoryPeriod; 	
	private double ibInSomaRefractoryPeriod; 	
	private double afAxonRefractoryPeriod;
	private double gIISomaRefractoryPeriod;
	
	
	public double getGammaCa() {
		return gammaCa;
	}
	public void setGammaCa(double gammaCa) {
		this.gammaCa = gammaCa;
	}	
	public double getStep() {
		return step;
	}
	public void setStep(double value) {
		this.step = value;
	}
	public double getStepBySix() {
		return stepBySix;
	}
	public void setStepBySix(double valueBySix) {
		this.stepBySix = valueBySix;
	}
	public double getStepByTwo() {
		return stepByTwo;
	}
	public void setStepByTwo(double valueByTwo) {
		this.stepByTwo = valueByTwo;
	}
	public double getAfAxonRefractoryPeriod() {
		return afAxonRefractoryPeriod;
	}
	public void setAfAxonRefractoryPeriod(double afAxonRefractoryPeriod) {
		this.afAxonRefractoryPeriod = afAxonRefractoryPeriod;
	}
	public double getIaInSomaRefractoryPeriod() {
		return iaInSomaRefractoryPeriod;
	}
	public void setIaInSomaRefractoryPeriod(double iaInSomaRefractoryPeriod) {
		this.iaInSomaRefractoryPeriod = iaInSomaRefractoryPeriod;
	}
	public double getIbInSomaRefractoryPeriod() {
		return ibInSomaRefractoryPeriod;
	}
	public void setIbInSomaRefractoryPeriod(double ibInSomaRefractoryPeriod) {
		this.ibInSomaRefractoryPeriod = ibInSomaRefractoryPeriod;
	}
	public double getMnAxonRefractoryPeriod() {
		return mnAxonRefractoryPeriod;
	}
	public void setMnAxonRefractoryPeriod(double mnAxonRefractoryPeriod) {
		this.mnAxonRefractoryPeriod = mnAxonRefractoryPeriod;
	}
	public double getMnSomaRefractoryPeriod() {
		return mnSomaRefractoryPeriod;
	}
	public void setMnSomaRefractoryPeriod(double mnSomaRefractoryPeriod) {
		this.mnSomaRefractoryPeriod = mnSomaRefractoryPeriod;
	}
	public double getRcSomaRefractoryPeriod() {
		return rcSomaRefractoryPeriod;
	}
	public void setRcSomaRefractoryPeriod(double rcSomaRefractoryPeriod) {
		this.rcSomaRefractoryPeriod = rcSomaRefractoryPeriod;
	}
	public double getGIISomaRefractoryPeriod() {
		return gIISomaRefractoryPeriod;
	}
	public void setGIISomaRefractoryPeriod(double gIISomaRefractoryPeriod) {
		this.gIISomaRefractoryPeriod = gIISomaRefractoryPeriod;
	}

}
