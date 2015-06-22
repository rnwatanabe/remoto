package br.remoto.util;

import java.io.Serializable;

public class Spike implements Serializable  
{
	private static final long serialVersionUID = 1L;

	private String cd;
	private double time;
	
	
	public Spike(String type, double time)
	{
		this.cd = type;
		this.time = time;
	}


	public double getTime() {
		return time;
	}


	public void setTime(double time) {
		this.time = time;
	}


	public String getCd() {
		return cd;
	}


	public void setCd(String type) {
		this.cd = type;
	}


}
