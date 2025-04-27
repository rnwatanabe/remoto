
package br.remoto.model.Conductance;

import br.remoto.model.vo.ConductanceVO;

/*
 * Created on 09/01/2006
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author RRCisi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


public abstract class PulseConductance extends ConductanceProperties
{
	protected double value;
	protected double t0;
	protected double pulseDuration;
	protected boolean state;

	protected double value2thSlope = 0.0;
	protected double value4thSlope = 0.0;

	
	public PulseConductance()
	{
	}
	
	
	public PulseConductance(ConductanceVO g)
	{
		gmax = g.getGmax() / 1000;	// transform values from nS to uS
		e = g.getE();
		inactivePeriod = g.getInactivePeriod();
		pulseDuration = g.getTpeak();
	}
	
	
    public abstract double getValue(double t);

    
    public abstract void reset();

	
	public void start(double time) 
	{
		changeState(time);	
	}
	

    public void changeState(double t)
    {
    	state = !state;
    	t0 = t;
    }

    
	public double getCurrent(int slope, double t, double V) 
	{
    	// If the Runge–Kutta method is in the first or third slope, 
		// use the same value calculated for the fourth or second one,
		// in order to speed-up simulations

		if( slope == 1 )
    		return value4thSlope * (e - V);
    	else if( slope == 3 )
    		return value2thSlope * (e - V);

    	double value = getValue(t); 

    	if( slope == 2 )
    		value2thSlope = value;
    	else if( slope == 4 )
    		value4thSlope = value;
		
		return value * (e - V);
	}


    public double getLastValue() 
	{
    	return value;
	}
	
}
