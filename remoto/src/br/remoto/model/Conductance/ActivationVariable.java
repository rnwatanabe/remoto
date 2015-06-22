
package br.remoto.model.Conductance;

import java.io.Serializable;


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

public class ActivationVariable implements Serializable
{
	private static final long serialVersionUID = 1L;

	protected double alpha;
	protected double beta;
	protected double value;
	protected double v0;
	protected double t0;
	
	
	public ActivationVariable()
	{
		reset();
	}
	
	
	public ActivationVariable(double alpha, double beta)
	{
		this.alpha = alpha;
		this.beta = beta;
		
		reset();
	}
	
	
	public void reset()
	{
		this.v0 = 0;
		this.t0 = 0;
	}
	
	
    public double getValueOn(double t) 
	{
    	value = v0 * Math.exp(-beta*(t - t0));
    		
    	return value;
	}

	
    public double getValueOff(double t) 
	{
    	value = 1 + (v0 - 1) * Math.exp(-alpha*(t - t0));

    	return value;
	}

	
    public void changeState(double t)
    {
    	t0 = t;
    	v0 = value;
    }
    
}
