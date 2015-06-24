package br.remoto.model.factory;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import br.remoto.model.Configuration;
import br.remoto.model.MotorUnit;
import br.remoto.model.Joint.Joint;
import br.remoto.model.Joint.Ankle.Models.AnkleIsometricModel;
import br.remoto.model.Joint.Ankle.Models.InvertedPendulumModel;
import br.remoto.model.Joint.Ankle.Models.PositionTaskModel;
import br.remoto.model.Musculotendon.MusculotendonSuperClass;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.ExtrafusalMuscleSuperClass;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.Models.DistributionMoments;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.Models.Hill;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.Models.Raikova;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.Models.SecondOrderCriticallyDampedSystem;
import br.remoto.model.Musculotendon.Tendon.NonInnerveted.NonInnervatedTendon;
import br.remoto.model.Neuron.Neuron;
import br.remoto.model.ReMoto;
import br.remoto.model.Neuron.Interneuron;
import br.remoto.model.Neuron.Motoneuron;
import br.remoto.model.Neuron.Miscellaneous;
import br.remoto.model.Proprioceptors.GolgiTendonOrgan;
import br.remoto.model.Proprioceptors.MuscleSpindle;
import br.remoto.model.vo.ConductanceVO;
import br.remoto.model.vo.MotorUnitVO;
import br.remoto.model.vo.MuscleVO;
import br.remoto.model.vo.Nucleus;
import br.remoto.model.vo.NeuronVO;
import br.remoto.util.Bias;
import br.remoto.util.Point;


public class JointFactory 
{
	
	Configuration conf;
	String JointModel;
	
	int numActiveMuscles = 0;
	
	public JointFactory(Configuration conf){
		this.conf = conf;
		this.JointModel = conf.getCdJointModel();;
	}
	
	
	public void createMusculotendons(Configuration conf, Neuron neurons[][], MuscleSpindle[] spindles,
			GolgiTendonOrgan[] gtos, MusculotendonSuperClass musculotendons[])
	{
		int ind = 0;
		
		String muscle_model = 	conf.getCdMuscleModel();
		String gto_model = 		conf.getCdGtoModel();
		String spindle_model = 	conf.getCdSpindleModel();
		
		//Neuron motoneurons[];
		
		//System.out.println("neurons.length: " + neurons.length);
		
		for(int x = 1; x < neurons.length; x++)
		{
			if( neurons[x].length == 0 )
				continue;
			
			Neuron neu = neurons[x][0];
			
			if( neu == null )
				continue;
			
			String cdNucleus = neu.getCdNucleus();
			
			//System.out.println("cdNucleus: " + cdNucleus);
			
			
			if( cdNucleus.equals( ReMoto.DT ) || cdNucleus.equals( ReMoto.IN_ext ) || cdNucleus.equals( ReMoto.IN_flex ) )
				continue;
			
			numActiveMuscles++;
			
			int numMNs = 0;
			
			if( conf.getNeuronType(cdNucleus, ReMoto.S).isActive() )
				numMNs = conf.getNeuronType(cdNucleus, ReMoto.S).getQuantity();
			
			if( conf.getNeuronType(cdNucleus, ReMoto.FR).isActive() )
				numMNs += conf.getNeuronType(cdNucleus, ReMoto.FR).getQuantity();
			
			if( conf.getNeuronType(cdNucleus, ReMoto.FF).isActive() )
				numMNs += conf.getNeuronType(cdNucleus, ReMoto.FF).getQuantity();
			
			//System.out.println("numMNs: " + numMNs);
			//motoneurons = new Neuron[numMNs];
			MotorUnit[] motorunits = new MotorUnit[numMNs];
			
			//System.out.println("neurons[x].length: " + neurons[x].length);
			
			int auxIndex = 0;
			
			// Search for all MNs of the nucleus
			for(int y = 0; y < neurons[x].length; y++)
			{
		    	String category = neurons[x][y].getCategory();
				
				if( !category.equals( ReMoto.MN ) )
					continue;
				
				neu = neurons[x][y];

				if( cdNucleus.equals( neu.getCdNucleus() ) &&
					neu.getCategory().equals(ReMoto.MN) )
				{
					
					motorunits[auxIndex++] = new MotorUnit( (Motoneuron)neu , muscle_model);
					
				}
			}
			
			// Set motor-units characteristics according to their sizes
			configureMotorUnits(conf, cdNucleus, motorunits);
			
			
			ExtrafusalMuscleSuperClass muscle = null;
			
			if(muscle_model.equals(ReMoto.Raikova)){
				muscle = new Raikova(conf, cdNucleus, motorunits, muscle_model);
			}
			else if(muscle_model.equals(ReMoto.Hill)){
				muscle = new Hill(conf, cdNucleus, motorunits, muscle_model);
			}
			else if(muscle_model.equals(ReMoto.DistributionMoments)){
				muscle = new DistributionMoments(conf, cdNucleus, motorunits, muscle_model);
			}
			else{
				muscle = new SecondOrderCriticallyDampedSystem(conf, cdNucleus, motorunits, muscle_model);
			}
			
			
				double gammaDynamic = conf.getGammaDynamic();
				double gammaStatic = conf.getGammaStatic();
				
				System.out.println("gammaDynamic: " + gammaDynamic);
				System.out.println("gammaStatic: " + gammaStatic);
				
				//NonInnervatedTendon tendon = new NonInnervatedTendon(conf, muscle.getOptimalLength(), muscle.getMaximumMuscleForce());
				
				NonInnervatedTendon tendon = new NonInnervatedTendon(conf, muscle.getOptimalLength(), muscle.getMaximumMuscleForce(), muscle.getSlackTendonLength(), muscle.getC_T(), muscle.getK_T(), muscle.getLR_T());
				
				MuscleSpindle spindle = new MuscleSpindle(cdNucleus, neurons, conf, gammaDynamic, gammaStatic, spindle_model);
				
				GolgiTendonOrgan gto = new GolgiTendonOrgan(cdNucleus, neurons, conf, gto_model);
				
				MusculotendonSuperClass musculotendon = new MusculotendonSuperClass(conf, muscle, tendon, spindle, gto);
								
				muscle.setAssociatedMusculotendon(musculotendon);
				
				spindles[ind] = spindle;
				gtos[ind] = gto;
				musculotendons[ind] = musculotendon;
				ind++;
			
		}
	}
	
	
	
	public void configureMotorUnits(Configuration conf, String cdNucleus, MotorUnit[] motorunits)
	{
		
		//System.out.println("a");
		
		
		// Always true: no more used
		boolean isMerged = conf.isMerge();

		double thickness = conf.getMiscellaneous( ReMoto.thickness + cdNucleus );
		double skinLayer = conf.getMiscellaneous( ReMoto.skinLayer );
		double tauAttenuation = conf.getMiscellaneous( ReMoto.tauAttenuation );
		double cteEnlargement = conf.getMiscellaneous( ReMoto.cteEnlargement );
		
		double gPeak1 = 0, gPeak2 = 0;
		double gMax1 = 0, gMax2 = 0;
		double tPeak1 = 0, tPeak2 = 0;
		double gPeak1Raikova = 0, gPeak2Raikova = 0;
		double tPeak1Raikova = 0, tPeak2Raikova = 0;
		double tHalf1Raikova = 0, tHalf2Raikova = 0;
		double ampEMG1 = 0, ampEMG2 = 0;
		double lambdaEMG1 = 0, lambdaEMG2 = 0;
		double b1 = 0, b2 = 0;
		double twTet1 = 0, twTet2 = 0;
		double bRaikova1 = 0, bRaikova2 = 0;
		double twTetRaikova1 = 0, twTetRaikova2 = 0;
		
		//System.out.println("b");

		Hashtable muPresetPosition = conf.getMuPresetPosition();

		MotorUnitVO muS = conf.getMotorUnitType(cdNucleus, ReMoto.S);
		MotorUnitVO muFR = conf.getMotorUnitType(cdNucleus, ReMoto.FR);
		MotorUnitVO muFF = conf.getMotorUnitType(cdNucleus, ReMoto.FF);
		NeuronVO referenceS = conf.getCompleteNeuronType(cdNucleus, ReMoto.S);
		NeuronVO referenceFR = conf.getCompleteNeuronType(cdNucleus, ReMoto.FR);
		NeuronVO referenceFF = conf.getCompleteNeuronType(cdNucleus, ReMoto.FF);
		MotorUnitVO muType;
		
		//System.out.println("c");
		
		for(int n = 0; n < motorunits.length; n++)
		{
			String type = motorunits[n].getType();
			String cd = motorunits[n].getCd();
			int index = motorunits[n].getIndex();
			
			//System.out.println("d");
			
			// Bias is used for properties that have two values 
			double bias = 0;
			int quantity = 0;

			if( type.equals( ReMoto.S ) )
			{
				quantity = referenceS.getQuantity();
			}
			else if( type.equals( ReMoto.FR ) )
			{
				quantity = referenceFR.getQuantity();				
				index -= referenceS.getQuantity();
			}
			else
			{
				quantity = referenceFF.getQuantity();				
				index -= ( referenceS.getQuantity() + referenceFR.getQuantity() );
			}
				
			if( quantity > 1 )
				bias = (double)(index - 1)/(double)(quantity - 1);
			
			// Set order by the FB size and type for the parameters below.
			// Other parameters assume the default value
			
			//System.out.println("e");
			
			// Always true: no more used
			if( isMerged )
			{
				
				//System.out.println("f");
				
				
				if( type.equals( ReMoto.S ) )
				{
					
					//System.out.println("g");
					
					
					gPeak1 = muS.getTwitchTension1();
					gMax1 = muS.getMaxTension1();
					tPeak1 = muS.getContractionTime1();
					gPeak1Raikova = muS.getTwitchTension1Raikova();
					tPeak1Raikova = muS.getContractionTime1Raikova();
					tHalf1Raikova = muS.getHalfRelaxationTimeRaikova1();
					ampEMG1 = muS.getAmpEMG1();
					lambdaEMG1 = muS.getLambdaEMG1();
					b1 = muS.getb1();
					twTet1 = muS.gettwTet1();
					bRaikova1 = muS.getbRaikova1();
					twTetRaikova1 = muS.gettwTetRaikova1();
					

					gPeak2 = ( muS.getTwitchTension2() + muFR.getTwitchTension1() ) / 2;
					gMax2 = ( muS.getMaxTension2() + muFR.getMaxTension1() ) / 2;
					tPeak2 = ( muS.getContractionTime2() + muFR.getContractionTime1() ) / 2;
					gPeak2Raikova = ( muS.getTwitchTension2Raikova() + muFR.getTwitchTension1Raikova() ) / 2;
					tPeak2Raikova = ( muS.getContractionTime2Raikova() + muFR.getContractionTime1Raikova() ) / 2;
					tHalf2Raikova = (muS.getHalfRelaxationTimeRaikova2() + muFR.getHalfRelaxationTimeRaikova1()) / 2;
					ampEMG2 = ( muS.getAmpEMG2() + muFR.getAmpEMG1() ) / 2;
					lambdaEMG2 = ( muS.getLambdaEMG2() + muFR.getLambdaEMG1() ) / 2;
					b2 = ( muS.getb2() + muFR.getb1() ) / 2;
					twTet2 = ( muS.gettwTet2() + muFR.gettwTet1() ) / 2;
					bRaikova2 = ( muS.getbRaikova2() + muFR.getbRaikova1() ) / 2;
					twTetRaikova2 = ( muS.gettwTetRaikova2() + muFR.gettwTetRaikova1() ) / 2;
				} 
				else if( type.equals( ReMoto.FR ) )
				{
					gPeak1 = ( muS.getTwitchTension2() + muFR.getTwitchTension1() ) / 2;
					gMax1 = ( muS.getMaxTension2() + muFR.getMaxTension1() ) / 2;
					tPeak1 = ( muS.getContractionTime2() + muFR.getContractionTime1() ) / 2;
					gPeak1Raikova = ( muS.getTwitchTension2Raikova() + muFR.getTwitchTension1Raikova() ) / 2;
					tPeak1Raikova = ( muS.getContractionTime2Raikova() + muFR.getContractionTime1Raikova() ) / 2;
					tHalf1Raikova = ( muS.getHalfRelaxationTimeRaikova2() + muFR.getHalfRelaxationTimeRaikova1()) / 2;
					ampEMG1 = ( muS.getAmpEMG2() + muFR.getAmpEMG1() ) / 2;
					lambdaEMG1 = ( muS.getLambdaEMG2() + muFR.getLambdaEMG1() ) / 2;
					b1 = ( muS.getb2() + muFR.getb1() ) / 2;
					twTet1 = ( muS.gettwTet2() + muFR.gettwTet1() ) / 2;
					bRaikova1 = ( muS.getbRaikova2() + muFR.getbRaikova1() ) / 2;
					twTetRaikova1 = ( muS.gettwTetRaikova2() + muFR.gettwTetRaikova1() ) / 2;
					
					gPeak2 = ( muFR.getTwitchTension2() + muFF.getTwitchTension1() ) / 2;
					gMax2 = ( muFR.getMaxTension2() + muFF.getMaxTension1() ) / 2;
					tPeak2 = ( muFR.getContractionTime2() + muFF.getContractionTime1() ) / 2;
					gPeak2Raikova = ( muFR.getTwitchTension2Raikova() + muFF.getTwitchTension1Raikova() ) / 2;
					tPeak2Raikova = ( muFR.getContractionTime2Raikova() + muFF.getContractionTime1Raikova() ) / 2;
					tHalf2Raikova = ( muFR.getHalfRelaxationTimeRaikova2() + muFF.getHalfRelaxationTimeRaikova1() ) / 2;
					ampEMG2 = ( muFR.getAmpEMG2() + muFF.getAmpEMG1() ) / 2;
					lambdaEMG2 = ( muFR.getLambdaEMG2() + muFF.getLambdaEMG1() ) / 2;
					b2 = ( muFR.getb2() + muFF.getb1() ) / 2;
					twTet2 = ( muFR.gettwTet2() + muFF.gettwTet1() ) / 2;
					bRaikova2 = ( muFR.getbRaikova2() + muFF.getbRaikova1() ) / 2;
					twTetRaikova2 = ( muFR.gettwTetRaikova2() + muFF.gettwTetRaikova1() ) / 2;
				}
				else
				{
					gPeak1 = ( muFR.getTwitchTension2() + muFF.getTwitchTension1() ) / 2;
					gMax1 = ( muFR.getMaxTension2() + muFF.getMaxTension1() ) / 2;
					tPeak1 = ( muFR.getContractionTime2() + muFF.getContractionTime1() ) / 2;
					gPeak1Raikova = ( muFR.getTwitchTension2Raikova() + muFF.getTwitchTension1Raikova() ) / 2;
					tPeak1Raikova = ( muFR.getContractionTime2Raikova() + muFF.getContractionTime1Raikova() ) / 2;
					tHalf1Raikova = ( muFR.getHalfRelaxationTimeRaikova2() + muFF.getHalfRelaxationTimeRaikova1() ) / 2;
					ampEMG1 = ( muFR.getAmpEMG2() + muFF.getAmpEMG1() ) / 2;
					lambdaEMG1 = ( muFR.getLambdaEMG2() + muFF.getLambdaEMG1() ) / 2;
					b1 = ( muFR.getb2() + muFF.getb1() ) / 2;
					twTet1 = ( muFR.gettwTet2() + muFF.gettwTet1() ) / 2;
					bRaikova1 = ( muFR.getbRaikova2() + muFF.getbRaikova1() ) / 2;
					twTetRaikova1 = ( muFR.gettwTetRaikova2() + muFF.gettwTetRaikova1() ) / 2;
					
					gPeak2 = muFF.getTwitchTension2();
					gMax2 = muFF.getMaxTension2();
					tPeak2 = muFF.getContractionTime2();
					gPeak2Raikova = muFF.getTwitchTension2Raikova();
					tPeak2Raikova = muFF.getContractionTime2Raikova();
					tHalf2Raikova = muFF.getHalfRelaxationTimeRaikova2();
					ampEMG2 = muFF.getAmpEMG2();
					lambdaEMG2 = muFF.getLambdaEMG2();
					b2 = muFF.getb2();
					twTet2 = muFF.gettwTet2();
					bRaikova2 = muFF.getbRaikova2();
					twTetRaikova2 = muFF.gettwTetRaikova2();
				}

				//motorunits[n].getTwitchFunction().setGpeak( Bias.gradual(gPeak1, gPeak2, bias ) );
				//motorunits[n].getTwitchFunction().setGmax( Bias.gradual(gMax1, gMax2, bias ) );
				//motorunits[n].getTwitchFunction().setTpeak( Bias.gradual(tPeak1, tPeak2, bias ) );
				motorunits[n].setGpeak( Bias.gradual(gPeak1, gPeak2, bias ) );
				motorunits[n].setGmax( Bias.gradual(gMax1, gMax2, bias ) );
				motorunits[n].setTpeak( Bias.gradual(tPeak1, tPeak2, bias ) );
				
				motorunits[n].setGpeakRaikova( Bias.gradual(gPeak1Raikova, gPeak2Raikova, bias ) );
				motorunits[n].setTpeakRaikova( Bias.gradual(tPeak1Raikova, tPeak2Raikova, bias ) );
				motorunits[n].setTHalfRaikova( Bias.gradual(tHalf1Raikova, tHalf2Raikova, bias ) );
				
				motorunits[n].setB(Bias.gradual(b1, b2, bias));
				motorunits[n].setTwTet(Bias.gradual(twTet1, twTet2, bias));
				
				motorunits[n].setBRaikova(Bias.gradual(bRaikova1, bRaikova2, bias));
				motorunits[n].setTwTetRaikova(Bias.gradual(twTetRaikova1, twTetRaikova2, bias));
								
				motorunits[n].setAmpEMG( Bias.gradual(ampEMG1, ampEMG2, bias ) );
				motorunits[n].setLambdaEMG( Bias.gradual(lambdaEMG1, lambdaEMG2, bias ) );
			}
			else
			{
				if( type.equals( ReMoto.S ) )
					muType = muS;
				else if( type.equals( ReMoto.FR ) )
					muType = muFR;
				else
					muType = muFF;
				
				//motorunits[n].getTwitchFunction().setGpeak( Bias.gradual(muType.getTwitchTension1(), muType.getTwitchTension2(), bias ) );
				//motorunits[n].getTwitchFunction().setGmax( Bias.gradual(muType.getMaxTension1(), muType.getMaxTension2(), bias ) );
				//motorunits[n].getTwitchFunction().setTpeak( Bias.gradual(muType.getContractionTime1(), muType.getContractionTime2(), bias ) );
				motorunits[n].setGpeak( Bias.gradual(muType.getTwitchTension1(), muType.getTwitchTension2(), bias ) );
				motorunits[n].setGmax( Bias.gradual(muType.getMaxTension1(), muType.getMaxTension2(), bias ) );
				motorunits[n].setTpeak( Bias.gradual(muType.getContractionTime1(), muType.getContractionTime2(), bias ) );
				
				
				motorunits[n].setGpeakRaikova( Bias.gradual(muType.getTwitchTension1Raikova(), muType.getTwitchTension2Raikova(), bias ) );
				motorunits[n].setTpeakRaikova( Bias.gradual(muType.getContractionTime1Raikova(), muType.getContractionTime2Raikova(), bias ) );
				motorunits[n].setTHalfRaikova( Bias.gradual(muType.getHalfRelaxationTimeRaikova1(), muType.getHalfRelaxationTimeRaikova2(), bias ) );
				
				motorunits[n].setB(Bias.gradual(muType.getb1(), muType.getb2(), bias));
				motorunits[n].setTwTet(Bias.gradual(muType.gettwTet1(), muType.gettwTet2(), bias));
				
				motorunits[n].setBRaikova(Bias.gradual(muType.getbRaikova1(), muType.getbRaikova2(), bias));
				motorunits[n].setTwTetRaikova(Bias.gradual(muType.gettwTetRaikova1(), muType.gettwTetRaikova2(), bias));
				
				
				motorunits[n].setAmpEMG( Bias.gradual(muType.getAmpEMG1(), muType.getAmpEMG2(), bias ) );
				motorunits[n].setLambdaEMG( Bias.gradual(muType.getLambdaEMG1(), muType.getLambdaEMG2(), bias ) );
			}
			
			if( conf.getMuDistribution() == ReMoto.muDistributionPreset )
			{
				Point point = (Point)muPresetPosition.get( cdNucleus + cd );
				
				if( point != null )
				{
					// Place motor-unit center in a preset position inside the muscle
					motorunits[n].presetSpatialDistribution( point.getY(), point.getZ(), thickness, skinLayer, tauAttenuation, cteEnlargement, n, motorunits.length );
				}
				else
				{
					// Place motor-unit center in a random position inside the muscle
					motorunits[n].randomSpatialDistribution( thickness, skinLayer, tauAttenuation, cteEnlargement );
				}
			}
			else if( conf.getMuDistribution() == ReMoto.muDistributionLinear )
			{
				// Place motor-unit center in a linear position inside the muscle
				motorunits[n].linearSpatialDistribution( thickness, skinLayer, tauAttenuation, cteEnlargement, n, motorunits.length );
			}
			else
			{
				// Place motor-unit center in a random position inside the muscle
				motorunits[n].randomSpatialDistribution( thickness, skinLayer, tauAttenuation, cteEnlargement );
			}
		}
	}




	public void createJoint(Configuration conf, Neuron[][] neurons,
			MusculotendonSuperClass[] musculotendons, MuscleSpindle[] spindles,
			GolgiTendonOrgan[] gtos, Joint joint) {
		
		createMusculotendons(conf, neurons, spindles, gtos, musculotendons);
		
		//System.out.println("a");
		
		if(JointModel.equals("isometric")){
			AnkleIsometricModel ankleIsometricModel = new AnkleIsometricModel(conf, musculotendons);
			joint.setAnkleModel(ankleIsometricModel);
			
			System.out.println("Creating " + JointModel);
			System.out.println("numActiveMuscles: " + numActiveMuscles);
			
			for(int i = 0; i < numActiveMuscles; i++){
				//System.out.println("d");
				musculotendons[i].setAssociatedJoint(joint.getAnkleModel());
			}
		}
		else if(JointModel.equals("pendulum")){
			InvertedPendulumModel invertedPendulumModel = new InvertedPendulumModel(conf, musculotendons);
			joint.setAnkleModel2(invertedPendulumModel);
			
			System.out.println("Creating " + JointModel);
			System.out.println("numActiveMuscles: " + numActiveMuscles);
			
			for(int i = 0; i < numActiveMuscles; i++){
				//System.out.println("d");
				musculotendons[i].setAssociatedJoint2(joint.getAnkleModel2());
			}
		}
		else if(JointModel.equals("position")){
			PositionTaskModel positionTaskModel = new PositionTaskModel(conf, musculotendons);
			joint.setAnkleModel3(positionTaskModel);
			
			System.out.println("Creating " + JointModel);
			System.out.println("numActiveMuscles: " + numActiveMuscles);
			
			for(int i = 0; i < numActiveMuscles; i++){
				//System.out.println("d");
				musculotendons[i].setAssociatedJoint3(joint.getAnkleModel3());
			}
		}
		
		//System.out.println("e");
		
	}
	
	
	

}
