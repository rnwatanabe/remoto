package br.remoto.GTO;

import java.util.Random;

import br.remoto.servlet.SpindleResults;
import br.remoto.servlet.SpindleSimulation;



public class GTOSinusoidalInput extends GTOInput{

	protected double amplitude;
	protected double frequency;
	protected double phase;
	protected double bias;
	//protected Random gaussianNoise = new Random();
	
	
	public GTOSinusoidalInput(String str_initial_time,String str_time_step,String str_final_time,
						String str_start_time,String str_end_time,
						String str_amplitude,String str_frequency, String str_phase, String str_bias,
						String str_isRandom, String str_stddev, String str_LPcutoff) {
		
		super(str_initial_time,str_time_step,str_final_time,
				str_start_time,str_end_time);
		
		double amplitude 		= Double.parseDouble(str_amplitude);
		double frequency 		= Double.parseDouble(str_frequency);
		double phase 			= Double.parseDouble(str_phase);
		double bias 			= Double.parseDouble(str_bias);
		this.isRandom	= str_isRandom;
		this.stddev	= Double.parseDouble(str_stddev);
		this.LPcutoff	= Double.parseDouble(str_LPcutoff);
		
		SpindleSimulation.inputs.put(2, this);
				
		this.setAmplitude(amplitude);
		this.setFrequency(frequency);
		this.setPhase(phase);
		this.setBias(bias);
	}
	
	
	
	
	public void setAmplitude(double amp) {
		amplitude = amp;
	}


	public void setFrequency(double freq) {
		frequency = freq;
	}
	public void setPhase(double phi) {
		phase = phi;
	}


	public void setBias(double Bias) {
		bias = Bias;
	}
	
	public double getAmplitude() {
		return amplitude;
	}

	public double getFrequency() {
		return frequency;
	}
	public double getPhase() {
		return phase;
	}

	public double getBias() {
		return bias;
	}
	
	
	public void showInputType() {
		System.out.println("Sinusoidal input");
	}
	
	public String getInputType() {
		return "sinusoidal";
	}
	
	
	
	public double stretch_function(double t){
		double sin = 0;
		double result = 0.0;
		
		if (t < start_time) sin = bias;
		else	if (t > end_time) sin = bias;
				else 	if (t >= start_time && t <= end_time){
							sin = bias + amplitude * Math.sin(frequency * 2 * Math.PI * t + phase * Math.PI / 180);
						}
		
		result = sin;
		return result;		
	}
		
}
