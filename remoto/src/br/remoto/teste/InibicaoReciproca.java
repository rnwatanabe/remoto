package br.remoto.teste;

import java.util.ArrayList;
import java.util.List;

import br.remoto.dao.ConfigurationDAO;
import br.remoto.dao.UserDAO;
import br.remoto.model.Configuration;
import br.remoto.model.Nerve;
import br.remoto.model.ReMoto;
import br.remoto.model.ResultDisplay;
import br.remoto.model.Simulation;
//import br.remoto.model.vo.NeuronVO;
import br.remoto.model.vo.ConductanceVO;
import br.remoto.model.vo.NeuronVO;
import br.remoto.model.vo.ResultVO;
import br.remoto.model.vo.User;
import br.remoto.util.Point;

// Linha de comando para execucao, a aprtir do diretorio raiz onde estao os .class:
// java -Xms900m -Xmx1000m -cp .;F:\rogerio\workspace\lib\jfreechart-1.0.6.jar;F:\rogerio\workspace\lib\jcommon-1.0.10.jar;F:\rogerio\workspace\lib\hsqldb.jar;C:\java\tomcat\lib\servlet-api.jar br.remoto.teste.InibicaoReciproca

public class InibicaoReciproca 
{
	UserDAO userDAO = new UserDAO();
	User user;
	Configuration conf;
	
	
	public InibicaoReciproca()
	{
		ReMoto.path = "C:\\java\\tomcat\\webapps\\remoto\\"; 
			
		user = userDAO.loadUser("guest", "guest");
		conf = new Configuration();
	}
		
	
	public static void main(String[] args) 
	{
		InibicaoReciproca teste = new InibicaoReciproca();
		
		teste.leBD();
		teste.levantaCurva();
	}
		
	
	public void leBD()
	{
		ConfigurationDAO simDAO = new ConfigurationDAO();
		conf = simDAO.getConfiguration(2);
	}
	
	
	public void levantaCurva()
	{
		ConfigurationDAO simDAO = new ConfigurationDAO();
    	String cdSimulation = "1234";

		conf = simDAO.getConfiguration(1);

		double tFin = 55;

		ResultVO resultVO = new ResultVO();
		
		resultVO.setWithEMGnoise( false );
		resultVO.setSample( 20 );
		resultVO.setOpt( ReMoto.array );
		
		System.out.println("clear all");
		System.out.println("close all");

		conf.setMerge( true );
		
    	conf.setQuantityNeuronType("TA", "Ia", 280);

    	conf.getNeuronType("TA", "Ia").setAxonVelocity1( 67 );
    	conf.getNeuronType("TA", "Ia").setAxonVelocity2( 67 );
    	
    	conf.getNeuronType("SOL", "Ia").setAxonVelocity1( 67 );
    	conf.getNeuronType("SOL", "Ia").setAxonVelocity2( 67 );
    	
    	conf.setMiscellaneous(ReMoto.latencyExcitatory, 0.7);
    	conf.setMiscellaneous(ReMoto.latencyInhibitory, 0.7);

/*    	
    	// Altera gSyn IaIn-MN
    	ConductanceVO gS = conf.getConductanceType("SOL", "SOL", "IN IaIn-MN S");
    	ConductanceVO gFR = conf.getConductanceType("SOL", "SOL", "IN IaIn-MN FR");
    	ConductanceVO gFF = conf.getConductanceType("SOL", "SOL", "IN IaIn-MN FF");
    	gS.setGmax( 250 );
    	gFR.setGmax( 250 );
    	gFF.setGmax( 250 );
    	conf.setConductanceType("SOL" + "SOL" + "IN IaIn-MN S", gS);
    	conf.setConductanceType("SOL" + "SOL" + "IN IaIn-MN FR", gFR);
    	conf.setConductanceType("SOL" + "SOL" + "IN IaIn-MN FF", gFF);
*/
    	
    	ConductanceVO gNoiseES = conf.getSynapseType("DT", "SOL", "Noise excitatory-MN S");
    	ConductanceVO gNoiseEFR = conf.getSynapseType("DT", "SOL", "Noise excitatory-MN FR");
    	ConductanceVO gNoiseEFF = conf.getSynapseType("DT", "SOL", "Noise excitatory-MN FF");
    	ConductanceVO gNoiseIS = conf.getSynapseType("DT", "SOL", "Noise inhibitory-MN S");
    	ConductanceVO gNoiseIFR = conf.getSynapseType("DT", "SOL", "Noise inhibitory-MN FR");
    	ConductanceVO gNoiseIFF = conf.getSynapseType("DT", "SOL", "Noise inhibitory-MN FF");
    	gNoiseES.setGmax( 2500 );
    	gNoiseEFR.setGmax( 2500 );
    	gNoiseEFF.setGmax( 2500 );
    	gNoiseIS.setGmax( 2500 );
    	gNoiseIFR.setGmax( 2500 );
    	gNoiseIFF.setGmax( 2500 );
    	conf.setSynapseType("DT" + "SOL" + "Noise excitatory-MN S", gNoiseES);
    	conf.setSynapseType("DT" + "SOL" + "Noise excitatory-MN FR", gNoiseEFR);
    	conf.setSynapseType("DT" + "SOL" + "Noise excitatory-MN FF", gNoiseEFF);
    	conf.setSynapseType("DT" + "SOL" + "Noise inhibitory-MN S", gNoiseIS);
    	conf.setSynapseType("DT" + "SOL" + "Noise inhibitory-MN FR", gNoiseIFR);
    	conf.setSynapseType("DT" + "SOL" + "Noise inhibitory-MN FF", gNoiseIFF);
    	
    	// Acrescenta ruido
    	List noises = conf.getNeuronTypes( ReMoto.DT, ReMoto.Noise );
	    for(int i = 0; i < noises.size(); i++)
	    {
    		NeuronVO noise = (NeuronVO)noises.get(i);
    		
    		noise.setMean( 1 );
    		noise.setActive( true );
    		noise.setQuantity( 1 );
	    }

    	List listNerves = new ArrayList();

    	Nerve tibial = new Nerve( conf.getNerve( ReMoto.PTN ), conf.getStep() );
    	
    	tibial.setAmp( 11.1 );
    	tibial.setTfin( tFin );
    	tibial.setFreq( 1.0 );
    	tibial.setActive( true );

    	Nerve dpn = new Nerve( conf.getNerve( ReMoto.CPN ), conf.getStep() );
    	
    	dpn.setAmp( 12 );
    	dpn.setTfin( tFin );
    	dpn.setFreq( 1.0 );
    	dpn.setActive( true );

		listNerves.add( tibial );
		listNerves.add( dpn );
		
    	conf.setNerves( listNerves );
    	
    	Simulation sim = new Simulation( conf, cdSimulation );
    	
		sim.createNetwork();
		sim.createInputs();
		sim.createStimulation();
		sim.createSynapses();

    	for(int run = 0; run < 20; run++)
		{
    		int linha = 1;
    		double h0 = 0;
    		double t0 = 2;

	    	for(double deltaT = 0; deltaT <= 20.0 + t0; deltaT = deltaT + 1)
			{
	    		List nerves = conf.getNerves();
	    		
	    		for(int n = 0; n < nerves.size(); n++)
	    		{
	    			Nerve nerve = (Nerve)nerves.get(n);
	    			
	    			if( nerve == null || nerve.isActive() == false )
	    				continue;
	
	    			if( nerve.getCdNerve().equals( ReMoto.CPN ) )
	    				nerve.setTini( t0 );
	    			else
	    				nerve.setTini( deltaT );
	    		}
	    		
	    		sim.run();
	    		
	    		conf.setTFin( tFin + deltaT );
	    		conf.setChangedConfiguration( false );
	    		conf.setKeepProperties( true );
	    		
				ArrayList outputEMG = new ArrayList();
	
				resultVO.setTask( ReMoto.EMG );
				resultVO.setCdEMG( ReMoto.ALL_MU );
				resultVO.setCdNucleus( "SOL" );
				
				conf.setResult( resultVO );
				
				ResultDisplay results = new ResultDisplay(conf);
				
				results.generateResults(sim, outputEMG);
	
				double y = 0;
				double yAnt1 = 0;
				double yAnt2 = 0;
				double yAnt3 = 0;
				
				boolean direction = false;
				
				double hMax = 0;
				double hMin = 0;
				
				for(int j = 0; j < outputEMG.size(); j++)
				{
					Point point = (Point)outputEMG.get(j);
					
					yAnt3 = yAnt2;
					yAnt2 = yAnt1;
					yAnt1 = y;
					y = point.getY();
					
					if( y > 0.01 || y < -0.01 )
					{
						if( direction == false && y > yAnt1 && yAnt1 > yAnt2 && yAnt2 > yAnt3 )
						{
							direction = true;
							
							if( point.getX() > 30 && yAnt3 < hMin )
							{
								hMin = yAnt3;
							}
						}
						else if( direction == true && y < yAnt1 && yAnt1 < yAnt2 && yAnt2 < yAnt3 )
						{
							direction = false;
							
							if( point.getX() > 30 && yAnt3 > hMax )
							{
								hMax = yAnt3;
							}
						}
					}
				}
				
				if( linha == 1 || h0 < 0.00001 )
					h0 = hMax - hMin;
	
				System.out.println("h" + run + "(" + linha + ",1) = " + (hMax - hMin)/h0 + ";");
				System.out.println("h" + run + "(" + linha + ",2) = " + (deltaT - t0) + ";");
				
				linha++;
				
				results = null;
				//sim = null;
	
				System.gc();
			}
		}
    	
		System.out.println("hold");
		System.out.println("plot(h(:,2),h(:,1))");
		System.out.println("plot(h(:,2),h(:,1),'.')");
		System.out.println("xlabel('Diferenca de tempo entre teste e condicionante [ms]')");
		System.out.println("ylabel('Amplitude do reflexo H [%]')");
	}

}
