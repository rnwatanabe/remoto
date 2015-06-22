package br.remoto.old;

public class Noise 
{
	protected boolean active;
	
	protected String cdNucleus;
	protected String cdNeuronType;
	protected double f0;
	protected double std;
	protected double K;
	protected double step;
	protected double w0;
	
	protected int set;
	protected double gset;
	protected double iNoise;
	

	public Noise()
	{
		active = false;
		K = 1;
	}
	
	
	public Noise(String cdNucleus, String cdNeuronType, boolean active, double f0, double std)
	{
		this();
		
		setCdNucleus(cdNucleus);
		setCdNeuronType(cdNeuronType);
		setActive(active);
		setF0(f0);
		setStd(std);
	}
	
	
	public void setF0(double f0) 
	{
		this.w0 = (double)(2.0 * Math.PI * f0/1000.0);
		this.f0 = f0;
	}


	public double getCurrent()
	{ 
		// Null noise
		if( std < 0.001 || f0 < 0.001 )
			return 0;
		
		iNoise = K * Math.exp(-step*w0) * iNoise + std * Math.sqrt(1.0 - Math.exp(-2.0*step*w0)) * normalNoise();
		
		return iNoise; 
	}


	// This noise should be a 0 mean, 1 of variance noise
	public double normalNoise()
	{
		return 2.0 * Math.random() - 1.0;
	}


	// This noise should be a 0 mean, 1 of variance noise
	public double normalNoise_OLD()
	{
		double fac, rsq, v1, v2;

		if( set == 0 )
		{
			do
			{
				v1 = 2.0 * Math.random() - 1.0;
				v2 = 2.0 * Math.random() - 1.0;
				rsq = v1*v1 + v2*v2;
			}
			while( rsq >= 1.0 || rsq == 0.0 );
			
			fac = Math.sqrt(-2.0* Math.log(rsq)/rsq);
			gset = v1 * fac;
			set = 1;
			
			return v2 * fac;
		}
		else
		{
			set = 0;
			return gset;
		}
	}


	public double getStd() {
		return std;
	}
	public double getF0() {
		return f0;
	}
	public double getStep() {
		return step;
	}
	public double getK() {
		return K;
	}
	public void setK(double max) {
		K = max;
	}
	public double getW0() {
		return w0;
	}
	public void setW0(double w0) {
		this.w0 = w0;
	}
	public void setStd(double std) {
		this.std = std;
	}
	public void setStep(double step) {
		this.step = step;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getCdNeuronType() {
		return cdNeuronType;
	}
	public void setCdNeuronType(String cdNeuronType) {
		this.cdNeuronType = cdNeuronType;
	}
	public String getCdNucleus() {
		return cdNucleus;
	}
	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}
	
}
