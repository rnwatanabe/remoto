
package br.remoto.model.Neuron;

import java.util.ArrayList;

import br.remoto.model.Current;
import br.remoto.model.ReMoto;
import br.remoto.model.Conductance.MultSynapticConductance;
import br.remoto.model.Conductance.PulseConductance;
import br.remoto.model.vo.NeuronVO;
import br.remoto.util.Signal;

//Modified in 05-10-11 by L. A. Elias

public abstract class SpinalNeuron extends Neuron
{
	protected double threshold;
	protected double rn;
	protected double thresholdCa;
	
	protected ArrayList somaSpikeTrain = new ArrayList();
	protected ArrayList signalStore = new ArrayList();

	protected double tSomaSpike;
	protected double tDendritePIC;

	// Ionic conductances
	protected PulseConductance gKf;
	protected PulseConductance gKs;
	protected PulseConductance gNa;
	

	// Injected current
	protected Current current;

	// Electrotonic properties
	protected double vs;
	protected double vd;
	protected double capacitanceSoma;
	protected double capacitanceDend;
	protected double gLeakSoma;
	protected double gLeakDend;
	protected double gCoupling;
	
	// Synapses which this neuron receives
	protected MultSynapticConductance dendExcitSynapses; 
	protected MultSynapticConductance dendInhibSynapses; 
	protected MultSynapticConductance somaExcitSynapses; 
	protected MultSynapticConductance somaInhibSynapses; 

	
	public SpinalNeuron()
	{
		super();

		storedSignals = false;
	}
	
	
	public SpinalNeuron(NeuronVO neu, int index, Miscellaneous misc)
	{
		super(neu, index, misc);
	}
	
		
	public void reset()
	{
		super.reset();
		
		if( somaSpikeTrain != null )
			somaSpikeTrain.clear();
		else
			somaSpikeTrain = new ArrayList();
		
		signalStore.clear();

		tSomaSpike = -ReMoto.T_MAX;
		tDendritePIC = -ReMoto.T_MAX;

		vs = 0;
		vd = 0;

		if( gKf != null )
			gKf.reset();

		if( gKs != null )
			gKs.reset();

		if( gNa != null )
			gNa.reset();
		
		
		
		// rcisi@27mai2009 - error on keep the same...
		//current = null;
	}		
		
	
	public void ensureCapacity(int size)
	{
		super.ensureCapacity(size);
		
		somaSpikeTrain.ensureCapacity(size);
	}

		
	public void atualize(double t) 
	{
		// Store signals as marked
		if( storedSignals == true )
		{
			signalStore.add( new Signal(ReMoto.Vd, vd, t) );
			signalStore.add( new Signal(ReMoto.Vs, vs, t) );

			signalStore.add( new Signal(ReMoto.gNa, gNa.getLastValue(), t) );
			signalStore.add( new Signal(ReMoto.gKf, gKf.getLastValue(), t) );
			signalStore.add( new Signal(ReMoto.gKs, gKs.getLastValue(), t) );
			
			if( dendExcitSynapses != null ) signalStore.add( new Signal(ReMoto.gExcDend, dendExcitSynapses.getLastValue(), t) );
			if( dendInhibSynapses != null ) signalStore.add( new Signal(ReMoto.gInibDend, dendInhibSynapses.getLastValue(), t) );
			if( somaExcitSynapses != null ) signalStore.add( new Signal(ReMoto.gExcSoma, somaExcitSynapses.getLastValue(), t) );
			if( somaInhibSynapses != null ) signalStore.add( new Signal(ReMoto.gInibSoma, somaInhibSynapses.getLastValue(), t) );
		}
	}

	
	protected double saturate(double var)  // limita o valor de E a P0 ou EsE
	{
		if( var < ReMoto.P0 )
			return ReMoto.P0;
		else if( var > ReMoto.EsE )
			return ReMoto.EsE;
		else
			return var;
	}


	public double getCapacitanceDend() {
		return capacitanceDend;
	}


	public void setCapacitanceDend(double capacitanceDend) {
		this.capacitanceDend = capacitanceDend;
	}


	public double getCapacitanceSoma() {
		return capacitanceSoma;
	}


	public void setCapacitanceSoma(double capacitanceSoma) {
		this.capacitanceSoma = capacitanceSoma;
	}


	public double getGCoupling() {
		return gCoupling;
	}


	public void setGCoupling(double coupling) {
		gCoupling = coupling;
	}


	public double getGLeakDend() {
		return gLeakDend;
	}


	public void setGLeakDend(double leakDend) {
		gLeakDend = leakDend;
	}


	public double getGLeakSoma() {
		return gLeakSoma;
	}


	public void setGLeakSoma(double leakSoma) {
		gLeakSoma = leakSoma;
	}


	public Current getCurrent() {
		return current;
	}


	public void setCurrent(Current current) {
		this.current = current;
	}


	public double getThreshold() {
		return threshold;
	}


	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public double getThresholdCa() {
		return thresholdCa;
	}

	public void setThresholdCa(double thresholdCa) {
		this.thresholdCa = thresholdCa;
	}
	
	public double getVd() {
		return vd;
	}


	public void setVd(double vd) {
		this.vd = vd;
	}


	public double getVs() {
		return vs;
	}


	public void setVs(double vs) {
		this.vs = vs;
	}


	public PulseConductance getGKf() {
		return gKf;
	}


	public void setGKf(PulseConductance gK) {
		this.gKf = gK;
	}


	public PulseConductance getGNa() {
		return gNa;
	}


	public void setGNa(PulseConductance gNa) {
		this.gNa = gNa;
	}


	public PulseConductance getGKs() {
		return gKs;
	}


	public void setGKs(PulseConductance ks) {
		gKs = ks;
	}

	public MultSynapticConductance getDendInhibSynapses() {
		return dendInhibSynapses;
	}


	public void setDendInhibSynapses(MultSynapticConductance inhibPre) {
		this.dendInhibSynapses = inhibPre;
	}

	
	public ArrayList getSomaSpikeTrain() {
		return somaSpikeTrain;
	}


	public void setSomaSpikeTrain(ArrayList spikeTrain) {
		this.somaSpikeTrain = spikeTrain;
	}
	
	
	public ArrayList getSignalStore() {
		return signalStore;
	}


	public void setSignalStore(ArrayList signalStore) {
		this.signalStore = signalStore;
	}


	public double getTSomaSpike() {
		return tSomaSpike;
	}


	public void setTSomaSpike(double somaSpike) {
		tSomaSpike = somaSpike;
	}
	
	public double getTDendritePIC() {
		return tDendritePIC;
	}

	public void setTDendriteSpike(double dendritePIC) {
		tDendritePIC = dendritePIC;		
	}

	public MultSynapticConductance getDendExcitSynapses() {
		return dendExcitSynapses;
	}


	public void setDendExcitSynapses(MultSynapticConductance excitPre) {
		this.dendExcitSynapses = excitPre;
	}


	public MultSynapticConductance getSomaExcitSynapses() {
		return somaExcitSynapses;
	}


	public void setSomaExcitSynapses(MultSynapticConductance excitPreSoma) {
		this.somaExcitSynapses = excitPreSoma;
	}


	public MultSynapticConductance getSomaInhibSynapses() {
		return somaInhibSynapses;
	}


	public void setSomaInhibSynapses(MultSynapticConductance inhibPreSoma) {
		this.somaInhibSynapses = inhibPreSoma;
	}


	public double getRn() {
		return rn;
	}


	public void setRn(double rn) {
		this.rn = rn;
	}
	
}
