
package br.remoto.teste;

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

public class MarkovConductance extends ConductanceVO
{
	private static final long serialVersionUID = 1L;

	protected double tau;
	protected double rInf;
	protected double r;
	protected double r0;
	protected double r1;
	protected double t0;
	protected double t1;
	protected double endOfPulse;
	protected boolean pulseOn;
	

	
	public MarkovConductance(ConductanceVO g)
	{
		super(g);
		
		gmax = g.getGmax() / 1000.0;	// transform values from nS to uS
		
		r = 0;
		rInf = (alpha * tmax)/(alpha * tmax + beta);
		tau = 1.0 / (alpha * tmax + beta);
		pulseOn = false;
		endOfPulse = -1;
	}
	
	
	public void start(double t) 
	{
    	t0 = t;
    	r0 = r;
    	pulseOn = true;
    	endOfPulse = t + tpeak;
	}

	
	public boolean isActive(double t) 
	{
		return (t - t0) > inactivePeriod;
	}
	
    
    public double getCurrent(double t, double V) 
	{
		return getValue(t) * (e - V);
	}
    
    
    public double getLastValue() 
	{
    	return gmax * r;
	}

	
    public double getValue(double t) 
	{
    	if( t <= endOfPulse )
    	{
    		r = rInf + (r0 - rInf) * Math.exp(-(t - t0)/tau);
		}
    	else
    	{
        	if( pulseOn == true )
        	{
            	t1 = t;
            	r1 = r;
            	pulseOn = false;
        	}

        	r = r1 * Math.exp(-beta*(t - t1));
    	}
    	
    	return gmax * r;
	}
	
}
