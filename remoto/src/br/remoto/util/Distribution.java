package br.remoto.util;


public class Distribution 
{
	public static double poissonPoint( double m )
	{
		double r = Math.random();

		return -m * Math.log(r);
	}	

	
	public static double gaussianPoint( double m, double dp )
	{
		double aux = 0.0;

		for(int i = 1; i <= 12; i++)
			aux += Math.random();

		return( m + dp * (aux - 6.0) );
	}
	
	public static double gammaPoint( double a, double k )
	{
		double aux = 1.0;

		for(int i = 1; i <= k; i++)
			aux *= Math.random();

		return -a * Math.log(aux);
	}
	
}
