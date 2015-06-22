package br.remoto.model.vo;

import java.io.Serializable;

public class Summary implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private String nucleus;
	private String neuronType;
	private String cd;
	private int numSpikes;
	private int index;
	private double rateFirstInterval;
	private double rateSteadyState;
	private double[] firingRates = new double[3];
	private double gMaxExc; 
	private double gMaxInh;
	private double threshold; 
	private double inputResistance;
	private int numberOfSpikesReceivedExc;
	private int numberOfSpikesReceivedInh;
	
	public Summary( String nucleus, 
					String neuronType, 
					String cd, 
					int index, 
					int numSpikes, 
					double rateFirstInterval, 
					double rateSteadyState, 
					double[] firingRates, 
					double gMaxExc, 
					double gMaxInh, 
					int numberOfSpikesReceivedExc, 
					int numberOfSpikesReceivedInh,
					double threshold, 
					double inputResistance)
	{
		this.nucleus = nucleus;
		this.neuronType = neuronType;
		this.cd = cd;
		this.numSpikes = numSpikes;
		this.index = index;
		this.rateFirstInterval = rateFirstInterval;
		this.rateSteadyState = rateSteadyState;
		this.firingRates = firingRates;
		this.gMaxExc = gMaxExc;
		this.gMaxInh = gMaxInh;
		this.numberOfSpikesReceivedExc = numberOfSpikesReceivedExc;
		this.numberOfSpikesReceivedInh = numberOfSpikesReceivedInh;
		this.threshold = threshold;
		this.inputResistance = inputResistance;
	}
	
	public int getNumSpikes() {
		return numSpikes;
	}
	public void setNumSpikes(int numSpikes) {
		this.numSpikes = numSpikes;
	}
	public double getRateFirstInterval() {
		return rateFirstInterval;
	}
	public void setRateFirstInterval(double rateFirstInterval) {
		this.rateFirstInterval = rateFirstInterval;
	}
	public String getNeuronType() {
		return neuronType;
	}
	public void setNeuronType(String neuronType) {
		this.neuronType = neuronType;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getNucleus() {
		return nucleus;
	}
	public void setNucleus(String nucleus) {
		this.nucleus = nucleus;
	}
	public double getRateSteadyState() {
		return rateSteadyState;
	}
	public void setRateSteadyState(double rateSteadyState) {
		this.rateSteadyState = rateSteadyState;
	}
	public double[] getFiringRates() {
		return firingRates;
	}
	public void setFiringRates(double[] firingRates) {
		this.firingRates = firingRates;
	}
	public double getGMaxExc() {
		return gMaxExc;
	}
	public void setGMaxExc(double maxExc) {
		gMaxExc = maxExc;
	}
	public double getGMaxInh() {
		return gMaxInh;
	}
	public void setGMaxInh(double maxInh) {
		gMaxInh = maxInh;
	}
	public int getNumberOfSpikesReceivedExc() {
		return numberOfSpikesReceivedExc;
	}
	public void setNumberOfSpikesReceivedExc(int numberOfSpikesReceived) {
		this.numberOfSpikesReceivedExc = numberOfSpikesReceived;
	}
	public int getNumberOfSpikesReceivedInh() {
		return numberOfSpikesReceivedInh;
	}
	public void setNumberOfSpikesReceivedInh(int numberOfSpikesReceivedInh) {
		this.numberOfSpikesReceivedInh = numberOfSpikesReceivedInh;
	}
	public double getInputResistance() {
		return inputResistance;
	}
	public void setInputResistance(double inputResistance) {
		this.inputResistance = inputResistance;
	}
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

}
