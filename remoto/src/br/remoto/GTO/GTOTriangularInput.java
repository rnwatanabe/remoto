package br.remoto.GTO;

import java.util.Random;

import br.remoto.servlet.SpindleResults;
import br.remoto.servlet.SpindleSimulation;

import java.util.Random;


public class GTOTriangularInput extends GTOInput{

	protected double initial_value;
	protected double final_value;
	//protected Random gaussianNoise = new Random();
	
	public GTOTriangularInput(String str_initial_time,String str_time_step,String str_final_time,
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
		
		SpindleSimulation.inputs.put(3, this);
				
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
		System.out.println("Triangular input");
	}
	
	
	public String getInputType() {
		return "triangular";
	}
		
	public double stretch_function(double t){
		
		double ang_coef = 0;
		double triang = 0;
		double result = 0;
		double aux = 0;
		
		aux = start_time + (end_time - start_time)/2;
		
		ang_coef = 2 * (final_value - initial_value)/(end_time - start_time);
		
		if (t < start_time) 	triang = initial_value;
		else	if (t > end_time) 	triang = initial_value;
				else 	if (t >= start_time && t <= aux)		triang = initial_value + ang_coef * (t - start_time);
						else 	if (t > aux && t <= end_time)		triang = final_value - ang_coef * (t - aux);
				
		result = triang;
		return result;		
	}
		
}
