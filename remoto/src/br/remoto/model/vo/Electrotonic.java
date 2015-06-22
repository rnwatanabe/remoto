package br.remoto.model.vo;

import java.io.Serializable;

//Modified in 05-10-11 by L. A. Elias

public class Electrotonic implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private String type;
	private String gcoupling;
	private String gleakSoma;
	private String gleakDend;
	private String rnd;
	private String rns;
	private String cd;
	private String cs;
	private String tauMemb;
	private String threshold;
	private String thresholdCa;
	private String rheobase;
	
	
	public String getGcoupling() {
		return gcoupling;
	}
	public void setGcoupling(String gcoupling) {
		this.gcoupling = gcoupling;
	}
	public String getGleakDend() {
		return gleakDend;
	}
	public void setGleakDend(String gleakDend) {
		this.gleakDend = gleakDend;
	}
	public String getGleakSoma() {
		return gleakSoma;
	}
	public void setGleakSoma(String gleakSoma) {
		this.gleakSoma = gleakSoma;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getCs() {
		return cs;
	}
	public void setCs(String cs) {
		this.cs = cs;
	}
	public String getRnd() {
		return rnd;
	}
	public void setRnd(String rnd) {
		this.rnd = rnd;
	}
	public String getRns() {
		return rns;
	}
	public void setRns(String rns) {
		this.rns = rns;
	}
	public String getTauMemb() {
		return tauMemb;
	}
	public void setTauMemb(String tauMemb) {
		this.tauMemb = tauMemb;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	public String getThresholdCa() {
		return thresholdCa;
	}
	public void setThresholdCa(String thresholdCa) {
		this.thresholdCa = thresholdCa;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRheobase() {
		return rheobase;
	}
	public void setRheobase(String rheobase) {
		this.rheobase = rheobase;
	}

	
}
