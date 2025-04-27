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
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.ExtrafusalMuscleSuperClass;
import br.remoto.model.Musculotendon.Tendon.NonInnerveted.NonInnervatedTendon;
import br.remoto.model.vo.MuscleVO;



public class DistributionMoments extends ExtrafusalMuscleSuperClass
{

	private double tau1 = 20;
	private double tau2 = 15;
	private double delay[];
	
	public DistributionMoments(Configuration conf, String CdMuscle, MotorUnit[] mu, String cdMuscleModel) {
		super(conf, CdMuscle, mu, cdMuscleModel);
		
		this.delay = new double[motorunits.length];
		
		for(int i = 0; i < motorunits.length; i++){
			//activationFunction[i] = new AlphaFunction(null, null, null);
			delay[i] = motorunits[i].getLatencyStimulusEndPlate();
		}
		
		samplerMotorUnitForceStore 	= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerForceStore 	= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		
		System.out.println("Creating Distributed Moments muscle");
	}
	
	
	
	
	
	public double instantActivation(double t, String mu_type)
	{
		double output = 0;
		double outputI = 0;
		double outputII = 0;
		
		for(int n = 0; n < motorunits.length; n++)
		{
			//System.out.println("motorunits[n].getLatencyStimulusEndPlate(): " + motorunits[n].getLatencyStimulusEndPlate());
			if(motorunits[n].getType().equals(ReMoto.S))
				outputI += instantMotorUnitActivation(motorunits[n].getCd(), t);
			else outputII += instantMotorUnitActivation(motorunits[n].getCd(), t);
		}
		
		if(mu_type.equals(ReMoto.S))	output = outputI;
		else output = outputII;

		return output;
	}
	
	/*
	public double instantMotorUnitActivation(String cdNeuron, double t)
	{
		// Pick mu in hashtable 
		MotorUnit mu = pickMotorUnit(cdNeuron);
		
		double output = 0;
		
		
        if( mu == null )
        	return 0;
        
        
        
        
        if( mu.getNumberOfSpikesAtEndPlate() == 0 )
			return 0;
		
        
        
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
            	
            	//activationFunction[mu.getIndex() - 1].start(iteration);
    			
    		}
    		
    	}
    	
    	//System.out.println("delay: " + delay[mu.getIndex() - 1]);
    	
    	for(int k = 0; k < mu.getNumberOfSpikesAtEndPlate(); k++){
			
			double aux_tSpike = mu.getEndPlateSpike(k);
			
			if(tSpike > t && tSpike - t <= delay[mu.getIndex() - 1] + ReMoto.T_TOLERANCE && k == mu.getNumberOfSpikesAtEndPlate() -1){
				
			}
			else{
				if(t - tSpike < 2 * mu.getTHalfRaikova())
					output += DMFuntion(t - aux_tSpike);
				//if(t - tSpike < 4 * mu.getTHalfRaikova() && motorunits.length < 10 || 
						//t - tSpike < 2 * mu.getTHalfRaikova() && motorunits.length >= 10)
			}
			
		}
    	
		
        mu.setIteration(iteration + 1);
        iteration = mu.getIteration();
        
        //System.out.println("time: " + t + "getValue: " + twitchFunction[mu.getIndex() - 1].getValue(iteration));
       
        return output;
	}
	*/
	
	private double DMActivationFuntion(double t) {
		
		return (Math.exp(-t/tau1) - Math.exp(-t/tau2)) * (tau1 - tau2);
	}



	
	
	private double activationFunction(double t, String mu_type){
		
		double activation = instantActivation(t, mu_type);
		
		return activation;
	}
	
	
		

	@Override
	public double instantMuscleForce(double t) {
		
		return activationFunction(t, "S");
	}

	@Override
	public double instantMotorUnitForce(String cdNeuron, double t) {
		// TODO Auto-generated method stub
		return activationFunction(t, "F");
	}

	public double instantMotorUnitActivation(String cdNeuron, double t)
	{
		// Pick mu in hashtable 
		MotorUnit mu = pickMotorUnit(cdNeuron);
				
		double activation = 0;
    
		if( mu == null ){
			return 0;
		}
        	
        if( mu.getNumberOfSpikesAtEndPlate() == 0 ){
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
    	
    	//System.out.println("delay: " + delay[mu.getIndex() - 1]);
    	
    	for(int k = 0; k < mu.getNumberOfSpikesAtEndPlate(); k++){
			
				if(t >= mu.getEndPlateSpike(k) && t - tSpike < 10.0 * mu.getTHalfRaikova()){
				
					activation += DMActivationFuntion(t - mu.getEndPlateSpike(k));
					
				}
					
				//System.out.println("time:     " + t + "  output:     " + output + "  t-tspike:     " + (t - mu.getEndPlateSpike(k)));
				
				
		}
    		
        mu.setIteration(iteration + 1);
        iteration = mu.getIteration();
        
        //System.out.println("time: " + t + "getValue: " + twitchFunction[mu.getIndex() - 1].getValue(iteration));
        
        return activation;
	}

	protected void calculateActivation(double t){
		
		double outputI = 0;
		double outputII = 0;
		//double maximumForce = 0;
		
		for(int n = 0; n < motorunits.length; n++)
		{
			//maximumForce += motorunits[n].getGpeak() * motorunits[n].getTwTet();
			
			//output += instantMotorUnitActivation(motorunits[n].getCd(), t);
			
			
			if(motorunits[n].getType().equals(ReMoto.S))
				outputI += instantMotorUnitActivation(motorunits[n].getCd(), t);
			else outputII += instantMotorUnitActivation(motorunits[n].getCd(), t);
			
		}
		
		activationNormSType = outputI;
		activationNormFType = outputII;
		
		activationNorm = activationNormSType + activationNormFType;
		
		super.atualizeActivation(t);
		
	}


	@Override
	public void atualize(double t) {
		// TODO Auto-generated method stub
		//force = instantMuscleForce(t);
		
		calculateActivation(t);
		
		//calculateLengthNorm();
		//calculateVelocity(timeNorm);
		//calculateForce();
		
		//forceStore.add( new Signal( ReMoto.force, force, t) );
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