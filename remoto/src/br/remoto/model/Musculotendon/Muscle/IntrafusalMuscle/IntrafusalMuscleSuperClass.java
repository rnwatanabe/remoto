package br.remoto.model.Musculotendon.Muscle.IntrafusalMuscle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import br.remoto.model.Configuration;
import br.remoto.model.MotorUnit;
import br.remoto.model.ReMoto;
import br.remoto.servlet.SpindleSimulation;
import br.remoto.spindle_simulator.Input;
import br.remoto.util.Signal;
import br.remoto.model.Musculotendon.Muscle.MuscleSuperClass;
import br.remoto.model.Proprioceptors.MuscleSpindle;



public abstract class IntrafusalMuscleSuperClass extends MuscleSuperClass
{

	//Definition of spindle parameters:
	
	public static final double K_SR = 10.4649;
	public static final double K_PR = 0.1500;
	public static final double M = 0.0002;
	//public static final double M = 0.0002/100;
	
	//public static final double beta_0_bag1 = 0.0605;
	//public static final double beta_0_bag2_chain = 0.0822;
	public static final double beta_1 = 0.2592;
	//public static final double beta_2_bag2 = -0.0460;
	public static final double beta_2_chain = -0.0690;
	public static final double GAMMA_1 = 0.0289;
	//public static final double GAMMA_2_bag2 = 0.0636;
	public static final double GAMMA_2_chain = 0.0954;
	public static final double C_lengthening = 1;
	public static final double C_shortening = 0.4200;
	//public static final double X_bag2 = 0.7;
	public static final double X_chain = 0.7;
	public static final double L_SR_N = 0.0423;
	//public static final double L_PR_N_bag1 = 0;
	//public static final double L_PR_N_bag2_and_chain = 0.89;
	//public static final double G_primary_bag1 = 20000;
	//public static final double G_primary_bag2_and_chain = 10000;
	//public static final double G_secondary_bag2_and_chain = 7250;
	public static final double a = 0.3;
	public static final double R = 0.46;
	public static final double L_SR_0 = 0.04;
	public static final double L_PR_0 = 0.76;
	//public static final double L_secondary_bag1 = 0;
	//public static final double L_secondary_bag2 = 0.04;
	public static final double L_secondary_chain = 0.04;
	//public static final double tau_bag1 = 0.149;
	//public static final double tau_bag2 = 0.205;
	//public static final double freq_bag1 = 60;
	//public static final double freq_bag2 = 60;
	public static final double freq_chain = 90;
	public static final double p = 2;
	
	protected double gamma_static;
	protected double gamma_dynamic;

	protected double time_step;
		
	protected double fusimotorActivation;
	protected double intrafusalFiberTension;
	
	protected double beta;
	protected double GAMMA;
	
	
	
	protected IntrafusalMuscleSuperClass(Configuration conf, String cdNucleus, double gamma_dyn, double gamma_static) {
		super(conf, cdNucleus);
		
		this.time_step = conf.getStep() / 1000;
		
		this.gamma_dynamic = gamma_dyn;
		this.gamma_static = gamma_static;
	}
	
	public abstract double calculateFusimotorActivation(double t);
	public abstract double calculatePrimaryAfferentActivity(double t);
	public abstract double calculateSecondaryAfferentActivity(double t);
	public abstract double calculateFiberTension(double t);
	protected abstract void betaAndGammaFuncs(double t);
	
	
	public double calculateC(double stretch_velocity, double dT){
		if((stretch_velocity - dT/K_SR) >= 0) return C_lengthening;
		else return C_shortening;
	}
	
	public void setGammaDynamic( double value )
    {
		gamma_dynamic = value;
    }

	public void setGammaStatic( double value )
    {
		gamma_static = value;
    }
	
	public double getIntrafusalFiberTension() {
		return intrafusalFiberTension;
	}

	/*
	public void calculateVelocityAndAcceleration(double t){
		
		
		if(t == 0){
			stretch_velocity = 0;
			stretch_acceleration = 0;
		}else{
			stretch_velocity = (lengthNorm - lastNormLength) / time_step;
			stretch_acceleration = (stretch_velocity - lastStretchVelocity) / time_step;
		}
		
		lastNormLength = lengthNorm;
		lastStretchVelocity = stretch_velocity;
	}
	*/
	
	public double getFusimotorActivation() {
		return fusimotorActivation;
	}
	
	
	

}




