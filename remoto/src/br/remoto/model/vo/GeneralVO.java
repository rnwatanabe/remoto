package br.remoto.model.vo;

import java.io.Serializable;


public class GeneralVO implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private int id;
	private double tFin;
	private String name;
	private String description;
	private String cdUser;
	private boolean merge;
	
	private double step;

	private boolean storeResults;
	private boolean storeSignals;
	private boolean keepProperties;


	public GeneralVO()
	{
		storeSignals = true;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getTFin() {
		return tFin;
	}
	public void setTFin(double fin) {
		tFin = fin;
	}
	
	//testing
	public double getStep() {
		return step;
	}
	public void setStep(double step) {
		this.step = step;
	}
	
	
	public String getCdUser() {
		return cdUser;
	}
	public void setCdUser(String cdUser) {
		this.cdUser = cdUser;
	}
	public boolean isMerge() {
		return merge;
	}
	public void setMerge(boolean merge) {
		this.merge = merge;
	}
	public boolean isKeepProperties() {
		return keepProperties;
	}
	public void setKeepProperties(boolean keepProperties) {
		this.keepProperties = keepProperties;
	}
	public boolean isStoreResults() {
		return storeResults;
	}
	public void setStoreResults(boolean store) {
		this.storeResults = store;
	}
	public boolean isStoreSignals() {
		return storeSignals;
	}
	public void setStoreSignals(boolean storeSignals) {
		this.storeSignals = storeSignals;
	}
	
}