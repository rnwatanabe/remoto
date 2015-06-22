package br.remoto.model.vo;

import java.io.Serializable;


public class Nucleus implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private String cd;
	private String name;
	private String layer;
	private int ind;
	
	private String joint;
	
	
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getInd() {
		return ind;
	}
	public void setInd(int ind) {
		this.ind = ind;
	}
	public String getLayer() {
		return layer;
	}
	public void setLayer(String layer) {
		this.layer = layer;
	}
	public String getJoint() {
		return joint;
	}
	public void setJoint(String joint) {
		this.joint = joint;
	}
	
}
