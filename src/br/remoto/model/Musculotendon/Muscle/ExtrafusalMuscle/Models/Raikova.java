package br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import br.remoto.model.Configuration;
import br.remoto.model.MotorUnit;
import br.remoto.model.ReMoto;
import br.remoto.util.Conversion;
import br.remoto.util.Sample;
import br.remoto.util.Signal;
import br.remoto.model.Conductance.AlphaFunction;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.ExtrafusalMuscleSuperClass;




public class Raikova extends ExtrafusalMuscleSuperClass
{
	
	private double[] k;
	private double[] p;
	private double[] m;
	
	private double Tc; //[ms]
	private double Thr; //[ms]

	private double delay[];
		
	public Raikova(Configuration conf, String CdMuscle, MotorUnit[] mu, String cdMuscleModel) {
		super(conf, CdMuscle, mu, cdMuscleModel);
				
		samplerMotorUnitForceStore 	= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerForceStore 	= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerLengthNormStore 				= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerVelocityStore 				= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerAccelerationStore 			= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		
		this.k = new double[motorunits.length];
		this.p= new double[motorunits.length];
		this.m = new double[motorunits.length];
		this.delay = new double[motorunits.length];
				
		for(int i = 0; i < motorunits.length; i++){
			
			Tc = motorunits[i].getTpeakRaikova();
			Thr = motorunits[i].getTHalfRaikova();
			
			System.out.println("Tc: " + Tc + "   Thr: " + Thr + "   Gpeak: " + motorunits[i].getGpeakRaikova() + " i " + i + " Index " + motorunits[i].getIndex());
			
			int Index = motorunits[i].getIndex() - 1; 
			
			k[Index] = Math.log(2)/(-Tc * Math.log(Thr/Tc) + Thr - Tc);
			
			//p[i] = motorunits[i].getGpeakRaikova() * Math.exp(-k[i] * Tc * (Math.log(Tc) - 1));
			
			p[Index] = Math.exp(-k[Index] * Tc * (Math.log(Tc) - 1));
						
			m[Index] = k[Index] * Tc;
			
			delay[Index] = motorunits[i].getLatencyStimulusEndPlate();
		}
		
		System.out.println("Creating Raikova muscle");
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
	

	public double instantMuscleForce(double t)
	{
		double output = 0;
		
		for(int n = 0; n < motorunits.length; n++)
		{
			output += instantMotorUnitForce(motorunits[n].getCd(), t);
			
		}

		return output;
	}

	
	public double instantMotorUnitForce(String cdNeuron, double t)
	{
		// Pick mu in hashtable 
		MotorUnit mu = pickMotorUnit(cdNeuron);
				
		double force = 0;
		double output = 0;
    
		if( mu == null )
        	return 0;
        
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
    		}
    		
    	}
    	
        mu.setIteration(iteration + 1);
        iteration = mu.getIteration();
        
    	for(int k = 0; k < mu.getNumberOfSpikesAtEndPlate(); k++){
			
				if(t >= mu.getEndPlateSpike(k) && t - tSpike < 10.0 * mu.getTHalfRaikova()){
					force += raikovaFunction(t - mu.getEndPlateSpike(k), mu.getIndex());
					if(mu.getType().equals(ReMoto.S))
				        output = motorUnitForceSaturationFunction(force, mu.getType(), mu.getBRaikova()) * mu.getTwTetRaikova() * mu.getGpeakRaikova();
						//output = force;
					else if(mu.getType().equals(ReMoto.FR))
				        output = motorUnitForceSaturationFunction(force, mu.getType(), mu.getBRaikova()) * mu.getTwTetRaikova() * mu.getGpeakRaikova();
				        //output = force;
				    else if(mu.getType().equals(ReMoto.FF))
				        output = motorUnitForceSaturationFunction(force, mu.getType(), mu.getBRaikova()) * mu.getTwTetRaikova() * mu.getGpeakRaikova();
				        //output = force;
				}
				//System.out.println(mu.getCd() + "B " +  mu.getBRaikova() + " Tc " + mu.getTpeakRaikova() + " Index " + mu.getIndex());
    	}
    	
    	if(mu.isStoredSignals()){
    		samplerMotorUnitForceStore.sample(motorUnitForceStore, String.valueOf(mu.getIndex()), t, output);
			//motorUnitForceStore.add( new Signal( String.valueOf(mu.getIndex()), output, t) );
    	}
    	
    	return output;
    	
	}
	
	private double raikovaFunction(double t, int motorUnitIndex) {

		//System.out.println(motorUnitIndex);
		return p[motorUnitIndex -1] * Math.pow(t, m[motorUnitIndex - 1]) * Math.exp(-k[motorUnitIndex - 1] * t);
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

	@Override
	public double getMaximumMuscleForce() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
