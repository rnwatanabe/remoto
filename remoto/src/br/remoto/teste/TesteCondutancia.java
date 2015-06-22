package br.remoto.teste;

import org.jfree.data.xy.XYSeries;

import br.remoto.model.Conductance.AlphaFunction;
import br.remoto.model.Conductance.MultSynapticConductance;
import br.remoto.model.Neuron.Miscellaneous;
import br.remoto.model.vo.ConductanceVO;
import br.remoto.util.PlotXYLine;

public class TesteCondutancia 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		TesteCondutancia teste = new TesteCondutancia();
		
		//teste.testaAlfa();
		//teste.testaMultipla();
		teste.testaMarkov();
	}

	
	public void testaMarkov()
	{
		ConductanceVO g = new ConductanceVO();
		
		g.setCdNucleus( "" );
		g.setCdConductanceType( "" );
		g.setE( 100 );

		g.setAlpha( 0.5 );
		g.setBeta( 0.1 );

		g.setTpeak( 1 );
		g.setTmax( 1 );
		g.setGmax( 1000 );
		
		double step = 0.01;
		
		MarkovConductance cond = new MarkovConductance( g );

		XYSeries serie = new XYSeries("Condutancia");
		
		for(int i = 0; i < 2500; i++)
		{
			double t = step * i; 
			
			if( i%200 == 0 && i < 900  )
			//if( i == 80 || i == 120 || i == 280|| i == 330 || i == 370 || i == 400 || i == 420 )
				cond.start(t);
			
			serie.add( Double.valueOf( i * step ), new Double( cond.getValue(t) ) );

			//System.out.println( cond.getValue(i) + "\t" + Double.valueOf( i * step ) );
		}

    	PlotXYLine.generate(serie,
    						 "D:\\historico_rogerio\\testes\\condutancia.jpg",
							 "Condutancia", 
							 "tempo [ms]", 
							 "condutancia [nS]");
	}

	
	public void testaMultipla()
	{
		ConductanceVO g = new ConductanceVO();
		
		g.setCdNucleus( "" );
		g.setCdConductanceType( "" );
		g.setE( 100 );

		g.setAlpha( 0.5 );
		g.setBeta( 0.1 );
		g.setTpeak( 1 );
		g.setTmax( 1 );
		
		double step = 0.01;
		int numberOfSynapse = 1;
		
		Miscellaneous misc = new Miscellaneous();
		misc.setStep( step );
		
		MultSynapticConductance cond = new MultSynapticConductance( g, "", misc );
		
		for(int i = 0; i < numberOfSynapse; i++)
			cond.addConductance( new ConductanceVO(), 1.0, "" );

		XYSeries serie = new XYSeries("Condutancia");
		
		for(int i = 0; i < 10000; i++)
		{
			double t = i * step;
			
			if( i%55 == 0  )
				cond.receiveSpike(t, Integer.toString(i%numberOfSynapse) );

			//if( i%200 == 0 && i < 8000  )
			//{
			//if( i == 80 || i == 120 || i == 280|| i == 330 || i == 370 || i == 400 || i == 420 )
			//	cond.start(t, Integer.toString(j%numberOfSynapse + 1) );
			//	j++;
			//}
			
			serie.add( Double.valueOf( i * step ), new Double( cond.getValue(1, t) ) );

			//System.out.println( cond.getValue(i) + "\t" + Double.valueOf( i * step ) );
		}

    	PlotXYLine.generate(serie,
    						 "D:\\historico_rogerio\\testes\\condutancia.jpg",
							 "Condutancia", 
							 "tempo [ms]", 
							 "condutancia [nS]");
	}



	
	public void testaAlfa()
	{
		ConductanceVO g = new ConductanceVO();
		
		g.setCdNucleus( "" );
		g.setCdNucleusPre( "" );
		g.setCdConductanceType( "" );
		g.setE( 100 );
		g.setConnectivity( 100 );
		g.setActive( true );

		g.setTpeak( 10 );
		g.setGmax( 10 );
		
		double step = 0.01;
		
		Miscellaneous misc = new Miscellaneous();
		misc.setStep( step );
		
		AlphaFunction cond = new AlphaFunction(g, misc);
		
		cond.reset( misc );

		XYSeries serie = new XYSeries("Condutancia");
		
		for(int i = 0; i < 5000; i++)
		{
			if( i == 1000  )
			//if( i%200 == 0 && i < 900  )
			//if( i == 80 || i == 120 || i == 280|| i == 330 || i == 370 || i == 400 || i == 420 )
				cond.start(i);
			
			serie.add( Double.valueOf( i * step ), new Double( cond.getValue(i) ) );

			//System.out.println( cond.getValue(i) + "\t" + Double.valueOf( i * step ) );
		}

    	PlotXYLine.generate(serie,
    						 "D:\\historico_rogerio\\testes\\alpha_condutancia.jpg",
							 "Condutancia", 
							 "tempo [ms]", 
							 "condutancia [nS]");
	}
}
