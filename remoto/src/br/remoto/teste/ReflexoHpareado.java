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
import br.remoto.model.vo.ConductanceVO;
import br.remoto.model.vo.ResultVO;
import br.remoto.model.vo.User;
import br.remoto.util.Point;

// Linha de comando para execucao, a aprtir do diretorio raiz onde estao os .class:
// java -Xms900m -Xmx1000m -cp .;F:\rogerio\workspace\lib\jfreechart-1.0.6.jar;F:\rogerio\workspace\lib\jcommon-1.0.10.jar;F:\rogerio\workspace\lib\hsqldb.jar;C:\java\tomcat\lib\servlet-api.jar br.remoto.teste.ReflexoHpareado

public class ReflexoHpareado 
{
	UserDAO userDAO = new UserDAO();
	User user;
	Configuration conf;
	
	
	public ReflexoHpareado()
	{
		ReMoto.path = "C:\\java\\tomcat\\webapps\\remoto\\"; 
			
		user = userDAO.loadUser("guest", "guest");
		conf = new Configuration();
	}
		
	
	public static void main(String[] args) 
	{
		ReflexoHpareado teste = new ReflexoHpareado();
		
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

		double tFin = 60;

		ResultVO resultVO = new ResultVO();
		
		//resultVO.setWithEMGnoise( false );
		//resultVO.setSample( 20 );
		resultVO.setOpt( ReMoto.array );
		
		System.out.println("clear all");
		System.out.println("close all");

    	conf.setMerge( true );
		
    	List listNerves = new ArrayList();

    	Nerve ptn = new Nerve( conf.getNerve( ReMoto.PTN ), conf.getStep() );
    	
    	ptn.setAmp( 10.0 );
    	ptn.setTini( 0.0 );
    	ptn.setTfin( tFin );
    	ptn.setFreq( 1.0 );
    	ptn.setActive( true );
    	ptn.setPaired( true );
    	ptn.setDelayPaired( 10.0 );
    	ptn.setAmpPaired( 30.0 );

    	listNerves.add( ptn );
    	
    	conf.setNerves( listNerves );
    	
    	//conf.setQuantityNeuronType("SOL", "RC", 0);
    	
    	// Altera gSyn RC-MN
    	ConductanceVO gS = conf.getSynapseType("SOL", "SOL", "IN RC-MN S");
    	ConductanceVO gFR = conf.getSynapseType("SOL", "SOL", "IN RC-MN FR");
    	ConductanceVO gFF = conf.getSynapseType("SOL", "SOL", "IN RC-MN FF");
    	gS.setGmax( 4000 );
    	gFR.setGmax( 4000 );
    	gFF.setGmax( 4000 );
    	conf.setSynapseType("SOL" + "SOL" + "IN RC-MN S", gS);
    	conf.setSynapseType("SOL" + "SOL" + "IN RC-MN FR", gFR);
    	conf.setSynapseType("SOL" + "SOL" + "IN RC-MN FF", gFF);
    	
    	Simulation sim = new Simulation( conf, cdSimulation );
    	
		sim.createNetwork();
		sim.createInputs();
		sim.createStimulation();
		sim.createSynapses();

		int linha = 1;

    	for(double amp = 10.0; amp <= 20.0; amp = amp + 0.5)
		{
        	for(int run = 0; run < 2; run++)
    		{
	        	listNerves = new ArrayList();
	
	        	ptn = new Nerve( conf.getNerve( ReMoto.PTN ), conf.getStep() );
	        	
	        	ptn.setAmp( amp );
	        	ptn.setTini( 0.0 );
	        	ptn.setTfin( tFin );
	        	ptn.setFreq( 1.0 );
	        	ptn.setActive( true );
	        	ptn.setDelayPaired( 10.0 );
	        	ptn.setAmpPaired( 30.0 );

	        	if( run%2 == 0 )
		        	ptn.setPaired( false );
	        	else
	            	ptn.setPaired( true );
	        	
	        	listNerves.add( ptn );
	        	
	        	conf.setNerves( listNerves );
	    		
	    		sim.run();
	    		
	    		conf.setTFin( tFin );
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
				double mMax = 0;
				double mMin = 0;
				
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
							
							if( point.getX() < 30 )
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
							
							if( point.getX() < 30 )
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
				
	        	if( run%2 == 0 )
	        	{
	        		System.out.println("h(" + linha + ",1) = " + (hMax - hMin) + ";");
	    			System.out.println("m(" + linha + ",1) = " + (mMax - mMin) + ";");
	        	}
	        	else
	        	{
	        		System.out.println("h(" + linha + ",2) = " + (hMax - hMin) + ";");
	    			System.out.println("m(" + linha + ",2) = " + (mMax - mMin) + ";");
					linha++;
	        	}
				
				results = null;
				//sim = null;
	
				System.gc();
			}
		}
    	
		System.out.println("hold");
		System.out.println("plot(h(:,2),h(:,1))");
		System.out.println("plot(h(:,2),h(:,1),'.')");
		System.out.println("xlabel('Amplitude do reflexo H condicionante [%]')");
		System.out.println("ylabel('Amplitude do reflexo H teste [%]')");
	}

}
