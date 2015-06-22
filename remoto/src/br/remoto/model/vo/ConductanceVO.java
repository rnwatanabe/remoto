
package br.remoto.model.vo;

import br.remoto.model.ReMoto;
import br.remoto.model.Conductance.ConductanceProperties;


/*
 * Created on 09/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author RRCisi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


public class ConductanceVO extends ConductanceProperties implements Comparable 
{
	private static final long serialVersionUID = 1L;

	protected String cdConductanceType;
	
	protected String cdSimpleConductanceType;
	
	protected String cdNucleus;
	protected String cdNucleusPre;
	protected double connectivity;
	protected double delay;
	protected int index;
	protected double tmax;
	protected double alpha;
	protected double beta;
	//protected String stateVariable;
	protected DynamicVO dynamics;

	
	public ConductanceVO()
	{
	}


	public ConductanceVO(String cdConductanceType, String cdNucleus, String cdNucleusPre)
	{
		this.cdConductanceType = cdConductanceType;
		this.cdNucleus = cdNucleus;
		this.cdNucleusPre = cdNucleusPre;
	}


	public ConductanceVO(ConductanceVO ref)
	{
		super.gmax = ref.gmax;
		super.tpeak = ref.tpeak;
		super.e = ref.e;
		super.active = ref.active;
		super.inactivePeriod = ref.inactivePeriod;

		this.cdConductanceType = ref.cdConductanceType;
		this.cdNucleus = ref.cdNucleus;
		this.cdNucleusPre = ref.cdNucleusPre;
		this.dynamics = ref.dynamics;
		this.connectivity = ref.connectivity;
		this.index = ref.index;
		this.tmax = ref.tmax;
		this.alpha = ref.alpha;
		this.beta = ref.beta;
	}


	// ----------------------------
	// Specific getters and setters

	public String getPreSimple() 
	{
		String[] split = cdConductanceType.split( "-" );
		String pre = split[0];
		
		return pre;
	}


	public String getPre() 
	{
		String[] split = cdConductanceType.split( "-" );
		String pre = split[0];
		
		// Synapse from tracts
		if( cdNucleusPre.equals( ReMoto.DT ) )
		{
			String[] split2 = pre.split( " " );
			String type = split2[1];
			
			return NeuronVO.convertName( type, false );			
		}			

		// Synapses from INs
		if( ( cdNucleusPre.indexOf( "flex" ) > 0 && cdNucleus.equals( ReMoto.TA ) ) ||
			( cdNucleusPre.indexOf( "ext" ) > 0 && ( cdNucleus.equals( ReMoto.SOL ) || cdNucleus.equals( ReMoto.LG ) || cdNucleus.equals( ReMoto.MG ) ) ) )
		{
			return pre;
		}			

		// Synapse between INs
		if( ( cdNucleusPre.indexOf( "flex" ) > 0 && cdNucleus.indexOf( "ext" ) > 0 ) ||
			( cdNucleusPre.indexOf( "ext" ) > 0 && cdNucleus.indexOf( "flex" ) > 0 ) )
		{
			cdNucleusPre = "antagonist";
		}			
			
		pre = pre + " " + cdNucleusPre;
		
		return pre;
	}


	public String getPos() 
	{
		String[] split = cdConductanceType.split( "-" );
		String pos = split[1];
		
		return pos;
	}


	public String getPolarity() 
	{
		if( e >= 0 )
			return ReMoto.excitatory;
		else
			return ReMoto.inhibitory;
	}
	
	
	public String getDynamicType() 
	{
		if( dynamics == null )
			return ReMoto.none;
		else
			return dynamics.getTextDynamicType();
	}

	
	// ---------------------------
	// General getters and setters

	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public double getConnectivity() {
		return connectivity;
	}


	public void setConnectivity(double connectivity) {
		this.connectivity = connectivity;
	}


	public double getAlpha() {
		return alpha;
	}


	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}


	public double getBeta() {
		return beta;
	}


	public void setBeta(double beta) {
		this.beta = beta;
	}

	
	public double getTmax() {
		return tmax;
	}


	public void setTmax(double tmax) {
		this.tmax = tmax;
	}


	public DynamicVO getDynamics() {
		return dynamics;
	}


	public void setDynamics(DynamicVO dynamics) {
		this.dynamics = dynamics;
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


	public double getDelay() {
		return delay;
	}


	public void setDelay(double latency) {
		this.delay = latency;
	}


	public int compareTo(Object arg0) 
	{
		ConductanceVO intern = this;
		ConductanceVO extern = (ConductanceVO)arg0;
		
		String compartmentInt = intern.getCompartment();
		String compartmentExt = extern.getCompartment();
		String cdTypeInt = intern.getCdConductanceType();
		String cdTypeExt = extern.getCdConductanceType();
		String cdNucleusPreInt = intern.getCdNucleusPre();
		String cdNucleusPreExt = extern.getCdNucleusPre();
		String cdNucleusInt = intern.getCdNucleus();
		String cdNucleusExt = extern.getCdNucleus();
		String polarityInt = intern.getPolarity();
		String polarityExt = extern.getPolarity();
		
		if( compartmentInt == null ) compartmentInt = "-";
		if( compartmentExt == null ) compartmentExt = "-";
		if( cdTypeInt == null ) cdTypeInt = "";
		if( cdTypeExt == null ) cdTypeExt = "";
		if( cdNucleusPreInt == null ) cdNucleusPreInt = "";
		if( cdNucleusPreExt == null ) cdNucleusPreExt = "";
		if( cdNucleusInt == null ) cdNucleusInt = "";
		if( cdNucleusExt == null ) cdNucleusExt = "";
		
		if( compartmentInt.equals( compartmentExt ) )
		{
			if( cdTypeInt.equals( cdTypeExt ) )
			{
				if( cdNucleusPreInt.equals( cdNucleusPreExt ) )
				{
					if( cdNucleusInt.equals( cdNucleusExt ) )
					{
						return polarityInt.compareTo( polarityExt );
					}
					else
					{
						return cdNucleusInt.compareTo( cdNucleusExt );
					}
				}
				else
				{
					return cdNucleusPreInt.compareTo( cdNucleusPreExt );
				}
			}
			else
			{
				return cdTypeInt.compareTo( cdTypeExt );
			}
		}

		return compartmentExt.compareTo( compartmentInt );
	}
	
}
