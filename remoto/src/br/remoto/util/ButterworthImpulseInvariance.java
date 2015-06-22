package br.remoto.util;

public class ButterworthImpulseInvariance 
{
	private double a11;
	private double a21;
	private double b01;
	private double b11;

	private double a12;
	private double b02;
	
	private double y1[];
	private double y2[];
	
	private int n;
	private double xLast;
	

	public ButterworthImpulseInvariance(double[] x, double fc1, double fc2, double T)
	{
		y1 = new double[x.length + 3];
		y2 = new double[x.length + 3];
		
		double w1 = 2*Math.PI * fc1;
		double w2 = 2*Math.PI * fc2;

		double alfa = -(w1*Math.pow(w2,2)) / (Math.sqrt(2)*w2*w1 - Math.pow(w1,2) - Math.pow(w2,2));
		double beta = -Math.pow(w2,4) /(Math.sqrt(2)*w2*w1 - Math.pow(w1,2) - Math.pow(w2,2));
		double gama =  (w1*Math.pow(w2,2))/(Math.sqrt(2)*w2*w1 - Math.pow(w1,2) - Math.pow(w2,2));

		double w0 = w2/Math.sqrt(2);
		double A =  (Math.sqrt(2) * w2)/2;
		double B =  (beta * Math.sqrt(2))/(alfa*w2) - 1;

		y1[0] = y1[1] = y2[0] = y2[1] = x[0] = x[1] = 0;
		
		xLast = 0;
		n = 1;

		a11 = -2 * Math.exp(-A*T) * Math.cos(w0*T);
		a21 = Math.exp(-2*A*T);
		b01 = T * alfa;
		b11 = T * alfa * Math.exp(-A*T)*( -Math.cos(w0*T) + B*Math.sin(w0*T) );

		a12 = -Math.exp(-w1*T);
		b02 = T * gama;
	}
	
	
	// fc1 and fc2: cut-off frequencies - T: sampling period (in seconds)
	public double bandPass(double x)
	{
	    n++;

	    y1[n] = -a11 * y1[n-1] - a21 * y1[n-2] + b01 * x + b11 * xLast;
	    y2[n] = -a12 * y2[n-1] + b02 * x;
	    
	    xLast = x;
		
		return y1[n] + y2[n];
	}

}
