package br.remoto.model.Musculotendon.Tendon.Innerveted.Lumped.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import br.remoto.model.Configuration;
import br.remoto.model.MotorUnit;
import br.remoto.model.ReMoto;
import br.remoto.model.Musculotendon.Tendon.Innerveted.Lumped.LumpedSuperClass;
import br.remoto.model.Neuron.Miscellaneous;
import br.remoto.util.Signal;


public class Lumped_Crago extends LumpedSuperClass
{

	private String cdNucleus;
	
	protected final int n = 2; 
	protected int[] it = new int[n+1];
	protected double[] x = new double[n+1];
	protected double[] y = new double[n+1];
	protected double a1;
	protected double a2;
	protected double b0;
	protected double b1;
	protected double b2;
	
	private int iteration;
	
	protected boolean started;
	protected Configuration conf;
	
	
	private double Gr = 60;// [Hz]
	private double Fn = 4; // [N]
	
	public Lumped_Crago (String cdNucleus, Configuration conf){
		super(conf);
		this.cdNucleus = cdNucleus;
		reset(conf);
		started = true;
		System.out.println("Creating Crago's Model of lumped GTO");
	}
	public double calculateIbFiringRate(double force, double t) {
		// TODO Auto-generated method stub
		
		return getValue(iteration++, force);
	}
	
	
	private double staticNonLinearity (double force){
		
		double output = Gr * Math.log(force/Fn + 1);
		
		return output;
	}
	
	
	public void reset(Configuration conf)
	{
		this.conf = conf;
		started = false;
		
		double step = conf.getStep();
		
		double den = 0.4 * Math.pow(step, 2) + 4.4 * step + 4;
		
		a1 = (0.8 * Math.pow(step, 2) - 8) / den;
		a2 = (0.4 * Math.pow(step, 2) - 4.4 * step + 4)/ den;
		b0 = (0.4 * Math.pow(step, 2) + 5.16 * step + 6.8) / den;
		b1 = (0.8 * Math.pow(step, 2) - 13.6) / den;
		b2 = (0.4 * Math.pow(step, 2) - 5.16 * step + 6.8) / den;
		
		for(int i = 0; i <= n; i++)
		{
			it[i] = 0;
			x[i] = 0;
			y[i] = 0;
		}
		
		
	}
	
	
	
	public double getValue(int iteration, double force) 
	{
		if( it[n] != iteration )
		{
	        iterate(iteration, force);

			if( !started )
				return 0;
			
			// New output
			y[n] = - a1*y[n-1] - a2*y[n-2] + b0*x[n] + b1*x[n-1] + b2*x[n-2];
		}
		
		return y[n];
	}
	
	private void iterate(int iteration, double force)
	{
		// New iteration
		for(int i = 0; i < n; i++)
		{
			it[i] = it[i+1];
			x[i] = x[i+1]; 	
			y[i] = y[i+1]; 	
		}
		
		// New values
		it[n] = iteration;
		
		x[n] =  40 * staticNonLinearity(force);

		//System.out.println("iteration: " + iteration + "       x[n]: " + x[n]);
	}
	
	
	public String getCdNucleus() {
		return cdNucleus;
	}
	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}
	
	
	
}

