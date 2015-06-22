
package br.remoto.model.Conductance;

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

public class PulseKfConductance extends PulseConductance
{
	private static final long serialVersionUID = 1L;

	protected double valueN;
	protected ActivationVariable n;

	
	public PulseKfConductance(ConductanceVO g)
	{
		super(g);
		
		n = new ActivationVariable(g.getAlpha(), g.getBeta());

		reset();
	}

	
    public void reset()
    {
		value = 0.0;
		value4thSlope = 0.0;
		state = false;
		n.reset();
    }
	

    public void changeState(double t)
    {
    	super.changeState(t);

    	n.changeState(t);    	
    }
	
	
    public double getValue(double t) 
	{
    	if( state == true )
    	{
    		if(t - t0 > pulseDuration)
    		{
    			changeState(t);
    			
    			valueN = n.getValueOn(t);
    		}
    		else
    		{
    			valueN = n.getValueOff(t);
    		}
    	}
    	else
    	{
    		valueN = n.getValueOn(t);
    	}
    	
    	double lastValue = value;

    	value = gmax * Math.pow( valueN, 4 );
    	
    	if( Double.isNaN(value) )
    		value = lastValue;
    	
    	return value;
	}
	
}
