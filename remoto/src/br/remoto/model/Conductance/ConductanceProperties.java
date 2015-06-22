
package br.remoto.model.Conductance;

import java.io.Serializable;


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


public class ConductanceProperties implements Serializable
{
	private static final long serialVersionUID = 1L;

	protected double gmax;
	protected double tpeak;
	protected double e;
	protected boolean active;
	protected double inactivePeriod;
	protected String compartment;

	
	public ConductanceProperties()
	{
	}


	public ConductanceProperties(ConductanceProperties ref)
	{
		this.gmax = ref.gmax;
		this.tpeak = ref.tpeak;
		this.e = ref.e;
		this.active = ref.active;
	}
	
	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public double getE() {
		return e;
	}


	public void setE(double e) {
		this.e = e;
	}


	public double getTpeak() {
		return tpeak;
	}


	public void setTpeak(double alfa) {
		this.tpeak = alfa;
	}


	public double getGmax() {
		return gmax;
	}


	public void setGmax(double saturation) {
		this.gmax = saturation;
	}


	public double getInactivePeriod() {
		return inactivePeriod;
	}


	public void setInactivePeriod(double inactivePeriod) {
		this.inactivePeriod = inactivePeriod;
	}


	public String getCompartment() {
		return compartment;
	}


	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}
	
}
