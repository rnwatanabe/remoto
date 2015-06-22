/*
 * Created on 09/01/2006
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.remoto.util;

/**
 * @author RRCisi
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Bias
{

	public static double gradual(double v1, double v2, double bias)
	{
		double value;
		double min;
		double max;

		if( v2 > v1 )
		{
			min = v1;
			max = v2;
			value = min + (max - min) * bias;
		}
		else
		{
			min = v2;
			max = v1;
			value = max - (max - min) * bias;
		}
		
		if( value < 0 )
			value = 0;

		return value;
	}


	public static double gradualGaussian(double v1, double v2, double bias, double std)
	{
		double value;
		double min;
		double max;

		if( v2 > v1 )
		{
			min = v1;
			max = v2;
			value = min + (max - min) * bias;
		}
		else
		{
			min = v2;
			max = v1;
			value = max - (max - min) * bias;
		}
		
		if( value < 0 )
			value = 0;
		
		value = Distribution.gaussianPoint(value, value * std);

		return value;
	}


	public static double uniform(double v1, double v2)
	{
		double value;
		double min;
		double max;
		
		if( v2 > v1)
		{
			min = v1;
			max = v2;
		}
		else
		{
			min = v2;
			max = v1;
		}

		double p = Math.random();
		
		value = min + (max - min) * p;
		
		if( value < 0 )
			value = 0;

		return value;
	}
	
}
