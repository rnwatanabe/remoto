
package br.remoto.model.Neuron;

import java.util.ArrayList;

import br.remoto.model.ReMoto;
import br.remoto.model.vo.NeuronVO;


public class Interneuron extends SpinalNeuron
{
	private static final long serialVersionUID = 1L;

	private double somaRefractoryPeriod;

	
	public Interneuron()
	{
	}
	
	
	public Interneuron(NeuronVO neu, int index, int indexCategory, Miscellaneous misc)
	{
		super(neu, index, misc);
		this.index = indexCategory;

		reset();

		// Set refractory period
		if( type.equals( ReMoto.RC ) )
			somaRefractoryPeriod = miscellaneous.getRcSomaRefractoryPeriod();
		else if( type.equals( ReMoto.IaIn ) )
			somaRefractoryPeriod = miscellaneous.getIaInSomaRefractoryPeriod();
		else if( type.equals( ReMoto.IbIn ) )
			somaRefractoryPeriod = miscellaneous.getIbInSomaRefractoryPeriod();
		else if( type.equals( ReMoto.gII ) )
			somaRefractoryPeriod = miscellaneous.getGIISomaRefractoryPeriod();
	}

	
	public void atualize(double t) 
	{
		try
		{
			super.atualize(t);
	
			atualizeSoma(t);
		}
		catch (Exception e) 
		{
			System.out.println( "Error while atualizing IN: " + cd );
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

			// Verify spike generation
			//if( vs > threshold(t) && t > tSomaSpike )
			if( vs > threshold && t > tSomaSpike + somaRefractoryPeriod )
			{
				addSomaSpike(t);
			}
		}
		catch(Exception e)
		{
			System.out.println( e.getMessage() );
		}
	}

	
	protected double dVs_dt(int slope, double t, double V)
	{
		double iIonic = 0;
		double iSynaptic = 0;
		double iInjected = 0;

		double iLeak = gLeakSoma * (ReMoto.E0 - V);

		iIonic += gNa.getCurrent(slope, t, V);
		iIonic += gKf.getCurrent(slope, t, V);
		iIonic += gKs.getCurrent(slope, t, V);

		// Synapses include all inputs and noises
		iSynaptic += dendExcitSynapses.getCurrent(slope, t, V);
		iSynaptic += dendInhibSynapses.getCurrent(slope, t, V);

		if( current != null )
		{
			iInjected = current.getCurrent(t);
		}

		// Currents in nA - capacitance in nF
		double retorno = (iIonic + iLeak + iSynaptic + iInjected) / capacitanceSoma;

		return retorno;
	}
	
	
	public void addSomaSpike(double t)
	{
		tSomaSpike = t;
		somaSpikeTrain.add(new Double(t));
		
		gNa.start(t);
		gKf.start(t);
		gKs.start(t);
		
		propagateSpike(t);
	}


	/*
	private double tauThreshold = 0.3;
	private double increaseThreshold = 10;
	
	protected double threshold(double t)
	{
		double ret = threshold;
		
		if( type.equals( ReMoto.RC ) && (t - tSomaSpike) < 6 * tauThreshold )
			ret = threshold + ( increaseThreshold * threshold - threshold ) * Math.exp( -(t - tSomaSpike)/tauThreshold );
		
		return ret;
	}
	*/

		
	// For interneurons, terminalSpikeTrain is the same as somaSpikeTrain
	public ArrayList getTerminalSpikeTrain() 
	{
		return somaSpikeTrain;
	}
	
}
