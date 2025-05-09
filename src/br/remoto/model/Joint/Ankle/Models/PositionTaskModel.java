package br.remoto.model.Joint.Ankle.Models;


import java.util.ArrayList;
import java.util.Random;

import br.remoto.model.Configuration;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.Joint.Ankle.AnkleSuperClass;
//import br.remoto.model.Musculotendon.Muscle.Muscle;
import br.remoto.model.Musculotendon.MusculotendonSuperClass;
import br.remoto.model.Neuron.Miscellaneous;
import br.remoto.util.ButterworthBilinear;
import br.remoto.util.ButterworthImpulseInvariance;
import br.remoto.util.Conversion;
import br.remoto.util.Distribution;
import br.remoto.util.Sample;
import br.remoto.util.Signal;


public class PositionTaskModel extends AnkleSuperClass
{	

	private static final long serialVersionUID = 1L;
	
	private double angleArray[];
	int iteration;
	private ButterworthBilinear butterBilinear;
	
	MusculotendonSuperClass[] musculotendons;
	Configuration conf;
	
	private double Gravity;
	private double footMass;
	private double ankleFootDistance;
	private double footInertia;
	private double jointViscosity;
	private double jointElasticity;
	private double maximumVoluntaryTorque;
	private double legAngle;
	
	
	
	private double step;
	
	double k1_a;
	double k2_a;
	double k3_a;
	double k4_a;
	
	double k1_v;
	double k2_v;
	double k3_v;
	double k4_v;
	
	public PositionTaskModel(Configuration conf, MusculotendonSuperClass[] musculotendons){
		super(conf.getJointVO());
		
		this.conf = conf;
		this.musculotendons = musculotendons;
		
		samplerJointAngleStore = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerJointVelocityStore = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerJointTorqueStore = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerJointMuscleTorqueStore = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerJointGravTorqueStore = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerJointPassiveTorqueStore = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerJointCenterMassStore = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerJointCenterPressureStore = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerJointDisturbanceStore = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		
		this.Gravity = 9.81;				 									//[m/s^2]
		this.footMass = 2.01; 													//[kg] Maurer and Peterka (2005)
		this.ankleFootDistance = 0.052;													//[m] Maurer and Peterka (2005)
		this.footInertia = (4.0/3.0) * footMass * Math.pow(ankleFootDistance ,2);		//[kg.m2]
		this.jointViscosity = 1.1;												//[Nm.s.rad^-1]
		this.jointElasticity = 320 ;			//[Nm.rad^-1] 75 is the body mass, 0.875 is half of body height
		this.maximumVoluntaryTorque = 79;									//[N.m] Descending command with mean at 4 ms and order 1 (Gamma)
		this.legAngle = 90 * (Math.PI/180);								//[rad]  Angle of the leg with the floor (Check this value)
		
		
		kneeAngle = conf.getKneeAngle();
		
		step = Conversion.convertMillisecondsToSeconds(conf.getStep());
		
		percentageOfMVC = conf.getpercentageOfMVC();
	}
	
	public void atualize(double t){
		
		if(t == 0){
			angle = conf.getJointAngle() * (Math.PI/180);
			gravitationalTorque = 0*footMass * Gravity * ankleFootDistance * Math.sin(legAngle - angle) - percentageOfMVC * maximumVoluntaryTorque*Math.cos(angle);
			passiveTorque = jointViscosity * velocity + jointElasticity * (angle - refAngle);
			centerMass = ankleFootDistance * Math.sin(angle);
			centerPressure = ankleFootDistance * Math.sin(angle);
		}
		
		samplerJointAngleStore.sample(jointAngleStore, "", t, angle * (180/Math.PI));
		samplerJointVelocityStore.sample(jointVelocityStore,"", t, velocity * (180/Math.PI));
		samplerJointTorqueStore.sample(jointTorqueStore, "", t, (acceleration*footInertia));
		samplerJointMuscleTorqueStore.sample(jointMuscleTorqueStore, "", t, torque);
		samplerJointGravTorqueStore.sample(jointGravTorqueStore, "", t, gravitationalTorque);
		samplerJointPassiveTorqueStore.sample(jointPassiveTorqueStore, "", t, passiveTorque);
		samplerJointCenterMassStore.sample(jointCenterMassStore, "", t, centerMass * 1000);
		samplerJointCenterPressureStore.sample(jointCenterPressureStore, "", t, centerPressure * 1000);
		samplerJointDisturbanceStore.sample(jointDisturbanceStore, "", t, disturbance);
		
		torque = calculateTorque(t);
			
		if (t >= 1000.0){
			calculateAngle(Conversion.convertMillisecondsToSeconds(t));
			calculateCenterMass(Conversion.convertMillisecondsToSeconds(t));
			calculateCenterPressure(Conversion.convertMillisecondsToSeconds(t));
		}
		
	}
	
	public void calculateCenterMass(double t){
		
		centerMass = ankleFootDistance * Math.sin(angle);
		
		//System.out.println((jointElasticity * (angle - refAngle)));
	}
	
	public void calculateCenterPressure(double t){
		
		centerPressure = ankleFootDistance  * ( Math.sin(angle) * (1 + (footInertia/(footMass * Gravity * ankleFootDistance)) * Math.pow(velocity,2))
				- (footInertia/(footMass * Gravity * ankleFootDistance)) * Math.cos(angle) * acceleration);
		
	}
	
	
	private void calculateAngle (double t){
		
		k1_a = velocity;
		k1_v = DvDt(t	, angle	, velocity);
		
		k2_a = velocity + (step/2) * k1_v;
		k2_v = DvDt(t + step/2	, angle + (step/2) * k1_a	, velocity  + (step/2) * k1_v);
		
		k3_a = (velocity + (step/2) * k2_v);
		k3_v = DvDt(t + step/2	, angle + (step/2) * k2_a	, velocity  + (step/2) * k2_v);
		
		k4_a = (velocity + step * k3_v);
		k4_v = DvDt(t + step		, angle + 	step * k3_a		, velocity  + 	step * k3_v);
		
		angle =  angle + (step * (k1_a + 2*k2_a + 2*k3_a + k4_a)/6);
		velocity =  velocity + (step * (k1_v + 2*k2_v + 2*k3_v + k4_v)/6);

	}
	
	public double calculateAcceleration(double t, double angle, double velocity){				
		
		gravitationalTorque = calculateGravTorque(t);
		passiveTorque = calculatePassiveTorque(t);
		disturbance = calculateDisturbance(t);
		
		System.out.println(" Weight= " + percentageOfMVC);
		
		acceleration = (torque - gravitationalTorque - passiveTorque + disturbance) / footInertia;
		//acceleration = (torque  - passiveTorque + disturbance + passiveTorque +2.5) / footInertia; //no gravity
		
		System.out.println(" Acceleration= " + acceleration*footInertia);
		
		return acceleration;
	}
	
	private double DvDt(double t, double angle, double velocity){
		return calculateAcceleration(t, angle, velocity);
	}
	
	
	private double calculateTorque (double t){
		
		double resultantMuscleTorque = 0;
		
		for(int i = 0; i < musculotendons.length; i++){
			
			if(musculotendons[i] != null){
				
				if(musculotendons[i].getCdMuscle().equals("TA")){
					resultantMuscleTorque += musculotendons[i].getForce() * musculotendons[i].getMomentArm();
				}
				else{
					resultantMuscleTorque += musculotendons[i].getForce() * musculotendons[i].getMomentArm();
				}
			}
		}
		
		return (resultantMuscleTorque);
		
	}
	
	private double calculatePassiveTorque (double t){
		
		double passiveTorque = (jointViscosity * velocity) + (jointElasticity * (angle - refAngle));
		
		return passiveTorque;
		
	}
	
	private double calculateGravTorque (double t){
		
		double gravTorque = 0*footMass * Gravity * ankleFootDistance * Math.sin(legAngle - angle) - percentageOfMVC * maximumVoluntaryTorque*Math.cos(angle) ;
				
		return gravTorque;
		
	}
	
	private double calculateDisturbance (double t){
		
		//white noise
		Random fRandom = new Random();
		double stdDev = 0;
		double disturbanceTorque = stdDev * fRandom.nextGaussian();
				
		//multisine random
		
		
		return disturbanceTorque;
		
	}

}
