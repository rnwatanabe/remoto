package br.remoto.model.factory;

import java.util.ArrayList;
import java.util.List;

import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;
import br.remoto.model.Neuron.NeuralTract;
import br.remoto.model.Neuron.Neuron;
import br.remoto.model.Neuron.Miscellaneous;
import br.remoto.model.vo.Nucleus;
import br.remoto.model.vo.NeuronVO;


public class NeuralTractFactory 
{
	
	public void createNeuralTracts(Configuration conf, Neuron neurons[][])
	{
    	List nuclei = conf.getNuclei();
    	List neuronTypes = conf.getNeuronTypes();
    	List listNeurons = new ArrayList();

		Miscellaneous misc = new Miscellaneous(); 
		misc.setStep( conf.getStep() );
		
	    List noises = conf.getNeuronTypes( ReMoto.DT, ReMoto.Noise );
	    
		// -------------------------------------------------------------------------------
	    // Make a loop among motor nuclei, in order to create descending tracts and noises
    	for(int x = 0; x < nuclei.size(); x++)
		{
	    	Nucleus nuc = (Nucleus)nuclei.get(x);
	    	
	    	NeuronVO referenceS = conf.getCompleteNeuronType(nuc.getCd(), ReMoto.S);
	    	NeuronVO referenceFR = conf.getCompleteNeuronType(nuc.getCd(), ReMoto.FR);
			
		    for(int y = 0; y < neuronTypes.size(); y++)
			{
		    	NeuronVO reference = (NeuronVO)neuronTypes.get(y);
				
				if( !reference.isActive() || reference.getQuantity() == 0 || !reference.getCdNucleus().equals( nuc.getCd() ) )
		    		continue;
				
				for(int index = 0; index < reference.getQuantity(); index++)
				{
				    // Create descending tracts
			    	if( reference.getCategory().equals( ReMoto.TR ) )
			    	{
						NeuralTract nt = new NeuralTract(reference, index, misc, true);
						
					    // Set neuron cd and order
					    nt.setCd( reference.getCategory() + " " + reference.getType() + " " + (index+1) );
				    	
				    	// Put neuron in the temporary array
					    listNeurons.add( nt );
			    	}
				    // Create noises for MNs
			    	else if( reference.getCategory().equals( ReMoto.MN ) )
					{
					    for(int n = 0; n < noises.size(); n++)
					    {
					    	NeuronVO noise = (NeuronVO)noises.get(n);
					    	
					    	if( ( noise.getType().equals( ReMoto.NoiseExcitatoryMN ) || noise.getType().equals( ReMoto.NoiseInhibitoryMN ) ) &&
					    		noise.isActive() )
					    	{
								int indexCategory = index;
								
								if( reference.getType().equals( ReMoto.FR ) )
									indexCategory += referenceS.getQuantity();
								else if( reference.getType().equals( ReMoto.FF ) )
									indexCategory += referenceS.getQuantity() + referenceFR.getQuantity();
								
					    		noise.setFin( conf.getTFin() );
								
								NeuralTract nt = new NeuralTract(noise, indexCategory, misc, false);
								nt.setIndex( indexCategory + 1 );
		
								listNeurons.add( nt );
					    	}
					    }
					}
				    // Create noises for RCs
			    	else if( reference.getType().equals( ReMoto.RC ) )
					{
					    for(int n = 0; n < noises.size(); n++)
					    {
					    	NeuronVO noise = (NeuronVO)noises.get(n);
					    	
					    	if( ( noise.getType().equals( ReMoto.NoiseExcitatoryRC ) || noise.getType().equals( ReMoto.NoiseInhibitoryRC ) ) &&
						    	noise.isActive() )
					    	{
								int indexCategory = index;
								
					    		noise.setFin( conf.getTFin() );
								
								NeuralTract nt = new NeuralTract(noise, indexCategory, misc, false);
								nt.setIndex( indexCategory + 1 );
		
								listNeurons.add( nt );
					    	}
					    }
					    
					    
					    // ----------------------------------
					    // Add spontaneous firing rate to RCs
					    
				    	NeuronVO noise = new NeuronVO();
				    	
						int indexCategory = index;
						
						double freq = conf.getMiscellaneous( ReMoto.spontaneousRC_mean );
						double cv = conf.getMiscellaneous( ReMoto.spontaneousRC_cv );
						
						if( freq < 0 )
							continue;
						
						double mean = 1000.0 / freq;
						double std = mean * cv;
						
						noise.setCategory( ReMoto.Noise );
						noise.setType( ReMoto.NoiseExcitatoryRC );
						noise.setCdNucleus( ReMoto.DT );
						noise.setActive( true );
						noise.setDistribution( ReMoto.gaussian );
						noise.setMean( mean );
						noise.setStd( std );
						noise.setIni( 0 );
			    		noise.setFin( conf.getTFin() );
						
						NeuralTract nt = new NeuralTract(noise, indexCategory, misc, false);
						nt.setIndex( indexCategory + 1 );

						listNeurons.add( nt );
					}
		    	}
			}
		}

    	// Transform ArrayList in Array to speed-up simulations
	    Neuron[] arrayNucleus = new Neuron[ listNeurons.size() ];

	    for(int n = 0; n < listNeurons.size(); n++)
		{
			arrayNucleus[n] = (Neuron)listNeurons.get(n);
		}
		
		neurons[ ReMoto.indDT ] = arrayNucleus;
	}

}
