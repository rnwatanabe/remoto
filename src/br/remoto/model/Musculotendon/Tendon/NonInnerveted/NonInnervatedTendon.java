package br.remoto.model.Musculotendon.Tendon.NonInnerveted;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import br.remoto.model.Configuration;
import br.remoto.model.MotorUnit;
import br.remoto.model.ReMoto;
import br.remoto.model.Musculotendon.Tendon.TendonSuperClass;
import br.remoto.util.Sample;
import br.remoto.util.Signal;


public class NonInnervatedTendon extends TendonSuperClass
{
	private double length;
	private double force;
	private double lengthNorm;
	private double forceNorm = 0;
	private double muscleMaximumForce;
	
	protected ArrayList lengthStore = new ArrayList();
	protected ArrayList forceStore = new ArrayList();
	
	private double cT;
	private double kT;
	private double LrT;
	private double L_slack_T;
	private double L0T;

	public NonInnervatedTendon(Configuration conf, double muscleOptimalLength, double muscleMaximumForce, double slackLength, double c_T, double k_T, double Lr_T)
	{
		super(conf);
		this.muscleMaximumForce = muscleMaximumForce;
		
		//New Parameters
		cT = c_T;
		kT = k_T;
		LrT = Lr_T;
		L_slack_T   = slackLength;
		L0T = 1.033 * L_slack_T;
		
		sampler1 = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		sampler2 = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		}
	
	public void atualize(double t, double musculotendonLength, double muscleLength, double pennationAngle)
	{
		double disturbance = calculateDisturbance(t);
		
		length = musculotendonLength - muscleLength *  Math.cos(pennationAngle) + 0*disturbance;
		lengthNorm = length / L0T;
		
		//System.out.println(lengthNorm);
		
		forceNorm = cT * kT * Math.log(Math.exp((lengthNorm - LrT) / kT) + 1);
		
		//System.out.println(forceNorm);
		
		force = forceNorm * muscleMaximumForce;

		sampler1.sample(lengthStore, "", t, length);
		sampler2.sample(forceStore, "", t, force);
	}

	
	public double getSlackLengthNorm() {
		return L_slack_T;
	}
	
	public void setSlackLengthNorm(double slackLengthNorm) {
		this.L_slack_T = slackLengthNorm;
	}
	
	public double getLength() {
		return length;
	}
	
	public void setLength(double length) {
		this.length = length;
	}
	public double getForce() {
		return force;
	}
	public void setForce(double force) {
		this.force = force;
	}

	public double getLengthNorm() {
		return lengthNorm;
	}

	public void setLengthNorm(double lengthNorm) {
		this.lengthNorm = lengthNorm;
	}

	public ArrayList getLengthStore() {
		return lengthStore;
	}

	public ArrayList getForceStore() {
		return forceStore;
	}
	
	private double calculateDisturbance (double t){
		
		double disturbanceTorque = 0.00005 * Math.sin( 2 * Math.PI * 20 * t / 1000) * 
				(1 + 0.5 * Math.sin( 2 * Math.PI * t / 1000));
				
			
		return disturbanceTorque;		
	}
}