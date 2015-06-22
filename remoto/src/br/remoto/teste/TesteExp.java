package br.remoto.teste;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.remoto.old.Exp;


public class TesteExp 
{

	SimpleDateFormat formatter = new SimpleDateFormat("H:mm:ss");

	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		TesteExp teste = new TesteExp();
		
		teste.testaEXP();
		teste.testaMath();
	}

	
	public void testaMath()
	{
		String start = formatter.format(new Date());
		
		double x = 0;

		for(int i = 0; i < 100000000; i++)
		{
			x += Math.exp( Math.random() );
		}
		
		String finish = formatter.format(new Date());
		
		System.out.println( start + " - " + finish + " - " + x );
	}

	
	public void testaEXP()
	{
		String start = formatter.format(new Date());
		
		Exp exp = new Exp();
		double x = 0;

		for(int i = 0; i < 100000000; i++)
		{
			x += exp.calc( Math.random() );
		}
		
		String finish = formatter.format(new Date());
		
		System.out.println( start + " - " + finish + " - " + x );
	}
}
