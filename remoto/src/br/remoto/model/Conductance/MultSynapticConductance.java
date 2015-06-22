
package br.remoto.model.Conductance;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import br.remoto.model.ReMoto;
import br.remoto.model.Neuron.Miscellaneous;
import br.remoto.model.vo.ConductanceVO;
import br.remoto.util.Spike;



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

// -----------------------------------------
//
// Implementation of Lytton (1996) algorithm
// along with synaptic depression
//
// -----------------------------------------

public class MultSynapticConductance extends ConductanceProperties
{
	private static final long serialVersionUID = 1L;

	protected String cd;

	protected Hashtable conductances;
	protected List conductancesOn;
	protected List spikesReceived;
	protected int numberOfSpikesReceived;

	protected double gMaxTot;

	protected double Non;
	protected double Ron;
	protected double Roff;
	protected double tauOn;
	protected double tauOff;
	
	protected double tMax;
	protected double alpha;
	protected double beta;
	protected double rInf;
	protected double expFinish;

	protected double ron;
	protected double roff;
	protected double t0;
	
	protected Miscellaneous misc;

	protected double value2thSlope = 0;
	protected double value4thSlope = 0;
	
	
	public MultSynapticConductance(ConductanceVO g, String cd, Miscellaneous misc)
	{
		super(g);
		
		this.cd = cd;
		this.misc = misc;

		this.tMax = g.getTmax();
		this.alpha = g.getAlpha();
		this.beta = g.getBeta();

		rInf = (alpha * tMax)/(alpha * tMax + beta);
		tauOn = 1.0 / (alpha * tMax + beta);
		tauOff = 1.0 / beta;
		expFinish = Math.exp(-tpeak/tauOn);

		conductances = new Hashtable();
		
		reset();
	}

	
    public void reset()
	{
		Non = 0;
		Ron = 0.0;
		Roff = 0.0;
		ron = 0.0;
		roff = 0.0;
		t0 = 0.0;
		value4thSlope = 0.0;
		
		conductancesOn = new ArrayList();
		spikesReceived = new ArrayList();
		
		numberOfSpikesReceived = 0;

		Iterator it = conductances.values().iterator();
		
		while( it.hasNext() )
		{
			SynapticConductance g = (SynapticConductance)it.next();
			
			g.reset();
		}
	}
    
    
    public void addConductance(ConductanceVO cond, double weight, String cd)
	{
    	double gMax = (cond.getGmax() * weight) / 1000.0;	// transform values from nS to uS
		
		gMaxTot += gMax;
		
		SynapticConductance g = new SynapticConductance(cd, gMax, cond.getTmax(), cond.getDelay(), cond.getDynamics());
		
		conductances.put(cd, g);
	}
	
    
	// Add spike with proper synapse latency
    public void receiveSpike(double t, String cd)
	{
		active = true;
		numberOfSpikesReceived++;
		
		SynapticConductance cond = (SynapticConductance)conductances.get(cd);
		
		spikesReceived.add( new Spike(cd, t += cond.getDelay()) );
	}
    
    	
    public double getCurrent(int slope, double t, double V) 
	{
    	if( gMaxTot <= 0 )
    		return 0;
    	
    	return getValue(slope, t) * (e - V);
	}

    
    public double getValue(int slope, double t) 
	{
    	if( active == false )
    		return 0;
    	
    	// If the Runge–Kutta method is in the first or third slope, 
		// use the same value calculated for the fourth or second one,
		// in order to speed-up simulations

    	if( slope == 1 )
    		return value4thSlope;
    	else if( slope == 3 )
    		return value2thSlope;
   		
    	// Calculate conductances in on and off state
   		Ron = Non*rInf + (ron - Non*rInf) * Math.exp(-(t - t0)/tauOn);
   		Roff = roff * Math.exp(-(t - t0)/tauOff);
    	
    	// Verify whether is time to start synaptic conductances
    	while( spikesReceived.size() > 0 )
    	{
    		Spike spike = (Spike)spikesReceived.get(0);
    		
    		if( t > spike.getTime() - ReMoto.T_TOLERANCE )
    		{
    			start(t, spike.getCd());
    			
    			spikesReceived.remove(0);
    		}
    		else
   				break;
    	}

    	// Verify whether is time to finish pulse transmission
    	while( conductancesOn.size() > 0 )
    	{
   			SynapticConductance g = (SynapticConductance)conductancesOn.get(0);
   			
   			if( t > g.getEndOfPulse() - ReMoto.T_TOLERANCE )
   			{
   				stop(t, g);
   		    	
   				conductancesOn.remove(0);
   			}
   			else
   				break;
    	}
    	
    	double value = gMaxTot * (Ron + Roff);

    	// If the Runge–Kutta method is in the second or fourth slope, keep the value
    	if( slope == 2 )
    		value2thSlope = value;
    	else if( slope == 4 )
    		value4thSlope = value;
    	
    	return value;
	}

	
    public double getLastValue() 
	{
    	if( gMaxTot <= 0 )
    		return 0;
    	
    	return gMaxTot * (Ron + Roff);
	}


    // This method could use Tmax dynamics
    public void start(double t, String cd) 
	{
		// Verify which synapse is beeing started
		SynapticConductance g = (SynapticConductance)conductances.get(cd);
    	
		// Verify synaptic dynamics
    	// Depressing or facilitating dynamics
    	if( g.getDynamics().getType() != ReMoto.NO_DYNAMICS )
    	{
    		double lastSpike = g.getLastSpike();
    		double tMaxDynamics = this.tMax;
    		
    		if( lastSpike > 0 )
    		{
        		double tauDynamics = g.getDynamics().getTau();
        		double variation = g.getDynamics().getVariation();
    			double lastTmax = g.getLastTmax();

    			if( g.getDynamics().getType() == ReMoto.DEPRESSING )
    			{
            		// variation/100 = 1 - p in Capek & Esplin (1977) and in Kohn et al. (1995)
    				lastTmax = lastTmax * (1 - variation / 100); 
        			
        			tMaxDynamics = lastTmax + (tMaxDynamics - lastTmax) * (1 - Math.exp( -(t - lastSpike)/tauDynamics ));
    			}
    	    	else if( g.getDynamics().getType() == ReMoto.FACILITATING )
    			{
    	    		lastTmax = lastTmax * (1 + variation / 100); 
        			
        			tMaxDynamics = lastTmax + (tMaxDynamics - lastTmax) * (1 - Math.exp( -(t - lastSpike)/tauDynamics ));
    			}
    		}
    		
    		rInf = (alpha * tMaxDynamics)/(alpha * tMaxDynamics + beta);
    		tauOn = 1.0 / (alpha * tMaxDynamics + beta);

    		g.setLastSpike( t );
    		g.setLastTmax( tMaxDynamics );
    	}
    	
    	boolean synapseOn = false;
    	
    	// Verify if the conductance to be started is already On
		for(int i = 0; i < conductancesOn.size(); i++) 
   		{
   			SynapticConductance gOn = (SynapticConductance)conductancesOn.get(i);
   			
   			if( gOn.getCd().equals( cd ) )
   			{
   				synapseOn = true;
   				break;
   			}
    	}

		// Contribution of a single conductance in relation to all synaptic conductances of the target neuron
		double synContrib = g.getGmax() / gMaxTot;
		
		if( synapseOn == false )
		{
			double ri = g.getRi() * Math.exp(-(t - g.getT() - tpeak)/tauOff);
			
			g.setRi( ri );
			g.setT( t );
	    	ron = Ron + ri * synContrib;
	    	roff = Roff - ri * synContrib;
			Non += synContrib;
	    	
	    	t0 = t;

	    	conductancesOn.add(g);
		}

		g.setEndOfPulse( t + tpeak );
		
	}

    
    // This method could use Gmax dynamics
    public void startAlternativeDynamics(double t, String cd) 
	{
		// Contribution of a single conductance in relation to all synaptic conductances of the target neuron
		double synContrib;
		
		// Verify which synapse is beeing started
		SynapticConductance g = (SynapticConductance)conductances.get(cd);
    	
		// Verify synaptic dynamics
    	
    	// No dynamics
    	if( g.getDynamics().getType() == ReMoto.NO_DYNAMICS )
    	{
    		synContrib = g.getGmax() / gMaxTot;
    	}
    	// Depressing or facilitating dynamics
    	else
    	{
    		double lastSpike = g.getLastSpike();
    		double gMax = g.getGmax();
    		
    		if( lastSpike > 0 )
    		{
        		double tauDynamics = g.getDynamics().getTau();
        		double variation = g.getDynamics().getVariation();
    			double lastGmax = g.getLastGmax();

    			if( g.getDynamics().getType() == ReMoto.DEPRESSING )
    			{
            		// variation/100 = 1 - p in Capek & Esplin (1977) and in Kohn et al. (1995)
        			lastGmax = lastGmax * (1 - variation / 100); 
        			
        			gMax = lastGmax + (gMax - lastGmax) * (1 - Math.exp( -(t - lastSpike)/tauDynamics ));
    			}
    	    	else if( g.getDynamics().getType() == ReMoto.FACILITATING )
    			{
        			lastGmax = lastGmax * (1 + variation / 100); 
        			
        			gMax = lastGmax + (gMax - lastGmax) * (1 - Math.exp( -(t - lastSpike)/tauDynamics ));
    			}
    		}

    		synContrib = gMax / gMaxTot;
    		
    		g.setLastSpike( t );
    		g.setLastGmax( gMax );
    	}
    	
    	boolean synapseOn = false;
    	
    	// Verify if the conductance to be started is already On
		for(int i = 0; i < conductancesOn.size(); i++) 
   		{
   			SynapticConductance gOn = (SynapticConductance)conductancesOn.get(i);
   			
   			if( gOn.getCd().equals( cd ) )
   			{
   				synapseOn = true;
   				break;
   			}
    	}

		if( synapseOn == false )
		{
			double ri = g.getRi() * Math.exp(-(t - g.getT() - tpeak)/tauOff);
			
			g.setRi( ri );
			g.setT( t );
	    	ron = Ron + ri * synContrib;
	    	roff = Roff - ri * synContrib;
			Non += synContrib;
	    	
	    	t0 = t;

	    	conductancesOn.add(g);
		}

		g.setEndOfPulse( t + tpeak );
		
	}
	
    
	private void stop(double t, SynapticConductance g) 
	{
		// Contribution of a single conductance in relation to all synaptic conductances of the target neuron
		double synContrib;
		
    	if( g.getDynamics().getType() == ReMoto.NO_DYNAMICS )
    	{
    		synContrib = g.getGmax() / gMaxTot;
    	}
    	else
    	{
    		double lastSpike = g.getLastSpike();
    		double gMax = g.getGmax();
    		
    		if( lastSpike > 0 )
    			gMax = g.getLastGmax();

    		synContrib = gMax / gMaxTot;
    	}

		double ri = rInf + (g.getRi() - rInf) * expFinish;
		
		g.setRi( ri );
		
    	t0 = t;
    	ron = Ron - ri * synContrib;
    	roff = Roff + ri * synContrib;
    	Non -= synContrib;
	}


	public String getCd() {
		return cd;
	}


	public void setCd(String cd) {
		this.cd = cd;
	}


	public double getGMaxTot() {
		return gMaxTot;
	}


	public void setGMaxTot(double maxTot) {
		gMaxTot = maxTot;
	}


	public int getNumberOfSpikesReceived() {
		return numberOfSpikesReceived;
	}


	public void setNumberOfSpikesReceived(int numberOfSpikesReceived) {
		this.numberOfSpikesReceived = numberOfSpikesReceived;
	}


}
