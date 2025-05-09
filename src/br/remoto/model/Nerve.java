package br.remoto.model;


import br.remoto.model.vo.NerveVO;


public class Nerve extends NerveVO
{
	private static final long serialVersionUID = 1L;

	protected double intensity;

	
	
	//testing
	protected double period;
	protected double start = 0;
	protected double start_aux = 0;
	protected double next_start = 0;
	protected double finish = 1;
	protected int cont = 0;
	
	public double getPeriod() {
		return period;
	}


	public void setPeriod(double period) {
		this.period = period;
	}
	
	
	
	
	public Nerve()
	{
	}
	
	
	public Nerve(NerveVO vo, double step)
	{
		this.cdNerve = vo.getCdNerve();
		this.tini = vo.getTini();
		this.tfin = vo.getTfin();
		this.amp = vo.getAmp();
		this.freq = vo.getFreq();
		this.modFreq = vo.getModFreq();
		this.active = vo.isActive();
		this.index = vo.getIndex();
		this.stimulusSpinalEntry = vo.getStimulusSpinalEntry();
		this.stimulusEndPlate = vo.getStimulusEndPlate(); 
		this.setPaired( vo.isPaired() );
		this.setDelayPaired( vo.getDelayPaired() );
		this.setAmpPaired( vo.getAmpPaired() );
		
		this.signal = vo.getSignal();
		this.cdSignal = vo.getCdSignal();
		this.modulating_amp = vo.getModulating_amp();
		this.modulating_freq = vo.getModulating_freq();
		this.width = vo.getWidth();
		this.delay = vo.getDelay();
		
		this.start = tini;
		this.start_aux = tini;
		finish = start + ReMoto.pulseWidth;
		
		
		if( freq < 1000/ReMoto.T_MAX )
			freq = 0;
		else if( freq > 1000/step )
			freq = 1000/step;

		if( freqPaired < 1000/ReMoto.T_MAX )
			freqPaired = freq;
	}
	
	
	public void atualize(double t)
	{
		intensity = 0;

		if( t < tini || t > tfin )
			return;
		
		period = 1000.0/(freq + signal.value(t));
		
		if(Math.abs(t - start) >= period){
			start_aux = start;
			start = start_aux + period;
		}
		
		if(Math.abs(t - start) <= ReMoto.pulseWidth)
			intensity = amp;
		
		/*
		if( t >= start && t < finish){
			intensity = amp;
			cont = 0;
		}
		else{
			if(cont == 1) start_aux = start - ReMoto.pulseWidth;
			//if(cont == 1) start_aux = start;
			start = start_aux + period;
			finish = start + ReMoto.pulseWidth;
		}
		*/
				
		cont++;
		
		if( paired == true )
			atualizePaired(t);
	}
	
	
	public void atualizePaired(double t)
	{
		double tIniPaired = tini + delayPaired;
		
		if( t < tIniPaired || t > tfin )
			return;
		
		double period = 1000.0/freqPaired;
		int cycle = (int)( (t - tIniPaired) / period );
		double start = tIniPaired + cycle * period;
		double finish = start + ReMoto.pulseWidth;
		
		if( t > start && t < finish + ReMoto.T_TOLERANCE )
			intensity += ampPaired;
	}


	public double getIntensity() {
		return intensity;
	}


	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}

	
}
