package br.remoto.model.factory;

import java.util.List;

import br.remoto.model.Configuration;
import br.remoto.model.Current;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.Nerve;
import br.remoto.model.ReMoto;
import br.remoto.model.Neuron.Motoneuron;
import br.remoto.model.Neuron.Neuron;
import br.remoto.model.Neuron.SensoryFiber;
import br.remoto.model.Neuron.SpinalNeuron;
import br.remoto.model.vo.NerveVO;


public class StimulationFactory 
{
	
	public void createNerves(Configuration conf, Neuron neurons[][], Nerve[] nerves)
	{
    	List listNerves = conf.getNerves();
    	boolean empty = true;
    	
    	//System.out.println("listNerves.size(): " + listNerves.size());
    	//System.out.println("nerves.length: " + nerves.length);
    	
		// Convert List to Nerve[]
    	for(int n = 0; n < listNerves.size(); n++)
		{
    		NerveVO vo = (NerveVO)listNerves.get(n);
    		
    		//System.out.println("vo.getCdNerve(): " + vo.getCdNerve());
			
    		if( vo == null )
    			continue;
    		
    		//System.out.println("vo.isActive(): " + vo.isActive());
    		
    		// This indicates that there is at least one nerve active
    		if( vo.isActive() == true )
    			empty = false;
    		
			nerves[n] = new Nerve( vo, conf.getStep() );
			
			//System.out.println("nerves[n].getCdNerve(): " + nerves[n].getCdNerve());
			
			
			for(int x = 1; x < neurons.length; x++)
			{
				if( neurons[x] == null )
					continue;
				
				for(int y = 0; y < neurons[x].length; y++)
				{
					Neuron neu = neurons[x][y];
					
					if( neu.isActive() == false )
						continue;
					
					if( ( neu.getCdNucleus().endsWith( ReMoto.TA ) && !nerves[n].getCdNerve().equals( ReMoto.CPN ) ) ||
						( ( neu.getCdNucleus().endsWith( ReMoto.LG ) ||
							neu.getCdNucleus().endsWith( ReMoto.MG ) ||
							neu.getCdNucleus().endsWith( ReMoto.SOL ) ) && !nerves[n].getCdNerve().equals( ReMoto.PTN ) ) )
					{
						continue;
					}
					
					if( neu.getCategory().equals( ReMoto.MN ) )
					{
						((Motoneuron)neu).setNerve( nerves[n] );	
					}
					else if( neu.getCategory().equals( ReMoto.AF ) )
					{
						((SensoryFiber)neu).setNerve( nerves[n] );	
					}
				}
			}
		}
    	
    	if( empty == true )
    		nerves = null;
	}
	

	public void injectCurrent(Configuration conf, Neuron neurons[][])
	{
		for(int x = ReMoto.indDT + 1; x < neurons.length; x++)
		{
			if( neurons[x] == null )
				continue;
			
			for(int y = 0; y < neurons[x].length; y++)
			{
				Neuron neu = neurons[x][y];
				
				if( neu.getCategory().equals( ReMoto.MN ) || neu.getCategory().equals( ReMoto.IN ) )
				{
				    Current inj = conf.getInjectedCurrent(neu.getCdNucleus(), neu.getType());
				    
					// Set injected current
				    if( inj != null && inj.isActive() )
				    {
				    	inj.setStep( conf.getStep() );
				    	
				    	//if( inj.getSignal() == null )
				    	//{
					    //	ModulatingSignal signal = conf.getModulationSignal(neu.getType(), neu.getCdNucleus());
					    //	inj.setSignal( signal );
				    	//}
				    	
				    	((SpinalNeuron)neu).setCurrent( inj );
				    }
				}
			}
		}
	}

}
