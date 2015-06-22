package br.remoto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import br.remoto.util.Signal;



public class Muscle implements Serializable
{
	/*
	private static final long serialVersionUID = 1L;

	private MotorUnit motorunits[];
	private Hashtable mn_mu = new Hashtable(); 
	private String cdNucleus;

	private ArrayList force;
	private ArrayList emg;

	
	public Muscle(String cdNucleus, MotorUnit[] mu)
	{
		this.cdNucleus = cdNucleus;
		this.motorunits = mu;
	}
	
	
	public void reset(double sample, boolean emgAttenuation)
	{
		for(int n = 0; n < motorunits.length; n++)
		{
			motorunits[n].reset( sample, emgAttenuation );
		}
	}

	
	public double instantMuscleEMG(double t)
	{
		double output = 0;

		for(int n = 0; n < motorunits.length; n++)
		{
			output += motorunits[n].getSignal(t, MotorUnit.EMG);
		}

		return output;
	}

	
	public double instantMuscleForce(double t)
	{
		double output = 0;
		
		for(int n = 0; n < motorunits.length; n++)
		{
			output += motorunits[n].getSignal(t, MotorUnit.TWITCH);
		}

		return output;
	}


	public double instantMotorUnitEMG(String cdNeuron, double t)
	{
		// Pick mu in hashtable 
		MotorUnit mu = pickMotorUnit(cdNeuron);

        if( mu == null )
        	return 0;
        
		return mu.getSignal(t, MotorUnit.EMG);
	}


	public double instantMotorUnitForce(String cdNeuron, double t)
	{
		// Pick mu in hashtable 
		MotorUnit mu = pickMotorUnit(cdNeuron);

        if( mu == null )
        	return 0;
        
		return mu.getSignal(t, MotorUnit.TWITCH);
	}

	
	public void atualize(double t)
	{
		emg.add( new Signal( ReMoto.EMG, instantMuscleEMG(t), t) );
		force.add( new Signal( ReMoto.force, instantMuscleForce(t), t) );
	}

	
	public MotorUnit pickMotorUnit(String cdNeuron)
	{
		// Try to pick fiber in hashtable 
		MotorUnit mu = (MotorUnit)mn_mu.get(cdNeuron);
		
		if( mu == null )
		{
			for(int n = 0; n < motorunits.length; n++)
	        {
	        	String cd = motorunits[n].getCd();

	        	if( cdNeuron.equals( cd ) )
	        	{
	        		mu = motorunits[n];

	        		mn_mu.put(cdNeuron, mu);
	        		
	        		break;
	        	}
	        }
		}
		
		return mu;
	}

	
	public MotorUnit[] getMotorunits() {
		return motorunits;
	}
	
	public void setFb(MotorUnit[] mu) {
		this.motorunits = mu;
	}

	public String getCdNucleus() {
		return cdNucleus;
	}

	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}

	public ArrayList getEmg() {
		return emg;
	}

	public void setEmg(ArrayList emg) {
		this.emg = emg;
	}

	public ArrayList getForce() {
		return force;
	}

	public void setForce(ArrayList force) {
		this.force = force;
	}
	
	*/
}

