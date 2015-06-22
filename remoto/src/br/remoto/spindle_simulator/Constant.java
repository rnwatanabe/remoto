package br.remoto.spindle_simulator;

import br.remoto.servlet.SpindleResults;
import br.remoto.servlet.SpindleSimulation;
import br.remoto.util.ButterworthBilinear;

import java.util.Random;

public class Constant extends Input{

	protected double value;
	
	//protected Random gaussianNoise = new Random();
	
	
		
	public Constant(String str_initial_time,String str_time_step,String str_final_time,
						String str_value,
						String str_isRandom, String str_stddev, String str_LPcutoff) {
		
		super(str_initial_time,str_time_step,str_final_time,
				str_initial_time);
		
		
		value 		= Double.parseDouble(str_value);
		this.isRandom	= str_isRandom;
		this.stddev		= Double.parseDouble(str_stddev);
		this.LPcutoff	= Double.parseDouble(str_LPcutoff);
		
		SpindleSimulation.inputs.put(0, this);
		
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
		
	public double stretch_function(double t){
		
		double constant = 0;
		double aux = 0;
		
		
		constant = value;
		
		return constant;		
	}
		
}
