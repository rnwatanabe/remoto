package br.remoto.util;

import java.util.ArrayList;


public class Sample 
{
	
	private double frequency;
	private double baseFrequency;
	private int it;
	private int order;
	
	public Sample (double frequency, double baseFrequency){

		this.frequency = frequency;
		this.baseFrequency = baseFrequency;
		
		order = (int) (baseFrequency / frequency);
		
		//System.out.println("Order: " + order);
		
		it = 0;
	}
	
	public void sample(ArrayList storeList, String index, double t, double y){
		
		if(it == 0 || it == order){
			storeList.add(new Signal( index, y, t));
			it = 0;
		}
		
		it++;
		
	}
	
	
}
