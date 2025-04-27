package br.remoto.teste;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import br.remoto.dao.ConfigurationDAO;
import br.remoto.dao.UserDAO;
import br.remoto.model.Configuration;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.ReMoto;
import br.remoto.model.ResultDisplay;
import br.remoto.model.Simulation;
import br.remoto.model.Neuron.Miscellaneous;
import br.remoto.model.Neuron.NeuralTract;
import br.remoto.model.vo.ConductanceVO;
import br.remoto.model.vo.DynamicVO;
import br.remoto.model.vo.MiscellaneousVO;
import br.remoto.model.vo.NerveVO;
import br.remoto.model.vo.NeuronVO;
import br.remoto.model.vo.Nucleus;
import br.remoto.model.vo.ResultVO;
import br.remoto.model.vo.User;
import br.remoto.util.PlotCombinedGraph;
import br.remoto.util.PlotScatter;
import br.remoto.util.PlotXYLine;
import br.remoto.util.Point;


public class PositionTaskScriptBagVar 
{
	UserDAO userDAO = new UserDAO();
	User user;
	Configuration conf;
	
	MessageFormat mf = new MessageFormat("{0,number,#.#####}", Locale.US);
	
	public PositionTaskScriptBagVar()
	{
		// Specify ReMoto's path
		
		
		ReMoto.path = "C:\\Users\\renato\\Dropbox\\PositionTask\\TPBag2Var\\";
		//ReMoto.path = "C:\\Users\\Vitor\\Desktop\\Workspace2\\remoto\\WebContent\\";
		
		// Specify user account
		
		user = userDAO.loadUser("rnwatanabe", "5o3iuzi");
		//user = userDAO.loadUser("vitor", "lodemarta");
		
		conf = new Configuration();
	}
		
	
	public static void main(String[] args) throws FileNotFoundException 
	{
		PositionTaskScriptBagVar teste = new PositionTaskScriptBagVar();
		
		teste.run();
	}
	
	public void run() throws FileNotFoundException
	{
		ConfigurationDAO simDAO = new ConfigurationDAO();
    	String cdSimulation = "1234";
    	
    	
    	// LOAD SCENARIO
    	
		conf = simDAO.getConfiguration(25);
		
		//conf.setMerge( true );
		ResultVO resultVO = new ResultVO();
		
		
		// CONFIGURATION SETTINGS
		
		
		//resultVO.setWithEMGnoise( false );
		//resultVO.setWithEMGattenuation( false );
		resultVO.setOpt( ReMoto.array );
		resultVO.setTask("");
		resultVO.setCdAnalysis("parameters");
		conf.setResult( resultVO );
		
		conf.setCdMuscleModel("hill");
		
		
		conf.setGammaDynamic(30);
		conf.setGammaStatic(30);
		conf.setPrimaryBag1Gain(7500);
		conf.setPrimaryBag2AndChainGain(4000);
		conf.setSecondaryBag2AndChainGain(4000);
								
		conf.setCdJoint("ankle");
		conf.setCdJointModel("position");
		conf.setDecimationFrequency(20000);
		//conf.setRecruitmentOrderFES("uniform");
		conf.setStep(0.05);
		conf.setTFin(15000);
		conf.setMiscellaneous("step", conf.getStep());
		
		int numOfSimulations = (5000 - 3500) / 100;
		
		conf.setChangedConfiguration( true );
		conf.setKeepProperties( false );
		
		String synapticDynamics = ReMoto.none;
		
		String dataPath;
		String graphicPath;
		
		dataPath = ReMoto.path + "data";
		graphicPath = ReMoto.path + "figures";
		
		//dataPath = ReMoto.path + "simulation\\data\\tests\\"  +  (int) (conf.getStep() * 1000) + "\\" ;
		//graphicPath = ReMoto.path + "simulation\\graphics\\tests\\" +  (int) (conf.getStep() * 1000)  + "\\" ;
		
		String timeStepString = "TimeStep: " + String.valueOf((int) (conf.getStep() * 1000)) + " us";
		String synapticDynamicsString = "SynapticDynamics: " + synapticDynamics;
		
		Simulation sim = new Simulation( conf, cdSimulation, timeStepString, synapticDynamicsString );
    	
    	sim.createNetwork();
    	sim.createJoint();
		sim.resetMuscles(conf.getStep());
		sim.createInputs();
		sim.createStimulation();
		sim.createSynapses();
		
		
		for(int i = 0; i < conf.getNeuronTypes(ReMoto.DT, ReMoto.TR).size(); i++){
    		
	    	NeuronVO dt = (NeuronVO) conf.getNeuronTypes(ReMoto.DT, ReMoto.TR).get(i);
	    	if(dt.isActive()){
	    		dt.setMean(10.8);
	    		dt.setStd(7);
	    		dt.setOrder(7);
	    	}
	    }
		
		String path = null;
		
		//double amp = -1.5;
		
		//path = "Amp=" + ((int)(-10 * amp));
		path = "";
		
		//for(int y = 0; y < conf.getNeuronTypes().size(); y++)
		//{
	    //	NeuronVO reference = (NeuronVO)conf.getNeuronTypes().get(y);
		//	
		//	if( !reference.isActive() || reference.getQuantity() == 0) 
	    //		continue;
		//	
		//	for(int index = 0; index < reference.getQuantity(); index++)
		//	{
		//	if( reference.getCategory().equals( ReMoto.TR ) )
		//    	{
		//    		reference.setAmp(amp);
		//    		reference.setWidth(3000);
		//    	}
		//	}
		//}
		
		for(int k = 1; k <= numOfSimulations; k++){
			
			conf.setPrimaryBag2AndChainGain(3500 + 100 * (k - 1));
			
			System.out.println("-> SCENARIO:  " + timeStepString + "  " + 
								"  Sim.: " + k);
			
			sim.run();
    		
			generateLogOfScenario(k, dataPath, path);
			
			generateAndStoreData("emgSOL", sim, dataPath, graphicPath, path, k);
			generateAndStoreData("emgMG", sim, dataPath, graphicPath, path, k);
			generateAndStoreData("emgLG", sim, dataPath, graphicPath, path, k);
			//generateAndStoreData("emg", sim, dataPath, graphicPath, path, k);
			generateAndStoreData("spikesMNsSOL", sim, dataPath, graphicPath, path, k);
			generateAndStoreData("spikesMNsMG", sim, dataPath, graphicPath, path, k);
			generateAndStoreData("spikesMNsLG", sim, dataPath, graphicPath, path, k);
			generateAndStoreData("spikesIasSOL", sim, dataPath, graphicPath, path, k);
			generateAndStoreData("spikesIasMG", sim, dataPath, graphicPath, path, k);
			generateAndStoreData("spikesIasLG", sim, dataPath, graphicPath, path, k);
			//generateAndStoreData("meanFiringRateIa", sim, dataPath, graphicPath, path, k);
			//generateAndStoreData("firingRateMNs", sim, dataPath, graphicPath, path, k);
			//generateAndStoreData("muscleForce", sim, dataPath, graphicPath, path, k);
			//generateAndStoreData("spikesIaINs", sim, dataPath, graphicPath, path, k);
			generateAndStoreData("muscleLength", sim, dataPath, graphicPath, path, k);
			generateAndStoreData("jointAngle", sim, dataPath, graphicPath, path, k);
			generateAndStoreData("jointTorque", sim, dataPath, graphicPath, path, k);
			
			System.out.println("-> Simulation Number " + k + " end");
		}
		
		System.out.println("-> SIMULATION END");

		
	}
	
	public void generateAndStoreData(String output, Simulation sim, String dataPath, String graphicPath, String path, int simCount) throws FileNotFoundException{
		
		int numSubplots = 1;
		
		conf.setNumOfSubplots(numSubplots);
		
		List[] nmMuscles 			= new List[numSubplots];
		List[] nmSubplots 			= new List[numSubplots];
		List[] nmCdNeurons 			= new List[numSubplots];
		List[] nmCdSpecification 	= new List[numSubplots];
		List[] yLabels 				= new List[numSubplots];
		List[] legendLabels 		= new List[numSubplots];
				
		for(int k = 0; k < numSubplots; k++){
			nmSubplots[k] 			= new ArrayList();
			nmCdNeurons[k] 			= new ArrayList();
			nmCdSpecification[k] 	= new ArrayList();
			yLabels[k] 				= new ArrayList();
			legendLabels[k] 		= new ArrayList();
			nmMuscles[k] 			= new ArrayList();
		}
		
		if(output.equals("emgSOL")){
			nmSubplots[0].add(			"EMG");
			nmCdNeurons[0].add(			"");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"EMG SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
		}
		if(output.equals("emgMG")){
			nmSubplots[0].add(			"EMG");
			nmCdNeurons[0].add(			"");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"EMG MG");
			legendLabels[0].add(		"MG");
			nmMuscles[0].add(			"MG");
		}
		if(output.equals("emgLG")){
			nmSubplots[0].add(			"EMG");
			nmCdNeurons[0].add(			"");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"EMG LG");
			legendLabels[0].add(		"MLG");
			nmMuscles[0].add(			"LG");
		}
		else if(output.equals("spikesMNsSOL")){
			nmSubplots[0].add(			"spikeTimes");
			nmCdNeurons[0].add(			"All MNs");
			nmCdSpecification[0].add(	"atTerminal");
			yLabels[0].add(				"MNs SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
		}
		else if(output.equals("spikesMNsMG")){
			nmSubplots[0].add(			"spikeTimes");
			nmCdNeurons[0].add(			"All MNs");
			nmCdSpecification[0].add(	"atTerminal");
			yLabels[0].add(				"MNs MG");
			legendLabels[0].add(		"MG");
			nmMuscles[0].add(			"MG");
		}
		else if(output.equals("spikesMNsLG")){
			nmSubplots[0].add(			"spikeTimes");
			nmCdNeurons[0].add(			"All MNs");
			nmCdSpecification[0].add(	"atTerminal");
			yLabels[0].add(				"MNs LG");
			legendLabels[0].add(		"LG");
			nmMuscles[0].add(			"LG");
		}
		else if(output.equals("firingRateMNs")){
			nmSubplots[0].add(			"firingRate");
			nmCdNeurons[0].add(			"All MNs");
			nmCdSpecification[0].add(	"atTerminal");
			yLabels[0].add(				"MNs SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
		}
		else if(output.equals("spikesIasSOL")){
			nmSubplots[0].add(			"spikeTimes");
			nmCdNeurons[0].add(			"Ia");
			nmCdSpecification[0].add(	"atTerminal");
			yLabels[0].add(				"Ia SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
		}
		else if(output.equals("spikesIasMG")){
			nmSubplots[0].add(			"spikeTimes");
			nmCdNeurons[0].add(			"Ia");
			nmCdSpecification[0].add(	"atTerminal");
			yLabels[0].add(				"Ia MG");
			legendLabels[0].add(		"MG");
			nmMuscles[0].add(			"MG");
		}
		else if(output.equals("spikesIasLG")){
			nmSubplots[0].add(			"spikeTimes");
			nmCdNeurons[0].add(			"Ia");
			nmCdSpecification[0].add(	"atTerminal");
			yLabels[0].add(				"Ia LG");
			legendLabels[0].add(		"LG");
			nmMuscles[0].add(			"LG");
		}
		else if(output.equals("meanFiringRateIa")){
			nmSubplots[0].add(			"meanFiringRate");
			nmCdNeurons[0].add(			"Ia");
			nmCdSpecification[0].add(	"atTerminal");
			yLabels[0].add(				"Ia SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
		}
		else if(output.equals("jointAngle")){
			nmSubplots[0].add(			"jointAngle");
			nmCdNeurons[0].add(			"");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"Angle");
			legendLabels[0].add(		"Ankle");
			nmMuscles[0].add(			"SOL");
		}
		else if(output.equals("muscleForce")){
			nmSubplots[0].add(			"muscleForce");
			nmCdNeurons[0].add(			"All MUs");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"Force SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
		}
		else if(output.equals("jointTorque")){
			nmSubplots[0].add(			"jointTorque");
			nmCdNeurons[0].add(			"");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"Torque");
			legendLabels[0].add(		"Ankle");
			nmMuscles[0].add(			"SOL");
		}
		else if(output.equals("spikesIaINs")){
			nmSubplots[0].add(			"spikeTimes");
			nmCdNeurons[0].add(			"IaIN");
			nmCdSpecification[0].add(	"atTerminal");
			yLabels[0].add(				"IaINs SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
		}
		else if(output.equals("somaticPotential")){
			nmSubplots[0].add(			"somaPotential");
			nmCdNeurons[0].add(			"IN IaIn 1");
			nmCdSpecification[0].add(	"atTerminal");
			yLabels[0].add(				"IaINs SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
		}
		else if(output.equals("muscleLength")){
			nmSubplots[0].add(			"muscleLength");
			nmCdNeurons[0].add(			"");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"Length [m]");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add("SOL");
		}		
		else if(output.equals("jointAngle")){
			nmSubplots[0].add(			"jointAngle");
			nmCdNeurons[0].add(			"");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"Angle [deg]");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add("SOL");
		}		
		else return;
		
		conf.setNmSubplots(			nmSubplots);
		conf.setNmCdNeurons(		nmCdNeurons);
		conf.setNmCdSpecification(	nmCdSpecification);
		conf.setyLabels(			yLabels);
		conf.setLegendLabels(		legendLabels);
		conf.setNmMuscles(			nmMuscles);
				
		ResultDisplay results = new ResultDisplay(conf);
		
		ArrayList outputList = new ArrayList();
		
		results.generateResults(sim, outputList);

		double y = 0;
		double t = 0;
		
		XYSeries xySeries = new XYSeries(output);
		
		File outputFile = null;
	    
		int varStep = 3500 + 100 * (simCount - 1);
	
		
		outputFile = new File(dataPath + path + "\\" + output + "Bag2" + varStep + ".txt");
		
	    PrintWriter printWriter = new PrintWriter(outputFile);
	    
	    String ind;
	    
		for(int j = 0; j < outputList.size(); j++)
		{
			Point point = (Point)outputList.get(j);
			
			ind = point.getIndex();
			t = point.getX();
			y = point.getY();
			
			Object[] objT = { new Double(t) };
			Object[] objF = { new Double(y) };
			
			printWriter.println(ind + "\t" + mf.format(objT) + "\t" + mf.format(objF));
			
			xySeries.add( t, Double.valueOf(y) );
		}
		
		printWriter.close();
		
		XYSeriesCollection datasetS = new XYSeriesCollection();
	    datasetS.addSeries( xySeries );

	    if(output.equals("spikesMNs") || output.equals("spikesIas") || output.equals("firingRateMNs") || output.equals("spikesIaINs") ){
	    	PlotScatter.generate(datasetS,
					 graphicPath + path + "\\" + output + simCount + ".jpg",
					 output, 
					 "Time [ms]", 
					 output);
	    }
	    else{
	    	PlotXYLine.generate(datasetS,
	    			 graphicPath + path + "\\" + output + simCount + ".jpg",
					 output, 
					 "Time [ms]", 
					 output);
	    }
	    
	    results = null;

		System.gc();
	}
	
	public void generateLogOfScenario(int simCount, String dataPath, String path) throws FileNotFoundException{
		
		
		File output;
	    
		output = new File(dataPath + path + "\\scenario" + simCount + ".txt");
		
	    PrintWriter printWriter = new PrintWriter(output);
	    
	    printWriter.println("");
	    printWriter.println("*******************************************");
	    printWriter.println("*********   SIMULATION SCENARIO   *********");
	    printWriter.println("*******************************************");
	    printWriter.println("");
	    
	    printWriter.println("");
	    printWriter.println("");
	    printWriter.println("------CONFIGURATION-------");
	    printWriter.println("");
	    
	    printWriter.println("conf.getCdJoint():             " + conf.getCdJoint());
	    printWriter.println("conf.getCdJointModel():        " + conf.getCdJointModel());
	    printWriter.println("conf.getDecimationFrequency(): " + conf.getDecimationFrequency());
	    printWriter.println("conf.getRecruitmentOrderFES(): " + conf.getRecruitmentOrderFES());
	    printWriter.println("conf.getStep():                " + conf.getStep());
	    printWriter.println("conf.getTFin():                " + conf.getTFin());
	    printWriter.println("conf.isChangedConfiguration(): " + conf.isChangedConfiguration());
	    printWriter.println("conf.isKeepProperties():       " + conf.isKeepProperties());
		
	    printWriter.println("");
	    printWriter.println("");
	    printWriter.println("------NEURONS-------");
	    printWriter.println("");
	    printWriter.println("Only neurons present in the simulation are shown.");
	    printWriter.println("");
	    
    	for(int i = 0; i < conf.getNeuronTypes().size(); i++){
    		NeuronVO neuronVO = (NeuronVO) conf.getNeuronTypes().get(i);
    		
	    	if(neuronVO.isActive()){
	    		printWriter.println("conf.getNeuronTypes().get(" + i + "): " + "\t" + 
	    				"neuronVO.getCdNucleus(): " 	+ neuronVO.getCdNucleus()  		+ "\t" +
	    				"neuronVO.getCategoryType(): " 	+ neuronVO.getCategoryType() 	+ "\t" +
	    				"neuronVO.getQuantity(): " 		+ neuronVO.getQuantity());
	    		
	    	}
	    }
    	
    	
    	printWriter.println("");
	    printWriter.println("");
	    printWriter.println("------SYNAPSES-------");
	    printWriter.println("");
	    printWriter.println("Only conductances present in the simulation are shown.");
	    printWriter.println("");
	    
	    
	    List conductances = conf.getAllActiveSynapticConductances();
	    
	    for(int i = 0; i < conductances.size(); i++){
	    	ConductanceVO g = (ConductanceVO) conductances.get(i);
	    	
	    	printWriter.println("g.getCdConductanceType(): " + g.getCdConductanceType() + "\t" +
	    						"g.getGmax(): " + g.getGmax() + "\t" + 
	    						"g.getDynamicType(): " + g.getDynamicType());
	    	
	    }
	    
	    printWriter.println("");
	    printWriter.println("");
	    printWriter.println("------DESCENDING COMMANDS-------");
	    printWriter.println("");
	    printWriter.println("Only DTs present in the simulation are shown.");
	    printWriter.println("");
	    
	    
	    for(int i = 0; i < conf.getNeuronTypes(ReMoto.DT, ReMoto.TR).size(); i++){
    		
	    	NeuronVO dt = (NeuronVO) conf.getNeuronTypes(ReMoto.DT, ReMoto.TR).get(i);
	    	
	    	printWriter.println("conf.getNeuronTypes(ReMoto.DT, ReMoto.TR).get(" + i + "): " + "\t" + 
    				"dt.getCategoryType(): " 	+ dt.getCategoryType()+ "\t" + 
    				"dt.getMean(): " 	+ dt.getMean()+ "\t" + 
    				"dt.getStd(): " 	+ dt.getStd()+ "\t" + 
    				"dt.getOrder(): " 	+ dt.getOrder()+ "\t" + 
    				"dt.getDistribution(): " 	+ dt.getDistribution()
    				);
	    }


	    
	    for(int y = 0; y < conf.getNeuronTypes().size(); y++)
		{
	    	NeuronVO reference = (NeuronVO)conf.getNeuronTypes().get(y);
			
			if( !reference.isActive() || reference.getQuantity() == 0) 
	    		continue;
			
			for(int index = 0; index < reference.getQuantity(); index++)
			{
			    if( reference.getCategory().equals( ReMoto.TR ) )
		    	{
		    		
		    		printWriter.println("conf.getNeuronTypes().get(" + y + "): " + "\t" + 
		    				"reference.getCategory().equals( ReMoto.TR )" + 
		    				"reference.getCdSignal(): " 	+ reference.getCdSignal()+ "\t" + 
		    				"reference.getAmp(): " 	+ reference.getAmp()+ "\t" + 
		    				"reference.getFreq(): " 	+ reference.getFreq()+ "\t" + 
		    				"reference.getIni(): " 	+ reference.getIni()+ "\t" + 
		    				"reference.getFin(): " 	+ reference.getFin()+ "\t" + 
		    				"reference.getWidth(): " 	+ reference.getWidth()+ "\t" 
		    				);
		    	}
			}
		}
	    
    	printWriter.println("");
	    printWriter.println("");
	    printWriter.println("------NERVES-------");
	    printWriter.println("");
	    printWriter.println("Only nerves present in the simulation are shown.");
	    printWriter.println("");
	    
	    for(int i = 0; i < conf.getAllNerves().size(); i++){
	    	NerveVO nerveVO = (NerveVO) conf.getAllNerves().get(i);
	    	
	    	if(nerveVO.isActive()){
	    		printWriter.println("conf.getAllNerves().get(" + i + "): " + "\t" + 
	    							"nerveVO.getCdNerve(): " + nerveVO.getCdNerve() + "\t" + 
	    							nerveVO.isActive());
	    		printWriter.println("");
		    	printWriter.println("\t"  + ".getAmp():      " 		+ nerveVO.getAmp());
	    		printWriter.println("\t"  + ".getCdJoint():  " 	 	+ nerveVO.getCdJoint());
	    		printWriter.println("\t"  + ".getCdSignal(): " 	 	+ nerveVO.getCdSignal());
	    		printWriter.println("\t"  + ".getDelay():    " 	 	+ nerveVO.getDelay());
	    		printWriter.println("\t"  + ".getFreq():     " 		+ nerveVO.getFreq());
	    		printWriter.println("\t"  + ".getTini():     " 		+ nerveVO.getTini());
	    		printWriter.println("\t"  + ".getTfin():     " 		+ nerveVO.getTfin());
	    		
	    	}
	    }
	    
	    printWriter.println("");
	    printWriter.println("");
	    printWriter.println("------JOINT-------");
	    printWriter.println("");
	    
	    printWriter.println("conf.getCdJoint():                  " + conf.getCdJoint());
	    printWriter.println("conf.getCdJointModel():             " + conf.getCdJointModel());
	    
	    //printWriter.println("conf.getJointStimulusInitialTime(): " + conf.getJointStimulusInitialTime());
	    //printWriter.println("conf.getJointStimulusFinalTime(): 	 " + conf.getJointStimulusFinalTime());
	    
	    //printWriter.println("conf.getJointInitialAngle():        " + conf.getJointInitialAngle());
	    //printWriter.println("conf.getJointFinalAngle():          " + conf.getJointFinalAngle());
	    printWriter.println("conf.getJointVelocity():            " + conf.getJointVelocity());
	    
	    
	    printWriter.println("");
	    printWriter.println("");
	    printWriter.println("------MUSCLE-------");
	    printWriter.println("");
	    
	    printWriter.println("conf.getCdMuscleModel(): " + conf.getCdMuscleModel());
	    
	    printWriter.println("");
	    printWriter.println("");
	    printWriter.println("------EMG-------");
	    printWriter.println("");
	    
	    printWriter.println("conf.getCdEMGModel(): " + conf.getCdEMGModel());
	    
	    printWriter.println("");
	    printWriter.println("");
	    printWriter.println("------MUSCLE SPINDLE-------");
	    printWriter.println("");
	    
	    printWriter.println("conf.getCdSpindleModel():                " 	+ conf.getCdSpindleModel());
	    printWriter.println("conf.getGammaStatic():                   " 	+ conf.getGammaStatic());
	    printWriter.println("conf.getGammaDynamic():                  "  	+ conf.getGammaDynamic());
	    printWriter.println("conf.getPrimaryBag1Gain():               " 	+ conf.getPrimaryBag1Gain());
	    printWriter.println("conf.getPrimaryBag2AndChainGain():       " 	+ conf.getPrimaryBag2AndChainGain());
	    printWriter.println("conf.getSecondaryBag2AndChainGain():       " 	+ conf.getSecondaryBag2AndChainGain());
	    printWriter.println("conf.getInitialRecruitmentThresholdIa(): " 	+ conf.getInitialRecruitmentThresholdIa());
	    printWriter.println("conf.getFinalRecruitmentThresholdIa():   " 	+ conf.getFinalRecruitmentThresholdIa());
	    printWriter.println("conf.getInitialRecruitmentThresholdII(): " 	+ conf.getInitialRecruitmentThresholdII());
	    printWriter.println("conf.getFinalRecruitmentThresholdII():   " 	+ conf.getFinalRecruitmentThresholdII());
	    
	    printWriter.println("");
	    printWriter.println("");
	    printWriter.println("------GOLGI TENDON ORGAN-------");
	    printWriter.println("");
	    
	    printWriter.println("conf.getCdGtoModel():                    "  + conf.getCdGtoModel());
	    printWriter.println("conf.getInitialRecruitmentThresholdIb(): "  + conf.getInitialRecruitmentThresholdIb());
	    printWriter.println("conf.getFinalRecruitmentThresholdIb():   "  + conf.getFinalRecruitmentThresholdIb());
	    
    	
	    
	    printWriter.println("");
	    printWriter.println("");
	    printWriter.println("------MISCELLANEOUS-------");
	    printWriter.println("");
	    
	    for(int i = 0; i < conf.getMiscellaneous().size(); i++){
	    	MiscellaneousVO misc = (MiscellaneousVO) conf.getMiscellaneous().get(i);
	    	printWriter.println("conf.getMiscellaneous().get(" + i + "): " + "\t" + 
	    							misc.getValue() 	+ "\t"  + 
	    							misc.getProperty());
	    	
	    }
	    
	    printWriter.close();
	    
	}

}
