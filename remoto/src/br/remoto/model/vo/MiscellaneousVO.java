package br.remoto.model.vo;

import java.io.Serializable;

public class MiscellaneousVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	public String property;
	public String description;
	public double value;
	public int index;
	public boolean division;
	
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public boolean isDivision() {
		return division;
	}
	public void setDivision(boolean division) {
		this.division = division;
	}
	
}
