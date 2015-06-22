package br.remoto.model.Conductance;

import java.io.Serializable;

import br.remoto.model.ReMoto;
import br.remoto.model.vo.DynamicVO;

public class SynapticConductance implements Serializable 
{
	private static final long serialVersionUID = 1L;

	protected String cd;
	protected double gmax;
	protected double tmax;
	protected double ri;
	protected double t;
	protected double endOfPulse;
	protected double delay;
	
	// Synaptic dynamics
	protected DynamicProperties dynamics;
	protected double lastSpike;
	protected double lastGmax; // for gMax dynamics
	protected double lastTmax; // for TMax dynamics

	
	public SynapticConductance(String cd, double gi, double tmax, double delay, DynamicVO dynamics)
	{
		this.cd = cd;
		this.gmax = gi;
		this.tmax = tmax;
		this.delay = delay;
		this.dynamics = new DynamicProperties( dynamics );
		
		reset();
	}
	
	
	public void reset() 
	{
		ri = 0;
		t = 0;
		
		lastSpike = -ReMoto.T_MAX;
		setLastGmax( gmax );
		setLastTmax( tmax );
	}

	
	public double getRi() {
		return ri;
	}
	public void setRi(double r) {
		this.ri = r;
	}
	public double getT() {
		return t;
	}
	public void setT(double t) {
		this.t = t;
	}
	public double getGmax() {
		return gmax;
	}
	public void setGmax(double gi) {
		this.gmax = gi;
	}
	public double getEndOfPulse() {
		return endOfPulse;
	}
	public void setEndOfPulse(double endOfPulse) {
		this.endOfPulse = endOfPulse;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public DynamicProperties getDynamics() {
		return dynamics;
	}
	public void setDynamics(DynamicProperties dynamics) {
		this.dynamics = dynamics;
	}
	public double getLastSpike() {
		return lastSpike;
	}
	public void setLastSpike(double lastSpike) {
		this.lastSpike = lastSpike;
	}
	public double getLastGmax() {
		return lastGmax;
	}
	public void setLastGmax(double lastGmax) {
		this.lastGmax = lastGmax;
	}
	public double getDelay() {
		return delay;
	}
	public void setDelay(double delay) {
		this.delay = delay;
	}
	public double getLastTmax() {
		return lastTmax;
	}
	public void setLastTmax(double lastTmax) {
		this.lastTmax = lastTmax;
	}

}
