package br.remoto.teste;

import br.remoto.model.Configuration;
import br.remoto.model.Simulation;

public class Standalone 
{
	public static void main(String[] args) 
	{
		Configuration conf = new Configuration();
		Simulation sim = new Simulation(conf, "1234");

		sim.createNetwork();
		
		new Thread(sim).start();
	}

}
