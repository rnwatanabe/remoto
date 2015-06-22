
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

public class PulseKsConductance extends PulseConductance
{
	private static final long serialVersionUID = 1L;

	protected double valueQ;
	protected ActivationVariable q;

	
	public PulseKsConductance(ConductanceVO g)
	{
		super(g);
		
		q = new ActivationVariable(g.getAlpha(), g.getBeta());

		reset();
	}

	
    public void reset()
    {
		value = 0.0;
		value4thSlope = 0.0;
		state = false;
		q.reset();
    }
	

    public void changeState(double t)
    {
    	super.changeState(t);

    	q.changeState(t);    	
    }
	
	
    public double getValue(double t) 
	{
    	if( state == true )
    	{
    		if(t - t0 > pulseDuration)
    		{
    			changeState(t);
    			
    			valueQ = q.getValueOn(t);
    		}
    		else
    		{
    			valueQ = q.getValueOff(t);
    		}
    	}
    	else
    	{
    		valueQ = q.getValueOn(t);
    	}
    	
    	double lastValue = value;

    	value = gmax * Math.pow( valueQ, 2 );
    	
    	if( Double.isNaN(value) )
    		value = lastValue;
    	
    	return value;
	}
	
}
