package br.remoto.model;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.Double;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.ServletOutputStream;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import br.remoto.model.Neuron.Motoneuron;
import br.remoto.model.Neuron.NeuralTract;
import br.remoto.model.Neuron.Neuron;
import br.remoto.model.Neuron.SensoryFiber;
import br.remoto.model.Neuron.SpinalNeuron;
import br.remoto.model.factory.NetworkFactory;
import br.remoto.model.vo.JointVO;
import br.remoto.model.vo.NerveVO;
import br.remoto.model.vo.NeuronVO;
import br.remoto.model.vo.Nucleus;
import br.remoto.model.vo.ResultVO;
import br.remoto.model.vo.Summary;
import br.remoto.util.Distribution;
import br.remoto.util.PlotBarGraph;
import br.remoto.util.ButterworthBilinear;
import br.remoto.util.ButterworthImpulseInvariance;
import br.remoto.util.Coordenate;
import br.remoto.util.ElectroCalculation;
import br.remoto.util.PlotCombinedGraph;
import br.remoto.util.PlotHistogramGraph;
import br.remoto.util.PlotLineGraph;
import br.remoto.util.PlotScatterGraph;
import br.remoto.util.Point;
import br.remoto.util.Signal;


public class InputGraphicDisplay
{
	private Configuration conf;
	private ArrayList output;

	private String graphicEnvironment = "";
	
	
	private int chartHeigth_input = 300;
	private int chartWidth_input = 450;
	private int chartHeigth_stimulation = 375;
	private int chartWidth_stimulation = 450;
	private int chartHeigth_injCurrent = 300;
	private int chartWidth_injCurrent = 450;
	private int chartHeigth_biomechInput = 300;
	private int chartWidth_biomechInput = 450;
	
	private String title_input = "Modulated Mean ISI";
	private String label_input = "Mean ISI [ms]";
	private String title_inputHist = "Basic Drive Distribution";
	private String title_stimulation = "";
	private String label_stimulation = "Stimulation [mA]";
	private String title_stimulationModulating = "";
	private String label_stimulationModulating = "Frequency [Hz]";
	private String title_injCurrent = "";
	private String label_injCurrent = "Injected Current [nA]";
	private String title_biomechInput = "";
	private String label_biomechInput = "Muscle Length [mm]";
	
	public InputGraphicDisplay(Configuration conf, String graficEnv)
	{
		this.conf = conf;
		this.graphicEnvironment = graficEnv;
	}

	public InputGraphicDisplay(Configuration conf)
	{
		this.conf = conf;
	}
	
	
	
	public void generateResults(Simulation sim, OutputStream stream)
    {
		
			try {
				
				displaySignal(stream, graphicEnvironment);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	

	public void generateResults(Simulation sim, ArrayList output)
    {
		this.output = output;

		generateResults(sim, (ServletOutputStream)null);
    }
	
	
	
	private void displaySignal(OutputStream stream, String env) throws IOException
	{
		
			double time = 0;
    		int totSteps = 0;
    		int iteration = 0;
    		
    		double step = conf.getStep();
    		double tFin = conf.getTFin();
    		
    		totSteps = (int)(tFin/step);
    		
    		XYSeriesCollection dataset = null;
    		XYSeriesCollection dataset2 = null;
    		HistogramDataset datasetHist = null;
    		
			if(env.equals("input")){
				
				List inputs = null;
				
				inputs = conf.getNeuronTypes(ReMoto.DT, ReMoto.TR);
				
				dataset = new XYSeriesCollection();
				ModulatingSignal modulatingSignal;
			
				for(int i = 0; i < inputs.size(); i++)
				{
					NeuronVO neu = (NeuronVO)inputs.get(i);
					
					
					if( neu.isActive() )
					{
						modulatingSignal = conf.getDescendingCommand( neu.getType(), neu.getCdNucleus() );
						
						if(modulatingSignal.getModType().equals("rate")){
							title_input = "Modulated Mean Firing Rate";
							label_input = "Mean Firing Rate [pps]";
						}
						
						XYSeries serie = new XYSeries( NeuronVO.convertName(neu.getType(), false) );
						
						for(iteration = 0; iteration < totSteps; iteration++)
						{
							time = step * iteration;
							
							if(modulatingSignal.getModType().equals("ISI"))
								serie.add(time, neu.getMean() + modulatingSignal.value(time));
							else serie.add(time, 1000/neu.getMean() + modulatingSignal.value(time));
						}
						
						dataset.addSeries(serie);
					}
				}

				PlotLineGraph.generate( dataset,
						 stream,
						 title_input, 
						 "Time [ms]", 
						 label_input,
						 chartWidth_input,
						 chartHeigth_input,
						 inputs.size());
			}
			else if(env.equals("inputHist")){
				
				List inputs = null;
				inputs = conf.getNeuronTypes(ReMoto.DT, ReMoto.TR);
				double ad[] = new double[1000];
				datasetHist = new HistogramDataset();
				double aux;
				
				for(int i = 0; i < inputs.size(); i++)
				{
					NeuronVO neu = (NeuronVO)inputs.get(i);
					
					if( neu.isActive() )
					{
						
						for (int j = 0; j < 1000; j++){
							
							if(neu.getDistribution().equals(ReMoto.poisson)){
								
								aux = Distribution.poissonPoint(neu.getMean());
								if(aux >= 0)	ad[j] = aux;
								
							}
							else if(neu.getDistribution().equals("Gm")){
								
								aux = Distribution.gammaPoint(neu.getMean() / neu.getStd(),neu.getStd());
								if(aux >= 0)	ad[j] = aux;
								
							}
							else if(neu.getDistribution().equals("G")){
								
								aux = Distribution.gaussianPoint(neu.getMean(),neu.getStd());
								if(aux >= 0)	ad[j] = aux;
								
							}
						}
						
						datasetHist.addSeries(NeuronVO.convertName(neu.getType(), false), ad, 100);
					}
				}
				
				
				PlotHistogramGraph.generate(datasetHist,
						 stream,
						 title_inputHist, 
						 "ISI [ms]", 
						 "",
						 chartWidth_input,
						 chartHeigth_input,
						 env);
				
			}
			else if(env.equals("stimulation")){
				
				List nerves;
				
				nerves = conf.getNerves();
				
				dataset = new XYSeriesCollection();
				dataset2 = new XYSeriesCollection();
				
				for(int i=0; i < nerves.size(); i++){
					
					NerveVO nerveVO = (NerveVO) nerves.get(i);
					Nerve nerve = new Nerve (nerveVO, conf.getStep());
					ModulatingSignal signal = nerve.getSignal();
					
					
					if(nerveVO.isActive()){
						XYSeries serie = new XYSeries( "Stimulation (" + nerveVO.getCdNerve() + ")");
						XYSeries serie2 = new XYSeries( "Frequency modulation (" + nerveVO.getCdNerve() + ")");
						
						for(iteration = 0; iteration < totSteps; iteration++)
						{
							time = step * iteration;
							nerve.atualize(time);
							
							serie.add(time, nerve.getIntensity());
							serie2.add(time, nerve.getFreq() + signal.value(time));
						}
						
						
						dataset.addSeries(serie);
						dataset2.addSeries(serie2);
					}
					
				}
				
				PlotCombinedGraph.generate( dataset,
											dataset2,
											stream,
											title_stimulation, 
											"Time [ms]", 
											label_stimulation,
											label_stimulationModulating,
											chartWidth_stimulation,
											chartHeigth_stimulation,
											nerves.size());
			}
			
			else if(env.equals("injCurrent")){
				
				List nuclei;
				List currents = null;
				String compartment = "";
				
				nuclei = conf.getNuclei();
				
				dataset = new XYSeriesCollection();
				
				for(int i=0; i < nuclei.size(); i++){
					
					Nucleus nucleus = (Nucleus) nuclei.get(i);
					
					//System.out.println("nucleus: " + nucleus.getCd());
					
					currents = conf.getInjectedCurrents(nucleus.getCd());
					
					
					for(int j=0; j < currents.size(); j++){
						
						Current current = (Current) currents.get(j);
						
						if(current.isActive()){
							
							if(current.getCompartment().equals("S"))compartment = "soma";
							else if(current.getCompartment().equals("D"))compartment = "dendrite";
							
							XYSeries serie = new XYSeries( nucleus.getCd() + " (" + current.getCdNeuronType() + "," + compartment + ")");
							
							for(iteration = 0; iteration < totSteps; iteration++)
							{
								time = step * iteration;
								serie.add(time, current.getCurrent(time));
							}
							
							dataset.addSeries(serie);
						}
						
					}
					
				}
				
				PlotLineGraph.generate( dataset,
						 stream,
						 title_injCurrent, 
						 "Time [ms]", 
						 label_injCurrent,
						 chartWidth_injCurrent,
						 chartHeigth_injCurrent,
						 nuclei.size() * currents.size());
			}
			else if(env.equals("neuronPositions")){
				
				Neuron neurons[][];
				
				JointVO jointVO = conf.getJointVO();
				
				neurons = new Neuron[ jointVO.getNumNuclei() + 1 ][];
				
				NetworkFactory netFactory = new NetworkFactory();

				// Create neurons
				netFactory.createNeurons(conf, neurons);
				
				XYSeries serie1 = new XYSeries( "SOL MNs" );
				XYSeries serie2 = new XYSeries( "MG MNs" );
				XYSeries serie3 = new XYSeries( "LG MNs" );
				XYSeries serie4 = new XYSeries( "TA MNs" );
				XYSeries serie5 = new XYSeries( "Entensors INs" );
				XYSeries serie6 = new XYSeries( "Flexors INs" );
				
				for(int i = 0; i < neurons[1].length; i++)
				{
					Neuron neuron = neurons[1][i];
					
					if(!neuron.getCategory().equals("AF")){
						SpinalNeuron neu = (SpinalNeuron)neuron;
				    	double position = neu.getXPosition();
				    	serie1.add( new Double(position) , new Double(3) );
					}
				}	
				for(int i = 0; i < neurons[2].length; i++)
				{
					Neuron neuron = neurons[2][i];
					
					if(!neuron.getCategory().equals("AF")){
						SpinalNeuron neu = (SpinalNeuron)neuron;
				    	double position = neu.getXPosition();
				    	serie2.add( new Double(position) , new Double(2) );
					}
						
				}	
				for(int i = 0; i < neurons[3].length; i++)
				{
					Neuron neuron = neurons[3][i];
					
					if(!neuron.getCategory().equals("AF")){
						SpinalNeuron neu = (SpinalNeuron)neuron;
				    	double position = neu.getXPosition();
				    	serie3.add( new Double(position) , new Double(2) );
					}
						
				}	
				for(int i = 0; i < neurons[4].length; i++)
				{
					Neuron neuron = neurons[4][i];
					
					if(!neuron.getCategory().equals("AF")){
						SpinalNeuron neu = (SpinalNeuron)neuron;
				    	double position = neu.getXPosition();
				    	serie4.add( new Double(position) , new Double(2) );
					}
						
				}	
				for(int i = 0; i < neurons[5].length; i++)
				{
					Neuron neuron = neurons[5][i];
					
					if(!neuron.getCategory().equals("AF")){
						SpinalNeuron neu = (SpinalNeuron)neuron;
				    	double position = neu.getXPosition();
				    	serie5.add( new Double(position) , new Double(1) );
					}
						
				}	
				for(int i = 0; i < neurons[6].length; i++)
				{
					Neuron neuron = neurons[6][i];
					
					if(!neuron.getCategory().equals("AF")){
						SpinalNeuron neu = (SpinalNeuron)neuron;
				    	double position = neu.getXPosition();
				    	serie5.add( new Double(position) , new Double(1) );
					}
						
				}	
				
				dataset = new XYSeriesCollection();
				dataset.addSeries(serie1);
				dataset.addSeries(serie2);
				dataset.addSeries(serie3);
				dataset.addSeries(serie4);
				dataset.addSeries(serie5);
				dataset.addSeries(serie6);
				
				PlotScatterGraph.generate(dataset,
						 stream,
						 "", 
						 "Caudal-rostral axis [mm]", 
						 "",
						 200,
						 400,
						 env);
				
				
				
			}
			else if(env.equals("biomechInput")){
				
				List nuclei;
				List biomechanicalInputs = null;
				String compartment = "";
				
				nuclei = conf.getNuclei();
				
				dataset = new XYSeriesCollection();
				
				for(int i=0; i < nuclei.size(); i++){
					
					Nucleus nucleus = (Nucleus) nuclei.get(i);
					
					//System.out.println("nucleus: " + nucleus.getCd());
					
					biomechanicalInputs = conf.getBiomechanicalInputs(nucleus.getCd());
					
					
					for(int j=0; j < biomechanicalInputs.size(); j++){
						
						BiomechanicalInput biomechanicalInput = (BiomechanicalInput) biomechanicalInputs.get(j);
						
						if(biomechanicalInput.isActive()){
							
							if(biomechanicalInput.getCompartment().equals("S"))compartment = "soma";
							else if(biomechanicalInput.getCompartment().equals("D"))compartment = "dendrite";
							
							XYSeries serie = new XYSeries( nucleus.getCd() + " (" + biomechanicalInput.getCdNeuronType() + "," + compartment + ")");
							
							for(iteration = 0; iteration < totSteps; iteration++)
							{
								time = step * iteration;
								serie.add(time, biomechanicalInput.getBiomechanicalInput(time));
							}
							
							dataset.addSeries(serie);
						}
						
					}
					
				}
				
				PlotLineGraph.generate( dataset,
						 stream,
						 title_biomechInput, 
						 "Time [ms]", 
						 label_biomechInput,
						 chartWidth_biomechInput,
						 chartHeigth_biomechInput,
						 nuclei.size() * biomechanicalInputs.size());
			}
		
	}	
	
}
