package br.remoto.util;

public class ButterworthBilinear 
{
	public final int LOW = 0;
	public final int HIGH = 1;
	
	
	private class Coeficients
	{
		double c0;
		double c1;
		double c2;
		double c3;
		double d1;
		double d2;
		double d3;
	}
	
	// fc: cut-off frequency - T: sampling period (in seconds)
	public double[] lowPass(double[] x, double fc, double T)
	{
		Coeficients low = createCoeficients(LOW, fc, T);
		
		double y[] = new double[x.length];
		
		y[0] = 0;
		y[1] = 0;
		y[2] = 0;

		for(int i = 3; i < x.length; i++)
		{
			y[i] = low.c0*x[i] + low.c1*x[i-1] + low.c2*x[i-2] + low.c3*x[i-3] + 
				   low.d1*y[i-1] + low.d2*y[i-2] + low.d3*y[i-3];
		}
		
		return y;
	}
	
	
	// fc: cut-off frequency - T: sampling period (in seconds)
	public double[] highPass(double[] x, double fc, double T)
	{
		Coeficients high = createCoeficients(HIGH, fc, T);
		
		double y[] = new double[x.length];
		
		y[0] = 0;
		y[1] = 0;
		y[2] = 0;

		for(int i = 3; i < x.length; i++)
		{
			y[i] = high.c0*x[i] + high.c1*x[i-1] + high.c2*x[i-2] + high.c3*x[i-3] + 
				   high.d1*y[i-1] + high.d2*y[i-2] + high.d3*y[i-3];
		}
		
		return y;
	}
	
	
	// fc1 and fc2: cut-off frequencies - T: sampling period (in seconds)
	public double[] bandPass(double[] x, double fc1, double fc2, double T)
	{
		Coeficients low = createCoeficients(LOW, fc2, T);
		Coeficients high = createCoeficients(HIGH, fc1, T);
		
		double aux[] = new double[x.length];
		double y[] = new double[x.length];
		
		aux[0] = 0;
		aux[1] = 0;
		aux[2] = 0;
		y[0] = 0;
		y[1] = 0;
		y[2] = 0;

		for(int i = 3; i < x.length; i++)
		{
			aux[i] = low.c0*x[i] + low.c1*x[i-1] + low.c2*x[i-2] + low.c3*x[i-3] + 
					 low.d1*aux[i-1] + low.d2*aux[i-2] + low.d3*aux[i-3];

			y[i] = high.c0*aux[i] + high.c1*aux[i-1] + high.c2*aux[i-2] + high.c3*aux[i-3] + 
				   high.d1*y[i-1] + high.d2*y[i-2] + high.d3*y[i-3];
		}
		
		return y;
	}

	
	private Coeficients createCoeficients(int type, double fc, double T)
	{
		Coeficients coef = new Coeficients();
		
		double wc = 2*Math.PI*fc;
		double wcw = (2/T)*Math.tan((wc*T)/2);

		double A0 =   8 + 8*wcw*T + 4*Math.pow(wcw*T, 2) +   Math.pow(wcw*T, 3);
		double A1 = -24 - 8*wcw*T + 4*Math.pow(wcw*T, 2) + 3*Math.pow(wcw*T, 3);
		double A2 =  24 - 8*wcw*T - 4*Math.pow(wcw*T, 2) + 3*Math.pow(wcw*T, 3);
		double A3 =  -8 + 8*wcw*T - 4*Math.pow(wcw*T, 2) + 	 Math.pow(wcw*T, 3);

		coef.d1 = -A1/A0;
		coef.d2 = -A2/A0;
		coef.d3 = -A3/A0;

		if( type == LOW )
		{
			coef.c0 = (  Math.pow(wcw*T, 3))/A0;
			coef.c1 = (3*Math.pow(wcw*T, 3))/A0;
			coef.c2 = coef.c1;
			coef.c3 = coef.c0;
		}
		else if( type == HIGH )
		{
			coef.c0 =   8/A0;
			coef.c1 = -24/A0;
			coef.c2 =  24/A0;
			coef.c3 =  -8/A0;
		}
		
		return coef;
	}

}
