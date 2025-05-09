/*
 * Created on 27/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.remoto.model.Neuron;

import br.remoto.model.ModulatingSignal;
import br.remoto.model.ReMoto;
import br.remoto.model.vo.NeuronVO;
import br.remoto.util.Distribution;


/**
 * @author RRCisi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NeuralTract extends Neuron
{
	private static final long serialVersionUID = 1L;

	protected double tTerminalSpike;
	protected boolean lastSpike;
	
	protected double mean;
	protected double std;
	protected String distribution;
	
	protected double thres;
	protected double y_int = 0;
	
	protected ModulatingSignal signal = new ModulatingSignal();
	
	
	public NeuralTract()
	{
	}
	
	
	public NeuralTract(NeuronVO neu, int index, Miscellaneous misc, boolean createSignal)
	{
		super(neu, index, misc);

		this.distribution = neu.getDistribution();
		this.mean = neu.getMean();
		this.std = neu.getStd();
		
		if( createSignal == true )
		{
			signal = new ModulatingSignal();
			
			signal.setAmp( neu.getAmp() );
			signal.setCdSignal( neu.getCdSignal() );
			signal.setFreq( neu.getFreq() );
			signal.setModFreq( neu.getModFreq() );
			signal.setTini( neu.getIni() );
			signal.setTfin( neu.getFin() );
			signal.setModType( neu.getModType() );
			signal.setWidth( neu.getWidth() );
			signal.setDelay( neu.getDelay() );
			signal.setModFactor( neu.getModFactor() );
		}
			
		reset();
	}
	
	
	public void reset()
	{
		super.reset();
		
		thres = Distribution.gammaPoint(1/std, std);
		
		tTerminalSpike = ReMoto.T_MAX;
		lastSpike = false;
		
		// If tract has ISI marked to trigger a spike (mean ISI > 0), calculate the first spike
		if( mean > ReMoto.T_TOLERANCE )
		{
			// Calculate the first spike beyond simulation beginning (t = 0)
			tTerminalSpike = 0;
			addSpike( 0 );
			
			if( signal != null && tTerminalSpike > signal.getTini() )			
			{
				terminalSpikeTrain.remove(0);
				
				tTerminalSpike = signal.getTini();
				addSpike( signal.getTini() );
			}
		}
		else if( signal != null )			
		{
			tTerminalSpike = signal.getTini();
			addSpike( signal.getTini() );
		}
		
		//System.out.println("tTerminalSpike: " + tTerminalSpike + "    mean: " + mean);
		
	}
	

	public void atualize(double t) 
	{
		try
		{
			/*
			// Propagate spike to all post-synaptic neurons
			if( t > tTerminalSpike && lastSpike == false )
			{
				propagateSpike(t);
	
				// If tract is susceptible to trigger a spike
				if( mean > ReMoto.T_TOLERANCE || ( signal != null && t > signal.getTini() && t < signal.getTfin() + ReMoto.T_TOLERANCE ) )
				{
					addSpike(t);
				}
				else
				{
					lastSpike = true;
				}
			}
			*/
			if(distribution.equals(ReMoto.poisson ) ){
				double instantMean = meanValue(t);
				double lambda = 1000 / instantMean;
				
				if( mean > ReMoto.T_TOLERANCE || ( signal != null && t > signal.getTini() && t < signal.getTfin() + ReMoto.T_TOLERANCE ) )
				{
					double probability = lambda * miscellaneous.getStep() / 1000;
					System.out.println("Poisson " + mean);	
					double aux = Math.random();
					
					if(aux <= probability){
							propagateSpike(t);
							tTerminalSpike = t;
							terminalSpikeTrain.add(new Double(tTerminalSpike));
							}
					}
			}
			else if(distribution.equals("Gm" ) ){
				double instantMean = meanValue(t);
				double lambda = 1000 / instantMean;

				if( mean > ReMoto.T_TOLERANCE || ( signal != null && t > signal.getTini() && t < signal.getTfin() + ReMoto.T_TOLERANCE ) )
				{
					y_int = y_int + (lambda * (miscellaneous.getStep() / 1000));
					//System.out.println("Gamma " + mean);
					//Algorithm proposed in Mathematics for Neuroscientists (Gabbiani and Cox, 2010)
					if(y_int >= thres){
						tTerminalSpike = t;
						propagateSpike(t);
						y_int = 0;
						thres = Distribution.gammaPoint(1/std, std);
						terminalSpikeTrain.add(new Double(tTerminalSpike));
						
						//System.out.println(std);
						}
					}
				}
			else if( distribution.equals( ReMoto.gaussian ) ){
				// Propagate spike to all post-synaptic neurons
				if( t > tTerminalSpike && lastSpike == false ){
					propagateSpike(t);
					//System.out.println("Gaussian " + mean);
					// If tract is susceptible to trigger a spike
					if( mean > ReMoto.T_TOLERANCE || ( signal != null && t > signal.getTini() && t < signal.getTfin() + ReMoto.T_TOLERANCE ) ){
						addSpike(t);
						}
					else{
						lastSpike = true;
						}
					}
				}
			}
		catch (Exception e){
			System.out.println( "Error while atualizing TR: " + cd );
			}
		}

	
	protected void addSpike(double t)
	{
		double instantMean = meanValue(t);
		
		if( instantMean < miscellaneous.getStep() && signal == null )
		{
			lastSpike = true;
			return;
		}
		
		if( distribution.equals( ReMoto.gaussian ) )	
			tTerminalSpike += Distribution.gaussianPoint(instantMean, std);

		if( instantMean < miscellaneous.getStep() && signal != null && tTerminalSpike > signal.getTfin() )
		{
			lastSpike = true;
			return;
		}
		
		terminalSpikeTrain.add( new Double(tTerminalSpike) );
		//System.out.println(tTerminalSpike);
	}
	
	
	public double meanValue(double time)
	{
		double ret = mean;
		
		if( signal != null )
		{
			double modulationValue = signal.value(time);
			
			// Variation in firing rate
			if( signal.getModType() != null && signal.getModType().equals( "rate" ) )
			{
				if( mean > miscellaneous.getStep() )
					modulationValue += 1000 / mean; // [Hz]
				
				if( modulationValue > miscellaneous.getStep() || modulationValue < -miscellaneous.getStep() ) 
				{
					ret = 1000 / modulationValue;
					
					if (ret > 2000)
						ret = 2000; // upper limit assumed
				}
				else
					ret = 2000; // upper limit assumed
			}
			// Variation in ISI
			else
			{
				ret += modulationValue;
			}
		}
		
//		if( ret < miscellaneous.getStep() )
		if( ret < 0.5 )

			ret = 0.5; //avoid high frequencies (upper limit = 2 kHz)

		return ret;
	}


	public ModulatingSignal getSignal() {
		return signal;
	}


	public void setSignal(ModulatingSignal signal) {
		this.signal = signal;
	}

}
