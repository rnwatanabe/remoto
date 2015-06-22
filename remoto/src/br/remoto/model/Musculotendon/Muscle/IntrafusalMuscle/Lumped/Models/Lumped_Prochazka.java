package br.remoto.model.Musculotendon.Muscle.IntrafusalMuscle.Lumped.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import br.remoto.model.Configuration;
import br.remoto.model.MotorUnit;
import br.remoto.model.ReMoto;
import br.remoto.model.Musculotendon.Muscle.IntrafusalMuscle.Lumped.LumpedSuperClass;
import br.remoto.util.Signal;



public class Lumped_Prochazka extends LumpedSuperClass
{

	public Lumped_Prochazka(Configuration conf, String cdNucleus) {
		super(conf, cdNucleus);
		System.out.println("Creating Prochazka's Model of Lumped Muscle Spindle");
	}

	
	
	public double calculateIaFiringRate(){
		
		//System.out.println("stretch_velocity" + stretch_velocity);
		
		double output;
		double aux = Math.pow(Math.abs(stretchVelocity), 0.6);
		
		if(stretchVelocity >= 0)
			output = 80 * aux + 250 * (lengthNorm - 1.00) + 15;
		else
			output = -80 * aux + 250 * (lengthNorm - 1.00) + 15;	
		
		if(output < 0)
			output = 0;
		
		return output;
	}
	
	public double calculateIIFiringRate(){
		
		double output;
		
		output = 250 * (lengthNorm - 1.00) + 8;
		
		if(output < 0)
			output = 0;
		
		return output;
	}
	

	@Override
	public double calculateFusimotorActivation(double t) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double calculatePrimaryAfferentActivity(double t) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double calculateSecondaryAfferentActivity(double t) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double calculateFiberTension(double t) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	protected void betaAndGammaFuncs(double t) {
		// TODO Auto-generated method stub
		
	}
	
}

