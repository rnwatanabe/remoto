package br.remoto.model.vo;

import java.io.Serializable;

import br.remoto.model.ModulatingSignal;

public class NerveVO implements Serializable 
{
	private static final long serialVersionUID = 1L;

	protected String cdNerve;
	protected String cdJoint;
	protected double tini;
    protected double tfin;
    protected double amp;
    protected double freq;
	protected double stimulusSpinalEntry;
	protected double stimulusEndPlate;    
	protected boolean active;

	protected boolean paired;
	protected double ampPaired;
	protected double delayPaired;
    protected double freqPaired;

	protected int index;
	
    protected ModulatingSignal signal;

	protected String cdSignal;
    protected double modulating_tini;
    protected double modulating_tfin;
	protected double modulating_amp;
	protected double modulating_freq;
	protected double width;
    protected double delay;
    //----------------------------------
	
	public NerveVO()
	{
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public double getAmp() {
		return amp;
	}


	public void setAmp(double amp) {
		this.amp = amp;
	}


	public String getCdNerve() {
		return cdNerve;
	}


	public void setCdNerve(String cdNerve) {
		this.cdNerve = cdNerve;
	}


	public double getFreq() {
		return freq;
	}


	public void setFreq(double freq) {
		this.freq = freq;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public double getStimulusEndPlate() {
		return stimulusEndPlate;
	}


	public void setStimulusEndPlate(double stimulusEndPlate) {
		this.stimulusEndPlate = stimulusEndPlate;
	}


	public double getStimulusSpinalEntry() {
		return stimulusSpinalEntry;
	}


	public void setStimulusSpinalEntry(double stimulusSpinalEntry) {
		this.stimulusSpinalEntry = stimulusSpinalEntry;
	}


	public double getTfin() {
		return tfin;
	}


	public void setTfin(double tfin) {
		this.tfin = tfin;
	}


	public double getTini() {
		return tini;
	}


	public void setTini(double tini) {
		this.tini = tini;
	}
	


	public boolean isPaired() {
		return paired;
	}


	public void setPaired(boolean paired) {
		this.paired = paired;
	}


	public double getAmpPaired() {
		return ampPaired;
	}


	public void setAmpPaired(double ampPaired) {
		this.ampPaired = ampPaired;
	}


	public double getDelayPaired() {
		return delayPaired;
	}


	public void setDelayPaired(double delayPaired) {
		this.delayPaired = delayPaired;
	}


	public double getFreqPaired() {
		return freqPaired;
	}


	public void setFreqPaired(double freqPaired) {
		this.freqPaired = freqPaired;
	}

	public double getModulating_freq() {
		return modulating_freq;
	}


	public void setModulating_freq(double modulating_freq) {
		this.modulating_freq = modulating_freq;
	}


	public double getDelay() {
		return delay;
	}


	public void setDelay(double delay) {
		this.delay = delay;
	}


	//Created by Vitor Chaud in 19/05/11
	//----------------------------------
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


	public double getModulating_amp() {
		return modulating_amp;
	}


	public void setModulating_amp(double modulating_amp) {
		this.modulating_amp = modulating_amp;
	}


	public double getModulating_tini() {
		return modulating_tini;
	}


	public void setModulating_tini(double modulating_tini) {
		this.modulating_tini = modulating_tini;
	}


	public double getModulating_tfin() {
		return modulating_tfin;
	}


	public void setModulating_tfin(double modulating_tfin) {
		this.modulating_tfin = modulating_tfin;
	}


	public double getWidth() {
		return width;
	}


	public void setWidth(double width) {
		this.width = width;
	}
	//----------------------------------


	public String getCdJoint() {
		return cdJoint;
	}


	public void setCdJoint(String cdJoint) {
		this.cdJoint = cdJoint;
	}
	
}
