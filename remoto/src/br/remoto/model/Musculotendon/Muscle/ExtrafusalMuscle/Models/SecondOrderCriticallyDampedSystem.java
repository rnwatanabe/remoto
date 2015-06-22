package br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import br.remoto.model.Configuration;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.MotorUnit;
import br.remoto.model.ReMoto;
import br.remoto.util.Conversion;
import br.remoto.util.Sample;
import br.remoto.util.Signal;
import br.remoto.model.Conductance.AlphaFunction;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.ExtrafusalMuscleSuperClass;




public class SecondOrderCriticallyDampedSystem extends ExtrafusalMuscleSuperClass
{
	protected AlphaFunction[] twitchFunction;
	
	private double maximumMuscleForce = 0;
	
	
	
	public SecondOrderCriticallyDampedSystem(Configuration conf, String CdMuscle, MotorUnit[] mu, String cdMuscleModel) {
		super(conf, CdMuscle, mu, cdMuscleModel);
		
		this.twitchFunction = new AlphaFunction[motorunits.length];
		
		for(int i = 0; i < motorunits.length; i++){
			twitchFunction[i] = new AlphaFunction(null, null, null);
			System.out.println(motorunits[i].getTpeak());
			maximumMuscleForce += motorunits[i].getGpeak() * motorunits[i].getTwTet();
		}
		
		samplerLengthNormStore 				= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerVelocityStore 				= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerAccelerationStore 			= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerMotorUnitForceStore 			= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerForceStore 					= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		
		
		System.out.println("Creating SOCDS muscle");
		System.out.println("maximumMuscleForce: " + maximumMuscleForce);
	}
	
	
	
	private double motorUnitForceSaturationFunction(double force, String muType, double b){
		
		double aux = 0;
		
		if(muType.equals(ReMoto.S))
			aux = 2 / (1 + Math.exp(-b * force)) -1;
		else if(muType.equals(ReMoto.FR))
			aux = 2 / (1 + Math.exp(-b * force)) -1;
		else if(muType.equals(ReMoto.FF))
			aux = 2 / (1 + Math.exp(-b * force)) -1;
		
		return aux;
	}
	
	
	public double instantMotorUnitForce(String cdNeuron, double t)
	{
		// Pick mu in hashtable 
		MotorUnit mu = pickMotorUnit(cdNeuron);
		
		double force;
		
        if( mu == null )
        	return 0;
        
        if(!twitchFunction[mu.getIndex() - 1].isStarted()){
        	twitchFunction[mu.getIndex() - 1].setGmax(mu.getGmax());
        	twitchFunction[mu.getIndex() - 1].setTpeak(mu.getTpeak());
        	twitchFunction[mu.getIndex() - 1].reset( mu.getMiscellaneous() );
        }
        
        if( mu.getNumberOfSpikesAtEndPlate() == 0 ){
        	if(mu.isStoredSignals()){
        		
        		samplerMotorUnitForceStore.sample(motorUnitForceStore, String.valueOf(mu.getIndex()), t, 0);
        		//motorUnitForceStore.add( new Signal( String.valueOf(mu.getIndex()), 0, t) );
        	}
            	
        	return 0;
        }
			
        int indexSpike;
    	int iteration;
    	double tSpike;
    	
    	indexSpike = mu.getIndexSpike();
    	iteration = mu.getIteration();
    	tSpike = mu.gettSpike();
    	
    	if(indexSpike < mu.getNumberOfSpikesAtEndPlate() ){
    		
    		tSpike = mu.getEndPlateSpike(indexSpike);
    		mu.settSpike(tSpike);
    		
    		if( t > tSpike ){
    			
            	mu.setIndexSpike(indexSpike + 1);
            	indexSpike = mu.getIndexSpike();
            	
            	twitchFunction[mu.getIndex() - 1].start(iteration);
    		}
    		
    	}
    	
        mu.setIteration(iteration + 1);
        iteration = mu.getIteration();
        
        force = twitchFunction[mu.getIndex() - 1].getValue(iteration);
        
        double output = 0;
        
        if(mu.getType().equals(ReMoto.S)){
        	output = motorUnitForceSaturationFunction(force, mu.getType(), mu.getB()) * mu.getTwTet() * mu.getGpeak();
        }
        else if(mu.getType().equals(ReMoto.FR)){
        	output = motorUnitForceSaturationFunction(force, mu.getType(), mu.getB()) * mu.getTwTet() * mu.getGpeak();
        }
        else if(mu.getType().equals(ReMoto.FF)){
        	output = motorUnitForceSaturationFunction(force, mu.getType(), mu.getB()) * mu.getTwTet() * mu.getGpeak();
        }
        	
        if(mu.isStoredSignals()){
        	samplerMotorUnitForceStore.sample(motorUnitForceStore, String.valueOf(mu.getIndex()), t, output);
        	//motorUnitForceStore.add( new Signal( String.valueOf(mu.getIndex()), output, t) );
        }
        	
        return output;
	}
	
	
	public double instantMuscleForce(double t)
	{
		double output = 0;
		
		for(int n = 0; n < motorunits.length; n++)
		{	
			output += instantMotorUnitForce(motorunits[n].getCd(), t);
		}

		return output;
	}



	@Override
	public void atualize(double t) {
		lengthNorm = calculateLengthNormAux(force, t);
		
		calculateVelocityAndAccelerationUsingDifferentiation(t);
			
		samplerLengthNormStore.sample(lengthNormStore, "", t, lengthNorm);
		samplerVelocityStore.sample(velocityStore, "", t, stretchVelocity);
		samplerAccelerationStore.sample(accelerationStore, "", t, stretchAcceleration);
		
		force = instantMuscleForce(t);
		
		samplerForceStore.sample(forceStore, "", t, force);
		
	}



	@Override
	public void setInitialLengthNorm() {
		// TODO Auto-generated method stub
		
	}
		
	public void setParameters(String cdMuscle, ArrayList muscles){
		
		
	}



	public double getMaximumMuscleForce() {
		return maximumMuscleForce;
	}



	public void setMaximumMuscleForce(double maximumMuscleForce) {
		this.maximumMuscleForce = maximumMuscleForce;
	}

	
}

