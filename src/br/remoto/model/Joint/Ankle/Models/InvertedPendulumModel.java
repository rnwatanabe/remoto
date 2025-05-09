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


public class InvertedPendulumModel extends AnkleSuperClass
{	

	private static final long serialVersionUID = 1L;
	
	private double angleArray[];
	int iteration;
	private ButterworthBilinear butterBilinear;
	
	MusculotendonSuperClass[] musculotendons;
	Configuration conf;
	
	private double Gravity;
	private double bodyMass;
	private double bodyHeight;
	private double bodyInertia;
	private double jointViscosity;
	private double jointElasticity;
	
	private double step;
	
	double k1_a;
	double k2_a;
	double k3_a;
	double k4_a;
	
	double k1_v;
	double k2_v;
	double k3_v;
	double k4_v;
	
	public InvertedPendulumModel(Configuration conf, MusculotendonSuperClass[] musculotendons){
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
		
		this.Gravity = 9.81;				 									//[m/s2]
		this.bodyMass = 60; 													//[kg]
		this.bodyHeight = 0.50 * 1.70;													//[m]
		this.bodyInertia = (4.0/3.0) * bodyMass * Math.pow(bodyHeight,2);		//[kg.m2]
		this.jointViscosity = 5.81;												//[Nm.s.rad-1]
		this.jointElasticity = 0.65 * bodyMass * Gravity * bodyHeight;			//[Nm.rad-1]
		
		kneeAngle = conf.getKneeAngle();
		
		step = Conversion.convertMillisecondsToSeconds(conf.getStep());
		
	}
	
	public void atualize(double t){

		if(t == 0){
			angle = conf.getJointAngle() * (Math.PI/180);
			gravitationalTorque = bodyMass * Gravity * bodyHeight * Math.sin(angle);
			passiveTorque = jointViscosity * velocity + jointElasticity * (angle - refAngle);
			centerMass = bodyHeight * Math.sin(angle);
			centerPressure = bodyHeight * Math.sin(angle);
		}
		
		samplerJointAngleStore.sample(jointAngleStore, "", t, angle * (180/Math.PI));
		samplerJointVelocityStore.sample(jointVelocityStore,"", t, velocity * (180/Math.PI));
		samplerJointTorqueStore.sample(jointTorqueStore, "", t, (gravitationalTorque + (2 * torque) - passiveTorque + disturbance));
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
		
		centerMass = bodyHeight * Math.sin(angle);
		
		//System.out.println((jointElasticity * (angle - refAngle)));
	}
	
	public void calculateCenterPressure(double t){
		
		centerPressure = bodyHeight * ( Math.sin(angle) * (1 + (bodyInertia/(bodyMass * Gravity * bodyHeight)) * Math.pow(velocity,2))
				- (bodyInertia/(bodyMass * Gravity * bodyHeight)) * Math.cos(angle) * acceleration);
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
		
		//System.out.println(disturbance);
		
		acceleration = (gravitationalTorque + (2 * torque) - passiveTorque + disturbance) / bodyInertia;
		
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
		
		double gravTorque = bodyMass * Gravity * bodyHeight * Math.sin(angle);
				
		return gravTorque;
		
	}
	
	private double calculateDisturbance (double t){
		
		Random fRandom = new Random();
		double stdDev = 0.05;
		double disturbanceTorque = stdDev * fRandom.nextGaussian();
				
		return disturbanceTorque;
		
	}

}
