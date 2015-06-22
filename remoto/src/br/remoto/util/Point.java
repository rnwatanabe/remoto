package br.remoto.util;

import java.io.Serializable;

public class Point implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private String index;
	private double x;
	private double y;
	private double z;
	
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}

}

