package br.remoto.model;

import java.io.Serializable;
import java.util.Random;

import br.remoto.util.Distribution;


public class ModulatingSignal implements Serializable 
{
	private static final long serialVersionUID = 1L;

    private String cdNeuronType;
    private String cdSignal;
    private String cdNucleus;
    private String modType;
    private double tini;
    private double tfin;
    private double amp;
    private double freq;
    private double width;
    private double delay;
    private double ret;
    private double modFreq;
    private double modFactor;
    
    
    public ModulatingSignal()
    {
    	this.cdSignal = ReMoto.none;
    }
    
    
    public ModulatingSignal( String cdNeuronType, String cdNucleus )
    {
    	this.cdNeuronType = cdNeuronType;
    	this.cdNucleus = cdNucleus;
    	this.cdSignal = ReMoto.none;
    }
    
    
    public double value(double time)
    {
		if( time < tini || time > tfin + ReMoto.T_TOLERANCE )
			return 0;

        if( cdSignal.equals( ReMoto.sine ) )
    	{
        	ret = sineModulation( time );
    	}
    	else if( cdSignal.equals( ReMoto.square ) )
    	{
    		ret = squareModulation( time );
    	}
    	else if( cdSignal.equals( ReMoto.ramp ) )
    	{
    		ret = rampModulation( time );
    	}
    	else if( cdSignal.equals( ReMoto.constant ) )
    	{
    		ret = constantModulation( time );
    	}
    	else if( cdSignal.equals( ReMoto.custom ) )
    	{
    		ret = customModulation( time );
    	}
    	else if( cdSignal.equals( ReMoto.random ) )
    	{
    		ret = randomModulation( time );
    	}
    	else if( cdSignal.equals( ReMoto.triangle ) )
    	{
    		ret = triangleModulation( time );
    	}
    	else if( cdSignal.equals( ReMoto.trapezoid ) )
    	{
    		ret = trapezoidModulation( time );
    	}
    	else if( cdSignal.equals( ReMoto.stochasticGamma ) )
    	{
    		ret = stochasticGammaModulation( time );
    	}
    	else if( cdSignal.equals( ReMoto.AM ) )
    	{
    		ret = amModulation( time );
    	}
    	else
    	{
    		ret = 0;
    	}
        
        return ret;
    }

	
    public boolean isActive(double time)
    {
    	if( cdSignal.equals( ReMoto.constant ) && freq > 0.01 )
    	{
    		int cycle = (int)( time * (freq/1000) );
    		double start = cycle / (freq/1000);
    		
    		if( time > start && time < start + width + ReMoto.T_TOLERANCE )
    			return true;
    		else
    			return false;
    	}
    	
    	return true;
    }
    
    
	private double constantModulation( double time )
	{
		return amp;
	}

	
	private double sineModulation( double time )
	{
		ret = amp * Math.sin( 2*Math.PI * freq * time / 1000 + (2*Math.PI * freq * -delay / 1000));
		if (time > 120000){
			ret = ret - 7;
		}		
		return ret;
	}
	
	private double amModulation( double time )
	{
		ret = amp * Math.sin( 2*Math.PI * freq * time / 1000 + (2*Math.PI * freq * -delay / 1000)) * (1 + modFactor * Math.sin( 2*Math.PI * modFreq * time / 1000 ));
		
		return ret;
	}
	
	private double squareModulation( double time )
	{
		double period = 1000.0/freq;
		int cycle = (int)( (time - tini) / period );
		double start = tini + cycle * period;
		
		if( time > start && time < start + width + ReMoto.T_TOLERANCE )
			ret = amp;
		else
			ret = 0;
		
		return ret;
	}
	
	
	private double rampModulation( double time )
	{
		if( time - tini > width  )
			ret = amp;
		else
			ret = amp * (time - tini)/width;
		
		return ret;
	}
	
	
	private double randomModulation( double time )
	{
		double ret = amp * Math.random();

		return ret;
	}

	
	private double customModulation( double time )
	{
		if( time < 10000 + tini )
		{
			amp = 12.35;
		}
		else
		{
			amp = 13.75;
		}
		
		return squareModulation(time);
	}
	
	private double triangleModulation( double time )
	{
		if( time - tini < width )
			ret = amp * (time - tini)/width;
		else
			ret = -amp * ( (time - tini) / width ) + 2 * amp;
		
		return ret;
	}
	
	private double trapezoidModulation( double time )
	{
		if( time - tini <= width )
			ret = amp * (time - tini)/width;
		else if (time - tini > width && time - tini <= 2 * width)
			ret = amp;
		else
			ret = -amp * ( (time - tini) / (width)) +  3 * amp;
		
		return ret;
	}
	
	private double stochasticGammaModulation( double time ){
		
		return Distribution.gammaPoint(amp, width);
		
	}
	
	public double getAmp() {
		return amp;
	}
	public void setAmp(double amp) {
		this.amp = amp;
	}
	public String getCdNucleus() {
		return cdNucleus;
	}
	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
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
	public double getModFactor() {
		return modFactor;
	}
	public void setModFactor(double modFactor) {
		this.modFactor = modFactor;
	}
	public double getModFreq() {
		return modFreq;
	}
	public void setFreq(double freq) {
		this.freq = freq;
	}
	public double getTfin() {
		return tfin;
	}
	public void setTfin(double tfin) {
		this.tfin = tfin;
	}
	public double getTini() {
		return tini;
	}
	public void setTini(double tini) {
		this.tini = tini;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getDelay() {
		return delay;
	}
	public void setDelay(double delay){
		this.delay = delay;
	}
	public String getCdNeuronType() {
		return cdNeuronType;
	}
	public void setCdNeuronType(String cdNeuron) {
		this.cdNeuronType = cdNeuron;
	}
	public String getModType() {
		return modType;
	}
	public void setModType(String type) {
		this.modType = type;
	}
    
}
