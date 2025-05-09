package br.remoto.model;

import java.io.Serializable;


public class Current implements Serializable  
{
	private static final long serialVersionUID = 1L;

	protected boolean active;
	
	protected String cdNucleus;
	protected String cdNeuronType;
	protected String compartment;
	protected double imax;
	protected double step;
	protected int index;

	// For modulating signals
    protected ModulatingSignal signal;

    private String cdSignal;
    private String modType;
    private double ini;
    private double fin;
    private double amp;
    private double freq;
    private double width;
    private double delay;
    private double modFreq;
    private double modFactor;
	
	
	public Current()
	{
		active = false;
	
	}

		
	public Current(String cdNucleus, String cdNeuronType, String compartment, boolean active, double i, double step, ModulatingSignal signal)
	{
		this.cdNucleus = cdNucleus;
		this.cdNeuronType = cdNeuronType;
		this.compartment = compartment;
		this.active = active;
		this.imax = i;
		this.step = step;
		this.signal = signal;
	}
	
	
	public double getCurrent(int iteration)
	{
		return getCurrent(iteration * step);
	}

	
	public double getCurrent(double t)
	{
		if( !active )
			return 0;
		
		double i = imax;
		
		if( signal != null )
		{
			i += signal.value(t);
		}

		return i;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public double getImax() {
		return imax;
	}


	public void setImax(double amp) {
		this.imax = amp;
	}


	public String getCdNeuronType() {
		return cdNeuronType;
	}


	public void setCdNeuronType(String cdNeuronType) {
		this.cdNeuronType = cdNeuronType;
	}


	public String getCdNucleus() {
		return cdNucleus;
	}


	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}


	public double getStep() {
		return step;
	}


	public void setStep(double step) {
		this.step = step;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public String getCompartment() {
		return compartment;
	}


	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}


	public ModulatingSignal getSignal() {
		return signal;
	}


	public void setSignal(ModulatingSignal signal) {
		this.signal = signal;
	}


	public String getCdSignal() {
		return cdSignal;
	}


	public void setCdSignal(String cdSignal) {
		this.cdSignal = cdSignal;
	}


	public double getFin() {
		return fin;
	}


	public void setFin(double fin) {
		this.fin = fin;
	}


	public double getFreq() {
		return freq;
	}


	public void setFreq(double freq) {
		this.freq = freq;
	}


	public double getIni() {
		return ini;
	}


	public void setIni(double ini) {
		this.ini = ini;
	}


	public double getAmp() {
		return amp;
	}


	public void setAmp(double modAmp) {
		this.amp = modAmp;
	}


	public String getModType() {
		return modType;
	}


	public void setModType(String modType) {
		this.modType = modType;
	}


	public double getWidth() {
		return width;
	}


	public void setWidth(double width) {
		this.width = width;
	}
	
	public double getDelay() {
		return delay;
	}
	
	public void setDelay(double delay) {
		this.delay = delay;
	}
	
	public double getModFreq() {
		return modFreq;
	}
	
	public void setModFreq(double modFreq) {
		this.modFreq = modFreq;
	}
	
	public double getModFactor() {
		return modFactor;
	}
	
	public void setModFactor(double modFactor) {
		this.modFactor = modFactor;
	}
	
}