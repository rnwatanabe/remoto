package br.remoto.model;


import java.util.ArrayList;

import br.remoto.model.Neuron.Miscellaneous;
import br.remoto.util.Signal;

public class Ankle
{
	/*
	protected Muscle soleus;
	protected ArrayList delayList;

	protected Miscellaneous miscellaneous;

	double angle;
	double y;		// y = dTheta/dt
	
	double deltaL = 2;
	double m = 1;
	double g = 10;
	double J = 1;
	double Gr = 1;
	double delay = 5;

	
	protected void atualizeTheta(double t)
	{
		if( t < delay )
			return;
		
		double torque1 = soleus.instantMuscleForce(t) * deltaL;
		double torque2 = soleus.instantMuscleForce(t) * deltaL; // it is getting, automatically, a new value, one step ahead
		
		try
		{
			double k1 = dTheta_dt( 1, torque1, torque2,	angle );
			double k2 = dTheta_dt( 2, torque1, torque2,	angle + miscellaneous.getStepByTwo() * k1 );
			double k3 = dTheta_dt( 3, torque1, torque2,	angle + miscellaneous.getStepByTwo() * k2 );
			double k4 = dTheta_dt( 4, torque1, torque2,	angle + miscellaneous.getStep() * k3 );
	
			angle = angle + miscellaneous.getStepBySix() * (k1 + 2*k2 + 2*k3 + k4);
		}
		catch(Exception e)
		{
			System.out.println( "atualizeTheta: " + e.getMessage() );
		}
		
		double feedback = angle * Gr;
		
		delayList.add( new Signal(ReMoto.PDController, feedback, t + delay) );
	}
	
	
	protected double dTheta_dt(int slope, double torque1, double torque2, double theta)
	{
		atualizeY(slope, torque1, torque2,  theta);
		
		return y;
	}
	
	
	protected void atualizeY(int slopePre, double torque1, double torque2, double theta)
	{
		try
		{
			double k1 = dY_dt( slopePre, 1, torque1, torque2,	theta, y );
			double k2 = dY_dt( slopePre, 2, torque1, torque2,	theta, y + miscellaneous.getStepByTwo() * k1 );
			double k3 = dY_dt( slopePre, 3, torque1, torque2,	theta, y + miscellaneous.getStepByTwo() * k2 );
			double k4 = dY_dt( slopePre, 4, torque1, torque2,	theta, y + miscellaneous.getStep() * k3 );
	
			y = y + miscellaneous.getStepBySix() * (k1 + 2*k2 + 2*k3 + k4);
		}
		catch(Exception e)
		{
			System.out.println( "atualizeY: " + e.getMessage() );
		}
	}
	
	
	protected double dY_dt(int slopePre, int slope, double torque1, double torque2, double theta, double y)
	{
		double torque = 0;
		
		if( slopePre == 4 || slope == 4 )
			torque = torque2;
		else
			torque = torque1;
		
		double d2ThetaDt = ( m * g * theta + torque ) / J;
		
		return d2ThetaDt;
	}


	public double getY() {
		return y;
	}


	public void setY(double thetaDt) {
		y = thetaDt;
	}


	public double getG() {
		return g;
	}


	public void setG(double g) {
		this.g = g;
	}


	public double getJ() {
		return J;
	}


	public void setJ(double j) {
		J = j;
	}


	public double getM() {
		return m;
	}


	public void setM(double m) {
		this.m = m;
	}


	public Miscellaneous getMiscellaneous() {
		return miscellaneous;
	}


	public void setMiscellaneous(Miscellaneous miscellaneous) {
		this.miscellaneous = miscellaneous;
	}


	public double getAngle() {
		return angle;
	}


	public void setAngle(double angle) {
		this.angle = angle;
	}


	public Muscle getSoleus() {
		return soleus;
	}


	public void setSoleus(Muscle soleus) {
		this.soleus = soleus;
	}


	public ArrayList getDelayList() {
		return delayList;
	}


	public void setDelayList(ArrayList delayList) {
		this.delayList = delayList;
	}

	*/
}
