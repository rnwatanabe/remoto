
// VERSÃO CORRENTE


package br.remoto.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import br.remoto.model.Configuration;
import br.remoto.model.Current;
import br.remoto.model.ReMoto;
import br.remoto.model.Simulation;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.ExtrafusalMuscleSuperClass;
import br.remoto.model.Neuron.NeuralTract;
import br.remoto.model.Neuron.Neuron;
import br.remoto.model.Neuron.SensoryFiber;
import br.remoto.model.Neuron.SpinalNeuron;
import br.remoto.model.vo.ResultVO;
import br.remoto.model.vo.Summary;
import br.remoto.util.ButterworthBilinear;
import br.remoto.util.ButterworthImpulseInvariance;
import br.remoto.util.Coordenate;
import br.remoto.util.Point;
import br.remoto.util.Signal;

public class ServletChartGeneratorApplet extends HttpServlet 
{

	private static final long serialVersionUID = 1L;
	
	String received;
	String title;
	
	String XLabel;
	String YLabel;
	String YLabel2 = "vazio";
	
	String task;
	
	ObjectOutputStream outputToApplet;
	ObjectInputStream inputFromApplet;
	
	
	
	protected Configuration conf;
	private ArrayList output;
	private ResultVO result;
	private Coordenate coord;
	private String cdNucleus;
	private double sample;
	private Neuron neurons[];
	private ExtrafusalMuscleSuperClass muscle;
	private MessageFormat mf;
	
	String cdSimulation;
	
	
	XYSeriesCollection dataset = null;
	XYSeriesCollection dataset2 = null;
	//DefaultCategoryDataset datasetHistogram = null;
	HistogramDataset datasetHistogram = null;
	
	
    public ServletChartGeneratorApplet(){}
    
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		try {
						
			inputFromApplet = new ObjectInputStream(request.getInputStream());
			cdSimulation = (String)inputFromApplet.readUTF();
			inputFromApplet.close();

			Simulation sim = (Simulation)ReMoto.simulations.get( cdSimulation );
			conf = sim.getConfiguration();

			if( sim != null )
			{
				
				result = conf.getResult();
				coord = result.getCoordenate();
				cdNucleus = result.getCdNucleus();
				//sample = 1.0 / result.getSample();
				task = result.getTask();
				
				mf = new MessageFormat("{0,number,#.#####}", Locale.US);
				neurons = sim.getNeuronsByNucleus(cdNucleus);

					
				if( coord == null ) 
					coord = new Coordenate();
				
				
				System.out.println("task: " + task);
				//System.out.println("sample: " + sample);
				
				
				// Recreate muscle in order to allow parameters to be modified, before each graph generation
				if( result.getTask().equals( ReMoto.force ) || 
					result.getTask().equals( ReMoto.EMG ) ||
					result.getTask().equals( ReMoto.spikesAndForce ) ||
					result.getTask().equals( ReMoto.spikesAndEMG ) ||
					result.getTask().equals( ReMoto.properties ) )
				{
					//sim.createMuscles(); //rcisi@13jun2009
					//sim.createJoints();
					
					muscle = sim.getMuscle( cdNucleus );
					
					
					if( muscle == null )
					{
						
						outputToApplet = new ObjectOutputStream(response.getOutputStream());

						outputToApplet.writeUTF("noData");		
						outputToApplet.flush();
						
						return;
					}
					
					//boolean emgAttenuation = result.isWithEMGattenuation();
					
					//muscle.setEMG(emgAttenuation);
					//muscle.reset(sample, emgAttenuation);
				}
				
				
				outputToApplet = new ObjectOutputStream(response.getOutputStream());
				outputToApplet.writeUTF(task);	
				outputToApplet.flush();
				
				/*
				if(task.equals("signal")){
					dataset = displaySignal				(response.getOutputStream());
					outputToApplet.writeObject(dataset);
					outputToApplet.flush();
				}
				else*/ if(task.equals("force")){
					dataset = displayForce				(response.getOutputStream());
					outputToApplet.writeObject(dataset);
					outputToApplet.flush();
				}
				else if(task.equals("EMG")){
					dataset = displayEMG				(response.getOutputStream());
					outputToApplet.writeObject(dataset);
					outputToApplet.flush();
				}
				else if(task.equals("spikes")){
					dataset = displaySpikes				(response.getOutputStream());
					outputToApplet.writeObject(dataset);
					outputToApplet.flush();
				}
				else if(task.equals("firingRate")){
					dataset = displayFiring				(response.getOutputStream());
					outputToApplet.writeObject(dataset);
					outputToApplet.flush();
				}
				else if(task.equals("histogram")){
					datasetHistogram = displayHistogram	(response.getOutputStream());
					outputToApplet.writeObject(datasetHistogram);		
					outputToApplet.flush();
				}
				else if(task.equals("spikesAndForce")){
					displaySpikesForce					(response.getOutputStream());
					outputToApplet.writeObject(dataset);
					outputToApplet.flush();
					outputToApplet.writeObject(dataset2);		
					outputToApplet.flush();
				}
				else if(task.equals("spikesAndEMG")){
					displaySpikesEMG					(response.getOutputStream());
					outputToApplet.writeObject(dataset);
					outputToApplet.flush();
					outputToApplet.writeObject(dataset2);		
					outputToApplet.flush();
				}
				
				//System.out.println("c");
				
				outputToApplet.writeUTF(title);		
				outputToApplet.flush();

				outputToApplet.writeUTF(XLabel);		
				outputToApplet.flush();

				outputToApplet.writeUTF(YLabel);		
				outputToApplet.flush();
				
				outputToApplet.writeUTF(YLabel2);		
				outputToApplet.flush();

				outputToApplet.close();
				
				dataset = null;
				dataset2 = null;
				datasetHistogram = null;

				System.gc();
			}
			
		}
		catch (Exception e) {
			System.err.println(e.toString());
		}
    }
    
    
    
    
    private XYSeriesCollection displaySpikes(OutputStream stream) throws IOException
	{
    	String cdNeuron = result.getCdSpikes();
		
		XYSeriesCollection dataset = processSpikes(stream, cdNeuron);
		this.title = "Spikes - " + cdNeuron;
		this.XLabel= "Time [ms]";
		this.YLabel= "Index";
		
		return dataset;
	}
	

	private XYSeriesCollection displayFiring(OutputStream stream) throws IOException
	{
		String cdNeuron = result.getCdFiringRate();
		
		XYSeriesCollection dataset = processFiring(stream, cdNeuron);
		
		this.title = "Firing rate - " + cdNeuron;
		this.XLabel= "Time [ms]";
		this.YLabel= "Firing rate [Hz]";
		
		return dataset;
	}

	
	private XYSeriesCollection displayForce(OutputStream stream) throws IOException
	{
		String cdNeuron = result.getCdForce();
		
		XYSeriesCollection dataset = null;
			
		dataset = processForce(stream, cdNeuron);
		
		this.title = "Force";
		this.XLabel= "Time [ms]";
		this.YLabel= "Force [N]";
		
		return dataset;
	}

	
	private XYSeriesCollection displayEMG(OutputStream stream) throws IOException
	{
		String cdNeuron = result.getCdEMG();
		
		XYSeriesCollection dataset = null;
		
		//dataset = processEMG(stream, cdNeuron);
		
		this.title = "EMG";
		this.XLabel= "Time [ms]";
		this.YLabel= "EMG [mV]";
		
		return dataset;
	}

	
	private void displaySpikesForce(OutputStream stream) throws IOException
	{
		XYSeriesCollection datasetForce = processForce(stream, ReMoto.ALL_MU );
		XYSeriesCollection datasetSpike = processSpikes( stream, ReMoto.ALL_MN_end_plate );
		
		this.title 		= "Force and spikes";
		this.XLabel		= "Time [ms]";
		this.YLabel		= "Motoneuron";
		this.YLabel2	= "Force [N]";
		
		dataset 		= datasetSpike;
		dataset2 		= datasetForce;
	}
	
	
	private void displaySpikesEMG(OutputStream stream) throws IOException
	{
		//XYSeriesCollection datasetEMG = processEMG( stream, ReMoto.ALL_MU );
		XYSeriesCollection datasetSpike = processSpikes( stream, ReMoto.ALL_MN_end_plate );
		
		this.title 		= "EMG and spikes";
		this.XLabel		= "Time [ms]";
		this.YLabel		= "Motoneuron";
		this.YLabel2	= "EMG [mV]";
		
		dataset 		= datasetSpike;
		//dataset2 		= datasetEMG;
	}
	
	/*
	private XYSeriesCollection displaySignal(OutputStream stream) throws IOException
	{
		String cdNeuron = result.getCdSignal();
		String cdConductance = result.getCdConductance();
		
    	SpinalNeuron spinalNeu = null;
		NeuralTract tractNeu = null;
		Current current = null;
		
		// Create chart for signal
		XYSeries serie = new XYSeries( cdNeuron );
		
		XYSeriesCollection dataset = null;
		
		for(int n = 0; neurons != null && n < neurons.length; n++)
	    {
			Neuron neu = (Neuron)neurons[n];
	    	
	        if( neu.isStoredSignals() || neu.getCategory().equals( ReMoto.TR ) )
	        {
	        	String cd = neurons[n].getCd();
	
	        	// cdNeuron.equals("") indicate all neurons
	        	if( !cdNeuron.equals("") &&
	        		!cdNeuron.equals( cd ) &&
		        	!( cdNeuron.equals( ReMoto.CM_TR_ext ) && neu.getType().equals( ReMoto.CM_ext ) ) &&
				    !( cdNeuron.equals( ReMoto.CM_TR_flex ) && neu.getType().equals( ReMoto.CM_flex ) ) &&
				    !( cdNeuron.equals( ReMoto.RBE_TR_ext ) && neu.getType().equals( ReMoto.RBE_ext ) ) &&
				    !( cdNeuron.equals( ReMoto.RBE_TR_flex ) && neu.getType().equals( ReMoto.RBE_flex ) ) &&
				    !( cdNeuron.equals( ReMoto.RBI_TR_ext ) && neu.getType().equals( ReMoto.RBI_ext ) ) &&
				    !( cdNeuron.equals( ReMoto.RBI_TR_flex ) && neu.getType().equals( ReMoto.RBI_flex ) ) )
	        	{
	        		continue;
	        	}
	        	
	        	if( neu.isStoredSignals() )
	        	{
	        		spinalNeu = (SpinalNeuron)neu;
	    			current = spinalNeu.getCurrent();
	        	}
	        	else if( neu.getCategory().equals( ReMoto.TR ) )
	        	{
	        		tractNeu = (NeuralTract)neu;
	        	}
	        	else
	        		continue;

	        	if( result.getTask().equals( ReMoto.signal ) && cdNeuron.equals("") )
	        		stream.write( (cd + "\n").getBytes() );
	        	
    			Signal signal = null;
	    		double t = 0;
	    		double value = 0;
	
	    		// Injected current or modulated mean value
	    		if( ( result.getWhichSignal().equals( ReMoto.inj ) && current != null ) || 
	    			neu.getCategory().equals( ReMoto.TR ) )
	    		{
					int totSteps = (int)(conf.getTFin()/sample);
	    			
    				for(int i = 0; i < totSteps; i++)
    				{
    					t = sample * i;

    					if( result.getWhichSignal().equals( ReMoto.inj ) )
    						value = current.getCurrent(t);
    					else
    						value = tractNeu.meanValue(t);

		        		if( !xLimit(coord, t) || !yLimit(coord, value) )
		        			continue;
		        		
		        		if( result.getOpt().equals( ReMoto.chart ) )
						{
		        			serie.add(t, value);
						}
		        		else
		        		{
							Object[] objT = { new Double(t) };
							Object[] objVm = { new Double(value) };
							stream.write( (mf.format( objT ) + "\t" + mf.format( objVm ) + "\n").getBytes() );
		        		}
    				}
	    		}
	    		// Internal neural signals
	    		else
	    		{
		    		for(int i = 0; i < spinalNeu.getSignalStore().size(); i++ )
		        	{
		    			signal = (Signal)spinalNeu.getSignalStore().get(i);
		
		        		if( result.getWhichSignal().equals( signal.getType() ) ||
		        			result.getWhichSignal().equals( "g" ) && cdConductance.equals( signal.getType() ) )
						{
		        			value = signal.getValue();
		        			
		        			// Conductance signals must be converted to nS
		        			if( result.getWhichSignal().equals( "g" ) )
		        				value = value * 1000;
						}
		    			else
		    			{
		    				continue;
		    			}
		        		
		        		if( !yLimit(coord, value) )
		        			continue;
		    			
		        		t = signal.getTime();
		        		
		        		if( !xLimit(coord, t) )
		        			continue;
		        		
		        		if( result.getOpt().equals( ReMoto.chart ) )
						{
		        			serie.add(t, value);
						}
		        		else
		        		{
							Object[] objT = { new Double(t) };
							Object[] objVm = { new Double(value) };
							stream.write( (mf.format( objT ) + "\t" + mf.format( objVm ) + "\n").getBytes() );
		        		}
		        	}
	    		}
		        
		        // It has already found the target neuron
		        break;
	        }
	    }
    	
		if( result.getOpt().equals( ReMoto.chart ) )
		{
			String title = "";
			String label = "";
			
    		if( cdNeuron.equals( ReMoto.CM_TR_ext ) || 
    	    	cdNeuron.equals( ReMoto.CM_TR_flex ) || 
    	    	cdNeuron.equals( ReMoto.RBE_TR_ext ) || 
    	    	cdNeuron.equals( ReMoto.RBE_TR_flex ) || 
    	    	cdNeuron.equals( ReMoto.RBI_TR_ext ) || 
    	    	cdNeuron.equals( ReMoto.RBI_TR_flex ) )
			{
				title = "ISI - mean value";
    			label = "ISI [ms]";
			}
			else if( result.getWhichSignal().equals( ReMoto.Vs ) || result.getWhichSignal().equals( ReMoto.Vd ) )
			{
				title = "Membrane Potential";
    			label = "Membrane potential [mV]";
			}
			else if( result.getWhichSignal().equals( ReMoto.inj ) )
			{
				title = "Injected current";
    			label = "Current [nA]";
			}
			else
			{
				title = "Conductance Activity - " + cdConductance;
    			label = "Conductance value [nS]";
			}
			
			dataset = new XYSeriesCollection( serie );
			this.title = title;
			this.XLabel= "Time [ms]";
			this.YLabel= label;
		}
		return dataset;
	}	
	*/
	/*
	private DefaultCategoryDataset displayHistogram(OutputStream stream) throws IOException
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		String cdNeuron = result.getCdHistogram();
		boolean moreThanLimit = false; 					
		double binWidth = result.getBinWidth();
		int numberOfBins = result.getNumberOfBins();
	
		//System.out.println("num. bins:" + numberOfBins);
		
		int bar[] = new int[numberOfBins];
	
		for(int i = 0; i < numberOfBins; i++)
		{
			bar[i] = 0;
		}
	    
	    for(int n = 0; neurons != null && n < neurons.length; n++)
	    {
	    	String cd = neurons[n].getCd();
	    	
	    	
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
	    		!cdNeuron.equals( cd ) )
	    		continue;
	    	
	    	if( ( cdNeuron.equals( ReMoto.ALL_MN ) && !neurons[n].getCategory().equals( ReMoto.MN ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_S ) && !neurons[n].getType().equals( ReMoto.S ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_FR ) && !neurons[n].getType().equals( ReMoto.FR ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_FF ) && !neurons[n].getType().equals( ReMoto.FF ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_CM_ext ) && !neurons[n].getType().equals( ReMoto.CM_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_CM_flex ) && !neurons[n].getType().equals( ReMoto.CM_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBE_ext ) && !neurons[n].getType().equals( ReMoto.RBE_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBE_flex ) && !neurons[n].getType().equals( ReMoto.RBE_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBI_ext ) && !neurons[n].getType().equals( ReMoto.RBI_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBI_flex ) && !neurons[n].getType().equals( ReMoto.RBI_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ia ) && !neurons[n].getType().equals( ReMoto.Ia ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ib ) && !neurons[n].getType().equals( ReMoto.Ib ) ) )
	    		continue;
	    	
			List spikes = neurons[n].getTerminalSpikeTrain();
			
			int bin;
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
	    		
	    		if( interval < 0 )
	    			continue;
	    		
	    		bin = (int)(interval / binWidth);
	    		
	    		// Put all spikes greater than limit in the same bin
	    		if( bin >= numberOfBins )
	    		{
	    			bin = numberOfBins - 1;
	    			moreThanLimit = true;
	    		}
	
	    		bar[bin] = bar[bin] + 1;
			}
	    }

	    int finalIndex = numberOfBins;
	    int initialIndex = 0;
	    
	    for(int i = finalIndex - 1; i >= 0; i--)
		{
			if( bar[i] == 0)
				finalIndex--;
			else
				break;
		}
	    
	    for(int i = 0; i < numberOfBins; i++)
		{
			if( bar[i] == 0)
				initialIndex++;
			else
				break;
		}
	    
	    String label = "";
		
	    // Add ISI to the dataset
	    for(int i = initialIndex; i < finalIndex - 1; i++)
		{
			// Make label in the format XX.X
			
			int meanISI = (int)(binWidth * 10 * (i+0.5));
			label = Integer.toString( meanISI );
			int length = label.length();
			label = label.substring(0, length - 1) + "." + label.substring(length - 1);
	
			processHistogram(result, dataset, stream, bar[i], label);
		}
		
	    if(finalIndex > initialIndex)
	    {
			int meanISI = (int)(binWidth * 10 * (finalIndex+0.5));
			label = Integer.toString( meanISI );
			int length = label.length();
			label = label.substring(0, length - 1) + "." + label.substring(length - 1);
			
			// Put all spikes greater than limit in the same bin
			if( moreThanLimit == true )
			{
				label = "> " + label;
			}
			
			processHistogram(result, dataset, stream, bar[finalIndex - 1], label);
	    }
	
	    this.title = "Histogram - " + cdNeuron;
		this.XLabel= "Mean ISI [ms]";
		this.YLabel= "Number of elements in each bin";
	    
		return dataset;
	}
	*/
	
	
	
	private HistogramDataset displayHistogram(OutputStream stream) throws IOException
	{
		HistogramDataset dataset = new HistogramDataset();
		
		String cdNeuron = result.getCdHistogram();	
		int numberOfBins = result.getNumberOfBins();
		
		int numSpikes = 0;
		
	    for(int n = 0; neurons != null && n < neurons.length; n++)
	    {
	    	String cd = neurons[n].getCd();
	    	
	    	
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
	    		!cdNeuron.equals( cd ) )
	    		continue;
	    	
	    	if( ( cdNeuron.equals( ReMoto.ALL_MN ) && !neurons[n].getCategory().equals( ReMoto.MN ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_S ) && !neurons[n].getType().equals( ReMoto.S ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_FR ) && !neurons[n].getType().equals( ReMoto.FR ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_FF ) && !neurons[n].getType().equals( ReMoto.FF ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_CM_ext ) && !neurons[n].getType().equals( ReMoto.CM_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_CM_flex ) && !neurons[n].getType().equals( ReMoto.CM_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBE_ext ) && !neurons[n].getType().equals( ReMoto.RBE_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBE_flex ) && !neurons[n].getType().equals( ReMoto.RBE_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBI_ext ) && !neurons[n].getType().equals( ReMoto.RBI_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBI_flex ) && !neurons[n].getType().equals( ReMoto.RBI_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ia ) && !neurons[n].getType().equals( ReMoto.Ia ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ib ) && !neurons[n].getType().equals( ReMoto.Ib ) ) )
	    		continue;
	    	
			List spikes = neurons[n].getTerminalSpikeTrain();

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
	    		
	    		numSpikes++;
			}
	    }
	    
	    
	    double valuesISI[] = new double[numSpikes];
	    numSpikes = 0;
	    
	    for(int n = 0; neurons != null && n < neurons.length; n++)
	    {
	    	String cd = neurons[n].getCd();
	    	
	    	
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
	    		!cdNeuron.equals( cd ) )
	    		continue;
	    	
	    	if( ( cdNeuron.equals( ReMoto.ALL_MN ) && !neurons[n].getCategory().equals( ReMoto.MN ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_S ) && !neurons[n].getType().equals( ReMoto.S ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_FR ) && !neurons[n].getType().equals( ReMoto.FR ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_FF ) && !neurons[n].getType().equals( ReMoto.FF ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_CM_ext ) && !neurons[n].getType().equals( ReMoto.CM_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_CM_flex ) && !neurons[n].getType().equals( ReMoto.CM_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBE_ext ) && !neurons[n].getType().equals( ReMoto.RBE_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBE_flex ) && !neurons[n].getType().equals( ReMoto.RBE_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBI_ext ) && !neurons[n].getType().equals( ReMoto.RBI_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBI_flex ) && !neurons[n].getType().equals( ReMoto.RBI_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ia ) && !neurons[n].getType().equals( ReMoto.Ia ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ib ) && !neurons[n].getType().equals( ReMoto.Ib ) ) )
	    		continue;
	    	
			List spikes = neurons[n].getTerminalSpikeTrain();

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
	    		
	    		valuesISI[numSpikes++] = interval;
			}
	    }
	    


	    if(valuesISI.length != 0)
	    	dataset.addSeries("H1", valuesISI, numberOfBins);
		
	    this.title = "Histogram - " + cdNeuron;
		this.XLabel= "Mean ISI [ms]";
		this.YLabel= "Number of elements in each bin";
	    
		return dataset;
	}
	
	
	private XYSeriesCollection processForce(OutputStream stream, String cdNeuron) throws IOException
	{
		// Create chart for muscle force
		
		XYSeries serie = new XYSeries("force - " + cdNeuron);
		
		int totSteps = (int)(conf.getTFin()/sample);
		
		
		if( cdNeuron.equals( ReMoto.ALL_MU ) )
		{
			
			double t = 0;
			double force = 0;
			
			for(int i = 0; i < muscle.getForceStore().size(); i++ ){
				
				//System.out.println(muscle.getForceStore().size());
				
				Signal signal = (Signal)muscle.getForceStore().get(i);
				
        		if( signal.getType().equals( ReMoto.force ) ){
        			
        			t = signal.getTime();
        			force = signal.getValue();
        			
        			//System.out.println("time: " + t + "     force: " + force);
        			
        			if( !xLimit(coord, t) ){
    	    			continue;
    	    		}
    	    			
    	    		
    	    		if( !yLimit(coord, force) ){
    	    			continue;
    	    		}
    	    		
    	    		if( result.getOpt().equals( ReMoto.chart ) )
					{
	        			serie.add(t, force);
					}
    				else if( result.getOpt().equals( ReMoto.file ) )
    				{
    					
    					Object[] objT = { new Double(t) };
    					Object[] objF = { new Double(force) };

    					byte[] out = (mf.format( objT ) + "\t" + mf.format( objF ) + "\n").getBytes();

    					stream.write( out );
    				}
    				else
    				{
    					Point point = new Point();
    					
    					point.setX( t );
    					point.setY( force );
    					
    					output.add( point );
    				}	
        		}
        				
        		
        			
				
				
			}
			/*	
			for(int i = 0; i < totSteps; i++)
			{
				
				double t = sample * i;
				double force = muscle.instantMuscleForce(t);
				
	    		if( !xLimit(coord, t) ){
	    			continue;
	    		}
	    			
	    		
	    		if( !yLimit(coord, force) ){
	    			continue;
	    		}
	    		
	    		if( result.getOpt().equals( ReMoto.chart_img ) || result.getOpt().equals( ReMoto.chart_img_new ) )
				{
					serie.add( t, force );
				}
				else if( result.getOpt().equals( ReMoto.file ) )
				{
					
					Object[] objT = { new Double(t) };
					Object[] objF = { new Double(force) };

					byte[] out = (mf.format( objT ) + "\t" + mf.format( objF ) + "\n").getBytes();

					stream.write( out );
				}
				else
				{
					Point point = new Point();
					
					point.setX( t );
					point.setY( force );
					
					output.add( point );
				}
	    		
			}
			*/
		}
		/* // temporarily disabled
		else
		{	
				double t = sample * i;
				double force = muscle.instantMotorUnitForce(cdNeuron, t);
				
				
				
	    		if( !xLimit(coord, t) ){
	    			continue;
	    		}
	    			
				
	    		if( !yLimit(coord, force) ){
	    			continue;
	    		}
	    			
	
	    		//force = yLimit(coord, force);
	    		
	    		if( result.getOpt().equals( ReMoto.chart_img ) || result.getOpt().equals( ReMoto.chart_img_new ) )
				{
					serie.add( t, force );
				}
				else
				{
					Object[] objT = { new Double(t) };
					Object[] objF = { new Double(force) };
					stream.write( (mf.format( objT ) + "\t" + mf.format( objF ) + "\n").getBytes() );
				}
								
			}			
		}
		*/
		
		//System.out.println("RESULTDISPLAY-serie.getMaxX(): " + serie.getMaxX());
		XYSeriesCollection dataset = new XYSeriesCollection( serie );
		
		return dataset;
	}

	/*
	private XYSeriesCollection processEMG(OutputStream stream, String cdNeuron) throws IOException
	{
		// Create chart for muscle EMG
		XYSeries serie = new XYSeries("EMG - " + cdNeuron);

		int totSteps = (int)(conf.getTFin()/sample);
		double[] x = new double[totSteps];
		
		double meanNoise = 0;
		
		if( cdNeuron.equals( ReMoto.ALL_MU ) )
		{
			for(int i = 0; i < totSteps; i++)
			{
				x[i] = muscle.instantMuscleEMG(sample * i);
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
	
	private XYSeriesCollection processSpikes(OutputStream stream, String cdNeuron) throws IOException
	{
		// Create chart for spike train
		XYSeries serie = new XYSeries( "spikes - " + cdNeuron );
		
		for(int n = 0; neurons != null && n < neurons.length; n++)
	    {
	    	String cd = neurons[n].getCd();
	    	
	    	// Plot spikes for the selected neuron 
	    	if( !cdNeuron.equals( ReMoto.ALL_MN_soma ) &&
		    	!cdNeuron.equals( ReMoto.ALL_MN_end_plate ) &&
		    	!cdNeuron.equals( ReMoto.ALL_Ia_stimulus_point ) && 
		    	!cdNeuron.equals( ReMoto.ALL_Ia_terminal ) && 
		    	!cdNeuron.equals( ReMoto.ALL_Ib_stimulus_point ) && 
		    	!cdNeuron.equals( ReMoto.ALL_Ib_terminal ) && 
	    		!cdNeuron.equals( ReMoto.ALL_CM_ext ) && 
	    		!cdNeuron.equals( ReMoto.ALL_CM_flex ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBE_ext ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBE_flex ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBI_ext ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBI_flex ) && 
		    	!cdNeuron.equals( ReMoto.ALL_RC ) && 
		    	!cdNeuron.equals( ReMoto.ALL_IaIn ) && 
		    	!cdNeuron.equals( ReMoto.ALL_IbIn ) && 
	    		!cdNeuron.equals( cd ) )
	    	{
	    		continue;
	    	}
	    	
	    	// If it was selected all MNs or NT, plot only MNs or NTs  
	    	if( ( cdNeuron.equals( ReMoto.ALL_MN_soma ) && !neurons[n].getCategory().equals( ReMoto.MN ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_MN_end_plate ) && !neurons[n].getCategory().equals( ReMoto.MN ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ia_stimulus_point ) && !neurons[n].getType().equals( ReMoto.Ia ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ia_terminal ) && !neurons[n].getType().equals( ReMoto.Ia ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ib_stimulus_point ) && !neurons[n].getType().equals( ReMoto.Ib ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_Ib_terminal ) && !neurons[n].getType().equals( ReMoto.Ib ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_CM_ext ) && !neurons[n].getType().equals( ReMoto.CM_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_CM_flex ) && !neurons[n].getType().equals( ReMoto.CM_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBE_ext ) && !neurons[n].getType().equals( ReMoto.RBE_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBE_flex ) && !neurons[n].getType().equals( ReMoto.RBE_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBI_ext ) && !neurons[n].getType().equals( ReMoto.RBI_ext ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RBI_flex ) && !neurons[n].getType().equals( ReMoto.RBI_flex ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_RC ) && !neurons[n].getType().equals( ReMoto.RC ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_IaIn ) && !neurons[n].getType().equals( ReMoto.IaIn ) ) ||
	    		( cdNeuron.equals( ReMoto.ALL_IbIn ) && !neurons[n].getType().equals( ReMoto.IbIn ) ) )
	    		continue;
	    	

	    	if( result.getTask().equals( ReMoto.spikes ) && cdNeuron.equals("") )
	    		stream.write( (cd + "\n").getBytes() );
	    	
			List spikes = null;

    		if( cdNeuron.equals( ReMoto.ALL_MN_soma ) ||
    			neurons[n].getCategory().equals( ReMoto.IN ) )
    		{
    			spikes = ((SpinalNeuron)neurons[n]).getSomaSpikeTrain();
    		}
    		else if( cdNeuron.equals( ReMoto.ALL_Ia_stimulus_point ) || 
        			 cdNeuron.equals( ReMoto.ALL_Ib_stimulus_point ) )
    		{
    			spikes = ((SensoryFiber)neurons[n]).getAxonSpikeTrain();
    		}
    		else
				spikes = neurons[n].getTerminalSpikeTrain();

			for(int i = 0; i < spikes.size(); i++)
			{
	    		double t = ((Double)(spikes.get(i))).doubleValue();
	    		
	    		int index = neurons[n].getIndex();
	    		
	    		if( !xLimit(coord, t) )
	    			continue;
	    		
	    		if( !yLimit(coord, index) )
	    			continue;
	
	    		if( result.getOpt().equals( ReMoto.chart ) )
				{
			        serie.add( new Double(t), new Integer(index) );
				}
				else if( result.getOpt().equals( ReMoto.file ) )
				{
					Object[] objIndex = { new Double(index) };
					Object[] objTime = { new Double(t) };
					stream.write( ( mf.format( objTime ) + "\t" + mf.format( objIndex ) + "\n").getBytes() );
				}
				else
				{
					Point point = new Point();
					
					point.setX( t );
					point.setY( index );
					
					output.add( point );
				}
			}
	    }
		XYSeriesCollection dataset = new XYSeriesCollection( serie );
		
		return dataset;
	}

	
	private XYSeriesCollection processFiring(OutputStream stream, String cdNeuron) throws IOException
	{
		// Create chart for spike train
		XYSeries serie = new XYSeries( "spikes - " + cdNeuron );
		
		for(int n = 0; neurons != null && n < neurons.length; n++)
	    {
	    	String cd = neurons[n].getCd();
	    	
	    	// Plot spikes for the selected neuron 
	    	if( !cdNeuron.equals( ReMoto.ALL_MN ) && 
	    		!cdNeuron.equals( ReMoto.ALL_CM_ext ) && 
	    		!cdNeuron.equals( ReMoto.ALL_CM_flex ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBE_ext ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBE_flex ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBI_ext ) && 
	    		!cdNeuron.equals( ReMoto.ALL_RBI_flex ) && 
	    		!cdNeuron.equals( cd ) )
	    	{
	    		continue;
	    	}
	    	
	    	// If it was selected all MNs or NT, plot only MNs or NTs  
	    	if( ( cdNeuron.equals( ReMoto.ALL_MN ) && !neurons[n].getCategory().equals( ReMoto.MN ) ) || 
        		( cdNeuron.equals( ReMoto.ALL_CM_ext ) && !neurons[n].getType().equals( ReMoto.CM_ext ) ) ||
        		( cdNeuron.equals( ReMoto.ALL_CM_flex ) && !neurons[n].getType().equals( ReMoto.CM_flex ) ) ||
        		( cdNeuron.equals( ReMoto.ALL_RBE_ext ) && !neurons[n].getType().equals( ReMoto.RBE_ext ) ) ||
        		( cdNeuron.equals( ReMoto.ALL_RBE_flex ) && !neurons[n].getType().equals( ReMoto.RBE_flex ) ) ||
        		( cdNeuron.equals( ReMoto.ALL_RBI_ext ) && !neurons[n].getType().equals( ReMoto.RBI_ext ) ) ||
        		( cdNeuron.equals( ReMoto.ALL_RBI_flex ) && !neurons[n].getType().equals( ReMoto.RBI_flex ) ) )
	    	{
	    		continue;
	    	}
	    	
	    	if( result.getTask().equals( ReMoto.spikes ) && cdNeuron.equals("") )
	    		stream.write( (cd + "\n").getBytes() );
	    	
			List spikes = neurons[n].getTerminalSpikeTrain();
			
			double tLastSpike = 0;
			
			if( spikes.size() > 0 )
				tLastSpike = ((Double)(spikes.get(0))).doubleValue();
			
			double firingRate = 0;

    		for(int i = 1; i < spikes.size(); i++)
			{
	    		double tSpike = ((Double)(spikes.get(i))).doubleValue();
	    		int index = neurons[n].getIndex();
	    		
	    		if( !xLimit(coord, tSpike) )
	    			continue;
	    		
	    		if( !yLimit(coord, index) )
	    			continue;
	    		
	    		firingRate = 1000.0 / (tSpike - tLastSpike);
	    		
	    		if( result.getOpt().equals( ReMoto.chart ) )
				{
			        serie.add( new Double(tSpike), new Double(firingRate) );
				}
				else
				{
					Object[] objTime = { new Double(tSpike) };
					Object[] objRate = { new Double(firingRate) };
					Object[] objIndex = { new Integer(index) };
					
					stream.write( ( mf.format( objIndex ) + "\t" + mf.format( objRate ) + "\t" + mf.format( objTime ) + "\n" ).getBytes() );
				}
	    		
	    		tLastSpike = tSpike;
			}
	    }
		
		XYSeriesCollection dataset = new XYSeriesCollection( serie );
		
		return dataset;
	}

    
    private void processEMG(Coordenate coord, ResultVO result, XYSeries serie, OutputStream stream, double in, double t)
	{
    	try
		{
			if( !xLimit(coord, t) )
				return;
			
			if( !yLimit(coord, in) )
				return;
			
			
    		if( result.getOpt().equals( ReMoto.chart ) )
			{
				serie.add( t, in );
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
    		if( result.getOpt().equals( ReMoto.chart ) )
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