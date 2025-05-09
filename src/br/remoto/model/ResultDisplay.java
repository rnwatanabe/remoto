package br.remoto.model;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.Double;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import br.remoto.model.Joint.Joint;
import br.remoto.model.Musculotendon.MusculotendonSuperClass;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.ExtrafusalMuscleSuperClass;
import br.remoto.model.Musculotendon.Muscle.IntrafusalMuscle.Bag1.Models.Bag1_Mileusnic;
import br.remoto.model.Musculotendon.Tendon.NonInnerveted.NonInnervatedTendon;
import br.remoto.model.Neuron.Interneuron;
import br.remoto.model.Neuron.Motoneuron;
import br.remoto.model.Neuron.NeuralTract;
import br.remoto.model.Neuron.Neuron;
import br.remoto.model.Neuron.SensoryFiber;
import br.remoto.model.Neuron.SpinalNeuron;
import br.remoto.model.Proprioceptors.GolgiTendonOrgan;
import br.remoto.model.Proprioceptors.MuscleSpindle;
import br.remoto.model.vo.ResultVO;
import br.remoto.model.vo.Summary;
import br.remoto.util.Complex;
import br.remoto.util.Conversion;
import br.remoto.util.FFT;
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


public class ResultDisplay
{
	private Configuration conf;
	private ArrayList output;
	private ResultVO result;
	private Coordenate coord;
	private Coordenate coordFFT;
	
	private Neuron abstractNeurons[];
	private SpinalNeuron abstractSpinalNeurons[];
	
	private Motoneuron motoneurons[];
	private SensoryFiber afferents[];
	private NeuralTract descendingTracts[];
	private Interneuron interneurons[];
	
	private Joint joint;
	private MusculotendonSuperClass musculotendon;
	private ExtrafusalMuscleSuperClass muscle;
	private NonInnervatedTendon tendon;
	private GolgiTendonOrgan gto;
	private MuscleSpindle spindle;
	private MessageFormat mf;
	
	private int numOfSubplots;
	private int cdSubplot;
	
	List[] nmMuscles;
	List[] nmSubplots;
	List[] nmCdNeurons;
	List[] nmCdSpecification;
	List[] yLabels;
	List[] legendLabels;
	
	private String titleLabel;
	private String xLabel;
	
	String[] YLabel;
	String XLabel;
	String title;
	String[] graphicTypes;
	
	
	public int chartHeigth;
	public int chartWidth;
	
	private int titleSize;
	private int xLabelFontSize;
	private int yLabelFontSize;
	private int xLabelNumberSize;
	private int yLabelNumberSize;
	private int legendFontSize;
	
	private String graphColor;
	private boolean darker;
	private boolean randomColors;
	private boolean analysis;
	
	private String cdAnalysis;
	private boolean isNumOfPoints;
	private boolean isMean;
	private boolean isVariance;
	private boolean isStd;
	private boolean isCV;
	private boolean isMin;
	private boolean isMax;
	
	private boolean periodicSignal;
	private int numOfPointsFFT;
	
	public int chartHeigth_new 	= 850;
	public int chartWidth_new  	= 850;
	
	int xAxisRange = 0;
	
	
	public ResultDisplay(Configuration conf)
	{
		this.conf = conf;
	}
	
	
	public void generateResults(Simulation sim, OutputStream stream)
    {
		
			System.gc();
			result 				= conf.getResult();
			coord 				= result.getCoordenate();
			coordFFT 			= result.getCoordenateFFT();
			//cdMuscle 			= result.getCdMuscle();
			numOfSubplots 		= conf.getNumOfSubplots();
			
			if(stream != null)
				cdSubplot 			= Integer.parseInt(result.getCdSubplot());
			
			
			nmMuscles 			= conf.getNmMuscles();
			nmSubplots 			= conf.getNmSubplots();
			nmCdNeurons 		= conf.getNmCdNeurons();
			nmCdSpecification 	= conf.getNmCdSpecification();
			yLabels 			= conf.getyLabels();
			legendLabels 		= conf.getLegendLabels();
			titleLabel 			= result.getTitleLabel();
			xLabel 				= result.getxLabel();
			chartHeigth 		= result.getChartHeigth();
			chartWidth 			= result.getChartWidth();
			titleSize 			= result.getTitleSize();
			xLabelFontSize 		= result.getxLabelFontSize();
			yLabelFontSize 		= result.getyLabelFontSize();
			xLabelNumberSize 	= result.getxLabelNumberSize();
			yLabelNumberSize 	= result.getyLabelNumberSize();
			legendFontSize 		= result.getLegendFontSize();
			graphColor 			= result.getGraphColor();
			darker 				= result.isDarker();
			randomColors 		= result.isRandomColors();
			analysis 			= result.isAnalysis();
			
			cdAnalysis 			= result.getCdAnalysis();
			isNumOfPoints 		= result.isNumOfPoints();
			isMean 				= result.isMean();
			isVariance 			= result.isVariance();
			isStd 				= result.isStd();
			isCV 				= result.isCV();
			isMin 				= result.isMin();
			isMax 				= result.isMax();
			
			numOfPointsFFT 		= result.getNumOfPointsFFT();
			periodicSignal 		= result.isPeriodicSignal();
			
			
			mf = new MessageFormat("{0,number,#.#####}", Locale.US);
			
			
			if( coord == null ) 	coord = new Coordenate();
			if( coordFFT == null ) 	coordFFT = new Coordenate();
			
			try
			{
				
				YLabel = new String[numOfSubplots];
				graphicTypes = new String[numOfSubplots];
				
				
				if( result.getTask().equals( ReMoto.properties ) )
				{
					displayProperties(stream);
				}
				else{
					displayGraphic(sim, stream);
				}
					
			}
			catch (IOException e)
			{
	    		System.out.println(e.getMessage());
			}
			
	}
	
	public void organizeNeurons(Simulation sim, String cdMuscle){
		
		abstractNeurons = sim.getNeuronsByNucleus("DT");
		
		descendingTracts = new NeuralTract[abstractNeurons.length];
		System.out.println(abstractNeurons.length);
		
		for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
	    {
			descendingTracts[n]  = (NeuralTract) abstractNeurons[n];
		}
		
		abstractNeurons = sim.getNeuronsByNucleus(cdMuscle);
		
		int numMNs = 0;
		int numAFs = 0;
		int indexMN = 0;
		int indexAF = 0;
		
		
		for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
	    {
			if(abstractNeurons[n].getCategory().equals(ReMoto.MN))
				numMNs++;
			else if(abstractNeurons[n].getCategory().equals(ReMoto.AF))
				numAFs++;
		}
		
		
		if(numMNs != 0)	motoneurons = new Motoneuron[numMNs];
		if(numAFs != 0)	afferents = new SensoryFiber[numAFs];
		
		for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
	    {
			if(abstractNeurons[n].getCategory().equals(ReMoto.MN)){
				motoneurons[indexMN++]  = (Motoneuron) abstractNeurons[n];
			}
					
			if(abstractNeurons[n].getCategory().equals(ReMoto.AF)){
				afferents[indexAF++]  = (SensoryFiber) abstractNeurons[n];
			}
		}
		
		if(	cdMuscle.equals("SOL") || cdMuscle.equals("MG") || cdMuscle.equals("LG")){
			abstractNeurons = sim.getNeuronsByNucleus("IN_ext");
		}
		else if(	cdMuscle.equals("TA")){
			abstractNeurons = sim.getNeuronsByNucleus("IN_flex");
		}
		
		interneurons = new Interneuron[abstractNeurons.length];
		
		for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
	   {
			interneurons[n]  = (Interneuron) abstractNeurons[n];
		}

		abstractNeurons = null;
		System.gc();
	}
	

	public void generateResults(Simulation sim, ArrayList output)
    {
		this.output = output;

		generateResults(sim, (ServletOutputStream)null);
    }
	
	
	private void displayGraphic (Simulation sim, OutputStream stream) throws IOException{
		
		XYSeriesCollection[] datasets = new XYSeriesCollection[numOfSubplots];
		
		/*
		System.out.println("RESULTS DISPLAY");
		
		for(int k = 0; k < numOfSubplots; k++){
			
			System.out.println("nmSubplots[" + k + "].get(0): " + nmSubplots[k].get(0));
			
		}
		*/
		
		joint = sim.getJoint();
		
		for(int k = 0; k < numOfSubplots; k++){
			
			muscle = sim.getMuscle( (String) nmMuscles[k].get(0) );
			
			if(muscle == null){
				
				datasets[k] = new XYSeriesCollection();
				YLabel[k] = "";
				if(graphicTypes[k] == null) graphicTypes[k] = "line";
			}
			else{
				
				muscle = sim.getMuscle( (String) nmMuscles[k].get(0) );
				musculotendon = muscle.getAssociatedMusculotendon();
				tendon = musculotendon.getTendon();
				gto = sim.getGolgiTendonOrgan((String) nmMuscles[k].get(0));
				spindle = sim.getMuscleSpindle((String) nmMuscles[k].get(0));
				
				organizeNeurons(sim, (String) nmMuscles[k].get(0));
				
				boolean emgAttenuation = result.isWithEMGattenuation();
				muscle.reset(conf.getStep(), emgAttenuation);
				
				
				if(nmSubplots[k].size() > 0 && nmCdNeurons[k].size() > 0){
					
					if(nmSubplots[k].get(0).equals("")){
						datasets[k] = new XYSeriesCollection();
						YLabel[k] = "";
						if(graphicTypes[k] == null) graphicTypes[k] = "line";
					}
					else{
						datasets[k] = processGraphic(stream,
								(String) nmSubplots[k].get(0),
								(String) nmCdNeurons[k].get(0),
								(String) nmCdSpecification[k].get(0),
								(String) legendLabels[k].get(0),
								k);
						
						YLabel[k] = (String) yLabels[k].get(0);
						if(graphicTypes[k] == null) graphicTypes[k] = "line";
						
						//System.out.println("datasets[k].getDomainUpperBound(false): " + datasets[k].getDomainUpperBound(false));
						//System.out.println("xAxisRange: " + xAxisRange);
						
						if(datasets[k].getDomainUpperBound(false) > xAxisRange)
							xAxisRange = (int) datasets[k].getDomainUpperBound(false);
						
					}
					
				}
				else{
					datasets[k] = new XYSeriesCollection();
				}
			}
			
		}
		
		if (numOfSubplots > 1){
			titleLabel = "Multiple subplots";
		}
		
		if( result.getOpt().equals( ReMoto.chart_img ))
		{
			
			PlotCombinedGraph.generate(datasets,
					stream,
					titleLabel, xLabel, YLabel,
					chartWidth, chartHeigth,
					numOfSubplots, xAxisRange,
					titleSize, xLabelFontSize, yLabelFontSize, xLabelNumberSize, yLabelNumberSize,
					legendFontSize, graphColor, darker, randomColors, graphicTypes, analysis, false, null);
			
		}	
		else if( result.getOpt().equals( ReMoto.chart_img_new ))
		{
			
			PlotCombinedGraph.generate(datasets,
					stream,
					titleLabel, xLabel, YLabel,
					chartWidth_new, chartHeigth_new,
					numOfSubplots, xAxisRange,
					titleSize, xLabelFontSize, yLabelFontSize, xLabelNumberSize, yLabelNumberSize,
					legendFontSize, graphColor, darker, randomColors, graphicTypes, analysis, false, null);
			
		}	
	}
	
	
	
	
	private void displayHistogram(OutputStream stream) throws IOException
	{
		HistogramDataset dataset = new HistogramDataset();
		
		String cdNeuron = result.getCdHistogram();	
		int numberOfBins = result.getNumberOfBins();
		
		int numSpikes = 0;
		
	    for(int n = 0; motoneurons != null && n < motoneurons.length; n++)
	    {
	    	String cd = motoneurons[n].getCd();
	    	
	    	
	    	// Plot histogram for the selected neuron 
	    	if( !cdNeuron.equals( ReMoto.ALL_MN ) && 
	    		!cdNeuron.equals( ReMoto.ALL_MN_S ) && 
	    		!cdNeuron.equals( ReMoto.ALL_MN_FR ) && 
	    		!cdNeuron.equals( ReMoto.ALL_MN_FF ) && 
	    		!cdNeuron.equals( ReMoto.ALL_CM_ext ) && 
	    		!cdNeuron.equals( ReMoto.ALL_CM_flex ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBE_ext ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBE_flex ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBI_ext ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBI_flex ) && 
	    		!cdNeuron.equals( ReMoto.ALL_Ia ) && 
	    		!cdNeuron.equals( ReMoto.ALL_Ib ) && 
	    		!cdNeuron.equals( ReMoto.ALL_II ) &&
	    		!cdNeuron.equals( cd ) )
	    		continue;
	    	
	    	if( ( cdNeuron.equals( ReMoto.ALL_MN ) && !motoneurons[n].getCategory().equals( ReMoto.MN ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_S ) && !motoneurons[n].getType().equals( ReMoto.S ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_FR ) && !motoneurons[n].getType().equals( ReMoto.FR ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_FF ) && !motoneurons[n].getType().equals( ReMoto.FF ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_CM_ext ) && !motoneurons[n].getType().equals( ReMoto.CM_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_CM_flex ) && !motoneurons[n].getType().equals( ReMoto.CM_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBE_ext ) && !motoneurons[n].getType().equals( ReMoto.RBE_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBE_flex ) && !motoneurons[n].getType().equals( ReMoto.RBE_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBI_ext ) && !motoneurons[n].getType().equals( ReMoto.RBI_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBI_flex ) && !motoneurons[n].getType().equals( ReMoto.RBI_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ia ) && !motoneurons[n].getType().equals( ReMoto.Ia ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ib ) && !motoneurons[n].getType().equals( ReMoto.Ib ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_II ) && !motoneurons[n].getType().equals( ReMoto.II ) ) )
	    		continue;
	    	
			List spikes = motoneurons[n].getTerminalSpikeTrain();

			double lastSpike = 0;
			double interval;
			
			
			if( spikes.size() > 0 )
				lastSpike = ((Double)(spikes.get(0))).doubleValue();
	
			// Colect all ISI
			for(int i = 1; i < spikes.size(); i++)
			{
				double tSpike = ((Double)(spikes.get(i))).doubleValue();
				interval = tSpike - lastSpike;
	    		lastSpike = tSpike;
	    		
	    		if( !xLimit(coord, interval) )
	    			continue;
	    		
	    		numSpikes++;
			}
	    }
	    
	    
	    double valuesISI[] = new double[numSpikes];
	    numSpikes = 0;
	    
	    for(int n = 0; motoneurons != null && n < motoneurons.length; n++)
	    {
	    	String cd = motoneurons[n].getCd();
	    	
	    	
	    	// Plot histogram for the selected neuron 
	    	if( !cdNeuron.equals( ReMoto.ALL_MN ) && 
	    		!cdNeuron.equals( ReMoto.ALL_MN_S ) && 
	    		!cdNeuron.equals( ReMoto.ALL_MN_FR ) && 
	    		!cdNeuron.equals( ReMoto.ALL_MN_FF ) && 
	    		!cdNeuron.equals( ReMoto.ALL_CM_ext ) && 
	    		!cdNeuron.equals( ReMoto.ALL_CM_flex ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBE_ext ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBE_flex ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBI_ext ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBI_flex ) && 
	    		!cdNeuron.equals( ReMoto.ALL_Ia ) && 
	    		!cdNeuron.equals( ReMoto.ALL_Ib ) &&
	    		!cdNeuron.equals( ReMoto.ALL_II ) &&
	    		!cdNeuron.equals( cd ) )
	    		continue;
	    	
	    	if( ( cdNeuron.equals( ReMoto.ALL_MN ) && !motoneurons[n].getCategory().equals( ReMoto.MN ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_S ) && !motoneurons[n].getType().equals( ReMoto.S ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_FR ) && !motoneurons[n].getType().equals( ReMoto.FR ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_FF ) && !motoneurons[n].getType().equals( ReMoto.FF ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_CM_ext ) && !motoneurons[n].getType().equals( ReMoto.CM_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_CM_flex ) && !motoneurons[n].getType().equals( ReMoto.CM_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBE_ext ) && !motoneurons[n].getType().equals( ReMoto.RBE_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBE_flex ) && !motoneurons[n].getType().equals( ReMoto.RBE_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBI_ext ) && !motoneurons[n].getType().equals( ReMoto.RBI_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBI_flex ) && !motoneurons[n].getType().equals( ReMoto.RBI_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ia ) && !motoneurons[n].getType().equals( ReMoto.Ia ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ib ) && !motoneurons[n].getType().equals( ReMoto.Ib ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_II ) && !motoneurons[n].getType().equals( ReMoto.II ) ) )	
	    		continue;
	    	
			List spikes = motoneurons[n].getTerminalSpikeTrain();

			double lastSpike = 0;
			double interval;
			
			
			if( spikes.size() > 0 )
				lastSpike = ((Double)(spikes.get(0))).doubleValue();
	
    		
			// Colect all ISI
			for(int i = 1; i < spikes.size(); i++)
			{
				double tSpike = ((Double)(spikes.get(i))).doubleValue();
				interval = tSpike - lastSpike;
	    		lastSpike = tSpike;
	    		
	    		if( !xLimit(coord, interval) )
	    			continue;
	    		
	    		valuesISI[numSpikes++] = interval;
			}
	    }
	    


	    if(valuesISI.length != 0)
	    	dataset.addSeries("H1", valuesISI, numberOfBins);
		
	    
		if( result.getOpt().equals( ReMoto.chart_img ))
		{
			PlotHistogramGraph.generate(dataset,
					 stream,
					 "Histogram - " + cdNeuron, 
					 "Mean ISI [ms]", 
					 "Number of elements in each bin",
					 chartHeigth,
					 chartWidth);
		}
		else if(result.getOpt().equals( ReMoto.chart_img_new )  )
		{
			PlotHistogramGraph.generate(dataset,
					 stream,
					 "Histogram - " + cdNeuron, 
					 "Mean ISI [ms]", 
					 "Number of elements in each bin",
					 chartHeigth_new,
					 chartWidth_new);
		}
		
	}
	
	
	
	private void displayProperties(OutputStream stream) throws IOException
	{
		String cdNeuron = result.getCdProperties();
		
		if( cdNeuron.equals( ReMoto.ALL_MN ) )
			stream.write( ("Id\t\tRheobase\tSoma thresh.\tInp. resist.\tAxon veloc.\tAxon thresh.\tPosition (rostro-caudal axis)\n\n").getBytes() );
		else if( cdNeuron.equals( ReMoto.ALL_IN ) )
			stream.write( ("Id\t\tRheobase\tThresh.\tInp. resist.\tPosition\n\n").getBytes() );
		else
		{
			displayPropertiesMotorUnits(stream);
			
			return;
		}

		mf = new MessageFormat("{0,number,#.###}", Locale.US);

		for(int n = 0; motoneurons != null && n < motoneurons.length; n++)
	    {
	    	if( ( motoneurons[n].getCategory().equals( ReMoto.MN ) && !cdNeuron.equals( ReMoto.ALL_MN ) ) ||
	    		( motoneurons[n].getCategory().equals( ReMoto.IN ) && !cdNeuron.equals( ReMoto.ALL_IN ) ) ||
	    		( !motoneurons[n].getCategory().equals( ReMoto.MN ) && !motoneurons[n].getCategory().equals( ReMoto.IN ) ) )
	    	{
		    	continue;
	    	}
			
			SpinalNeuron neu = (SpinalNeuron)motoneurons[n];
			
			String cd = neu.getCd();

			ElectroCalculation el = new ElectroCalculation();

			double gCoupling = neu.getGCoupling();
			double gLeakSoma = neu.getGLeakSoma();
			double gLeakDend = neu.getGLeakDend();
			double threshold = neu.getThreshold();
			double position = neu.getXPosition();

			double axonThreshold = 0;
			double latencyStimulusSpinal = 0;
			double stimulusSpinalDistance = 0;
			double axonVelocity = 0; 
			double rn = 0;
			double rheobase = 0;

			if( cdNeuron.equals( ReMoto.ALL_MN ) )
			{
				axonThreshold = ((Motoneuron)neu).getAxonThreshold();
				latencyStimulusSpinal = ((Motoneuron)neu).getLatencyStimulusSpinal();
				stimulusSpinalDistance = ((Motoneuron)neu).getNerve().getStimulusSpinalEntry();
				axonVelocity = (stimulusSpinalDistance/latencyStimulusSpinal) * 1000.0; 
				rn = el.calcRn(gCoupling, gLeakSoma, gLeakDend);
			}
			else
			{
				rn = 1 / gLeakSoma;
			}

			rheobase = threshold / rn;

			if( cd.length() < 8 )
	    		stream.write( (cd + "\t\t").getBytes() );
	    	else
	    		stream.write( (cd + "\t").getBytes() );
	    	
			Object[] objRheobase = { new Double(rheobase) };
			Object[] objThreshold = { new Double(threshold) };
			Object[] objRn = { new Double(rn) };
			Object[] objAxonVelocity = { new Double(axonVelocity) };
			Object[] objAxonThreshold = { new Double(axonThreshold) };
			Object[] objPosition = { new Double(position) };

			if( cdNeuron.equals( ReMoto.ALL_MN ) )
				stream.write( ( mf.format( objRheobase ) + "\t\t" + 
								mf.format( objThreshold ) + "\t\t" + 
								mf.format( objRn ) + "\t\t" + 
								mf.format( objAxonVelocity ) + "\t\t" + 
								mf.format( objAxonThreshold ) + "\t\t" + 
								mf.format( objPosition ) + "\t\t" + 
								"\n").getBytes() );
			else
				stream.write( ( mf.format( objRheobase ) + "\t\t" + 
								mf.format( objThreshold ) + "\t\t" + 
								mf.format( objRn ) + "\t\t" + 
								mf.format( objPosition ) + "\t\t" + 
								"\n").getBytes() );
	    }
	}

	
	private void displayPropertiesMotorUnits(OutputStream stream) throws IOException
	{
		mf = new MessageFormat("{0,number,#.##}", Locale.US);

		stream.write( ("Id\t\tPos. Y\t\tPos. Z\t\tAttenuation [%]\tWidening [%]\n\n").getBytes() );

		MotorUnit motorunits[] = muscle.getMotorunits();
		
		for(int n = 0; motorunits != null && n < motorunits.length; n++)
	    {
			MotorUnit mu = motorunits[n];
			
			String cd = mu.getCd();
			double posY = 0; 
			double posZ = 0;
			double attenuation = 0;
			double timeWidening = 0;
			
			posY = mu.getPosY(); 
			posZ = mu.getPosZ();
			attenuation = (1 - mu.getAttenuationToSkin()) * 100;
			timeWidening = (mu.getTimeWidening() - 1) * 100;
			
			Object[] objPosY = { new Double(posY) };
			Object[] objPosZ = { new Double(posZ) };
			Object[] objAttenuation = { new Double(attenuation) };
			Object[] objTimeWidening = { new Double(timeWidening) };
			
			if( cd.length() < 8 )
	    		stream.write( (cd + "\t\t").getBytes() );
	    	else
	    		stream.write( (cd + "\t").getBytes() );
	    	
    		stream.write( (	mf.format( objPosY ) + "\t\t" + 
							mf.format( objPosZ ) + "\t\t" + 
							mf.format( objAttenuation ) + "\t\t" + 
							mf.format( objTimeWidening ) + "\t\t" + 
							"\n").getBytes() );
	    }
	}

	
	
	
	///////////////////////////////////////////////////////
	
	
	
	private XYSeriesCollection processGraphic(OutputStream stream,
												String graphicType,
												String cdNeuron,
												String cdSpecification,
												String legendLabel,
												int k) throws IOException{
		
		XYSeries serie = null;
		
		ArrayList yAxis = new ArrayList();
		
		double t = 0;
		double y = 0;
		
		
		
		
		if(cdNeuron.equals("Ia") || cdNeuron.equals("II") || cdNeuron.equals("Ib")){
			abstractNeurons = afferents;
		}
		else if(cdNeuron.equals("renshaw") || cdNeuron.equals("IaIN") || cdNeuron.equals("IbIN") || cdNeuron.equals("gII")){
			abstractNeurons = interneurons;
		}
		else if(cdNeuron.equals("CM_ext") || cdNeuron.equals("CM_flex") || cdNeuron.equals("ExcINs_ext") ||
				cdNeuron.equals("ExcINs_flex") || cdNeuron.equals("InhINs_ext") || cdNeuron.equals("InhINs_flex")){
			abstractNeurons = descendingTracts;
		}
		else{
			abstractNeurons = motoneurons;
		}
		
		
		
		
		if(graphicType.equals("meanFiringRate")){
			
			graphicTypes[k] = "line";
			
			serie = new XYSeries(legendLabel);
			
			if(cdNeuron.equals("Ia"))
				yAxis = spindle.getIaFiringRateStore();
			else if(cdNeuron.equals("II"))
				yAxis = spindle.getIIFiringRateStore();
			else if(cdNeuron.equals("Ib"))
				yAxis = gto.getIbFiringRateStore();
			
		}
		else if(graphicType.equals("jointAngle")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = joint.getJointAngleStore();
		}
		else if(graphicType.equals("jointVelocity")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = joint.getJointVelocityStore();
		}
		else if(graphicType.equals("jointCenterMass")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = joint.getJointCenterMassStore();
		}
		else if(graphicType.equals("jointCenterPressure")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = joint.getJointCenterPressureStore();
		}
		else if(graphicType.equals("jointTorque")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = joint.getJointTorqueStore();
		}
		else if(graphicType.equals("jointMuscleTorque")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = joint.getJointMuscleTorqueStore();
		}
		else if(graphicType.equals("jointGravTorque")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = joint.getJointGravTorqueStore();
		}

		else if(graphicType.equals("jointPassiveTorque")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = joint.getJointPassiveTorqueStore();
		}
		else if(graphicType.equals("jointDisturbance")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = joint.getJointDisturbanceStore();
		}
		else if(graphicType.equals("musculotendonLength")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = musculotendon.getLengthStore();
		}
		else if(graphicType.equals("musculotendonMomentArm")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = musculotendon.getMomentArmStore();
		}
		else if(graphicType.equals("jointAngleVsJointTorque")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			
			List angleList = joint.getJointAngleStore();
			List torqueList = joint.getJointTorqueStore();
			
			for(int i = 0; i < angleList.size(); i++){
				
				Signal angle = (Signal) angleList.get(i);
				Signal torque = (Signal) torqueList.get(i);
				
				yAxis.add(new Signal("jointAngleVsJointTorque", new Double(torque.getValue()), new Double(angle.getValue())));
			}
		}
		else if(graphicType.equals("jointAngleVsVelocity")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			
			List angleList = joint.getJointAngleStore();
			List velocityList = joint.getJointVelocityStore();
			
			for(int i = 0; i < angleList.size(); i++){
				
				Signal angle = (Signal) angleList.get(i);
				Signal velocity = (Signal) velocityList.get(i);
				
				yAxis.add(new Signal("jointAngleVsVelocity", new Double(velocity.getValue()), new Double(angle.getValue())));
			}
		}
		else if(graphicType.equals(ReMoto.muscleLength)){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = muscle.getLengthStore();
		}
		else if(graphicType.equals("pennationAngle")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = muscle.getPennationAngleStore();
		}
		else if(graphicType.equals(ReMoto.muscleVelocity)){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = muscle.getVelocityStore();
		}
		else if(graphicType.equals(ReMoto.muscleAcceleration)){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = muscle.getAccelerationStore();
		}
		else if(graphicType.equals(ReMoto.activationNorm)){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = muscle.getMuscleActivationStore();
		}
		else if(graphicType.equals(ReMoto.activationNormSType)){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = muscle.getSTypeMuscleActivationStore();
		}
		else if(graphicType.equals(ReMoto.activationNormFType)){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = muscle.getFTypeMuscleActivationStore();
		}
		else if(graphicType.equals("twitchPeak")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			MotorUnit[] motorunits = muscle.getMotorunits();
			
			for(int n = 0; motorunits != null && n < motorunits.length; n++){
				MotorUnit mu = motorunits[n];
				int index = mu.getIndex();
		    	double twitchPeak = mu.getGpeak();
	    		
		    	yAxis.add (new Signal( "twitchPeak", new Double(twitchPeak),new Integer(index) ));
			}
			
			
			
		}
		else if(graphicType.equals("contractionTime")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			MotorUnit[] motorunits = muscle.getMotorunits();
			
			for(int n = 0; motorunits != null && n < motorunits.length; n++){
				MotorUnit mu = motorunits[n];
				int index = mu.getIndex();
		    	double contractionTime = mu.getTpeak();
	    		
		    	yAxis.add (new Signal( "contractionTime", new Double(contractionTime),new Integer(index) ));
			}
		}
		else if(graphicType.equals("halfRelaxation")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			MotorUnit[] motorunits = muscle.getMotorunits();
			
			//System.out.println("motorunits.length: " + motorunits.length);
			
			for(int n = 0; motorunits != null && n < motorunits.length; n++){
				MotorUnit mu = motorunits[n];
				int index = mu.getIndex();
		    	double halfRelaxation = 0;
		    	halfRelaxation = mu.getTHalfRaikova();
		    	yAxis.add (new Signal( "halfRelaxation", new Double(halfRelaxation),new Integer(index) ));
			}
		}
		else if(graphicType.equals("amplitudeMUAP")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			
			MotorUnit[] motorunits = muscle.getMotorunits();
			
			
			for(int n = 0; motorunits != null && n < motorunits.length; n++){
				MotorUnit mu = motorunits[n];
				int index = mu.getIndex();
		    	double amplitudeMUAP = mu.getAmpEMG();
	    		
		    	yAxis.add (new Signal( "amplitudeMUAP", new Double(amplitudeMUAP),new Integer(index) ));
			}
		}
		else if(graphicType.equals("recruitmentThresholds")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
		    {
				
				Neuron neuron = (Neuron)abstractNeurons[n];
		    	
		    	if( !cdNeuron.equals( "All MNs" ) &&
			    	!cdNeuron.equals( "All MNs S" ) &&
			    	!cdNeuron.equals( "All MNs FR" ) && 
			    	!cdNeuron.equals( "All MNs FF" ) &&
			    	!cdNeuron.equals( "renshaw" ) && 
			    	!cdNeuron.equals( "IaIN" ) && 
			    	!cdNeuron.equals( "IbIN" ) &&
			    	!cdNeuron.equals( "gII" ) &&
			    	!cdNeuron.equals( "Ia" ) && 
			    	!cdNeuron.equals( "Ib" ) && 
			    	!cdNeuron.equals( "II" ) &&
		    		!cdNeuron.equals( neuron.getCd() ) )
		    	{
		    		continue;
		    	}
		    	
		    	int index = neuron.getIndex();
		    	double threshold = 0;
		    	
		    	if(neuron.getCategory().equals("MN") || neuron.getCategory().equals("IN")){
		    		
		    		SpinalNeuron neu = (SpinalNeuron) neuron;
		    		threshold = neu.getThreshold();
		    		
		    	}
		    	else if(neuron.getCategory().equals("AF")){
		    		
		    		SensoryFiber sf = (SensoryFiber) neuron;
		    		threshold = sf.getRecruitmentThreshold();
		    	}
		    	
				
		    	yAxis.add (new Signal( "threshold", new Double(threshold),new Integer(index) ));
				
		    }
		}
		else if(graphicType.equals("axonConductionVelocity")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
		    {
				
				Neuron neuron = (Neuron)abstractNeurons[n];
		    	
		    	if( !cdNeuron.equals( "All MNs" ) &&
			    	!cdNeuron.equals( "All MNs S" ) &&
			    	!cdNeuron.equals( "All MNs FR" ) && 
			    	!cdNeuron.equals( "All MNs FF" ) && 
			    	!cdNeuron.equals( "Ia" ) && 
			    	!cdNeuron.equals( "Ib" ) &&
			    	!cdNeuron.equals( "II" ) &&
		    		!cdNeuron.equals( neuron.getCd() ) )
		    	{
		    		continue;
		    	}
				
		    	int index = neuron.getIndex();
		    	double axonConductionVelocitiy = neuron.getAxonConductionVelocity();
	    		
		    	//System.out.println("axonVelocity: " + axonConductionVelocitiy);
				
		    	
		    	yAxis.add (new Signal( "axonConductionVelocities", new Double(axonConductionVelocitiy),new Integer(index) ));
				
		    }
		}
		else if(graphicType.equals("somaticInputResistance")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
		    {
				
				Neuron neuron = (Neuron)abstractNeurons[n];
		    	
		    	if( !cdNeuron.equals( "All MNs" ) &&
			    	!cdNeuron.equals( "All MNs S" ) &&
			    	!cdNeuron.equals( "All MNs FR" ) && 
			    	!cdNeuron.equals( "All MNs FF" ) &&
			    	!cdNeuron.equals( "renshaw" ) && 
			    	!cdNeuron.equals( "IaIN" ) && 
			    	!cdNeuron.equals( "IbIN" ) &&
			    	!cdNeuron.equals( "gII" ) &&
		    		!cdNeuron.equals( neuron.getCd() ) )
		    	{
		    		continue;
		    	}
		    	
		    	SpinalNeuron neu = (SpinalNeuron) neuron;
				
		    	
		    	ElectroCalculation el = new ElectroCalculation();

				double gCoupling = neu.getGCoupling();
				double gLeakSoma = neu.getGLeakSoma();
				double gLeakDend = neu.getGLeakDend();
				
				double rn = 0;

				if( cdNeuron.equals( "All MNs" ) )
				{
					rn = el.calcRn(gCoupling, gLeakSoma, gLeakDend);
				}
				else
				{
					rn = 1 / gLeakSoma;
				}
				
				
		    	int index = neuron.getIndex();
	    		
		    	yAxis.add (new Signal( "rn", new Double(rn),new Integer(index) ));
				
		    }
		}
		else if(graphicType.equals("thresholdVsInputConductance")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
		    {
				
				Neuron neuron = (Neuron)abstractNeurons[n];
		    	
		    	if( !cdNeuron.equals( "All MNs" ) &&
			    	!cdNeuron.equals( "All MNs S" ) &&
			    	!cdNeuron.equals( "All MNs FR" ) && 
			    	!cdNeuron.equals( "All MNs FF" ) &&
			    	!cdNeuron.equals( "renshaw" ) && 
			    	!cdNeuron.equals( "IaIN" ) && 
			    	!cdNeuron.equals( "IbIN" ) &&
			    	!cdNeuron.equals( "gII" ) &&
		    		!cdNeuron.equals( neuron.getCd() ) )
		    	{
		    		continue;
		    	}
		    	
		    	SpinalNeuron neu = (SpinalNeuron) neuron;
				
		    	ElectroCalculation el = new ElectroCalculation();

				double gCoupling = neu.getGCoupling();
				double gLeakSoma = neu.getGLeakSoma();
				double gLeakDend = neu.getGLeakDend();
				
				double rn = 0;

				if( cdNeuron.equals( "All MNs" ) )
				{
					rn = el.calcRn(gCoupling, gLeakSoma, gLeakDend);
				}
				else
				{
					rn = 1 / gLeakSoma;
				}
				
				double threshold = neu.getThreshold();
	    		
		    	yAxis.add (new Signal( "thresholdVsInputConductance", new Double(threshold), new Double(1/rn) ));
				
		    }
		}
		else if(graphicType.equals("rheobase")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
		    {
				
				Neuron neuron = (Neuron)abstractNeurons[n];
		    	
		    	if( !cdNeuron.equals( "All MNs" ) &&
			    	!cdNeuron.equals( "All MNs S" ) &&
			    	!cdNeuron.equals( "All MNs FR" ) && 
			    	!cdNeuron.equals( "All MNs FF" ) &&
			    	!cdNeuron.equals( "renshaw" ) && 
			    	!cdNeuron.equals( "IaIN" ) && 
			    	!cdNeuron.equals( "IbIN" ) &&
			    	!cdNeuron.equals( "gII" ) &&
		    		!cdNeuron.equals( neuron.getCd() ) )
		    	{
		    		continue;
		    	}
		    	
		    	SpinalNeuron neu = (SpinalNeuron) neuron;
				
		    	
		    	ElectroCalculation el = new ElectroCalculation();

				double gCoupling = neu.getGCoupling();
				double gLeakSoma = neu.getGLeakSoma();
				double gLeakDend = neu.getGLeakDend();
				
				double threshold = neu.getThreshold();
				
				double rn = 0;
				double rheobase = 0;
				

				if( cdNeuron.equals( "All MNs" ) )
				{
					rn = el.calcRn(gCoupling, gLeakSoma, gLeakDend);
				}
				else
				{
					rn = 1 / gLeakSoma;
				}
				
				rheobase = threshold / rn;
				
		    	int index = neuron.getIndex();
	    		
		    	yAxis.add (new Signal( "rheobase", new Double(rheobase),new Integer(index) ));
				
		    }
		}
		else if(graphicType.equals("axonThresholds")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);

			for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
		    {
				
				Neuron neuron = (Neuron)abstractNeurons[n];
		    	
		    	if( !cdNeuron.equals( "All MNs" ) &&
			    	!cdNeuron.equals( "All MNs S" ) &&
			    	!cdNeuron.equals( "All MNs FR" ) && 
			    	!cdNeuron.equals( "All MNs FF" ) && 
			    	!cdNeuron.equals( "Ia" ) && 
			    	!cdNeuron.equals( "Ib" ) &&
			    	!cdNeuron.equals( "II" ) &&
		    		!cdNeuron.equals( neuron.getCd() ) )
		    	{
		    		continue;
		    	}
				
		    	int index = neuron.getIndex();
		    	double axonThreshold = neuron.getAxonThreshold();
	    		
		    	yAxis.add (new Signal( "axonThresholds", new Double(axonThreshold),new Integer(index) ));
				
		    }
		}
		else if(graphicType.equals("neuronPositions")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			
			for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
		    {
				
				Neuron neuron = (Neuron)abstractNeurons[n];
		    	
		    	if( !cdNeuron.equals( "All MNs" ) &&
			    	!cdNeuron.equals( "All MNs S" ) &&
			    	!cdNeuron.equals( "All MNs FR" ) && 
			    	!cdNeuron.equals( "All MNs FF" ) && 
		    		!cdNeuron.equals( neuron.getCd() ) )
		    	{
		    		continue;
		    	}
				
		    	int index = neuron.getIndex();
		    	double position = neuron.getXPosition();
	    		
		    	yAxis.add (new Signal( "neuronPositions", new Double(position),new Integer(index) ));
		    }
		}
		else if(graphicType.equals("MUsPosition")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			
			MotorUnit[] motorunits = muscle.getMotorunits();
			
			for(int n = 0; motorunits != null && n < motorunits.length; n++){
				MotorUnit mu = motorunits[n];
	    		
				double width;
				double depth;
				
				if(cdNeuron.equals("All MUs")){
					width = mu.posZ;
					depth = mu.posY;
					
					yAxis.add (new Signal( "MUsPosition", new Double(depth),new Double(width) ));
					
				}
				else if(cdNeuron.equals("All MUs S")){
					if(mu.getType().equals("S")){
						width = mu.posZ;
						depth = mu.posY;
						
						yAxis.add (new Signal( "MUsPosition", new Double(depth),new Double(width) ));
					}
				}
				else if(cdNeuron.equals("All MUs FR")){
					if(mu.getType().equals("FR")){
						width = mu.posZ;
						depth = mu.posY;
						
						yAxis.add (new Signal( "MUsPosition", new Double(depth),new Double(width) ));
					}
				}
				else if(cdNeuron.equals("All MUs FF")){
					if(mu.getType().equals("FF")){
						width = mu.posZ;
						depth = mu.posY;
						
						yAxis.add (new Signal( "MUsPosition", new Double(depth),new Double(width) ));
					}
				}
				
		    	
			}
			
			
		}
		else if(graphicType.equals(ReMoto.muscleLengthNorm)){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = muscle.getLengthNormStore();
		}
		else if(graphicType.equals(ReMoto.forceParallelElement)){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = muscle.getForceParallelElementStore();
		}
		else if(graphicType.equals(ReMoto.forceViscousElement)){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = muscle.getForceViscousElementStore();
		}
		else if(graphicType.equals(ReMoto.forceActiveSType)){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = muscle.getForceActiveSTypeStore();
		}
		else if(graphicType.equals(ReMoto.forceActiveFType)){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = muscle.getForceActiveFTypeStore();
		}
		else if(graphicType.equals("muscleForce")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			
			if(cdNeuron.equals("All MUs")){
				yAxis = muscle.getForceStore();
			}
			else{
				yAxis = muscle.getMotorUnitForceStore(cdNeuron);
			}
			
		}
		else if(graphicType.equals(ReMoto.tendonLength)){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = tendon.getLengthStore();
		}
		else if(graphicType.equals(ReMoto.tendonForce)){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			yAxis = tendon.getForceStore();
		}
		else if(graphicType.equals("tendonLengthVSTendonForce")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			
			List tendonForceList = tendon.getForceStore();
			List tendonLengthList = tendon.getLengthStore();
			
			//System.out.println("tendonForceList.size(): " + tendonForceList.size());
			//System.out.println("tendonLengthList.size(): " + tendonLengthList.size());
			
			for(int i = 0; i < tendonForceList.size(); i++){
				
				Signal force = (Signal) tendonForceList.get(i);
				Signal length = (Signal) tendonLengthList.get(i);
				
				//System.out.println("length:  " + length.getValue() + "     force:  " + force.getValue());
				
				
				yAxis.add(new Signal("tendonLengthVSTendonForce", new Double(force.getValue()), new Double(length.getValue())));
			}
		}
		else if(graphicType.equals("muscleLengthVSTendonForce")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			
			List tendonForceList = tendon.getForceStore();
			List muscleLengthList = muscle.getLengthStore();
			
			for(int i = 0; i < tendonForceList.size(); i++){
				
				Signal force = (Signal) tendonForceList.get(i);
				Signal length = (Signal) muscleLengthList.get(i);
				
				yAxis.add(new Signal("muscleLengthVSTendonForce", new Double(force.getValue()), new Double(length.getValue())));
			}
		}
		else if(graphicType.equals("MTLengthVSTendonForce")){
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			
			List tendonForceList = tendon.getForceStore();
			List musculotendonLengthList = musculotendon.getLengthStore();
			
			for(int i = 0; i < tendonForceList.size(); i++){
				
				Signal force = (Signal) tendonForceList.get(i);
				Signal length = (Signal) musculotendonLengthList.get(i);
				
				yAxis.add(new Signal("MTLengthVSTendonForce", new Double(force.getValue()), new Double(length.getValue())));
			}
		}
		else if(graphicType.equals("normalizedForce")){
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			
			List force = null;
			
			if(muscle.getMuscleModel().equals("hill")){
				force = tendon.getForceStore();
				
			}
			else{
				force = muscle.getForceStore();
			}
			
			for(int i = 0; i < force.size(); i++){
				
				Signal signal = (Signal) force.get(i);
				
				yAxis.add(new Signal("normalizedForce", signal.getValue() / muscle.getMaximumMuscleForce() * 100, signal.getTime()));
			}
			
		}
		else if(graphicType.equals("somaPotential") ||
				graphicType.equals("dendritePotential") ||
				graphicType.equals("conductance")){
			
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			SpinalNeuron spinalNeu = null;
			Signal signal = null;
    		
			for(int n = 0; motoneurons != null && n < motoneurons.length; n++)
		    {
				Neuron neu = (Neuron)motoneurons[n];
		    	
		        if( neu.isStoredSignals() && neu.getCd().equals(cdNeuron)){
		        	
		        	spinalNeu = (SpinalNeuron)neu;
		    		
		        	for(int i = 0; i < spinalNeu.getSignalStore().size(); i++ )
		        	{
		    			signal = (Signal)spinalNeu.getSignalStore().get(i);
		    			
		    			if(graphicType.equals("somaPotential")){
		    				if( "Vs".equals( signal.getType() ) )
							{
			        			yAxis.add (new Signal( "Vs", signal.getValue(), signal.getTime()));
			        			
							}
		    			}
		    			if(graphicType.equals("dendritePotential")){
		    				if( "Vd".equals( signal.getType() ) )
							{
			        			yAxis.add (new Signal( "Vd", signal.getValue(), signal.getTime()));
			        			
							}
		    			}	
		    			if(graphicType.equals("conductance")){
		    				if( cdSpecification.equals( signal.getType() ) )
							{
			        			// Conductance signals must be converted to nS
			        			
			        			yAxis.add (new Signal( cdSpecification, 1000 * signal.getValue(), signal.getTime()));
			        			
							}
		    			}	
		        	}
		        }
		    }
		        
		    for(int n = 0; interneurons != null && n < interneurons.length; n++)
			    {
					Neuron neu = (Neuron)interneurons[n];
			    	
			        if( neu.isStoredSignals() && neu.getCd().equals(cdNeuron)){
			        	
			        	spinalNeu = (Interneuron)neu;
			    		
			        	for(int i = 0; i < spinalNeu.getSignalStore().size(); i++ )
			        	{
			    			signal = (Signal)spinalNeu.getSignalStore().get(i);
			    			
			    			if(graphicType.equals("somaPotential")){
			    				if( "Vs".equals( signal.getType() ) )
								{
				        			yAxis.add (new Signal( "Vs", signal.getValue(), signal.getTime()));
				        			
								}
			    			}
			    			if(graphicType.equals("conductance")){
			    				if( cdSpecification.equals( signal.getType() ) )
								{
				        			// Conductance signals must be converted to nS
				        			
				        			yAxis.add (new Signal( cdSpecification, 1000 * signal.getValue(), signal.getTime()));
				        			
								}
			    			}	
			        	}
			        	
			        }
			    }
		}
		else if(graphicType.equals("injectedCurrent")){
			
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			SpinalNeuron spinalNeu = null;
			Signal signal = null;
			Current current = null;
    		
			for(int n = 0; motoneurons != null && n < motoneurons.length; n++)
		    {
				Neuron neu = (Neuron)motoneurons[n];
		    	
		        if( neu.isStoredSignals() && neu.getCd().equals(cdNeuron)){
		        	
		        	spinalNeu = (SpinalNeuron)neu;
		        	current = spinalNeu.getCurrent();
		        	
		        	double time;
		        	
		        	if(current!= null)
			        	for(time = 0; time < conf.getTFin(); time += conf.getStep()){
			        		yAxis.add (new Signal( "inj", current.getCurrent(time), time));
			        	}
		        }
		    }
		}
		else if(graphicType.equals("meanISI")){
			
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			
			NeuralTract tractNeu = descendingTracts[0];
	    	
	        if( tractNeu.getType().equals(cdNeuron)){
	        	
	        	double time;
	        	
	        	for(time = 0; time < conf.getTFin(); time += conf.getStep()){
	        		yAxis.add (new Signal( "meanISI", tractNeu.meanValue(time), time));
	        	}
		        	
	        }
			
		}
		else if(graphicType.equals("VsxVdot")){
			
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			SpinalNeuron spinalNeu = null;
			Signal signal = null;
    		
			for(int n = 0; motoneurons != null && n < motoneurons.length; n++)
		    {
				Neuron neu = (Neuron)motoneurons[n];
		    	
		        if( neu.isStoredSignals() && neu.getCd().equals(cdNeuron)){
		        	
		        	//System.out.println("cdNeuron: " + cdNeuron);
		        	//System.out.println("neu.getCd(): " + neu.getCd());
		        	
		        	
		        	spinalNeu = (SpinalNeuron)neu;
		    		
		        	for(int i = 0; i < spinalNeu.getSignalStore().size(); i++ )
		        	{
		    			signal = (Signal)spinalNeu.getSignalStore().get(i);
		    			
		        		if( "VsxVdot".equals( signal.getType() ) )
						{
		        			yAxis.add (new Signal( "VsxVdot", signal.getValue(), signal.getTime()));
		        		}
		        	}
		        }
		    }
		}
		else if(graphicType.equals("spikeTimes")){
			
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			List spikes = null;
			
			System.out.println(cdNeuron);
			
			for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
		    {
				spikes = Collections.emptyList();
				Neuron neuron = (Neuron)abstractNeurons[n];
		    	
				//System.out.println(cdNeuron);
				
		        // Plot spikes for the selected neuron 
		    	if( !cdNeuron.equals( "All MNs" ) &&
			    	!cdNeuron.equals( "All MNs S" ) &&
			    	!cdNeuron.equals( "All MNs FR" ) && 
			    	!cdNeuron.equals( "All MNs FF" ) && 
			    	!cdNeuron.equals( "Ia" ) && 
			    	!cdNeuron.equals( "II" ) && 
		    		!cdNeuron.equals( "Ib" ) && 
		    		!cdNeuron.equals( "CM_ext" ) && 
		    		!cdNeuron.equals( "CM_flex" ) && 
		    		!cdNeuron.equals( "ExcINs_ext" ) && 
		    		!cdNeuron.equals( "ExcINs_flex" ) && 
		    		!cdNeuron.equals( "InhINs_ext" ) && 
		    		!cdNeuron.equals( "InhINs_flex" ) && 
			    	!cdNeuron.equals( "renshaw" ) && 
			    	!cdNeuron.equals( "IaIN" ) && 
			    	!cdNeuron.equals( "IbIN" ) && 
			    	!cdNeuron.equals( "gII" ) &&
		    		!cdNeuron.equals( neuron.getCd() ) )
		    	{
		    		continue;
		    	}
		    	
		    	if( cdNeuron.equals( "CM_ext" )){
		    		if(neuron.getType().equals("CM_ext")){
		    			spikes = ((NeuralTract)neuron).getTerminalSpikeTrain();
		    		}
				}
		    	else if( cdNeuron.equals( "CM_flex" )){
		    		if(neuron.getType().equals("CM_flex")){
		    			spikes = ((NeuralTract)neuron).getTerminalSpikeTrain();
		    		}
	    		}
		    	else if( cdNeuron.equals( "ExcINs_ext" )){
		    		if(neuron.getType().equals("excitatory_MN") ){
		    			spikes = ((NeuralTract)neuron).getTerminalSpikeTrain();
		    		}
		    	}
		    	else if( cdNeuron.equals( "ExcINs_flex" )){
		    		if(neuron.getType().equals("excitatory_RC") ){
		    			spikes = ((NeuralTract)neuron).getTerminalSpikeTrain();
		    		}
		    	}
		    	else if( cdNeuron.equals( "InhINs_ext" )){
		    		if(neuron.getType().equals("inhibitory_MN") ){
		    			spikes = ((NeuralTract)neuron).getTerminalSpikeTrain();
		    		}
		    	}
		    	else if( cdNeuron.equals( "InhINs_flex" )){
		    		if(neuron.getType().equals("inhibitory_MN") ){
		    			spikes = ((NeuralTract)neuron).getTerminalSpikeTrain();
		    		}
		    	}
		    	else if( cdNeuron.equals( "renshaw" ) ){
			   		
			   		if(neuron.getType().equals("RC")){
			   			spikes = ((Interneuron)neuron).getTerminalSpikeTrain();
			   		}
			   		else continue;	
					}
		    	else if( cdNeuron.equals( "gII" ) ){
			   		if(neuron.getType().equals("gII")){
			   			spikes = ((Interneuron)neuron).getTerminalSpikeTrain();			   			
			   		}
			   		else continue;	
					}
			   	else if( cdNeuron.equals( "IaIN" ) ){
			   		if(neuron.getType().equals("IaIn")){
			   			spikes = ((Interneuron)neuron).getTerminalSpikeTrain();
			   		}
			   		else continue;	
					}
			   	else if( cdNeuron.equals( "IbIN" ) ){
			   		if(neuron.getType().equals("IbIn")){
			   			spikes = ((Interneuron)neuron).getTerminalSpikeTrain();
			   		}
			   		else continue;	
					}
		    	else if( cdNeuron.equals( "All MNs" ) ){
		        	if(cdSpecification.equals("atSoma")){
		        		spikes = ((SpinalNeuron)neuron).getSomaSpikeTrain();
		        	}
		        	if(cdSpecification.equals("atTerminal")){
		        		spikes = ((SpinalNeuron)neuron).getTerminalSpikeTrain();
		        	}
				}
		        else if( cdNeuron.equals( "All MNs S" ) ){
		        	if(neuron.getType().equals("S")){
		        		if(cdSpecification.equals("atSoma")){
			        		spikes = ((SpinalNeuron)abstractNeurons[n]).getSomaSpikeTrain();
			        	}
			        	if(cdSpecification.equals("atTerminal")){
			        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
			        	}
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "All MNs FR" ) ){
		        	if(neuron.getType().equals("FR")){
		        		if(cdSpecification.equals("atSoma")){
			        		spikes = ((SpinalNeuron)abstractNeurons[n]).getSomaSpikeTrain();
			        	}
			        	if(cdSpecification.equals("atTerminal")){
			        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
			        	}
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "All MNs FF" ) ){
		        	if(neuron.getType().equals("FF")){
		        		if(cdSpecification.equals("atSoma")){
			        		spikes = ((SpinalNeuron)abstractNeurons[n]).getSomaSpikeTrain();
			        	}
			        	if(cdSpecification.equals("atTerminal")){
			        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
			        	}
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "Ia" ) ){
		        	if(neuron.getType().equals("Ia")){
		        		if(cdSpecification.equals("atStimulusPoint")){
			        		spikes = ((SensoryFiber)abstractNeurons[n]).getAxonSpikeTrain();
			        	}
			        	if(cdSpecification.equals("atTerminal")){
			        		spikes = ((SensoryFiber)abstractNeurons[n]).getTerminalSpikeTrain();
			        	}
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "II" ) ){
		        	if(neuron.getType().equals("II")){
		        		if(cdSpecification.equals("atStimulusPoint")){
			        		spikes = ((SensoryFiber)abstractNeurons[n]).getAxonSpikeTrain();
			        	}
			        	if(cdSpecification.equals("atTerminal")){
			        		spikes = ((SensoryFiber)abstractNeurons[n]).getTerminalSpikeTrain();
			        	}
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "Ib" ) ){
		        	if(neuron.getType().equals("Ib")){
		        		if(cdSpecification.equals("atStimulusPoint")){
			        		spikes = ((SensoryFiber)abstractNeurons[n]).getAxonSpikeTrain();
			        	}
			        	if(cdSpecification.equals("atTerminal")){
			        		spikes = ((SensoryFiber)abstractNeurons[n]).getTerminalSpikeTrain();
			        	}
		        	}
		        	else continue;
				}
				else{
					spikes = abstractNeurons[n].getTerminalSpikeTrain();
					//spikes = Collections.emptyList();
				}
		    		

				for(int i = 0; i < spikes.size(); i++){
			    		double time = ((Double)(spikes.get(i))).doubleValue();
			    		
			    		int index = abstractNeurons[n].getIndex();
			    		System.out.println("Index: " + index);
			    		if( !xLimit(coord, t) )
			    			continue;
			    		
			    		if( !yLimit(coord, index) )
			    			continue;

			    		yAxis.add (new Signal( "spikeTimes", new Integer(index), new Double(time)));
			    		//yAxis.add (new Signal( "spikeTimes", contPass, new Double(time)));
				}
		    }
		}
		else if(graphicType.equals("firingRate")){
			
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			List spikes = null;
			
			
			for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
		    {
				
				Neuron neuron = (Neuron)abstractNeurons[n];
		    	
		    	if( !cdNeuron.equals( "All MNs" ) &&
			    	!cdNeuron.equals( "All MNs S" ) &&
			    	!cdNeuron.equals( "All MNs FR" ) && 
			    	!cdNeuron.equals( "All MNs FF" ) && 
			    	!cdNeuron.equals( "Ia" ) && 
			    	!cdNeuron.equals( "Ib" ) &&
			    	!cdNeuron.equals( "II" ) &&
			    	!cdNeuron.equals( "renshaw" ) && 
			    	!cdNeuron.equals( "IaIN" ) && 
			    	!cdNeuron.equals( "IbIN" ) && 
			    	!cdNeuron.equals( "gII" ) &&
		    		!cdNeuron.equals( neuron.getCd() ) )
		    	{
		    		continue;
		    	}
				
		    	if( cdNeuron.equals( "All MNs" ) ){
		        	spikes = ((SpinalNeuron)neuron).getTerminalSpikeTrain();
				}
		        else if( cdNeuron.equals( "All MNs S" ) ){
		        	if(neuron.getType().equals("S")){
		        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "All MNs FR" ) ){
		        	if(neuron.getType().equals("FR")){
		        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "All MNs FF" ) ){
		        	if(neuron.getType().equals("FF")){
		        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "Ia" ) ){
		        	if(neuron.getType().equals("Ia")){
		        		spikes = ((SensoryFiber)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "II" ) ){
		        	if(neuron.getType().equals("II")){
		        		spikes = ((SensoryFiber)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "Ib" ) ){
		        	if(neuron.getType().equals("Ib")){
		        		spikes = ((SensoryFiber)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "renshaw" ) ){
			   		
			   		if(neuron.getType().equals("RC")){
			   			spikes = ((Interneuron)neuron).getTerminalSpikeTrain();
			   		}
			   		else continue;	
					}
		    	else if( cdNeuron.equals( "gII" ) ){
			   		if(neuron.getType().equals("gII")){
			   			spikes = ((Interneuron)neuron).getTerminalSpikeTrain();			   			
			   		}
			   		else continue;	
					}
			   	else if( cdNeuron.equals( "IaIN" ) ){
			   		if(neuron.getType().equals("IaIn")){
			   			spikes = ((Interneuron)neuron).getTerminalSpikeTrain();
			   		}
			   		else continue;	
					}
			   	else if( cdNeuron.equals( "IbIN" ) ){
			   		if(neuron.getType().equals("IbIn")){
			   			spikes = ((Interneuron)neuron).getTerminalSpikeTrain();
			   		}
			   		else continue;	
					}
				else{
					spikes = abstractNeurons[n].getTerminalSpikeTrain();
				}
				
				double tLastSpike = 0;
				
				if( spikes.size() > 0 )
					tLastSpike = ((Double)(spikes.get(0))).doubleValue();
				
				double firingRate = 0;


				for(int i = 1; i < spikes.size(); i++)
				{
			    		double tSpike = ((Double)(spikes.get(i))).doubleValue();
			    		
			    		int index = motoneurons[n].getIndex();
			    		
			    		if( !xLimit(coord, tSpike) )
			    			continue;
			    		
			    		if( !yLimit(coord, index) )
			    			continue;
			    		
			    		firingRate = 1000.0 / (tSpike - tLastSpike);
			    		
			    		
			    		if(firingRate < 10000)
		    				yAxis.add (new Signal( String.valueOf(abstractNeurons[n].getIndex()), new Double(firingRate), new Double(tSpike)));
			    		
			    		tLastSpike = tSpike;
				}
		    }
		}
		else if(graphicType.equals("ISI")){
			
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			List spikes = null;
			
			for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
		    {
				
				Neuron neuron = (Neuron)abstractNeurons[n];
		    	
		    	if( !cdNeuron.equals( "All MNs" ) &&
			    	!cdNeuron.equals( "All MNs S" ) &&
			    	!cdNeuron.equals( "All MNs FR" ) && 
			    	!cdNeuron.equals( "All MNs FF" ) && 
			    	!cdNeuron.equals( "Ia" ) && 
			    	!cdNeuron.equals( "Ib" ) &&
			    	!cdNeuron.equals( "II" ) &&
			    	!cdNeuron.equals( "renshaw" ) && 
			    	!cdNeuron.equals( "IaIN" ) && 
			    	!cdNeuron.equals( "IbIN" ) && 
			    	!cdNeuron.equals( "gII" ) &&
		    		!cdNeuron.equals( neuron.getCd() ) )
		    	{
		    		continue;
		    	}
				
		    	if( cdNeuron.equals( "All MNs" ) ){
		        	spikes = ((SpinalNeuron)neuron).getTerminalSpikeTrain();
				}
		        else if( cdNeuron.equals( "All MNs S" ) ){
		        	if(neuron.getType().equals("S")){
		        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "All MNs FR" ) ){
		        	if(neuron.getType().equals("FR")){
		        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "All MNs FF" ) ){
		        	if(neuron.getType().equals("FF")){
		        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "Ia" ) ){
		        	if(neuron.getType().equals("Ia")){
		        		spikes = ((SensoryFiber)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "II" ) ){
		        	if(neuron.getType().equals("II")){
		        		spikes = ((SensoryFiber)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "Ib" ) ){
		        	if(neuron.getType().equals("Ib")){
		        		spikes = ((SensoryFiber)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "renshaw" ) ){
			   		
			   		if(neuron.getType().equals("RC")){
			   			spikes = ((Interneuron)neuron).getTerminalSpikeTrain();
			   		}
			   		else continue;	
					}
		    	else if( cdNeuron.equals( "gII" ) ){
			   		if(neuron.getType().equals("gII")){
			   			spikes = ((Interneuron)neuron).getTerminalSpikeTrain();			   			
			   		}
			   		else continue;	
					}
			   	else if( cdNeuron.equals( "IaIN" ) ){
			   		if(neuron.getType().equals("IaIn")){
			   			spikes = ((Interneuron)neuron).getTerminalSpikeTrain();
			   		}
			   		else continue;	
					}
			   	else if( cdNeuron.equals( "IbIN" ) ){
			   		if(neuron.getType().equals("IbIn")){
			   			spikes = ((Interneuron)neuron).getTerminalSpikeTrain();
			   		}
			   		else continue;	
					}
				else{
					spikes = abstractNeurons[n].getTerminalSpikeTrain();
				}
				
				double tLastSpike = 0;
				
				if( spikes.size() > 0 )
					tLastSpike = ((Double)(spikes.get(0))).doubleValue();
				
				double ISI = 0;


				for(int i = 1; i < spikes.size(); i++)
				{
			    		double tSpike = ((Double)(spikes.get(i))).doubleValue();
			    		
			    		int index = motoneurons[n].getIndex();
			    		
			    		if( !xLimit(coord, tSpike) )
			    			continue;
			    		
			    		if( !yLimit(coord, index) )
			    			continue;
			    		
			    		ISI = (tSpike - tLastSpike);
			    		
			    		
			    		yAxis.add (new Signal( "ISI", new Double(ISI), new Double(tSpike)));
			    		
			    		tLastSpike = tSpike;
				}
		    }
		}
		else if(graphicType.equals("stimulusRate")){
			
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			List spikes = null;
			
			MotorUnit[] motorunits = muscle.getMotorunits();
			
			for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
		    {
				MotorUnit mu = motorunits[n];
				double contractionTime = mu.getTpeak();
				
				Neuron neuron = (Neuron)abstractNeurons[n];
		    	
		    	if( !cdNeuron.equals( "All MNs" ) &&
			    	!cdNeuron.equals( "All MNs S" ) &&
			    	!cdNeuron.equals( "All MNs FR" ) && 
			    	!cdNeuron.equals( "All MNs FF" ) && 
		    		!cdNeuron.equals( neuron.getCd() ) )
		    	{
		    		continue;
		    	}
				
		    	if( cdNeuron.equals( "All MNs" ) ){
		        	spikes = ((SpinalNeuron)neuron).getTerminalSpikeTrain();
				}
		        else if( cdNeuron.equals( "All MNs S" ) ){
		        	if(neuron.getType().equals("S")){
		        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "All MNs FR" ) ){
		        	if(neuron.getType().equals("FR")){
		        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "All MNs FF" ) ){
		        	if(neuron.getType().equals("FF")){
		        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
				else{
					spikes = abstractNeurons[n].getTerminalSpikeTrain();
				}
				
				double tLastSpike = 0;
				
				if( spikes.size() > 0 )
					tLastSpike = ((Double)(spikes.get(0))).doubleValue();
				
				double stimulusRate = 0;
				
				for(int i = 1; i < spikes.size(); i++)
				{
			    		double tSpike = ((Double)(spikes.get(i))).doubleValue();
			    		
			    		int index = motoneurons[n].getIndex();
			    		
			    		if( !xLimit(coord, tSpike) )
			    			continue;
			    		
			    		if( !yLimit(coord, index) )
			    			continue;
			    		
			    		stimulusRate = contractionTime/(tSpike - tLastSpike);
			    		
			    		yAxis.add (new Signal( "ISI", new Double(stimulusRate), new Double(tSpike)));
			    		
			    		tLastSpike = tSpike;
				}
		    }
		}
		else if(graphicType.equals("stimulusRateVsForce")){
			
			graphicTypes[k] = "scatter";
			serie = new XYSeries(legendLabel);
			List spikes = null;
			List force = muscle.getForceStore();
			List stimulusRateList = new ArrayList();
			
			MotorUnit[] motorunits = muscle.getMotorunits();
			
			for(int n = 0; abstractNeurons != null && n < abstractNeurons.length; n++)
		    {
				MotorUnit mu = motorunits[n];
				double contractionTime = mu.getTpeak();
				
				Neuron neuron = (Neuron)abstractNeurons[n];
		    	
		    	if( !cdNeuron.equals( "All MNs" ) &&
			    	!cdNeuron.equals( "All MNs S" ) &&
			    	!cdNeuron.equals( "All MNs FR" ) && 
			    	!cdNeuron.equals( "All MNs FF" ) && 
		    		!cdNeuron.equals( neuron.getCd() ) )
		    	{
		    		continue;
		    	}
				
		    	if( cdNeuron.equals( "All MNs" ) ){
		        	spikes = ((SpinalNeuron)neuron).getTerminalSpikeTrain();
				}
		        else if( cdNeuron.equals( "All MNs S" ) ){
		        	if(neuron.getType().equals("S")){
		        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "All MNs FR" ) ){
		        	if(neuron.getType().equals("FR")){
		        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
		        else if( cdNeuron.equals( "All MNs FF" ) ){
		        	if(neuron.getType().equals("FF")){
		        		spikes = ((SpinalNeuron)abstractNeurons[n]).getTerminalSpikeTrain();
		        	}
		        	else continue;
				}
				else{
					spikes = abstractNeurons[n].getTerminalSpikeTrain();
				}
				
				double tLastSpike = 0;
				
				if( spikes.size() > 0 )
					tLastSpike = ((Double)(spikes.get(0))).doubleValue();
				
				double stimulusRate = 0;
				
				for(int i = 1; i < spikes.size(); i++)
				{
			    		double tSpike = ((Double)(spikes.get(i))).doubleValue();
			    		
			    		int index = motoneurons[n].getIndex();
			    		
			    		if( !xLimit(coord, tSpike) )
			    			continue;
			    		
			    		if( !yLimit(coord, index) )
			    			continue;
			    		
			    		stimulusRate = contractionTime/(tSpike - tLastSpike);
			    		
			    		stimulusRateList.add (new Signal( "ISI", new Double(stimulusRate), new Double(tSpike)));
			    		
			    		tLastSpike = tSpike;
				}
		    }
			
			int auxIndex = 0;
			
			for(int i = 0; i < force.size(); i++ ){
				
				Signal signalForce = (Signal)force.get(i);
				
				if(auxIndex < stimulusRateList.size()){
					Signal signalStimulusRate = (Signal)stimulusRateList.get(auxIndex);
					
					//System.out.println("signalForce.getTime(): " + signalForce.getTime() + "   signalStimulusRate.getTime(): " + signalStimulusRate.getTime());
					
					if(signalForce.getTime() > signalStimulusRate.getTime()){
						yAxis.add (new Signal( "stimulusRateVsForce", new Double(signalForce.getValue()), new Double(signalStimulusRate.getValue())));
						auxIndex++;
					}
					
					
				}
			}
			
		}
		else if(graphicType.equals("EMG")){
			
			graphicTypes[k] = "line";
			serie = new XYSeries(legendLabel);
			
			
			int totSteps = (int)(conf.getTFin()/conf.getStep());
			double[] x = new double[totSteps];
			
			double meanNoise = 0;
			
			
			for(int i = 0; i < totSteps; i++)
			{
				
				if(cdNeuron.equals("All MUs")){
					x[i] = muscle.instantMuscleEMG(conf.getStep() * i);
				}
				else{
					x[i] = muscle.instantMotorUnitEMG(cdNeuron, conf.getStep() * i);
				}
				
				meanNoise += Math.abs(x[i]);
			}
			
			
			if( result.isWithEMGnoise() )
				meanNoise /= totSteps;
			else
				meanNoise = 0;
			
			meanNoise *= conf.getMiscellaneous( ReMoto.noiseEMG );
			
			
			ButterworthBilinear butterBilinear = new ButterworthBilinear();
			ButterworthImpulseInvariance butterImpulseInvariance = new ButterworthImpulseInvariance(x, result.getFcLow(), result.getFcHigh(), conf.getStep() * 1e-3);
			
			if( result.getFilter().equals( ReMoto.ThirdThird ) )
			{
				x = butterBilinear.bandPass(x, result.getFcLow(), result.getFcHigh(), conf.getStep() * 1e-3);
		    }
			
			
			for(int i = 0; i < x.length; i++ )
	    	{
				
				if( result.getFilter().equals( ReMoto.ThirdThird ) )
				{
					y = x[i] + meanNoise * Math.random();
				}
				else if( result.getFilter().equals( ReMoto.FirstSecond ) )
				{
					y = butterImpulseInvariance.bandPass( x[i] + meanNoise * Math.random() );
					  	
				}
				else // No filtering
				{
					y = x[i] + meanNoise * Math.random();     	
				}
				
				
				//y = x[i] + meanNoise * Math.random();
				
	    		t = i * conf.getStep();
	    		
	    		if( !xLimit(coord, t) )
					continue;
				
				if( !yLimit(coord, y) )
					continue;
			
				yAxis.add (new Signal( "emg", new Double(y), new Double(t)));
	    		
	    	}  
			
			
		}
		
		
		int numOfPoints = 0;
		int N = 0;
		double sum = 0;
		double sumOfSquaredDifferences = 0;
		double mean = 0;
		double variance = 0;
		double std = 0;
		double min = 0;
		double max = 0;
		
		XYSeries serie1 = null;
		XYSeries serie2 = null;
		XYSeries serie3 = null;
		XYSeries serie4 = null;
		XYSeries serie5 = null;
		XYSeries serie6 = null;
		XYSeries serie7 = null;
		XYSeries serie8 = null;
		XYSeries serie9 = null;
		
		for(int i = 0; i < yAxis.size(); i++ ){
			
			Signal signal = (Signal)yAxis.get(i);
			
			String ind = signal.getType();
			
			
			t = signal.getTime();
			y = signal.getValue();
			
			if( !xLimit(coord, t) ){
    			continue;
    		}
    		if( !yLimit(coord, y) ){
    			continue;
    		}
    		
    		if(analysis){
    			numOfPoints++;
				sum += y;
			}
    		else{
    			if( result.getOpt().equals( ReMoto.chart_img ) || result.getOpt().equals( ReMoto.chart_img_new ) )
    			{
    				serie.add( t, y );
    			}
    			else if( result.getOpt().equals( ReMoto.file ) )
    			{
    				Object[] objT = { new Double(t) };
    				Object[] objF = { new Double(y) };
    				byte[] out = (mf.format( objT ) + "\t" + mf.format( objF ) + "\n").getBytes();
    				stream.write( out );
    			}
    			else
    			{
    				Point point = new Point();
    				point.setIndex(ind);
    				point.setX( t );
    				point.setY( y );
    				output.add( point );
    			}	
    		}
		}
		
		if(cdAnalysis.equals("fft")){
			
			double Fs = 1000 / conf.getStep();
			
			N = FFT.nextPow2(numOfPointsFFT);
			
			Complex[] dataArray = new Complex[N];
			
			for(int i = 0; i < N; i++ ){
				
				if(i < yAxis.size()){
					Signal signal = (Signal)yAxis.get(i);
					dataArray[i] = new Complex(signal.getValue(),0);
				}
				else{
					dataArray[i] = new Complex(0, 0);
				}
			}
			
			Complex[] dataFFT = FFT.fft(dataArray);
			
			double f = 0;
			double amp = 0;
			
			for(int i = 0; i < N/2 + 1; i++ ){
				
				if(i == 0){
					f = 0;
				}
				else{
					f = f + (Fs/2) *((double) 2/N);
				}
				
				if(periodicSignal){
					Complex Y = dataFFT[i].divides(new Complex(yAxis.size(), 0));
					amp = 2 * Y.abs();
				}
				else{
					amp = dataFFT[i].abs();
				}
				
				if( !xLimit(coordFFT, f) ){
	    			continue;
	    		}
	    		if( !yLimit(coordFFT, amp) ){
	    			continue;
	    		}
	    		
				if( result.getOpt().equals( ReMoto.chart_img ) || result.getOpt().equals( ReMoto.chart_img_new ) )
				{
					serie.add( f, amp);
				}
				else if( result.getOpt().equals( ReMoto.file ) )
				{
					Object[] objT = { new Double(f) };
					Object[] objF = { new Double(amp)};
					byte[] out = (mf.format( objT ) + "\t" + mf.format( objF ) + "\n").getBytes();
					stream.write( out );
				}
				
				
			}
			
		}
		
		
		
		if(analysis && cdAnalysis.equals("parameters")){
			
			//System.out.println("isNumOfPoints: " + isNumOfPoints + "  isMean: " + isMean + "  isVariance: " + isVariance + "   isStd: " + isStd + "  isCV: " + isCV);
			
			
			mean = sum/numOfPoints;
			
			for(int j = 0; j < yAxis.size(); j++ ){
				
				Signal signal = (Signal)yAxis.get(j);
				
				t = signal.getTime();
				y = signal.getValue();
				
				if( !xLimit(coord, t) ){
	    			continue;
	    		}
	    		if( !yLimit(coord, y) ){
	    			continue;
	    		}
				
	    		if(y < min)	min = y;
	    		if(y > max) max = y;
	    		
				sumOfSquaredDifferences += Math.pow(y - mean, 2);
			}
			
			variance = sumOfSquaredDifferences / numOfPoints;
			std = Math.sqrt(variance);
			
			
			serie1 = new XYSeries("");
			serie2 = new XYSeries("");
			if(isNumOfPoints) 	serie3 = new XYSeries( "Number of points: " + numOfPoints);
			if(isMean) 			serie4 = new XYSeries( "Mean: " + Conversion.convertNumberOfDecimalAlgarisms(mean, 2));
			if(isVariance)		serie5 = new XYSeries( "Variance: " + Conversion.convertNumberOfDecimalAlgarisms(variance, 2));
			if(isStd) 			serie6 = new XYSeries( "Standard deviation: " + Conversion.convertNumberOfDecimalAlgarisms(std, 5));
			if(isCV) 			serie7 = new XYSeries( "Coeficient of variation: " + Conversion.convertNumberOfDecimalAlgarisms(std/mean, 5));
			if(isMin) 			serie8 = new XYSeries( "Minimum value: " + Conversion.convertNumberOfDecimalAlgarisms(min, 2));
			if(isMax) 			serie9 = new XYSeries( "Maximum value: " + Conversion.convertNumberOfDecimalAlgarisms(max, 2));
			
			for(int j = 0; j < yAxis.size(); j++ ){
				
				Signal signal = (Signal)yAxis.get(j);
				
				t = signal.getTime();
				y = signal.getValue();
				
				if( !xLimit(coord, t) ){
	    			continue;
	    		}
	    		if( !yLimit(coord, y) ){
	    			continue;
	    		}
				
	    		if( result.getOpt().equals( ReMoto.chart_img ) || result.getOpt().equals( ReMoto.chart_img_new ) )
    			{
    				serie.add( t, y );
    			}
	    		
	    		if(isMean) 			serie4.add(t, mean);
	    		if(isStd) 			serie1.add(t, mean - std);
	    		if(isStd) 			serie2.add(t, mean + std);
				
			}
			
			if( result.getOpt().equals( ReMoto.file ) )
			{
				
				Object[] objInitialTime = { new Double(coord.getXini()) };
				Object[] objFinalTime 	= { new Double(coord.getXfin()) };
				Object[] objNumPoints 	= { new Double(numOfPoints) };
				Object[] objMean 		= { new Double(mean) };
				Object[] objVariance 	= { new Double(variance) };
				Object[] objSTD 		= { new Double(std) };
				Object[] objCV 			= { new Double(std/mean) };
				Object[] objMin 		= { new Double(min) };
				Object[] objMax 		= { new Double(max) };
				
				byte[] out = ("Time range" + "\t" + mf.format( objInitialTime )  + "\t" + mf.format( objFinalTime ) + "\n").getBytes();
				stream.write( out );
				
				out = ("Number of points" + "\t" + mf.format( objNumPoints ) + "\n").getBytes();
				stream.write( out );
				
				out = ("Mean" + "\t" + mf.format( objMean ) + "\n").getBytes();
				stream.write( out );
				
				out = ("Variance" + "\t" + mf.format( objVariance ) + "\n").getBytes();
				stream.write( out );
				
				out = ("Standard deviation" + "\t" + mf.format( objSTD ) + "\n").getBytes();
				stream.write( out );
				
				out = ("Coeficient of variation" + "\t" + mf.format( objCV ) + "\n").getBytes();
				stream.write( out );
				
				out = ("Minimum value" + "\t" + mf.format( objMin ) + "\n").getBytes();
				stream.write( out );
				
				out = ("Maximum value" + "\t" + mf.format( objMax ) + "\n").getBytes();
				stream.write( out );
				
			}
			
			
			
		}
		
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(serie);
		if(analysis && cdAnalysis.equals("parameters")){
			dataset.addSeries(serie1);
			dataset.addSeries(serie2);
			if(isNumOfPoints) 	dataset.addSeries(serie3);
			if(isMean) 			dataset.addSeries(serie4);
			if(isVariance)		dataset.addSeries(serie5);
			if(isStd) 			dataset.addSeries(serie6);
			if(isCV) 			dataset.addSeries(serie7);
			if(isMin) 			dataset.addSeries(serie8);
			if(isMax) 			dataset.addSeries(serie9);
		}
		
		serie1 = null;
		serie2 = null;
		serie3 = null;
		serie4 = null;
		serie5 = null;
		serie6 = null;
		serie7 = null;
		serie8 = null;
		serie9 = null;
		System.gc();
		
		
		
		
		return dataset;
	}
	
	
	/*
	private XYSeriesCollection processEMG(OutputStream stream, String cdNeuron) throws IOException
	{
		
		XYSeries serie = new XYSeries("EMG - " + cdNeuron);

		int totSteps = (int)(conf.getTFin()/sample);
		double[] x = new double[totSteps];
		
		double meanNoise = 0;
		
		if( cdNeuron.equals( ReMoto.ALL_MU ) )
		{
			
			
			for(int i = 0; i < totSteps; i++)
			{
				x[i] = muscle.instantMuscleEMG(sample * i);
				//System.out.println("x[i]: " + x[i]);
				meanNoise += Math.abs(x[i]);
			}
		}
		else
		{
	    	for(int i = 0; i < totSteps; i++)
	    	{
				x[i] = muscle.instantMotorUnitEMG(cdNeuron, sample * i);
				meanNoise += Math.abs(x[i]);
	    	}
		}
			
		if( result.isWithEMGnoise() )
			meanNoise /= totSteps;
		else
			meanNoise = 0;
		
		meanNoise *= conf.getMiscellaneous( ReMoto.noiseEMG );
		
		if( result.getFilter().equals( ReMoto.ThirdThird ) )
		{
			ButterworthBilinear butter = new ButterworthBilinear();
			x = butter.bandPass(x, result.getFcLow(), result.getFcHigh(), sample * 1e-3);
	
	    	for(int i = 0; i < x.length; i++ )
	    	{
	    		processEMG(coord, result, serie, stream, x[i] + meanNoise * Math.random(), i * sample);
	    	}        	
		}
		else if( result.getFilter().equals( ReMoto.FirstSecond ) )
		{
			ButterworthImpulseInvariance butter = new ButterworthImpulseInvariance(x, result.getFcLow(), result.getFcHigh(), sample * 1e-3);
	
			for(int i = 0; i < x.length; i++)
	    	{
	    		double out = butter.bandPass( x[i] + meanNoise * Math.random() );
	
	    		processEMG(coord, result, serie, stream, out, i * sample);
	    	}        	
		}
		else // No filtering
		{
	    	for(int i = 0; i < x.length; i++ )
	    	{
	    		processEMG(coord, result, serie, stream, x[i] + meanNoise * Math.random(), i * sample);
	    	}        	
		}
		
		XYSeriesCollection dataset = new XYSeriesCollection( serie );
		
		return dataset;
	}
	*/
	

    
    private void processEMG(Coordenate coord, ResultVO result, List yAxis, OutputStream stream, double in, double t)
	{
		try
		{
			if( !xLimit(coord, t) )
				return;
			
			if( !yLimit(coord, in) )
				return;
		
    		if( result.getOpt().equals( ReMoto.chart_img ) || result.getOpt().equals( ReMoto.chart_img_new ) )
			{
    			yAxis.add (new Signal( "emg", new Double(in), new Double(t)));
			}
			else if( result.getOpt().equals( ReMoto.file ) )
			{
				Object[] objT = { new Double(t) };
				Object[] objE = { new Double(in) };
				byte[] out = (mf.format( objT ) + "\t" + mf.format( objE ) + "\n").getBytes();
				
				stream.write( out );
			}
			else
			{
				Point point = new Point();
				
				point.setX( t );
				point.setY( in );
				
				output.add( point );
			}
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	private void processHistogram(ResultVO result, DefaultCategoryDataset dataset, OutputStream stream, int spikes, String label)
	{
		try
		{
    		if( result.getOpt().equals( ReMoto.chart_img ) || result.getOpt().equals( ReMoto.chart_img_new ) )
			{
				dataset.addValue(spikes, label, "");
			}
			else
			{
				Object[] objS = { new Integer(spikes) };
				stream.write( ( label + "\t" + mf.format( objS ) + "\n").getBytes() );
			}
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}


	public List generateSummary(Simulation sim)
    {
		List ret = new ArrayList();
		Neuron neuronsNucleus[][] = sim.getNeurons();	
		int numSteadyState = 4;

		try
		{
			for(int x = 0; neuronsNucleus != null && x < neuronsNucleus.length; x++)
            {
				Neuron neurons[] = neuronsNucleus[x];	

				for(int n = 0; neurons != null && n < neurons.length; n++)
	            {
	            	String nucleus = neurons[n].getCdNucleus();
	            	String type = neurons[n].getType();
	            	String cd = neurons[n].getCd();
					int index = neurons[n].getIndex();
					double rate1I = 0;
					double rateSS = 0; 
					
					List listSpikes = neurons[n].getTerminalSpikeTrain();
					
					int numSpikes = listSpikes.size();

					if( numSpikes > 1 )
					{
						double tSpike0 = ((Double)(listSpikes.get(0))).doubleValue();
						double tSpike1 = ((Double)(listSpikes.get(1))).doubleValue();

						rate1I = 1000.0 / ( tSpike1 - tSpike0 );
					}
					
					if( numSpikes > numSteadyState )
					{
						double tSpikeSS =   ((Double)(listSpikes.get( numSteadyState - 1 ))).doubleValue();
						double tSpikeLast = ((Double)(listSpikes.get( numSpikes - 1 ))).doubleValue();

						rateSS = 1000.0 / ( (tSpikeLast - tSpikeSS) / (numSpikes - numSteadyState) ); 
					}

					double[] firingRates = new double[numSteadyState]; 

					for(int i = 0; i < firingRates.length; i++)
					{
						if( numSpikes > i+1 )
						{
							firingRates[i] = 1000/((((Double)(listSpikes.get(i+1))).doubleValue() -
							    	  				((Double)(listSpikes.get(i))  ).doubleValue() ) );
						}
						else
						{
							firingRates[i] = 0;				
						}
					}
					
					double gMaxExc = 0; 
					double gMaxInh = 0;
					double threshold = 0;
					double inputResistance = 0;
					int numberOfSpikesExc = 0;
					int numberOfSpikesInh = 0;

					if( neurons[n].getCategory().equals( ReMoto.IN ) || neurons[n].getCategory().equals( ReMoto.MN ) )
					{
						gMaxExc = ((SpinalNeuron)neurons[n]).getDendExcitSynapses().getGMaxTot();
						gMaxInh = ((SpinalNeuron)neurons[n]).getDendInhibSynapses().getGMaxTot();
						numberOfSpikesExc = ((SpinalNeuron)neurons[n]).getDendExcitSynapses().getNumberOfSpikesReceived();
						numberOfSpikesInh = ((SpinalNeuron)neurons[n]).getDendInhibSynapses().getNumberOfSpikesReceived();
						threshold = ((SpinalNeuron)neurons[n]).getThreshold();
						inputResistance = ((SpinalNeuron)neurons[n]).getRn();
					}

					Summary summ = new Summary( nucleus, 
												type, 
												cd, 
												index, 
												numSpikes, 
												rate1I, 
												rateSS, 
												firingRates, 
												gMaxExc, 
												gMaxInh, 
												numberOfSpikesExc, 
												numberOfSpikesInh, 
												threshold, 
												inputResistance);
					
					ret.add( summ );
	            }
            }
		}
		catch( Exception e )
		{
    		System.out.println(e.getMessage());
		}
		
		return ret;
    }

	
	private boolean xLimit(Coordenate coord, double x)
	{
		if( coord.getXini() < 0.1 && coord.getXfin() < 0.1 &&
			coord.getYini() < 0.1 && coord.getYfin() < 0.1	)
			return true;
			
		if( x < coord.getXini() )
			return false;
		
		if( x > coord.getXfin() )
			return false;

		return true;
	}


	private boolean yLimit(Coordenate coord, double y)
	{
		if( coord.getXini() < 0.1 && coord.getXfin() < 0.1 &&
			coord.getYini() < 0.1 && coord.getYfin() < 0.1	)
			return true;
		
		if( y < coord.getYini() )
			return false;
		
		if( y > coord.getYfin() )
			return false;

		return true;
	}

	
}
