package br.remoto.teste;

import org.jfree.data.xy.XYSeries;

import br.remoto.model.ReMoto;
import br.remoto.util.ButterworthBilinear;
import br.remoto.util.ButterworthImpulseInvariance;
import br.remoto.util.PlotXYLine;

public class TesteFiltro {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		TesteFiltro teste = new TesteFiltro();
		
		teste.testaFiltroImpulseInvariance();
	}
	
	
	public void testaFiltroImpulseInvariance()
	{

		double T = 0.05e-3;
		double[] in = new double[(int)(1/T)];
		
		/* Onda quadrada
		int periodos = (in.length/2)/20;
		int valor = 0;
		int cont = 0;
		
		for(int i = 0; i < in.length; i++)
		{
			if( cont == periodos && valor == 0 )
			{
				valor = 1;
				cont = 0;
			}
			else if( cont == periodos && valor == 1 )
			{
				valor = 0;
				cont = 0;
			}
			
			in[i] = valor;
			cont++;
		}
		*/

		// Onda senoidal
		for(int i = 0; i < in.length; i++)
		{
			in[i] = 5; //Math.sin( 2*Math.PI*200*i*T );
		}

			
		ButterworthImpulseInvariance butter = new ButterworthImpulseInvariance(in, 50, 1000, T);

		//double[] out = butter.highPass(in, 100, T);
		//double[] out = butter.lowPass(in, 100, T);
		
		XYSeries serieIn = new XYSeries("Filter");
		XYSeries serieOut = new XYSeries("Filter");
		
		for(int i = 0; i < in.length; i++)
		{
			serieIn.add( i * T, in[i] );
			serieOut.add( i * T, butter.bandPass(in[i]) );
		}
		
		ReMoto.path = "D:\\historico_rogerio\\testes\\"; 
		
    	PlotXYLine.generate(serieIn,
    						 ReMoto.path + "filtroIn.jpg",
							 "", 
							 "", 
							 "");
		
    	PlotXYLine.generate(serieOut,
    						 ReMoto.path + "filtroOut.jpg",
							 "", 
							 "", 
							 "");

	}
	
	
	public void testaFiltroBilinear()
	{
		ButterworthBilinear butter = new ButterworthBilinear();

		double T = 0.05e-3;
		double[] in = new double[(int)(1/T)];
		
		/* Onda quadrada
		int periodos = (in.length/2)/20;
		int valor = 0;
		int cont = 0;
		
		for(int i = 0; i < in.length; i++)
		{
			if( cont == periodos && valor == 0 )
			{
				valor = 1;
				cont = 0;
			}
			else if( cont == periodos && valor == 1 )
			{
				valor = 0;
				cont = 0;
			}
			
			in[i] = valor;
			cont++;
		}
		*/

		// Onda senoidal
		for(int i = 0; i < in.length; i++)
		{
			in[i] = Math.sin( 2*Math.PI*125*i*T );
		}

			
		//double[] out = butter.highPass(in, 100, T);
		//double[] out = butter.lowPass(in, 100, T);
		double[] out = butter.bandPass(in, 50, 1000, T);
		
		XYSeries serieIn = new XYSeries("Filter");
		XYSeries serieOut = new XYSeries("Filter");
		
		for(int i = 0; i < out.length; i++)
		{
			serieIn.add( i * T, in[i] );
			serieOut.add( i * T, out[i] );
		}
		
		ReMoto.path = "D:\\historico_rogerio\\testes\\"; 
		
    	PlotXYLine.generate(serieIn,
    						 ReMoto.path + "filtroIn.jpg",
							 "", 
							 "", 
							 "");
		
    	PlotXYLine.generate(serieOut,
    						 ReMoto.path + "filtroOut.jpg",
							 "", 
							 "", 
							 "");

	}

}
