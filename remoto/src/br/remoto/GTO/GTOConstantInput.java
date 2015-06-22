package br.remoto.GTO;

import br.remoto.servlet.GTOConfig;
import br.remoto.util.ButterworthBilinear;

import java.util.Random;

public class GTOConstantInput extends GTOInput{

	protected double value;
	
		
	public GTOConstantInput(String str_initial_time,String str_time_step,String str_final_time,
						String str_value,
						String str_isRandom, String str_stddev, String str_LPcutoff) {
		
		super(str_initial_time,str_time_step,str_final_time,
				str_initial_time,str_final_time);
		
		//System.out.println("debug3");
		
		value 			= Double.parseDouble(str_value);
		this.isRandom	= str_isRandom;
		this.stddev		= Double.parseDouble(str_stddev);
		this.LPcutoff	= Double.parseDouble(str_LPcutoff);	
		this.setValue(value);
	}
	
	


	public void setValue(double val) {
		value = val;
	}

	public double getValue() {
		return value;
	}
	

	
	public void showInputType() {
		System.out.println("Constant input");
	}
	
	public String getInputType() {
		return "constant";
	}
	
		
	public double stretch_function(double t){
		
		double constant = 0;
				
		constant = value;
		
		return constant;		
	}
		
}
