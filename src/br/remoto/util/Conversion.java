package br.remoto.util;


public class Conversion 
{
	
	public static double convertMillisecondsToSeconds(double t){
		return t/1000;
	}
	
	public static double convertSecondsToMilliseconds(double t){
		return t * 1000;
	}
	
	
	public static double convertVelocityMillisecondsToSeconds(double v){
		return v*1000;
	}
	
	public static double convertAccelerationMillisecondsToSeconds(double a){
		return a*1000000;
	}
	
	public static double convertVelocitySecondsToMilliseconds(double v){
		return v/1000;
	}
	
	public static double convertAccelerationSecondsToMilliseconds(double a){
		return a/1000000;
	}
	
	public static double convertNumberOfDecimalAlgarisms (double a, int num){
		
		double result = 0;
		int aux = 0;
		int aux2 = (int) Math.pow(10, num);
		
		aux = (int) (a * aux2);
		
		result = ((double) aux) / aux2;
		
		return result;
	}
	
	
}
