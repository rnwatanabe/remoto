package br.remoto.model.Conductance;

import java.io.Serializable;

import br.remoto.model.ReMoto;
import br.remoto.model.vo.DynamicVO;


public class DynamicProperties implements Serializable 
{
	private static final long serialVersionUID = 1L;

	protected int type;
	protected double tau;
	protected double variation;
	
	
	public DynamicProperties(DynamicVO vo)
	{
		if( vo == null )
			vo = new DynamicVO();
		
		this.tau = vo.getTau();
		this.variation = vo.getVariation();
		
		if( vo.getDynamicType().equals( ReMoto.noDynamics ) )
			type = ReMoto.NO_DYNAMICS;
		else if( vo.getDynamicType().equals( ReMoto.depressing ) )
			type = ReMoto.DEPRESSING;
		else if( vo.getDynamicType().equals( ReMoto.facilitating ) )
			type = ReMoto.FACILITATING;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
