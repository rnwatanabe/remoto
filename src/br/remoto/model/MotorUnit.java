
package br.remoto.model;

import java.io.Serializable;
import java.util.ArrayList;

import br.remoto.model.Conductance.AlphaFunction;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.Models.Raikova;
import br.remoto.model.Neuron.Motoneuron;
import br.remoto.model.Neuron.Miscellaneous;


public class MotorUnit implements Serializable
{
	private static final long serialVersionUID = 1L;

	protected ArrayList endPlateSpikeTrain;
	//protected AlphaFunction twitchFunction;
	//protected RaikovaFunction twitchFunction2;

	protected double posY;
	protected double posZ;
	protected double attenuationToSkin;
	protected double timeWidening;

	protected double ampEMG;
	protected double lambdaEMG;
	
	protected String cd;
	protected String type;
	protected String cdNucleus;
	protected String cdMuscleModel;
	
	protected int index;
	protected int indexSpike;
	protected int iteration;
	protected double tSpike;
	
	protected int indexSpike_aux;
	protected int iteration_aux;
	protected double tSpike_aux;
	
	protected double Gpeak;
	protected double Gmax;
	protected double Tpeak;
	
	protected double GpeakRaikova;
	protected double TpeakRaikova;
	protected double THalfRaikova;
	
	protected double B;
	protected double TwTet;
	
	protected double BRaikova;
	protected double TwTetRaikova;

	protected double tPreviousSpike;
	
	protected double latencyStimulusEndPlate;
	
	protected boolean emgAttenuation;
	protected int hrType;
	
	// For some mus, store internal signals
	protected boolean storedSignals;
	
	protected Miscellaneous miscellaneous;

	public static final int EMG = 1;
	public static final int TWITCH = 2;

	
	public MotorUnit( Motoneuron mn , String muscle_model)
	{
		this.latencyStimulusEndPlate = mn.getLatencyStimulusEndPlate();
		this.endPlateSpikeTrain = mn.getTerminalSpikeTrain();
		this.index = mn.getIndex();
		this.type = mn.getType();
		this.cdNucleus = mn.getCdNucleus();
		this.cd = mn.getCd();
		this.miscellaneous = mn.getMiscellaneous();
		this.cdMuscleModel = muscle_model;
		this.storedSignals = mn.isStoredSignals();
		
		if( index%2 == 0 )
			hrType = 1;
		else
			hrType = 2;
	}

	
	public void reset( double sample, boolean emgAttenuation )
	{
		//System.out.println("index: " + index);
		//System.out.println("indexSpike: " + indexSpike);
		//System.out.println("iteration: " + iteration);
		//System.out.println("tSpike: " + tSpike);
    	
		this.emgAttenuation = emgAttenuation;

		//miscellaneous.setStep( sample );
		//twitchFunction.reset( miscellaneous );
		
		indexSpike_aux = 0;
		iteration_aux = 0;
		
		if( getNumberOfSpikesAtEndPlate() > 0 )
			tSpike_aux = getEndPlateSpike(0);
		else
			tSpike_aux = ReMoto.T_MAX;
	}
	
	
	
	
	public double getEMGSignal(double t)
	{
		double out = 0;
		
		if( getNumberOfSpikesAtEndPlate() == 0 )
			return 0;
		
		if( t > tSpike_aux )
		{
			indexSpike_aux++;
			
			if( getNumberOfSpikesAtEndPlate() > indexSpike_aux )
			{
				tSpike_aux = getEndPlateSpike(indexSpike_aux);
			}
			else
			{
				
				tSpike_aux = ReMoto.T_MAX;
				indexSpike_aux = getNumberOfSpikesAtEndPlate() - 1;				
			}
		}
		
		double timeCte = lambdaEMG;
		
		if( emgAttenuation == true )
		{
			timeCte *= timeWidening;
		}
		
		for(int n = indexSpike_aux; n >= 0; n--)
		{
			tPreviousSpike = getEndPlateSpike(n);
			
			if( tPreviousSpike > t )
				continue;

			double value = 0;

			// For complete EMG graphic
			// According to Zhou et al. J. Neuroph.
			// Matlab: -exp(-(0.75*t).^2/2).*((0.75*t).^2-1)
			// double arg = Math.pow( 0.75*(t - tSpike - durEMG/2), 2);
			// out += ampEMG * -Math.exp(-arg/2) * (arg - 1);
			// hrA - hrB indica a amplificacao diferencial de 2 eletrodos

			// Matlab:	hr2 = k2 .* (1 - 2 * (t - 3*ld).^2/ld^2) .* exp(-(t - 3*ld).^2/ld^2);
			//			hr22 = k2 .* (1 - 2 * (tt - 3*ld).^2/ld^2) .* exp(-(tt - 3*ld).^2/ld^2);
			//			plot(t, (hr2 - hr22) .* (-t./10 + 0.5));
			
			double tA = t - tPreviousSpike - 3 * timeCte;
			
			// Break when signal reaches its resting value
			if( tA > 6 * timeCte )
				break;
			
			double term = Math.pow(tA/timeCte, 2);
			double aux;
						
			if( hrType == 1 ){
				double tp = timeCte / Math.sqrt(2);
				aux = tp * Math.exp(-Math.pow(tp/timeCte, 2));
				value = (1/aux) * tA * Math.exp(-term);		// shape HR1
			}
			else{
				value = ( 1 - 2 * term ) * Math.exp(-term);	// shape HR2
			}
						
			out += ampEMG * value;
		}
		
		if( emgAttenuation == true )
		{
			out *= attenuationToSkin;
		}
		
		return out;
	}


	public void randomSpatialDistribution(double muscleThickness, double skinLayer, double tauAttenuation, double cteWidening)
	{
		// Place MU in a random position inside a muscle

		int cont = 0;
		double y = 2;
		double z = 2;
		
		// Pick a position inside the unitary circle
		while( Math.pow(y, 2) + Math.pow(z, 2) >= 1 )
		{
			y = 2 * (0.5 - Math.random());
			z = 2 * (0.5 - Math.random());
			
			cont++;
			
			// Give up, and pick a position in the z diameter
			if( cont > 10 )
			{
				z = 0;
				break;
			}
		}
		
		y = y * (muscleThickness/2) + (muscleThickness/2) + skinLayer;
		z *= (muscleThickness/2);

		/* Do not work!
		double y = 2 * (0.5 - Math.random());
		double maxZ = Math.sqrt( 1 - Math.pow(y, 2) );
		double z = 2 * (0.5 - Math.random()) * maxZ;
		
		y = y * (muscleThickness/2) + (muscleThickness/2) + skinLayer;
		z *= (muscleThickness/2);
		*/
		
		/* Do not work, also!
		double radius = (muscleThickness/2) * Math.random();
		double angle = 360 * Math.random();
		
		double y = radius * Math.sin( angle );
		double z = radius * Math.cos( angle );
		*/
		
		double distance = Math.sqrt( Math.pow( z, 2) + Math.pow( y, 2) );
		
		posY = y;
		posZ = z;

		// Fitted to Fuglevand et al. 1992 - Biological Cybernetics
		attenuationToSkin = Math.exp( -distance/tauAttenuation );

		// Fitted to Hermens et al. 1992 - IEEE EMBS 
		timeWidening = 1 + cteWidening * distance;
	}


	public void linearSpatialDistribution(double muscleThickness, double skinLayer, double tauAttenuation, double cteWidening, int muNumber, int muTotal)
	{
		// Place MU in a fixed position inside a muscle
		double y = (double)muNumber / (muTotal - 1);
		double z = 0;
		
		y = y * (muscleThickness - skinLayer) + skinLayer;
		
		double distance = y;
		
		posY = y;
		posZ = z;

		// Fitted to Fuglevand et al. 1992 - Biological Cybernetics
		attenuationToSkin = Math.exp( -distance/tauAttenuation );

		// Fitted to Hermens et al. 1992 - IEEE EMBS 
		timeWidening = 1 + cteWidening * distance;
	}

	
	public void presetSpatialDistribution(double y, double z, double muscleThickness, double skinLayer, double tauAttenuation, double cteWidening, int muNumber, int muTotal)
	{
		// Place MU in a preset position inside a muscle
		posY = y;
		posZ = z;

		double distance = Math.sqrt( Math.pow( posY, 2) + Math.pow( posZ, 2) );
		
		// Fitted to Fuglevand et al. 1992 - Biological Cybernetics
		attenuationToSkin = Math.exp( -distance/tauAttenuation );

		// Fitted to Hermens et al. 1992 - IEEE EMBS 
		timeWidening = 1 + cteWidening * distance;
	}

	
	public double getEndPlateSpike(int index) 
	{
		return ((Double)endPlateSpikeTrain.get(index)).doubleValue();
	}
	
	
	public int getNumberOfSpikesAtEndPlate() 
	{
		return endPlateSpikeTrain.size();
	}

	
	// General getters and setters 
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmpEMG() {
		return ampEMG;
	}
	public void setAmpEMG(double ampEMG) {
		this.ampEMG = ampEMG;
	}
	public double getLambdaEMG() {
		return lambdaEMG;
	}
	public void setLambdaEMG(double lambdaEMG) {
		this.lambdaEMG = lambdaEMG;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String id) {
		this.cd = id;
	}
	public String getCdNucleus() {
		return cdNucleus;
	}
	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}
	/*
	public AlphaFunction getTwitchFunction() {
		return twitchFunction;
	}
	public void setTwitchFunction(AlphaFunction twitchFunction) {
		this.twitchFunction = twitchFunction;
	}
	*/
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public double getPosZ() {
		return posZ;
	}
	public void setPosZ(double posX) {
		this.posZ = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
	public double getAttenuationToSkin() {
		return attenuationToSkin;
	}
	public void setAttenuationToSkin(double distanceToSkin) {
		this.attenuationToSkin = distanceToSkin;
	}
	public double getTimeWidening() {
		return timeWidening;
	}
	public void setTimeWidening(double timeEnlargement) {
		this.timeWidening = timeEnlargement;
	}


	public Miscellaneous getMiscellaneous() {
		return miscellaneous;
	}


	public void setMiscellaneous(Miscellaneous miscellaneous) {
		this.miscellaneous = miscellaneous;
	}

	public int getIndexSpike() {
		return indexSpike;
	}


	public int getIteration() {
		return iteration;
	}


	public double gettSpike() {
		return tSpike;
	}
	
	public void setIndexSpike(int indexSpike) {
		this.indexSpike = indexSpike;
	}


	public void setIteration(int iteration) {
		this.iteration = iteration;
	}


	public void settSpike(double tSpike) {
		this.tSpike = tSpike;
	}


	public double getGpeak() {
		return Gpeak;
	}


	public void setGpeak(double gpeak) {
		Gpeak = gpeak;
	}


	public double getGmax() {
		return Gmax;
	}


	public void setGmax(double gmax) {
		Gmax = gmax;
	}


	public double getTpeak() {
		return Tpeak;
	}


	public void setTpeak(double tpeak) {
		Tpeak = tpeak;
	}


	public double getLatencyStimulusEndPlate() {
		return latencyStimulusEndPlate;
	}


	public void setLatencyStimulusEndPlate(double latencyStimulusEndPlate) {
		this.latencyStimulusEndPlate = latencyStimulusEndPlate;
	}


	public double getGpeakRaikova() {
		return GpeakRaikova;
	}


	public void setGpeakRaikova(double gpeakRaikova) {
		GpeakRaikova = gpeakRaikova;
	}


	public double getTpeakRaikova() {
		return TpeakRaikova;
	}


	public void setTpeakRaikova(double tpeakRaikova) {
		TpeakRaikova = tpeakRaikova;
	}


	public double getTHalfRaikova() {
		return THalfRaikova;
	}


	public void setTHalfRaikova(double tHalfRaikova) {
		THalfRaikova = tHalfRaikova;
	}
	
	public double getB() {
		return B;
	}


	public void setB(double b) {
		B = b;
	}
	
	public double getBRaikova() {
		return BRaikova;
	}


	public void setBRaikova(double bRaikova) {
		BRaikova = bRaikova;
	}

	public boolean isEmgAttenuation() {
		return emgAttenuation;
	}


	public void setEmgAttenuation(boolean emgAttenuation) {
		this.emgAttenuation = emgAttenuation;
	}


	public boolean isStoredSignals() {
		return storedSignals;
	}


	public void setStoredSignals(boolean storedSignals) {
		this.storedSignals = storedSignals;
	}


	public double getTwTet() {
		
		return TwTet;
	}
	
	public void setTwTet(double twTet){
		TwTet = twTet;
	}
	
	public double getTwTetRaikova() {
		
		return TwTetRaikova;
	}
	
	public void setTwTetRaikova(double twTetRaikova){
		TwTetRaikova = twTetRaikova;
	}
	
}

