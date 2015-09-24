
package br.remoto.model.Musculotendon.Muscle.IntrafusalMuscle.Bag1.Models;


import br.remoto.model.Configuration;
import br.remoto.model.Musculotendon.Muscle.IntrafusalMuscle.Bag1.Bag1SuperClass;
import br.remoto.model.Proprioceptors.MuscleSpindle;
import br.remoto.servlet.SpindleSimulation;

public class Bag1_Mileusnic extends Bag1SuperClass{
	
		public static final double beta_0_bag1 = 0.0605;
		public static final double L_PR_N_bag1 = 0;
		private double G_primary_bag1;
		public static final double L_secondary_bag1 = 0;
		public static final double tau_bag1 = 0.149;
		public static final double freq_bag1 = 60;
		
		private double z = 0;
		
		private double k1_T = 0;
		private double k2_T = 0;
		private double k3_T = 0;
		private double k4_T = 0;
		
		private double k1_z = 0;
		private double k2_z = 0;
		private double k3_z = 0;
		private double k4_z = 0;
		
		
		public Bag1_Mileusnic(String cdNucleus, Configuration conf, double gamma_dyn, double gamma_static) {
			super(conf, cdNucleus, gamma_dyn, gamma_static);
			if(cdMuscle.equals("SOL") || cdMuscle.equals("MG") || cdMuscle.equals("LG") || cdMuscle.equals("TA"))
				this.G_primary_bag1 = conf.getPrimaryBag1Gain();
			else
				this.G_primary_bag1 = 0;
			
			System.out.println("Creating Mileusnic's Model of Bag1");
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
					/*k2_T = f1(t + time_step/2	, intrafusalFiberTension + k1_T*time_step/2 , z + k1_z*time_step/2);
					k3_T = f1(t + time_step/2	, intrafusalFiberTension + k2_T*time_step/2 , z + k2_z*time_step/2);
					k4_T = f1(t + time_step		, intrafusalFiberTension + k3_T*time_step   , z + k3_z*time_step);
					 */
					//intrafusalFiberTension =  intrafusalFiberTension + (time_step * (k1_T + 2*k2_T + 2*k3_T + k4_T)/6);
					intrafusalFiberTension =  intrafusalFiberTension + (time_step * (k1_T));


					k1_z = f2(t					, intrafusalFiberTension 					, z);
					/*k2_z = f2(t + time_step/2	, intrafusalFiberTension + k1_T*time_step/2 , z + k1_z*time_step/2);
					k3_z = f2(t + time_step/2	, intrafusalFiberTension + k2_T*time_step/2 , z + k2_z*time_step/2);
					k4_z = f2(t + time_step		, intrafusalFiberTension + k3_T*time_step   , z + k3_z*time_step);
					*/
					//z = z + (time_step * (k1_z + 2*k2_z + 2*k3_z + k4_z)/6);
					z = z + (time_step * (k1_z));
					
				}
				catch(Exception e)
				{
					System.out.println( "Fusimotor Activation: " + e.getMessage() );
				}
			}	
			
			return intrafusalFiberTension;
		}
		
		
		/*
		public double calculateFiberTensionEuler(double t) {
			
			
			if (t == 0){
				
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
	    */



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
		
		// Deve ser feita a atualiza��o do fusimotorActivativation para que o m�todo de integra��o fique correto
		// Se for suposto que a ativa��o gamma � constante, ent�o o fusimotorActivation (normalizado) � uma exponencial
		// e sua depend�ncia explicita do tempo permite seus c�lculos em fra��es "futuras" no tempo.
		// Caso a ativa��o gamma n�o seja constante, a depend�ncia temporal deve ser buscada mais adiante.
		
		
		public double calculateFusimotorActivation(double t){
			
			if (t == 0){
				fusimotorActivation = 0;
			}
			else{
				try
				{				
					double k1_bag1 = Df_dynDt(t					, fusimotorActivation);
					/*double k2_bag1 = Df_dynDt(t + time_step/2	, fusimotorActivation + k1_bag1*time_step/2);
					double k3_bag1 = Df_dynDt(t + time_step/2	, fusimotorActivation + k2_bag1*time_step/2);
					double k4_bag1 = Df_dynDt(t + time_step		, fusimotorActivation + k3_bag1*time_step);
					*/
					//fusimotorActivation =  fusimotorActivation + (time_step * (k1_bag1 + 2*k2_bag1 + 2*k3_bag1 + k4_bag1)/6);
					fusimotorActivation =  fusimotorActivation + (time_step * (k1_bag1));
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
			
			double term1 = K_SR/M;
			
			double term2 = calculateC(stretchVelocity, z) * beta * Math.signum(stretchVelocity - z/K_SR) * 
							Math.pow(Math.abs(stretchVelocity - z/K_SR), a) * Math.abs(lengthNorm - L_SR_0 - T/K_SR - R); 
			
			double term3 = K_PR * (lengthNorm - L_SR_0 - T/K_SR - L_PR_0);
			
			double term4 = M * stretchAcceleration;
			
			double term5 = GAMMA;
			
			double term6 = T;
			
			return term1 * (term2 + term3 + term4 + term5 - term6);
		}

		
		public double calculateSecondaryAfferentActivity(double t) {
			return 0;
		}
		
	}

