
package br.remoto.model.vo;

import br.remoto.model.ReMoto;
import br.remoto.model.Neuron.NeuronProperties;

//Modified in 05-10-11 by L. A. Elias

public class NeuronVO extends NeuronProperties implements Comparable 
{
	private static final long serialVersionUID = 1L;

	// Variable properties
	private double threshold1;
	private double threshold2;
	private double thresholdCa1;
	private double thresholdCa2;
	private double axonThreshold1;
	private double axonThreshold2;
	private double axonVelocity1;
	private double axonVelocity2;

	// Neural tracts properties
	protected double mean;
	protected double std;
	protected int order;
	protected String distribution;

	// Pool properties
	protected int quantity;

	// Electrotonic properties
	private double ri;
	private double cm;
	private double rmDend1;
	private double rmDend2;
	private double rmSoma1;
	private double rmSoma2;

	// Geometrical properties
	private double ddend1;
	private double ddend2;
	private double ldend1;
	private double ldend2;
	private double dsoma1;	
	private double dsoma2;	
	private double lsoma1;	
	private double lsoma2;	
	private double totalArea;	

	// Ionic conductances
	private ConductanceVO gNaVO = new ConductanceVO();
	private ConductanceVO gKsVO = new ConductanceVO();
	private ConductanceVO gKfVO = new ConductanceVO();
	private ConductanceVO gCaVO = new ConductanceVO();

	// For pulse based conductances
	private ConductanceVO gNaMVO = new ConductanceVO();
	private ConductanceVO gNaHVO = new ConductanceVO();
	private ConductanceVO gKNVO = new ConductanceVO();
	private ConductanceVO gKQVO = new ConductanceVO();
	private ConductanceVO gCaPVO = new ConductanceVO();

	// For modulating signals
    private String cdSignal;
    private String modType;
    private double ini;
    private double fin;
    private double amp;
    private double freq;
    private double width;
    private double delay;
    private double modFreq;
    private double modFactor;

	protected String cdNerve;
	protected String nameNucleus;

	
	public NeuronVO()
	{
		indexStoreVm1 = -1;
		indexStoreVm2 = -1;
	}
	
	
	// -------------------------
	// Gk specific gets and sets 

	public String getCategoryType() 
	{
		if( ReMoto.ALL.equals(type) || ReMoto.ALL.equals(category) )
			return ReMoto.ALL;
		else if( ReMoto.ACTIVE.equals(type) || ReMoto.ACTIVE.equals(category) )
			return ReMoto.ACTIVE;
	
		return category + " " + type;
	}


	public String getCategoryTypeForSynapse() 
	{
		if( ReMoto.ALL.equals(type) || ReMoto.ALL.equals(category) )
			return ReMoto.ALL;
		else if( ReMoto.ACTIVE.equals(type) || ReMoto.ACTIVE.equals(category) )
			return ReMoto.ACTIVE;
	
		if( category.equals( ReMoto.Noise ) )
			return convertName( type, false );
		
		return category + " " + convertName( type, false );
	}


	public String getTractName() 
	{
		return convertName( type, true );
	}

	
	public static String convertName(String name, boolean completeName) 
	{
		if( completeName == true )
		{
			if( name.equals( ReMoto.CM_ext ) )
				return ReMoto.completeName_CM_ext;
			else if( name.equals( ReMoto.RBE_ext ) )
				return ReMoto.completeName_RBE_ext;
			else if( name.equals( ReMoto.RBI_ext ) )
				return ReMoto.completeName_RBI_ext;
			else if( name.equals( ReMoto.CM_flex ) )
				return ReMoto.completeName_CM_flex;
			else if( name.equals( ReMoto.RBE_flex ) )
				return ReMoto.completeName_RBE_flex;
			else if( name.equals( ReMoto.RBI_flex ) )
				return ReMoto.completeName_RBI_flex;
			else if( name.equals( ReMoto.NoiseExcitatoryMN ) )
				return ReMoto.completeName_NoiseExcitatoryMN;
			else if( name.equals( ReMoto.NoiseInhibitoryMN ) )
				return ReMoto.completeName_NoiseInhibitoryMN;
			else if( name.equals( ReMoto.NoiseExcitatoryRC ) )
				return ReMoto.completeName_NoiseExcitatoryRC;
			else if( name.equals( ReMoto.NoiseInhibitoryRC ) )
				return ReMoto.completeName_NoiseInhibitoryRC;
		}
		else
		{
			if( name.equals( ReMoto.CM_ext ) )
				return ReMoto.name_CM_ext;
			else if( name.equals( ReMoto.RBE_ext ) )
				return ReMoto.name_RBE_ext;
			else if( name.equals( ReMoto.RBI_ext ) )
				return ReMoto.name_RBI_ext;
			else if( name.equals( ReMoto.CM_flex ) )
				return ReMoto.name_CM_flex;
			else if( name.equals( ReMoto.RBE_flex ) )
				return ReMoto.name_RBE_flex;
			else if( name.equals( ReMoto.RBI_flex ) )
				return ReMoto.name_RBI_flex;
			else if( name.equals( ReMoto.NoiseExcitatoryMN ) )
				return ReMoto.name_NoiseExcitatoryMN;
			else if( name.equals( ReMoto.NoiseInhibitoryMN ) )
				return ReMoto.name_NoiseInhibitoryMN;
			else if( name.equals( ReMoto.NoiseExcitatoryRC ) )
				return ReMoto.name_NoiseExcitatoryRC;
			else if( name.equals( ReMoto.NoiseInhibitoryRC ) )
				return ReMoto.name_NoiseInhibitoryRC;
		}
		
		return name;
	}
	
	
	public int getIndexStoreVm1() 
	{
		if( indexStoreVm1 < 1 )
		{
			return 1;
		}
		else if( indexStoreVm1 > quantity )
		{
			return quantity;
		}
		else
			return indexStoreVm1;
	}
	
	
	public int getIndexStoreVm2() 
	{
		if( indexStoreVm2 < 1 )
		{
			if( quantity % 2 == 0 )
				return quantity / 2;
			else
				return quantity / 2 + 1;
		}
		else if( indexStoreVm1 > quantity )
		{
			return quantity;
		}
		else
			return indexStoreVm2;
	}


	public int compareTo(Object arg0) 
	{
		NeuronVO intern = this;
		NeuronVO extern  = (NeuronVO)arg0;
		
		return intern.getType().compareTo( extern.getType() );
	}
	
	
	public ConductanceVO getGKfVO() {
		return gKfVO;
	}
	public void setGKfVO(ConductanceVO kfVO) {
		gKfVO = kfVO;
	}
	public ConductanceVO getGKNVO() {
		return gKNVO;
	}
	public void setGKNVO(ConductanceVO gknvo) {
		gKNVO = gknvo;
	}
	public ConductanceVO getGKQVO() {
		return gKQVO;
	}
	public void setGKQVO(ConductanceVO gkqvo) {
		gKQVO = gkqvo;
	}
	public ConductanceVO getGKsVO() {
		return gKsVO;
	}
	public void setGKsVO(ConductanceVO ksVO) {
		gKsVO = ksVO;
	}
	public ConductanceVO getGNaHVO() {
		return gNaHVO;
	}
	public void setGNaHVO(ConductanceVO naHVO) {
		gNaHVO = naHVO;
	}
	public ConductanceVO getGNaMVO() {
		return gNaMVO;
	}
	public void setGNaMVO(ConductanceVO naMVO) {
		gNaMVO = naMVO;
	}
	public ConductanceVO getGNaVO() {
		return gNaVO;
	}
	public void setGNaVO(ConductanceVO naVO) {
		gNaVO = naVO;
	}
	public ConductanceVO getGCaPVO() {
		return gCaPVO;
	}
	public void setGCaPVO(ConductanceVO caPVO) {
		gCaPVO = caPVO;
	}
	public ConductanceVO getGCaVO() {
		return gCaVO;
	}
	public void setGCaVO(ConductanceVO caVO) {
		gCaVO = caVO;
	}
	public double getAxonThreshold1() {
		return axonThreshold1;
	}
	public double getDelay() {
		return delay;
	}


	public void setDelay(double delay) {
		this.delay = delay;
	}


	public void setAxonThreshold1(double axonThreshold1) {
		this.axonThreshold1 = axonThreshold1;
	}
	public double getAxonThreshold2() {
		return axonThreshold2;
	}
	public void setAxonThreshold2(double axonThreshold2) {
		this.axonThreshold2 = axonThreshold2;
	}
	public double getAxonVelocity1() {
		return axonVelocity1;
	}
	public void setAxonVelocity1(double axonVelocity1) {
		this.axonVelocity1 = axonVelocity1;
	}
	public double getAxonVelocity2() {
		return axonVelocity2;
	}
	public void setAxonVelocity2(double axonVelocity2) {
		this.axonVelocity2 = axonVelocity2;
	}
	/*
	public double getRheobase1() {
		return rheobase1;
	}
	public void setRheobase1(double rheobase1) {
		this.rheobase1 = rheobase1;
	}
	public double getRheobase2() {
		return rheobase2;
	}
	public void setRheobase2(double rheobase2) {
		this.rheobase2 = rheobase2;
	}
	*/
	public double getThresholdCa1() {
		return thresholdCa1;
	}
	public void setThresholdCa1(double thresholdCa1) {
		this.thresholdCa1 = thresholdCa1;
	}
	public double getThresholdCa2() {
		return thresholdCa2;
	}
	public void setThresholdCa2(double thresholdCa2) {
		this.thresholdCa2 = thresholdCa2;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getCm() {
		return cm;
	}
	public void setCm(double cm) {
		this.cm = cm;
	}
	public double getRi() {
		return ri;
	}
	public void setRi(double ri) {
		this.ri = ri;
	}
	public String getDistribution() {
		return distribution;
	}
	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}
	public double getMean() {
		return mean;
	}
	public void setMean(double mean) {
		this.mean = mean;
	}
	public double getStd() {
		return std;
	}
	public void setStd(double std) {
		this.std = std;
	}
	public String getCdNerve() {
		return cdNerve;
	}
	public void setCdNerve(String nerve) {
		this.cdNerve = nerve;
	}
	public String getNameNucleus() {
		return nameNucleus;
	}
	public void setNameNucleus(String nameNucleus) {
		this.nameNucleus = nameNucleus;
	}
	public double getTotalArea() {
		return totalArea;
	}
	public void setTotalArea(double totalArea) {
		this.totalArea = totalArea;
	}


	public double getDdend1() {
		return ddend1;
	}


	public void setDdend1(double ddend1) {
		this.ddend1 = ddend1;
	}


	public double getDdend2() {
		return ddend2;
	}


	public void setDdend2(double ddend2) {
		this.ddend2 = ddend2;
	}


	public double getDsoma1() {
		return dsoma1;
	}


	public void setDsoma1(double dsoma1) {
		this.dsoma1 = dsoma1;
	}


	public double getDsoma2() {
		return dsoma2;
	}


	public void setDsoma2(double dsoma2) {
		this.dsoma2 = dsoma2;
	}


	public double getLdend1() {
		return ldend1;
	}


	public void setLdend1(double ldend1) {
		this.ldend1 = ldend1;
	}


	public double getLdend2() {
		return ldend2;
	}


	public void setLdend2(double ldend2) {
		this.ldend2 = ldend2;
	}


	public double getLsoma1() {
		return lsoma1;
	}


	public void setLsoma1(double lsoma1) {
		this.lsoma1 = lsoma1;
	}


	public double getLsoma2() {
		return lsoma2;
	}


	public void setLsoma2(double lsoma2) {
		this.lsoma2 = lsoma2;
	}


	public double getRmDend1() {
		return rmDend1;
	}


	public void setRmDend1(double rmDend1) {
		this.rmDend1 = rmDend1;
	}


	public double getRmDend2() {
		return rmDend2;
	}


	public void setRmDend2(double rmDend2) {
		this.rmDend2 = rmDend2;
	}


	public double getRmSoma1() {
		return rmSoma1;
	}


	public void setRmSoma1(double rmSoma1) {
		this.rmSoma1 = rmSoma1;
	}


	public double getRmSoma2() {
		return rmSoma2;
	}


	public void setRmSoma2(double rmSoma2) {
		this.rmSoma2 = rmSoma2;
	}


	public double getAmp() {
		return amp;
	}


	public void setAmp(double amp) {
		this.amp = amp;
	}


	public String getCdSignal() {
		return cdSignal;
	}


	public void setCdSignal(String cdSignal) {
		this.cdSignal = cdSignal;
	}


	public double getFreq() {
		return freq;
	}


	public void setModFreq(double modFreq) {
		this.modFreq = modFreq;
	}
	
	public double getModFreq() {
		return modFreq;
	}
	
	public void setModFactor(double modFactor) {
		this.modFactor = modFactor;
	}
	
	public double getModFactor() {
		return modFactor;
	}


	public void setFreq(double freq) {
		this.freq = freq;
	}


	public String getModType() {
		return modType;
	}


	public void setModType(String type) {
		this.modType = type;
	}


	public double getWidth() {
		return width;
	}


	public void setWidth(double width) {
		this.width = width;
	}

	
	public double getFin() {
		return fin;
	}
	
	
	public void setFin(double fin) {
		this.fin = fin;
	}
	
	
	public double getIni() {
		return ini;
	}

	
	public void setIni(double ini) {
		this.ini = ini;
	}

	
	public double getThreshold1() {
		return threshold1;
	}
	

	public void setThreshold1(double threshold1) {
		this.threshold1 = threshold1;
	}


	public double getThreshold2() {
		return threshold2;
	}


	public void setThreshold2(double threshold2) {
		this.threshold2 = threshold2;
	}


	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}
	
}
