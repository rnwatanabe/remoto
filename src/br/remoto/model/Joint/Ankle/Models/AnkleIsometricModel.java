package br.remoto.model.Joint.Ankle.Models;


import java.util.ArrayList;

import br.remoto.model.Configuration;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.Joint.Ankle.AnkleSuperClass;
//import br.remoto.model.Musculotendon.Muscle.Muscle;
import br.remoto.model.Musculotendon.MusculotendonSuperClass;
import br.remoto.model.Neuron.Miscellaneous;
import br.remoto.util.ButterworthBilinear;
import br.remoto.util.ButterworthImpulseInvariance;
import br.remoto.util.Distribution;
import br.remoto.util.Sample;
import br.remoto.util.Signal;


public class AnkleIsometricModel extends AnkleSuperClass
{
	private static final long serialVersionUID = 1L;
	
	private double angleArray[];
	int iteration;
	private ButterworthBilinear butterBilinear;
	
	MusculotendonSuperClass[] musculotendons;
	Configuration conf;
	
	public AnkleIsometricModel(Configuration conf, MusculotendonSuperClass[] musculotendons){
		super(conf.getJointVO());
		
		this.conf = conf;
		this.musculotendons = musculotendons;
		
		samplerJointAngleStore = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		samplerJointTorqueStore = new Sample(conf.getDecimationFrequency(), 1000 / conf.getStep());
		
		iteration = 0;
		
		butterBilinear = new ButterworthBilinear();
		
		angleArray = new double[(int) (conf.getTFin() / conf.getStep())];

		double aux = Math.random();
		double T = 150;
		double taux = 0;
		//double amp = 0.015 * (180/Math.PI);
		double output = 0;
		
		for(int i = 0; i < angleArray.length; i++){
			
			double t = i * conf.getStep();
			ModulatingSignal signal = new ModulatingSignal();
			
			//double width = conf.getJointAngle() * 1000 / conf.getJointVelocity();
			
		    signal.setCdSignal("ramp");
			signal.setAmp(0);
			signal.setTini(1000);
			signal.setTfin(1260);
			signal.setWidth(60);
			
			/*
			// Triangular modulation
			double amp;
			
			if(t > 0 && t <= 500)
				amp = 0;
			else if(t > 500 && t <= 3500)
				amp = 5 * (t - 500)/1000;
			else if(t > 3500 && t <= 4000)
				amp = 15;
			else if(t > 4000 && t <= 7000)
				amp = 15 - 5 * (t - 4000)/1000;
			else if(t > 7000 && t <= 7500)
				amp = 0;
			else if(t > 7500 && t <= 10500)
				amp = - 5 * (t - 7500)/1000;
			else if(t > 10500 && t <= 11000)
				amp = -15;
			else if(t > 11000 && t <= 14000)
				amp = -15 + 5 * (t - 11000)/1000;
			else if(t > 14000 && t <= 14500)
				amp = 0;
			else
				amp = 0;
			*/
			
			/*
			// White Gaussian Noise
			double noise;
			
			if(t > 1000 && t <= 20000)
				noise = Distribution.gaussianPoint(0, 0.1);
			else
				noise = 0;
			*/
			
			/*
			//PRBS
			if(t > 1000 && t < 20000){
				if ((t - taux) <= T){
					if(aux > 0.5){
						output = amp;
					}
					else{
						output = -amp;
					}
				}
				else{
					taux = t;
					aux = Math.random();
				}
			}
			*/	
			
			//angleArray[i] = (conf.getJointAngle() + signal.value(t))*(Math.PI/180);
			angleArray[i] = (conf.getJointAngle())*(Math.PI/180);
		}
		
		kneeAngle = conf.getKneeAngle()*(Math.PI/180);
		
	}
	
	
	public void atualize(double t){
		
		samplerJointAngleStore.sample(jointAngleStore, "", t, angle*(180/Math.PI));
		samplerJointTorqueStore.sample(jointTorqueStore, "", t, torque);
		
		angle = calculateAngle(t);		
		torque = calculateTorque(t);
		
	}
	
	private double calculateAngle (double t){
		
		//double result;
		double filteredResult;		
		
		filteredResult = angleArray[iteration];
		iteration++;
		
		return filteredResult;
	}
	
	private double calculateTorque (double t){
		
		double resultantTorque = 0;
		
		for(int i = 0; i < musculotendons.length; i++){
			
			if(musculotendons[i] != null){
				
				if(musculotendons[i].getCdMuscle().equals("TA")){
					resultantTorque += musculotendons[i].getForce() * musculotendons[i].getMomentArm();
				}
				else{
					resultantTorque += musculotendons[i].getForce() * musculotendons[i].getMomentArm();
				}
			}
		}		
		return resultantTorque;
	}

}
