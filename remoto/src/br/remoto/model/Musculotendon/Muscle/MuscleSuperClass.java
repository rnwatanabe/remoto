package br.remoto.model.Musculotendon.Muscle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import br.remoto.model.Configuration;
import br.remoto.model.MotorUnit;
import br.remoto.model.ReMoto;
import br.remoto.util.Sample;
import br.remoto.util.Signal;


public abstract class MuscleSuperClass implements Serializable
{
	private static final long serialVersionUID = 1L;

	protected Sample samplerSTypeMuscleActivationStore;
	protected Sample samplerFTypeMuscleActivationStore;
	protected Sample samplerMuscleActivationStore;
	protected Sample samplerLengthStore;
	protected Sample samplerLengthNormStore;
	protected Sample samplerVelocityStore;
	protected Sample samplerAccelerationStore;
	protected Sample samplerForceParallelElementStore;
	protected Sample samplerForceViscousElementStore;
	protected Sample samplerForceActiveSTypeStore;
	protected Sample samplerForceActiveFTypeStore;
	protected Sample samplerForceStore;
	protected Sample samplerMotorUnitForceStore;
	protected Sample samplerPennationAngleStore;
	
	protected ArrayList lengthStore = new ArrayList();
	
	protected ArrayList lengthNormStore = new ArrayList();
	protected ArrayList velocityStore = new ArrayList();
	protected ArrayList accelerationStore = new ArrayList();
	
	protected ArrayList muscleActivationStore = new ArrayList();
	
	protected ArrayList forceParallelElementStore = new ArrayList();
	protected ArrayList forceViscousElementStore = new ArrayList();
	
	protected ArrayList forceActiveSTypeStore = new ArrayList();
	protected ArrayList forceActiveFTypeStore = new ArrayList();
	
	protected ArrayList pennationAngleStore = new ArrayList();
	
	protected double activationNorm;
	
	protected String cdMuscle;

	protected double force;
	protected double forceNorm;
	
	protected double length;
	protected double lengthNorm;
	protected double pennationAngle;
	
	protected double lastNormLength = 0;
	protected double lastStretchVelocity = 0;
	
	protected double velocity = 0;
	protected double acceleration = 0;
	protected double stretchVelocity = 0;
	protected double stretchAcceleration = 0;
	protected double shorteningVelocity = 0;
	protected double shorteningAcceleration = 0;
	
	
	protected Configuration conf;
	
	public MuscleSuperClass(Configuration conf, String cdMuscle)
	{
		this.conf = conf;
		this.cdMuscle = cdMuscle;
		
		samplerSTypeMuscleActivationStore 	= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerFTypeMuscleActivationStore 	= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerMuscleActivationStore 		= new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
	}
	
	public String getCdMuscle() {
		return cdMuscle;
	}

	public void setCdMuscle(String cdMuscle) {
		this.cdMuscle = cdMuscle;
	}
	
	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getPennationAngle() {
		return pennationAngle;
	}

	public void setPennationAngle(double pennationAngle) {
		this.pennationAngle = pennationAngle;
	}
	
	public double getLengthNorm() {
		return lengthNorm;
	}

	public void setLengthNorm(double lengthNorm) {
		this.lengthNorm = lengthNorm;
		
	}

	public ArrayList getLengthNormStore() {
		return lengthNormStore;
	}

	public void setLengthNormStore(ArrayList lengthNormStore) {
		this.lengthNormStore = lengthNormStore;
	}

	public ArrayList getVelocityStore() {
		return velocityStore;
	}

	public void setVelocityStore(ArrayList velocityStore) {
		this.velocityStore = velocityStore;
	}

	public ArrayList getAccelerationStore() {
		return accelerationStore;
	}

	public void setAccelerationStore(ArrayList accelerationStore) {
		this.accelerationStore = accelerationStore;
	}
	
	
	public void calculateVelocityAndAccelerationUsingDifferentiation(double t){
		
		
		if(t == 0){
			stretchVelocity = 0;
			stretchAcceleration = 0;
		}else{
			stretchVelocity = (lengthNorm - lastNormLength) / conf.getStep();
			stretchAcceleration = (stretchVelocity - lastStretchVelocity) / conf.getStep();
		}
		
		lastNormLength = lengthNorm;
		lastStretchVelocity = stretchVelocity;
	}
	
	
	
	protected void atualizeActivation(double t){
		//muscleActivationStore.add( new Signal( ReMoto.activationNorm, activationNorm, t) );
		samplerMuscleActivationStore.sample(muscleActivationStore, "", t, activationNorm);
	}

	public double getStretchVelocity() {
		return stretchVelocity;
	}

	public void setStretchVelocity(double stretchVelocity) {
		this.stretchVelocity = stretchVelocity;
	}

	public double getStretchAcceleration() {
		return stretchAcceleration;
	}

	public void setStretchAcceleration(double stretchAcceleration) {
		this.stretchAcceleration = stretchAcceleration;
	}

	public double getActivationNorm() {
		return activationNorm;
	}

	public void setActivationNorm(double activationNorm) {
		this.activationNorm = activationNorm;
	}

	public ArrayList getMuscleActivationStore() {
		return muscleActivationStore;
	}

	public void setMuscleActivationStore(ArrayList muscleActivationStore) {
		this.muscleActivationStore = muscleActivationStore;
	}

	public ArrayList getForceParallelElementStore() {
		return forceParallelElementStore;
	}

	public void setForceParallelElementStore(ArrayList forceParallelElementStore) {
		this.forceParallelElementStore = forceParallelElementStore;
	}

	public ArrayList getForceViscousElementStore() {
		return forceViscousElementStore;
	}

	public void setForceViscousElementStore(ArrayList forceViscousElementStore) {
		this.forceViscousElementStore = forceViscousElementStore;
	}

	public ArrayList getForceActiveSTypeStore() {
		return forceActiveSTypeStore;
	}

	public void setForceActiveSTypeStore(ArrayList forceActiveSTypeStore) {
		this.forceActiveSTypeStore = forceActiveSTypeStore;
	}

	public ArrayList getForceActiveFTypeStore() {
		return forceActiveFTypeStore;
	}

	public void setForceActiveFTypeStore(ArrayList forceActiveFTypeStore) {
		this.forceActiveFTypeStore = forceActiveFTypeStore;
	}

	public double getForceNorm() {
		return forceNorm;
	}

	public void setForceNorm(double forceNorm) {
		this.forceNorm = forceNorm;
	}
	
	public double getForce() {
		return force;
	}

	public void setForce(double force) {
		this.force = force;
	}

	public double getShorteningVelocity() {
		return shorteningVelocity;
	}

	public void setShorteningVelocity(double shorteningVelocity) {
		this.shorteningVelocity = shorteningVelocity;
	}

	public double getShorteningAcceleration() {
		return shorteningAcceleration;
	}

	public void setShorteningAcceleration(double shorteningAcceleration) {
		this.shorteningAcceleration = shorteningAcceleration;
	}

	public ArrayList getLengthStore() {
		return lengthStore;
	}

	public void setLengthStore(ArrayList lengthStore) {
		this.lengthStore = lengthStore;
	}

	public ArrayList getPennationAngleStore() {
		return pennationAngleStore;
	}

	public void setPennationAngleStore(ArrayList pennationAngleStore) {
		this.pennationAngleStore = pennationAngleStore;
	}


}

