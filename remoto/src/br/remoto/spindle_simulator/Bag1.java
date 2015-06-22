
package br.remoto.spindle_simulator;

import br.remoto.servlet.SpindleSimulation;

public class Bag1 extends IntrafusalMuscleFiber{
	
	private double z = 0;
	
	
	private double k1_T = 0;
	private double k2_T = 0;
	private double k3_T = 0;
	private double k4_T = 0;
	
	private double k1_z = 0;
	private double k2_z = 0;
	private double k3_z = 0;
	private double k4_z = 0;
	
	
	protected Bag1(double gamma_dyn, double gamma_static) {
		super(gamma_dyn,  gamma_static);
	}
	
	
	@Override
	public double calculateFiberTension(double t) {
		
		if (t == initial_time){
			intrafusalFiberTension = 0;
		}
		else{
			try
			{
				k1_T = f1(t					, intrafusalFiberTension 					, z);
				k2_T = f1(t + time_step/2	, intrafusalFiberTension + k1_T*time_step/2 , z + k1_z*time_step/2);
				k3_T = f1(t + time_step/2	, intrafusalFiberTension + k2_T*time_step/2 , z + k2_z*time_step/2);
				k4_T = f1(t + time_step		, intrafusalFiberTension + k3_T*time_step   , z + k3_z*time_step);
		
				intrafusalFiberTension =  intrafusalFiberTension + (time_step * (k1_T + 2*k2_T + 2*k3_T + k4_T)/6);


				k1_z = f2(t					, intrafusalFiberTension 					, z);
				k2_z = f2(t + time_step/2	, intrafusalFiberTension + k1_T*time_step/2 , z + k1_z*time_step/2);
				k3_z = f2(t + time_step/2	, intrafusalFiberTension + k2_T*time_step/2 , z + k2_z*time_step/2);
				k4_z = f2(t + time_step		, intrafusalFiberTension + k3_T*time_step   , z + k3_z*time_step);
				
				z = z + (time_step * (k1_z + 2*k2_z + 2*k3_z + k4_z)/6);
				
				
			}
			catch(Exception e)
			{
				System.out.println( "Fusimotor Activation: " + e.getMessage() );
			}
		}	
		return intrafusalFiberTension;
	}
	
	
	
public double calculateFiberTensionEuler(double t) {
		
		System.out.println("");
		System.out.println("ITERATION");
		System.out.println("");
		if (t == initial_time){
			
			intrafusalFiberTension = 0;
			z = 0;
			System.out.println("t = " + t + "     T = " + intrafusalFiberTension+ "     z = " + z);
		}
		else{
			try
			{
				intrafusalFiberTension =  intrafusalFiberTension + (time_step * f1(t, intrafusalFiberTension , z));
				
				z = z + (time_step * f2(t, intrafusalFiberTension, z));
				
				System.out.println("t = " + t + "     T = " + intrafusalFiberTension+ "     z = " + z);
				
				
			}
			catch(Exception e)
			{
				System.out.println( "Fusimotor Activation: " + e.getMessage() );
			}
		}	
		return intrafusalFiberTension;
	}




	@Override
	public double calculatePrimaryAfferentActivity(double t) {
		double afferentActivity;
		afferentActivity = G_primary_bag1 * ((intrafusalFiberTension/K_SR) - (L_SR_N - L_SR_0));
		if (afferentActivity < 0) afferentActivity = 0;
		return afferentActivity;
	}
	
	protected void betaAndGammaFuncs(double t){
		double f_dyn = fusimotorActivation;
		beta = beta_0_bag1 + beta_1 * f_dyn;
		GAMMA = GAMMA_1 * f_dyn;
	}
	 
	public double calculateFusimotorActivation(double t){
		
		if (t == initial_time){
			fusimotorActivation = 0;
		}
		else{
			try
			{				
				double k1_bag1 = Df_dynDt(t					, fusimotorActivation);
				double k2_bag1 = Df_dynDt(t + time_step/2	, fusimotorActivation + k1_bag1*time_step/2);
				double k3_bag1 = Df_dynDt(t + time_step/2	, fusimotorActivation + k2_bag1*time_step/2);
				double k4_bag1 = Df_dynDt(t + time_step		, fusimotorActivation + k3_bag1*time_step);
		
				fusimotorActivation =  fusimotorActivation + (time_step * (k1_bag1 + 2*k2_bag1 + 2*k3_bag1 + k4_bag1)/6);
			}
			catch(Exception e)
			{
				System.out.println( "Fusimotor Activation: " + e.getMessage() );
			}
		}	
		return fusimotorActivation;
	}
	
	
	private double Df_dynDt(double t, double f_dyn){
		
		double term1 = Math.pow(gamma_dynamic, p)/tau_bag1;
		
		double term2 = Math.pow(gamma_dynamic, p) + Math.pow(freq_bag1, p);
		
		double term3 = f_dyn/tau_bag1;

		return (term1/term2) - term3;
	}

	
	
	private double f1(double t, double T, double z) {
				
		return z;
	}
	
		
	
	private double f2(double t, double T, double z) {
		
		betaAndGammaFuncs(t);
		
		//input.stretch_dynamics(t);
		//length = input.stretch_function(t);
		//stretch_velocity = input.getStretchVelocity();
		//stretch_acceleration = input.getStretchAcceleration();
		
		spindle = (Spindle)SpindleSimulation.spindle_simulations.get( 13 );
		length = spindle.getStretch();
		stretch_velocity = spindle.getVelocity();
		stretch_acceleration = spindle.getAcceleration();
		
		double term1 = K_SR/M;
		
		double term2 = calculateC(stretch_velocity, z) * beta * Math.signum(stretch_velocity - z/K_SR) * 
						Math.pow(Math.abs(stretch_velocity - z/K_SR), a) * Math.abs(length - L_SR_0 - T/K_SR - R); 
		
		double term3 = K_PR * (length - L_SR_0 - T/K_SR - L_PR_0);
		
		double term4 = M * stretch_acceleration;
		
		double term5 = GAMMA;
		
		double term6 = T;
		
		return term1 * (term2 + term3 + term4 + term5 - term6);
	}

	
	public double calculateSecondaryAfferentActivity(double t) {
		return 0;
	}
}
