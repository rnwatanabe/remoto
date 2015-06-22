package br.remoto.spindle;


import br.remoto.servlet.SpindleResults;
import br.remoto.servlet.SpindleSimulation;


public abstract class IntrafusalMuscleFiber {
	
	//Definition of spindle parameters:
	
	public static final double K_SR = 10.4649;
	public static final double K_PR = 0.1500;
	public static final double M = 0.0002;
	//public static final double M = 0.0002/100;
	
	public static final double beta_0_bag1 = 0.0605;
	public static final double beta_0_bag2_chain = 0.0822;
	public static final double beta_1 = 0.2592;
	public static final double beta_2_bag2 = -0.0460;
	public static final double beta_2_chain = -0.0690;
	public static final double GAMMA_1 = 0.0289;
	public static final double GAMMA_2_bag2 = 0.0636;
	public static final double GAMMA_2_chain = 0.0954;
	public static final double C_lengthening = 1;
	public static final double C_shortening = 0.4200;
	public static final double X_bag2 = 0.7;
	public static final double X_chain = 0.7;
	public static final double L_SR_N = 0.0423;
	public static final double L_PR_N_bag1 = 0;
	public static final double L_PR_N_bag2_and_chain = 0.89;
	public static final double G_primary_bag1 = 20000;
	public static final double G_primary_bag2_and_chain = 10000;
	public static final double G_secondary_bag2_and_chain = 7250;
	public static final double a = 0.3;
	public static final double R = 0.46;
	public static final double L_SR_0 = 0.04;
	public static final double L_PR_0 = 0.76;
	public static final double L_secondary_bag1 = 0;
	public static final double L_secondary_bag2 = 0.04;
	public static final double L_secondary_chain = 0.04;
	public static final double tau_bag1 = 0.149;
	public static final double tau_bag2 = 0.205;
	public static final double freq_bag1 = 60;
	public static final double freq_bag2 = 60;
	public static final double freq_chain = 90;
	public static final double p = 2;
	
	
	Input input = (Input)SpindleSimulation.inputs.get( 13 );
	Spindle spindle = (Spindle)SpindleSimulation.spindle_simulations.get( 13 );
	
	double initial_time = input.getInitial_time();
	double time_step = input.getTime_step();
		
	double gamma_static;
	double gamma_dynamic;
		
	double length 	= 0;
	double stretch_velocity 	= 0;
	double stretch_acceleration = 0;
		
	protected double fusimotorActivation;
	protected double intrafusalFiberTension;
	
	protected double beta;
	protected double GAMMA;
	
	protected IntrafusalMuscleFiber(double gamma_dyn, double gamma_static) {
		
		this.setGammaDynamic(gamma_dyn);
		this.setGammaStatic(gamma_static);
	}
	
	protected abstract double calculateFusimotorActivation(double t);
	protected abstract double calculatePrimaryAfferentActivity(double t);
	protected abstract double calculateSecondaryAfferentActivity(double t);
	protected abstract double calculateFiberTension(double t);
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

	public void setIntrafusalFiberTension(double intrafusalFiberTension) {
		this.intrafusalFiberTension = intrafusalFiberTension;
	}

}



