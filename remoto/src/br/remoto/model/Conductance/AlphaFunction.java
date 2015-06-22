
package br.remoto.model.Conductance;

import br.remoto.model.Neuron.Miscellaneous;
import br.remoto.model.vo.ConductanceVO;

/*
 * Created on 09/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author RRCisi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class AlphaFunction extends ConductanceVO
{
	private static final long serialVersionUID = 1L;

	protected final int n = 2; 
	protected int[] it = new int[n+1];
	protected double[] x = new double[n+1];
	protected double[] y = new double[n+1];
	protected double a1;
	protected double a2;
	protected double b0;
	protected double b1;
	//protected double gpeak;
	
	protected boolean started;
	protected Miscellaneous misc;

	
	public AlphaFunction(String type, String cdNucleus, String cdNucleusPre)
	{
		this.cdConductanceType = type;
		this.cdNucleus = cdNucleus;
		this.cdNucleusPre = cdNucleusPre;
	}

	
	public AlphaFunction(ConductanceVO g, Miscellaneous misc)
	{
		super(g);
		
		gmax = g.getGmax() / 1000.0;	// transform values from nS to uS

		reset(misc);
	}

	
	public void reset(Miscellaneous misc)
	{
		this.misc = misc;
		started = false;
		
		double step = misc.getStep();
		
		a1 = 2*Math.exp( -step/tpeak );
		a2 = - Math.exp(-( 2*step )/tpeak);
		b0 = 0;
		//b1 = step * (gpeak * step/tpeak) * Math.exp(1 - (step/tpeak));
		b1 = step * (1 * step/tpeak) * Math.exp(1 - (step/tpeak));
		
		for(int i = 0; i <= n; i++)
		{
			it[i] = 0;
			x[i] = 0;
			y[i] = 0;
		}
		
		
	}
	
	
	public boolean isStarted() {
		return started;
	}


	public void setStarted(boolean started) {
		this.started = started;
	}


	public void start(int iteration) 
	{
		if( it[n] != iteration )
		{
			iterate(iteration);
		}

		started = true;

		// New input
		x[n] = 1.0/misc.getStep();
		
		// New output
		y[n] = a1*y[n-1] + a2*y[n-2] + b1*x[n-1];

		/*
		if( y[n] > gmax )
			y[n] = gmax;
		*/
		
		
	}

	
	public boolean isActive(double timeAfterSpike) 
	{
		return timeAfterSpike > inactivePeriod;
	}
	
    
    public double getValue(int iteration) 
	{
		if( it[n] != iteration )
		{
	        iterate(iteration);

			if( !started )
				return 0;
			
			// New ouput
			y[n] = a1*y[n-1] + a2*y[n-2] + b1*x[n-1];
			
			/*
			if( y[n] > gmax )
				y[n] = gmax;
			*/
		}
		
		return y[n];
	}
    
    
    public double getLastValue() 
	{
		return y[n];
	}
	
    
    public double getCurrent(int iteration, double V) 
	{
		return getValue(iteration) * (e - V);
	}
    
    
	private void iterate(int iteration)
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
		x[n] = 0.0;
	}

	/*
	public double getGpeak() {
		return gpeak;
	}


	public void setGpeak(double gpeak) {
		this.gpeak = gpeak;
	}
	*/
	
	/*
	public double getCurrentK(int itNow, double V) 
	{
		if(tSpike < 0.1)
			return 0;
			
		double t = step * itNow - tSpike;
		
		double gK1 = 0.9 * Math.exp(-t/28.0);
        double gK2 = -0.22 * Math.exp(1 - t/6.0) * t/6.0;

        return 14.5 * (gK1 + gK2);
	}

	
	public double getCurrent(double t, double V) 
	{
		double value = 0;
		
		if( active == false )
			return 0;
		
		for(int n = spikeTrain.size() - 1; n >= 0; n--)
		{
			double tSpike = ((Double)spikeTrain.get(n)).doubleValue();

			if( tSpike > t )
				continue;

			double time = (t - tSpike)/tpeak;
			double amp = time * Math.exp(1 - time);
			
			// Break for amplitudes fewer than 1%
			if( amp < 0.01 && time > tpeak )
				break;
			
			value += gpeak * amp;
		}
		
		return value * (e - V);
	}

	
	public double getCurrentGk(double t, double V)
	{
		double value = 0;
		
		if( active == false )
			return 0;
		
		for(int n = spikeTrain.size() - 1; n >= 0; n--)
		{
			double tSpike = ((Double)spikeTrain.get(n)).doubleValue();
			double time = t - tSpike;
			double amp = amp1 * Math.exp(-time/tau1) + 
					 	 amp2 * Math.exp(1 - time/tau2) * time/tau2;
			
			// Break for amplitudes fewer than 1%
			if( amp < 0.01 && time > tau1 && time > tau2 )
				break;
			
			value += gpeak * amp;
		}
		
		return value * (e - V);
	}

	
	public double getCurrentGk(double t, double V)
	{
		if( spikeTrain.size() == 0 )
			return 0;
		
		int nSpike = spikeTrain.size() - 1;
		double tAHP = ((Double)spikeTrain.get(nSpike)).doubleValue() + ReMoto.spikeWidth + step;
		int itAHP = (int)Math.round( tAHP / step );
		int itNow = (int)Math.round( t / step );

		boolean find = false;
		int n = it.length - 2;

		for(; n < it.length; n++)
		{
			if( it[n] == itNow )
			{
				find = true;
				break;
			}
		}
		
		if( find == false )
		{
			for(n = 0; n < it.length - 1; n++)
			{
				x[n] = x[n+1]; 	
				y1[n] = y1[n+1]; 	
				y2[n] = y2[n+1]; 	
				it[n] = it[n+1];
			}
		}

		// ----------------
		// Atualize vectors
		
		// Iteration
		it[n] = itNow;

		// Input
		if( itNow == itAHP )
			x[n] = 1/step;
		else
			x[n] = 0;
		
		// Ouput
	    y1[n] = a11*y1[n-1] + b11*x[n];
	    y2[n] = a21*y2[n-1] - a22*y2[n-2] + b21*x[n-1];
	    
		return gpeak * (y1[n] + y2[n]) * (e - V);
	}

	
	private double valueKBaldissera(double t)
	{
		//return ampAcc * (amp1 * Math.exp(-(t - tSpike)/tau1));
		
		double a1 = 1.8;
		double b1 = 2.1;
		double c1 = 20;
		double a2 = 0.95;
		double b2 = 14;
		double a3 = 0.55;
		double b3 = 0.08;
		double c3 = 10;
		double d3 = 2.4;
		double e3 = 200;
		
		return	ampAcc * ( a1 * Math.exp(-(Math.pow((t - tSpike),b1))/c1) + 
						   a2 * Math.exp(-(t - tSpike)/b2) 
			              -a3 * Math.pow((t - tSpike),b3) * Math.exp(-(t - tSpike)/c3) * Math.exp(-(Math.pow((t - tSpike),d3))/e3) );
	}
	*/
	
}
