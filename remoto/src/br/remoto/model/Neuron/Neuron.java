
package br.remoto.model.Neuron;

import java.util.ArrayList;
import java.util.List;

import br.remoto.model.Conductance.MultSynapticConductance;
import br.remoto.model.vo.NeuronVO;


public abstract class Neuron extends NeuronProperties
{
	protected String cd;
	protected Miscellaneous miscellaneous;
	
	// Main spike train
	protected ArrayList terminalSpikeTrain = new ArrayList();
	
	// Synapses made by this neuron
	protected List transmittingSynapses = new ArrayList();
	
	// For some neurons, store internal signals
	protected boolean storedSignals;
	
	// Slice and region in the rostro-caudal axis (placing procedure)
	protected double xPosition;
	
	protected double axonThreshold;
	protected double axonConductionVelocity;
	
	public abstract void atualize(double t);
	
	
	public Neuron()
	{
	}
	
	
	public Neuron(NeuronVO neu, int index, Miscellaneous misc)
	{
		this();
		
		this.category = neu.getCategory();
		this.type = neu.getType();
		this.cdNucleus = neu.getCdNucleus();
		this.active = neu.isActive();
		this.index = index;
		this.miscellaneous = misc;
		this.cd = category + " " + type + " " + (index + 1);
	}
	

	public void reset()
	{
		if( terminalSpikeTrain != null )
			terminalSpikeTrain.clear();
		else
			terminalSpikeTrain = new ArrayList();
	}
	
		
	protected void propagateSpike(double t)
	{
		// Start all post-synaptic conductances 

		for(int k = 0; k < transmittingSynapses.size(); k++)
		{
			((MultSynapticConductance)transmittingSynapses.get(k)).receiveSpike(t, cdNucleus + cd);
		}
	}
		
	
	public void ensureCapacity(int size)
	{
		terminalSpikeTrain.ensureCapacity(size);
	}

	
	public String getCd() {
		return cd;
	}


	public void setCd(String cd) {
		this.cd = cd;
	}


	public List getTransmittingSynapses() {
		return transmittingSynapses;
	}


	public void setTransmittingSynapses(List posSynapses) {
		this.transmittingSynapses = posSynapses;
	}


	public Miscellaneous getMiscellaneous() {
		return miscellaneous;
	}

	
	public boolean isStoredSignals() {
		return storedSignals;
	}


	public void setStoredSignals(boolean storeVM) {
		this.storedSignals = storeVM;
	}


	public double getXPosition() {
		return xPosition;
	}


	public void setXPosition(double position) {
		xPosition = position;
	}


	public ArrayList getTerminalSpikeTrain() {
		return terminalSpikeTrain;
	}

	
	public void setTerminalSpikeTrain(ArrayList terminalSpikeTrain) {
		this.terminalSpikeTrain = terminalSpikeTrain;
	}


	public void setMiscellaneous(Miscellaneous misc) {
		this.miscellaneous = misc;
	}
	
	
	public double getAxonThreshold() {
		return axonThreshold;
	}


	public void setAxonThreshold(double axonThreshold) {
		this.axonThreshold = axonThreshold;
	}


	public double getAxonConductionVelocity() {
		return axonConductionVelocity;
	}


	public void setAxonConductionVelocity(double axonConductionVelocity) {
		this.axonConductionVelocity = axonConductionVelocity;
	}


}
