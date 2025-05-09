/*
 * Created on 27/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.remoto.model.Neuron;

import java.util.ArrayList;

import br.remoto.model.Nerve;
import br.remoto.model.ReMoto;
import br.remoto.model.Proprioceptors.GolgiTendonOrgan;
import br.remoto.model.Proprioceptors.MuscleSpindle;
import br.remoto.model.Proprioceptors.Vestibular;
import br.remoto.model.vo.NeuronVO;
import br.remoto.util.Distribution;


public class SensoryFiber extends Neuron
{
	private static final long serialVersionUID = 1L;

	protected double tAxonSpike;

	protected double nextSpike;
	protected int indexSpike;

	//protected double axonThreshold;
	protected double latencyStimulusSpinal;
	protected double latencyStimulusEndPlate;
	
	protected ArrayList axonSpikeTrain = new ArrayList();

	protected Nerve nerve;

	protected GolgiTendonOrgan gto;
	protected MuscleSpindle spindle;
	protected Vestibular vestibular;
	
	private int index;
	
	protected double recruitmentThreshold;
	
	protected double gammaOrderIa = 25;
	protected double thresIa;
	protected double y_intIa = 0;

	protected double gammaOrderIb = 30;
	protected double thresIb;
	protected double y_intIb = 0;
	
	protected double gammaOrderII = 400;
	protected double thresII;
	protected double y_intII = 0;
	
	
	
	public SensoryFiber()
	{
	}
	
	
	public SensoryFiber(NeuronVO neu, int index, Miscellaneous misc)
	{
		super(neu, index, misc);
		this.index = index;
		
		reset();
	}
	
	
	public void reset()
	{
		super.reset();
		
		thresIa = Distribution.gammaPoint(1/gammaOrderIa, gammaOrderIa);
		thresIb = Distribution.gammaPoint(1/gammaOrderIb, gammaOrderIb);
		thresII = Distribution.gammaPoint(1/gammaOrderII, gammaOrderII);

		if( axonSpikeTrain != null )
			axonSpikeTrain.clear();
		else
			axonSpikeTrain = new ArrayList();

		tAxonSpike = -ReMoto.T_MAX;

		nextSpike = -ReMoto.T_MAX;
		indexSpike = 0;
	}
		
	
	public void ensureCapacity(int size)
	{
		super.ensureCapacity(size);
		
		axonSpikeTrain.ensureCapacity(size);
	}
	

	public void atualize(double t) 
	{
		
		try
		{
			// Propagate spike to all post-synaptic neurons
			if( indexSpike < axonSpikeTrain.size() )
			{
				nextSpike = ((Double)axonSpikeTrain.get(indexSpike)).doubleValue() + latencyStimulusSpinal;
	
				if( t > nextSpike )
				{
					propagateSpike(t);
					indexSpike++;
					
					terminalSpikeTrain.add( new Double(t) );
				}
			}
	
			// If stimulus is greater than threshold and fiber is not in refractory period
			if( nerve.getIntensity() > axonThreshold && t - tAxonSpike > miscellaneous.getAfAxonRefractoryPeriod() )
			{
				tAxonSpike = t;
				axonSpikeTrain.add( new Double(t) );
			}
			
			if(getType().equals(ReMoto.Ib) && t - tAxonSpike > miscellaneous.getAfAxonRefractoryPeriod()){
				
				
				double IbFiringRate = gto.getIbFiringRate();
				
				double minimalFiring = 5;
				
				if(IbFiringRate > this.recruitmentThreshold){
					double lambdaIb = (IbFiringRate - recruitmentThreshold) + Distribution.gaussianPoint(minimalFiring, 0.1*minimalFiring);
					
					y_intIb = y_intIb + (lambdaIb * (miscellaneous.getStep() / 1000));
					
					//Algorithm proposed in Mathematics for Neuroscientists (Gabbiani and Cox, 2010)
					if(y_intIb >= thresIb){
						tAxonSpike = t + latencyStimulusEndPlate;
						y_intIb = 0;
						thresIb = Distribution.gammaPoint(1/gammaOrderIb, gammaOrderIb);
						axonSpikeTrain.add(new Double(tAxonSpike));
						}
					}		
			}	
			
			if(getType().equals(ReMoto.Ia) && t - tAxonSpike > miscellaneous.getAfAxonRefractoryPeriod()){
				
				/*
				// Paper Japan
				double lambdaIa = 1000.0 / 5;
					
				y_intIa = y_intIa + (lambdaIa * (miscellaneous.getStep() / 1000));
					
				//Algorithm proposed in Mathematics for Neuroscientists (Gabbiani and Cox, 2010)
				if(y_intIa >= thresIa){
					tAxonSpike = t;
					y_intIa = 0;
					thresIa = Distribution.gammaPoint(1/gammaOrderIa, gammaOrderIa);
					axonSpikeTrain.add(new Double(tAxonSpike));
					}
				*/

				// Default code
				double IaFiringRate = spindle.getIaFiringRate();
						
				//System.out.println("  IaFiringRate: " + IaFiringRate );
				
				double minimalFiring = 5;
				
				if(IaFiringRate > this.recruitmentThreshold){
					double lambdaIa = (IaFiringRate - recruitmentThreshold) + Distribution.gaussianPoint(minimalFiring, 0.1*minimalFiring);
					
					y_intIa = y_intIa + (lambdaIa * (miscellaneous.getStep() / 1000));
					
					//Algorithm proposed in Mathematics for Neuroscientists (Gabbiani and Cox, 2010)
					if(y_intIa >= thresIa){
						tAxonSpike = t + latencyStimulusEndPlate;
						y_intIa = 0;
						thresIa = Distribution.gammaPoint(1/gammaOrderIa, gammaOrderIa);
						axonSpikeTrain.add(new Double(tAxonSpike));
						}
					}
			}
			
			if(getType().equals(ReMoto.II) && t - tAxonSpike > miscellaneous.getAfAxonRefractoryPeriod()){
				
				
				// Paper Japan
				/*
				double lambdaII = 1000.0 / 40;
					
				y_intII = y_intII + (lambdaII * (miscellaneous.getStep() / 1000));
					
				//Algorithm proposed in Mathematics for Neuroscientists (Gabbiani and Cox, 2010)
				if(y_intII >= thresII){
					tAxonSpike = t;
					y_intII = 0;
					thresII = Distribution.gammaPoint(1/gammaOrderIa, gammaOrderIa);
					axonSpikeTrain.add(new Double(tAxonSpike));
					}
				*/
				
				// Default code
				double IIFiringRate = spindle.getIIFiringRate();
				
				double minimalFiring = 5;
				
				if(IIFiringRate > this.recruitmentThreshold){
					double lambdaII = (IIFiringRate - recruitmentThreshold) + Distribution.gaussianPoint(minimalFiring, 0.1*minimalFiring);
					
					y_intII = y_intII + (lambdaII * (miscellaneous.getStep() / 1000));
					
					//Algorithm proposed in Mathematics for Neuroscientists (Gabbiani and Cox, 2010)
					if(y_intII >= thresII){
						tAxonSpike = t + latencyStimulusEndPlate;
						y_intII = 0;
						thresII = Distribution.gammaPoint(1/gammaOrderIa, gammaOrderIa);
						axonSpikeTrain.add(new Double(tAxonSpike));
						}
					}
			}
			
			
		}
		catch (Exception e) 
		{
			System.out.println( "Error while atualizing AF: " + cd );
		}
	}
	
	public void setLatency(double axonVelocity, double stimulusSpinalDistance, double stimulusEndPlateDistance) 
	{
		if( axonVelocity < 0.01 )
			axonVelocity = 0.01;
		
		latencyStimulusSpinal = (stimulusSpinalDistance/axonVelocity) * 1000.0; 
		latencyStimulusEndPlate = (stimulusEndPlateDistance/axonVelocity) * 1000.0;
	
		latencyStimulusSpinal = Math.round(latencyStimulusSpinal/miscellaneous.getStep())* miscellaneous.getStep();
		latencyStimulusEndPlate = Math.round(latencyStimulusEndPlate/miscellaneous.getStep())* miscellaneous.getStep();
		
	}


	public int getIndexSpike() {
		return indexSpike;
	}


	public void setIndexSpike(int indexSpike) {
		this.indexSpike = indexSpike;
	}


	public double getLatencyStimulusSpinal() {
		return latencyStimulusSpinal;
	}


	public void setLatencyStimulusSpinal(double latencyStimulusSpinal) {
		this.latencyStimulusSpinal = latencyStimulusSpinal;
	}


	public double getNextSpike() {
		return nextSpike;
	}


	public void setNextSpike(double nextSpike) {
		this.nextSpike = nextSpike;
	}


	public ArrayList getAxonSpikeTrain() {
		return axonSpikeTrain;
	}


	public void setAxonSpikeTrain(ArrayList axonSpikeTrain) {
		this.axonSpikeTrain = axonSpikeTrain;
	}


	


	public Nerve getNerve() {
		return nerve;
	}


	public void setNerve(Nerve nerve) {
		this.nerve = nerve;
	}


	public void setGTO(GolgiTendonOrgan golgiTendonOrgan) {
		gto = golgiTendonOrgan;
	}


	public MuscleSpindle getSpindle() {
		return spindle;
	}


	public void setSpindle(MuscleSpindle spindle) {
		this.spindle = spindle;
	}
	
	public void setVestibular(Vestibular vestibular) {
		this.vestibular = vestibular;
	}


	public double getRecruitmentThreshold() {
		return recruitmentThreshold;
	}

	public void setRecruitmentThreshold(double recruitmentThreshold) {
		this.recruitmentThreshold = recruitmentThreshold;
	}
	
	/*
	public double getAxonConductionVelocity() {
		double stimulusSpinalDistance = nerve.getStimulusSpinalEntry();
		return (stimulusSpinalDistance/latencyStimulusSpinal) * 1000.0; 
	}
	*/
}
