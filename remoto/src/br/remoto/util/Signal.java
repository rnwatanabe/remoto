package br.remoto.util;

import java.io.Serializable;

public class Signal implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private String type;
	private double value;
	private double time;
	
	
	public Signal(String type, double value, double time)
	{
		this.type = type;
		this.value = value;
		this.time = time;
	}


	public double getTime() {
		return time;
	}


	public void setTime(double time) {
		this.time = time;
	}


	public double getValue() {
		return value;
	}


	public void setValue(double value) {
		this.value = value;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


}
