package br.remoto.model.Conductance;

import br.remoto.model.vo.ConductanceVO;

/**
 * @author leonardo
 * @since 05-10-11
 * @version 1.0  
 */

public class PulseCaConductance extends PulseConductance {
	
	private static final long serialVersionUID = 1L;

	protected double valueP;
	protected ActivationVariable p;

	
	public PulseCaConductance(ConductanceVO g)
	{
		super(g);
		
		p = new ActivationVariable(g.getAlpha(), g.getBeta());

		reset();
	}

	
    public void reset()
    {
		value = 0.0;
		value4thSlope = 0.0;
		state = false;
		p.reset();
    }
	

    public void changeState(double t)
    {
    	super.changeState(t);

    	p.changeState(t);    	
    }
	
	
    public double getValue(double t) 
	{
    	if( state == true )
    	{
    		if(t - t0 > pulseDuration)
    		{
    			changeState(t);
    			
    			valueP = p.getValueOn(t);
    		}
    		else
    		{
    			valueP = p.getValueOff(t);
    		}
    	}
    	else
    	{
    		valueP = p.getValueOn(t);
    	}
    	
    	double lastValue = value;
    	
    	value = gmax * valueP;
    	
    	if( Double.isNaN(value) )
    		value = lastValue;
    	
    	return value;
	}
}
