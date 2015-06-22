package br.remoto.model.vo;

import java.io.Serializable;


public class JointVO implements Serializable 
{
	private static final long serialVersionUID = 1L;

	protected String cd;
	protected String name;
	protected int numNuclei;
	protected int numMotorNuclei;
	protected int numNerves;
	protected int ind;
	
	
	public JointVO(){}
	
	
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


	public int getNumNuclei() {
		return numNuclei;
	}


	public void setNumNuclei(int numNuclei) {
		this.numNuclei = numNuclei;
	}


	public int getNumMotorNuclei() {
		return numNuclei - 2;
	}


	public void setNumMotorNuclei(int numMotorNuclei) {
		this.numMotorNuclei = numMotorNuclei;
	}


	public int getNumNerves() {
		return 2;
	}


	public void setNumNerves(int numNerves) {
		this.numNerves = numNerves;
	}
	
}
