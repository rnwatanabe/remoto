package br.remoto.model;

/*
Authors:  Rogerio R. Lima Cisi and Andre F. Kohn

rogeriocisi@gmail.com - andfkohn@leb.usp.br

This program is free software under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

A copy of the GNU General Public License can be obtained at http://www.gnu.org/licenses/.
*/

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.remoto.model.Conductance.MultSynapticConductance;
import br.remoto.model.Joint.Joint;
import br.remoto.model.Joint.Ankle.Models.AnkleIsometricModel;
import br.remoto.model.Musculotendon.MusculotendonSuperClass;
import br.remoto.model.Musculotendon.Muscle.ExtrafusalMuscle.ExtrafusalMuscleSuperClass;
import br.remoto.model.Neuron.Interneuron;
import br.remoto.model.Neuron.Motoneuron;
import br.remoto.model.Neuron.NeuralTract;
import br.remoto.model.Neuron.Neuron;
import br.remoto.model.Neuron.SensoryFiber;
import br.remoto.model.Proprioceptors.GolgiTendonOrgan;
import br.remoto.model.Proprioceptors.MuscleSpindle;
import br.remoto.model.factory.JointFactory;
import br.remoto.model.factory.NeuralTractFactory;
import br.remoto.model.factory.NetworkFactory;
import br.remoto.model.factory.StimulationFactory;
import br.remoto.model.factory.SynapsesFactory;
import br.remoto.model.vo.JointVO;
import br.remoto.model.vo.NeuronVO;
import br.remoto.model.vo.Nucleus;


public class Simulation implements Serializable, Runnable
{
	private static final long serialVersionUID = 1L;

	private String cdSimulation;

	private Configuration conf;
	private Neuron neurons[][];
	private Nerve nerves[];
	//private Muscle muscles[];
	//private ExtrafusalMuscleSuperClass muscles[];
	private MusculotendonSuperClass musculotendons[];
	private Joint joint;
	private GolgiTendonOrgan gtos[];
	private MuscleSpindle spindles[];
	
	private int status;
	private int percentageInt;
	private String start;
	private String finish;
	private Date dateFinish;
	
	private int iteration;
	private int totSteps;
	double time;
	
	int numActiveMuscles = 0;
	
	public final static int SIM_IDLE = 0;
	public final static int SIM_RUNNING = 1;
	public final static int SIM_FINISH = 2;
	public final static int SIM_ERROR = 3;
	public final static int SIM_CANCEL = 4;

	
	public Simulation(Configuration conf, String cdSimulation)
	{
		this.conf = conf;
		this.cdSimulation = cdSimulation;
		this.status = SIM_IDLE;
		
		JointVO jointVO = conf.getJointVO();
		
	    neurons = new Neuron[jointVO.getNumNuclei() + 1][];
    	nerves = new Nerve[ jointVO.getNumNerves() ];
    	musculotendons = new MusculotendonSuperClass[ jointVO.getNumMotorNuclei() ];
    	joint = new Joint(conf);
    	gtos = new GolgiTendonOrgan [ jointVO.getNumMotorNuclei() ];
    	spindles = new MuscleSpindle [ jointVO.getNumMotorNuclei() ];
    	
	}
		
	String stringVariables;
	
	public Simulation(Configuration conf, String cdSimulation, String...variables )
	{
		this.conf = conf;
		this.cdSimulation = cdSimulation;
		this.status = SIM_IDLE;
		
		JointVO jointVO = conf.getJointVO();
		System.out.println(jointVO.getNumNuclei() + 1);
	    neurons = new Neuron[jointVO.getNumNuclei() + 1][];
    	nerves = new Nerve[ jointVO.getNumNerves() ];
    	musculotendons = new MusculotendonSuperClass[ jointVO.getNumMotorNuclei() ];
    	joint = new Joint(conf);
    	gtos = new GolgiTendonOrgan [ jointVO.getNumMotorNuclei() ];
    	spindles = new MuscleSpindle [ jointVO.getNumMotorNuclei() ];
    	
    	
    	for(int i = 0; i < variables.length; i++){
    		
    		if(i == 0){
    			stringVariables = variables[0];
    		}
    		else{
    			stringVariables = stringVariables + "   -   " + variables[i];
    		}
    	}
	}
	
	
	// --------------
	// Create neurons
	public void createNetwork()
	{
		NetworkFactory netFactory = new NetworkFactory();

		// Create neurons
		netFactory.createNeurons(conf, neurons);

		conf.setChangedConfiguration( false );
	}
		
	/*
	// --------------
	// Create muscles
	public void createMuscles()
	{
		JointFactory jointFactory = new JointFactory();
		
		// Create muscles
		jointFactory.createMuscles(conf, neurons, muscles);
	}
	*/
	
	// --------------
	// Create joint
	public void createJoint()
	{
		JointFactory jointFactory = new JointFactory(conf);
		
		// Create muscles
		jointFactory.createJoint(conf, neurons, musculotendons, spindles, gtos, joint);
	
	}
	
	
	
	// ------------------
	// Create stimulation
	public void createStimulation()
	{
	    StimulationFactory stimulationFactory = new StimulationFactory();
	
		// Create nerves
		stimulationFactory.createNerves(conf, neurons, nerves);
		
		// Set injected currents
		stimulationFactory.injectCurrent(conf, neurons);
	}
	
	
	// -------------
	// Create inputs
	public void createInputs()
	{
	    NeuralTractFactory inputFactory = new NeuralTractFactory();
		
		// Create descending tracts and noises
	    inputFactory.createNeuralTracts(conf, neurons);
	}
		
	
	// ---------------
	// Create synapses
	public void createSynapses()
	{
		SynapsesFactory synFactory = new SynapsesFactory();

		// Create synapses
		synFactory.createSynapses(conf, neurons);

		// ??? teste para saber o numero de sinapses de uma simulacao de larga escala
		//System.out.println( "Numero maximo de sinapses: " + SynapsesFactory.numSinapses );
	}

	
	public void run()
	{
		ReMoto.simulations.put(cdSimulation, this);

		status = SIM_RUNNING;
		
		double step = conf.getStep();
		double tFin = conf.getTFin();
		
		if( conf.isStoreSignals() )
		{
			if( tFin > ReMoto.T_MAX )
				tFin = ReMoto.T_MAX;
		}
		else
		{
			if( tFin > ReMoto.T_MAX_NO_SIGNALS )
				tFin = ReMoto.T_MAX_NO_SIGNALS;
		}
		
		conf.setTFin( tFin );

		// For performance reason, it is preferable to create new elements whenever perform a new simulation.
		// Reset elements is  only used when it is necessary to keep the same configuration for repeatable simulations
		if( conf.isChangedConfiguration() == true || conf.isKeepProperties() == false )
		{
			createNetwork();
			createJoint();
			resetMuscles(step);
			createInputs();
			createStimulation();
			createSynapses();
		}
		else
		{
			createStimulation();
			resetElements();
		}
		
		// Begin simulation
		SimpleDateFormat formatter = new SimpleDateFormat("H:mm:ss");
		start = formatter.format(new Date());
		
		totSteps = (int)(tFin/step);
		
		time = 0;
		
		for(iteration = 0; iteration < totSteps; iteration++)
		{
			//System.out.println("total memory: " + Runtime.getRuntime().totalMemory());
			//System.out.println("free memory: " + Runtime.getRuntime().freeMemory());
			
			//conf.setNumOfPointsInTheSignal(iteration + 1);
			
			time = step * iteration;
			
			for(int n = 0; nerves != null && n < nerves.length; n++)
			{
				if( nerves[n] != null && nerves[n].isActive() == true )
					nerves[n].atualize(time);
			}

			for(int x = 0; x < neurons.length; x++)
			{
				if( neurons[x] == null )
					continue;
				
				for(int n = 0; n < neurons[x].length; n++)
				{
					neurons[x][n].atualize(time);
				}
			}
			
			for(int x = 0; x < musculotendons.length; x++)
			{
				if( musculotendons[x] == null )
					continue;
				
				//System.out.println("musculotendons[x].getCdMuscle(): " + musculotendons[x].getCdMuscle());
				
				
				musculotendons[x].atualize(time);
				
			}
			
			joint.atualize(time);
			
			if( iteration%20 == 0 )
			{
				percentageInt = (int) ( (double)iteration/(double)totSteps*100 );
			}
			
		}
		
		dateFinish = new Date();
    	
    	finish = formatter.format(dateFinish);
		
    	
		if(status!= SIM_CANCEL)	status = SIM_FINISH;
		
		
		// Serialize this object in order to store simulation results
		if( conf.isStoreResults() == true )
		{
			FileOutputStream fos = null;
			ObjectOutputStream out = null;
			
			try
			{
				fos = new FileOutputStream( ReMoto.path + "store/" + conf.getId() + ".sim" );
				out = new ObjectOutputStream( fos );
				out.writeObject( this );
				out.close();
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}		
		}

		return;
	}
	
	
	public void finishSimulation()
	{
		// Make iteration equal to the toal amount of steps, in order to finish the simulation
		status = SIM_CANCEL;
		
		SimpleDateFormat formatter = new SimpleDateFormat("H:mm:ss");
		dateFinish = new Date();
    	finish = formatter.format(dateFinish);
    	
		iteration = totSteps;
	}
	

	public Neuron[] getNeuronsByNucleus( String cdNucleus ) 
	{
		Nucleus nuc = conf.getNucleus( cdNucleus );
		
		return neurons[ nuc.getInd() ];
	}


	public List getNeurons(String cdNucleus, String type) 
	{
		List list = new ArrayList();
		String category = "";
		
		// Fill list in this order: MNs, INs, SFs, TRs and Noises
		for(int t = 0; t <= 4; t ++)
		{
			if( t == 0 ) category = ReMoto.MN;
			else if( t == 1 ) category = ReMoto.IN;
			else if( t == 2 ) category = ReMoto.AF;
			else if( t == 3 ) category = ReMoto.TR;
			else if( t == 4 ) category = ReMoto.Noise;
			
			Neuron neuronsNucleus[] = getNeuronsByNucleus(cdNucleus);	
			
			if( neuronsNucleus != null )
			{
				for(int i = 0; i < neuronsNucleus.length; i++)
				{
					Neuron neu = neuronsNucleus[i];
					
					if( neu.getCategory().equals( category ) && 
						( neu.getType().equals( type ) || "".equals( type ) ) )
					{
						list.add( neu );
					}
				}
			}
		}

		return list;
	}


	public List getNeuronsStoredSignals(String cdNucleus, String category) 
	{
		List ret = new ArrayList();
    	List types = conf.getNeuronTypes(cdNucleus, category);
    	
		Neuron neuronsNucleus[] = getNeuronsByNucleus(cdNucleus);	
		
		
    	if( neuronsNucleus != null )
		{
			for(int j = 0; j < types.size(); j++)
			{
				NeuronVO reference = (NeuronVO)types.get(j);
				
				for(int i = 0; i < neuronsNucleus.length; i++)
				{
					Neuron neu = neuronsNucleus[i];
					
					if( !neu.getCategory().equals( category ) || !neu.getType().equals( reference.getType() ) )
						continue;
					
					String cd1 = neu.getCategory() + " " + neu.getType() + " " + reference.getIndexStoreVm1();
					String cd2 = neu.getCategory() + " " + neu.getType() + " " + reference.getIndexStoreVm2();
					
					if( neu.getCd().equals( cd1 ) || neu.getCd().equals( cd2 ) ) 
						ret.add( neu );
				}
			}
		}
		
		return ret;
	}

	public ExtrafusalMuscleSuperClass getMuscle( String cdNucleus ) 
	{
		for(int i = 0; i < musculotendons.length; i++)
		{
			if( musculotendons[i] == null )
				continue;
			
			ExtrafusalMuscleSuperClass m = (ExtrafusalMuscleSuperClass)musculotendons[i].getMuscle();
			
			if(m.getCdMuscle().equals( cdNucleus )){
				return m;
			}
			
		}
		
		return null;
	}
	
	
	public GolgiTendonOrgan getGolgiTendonOrgan( String cdNucleus ) 
	{
		for(int i = 0; i < musculotendons.length; i++)
		{
			GolgiTendonOrgan gto = (GolgiTendonOrgan)musculotendons[i].getGto();
			
			if( gto != null && gto.getCdNucleus().equals( cdNucleus ) )
				return gto;
		}
		
		return null;
	}
	
	public MuscleSpindle getMuscleSpindle( String cdNucleus ) 
	{
		for(int i = 0; i < musculotendons.length; i++)
		{
			MuscleSpindle spindle = (MuscleSpindle)musculotendons[i].getSpindle();
			
			if( spindle != null && spindle.getCdNucleus().equals( cdNucleus ) ){
				return spindle;
			}
				
		}
		return null;
	}
	
	public void resetMuscles(double sample){
		
		for(int i = 0; i < musculotendons.length; i++)
		{
			//System.out.println("musculotendons.length:  " + musculotendons.length );
			
			
			if(musculotendons[i] != null){
				ExtrafusalMuscleSuperClass m = (ExtrafusalMuscleSuperClass)musculotendons[i].getMuscle();
				m.reset(sample, true);
			}
			//ExtrafusalMuscleSuperClass m = (ExtrafusalMuscleSuperClass)musculotendons[i].getMuscle();
			//System.out.println("m.getCdNucleus(): " + m.getCdNucleus());
			//if( m != null)	System.out.println("getCdNucleus:  " + m.getCdNucleus() );
			//m.reset(sample, true);
		}
	}


	private void resetElements() 
	{
		int x = 0;
		int y = 0;
		
		try
		{
			for(x = 0; x < neurons.length; x++)
			{
				for(y = 0; y < neurons[x].length; y++)
				{
					Neuron neu = neurons[x][y];
					
					if( neu.getCategory().equals( ReMoto.MN ) )
						((Motoneuron)neu).reset();
					else if( neu.getCategory().equals( ReMoto.IN ) )
						((Interneuron)neu).reset();
					else if( neu.getCategory().equals( ReMoto.AF ) )
						((SensoryFiber)neu).reset();
					else if( neu.getCategory().equals( ReMoto.TR ) )
						((NeuralTract)neu).reset();
					else
						neu.reset();

				    // Reset post-synapses
					for(int i = 0; i < neu.getTransmittingSynapses().size(); i++)
					{
						((MultSynapticConductance)neu.getTransmittingSynapses().get(i)).reset();
					}
				}
			}
		}
		catch (Exception e) 
		{
			System.out.println( "Error while reseting neurons: " + x + "." + y );
		}
	}
	
	
	public int getPercentageInt() {
		return percentageInt;
	}
	public void setPercentageInt(int percentage) {
		this.percentageInt = percentage;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Neuron[][] getNeurons() {
		return neurons;
	}
	public void setNeurons(Neuron[][] neurons) {
		this.neurons = neurons;
	}
	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public Date getDateFinish() {
		return dateFinish;
	}
	public void setDateFinish(Date dateFinish) {
		this.dateFinish = dateFinish;
	}
	public Configuration getConfiguration() {
		return conf;
	}
	public void setConfiguration(Configuration conf) {
		this.conf = conf;
	}
	public String getCdSimulation() {
		return cdSimulation;
	}
	public void setCdSimulation(String cdSimulation) {
		this.cdSimulation = cdSimulation;
	}
	public Nerve[] getNerves() {
		return nerves;
	}
	public void setNerves(Nerve[] nerves) {
		this.nerves = nerves;
	}
	public double getTime(){
		return time;
	}


	public Joint getJoint() {
		return joint;
	}
}
