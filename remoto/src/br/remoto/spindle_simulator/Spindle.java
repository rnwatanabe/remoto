package br.remoto.spindle_simulator;


import br.remoto.servlet.SpindleResults;
import br.remoto.servlet.SpindleSimulation;
import br.remoto.spindle_simulator.RampAndHold;
import br.remoto.spindle_simulator.Sinusoidal;


public class Spindle {
	
	Input input;
	
	Bag1 bag1;
	Bag2 bag2;
	Chain chain;
	
	//Definition of simulation parameters with default values (times specified in seconds):
	
	double t;

	double initial_time;
	double time_step;
	double final_time;

	double start_time;
	
	double initial_value;
	double final_value;
	
	double amplitude;
	double frequency;
	double phase;
	double bias;
	
	double gamma_static;
	double gamma_dynamic;
	
	double length 			= 0;
	public static final double S = 0.156;
	
	public int num_steps;
	public double stretch;
	public double velocity;
	public double acceleration;
	
	double[] timeVector 		= new double[num_steps + 1];
	double[] stretchVector 		= new double[num_steps + 1];
	double[] fusimotorVector 	= new double[num_steps + 1];
	double[] IaVector 			= new double[num_steps + 1];
	double[] IIVector 			= new double[num_steps + 1];
	
	double[] f_dynamic_bag1Vector 	= new double[num_steps + 1];
	double[] f_static_bag2Vector 	= new double[num_steps + 1];
	double[] f_static_chainVector 	= new double[num_steps + 1];
	
	double[] tension_bag1Vector 	= new double[num_steps + 1];
	double[] tension_bag2Vector 	= new double[num_steps + 1];
	double[] tension_chainVector 	= new double[num_steps + 1];
	
		
	public Spindle(String str_gamma_static, String str_gamma_dynamic, String input_type) {
				
		if(input_type.equals("constant"))  		input= (Input)SpindleSimulation.inputs.get( 0 );
		if(input_type.equals("ramp-and-hold"))  input= (Input)SpindleSimulation.inputs.get( 1 );
		if(input_type.equals("sinusoidal"))  	input= (Input)SpindleSimulation.inputs.get( 2 );
		if(input_type.equals("triangular"))  	input= (Input)SpindleSimulation.inputs.get( 3 );
		
		initial_time 	= input.getInitial_time();
		time_step 		= input.getTime_step();
		final_time 		= input.getFinal_time();

		start_time 		= input.getStart_time();
		
		gamma_static 	= Double.parseDouble(str_gamma_static);
		gamma_dynamic 	= Double.parseDouble(str_gamma_dynamic);
		
		num_steps 		= (int) Math.round((final_time - initial_time)/time_step);
		
		timeVector 		= new double[num_steps + 1];
		stretchVector 	= new double[num_steps + 1];
		IaVector 		= new double[num_steps + 1];
		IIVector 		= new double[num_steps + 1];
		
		f_dynamic_bag1Vector 	= new double[num_steps + 1];
		f_static_bag2Vector 	= new double[num_steps + 1];
		f_static_chainVector 	= new double[num_steps + 1];
		
		tension_bag1Vector 		= new double[num_steps + 1];
		tension_bag2Vector 		= new double[num_steps + 1];
		tension_chainVector 	= new double[num_steps + 1];
		
		bag1 	= new Bag1(gamma_dynamic,gamma_static);
		bag2 	= new Bag2(gamma_dynamic,gamma_static);
		chain 	= new Chain(gamma_dynamic,gamma_static);
		
	}
	
	
	public int getNum_steps() {
		return num_steps;
	}
	
	public double[] getIaVector() {
		return IaVector;
	}
	
	public double[] getIIVector() {
		return IIVector;
	}
	
		
	public double[] getTimeVector() {
		return timeVector;
	}
	
	public double[] getStretchVector() {
		return stretchVector;
	}
	
	public double getStretch() {
		return stretch;
	}
	
	public double getVelocity() {
		return velocity;
	}
	
	public double getAcceleration() {
		return acceleration;
	}
	
	
	public double[] getF_dynamic_bag1Vector() {
		return f_dynamic_bag1Vector;
	}
	
	public double[] getF_static_bag2Vector() {
		return f_static_bag2Vector;
	}
	
	
	public double[] getF_static_chainVector() {
		return f_static_chainVector;
	}
	
	
	public double[] getTension_bag1Vector() {
		return tension_bag1Vector;
	}
	
	
	public double[] getTension_bag2Vector() {
		return tension_bag2Vector;
	}
	
	
	public double[] getTension_chainVector() {
		return tension_chainVector;
	}
	
	public void Simulation()
    {
		double t = initial_time;
		
		SpindleSimulation.spindle_simulations.put(13, this);
		
		input.showInputType();
		
		stretchVector 		= input.getStretchVector();
		
		for(int iteration = 0; iteration <= num_steps; iteration++){
									
			timeVector[iteration] 			= t;
			
			input.stretch_dynamics(iteration);
			
			stretch 						= stretchVector[iteration];
			velocity 						= input.getStretchVelocity();
			acceleration 					= input.getStretchAcceleration();
			
			tension_bag1Vector[iteration] 	= bag1.calculateFiberTension(t);  
			tension_bag2Vector[iteration] 	= bag2.calculateFiberTension(t);
			tension_chainVector[iteration] 	= chain.calculateFiberTension(t); 
			
			
			f_dynamic_bag1Vector[iteration] = bag1.calculateFusimotorActivation(t); 
			f_static_bag2Vector[iteration] 	= bag2.calculateFusimotorActivation(t); 
			f_static_chainVector[iteration] = chain.calculateFusimotorActivation(t); 
			
			IaVector[iteration] 			= calculateIa(t);
			
			IIVector[iteration] 			= calculateII(t);
			
			t = t + time_step;
		}
    }
	
	
	
	public double calculateIa(double t){
		
		double primary_afferent_bag1 = bag1.calculatePrimaryAfferentActivity(t);
		double primary_afferent_bag2 = bag2.calculatePrimaryAfferentActivity(t);
		double primary_afferent_chain = chain.calculatePrimaryAfferentActivity(t);
		double smaller;
		double larger;
		
		if (primary_afferent_bag1 >= (primary_afferent_bag2 + primary_afferent_chain)){
			larger = primary_afferent_bag1;
			smaller = (primary_afferent_bag2 + primary_afferent_chain);
		}
		else {
			larger = (primary_afferent_bag2 + primary_afferent_chain);
			smaller = primary_afferent_bag1;
		}
		return S * smaller + larger;
	}
	
	
	
	public double calculateII(double t){
		double secondary_afferent_bag2 = bag2.calculateSecondaryAfferentActivity(t);
		double secondary_afferent_chain = chain.calculateSecondaryAfferentActivity(t);
		return secondary_afferent_bag2 + secondary_afferent_chain;
	}


}
