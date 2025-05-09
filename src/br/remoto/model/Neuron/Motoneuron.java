
package br.remoto.model.Neuron;

import java.util.ArrayList;

import br.remoto.model.Nerve;
import br.remoto.model.ReMoto;
import br.remoto.model.Conductance.PulseConductance;
import br.remoto.model.vo.NeuronVO;
import br.remoto.util.Signal;


public class Motoneuron extends SpinalNeuron
{
	private static final long serialVersionUID = 1L;

	protected double tAxonSpike;

	//protected double axonThreshold;

	protected double latencyStimulusSpinal;
	protected double latencyStimulusEndPlate;
	protected double latencySpinalEndPlate;

	protected ArrayList orthodromicSpikeTrain;
	protected ArrayList antidromicSpikeTrain;

	protected int indexAntidromicSpike;
	protected int indexOrthodromicSpike;
	
	protected Miscellaneous misc;
	
	protected Nerve nerve;

	protected PulseConductance gCa;
	
	public Motoneuron()
	{
	}
	
	
	public Motoneuron(NeuronVO neu, int index, int indexCategory, Miscellaneous misc)
	{
		super(neu, index, misc);
		this.index = indexCategory;
		
		this.misc = misc;

		reset();
	}

	
	public void reset()
	{
		super.reset();
		
		if( gCa != null )
			gCa.reset();
		
		tAxonSpike = -ReMoto.T_MAX;

		if( orthodromicSpikeTrain != null ) 
			orthodromicSpikeTrain.clear();
		else
			orthodromicSpikeTrain = new ArrayList();
		
		if( antidromicSpikeTrain != null ) 
			antidromicSpikeTrain.clear();
		else
			antidromicSpikeTrain = new ArrayList();
		
		indexAntidromicSpike = 0;
		indexOrthodromicSpike = 0;
	}

	
	public void atualize(double t) 
	{
		try
		{
			super.atualize(t);
	
			// Store signals as marked
			if( storedSignals == true )
			{
				signalStore.add( new Signal(ReMoto.gCa, gCa.getLastValue(), t) );
			}
			
			atualizeDendrite(t);
			atualizeSoma(t);
			atualizeAxon(t);
			
			double tAntidromicSpike = ReMoto.T_MAX;
			double tOrthodromicSpike = -ReMoto.T_MAX;
			
			// Verify antidromic propagation of axonal spikes and collision
			if( indexAntidromicSpike < antidromicSpikeTrain.size() )
			{
				tAntidromicSpike = ((Double)antidromicSpikeTrain.get(indexAntidromicSpike)).doubleValue();
				
				if( t > tAntidromicSpike + latencyStimulusSpinal )
				{
					// Verify collision between soma spike and axon spike
					if( t - tSomaSpike >= miscellaneous.getMnSomaRefractoryPeriod() - ReMoto.T_TOLERANCE )
					{
						addSomaSpike(t);
					}
					
					indexAntidromicSpike++;
				}
			}
	
			// Verify orthodromic propagation of soma spikes and collision
			if( indexOrthodromicSpike < orthodromicSpikeTrain.size() )
			{
				tOrthodromicSpike = ((Double)orthodromicSpikeTrain.get(indexOrthodromicSpike)).doubleValue();
				
				if( t > tOrthodromicSpike + latencyStimulusSpinal )
				{
					// Verify collision between soma spike and axon spike
					if( t - tAxonSpike >= miscellaneous.getMnAxonRefractoryPeriod() - ReMoto.T_TOLERANCE )
					{
						addAxonSpike(t);
					}
					
					indexOrthodromicSpike++;
				}
			}
			
			// Verify collision in the middle of the axon
			if( Math.abs( tAntidromicSpike - tOrthodromicSpike ) < latencyStimulusSpinal )
			{
				// Increase indexes in order to collide both spikes
				indexAntidromicSpike++;
				indexOrthodromicSpike++;
			}
				
		}
		catch (Exception e) 
		{
			System.out.println( "Error while atualizing MN: " + cd );
		}
	}

	
	protected void atualizeDendrite(double t)
	{
		try
		{
			double k1 = dVd_dt( 2, t,									saturate( vd ) );
			double k2 = dVd_dt( 2, t + miscellaneous.getStepByTwo(),	saturate( vd + miscellaneous.getStepByTwo() * k1 ) );
			double k3 = dVd_dt( 2, t + miscellaneous.getStepByTwo(),	saturate( vd + miscellaneous.getStepByTwo() * k2 ) );
			double k4 = dVd_dt( 2, t + miscellaneous.getStep(),			saturate( vd + miscellaneous.getStep() * k3 ) );

			vd = saturate( vd + miscellaneous.getStepBySix() * (k1 + 2*k2 + 2*k3 + k4) );	
		}
		catch(Exception e)
		{
			System.out.println( "MN.atualizeDendrite: " + e.getMessage() );
		}
	}
	
	
	protected void atualizeSoma(double t)
	{
		try
		{
			double k1 = dVs_dt( 2, t,									saturate( vs ) );
			double k2 = dVs_dt( 2, t + miscellaneous.getStepByTwo(),	saturate( vs + miscellaneous.getStepByTwo() * k1 ) );
			double k3 = dVs_dt( 2, t + miscellaneous.getStepByTwo(),	saturate( vs + miscellaneous.getStepByTwo() * k2 ) );
			double k4 = dVs_dt( 2, t + miscellaneous.getStep(),			saturate( vs + miscellaneous.getStep() * k3 ) );
	
			vs = saturate( vs + miscellaneous.getStepBySix() * (k1 + 2*k2 + 2*k3 + k4) );
			
			// Store signals as marked
			/*if( storedSignals == true )
			{
				signalStore.add( new Signal("VsxVdot", (k1 + 2*k2 + 2*k3 + k4)/6, vs) );
			}*/
			
			
			// Verify soma spike generation
			// Verify colision between soma and axon spikes
			if( vs > threshold  && 
					t - tSomaSpike > miscellaneous.getMnSomaRefractoryPeriod() )
			{
				addSomaSpike(t);
				orthodromicSpikeTrain.add( new Double(t) );
			}
			if(miscellaneous.getGammaCa() > 0){
				// Verify PIC activation
				if( vs > thresholdCa && t - tDendritePIC > 0.05 )
				{
					addDendritePIC(t);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println( "MN.atualizeSoma: " + e.getMessage() );
		}
	}

	
	public void atualizeAxon(double t)
	{
		if( nerve == null || nerve.isActive() == false )
			return;
		
		// ??? teste de inibicao reciproca homonima
		//if( cd.equals( "MN S 125" ) || cd.equals( "MN FR 62" ) || cd.equals( "MN FF 62" ) )
		//	return;

		// Verify axon spike generation
		// Verify colision between axon and soma spikes
		if( nerve.getIntensity() > axonThreshold && 
			t - tAxonSpike > miscellaneous.getMnAxonRefractoryPeriod() )
		{
			addAxonSpike(t);
			antidromicSpikeTrain.add( new Double(t) );
		}
	}


	protected double dVd_dt(int slope, double t, double Vd)
	{
		double iIonic = 0;
		double iSynaptic = 0;
		
		if(miscellaneous.getGammaCa() > 0){
			iIonic += gCa.getCurrent(slope, t, Vd) * miscellaneous.getGammaCa();
		}
		
		
		
		// Synapses include all inputs and noises
		iSynaptic += dendExcitSynapses.getCurrent(2, t, Vd);
		iSynaptic += dendInhibSynapses.getCurrent(2, t, Vd);
		
		
		
		double iSoma = gCoupling * (vs - Vd);
		double iLeak = gLeakDend * (ReMoto.E0 - Vd);

		double iInjected = 0;

		if( current != null && ReMoto.dendriteCompartment.equals( current.getCompartment() ) )
		{
			iInjected = current.getCurrent(t);
		}
		
		//System.out.println( "gCoupling " + gCoupling );
		// Currents in nA - capacitance in nF
		double retorno = (iIonic + iSynaptic + iSoma + iLeak + iInjected) / capacitanceDend;
		
		//System.out.println("capacitanceSDend" + capacitanceDend);
		if( storedSignals == true)
		{
			signalStore.add( new Signal("VsxVdot", iInjected, t) );
		}
		
		
		
		return retorno;
	}


	protected double dVs_dt(int slope, double t, double Vs)
	{
		double iIonic = 0;
		double iSynaptic = 0;

		iIonic += gNa.getCurrent(slope, t, Vs);
		iIonic += gKf.getCurrent(slope, t, Vs);
		iIonic += gKs.getCurrent(slope, t, Vs);

		// Synapses include all inputs and noises
		iSynaptic += somaExcitSynapses.getCurrent(2, t, Vs);
		iSynaptic += somaInhibSynapses.getCurrent(2, t, Vs);
		
		double iDendrite = gCoupling * (vd - Vs);
		double iLeak = gLeakSoma * (ReMoto.E0 - Vs);
		
		double iInjected = 0;

		if( current != null && ReMoto.somaCompartment.equals( current.getCompartment() ) )
		{
			iInjected = current.getCurrent(t);
		}
		
		//System.out.println("iInjected" + iInjected);

		// Currents in nA - capacitance in nF
		double result = (iIonic + iSynaptic + iDendrite + iLeak + iInjected) / capacitanceSoma;
		//System.out.println("capacitanceSoma" + capacitanceSoma);
		
		
		return result;
	}

	public void addDendritePIC(double t)
	{
		tDendritePIC = t;
		
		gCa.start(t);
	}
	
	public void addSomaSpike(double t)
	{
		tSomaSpike = t;
		somaSpikeTrain.add(new Double(t));
		
		gNa.start(t);
		gKf.start(t);
		gKs.start(t);
		
		propagateSpike(t);
		
		//System.out.println("SPIKE AT SOMA: " + t);
	}

	
	public void addAxonSpike(double t)
	{
		tAxonSpike = t;
		
		terminalSpikeTrain.add(new Double(t + latencyStimulusEndPlate));
		
		/*
		// Paper Japan
		terminalSpikeTrain.add(new Double(t + latencySpinalEndPlate - latencyStimulusSpinal));
		*/
	}


	public void setLatencies(double  axonVelocity, double stimulusSpinalDistance, double stimulusEndPlateDistance) 
	{
		if( axonVelocity < 0.01 )
			axonVelocity = 0.01;
		
		latencyStimulusSpinal = (stimulusSpinalDistance/axonVelocity) * 1000.0; 
		latencyStimulusEndPlate = (stimulusEndPlateDistance/axonVelocity) * 1000.0;
		latencySpinalEndPlate = ((stimulusSpinalDistance + stimulusEndPlateDistance)/axonVelocity) * 1000.0;
		
		/*
		// Paper Japan
		latencySpinalEndPlate = ((stimulusEndPlateDistance - stimulusSpinalDistance)/axonVelocity) * 1000.0;
		*/
		
		latencyStimulusSpinal = Math.round(latencyStimulusSpinal/misc.getStep())*misc.getStep();
		latencyStimulusEndPlate = Math.round(latencyStimulusEndPlate/misc.getStep())*misc.getStep();
		latencySpinalEndPlate = Math.round(latencySpinalEndPlate/misc.getStep())*misc.getStep();
	}

	
	public double getLatencySpinalEndPlate() {
		return latencySpinalEndPlate;
	}
	

	public void setLatencySpinalEndPlate(double latencySpinalEndPlate) {
		this.latencySpinalEndPlate = latencySpinalEndPlate;
	}


	public double getLatencyStimulusEndPlate() {
		return latencyStimulusEndPlate;
	}


	public void setLatencyStimulusEndPlate(double latencyStimulusEndPlate) {
		this.latencyStimulusEndPlate = latencyStimulusEndPlate;
	}


	public double getLatencyStimulusSpinal() {
		return latencyStimulusSpinal;
	}


	public void setLatencyStimulusSpinal(double latencyStimulusSpinal) {
		this.latencyStimulusSpinal = latencyStimulusSpinal;
	}


	public double getAxonThreshold() {
		return axonThreshold;
	}


	public void setAxonThreshold(double axonThreshold) {
		this.axonThreshold = axonThreshold;
	}


	public Nerve getNerve() {
		return nerve;
	}


	public void setNerve(Nerve nerve) {
		this.nerve = nerve;
	}

	public PulseConductance getGCa() {
		return gCa;
	}
	
	public void setGCa(PulseConductance gCa){
		this.gCa = gCa;
	}
	
	/*
	public double getAxonConductionVelocity() {
		double stimulusSpinalDistance = nerve.getStimulusSpinalEntry();
		return (stimulusSpinalDistance/latencyStimulusSpinal) * 1000.0; 
	}
	*/
}
