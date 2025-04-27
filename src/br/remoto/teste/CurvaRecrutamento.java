package br.remoto.teste;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.xy.XYSeries;

import br.remoto.dao.ConfigurationDAO;
import br.remoto.dao.UserDAO;
import br.remoto.model.Configuration;
import br.remoto.model.Nerve;
import br.remoto.model.ReMoto;
import br.remoto.model.ResultDisplay;
import br.remoto.model.Simulation;
import br.remoto.model.vo.ResultVO;
import br.remoto.model.vo.User;
import br.remoto.util.Point;

// Linha de comando para execucao, a aprtir do diretorio raiz onde estao os .class:
// java -Xms900m -Xmx1000m -cp .;F:\rogerio\workspace\lib\jfreechart-1.0.6.jar;F:\rogerio\workspace\lib\jcommon-1.0.10.jar;F:\rogerio\workspace\lib\hsqldb.jar;C:\java\tomcat\lib\servlet-api.jar br.remoto.teste.CurvaRecrutamento

public class CurvaRecrutamento 
{
	UserDAO userDAO = new UserDAO();
	User user;
	Configuration conf;
	
	
	public CurvaRecrutamento()
	{
		ReMoto.path = "/home/renato/Documents/test/"; 
			
		user = userDAO.loadUser("guest", "guest");
		conf = new Configuration();
	}
		
	
	public static void main(String[] args) 
	{
		CurvaRecrutamento teste = new CurvaRecrutamento();
		
		teste.leBD();
		teste.levantaCurva();
	}
		
	
	public void leBD()
	{
		ConfigurationDAO simDAO = new ConfigurationDAO();
		conf = simDAO.getConfiguration(17);
	}
	
	
	public void levantaCurva()
	{
		ConfigurationDAO simDAO = new ConfigurationDAO();
    	String cdSimulation = "1234";

		conf = simDAO.getConfiguration(17);

		double tFin = 40;

		ResultVO resultVO = new ResultVO();
		
		resultVO.setWithEMGnoise( false );
		resultVO.setWithEMGattenuation( true );
		
		resultVO.setSample( 20 );
		resultVO.setOpt( ReMoto.array );
		
		System.out.println("clear all");
		System.out.println("close all");

		int linha = 1;
		int linhaMN = 1;

		conf.setTFin( tFin );
		conf.setMerge( true );
		
		/*
		conf.setQuantityNeuronType("MG", "S", 250);
    	conf.setQuantityNeuronType("MG", "FR", 125);
    	conf.setQuantityNeuronType("MG", "FF", 125);
    	conf.setQuantityNeuronType("MG", "Ia", 80);
    	conf.setQuantityNeuronType("MG", "Ib", 40);

    	conf.setQuantityNeuronType("LG", "S", 200);
    	conf.setQuantityNeuronType("LG", "FR", 100);
    	conf.setQuantityNeuronType("LG", "FF", 100);
    	conf.setQuantityNeuronType("LG", "Ia", 76);
    	conf.setQuantityNeuronType("LG", "Ib", 38);

    	// Noise
    	conf.setQuantityNeuronType("DT", ReMoto.NoiseExcitatoryMN, 1 );
    	conf.getNeuronType("DT", ReMoto.NoiseExcitatoryMN).setMean( 1 );
    	*/

    	/*
    	conf.setQuantityNeuronType("SOL", "S", 100);
    	conf.setQuantityNeuronType("SOL", "FR", 0);
    	conf.setQuantityNeuronType("SOL", "FF", 0);
    	conf.setQuantityNeuronType("SOL", "Ia", 200);
    	conf.setQuantityNeuronType("SOL", "Ib", 0);

    	conf.setQuantityNeuronType("IN_ext", "RC", 0);
    	conf.setQuantityNeuronType("IN_ext", "IaIn", 0);
    	conf.setQuantityNeuronType("IN_ext", "IbIn", 0);
    	*/

    	/*
    	conf.setQuantityNeuronType("MG", "S", 250);
    	conf.setQuantityNeuronType("MG", "FR", 125);
    	conf.setQuantityNeuronType("MG", "FF", 125);
    	conf.setQuantityNeuronType("MG", "Ia", 80);
    	conf.setQuantityNeuronType("MG", "Ib", 40);

    	conf.setQuantityNeuronType("LG", "S", 200);
    	conf.setQuantityNeuronType("LG", "FR", 100);
    	conf.setQuantityNeuronType("LG", "FF", 100);
    	conf.setQuantityNeuronType("LG", "Ia", 76);
    	conf.setQuantityNeuronType("LG", "Ib", 38);
		 */
		
		/*

    	conf.getNeuronType("SOL", "Ib").setAxonThreshold1( 22 );
    	conf.getNeuronType("MG", "Ib").setAxonThreshold1( 22 );
    	conf.getNeuronType("LG", "Ib").setAxonThreshold1( 22 );

    	conf.getNeuronType("SOL", "Ib").setAxonThreshold2( 13 );
    	conf.getNeuronType("MG", "Ib").setAxonThreshold2( 13 );
    	conf.getNeuronType("LG", "Ib").setAxonThreshold2( 13 );
    	
		conf.setMiscellaneous( ReMoto.latencyExcitatory, 0 );
		conf.setMiscellaneous( ReMoto.latencyInhibitory, 0 );

    	conf.getNeuronType("SOL", "Ib").setAxonThreshold1( 18 );
    	conf.getNeuronType("MG", "Ib").setAxonThreshold1( 18 );
    	conf.getNeuronType("LG", "Ib").setAxonThreshold1( 18 );

    	conf.getNeuronType("SOL", "Ib").setAxonThreshold2( 13 );
    	conf.getNeuronType("MG", "Ib").setAxonThreshold2( 13 );
    	conf.getNeuronType("LG", "Ib").setAxonThreshold2( 13 );

    	conf.getNeuronType("SOL", "S").setAxonThreshold1( 10 );
    	conf.getNeuronType("SOL", "S").setAxonThreshold2( 10 );
    	conf.getNeuronType("SOL", "FR").setAxonThreshold1( 10 );
    	conf.getNeuronType("SOL", "FR").setAxonThreshold2( 10 );
    	conf.getNeuronType("SOL", "FF").setAxonThreshold1( 10 );
    	conf.getNeuronType("SOL", "FF").setAxonThreshold2( 10 );
    	*/

    	/*
    	// Set INs and Ibs
    	//conf.setQuantityNeuronType("SOL", "RC", 0);
    	//conf.setQuantityNeuronType("SOL", "Ib (SOL)", 0);
    	//conf.setQuantityNeuronType("SOL", "IbIn", 40);
    	//conf.setQuantityNeuronType("SOL", "RC", 100);
    	//conf.setQuantityNeuronType("SOL", "Ib (SOL)", 200);

    	List noises = conf.getNeuronTypes("DT", ReMoto.Noise);
    	
    	for(int j = 0; j < noises.size(); j++)
    	{
    		NeuronVO noise = (NeuronVO)noises.get(j);
    		
    		if( noise.getType().equals( ReMoto.NoiseExcitatoryMN ) )
    		{
	    		noise.setMean( 1 );
	    		noise.setActive( true );
	    		noise.setQuantity( 1 );
    		}
    	}
    	*/
    	
    	List listNerves = new ArrayList();

    	Nerve tibial = new Nerve( conf.getNerve( ReMoto.PTN ), conf.getStep() ); 
    	
    	tibial.setCdNerve( ReMoto.PTN );
    	tibial.setTini( 0 );
    	tibial.setTfin( tFin );
    	tibial.setFreq( 1.0 );
    	tibial.setActive( true );

		listNerves.add( tibial );
    	
		/*
    	Nerve cpn = new Nerve( conf.getNerve( ReMoto.CPN ), conf.getStep() ); 
    	
    	cpn.setCdNerve( ReMoto.CPN );
    	cpn.setTini( 0 );
    	cpn.setTfin( tFin );
    	cpn.setAmp( i );
    	cpn.setFreq( 1.0 );
    	cpn.setActive( true );

		listNerves.add( cpn );
    	*/
		
    	conf.setNerves( listNerves );
    	
    	Simulation sim = new Simulation( conf, cdSimulation );
    	
		sim.createNetwork();
		sim.createInputs();
		sim.createStimulation();
		sim.createSynapses();

    	for(double i = 10.0; i <= 20.0; i = i + 0.5)
		{
    		List nerves = conf.getNerves();
    		
    		for(int n = 0; n < nerves.size(); n++)
    		{
    			Nerve nerve = (Nerve)nerves.get(n);
    			
    			if( nerve == null || nerve.isActive() == false )
    				continue;

    			nerve.setAmp( i );
    		}
    		
    		sim.run();
    		
    		conf.setChangedConfiguration( false );
    		conf.setKeepProperties( true );
    		
			ArrayList outputEMG = new ArrayList();
			ArrayList outputSpikes = new ArrayList();

			resultVO.setTask( ReMoto.EMG );
			resultVO.setCdEMG( ReMoto.ALL_MU );
			resultVO.setCdNucleus( "SOL" );
			
			conf.setResult( resultVO );
			
			ResultDisplay results = new ResultDisplay(conf);
			
			results.generateResults(sim, outputEMG);

			double y = 0;
			double t = 0;
			double yAnt1 = 0;
			double yAnt2 = 0;
			double yAnt3 = 0;
			
			boolean direction = false;
			
			double hMax = 0;
			double hMin = 0;
			double mMax = 0;
			double mMin = 0;
			
			XYSeries emg = new XYSeries("EMG");

			for(int j = 0; j < outputEMG.size(); j++)
			{
				Point point = (Point)outputEMG.get(j);
				
				yAnt3 = yAnt2;
				yAnt2 = yAnt1;
				yAnt1 = y;
				t = point.getX();
				y = point.getY();
				
				// To be plotted
				emg.add( t, Double.valueOf( y ) );
				
				if( y > 0.01 || y < -0.01 )
				{
					if( direction == false && y > yAnt1 && yAnt1 > yAnt2 && yAnt2 > yAnt3 )
					{
						direction = true;
						
						if( t < 25 )
						{
							if( yAnt3 < mMin )
								mMin = yAnt3;
						}
						else
						{
							if( yAnt3 < hMin )
								hMin = yAnt3;
						}
					}
					else if( direction == true && y < yAnt1 && yAnt1 < yAnt2 && yAnt2 < yAnt3 )
					{
						direction = false;
						
						if( t < 25 )
						{
							if( yAnt3 > mMax )
								mMax = yAnt3;
						}
						else
						{
							if( yAnt3 > hMax )
								hMax = yAnt3;
						}
					}
				}
			}

			/*
			if( i > 13.9 && i < 14.1 )
			{
				XYSeriesCollection datasetS = new XYSeriesCollection();
			    datasetS.addSeries( emg );

			    PlotXYLine.generate(datasetS,
		    						 ReMoto.path + "emg.jpg",
									 "EMG", 
									 "applied current [mA]", 
									 "EMG [mV]");
			}
			*/
			
			
			System.out.println("m(" + linha + ",1) = " + (mMax - mMin) + ";");
			System.out.println("m(" + linha + ",2) = " + i + ";");

			System.out.println("h(" + linha + ",1) = " + (hMax - hMin) + ";");
			System.out.println("h(" + linha + ",2) = " + i + ";");

			linha++;

			resultVO.setTask( ReMoto.spikes );
			resultVO.setCdSpikes( ReMoto.ALL_MN_end_plate );
			conf.setResult( resultVO );
			results.generateResults(sim, outputSpikes);

			for(int j = 0; j < outputSpikes.size(); j++)
			{
				Point point = (Point)outputSpikes.get(j);
				
				t = point.getX();
				int numMN = (int)Math.round( point.getY() );

				System.out.println(t + "\t" + numMN + "\t" + i);

				linhaMN++;
			}
			
			results = null;
			//sim = null;

			System.gc();
		}

		System.out.println("hold");
		System.out.println("plot(m(:,2),m(:,1))");
		System.out.println("plot(h(:,2),h(:,1))");
		System.out.println("plot(m(:,2),m(:,1),'.')");
		System.out.println("plot(h(:,2),h(:,1),'.')");
		System.out.println("xlabel('Intensidade do est�mulo [mA]')");
		System.out.println("ylabel('Amplitude das respostas [mV]')");
	
	}

}
