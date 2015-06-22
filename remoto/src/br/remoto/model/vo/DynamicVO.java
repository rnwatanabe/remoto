package br.remoto.model.vo;

import java.io.Serializable;

import br.remoto.model.ReMoto;

public class DynamicVO implements Serializable 
{
	private static final long serialVersionUID = 1L;

	protected String cdConductanceType;
	protected String cdNucleus;
	protected String cdNucleusPre;
	protected String dynamicType;
	protected double tau;
	protected double variation;
	
	
	public DynamicVO()
	{
		dynamicType = ReMoto.noDynamics;
		tau = 0;
		variation = 0;
	}
	
	
	public String getTextDynamicType() 
	{
		if( dynamicType == null )
			return ReMoto.none;
		else if( dynamicType.equals( ReMoto.depressing ) )
			return "Depressing";
		else if( dynamicType.equals( ReMoto.facilitating ) )
			return "Facilitating";
		
		return ReMoto.none;
	}

	public String getCdConductanceType() {
		return cdConductanceType;
	}
	public void setCdConductanceType(String cdConductanceType) {
		this.cdConductanceType = cdConductanceType;
	}
	public String getCdNucleus() {
		return cdNucleus;
	}
	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}
	public String getCdNucleusPre() {
		return cdNucleusPre;
	}
	public void setCdNucleusPre(String cdNucleusPre) {
		this.cdNucleusPre = cdNucleusPre;
	}
	public String getDynamicType() {
		return dynamicType;
	}
	public void setDynamicType(String dynamicType) {
		this.dynamicType = dynamicType;
	}
	public double getTau() {
		return tau;
	}
	public void setTau(double tau) {
		this.tau = tau;
	}
	public double getVariation() {
		return variation;
	}
	public void setVariation(double variation) {
		this.variation = variation;
	}

}
