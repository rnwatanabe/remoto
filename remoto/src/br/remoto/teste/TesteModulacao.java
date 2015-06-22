package br.remoto.teste;

import org.jfree.data.xy.XYSeries;

import br.remoto.model.ModulatingSignal;
import br.remoto.model.ReMoto;
import br.remoto.util.PlotXYLine;


public class TesteModulacao 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		TesteModulacao testa = new TesteModulacao();
		
		testa.testa();
	}


	public void testa()
	{
		ModulatingSignal signal = new ModulatingSignal();
		
		signal.setCdSignal( ReMoto.ramp );
		signal.setAmp( 0.5 );
		signal.setFreq( 10 );
		signal.setWidth( 20 );
		
		double value = 0;
		double step = 0.05;
		
		XYSeries serie = new XYSeries("Teste do sinal modulante");

		for(int t = 0; t < 10000; t++)
		{
			double time = t * step;
			
			value = signal.value(time);

			serie.add( time, value );
		}
		
    	PlotXYLine.generate(serie,
    						"D:\\historico_rogerio\\testes\\modulante.jpg",
							"", 
							"", 
							"");
	}
}
