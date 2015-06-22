
package br.remoto.model.Musculotendon.Muscle.IntrafusalMuscle.Bag2.Models;

import br.remoto.model.Configuration;
import br.remoto.model.MotorUnit;
import br.remoto.model.Musculotendon.Muscle.IntrafusalMuscle.Bag2.Bag2SuperClass;
import br.remoto.model.Proprioceptors.MuscleSpindle;
import br.remoto.servlet.SpindleSimulation;
import br.remoto.spindle_simulator.Spindle;

public class Bag2_Mileusnic extends Bag2SuperClass{

	//protected double time_step;

	public static final double beta_0_bag2_chain = 0.0822;
	public static final double beta_2_bag2 = -0.0460;
	public static final double GAMMA_2_bag2 = 0.0636;
	public static final double X_bag2 = 0.7;
	public static final double L_PR_N_bag2_and_chain = 0.89;
	//public static final double G_primary_bag2_and_chain = 10000;
	//public static final double G_secondary_bag2_and_chain = 7250;
	private double G_primary_bag2_and_chain;
	private double G_secondary_bag2_and_chain;
	public static final double L_secondary_bag2 = 0.04;
	public static final double tau_bag2 = 0.205;
	public static final double freq_bag2 = 60;
	
	
	private double z = 0;
	
	private double k1_T = 0;
	private double k2_T = 0;
	private double k3_T = 0;
	private double k4_T = 0;
	
	private double k1_z = 0;
	private double k2_z = 0;
	private double k3_z = 0;
	private double k4_z = 0;
	
	
	public Bag2_Mileusnic(String cdNucleus, Configuration conf, double gamma_dyn, double gamma_static) {
		super(conf, cdNucleus, gamma_dyn, gamma_static);
		this.time_step = conf.getStep() / 1000;
		
		if(cdMuscle.equals("SOL") || cdMuscle.equals("MG") || cdMuscle.equals("LG") || cdMuscle.equals("TA")){
			this.G_primary_bag2_and_chain = conf.getPrimaryBag2AndChainGain();
			this.G_secondary_bag2_and_chain = conf.getSecondaryBag2AndChainGain();
		}	
		else
			this.G_primary_bag2_and_chain = 0;
		
		System.out.println("G_primary_bag2_and_chain: " + G_primary_bag2_and_chain);
		
		System.out.println("Creating Mileusnic's Model of Bag2");
	}

	
		
	
	protected void betaAndGammaFuncs(double t){

		double f_static = fusimotorActivation;
		beta = beta_0_bag2_chain + beta_2_bag2 * f_static;
		GAMMA = GAMMA_2_bag2 * f_static;
	}
	
	@Override
	public double calculateFusimotorActivation(double t){
		
		if (t == 0){
			fusimotorActivation = 0;
		}
		else{
			try
			{
				
				double k1_bag2 = Df_static_bag2Dt(t					, fusimotorActivation);
				double k2_bag2 = Df_static_bag2Dt(t + time_step/2	, fusimotorActivation + k1_bag2*time_step/2);
				double k3_bag2 = Df_static_bag2Dt(t + time_step/2	, fusimotorActivation + k2_bag2*time_step/2);
				double k4_bag2 = Df_static_bag2Dt(t + time_step		, fusimotorActivation + k3_bag2*time_step);
				
				fusimotorActivation =  fusimotorActivation + (time_step * (k1_bag2 + 2*k2_bag2 + 2*k3_bag2 + k4_bag2)/6);
				
			}
			catch(Exception e)
			{
				System.out.println( "Fusimotor Activation: " + e.getMessage() );
			}
		}	
		return fusimotorActivation;
	}
	
	
	private double Df_static_bag2Dt(double t, double f_static){
		
		double term1 = Math.pow(gamma_static, p)/tau_bag2;
		
		double term2 = Math.pow(gamma_static, p) + Math.pow(freq_bag2, p);
		
		double term3 = f_static/tau_bag2;
			
		return (term1/term2) - term3;
	}
	
	
	@Override
public double calculateFiberTension(double t) {
		
		if (t == 0){
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
	
	
	private double f1(double t, double T, double z) {
		
		return z;
	}
	
	
	private double f2(double t, double T, double z) {
		
		betaAndGammaFuncs(t);
		
		//input.stretch_dynamics(t);
		//length = input.stretch_function(t);
		//stretch_velocity = input.getStretchVelocity();
		//stretch_acceleration = input.getStretchAcceleration();
		
		//spindle = (MuscleSpindle)SpindleSimulation.spindle_simulations.get( 13 );
		//length = spindle.getStretch();
		//stretch_velocity = spindle.getVelocity();
		//stretch_acceleration = spindle.getAcceleration();
			
		double term1 = K_SR/M;
		
		
		double term2 = calculateC(stretchVelocity, z) * beta * Math.signum(stretchVelocity - z/K_SR) * 
						Math.pow(Math.abs(stretchVelocity - z/K_SR), a) * Math.abs(lengthNorm - L_SR_0 - T/K_SR - R);  //Math.abs(?
		
		
		double term3 = K_PR * (lengthNorm - L_SR_0 - T/K_SR - L_PR_0);
		
		double term4 = M * stretchAcceleration;
		
		double term5 = GAMMA;
		
		double term6 = T;
		
		return term1 * (term2 + term3 + term4 + term5 - term6);
	}
	
	
	@Override
	public double calculatePrimaryAfferentActivity(double t) {
		double afferentActivity;
		afferentActivity = G_primary_bag2_and_chain * ((intrafusalFiberTension/K_SR) - (L_SR_N - L_SR_0));
		if (afferentActivity < 0) afferentActivity = 0;
		return afferentActivity;
	}
	
	public double calculateSecondaryAfferentActivity(double t) {
		double term1;
		double term2;
		double afferentActivity;

		//spindle = (MuscleSpindle)SpindleSimulation.spindle_simulations.get( 13 );
		//length = spindle.getStretch();
				
		term1 = X_bag2 * (L_secondary_bag2/L_SR_0) * ((intrafusalFiberTension/K_SR) - (L_SR_N - L_SR_0));
		
		term2 = (1 - X_bag2) * (L_secondary_bag2/L_PR_0) * (lengthNorm - (intrafusalFiberTension/K_SR) - L_SR_0 - L_PR_N_bag2_and_chain);
				
		afferentActivity = G_secondary_bag2_and_chain * (term1 + term2);
		if (afferentActivity < 0) afferentActivity = 0;
		return afferentActivity;
	}
	
	
}
