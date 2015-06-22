package br.remoto.GTO;

import br.remoto.servlet.GTOConfig;


import java.util.Random;

public abstract class GTOInput {

	
	protected double initial_time;
	protected double time_step;
	protected double final_time;

	protected double start_time;
	protected double end_time;
		
	
	protected String isRandom = "";
	protected double stddev;
	protected double LPcutoff;
	
	protected LowPassFilter butter = new LowPassFilter();
	protected Random gaussianNoise = new Random();
	
	//double tension_velocity 	= 0;
	//double tension_acceleration = 0;
	
	
	int num_steps = (int) Math.round((final_time - initial_time)/time_step);
	
	double[] timeVector 		= new double[num_steps + 1];
	double[] tensionVector 		= new double[num_steps + 1];
	
	
	public GTOInput(String str_initial_time,String str_time_step,String str_final_time,String str_start_time,String str_end_time) {
		//super();
		
		//System.out.println("debug4");
		
		//GTOConfig.GTO_Inputs.put(13, this);
		
		//System.out.println("debug5");
		
		double initial_time 	= Double.parseDouble(str_initial_time);
		double time_step 		= Double.parseDouble(str_time_step);
		double final_time 		= Double.parseDouble(str_final_time);
		double start_time 		= Double.parseDouble(str_start_time);
		double end_time 		= Double.parseDouble(str_end_time);
				
		this.setInitial_time(initial_time);
		this.setTime_step(time_step);
		this.setFinal_time(final_time);
		
		this.setStart_time(start_time);
		this.setEnd_time(end_time);	
		
		num_steps 		= (int) Math.round((final_time - initial_time)/time_step);
		
		timeVector 		= new double[num_steps + 1];
		tensionVector 	= new double[num_steps + 1];
	}
	
	
	public void setInitial_time( double value )
    {
		initial_time = value;
    }

	public void setTime_step( double value )
    {
		time_step = value;
    }
	
	public double getFinal_time() {
		return final_time;
	}

	public void setFinal_time(double finalTime) {
		final_time = finalTime;
	}


	public void setStart_time(double startTime) {
		start_time = startTime;
	}


	public void setEnd_time(double endTime) {
		end_time = endTime;
	}


	
	
			
	public double getInitial_time() {
		return initial_time;
	}

	public double getTime_step() {
		return time_step;
	}


	public double getStart_time() {
		return start_time;
	}

	public double getEnd_time() {
		return end_time;
	}

	/*	
	public double getStretchVelocity() {
		return stretch_velocity;
	}

	public double getStretchAcceleration() {
		return stretch_acceleration;
	}
	*/
	
	public void SimulateInput()
    {
		double t = initial_time;
		
		for(int iteration = 0; iteration <= num_steps; iteration++){
			
			timeVector[iteration] = t;
			
			tensionVector[iteration] = this.stretch_function(t);
			
			t = t + time_step;
		}
		
		if (isRandom.equals("is_random")){
			double aux[];
			double IC;
			double noise = 0.0;
			
			IC = tensionVector[0];
			//System.out.println(IC);
			//System.out.println(LPcutoff);
			
			for (int iteration = 0; iteration < tensionVector.length; iteration++){
				noise = gaussianNoise.nextGaussian();
				tensionVector[iteration] = tensionVector[iteration] + noise * stddev;
			}
						
			aux = butter.lowPass(tensionVector, LPcutoff, time_step, IC);
			tensionVector = aux;
			
			aux = null;
		}
		
		
	}
		
	
	public int getNum_steps() {
		return num_steps;
	}
	
	public double[] getTimeVector() {
		return timeVector;
	}
	
	public double[] getTensionVector() {
		return tensionVector;
	}
	
	public abstract void showInputType();
	
	public abstract String getInputType();
	
	
	protected abstract double stretch_function(double t);
	
	
	/*
	public void stretch_dynamics(int iteration){
		
		double length;
		
		if (iteration == 0 || iteration == 1){
			stretch_velocity = 0;
			stretch_acceleration = 0;	
		}
		else{
			double stretch_last_last = stretchVector[iteration - 2];
			double stretch_last = stretchVector[iteration - 1];
			length = stretchVector[iteration];
			double stretch_velocity_last = (stretch_last - stretch_last_last)/time_step;
			
			stretch_velocity = (length - stretch_last)/time_step;
			stretch_acceleration = (stretch_velocity - stretch_velocity_last)/time_step;	
		}
	}
	*/
	
}
