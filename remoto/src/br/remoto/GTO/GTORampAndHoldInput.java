package br.remoto.GTO;

import java.util.Random;

import br.remoto.servlet.SpindleResults;
import br.remoto.servlet.SpindleSimulation;

import java.util.Random;


public class GTORampAndHoldInput extends GTOInput{

	protected double initial_value;
	protected double final_value;
	//protected Random gaussianNoise = new Random();
	
	public GTORampAndHoldInput(String str_initial_time,String str_time_step,String str_final_time,
						String str_start_time,String str_end_time,
						String str_initial_value,String str_final_value,
						String str_isRandom, String str_stddev, String str_LPcutoff) {
		
		super(str_initial_time,str_time_step,str_final_time,
				str_start_time,str_end_time);
		
		
		double initial_value 		= Double.parseDouble(str_initial_value);
		double final_value 			= Double.parseDouble(str_final_value);
		this.isRandom	= str_isRandom;
		this.stddev	= Double.parseDouble(str_stddev);
		this.LPcutoff	= Double.parseDouble(str_LPcutoff);
		
		SpindleSimulation.inputs.put(1, this);
				
		this.setInitial_value(initial_value);
		this.setFinal_value(final_value);
	}
	

	public void setInitial_value(double initialValue) {
		initial_value = initialValue;
	}


	public void setFinal_value(double finalValue) {
		final_value = finalValue;
	}
			
	public double getInitial_value() {
		return initial_value;
	}

	public double getFinal_value() {
		return final_value;
	}
	
	
	public void showInputType() {
		System.out.println("Ramp-and-hold input");
	}
	
	
	public String getInputType() {
		return "ramp-and-hold";
	}
	
		
	public double stretch_function(double t){
		
		double ang_coef = 0;
		double ramp = 0;
		double result = 0;
		
		ang_coef = (final_value - initial_value)/(end_time - start_time);
		
		if (t < start_time) ramp = initial_value;
		else	if (t > end_time) ramp = final_value;
				else 	if (t >= start_time && t <= end_time){
							ramp = initial_value + ang_coef * (t - start_time);
						}
		
		
		result = ramp;
		return result;		
	}
	
}
