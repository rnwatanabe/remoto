
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

public class PulseNaConductance extends PulseConductance
{
	private static final long serialVersionUID = 1L;

	protected double valueM;
	protected double valueH;
	protected ActivationVariable m;
	protected ActivationVariable h;

	public PulseNaConductance(ConductanceVO gM, ConductanceVO gH)
	{
		super(gM);
		
		m = new ActivationVariable(gM.getAlpha(), gM.getBeta());
		h = new ActivationVariable(gH.getAlpha(), gH.getBeta());

		reset();
	}

	
    public void reset()
    {
		value = 0.0;
		value4thSlope = 0.0;
		state = false;
		m.reset();
		h.reset();
    }
	

    public void changeState(double t)
    {
    	super.changeState(t);

    	m.changeState(t);    	
    	h.changeState(t);    	
    }


    public double getValue(double t) 
	{
    	if( state == true )
    	{
    		if(t - t0 > pulseDuration)
    		{
    			changeState(t);
    			
    			valueM = m.getValueOn(t);
    			valueH = h.getValueOff(t);
    		}
    		else
    		{
    			valueM = m.getValueOff(t);
    			valueH = h.getValueOn(t);
    		}
    	}
    	else
    	{
    		valueM = m.getValueOn(t);
    		valueH = h.getValueOff(t);
    	}
    	
    	double lastValue = value;

    	
    	
    	value = gmax * Math.pow(valueM, 3) * valueH;
    	
    	if( Double.isNaN(value) )
    		value = lastValue;
    	
    	return value;
	}
	
}
