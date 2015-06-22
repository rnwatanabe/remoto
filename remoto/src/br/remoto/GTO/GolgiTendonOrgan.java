
package br.remoto.GTO;

import br.remoto.servlet.GTOConfig;
import br.remoto.servlet.GTOSimulation;
import br.remoto.servlet.GTOSuper;

public class GolgiTendonOrgan {
	
	
	public double tension = 0 ;
	
	private int numberOfInsetingFibers;
	
	GTOInput input;
	
	double t;
	
	LooselyPackedCollagen[] lpc = new LooselyPackedCollagen[numberOfInsetingFibers];
	DenselyPackedCollagen[] dpc = new DenselyPackedCollagen[numberOfInsetingFibers];
	BypassingCollagen[] 	bc 	= new BypassingCollagen[numberOfInsetingFibers];
	SenroryRegionCollagen[] src = new SenroryRegionCollagen[numberOfInsetingFibers];

	double initial_time;
	double time_step;
	double final_time;
	
	public int num_steps;
	
	double[] timeVector 		= new double[num_steps + 1];
	double[] tensionVector   = new double[num_steps + 1];
	double[] IbVector 			= new double[num_steps + 1];
	
	public GolgiTendonOrgan(String input_type) {
				
		
		//if(input_type.equals("constant"))  		input= (GTOInput)GTOConfig.GTO_Inputs.get( 1 );
		//if(input_type.equals("ramp-and-hold"))  input= (GTOInput)GTOConfig.GTO_Inputs.get( 1 );
		//if(input_type.equals("sinusoidal"))  	input= (GTOInput)GTOConfig.GTO_Inputs.get( 1 );
		//if(input_type.equals("triangular"))  	input= (GTOInput)GTOConfig.GTO_Inputs.get( 1 );
		
		input = (GTOInput) GTOSuper.GTO_Inputs.get(1);
		
		input.showInputType();
		
		
		
		initial_time 	= input.getInitial_time();
		time_step 		= input.getTime_step();
		final_time 		= input.getFinal_time();
		
		num_steps 		= (int) Math.round((final_time - initial_time)/time_step);
		
		
		timeVector 		= new double[num_steps + 1];
		tensionVector   = new double[num_steps + 1];
		IbVector 		= new double[num_steps + 1];
		

	}

	
	protected double calculateSumOfCollagenTensions(){
		return 13;
	}
	
	
	public void Simulation()
    {
		GTOSuper.GTO_simulations.put(13, this);
		
		double t = initial_time;
				
		//tensionVector 		= input.getTensionVector();
		
		
		for(int iteration = 0; iteration <= num_steps; iteration++){
									
			timeVector[iteration] 			= t;
			
			//input.stretch_dynamics(iteration);
			
			tension 						= tensionVector[iteration];
			//velocity 						= input.getStretchVelocity();
			//acceleration 					= input.getStretchAcceleration();
			
			//tension_bag1Vector[iteration] 	= bag1.calculateFiberTension(t);  
			//tension_bag2Vector[iteration] 	= bag2.calculateFiberTension(t);
			//tension_chainVector[iteration] 	= chain.calculateFiberTension(t); 
			
			
			//f_dynamic_bag1Vector[iteration] = bag1.calculateFusimotorActivation(t); 
			//f_static_bag2Vector[iteration] 	= bag2.calculateFusimotorActivation(t); 
			//f_static_chainVector[iteration] = chain.calculateFusimotorActivation(t); 
			
			IbVector[iteration] 			= calculateIb(t);
			
			
			t = t + time_step;
		}
    }
	
	public double calculateIb(double t){
		
		//double primary_afferent_bag1 = bag1.calculatePrimaryAfferentActivity(t);
		//double primary_afferent_bag2 = bag2.calculatePrimaryAfferentActivity(t);
		//double primary_afferent_chain = chain.calculatePrimaryAfferentActivity(t);
		
		
		return 133;
	}
	
	public int getNum_steps() {
		return num_steps;
	}
	
	public double[] getIbVector() {
		return IbVector;
	}
	
	public double[] getTimeVector() {
		return timeVector;
	}
	
}
