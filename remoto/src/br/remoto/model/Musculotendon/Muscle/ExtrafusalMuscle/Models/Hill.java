package br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import br.remoto.model.Configuration;
import br.remoto.model.MotorUnit;
import br.remoto.model.ReMoto;
import br.remoto.util.Conversion;
import br.remoto.util.Sample;
import br.remoto.util.Signal;
import br.remoto.model.Conductance.AlphaFunction;
import br.remoto.model.Musculotendon.MusculotendonSuperClass;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.ExtrafusalMuscleSuperClass;
import br.remoto.model.Musculotendon.Tendon.NonInnerveted.NonInnervatedTendon;
import br.remoto.model.vo.MuscleVO;


public class Hill extends ExtrafusalMuscleSuperClass
{
	protected AlphaFunction[] activationFunction;
	private double maximumMuscleForce;
	private double mass;
	private double forceParallelElement;
	private double forceViscousElement;
	private double forceActiveSType;
	private double forceActiveFType;
	
	// New parameters
	private double LsP;
	private double L0P;
	private double skP;
	private double sdP;
	
	private double pennationAngleAtOptimalLength;
	private double step;
	
	double k1_l;
	double k2_l;
	double k3_l;
	double k4_l;
	
	double k1_v;
	double k2_v;
	double k3_v;
	double k4_v;
	
	public Hill(Configuration conf, String cdMuscle, MotorUnit[] mu, String cdMuscleModel){
		super(conf, cdMuscle, mu, cdMuscleModel);
		
		this.activationFunction = new AlphaFunction[motorunits.length];
		
		for(int i = 0; i < motorunits.length; i++){
			activationFunction[i] = new AlphaFunction(null, null, null);
		}
		
		MuscleVO vo = conf.getMuscle(cdMuscle, "hill");
		
		samplerLengthStore 					= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerLengthNormStore 				= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerVelocityStore 				= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerAccelerationStore 			= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerForceParallelElementStore 	= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerForceViscousElementStore 	= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerForceActiveSTypeStore 		= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerForceActiveFTypeStore 		= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerForceStore 					= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerPennationAngleStore 			= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		
		step = Conversion.convertMillisecondsToSeconds(conf.getStep());
		//step = conf.getStep();

		optimalLength = vo.getOptimalLength();
		pennationAngleAtOptimalLength = vo.getPennationAngle() * Math.PI / 180;
		slackTendonLength = vo.getSlackTendonLength();
		maximumMuscleForce = vo.getMaximumMuscleForce();
		mass = vo.getMuscleMass();
		viscosityCoeficient = vo.getViscosityCoeficient();
		elasticCoeficientOfParallelElement = vo.getElasticCoeficientOfParallelElement();
		skP = elasticCoeficientOfParallelElement;
		sdP = viscosityCoeficient;
	}
	
	public void setInitialLengthNorm(){
		
		if(cdMuscle.equals("SOL")){
			//lengthNorm = 1.04414; //Ankle Angle = 0deg
			lengthNorm = 0.97026; //Ankle Angle = 3deg
			length = lengthNorm * optimalLength;
		}		
		else if(cdMuscle.equals("MG")){
			//lengthNorm = 1.06290; //Ankle Angle = 0deg
			lengthNorm = 1.00972; //Ankle Angle = 3deg
			length = lengthNorm * optimalLength;
		}
		else if(cdMuscle.equals("LG")){
			//lengthNorm = 0.99971; //Ankle Angle = 0deg
			lengthNorm = 1.04775; //Ankle Angle = 3deg
			length = lengthNorm * optimalLength;
		}
		else if(cdMuscle.equals("TA")){
			//lengthNorm = 1.02823; //Ankle Angle = 0deg
			lengthNorm = 1.23948; //Ankle Angle = 3deg
			length = lengthNorm * optimalLength;
		}
		else{
			lengthNorm = 1;
			length = lengthNorm * optimalLength;
		}
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
	
	public double instantMotorUnitActivation(String cdNeuron, double t)
	{
		// Pick mu in hashtable 
		MotorUnit mu = pickMotorUnit(cdNeuron);
		
        if( mu == null )
        	return 0;
        
        if(!activationFunction[mu.getIndex() - 1].isStarted()){
        	activationFunction[mu.getIndex() - 1].setGmax(mu.getGmax());
        	activationFunction[mu.getIndex() - 1].setTpeak(mu.getTpeak());
        	activationFunction[mu.getIndex() - 1].reset( mu.getMiscellaneous() );
        }
        
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
            	
            	activationFunction[mu.getIndex() - 1].start(iteration);
    			
    		}
    		
    	}
    	
        mu.setIteration(iteration + 1);
        iteration = mu.getIteration();
       
        double force = activationFunction[mu.getIndex() - 1].getValue(iteration);
        
        double output = 0;
        
        if(mu.getType().equals(ReMoto.S))
        	output = motorUnitForceSaturationFunction(force, mu.getType(), mu.getB()) * mu.getTwTet() * mu.getGpeak();
        else if(mu.getType().equals(ReMoto.FR))
        	output = motorUnitForceSaturationFunction(force, mu.getType(), mu.getB()) * mu.getTwTet() * mu.getGpeak();
        else if(mu.getType().equals(ReMoto.FF))
        	output = motorUnitForceSaturationFunction(force, mu.getType(), mu.getB()) * mu.getTwTet() * mu.getGpeak();
        else
        	output = 0;
               
        return output;
	}
	
	protected void calculateActivation(double t){
		
		double outputI = 0;
		double outputII = 0;
		double maximumForce = 0;
		
		for(int n = 0; n < motorunits.length; n++)
		{

			maximumForce += motorunits[n].getGpeak() * motorunits[n].getTwTet();
			
			if(motorunits[n].getType().equals(ReMoto.S))
				outputI += instantMotorUnitActivation(motorunits[n].getCd(), t);
			else outputII += instantMotorUnitActivation(motorunits[n].getCd(), t);
			
		}
		
		/*
		if(cdMuscle.equals("SOL")){
			activationNormSType = 0;
			activationNormFType = 0;
		}
		if(cdMuscle.equals("MG")){
			activationNormSType = 0;
			activationNormFType = 0;
		}
		if(cdMuscle.equals("LG")){
			activationNormSType = 0;
			activationNormFType = 0;
		}
		if(cdMuscle.equals("TA")){
			activationNormSType = 0;
			activationNormFType = 0;
		}
		*/

		activationNormSType = outputI / maximumForce;
		activationNormFType = outputII / maximumForce;
	
		activationNorm = activationNormSType + activationNormFType;
		
		super.atualizeActivation(t);
		
	}
	
	protected double calculatePennationAngle(double muscleLengthNorm){
		return Math.asin(Math.sin(pennationAngleAtOptimalLength) / muscleLengthNorm);
	}
	
	public void atualize(double t)
	{	
		samplerLengthStore.sample(lengthStore, "", t, length);
		samplerLengthNormStore.sample(lengthNormStore, "", t, lengthNorm);
		samplerVelocityStore.sample(velocityStore, "", t, velocity);
		samplerAccelerationStore.sample(accelerationStore, "", t, acceleration);
		samplerForceParallelElementStore.sample(forceParallelElementStore, "", t, forceParallelElement);
		samplerForceViscousElementStore.sample(forceViscousElementStore, "", t, forceViscousElement);
		samplerForceActiveSTypeStore.sample(forceActiveSTypeStore, "", t, forceActiveSType);
		samplerForceActiveFTypeStore.sample(forceActiveFTypeStore, "", t, forceActiveFType);
		samplerPennationAngleStore.sample(pennationAngleStore, "", t, pennationAngle * 180 / Math.PI);
		samplerForceStore.sample(forceStore, "", t, force);
		
		calculateActivation(t);

		pennationAngle = calculatePennationAngle(lengthNorm);
		calculateVelocityAndLength(Conversion.convertMillisecondsToSeconds(t));
	
		lengthNorm = length / optimalLength;
		stretchVelocity = Conversion.convertVelocitySecondsToMilliseconds(velocity / optimalLength);
		stretchAcceleration = Conversion.convertAccelerationSecondsToMilliseconds(acceleration / optimalLength);
		
		force = instantMuscleForce(t);

	}

	private double calculateForceParallelElement(double lengthNorm){
		forceParallelElement = (Math.exp(skP * (lengthNorm - 1) / ReMoto.eps0_M)) / (Math.exp(skP));
				
		return forceParallelElement;
	}
	
	private double calculateForceViscousElement(double lengthNorm, double stretchVelocity){
		forceViscousElement = sdP * (stretchVelocity);
		
		return forceViscousElement;
	}
	
	private double calculateForceActiveSType(double t, double lengthNorm, double stretchVelocity){
		double aux1 = activationNormSType;
		double aux2 = calculateForceLengthRelationship(lengthNorm, ReMoto.S);
		double aux3 = calculateForceVelocityRelationship(lengthNorm, stretchVelocity, ReMoto.S);
		
		forceActiveSType = aux1 * aux2 * aux3;
		
		return forceActiveSType;
	}
	
	private double calculateForceActiveFType(double t, double lengthNorm, double stretchVelocity){
		double aux1 = activationNormFType;
		double aux2 = calculateForceLengthRelationship(lengthNorm, ReMoto.F);
		double aux3 = calculateForceVelocityRelationship(lengthNorm, stretchVelocity, ReMoto.F);
		
		forceActiveFType = aux1 * aux2 * aux3;
		
		return forceActiveFType;
	}
	
	private double calculateForceLengthRelationship(double lengthNorm, String fiberType){
		
		double output = 0;
	
		if(fiberType.equals(ReMoto.S)){
			output = Math.exp(-Math.pow(Math.abs((Math.pow(lengthNorm, ReMoto.B_SType) - 1) / ReMoto.w_SType), ReMoto.p_SType));
		}
		else if(fiberType.equals(ReMoto.F)){
			output = Math.exp(-Math.pow(Math.abs((Math.pow(lengthNorm, ReMoto.B_FType) - 1) / ReMoto.w_FType), ReMoto.p_FType));
		}
		
		return output;
	}
	
	private double calculateForceVelocityRelationship(double lengthNorm, double stretchVelocity, String fiberType){
		double num = 0;
		double den = 0;
		
		if(stretchVelocity > 0.1){
			if(fiberType.equals(ReMoto.S)){
				num = ReMoto.bv_SType - 
						(ReMoto.av0_SType + ReMoto.av1_SType * lengthNorm + ReMoto.av2_SType * Math.pow(lengthNorm, 2)) *
						stretchVelocity;
				den = ReMoto.bv_SType + stretchVelocity;
			}
			else if(fiberType.equals(ReMoto.F)){
				num = ReMoto.bv_FType -
				(ReMoto.av0_FType + ReMoto.av1_FType * lengthNorm + ReMoto.av2_FType * Math.pow(lengthNorm, 2)) *
				stretchVelocity;
				den = ReMoto.bv_FType + stretchVelocity;
			}
		}
		else{
			if(fiberType.equals(ReMoto.S)){
				num = ReMoto.maximumVelocitySType - stretchVelocity;
				den = ReMoto.maximumVelocitySType + (stretchVelocity * (ReMoto.cv0_SType + ReMoto.cv1_SType * lengthNorm));
			}
			else if(fiberType.equals(ReMoto.F)){
				num = ReMoto.maximumVelocityFType - stretchVelocity;
				den = ReMoto.maximumVelocityFType + (stretchVelocity * (ReMoto.cv0_FType + ReMoto.cv1_FType * lengthNorm));
			}
		}
		
		return num/den;
	}
	
	public double calculateContractileElementForce(double t, double lengthNorm, double stretchVelocity){
		forceNorm = calculateForceParallelElement(lengthNorm) + 
				calculateForceViscousElement(lengthNorm, stretchVelocity) + 
				calculateForceActiveSType(t, lengthNorm, stretchVelocity) + 
				calculateForceActiveFType(t, lengthNorm, stretchVelocity);
				
		return forceNorm * maximumMuscleForce;
		
	}
	
	public double calculateAcceleration(double t, double length, double velocity){				
		force = calculateContractileElementForce(t, length/optimalLength, velocity/optimalLength);
		
		 // acceleration = (associatedMusculotendon.getTendon().getForce() * Math.cos(pennationAngle) -
					//	   force * Math.pow(Math.cos(pennationAngle),2)) / mass;
		
		/*Random  fRandom = new Random();
		
		double stdAcc = 0.0025 * (fRandom.nextGaussian());
		acceleration = 0.0 + stdAcc;*/
		acceleration = 0;
		//System.out.println( "acceleration: " + acceleration );
		
		return acceleration;
	}
	
	public void calculateVelocityAndLength(double t){
		
		k1_l = velocity;
		k1_v = DvDt(t	, length	, velocity);
		
		k2_l = velocity + (step/2) * k1_v;
		k2_v = DvDt(t + step/2	, length + (step/2) * k1_l	, velocity  + (step/2) * k1_v);
		
		k3_l = (velocity + (step/2) * k2_v);
		k3_v = DvDt(t + step/2	, length + (step/2) * k2_l	, velocity  + (step/2) * k2_v);
		
		k4_l = (velocity + step * k3_v);
		k4_v = DvDt(t + step		, length + 	step * k3_l		, velocity  + 	step * k3_v);
		
		//length =  length + (step * (k1_l + 2*k2_l + 2*k3_l + k4_l)/6);
		//velocity =  velocity + (step * (k1_v + 2*k2_v + 2*k3_v + k4_v)/6);
		length =  length + (step * (k1_l));
		//velocity =  velocity + (step * (k1_v));
		
		
		Random  fRandom = new Random();
		
		double stdVec = 0.084* (fRandom.nextGaussian());
		velocity = 0.0000 + stdVec;
		
	}
	
	private double DvDt(double t, double length, double velocity){
		return calculateAcceleration(t, length, velocity);
	}
	
	@Override
	public double instantMuscleForce(double t) {
		
		return force;
	}
	

	@Override
	public double instantMotorUnitForce(String cdNeuron, double t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getMaximumMuscleForce() {
		return maximumMuscleForce;
	}
	
	public void setMaximumMuscleForce(double maximumMuscleForce) {
		this.maximumMuscleForce = maximumMuscleForce;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}
	
}