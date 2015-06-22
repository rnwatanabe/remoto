package br.remoto.util;

import java.io.Serializable;

import br.remoto.model.ReMoto;

public class Coordenate implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private double xini;
	private double xfin;
	private double yini;
	private double yfin;
	
	private boolean active;

	
	public Coordenate()
	{
		xini = 0;
		xfin = ReMoto.T_MAX;
		yini = -ReMoto.Y_MAX;
		yfin = ReMoto.Y_MAX;
	}
	
	
	public Coordenate(double xini, double xfin, double yini, double yfin)
	{
		setXini(xini);
		setXfin(xfin);
		setYini(yini);
		setYfin(yfin);
	}
	
	public double getXfin() {
		return xfin;
	}
	public void setXfin(double xfin) {
		this.xfin = xfin;
	}
	public double getXini() {
		return xini;
	}
	public void setXini(double xini) {
		this.xini = xini;
	}
	public double getYfin() {
		return yfin;
	}
	public void setYfin(double yfin) {
		this.yfin = yfin;
	}
	public double getYini() {
		return yini;
	}
	public void setYini(double yini) {
		this.yini = yini;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	
}
