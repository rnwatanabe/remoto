package br.remoto.model.vo;

import java.io.Serializable;

public class Sample implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private String label;
	private double value;
	

	public Sample(String label, double value)
	{
		setLabel(label);
		setValue(value);
	}
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
}
