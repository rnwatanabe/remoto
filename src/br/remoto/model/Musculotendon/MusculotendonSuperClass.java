package br.remoto.model.Musculotendon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import br.remoto.model.Configuration;
import br.remoto.model.MotorUnit;
import br.remoto.model.ReMoto;
import br.remoto.model.Joint.Ankle.Models.AnkleIsometricModel;
import br.remoto.model.Joint.Ankle.Models.InvertedPendulumModel;
import br.remoto.model.Joint.Ankle.Models.PositionTaskModel;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.ExtrafusalMuscleSuperClass;
import br.remoto.model.Musculotendon.Tendon.NonInnerveted.NonInnervatedTendon;
import br.remoto.model.Proprioceptors.GolgiTendonOrgan;
import br.remoto.model.Proprioceptors.MuscleSpindle;
import br.remoto.util.Conversion;
import br.remoto.util.Sample;
import br.remoto.util.Signal;



public class MusculotendonSuperClass
{
	
	protected Sample sampler1;
	protected Sample sampler2;
	
	protected ArrayList lengthStore = new ArrayList();
	protected ArrayList momentArmStore = new ArrayList();
	
	ExtrafusalMuscleSuperClass extrafusalMuscle;
	NonInnervatedTendon tendon;
	
	GolgiTendonOrgan gto;
	MuscleSpindle spindle;
	
	AnkleIsometricModel associatedJoint;
	InvertedPendulumModel associatedJoint2;
	PositionTaskModel associatedJoint3;
	
	private String cdMuscle;
	
	private double length;
	private double initialLength;
	private double velocity = 0;
	private double momentArm;
	
	private double force;
	
	Configuration conf;
	String JointModel;
	
	
	public MusculotendonSuperClass(Configuration conf, ExtrafusalMuscleSuperClass extrafusalMuscle, NonInnervatedTendon tendon,
									MuscleSpindle spindle, GolgiTendonOrgan gto) {
		
		this.extrafusalMuscle = extrafusalMuscle;
		this.tendon = tendon;
		this.gto = gto;
		this.spindle = spindle;
		this.cdMuscle = extrafusalMuscle.getCdMuscle();
		this.conf = conf;
		this.JointModel = conf.getCdJointModel();
		
		sampler1 = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		sampler2 = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		
		System.out.println("Creating musculotendon nucleus: " + extrafusalMuscle.getCdMuscle());
		
	}
	
	public void atualize(double t){
				
		if(JointModel.equals("isometric")){
			calculateLength(cdMuscle, associatedJoint.getAngle()*(180/Math.PI), associatedJoint.getKneeAngle());
			if (t == 0)	extrafusalMuscle.setInitialLengthNorm();
			calculateMomentArmAnkle(cdMuscle, associatedJoint.getAngle()*(180/Math.PI),  associatedJoint.getKneeAngle());
			
			extrafusalMuscle.atualize(t);
			
			if(extrafusalMuscle.getMuscleModel().equals("hill")){
				tendon.atualize(t,
						length,
						extrafusalMuscle.getLengthNorm() * extrafusalMuscle.getOptimalLength(),
						extrafusalMuscle.getPennationAngle());
		
				force = tendon.getForce();
				gto.atualize(t, tendon.getForce());
				spindle.atualize(t, extrafusalMuscle.getLengthNorm(), extrafusalMuscle.getStretchVelocity(), extrafusalMuscle.getStretchAcceleration() );
			}
			else{
				force = extrafusalMuscle.getForce();
				gto.atualize(t, force);
			}
			
			
		
			//lengthStore.add( new Signal( "musculotendonLength", length, t) );
			//momentArmStore.add( new Signal( "musculotendonMomentArmStore", momentArm, t) );
			
			sampler1.sample(lengthStore, "", t, length);
			sampler2.sample(momentArmStore, "", t, momentArm);
			
		}
		else if(JointModel.equals("pendulum")){
			calculateLength(cdMuscle, associatedJoint2.getAngle()*(180/Math.PI), associatedJoint2.getKneeAngle());
			if (t == 0)	extrafusalMuscle.setInitialLengthNorm();
			calculateMomentArmAnkle(cdMuscle, associatedJoint2.getAngle()*(180/Math.PI),  associatedJoint2.getKneeAngle());
			
			extrafusalMuscle.atualize(t);
			
			if(extrafusalMuscle.getMuscleModel().equals("hill")){
				tendon.atualize(t,
						length,
						extrafusalMuscle.getLengthNorm() * extrafusalMuscle.getOptimalLength(),
						extrafusalMuscle.getPennationAngle());
		
				force = tendon.getForce();
				gto.atualize(t, tendon.getForce());
				
			}
			else{
				force = extrafusalMuscle.getForce();
				gto.atualize(t, force);
			}
			
			spindle.atualize(t, extrafusalMuscle.getLengthNorm(), extrafusalMuscle.getStretchVelocity(), extrafusalMuscle.getStretchAcceleration() );
		
			//lengthStore.add( new Signal( "musculotendonLength", length, t) );
			//momentArmStore.add( new Signal( "musculotendonMomentArmStore", momentArm, t) );
			
			sampler1.sample(lengthStore, "", t, length);
			sampler2.sample(momentArmStore, "", t, momentArm);
		}
		else if(JointModel.equals("position")){
			calculateLength(cdMuscle, associatedJoint3.getAngle()*(180/Math.PI), associatedJoint3.getKneeAngle());
			if (t == 0)	extrafusalMuscle.setInitialLengthNorm();
			calculateMomentArmAnkle(cdMuscle, associatedJoint3.getAngle()*(180/Math.PI),  associatedJoint3.getKneeAngle());
			
			extrafusalMuscle.atualize(t);
			
			if(extrafusalMuscle.getMuscleModel().equals("hill")){
				tendon.atualize(t,
						length,
						extrafusalMuscle.getLengthNorm() * extrafusalMuscle.getOptimalLength(),
						extrafusalMuscle.getPennationAngle());
		
				force = tendon.getForce();
				gto.atualize(t, tendon.getForce());
				
			}
			else{
				force = extrafusalMuscle.getForce();
				gto.atualize(t, force);
			}
			
			spindle.atualize(t, extrafusalMuscle.getLengthNorm(), extrafusalMuscle.getStretchVelocity(), extrafusalMuscle.getStretchAcceleration() );
		
			//lengthStore.add( new Signal( "musculotendonLength", length, t) );
			//momentArmStore.add( new Signal( "musculotendonMomentArmStore", momentArm, t) );
			
			sampler1.sample(lengthStore, "", t, length);
			sampler2.sample(momentArmStore, "", t, momentArm);
		}
	}
	
	
	// Polynomial fit from the data reported in Arnold et al. (2010) -- [L.A.E. 31/05/2012]
	private void calculateLength(String cdMuscle, double ankleAngle, double kneeAngle){
				
		if(cdMuscle.equals("SOL")){

			length =	9.2739 * Math.pow(10, -11) * Math.pow(ankleAngle, 4) - 
						3.1478 * Math.pow(10, -8) * Math.pow(ankleAngle, 3) - 
						2.2431 * Math.pow(10, -6) * Math.pow(ankleAngle, 2) + 
						7.2186 * Math.pow(10, -4) * ankleAngle + 
						0.32284;
		}
		else if(cdMuscle.equals("MG")){
			if(kneeAngle == 0){
				length =	7.3492 * Math.pow(10, -11) * Math.pow(ankleAngle, 4) - 
							3.4959 * Math.pow(10, -8) * Math.pow(ankleAngle, 3) - 
							1.1328 * Math.pow(10, -6) * Math.pow(ankleAngle, 2) + 
							7.4794 * Math.pow(10, -4) * ankleAngle + 
							0.46349;

			}
			else if(kneeAngle == 90){
				length =	7.3209 * Math.pow(10, -11) * Math.pow(ankleAngle, 4) - 
							3.4982 * Math.pow(10, -8) * Math.pow(ankleAngle, 3) - 
							1.1266 * Math.pow(10, -6) * Math.pow(ankleAngle, 2) + 
							7.4750 * Math.pow(10, -4) * ankleAngle + 
							0.42343;
			}
			else
				length = 0;
		}
		else if(cdMuscle.equals("LG")){
			if(kneeAngle == 0){
				length =	7.6451 * Math.pow(10, -11) * Math.pow(ankleAngle, 4) - 
							3.5529 * Math.pow(10, -8) * Math.pow(ankleAngle, 3) - 
							1.2491 * Math.pow(10, -6) * Math.pow(ankleAngle, 2) + 
							7.6234 * Math.pow(10, -4) * ankleAngle + 
							0.45515;
			}
			else if(kneeAngle == 90){
				length = 	7.6452 * Math.pow(10, -11) * Math.pow(ankleAngle, 4) - 
							3.5540 * Math.pow(10, -8) * Math.pow(ankleAngle, 3) - 
							1.2478 * Math.pow(10, -6) * Math.pow(ankleAngle, 2) + 
							7.6228 * Math.pow(10, -4) * ankleAngle + 
							0.41375;
			}
			else
				length = 0;
		}
		else if(cdMuscle.equals("TA")){
			length =	1.5002 * Math.pow(10, -10) * Math.pow(ankleAngle, 4) + 
						2.4220 * Math.pow(10, -8) * Math.pow(ankleAngle, 3) - 
						1.4106 * Math.pow(10, -6) * Math.pow(ankleAngle, 2) - 
						7.4414 * Math.pow(10, -4) * ankleAngle + 
						0.30587;
		}
	}
	
	private void calculateMomentArmAnkle(String cdMuscle, double ankleAngle, double kneeAngle){
		
		if(cdMuscle.equals("SOL")){

			momentArm =	-5.4944 * Math.pow(10, -11) * Math.pow(ankleAngle, 4) - 
						2.2185 * Math.pow(10, -8) * Math.pow(ankleAngle, 3) + 
						5.4507 * Math.pow(10, -6) * Math.pow(ankleAngle, 2) + 
						2.5739 * Math.pow(10, -4) * ankleAngle -
						0.041363;
		}
		else if(cdMuscle.equals("MG")){
			if(kneeAngle == 0){
				momentArm =	-1.0214 * Math.pow(10, -10) * Math.pow(ankleAngle, 4) - 
							1.8660 * Math.pow(10, -8) * Math.pow(ankleAngle, 3) + 
							6.0830 * Math.pow(10, -6) * Math.pow(ankleAngle, 2) + 
							1.3053 * Math.pow(10, -4) * ankleAngle -
							0.042864;

		}
		else if(kneeAngle == 90){
				momentArm =	-1.0296 * Math.pow(10, -10) * Math.pow(ankleAngle, 4) - 
							1.8619 * Math.pow(10, -8) * Math.pow(ankleAngle, 3) + 
							6.0872 * Math.pow(10, -6) * Math.pow(ankleAngle, 2) + 
							1.2988 * Math.pow(10, -4) * ankleAngle - 
							0.042841;
		}
		else
				momentArm = 0;
		}
		else if(cdMuscle.equals("LG")){
			if(kneeAngle == 0){
				momentArm =	-1.0234 * Math.pow(10, -10) * Math.pow(ankleAngle, 4) - 
							1.9345 * Math.pow(10, -8) * Math.pow(ankleAngle, 3) + 
							6.1803 * Math.pow(10, -6) * Math.pow(ankleAngle, 2) + 
							1.4388 * Math.pow(10, -4) * ankleAngle - 
							0.043687;
			}
			else if(kneeAngle == 90){
				momentArm = -1.0261 * Math.pow(10, -10) * Math.pow(ankleAngle, 4) - 
							1.9351 * Math.pow(10, -8) * Math.pow(ankleAngle, 3) + 
							6.1825 * Math.pow(10, -6) * Math.pow(ankleAngle, 2) + 
							1.4374 * Math.pow(10, -4) * ankleAngle - 
							0.043684;
			}
			else
				momentArm = 0;
		}
		else if(cdMuscle.equals("TA")){
			momentArm =	-4.3391 * Math.pow(10, -10) * Math.pow(ankleAngle, 4) - 
						4.4462 * Math.pow(10, -8) * Math.pow(ankleAngle, 3) - 
						3.8880 * Math.pow(10, -6) * Math.pow(ankleAngle, 2) + 
						1.6568 * Math.pow(10, -4) * ankleAngle + 
						0.042616;
		}
	}
	
	
	
	
	public ExtrafusalMuscleSuperClass getMuscle(){
		return extrafusalMuscle;
	}
	
	public void setMuscle(ExtrafusalMuscleSuperClass muscle) {
		// TODO Auto-generated method stub
		extrafusalMuscle = muscle;
	}

	public NonInnervatedTendon getTendon() {
		return tendon;
	}

	public void setTendon(NonInnervatedTendon tendon) {
		this.tendon = tendon;
	}



	public String getCdMuscle() {
		return cdMuscle;
	}



	public void setCdMuscle(String cdMuscle) {
		this.cdMuscle = cdMuscle;
	}



	public GolgiTendonOrgan getGto() {
		return gto;
	}



	public void setGto(GolgiTendonOrgan gto) {
		this.gto = gto;
	}



	public MuscleSpindle getSpindle() {
		return spindle;
	}



	public void setSpindle(MuscleSpindle spindle) {
		this.spindle = spindle;
	}



	public double getLength() {
		return length;
	}



	public void setLength(double length) {
		this.length = length;
	}



	public double getInitialLength() {
		return initialLength;
	}



	public void setInitialLength(double initialLength) {
		this.initialLength = initialLength;
	}



	public double getVelocity() {
		return velocity;
	}
	
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public double getMomentArm() {
		return momentArm;
	}

	
	public void setMomentArm(double momentArm) {
		this.momentArm = momentArm;
	}



	public double getForce() {
		return force;
	}



	public void setForce(double force) {
		this.force = force;
	}

	public AnkleIsometricModel getAssociatedJoint() {
		return associatedJoint;
	}

	public void setAssociatedJoint(AnkleIsometricModel associatedJoint) {
		this.associatedJoint = associatedJoint;
	}
	
	public InvertedPendulumModel getAssociatedJoint2() {
		return associatedJoint2;
	}

	public void setAssociatedJoint2(InvertedPendulumModel associatedJoint2) {
		this.associatedJoint2 = associatedJoint2;
	}
	
	public PositionTaskModel getAssociatedJoint3() {
		return associatedJoint3;
	}

	public void setAssociatedJoint3(PositionTaskModel associatedJoint3) {
		this.associatedJoint3 = associatedJoint3;
	}
	
	public ArrayList getLengthStore() {
		return lengthStore;
	}

	public void setLengthStore(ArrayList lengthStore) {
		this.lengthStore = lengthStore;
	}

	public ArrayList getMomentArmStore() {
		return momentArmStore;
	}

	public void setMomentArmStore(ArrayList momentArmStore) {
		this.momentArmStore = momentArmStore;
	}
	
	
	
}

