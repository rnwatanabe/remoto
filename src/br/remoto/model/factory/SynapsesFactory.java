package br.remoto.model.factory;

import java.util.ArrayList;
import java.util.List;

import br.remoto.model.Configuration;
import br.remoto.model.Neuron.Interneuron;
import br.remoto.model.Neuron.Neuron;
import br.remoto.model.Neuron.SpinalNeuron;
import br.remoto.model.ReMoto;
import br.remoto.model.Neuron.Motoneuron;
import br.remoto.model.vo.ConductanceVO;
import br.remoto.model.vo.DynamicVO;


public class SynapsesFactory 
{
	// ??? teste para saber o numero de sinapses de uma simulacao de larga escala
	//public static long numSinapses = 0; 
	

	public void createSynapses(Configuration conf, Neuron neurons[][])
	{
		List nt = new ArrayList();
		List in = new ArrayList();
		List rc = new ArrayList();
		List noises = new ArrayList();

		// Store NT, IN and Noises to use bellow
		for(int x = 0; x < neurons.length; x++)
		{

			for(int n = 0; n < neurons[x].length; n++)
			{
				Neuron neu = neurons[x][n];
				
				if( neu.getCategory().equals( ReMoto.TR ) )
					nt.add( neu );
				else if( neu.getCategory().equals( ReMoto.IN ) )
					in.add( neu );
				else if( neu.getCategory().equals( ReMoto.Noise ) )
					noises.add( neu );

				if( neu.getType().equals( ReMoto.RC ) )
					rc.add( neu );
			}
		}

		// The loop begins with the index of the first regular nucleus
		for(int x = ReMoto.indDT + 1; x < neurons.length; x++)
		{
			// MNs, INs and Noises of each nucleus
			List mnNucleus = new ArrayList();
			List rcNucleus = new ArrayList();
			List inNucleus = new ArrayList();

			Neuron neu = null;
			
			// Collect neurons of this nucleus and put on the lists
			for(int n = 0; n < neurons[x].length; n++)
			{
				neu = neurons[x][n];
				
				if( neu.getCategory().equals( ReMoto.MN ) )
					mnNucleus.add( neu );
				else if( neu.getType().equals( ReMoto.RC ) )
					rcNucleus.add( neu );
				else if( neu.getType().equals( ReMoto.IaIn ) || neu.getType().equals( ReMoto.IbIn ) || neu.getType().equals( ReMoto.gII ) )
					inNucleus.add( neu );
			}
			
			// ---------------------------------
			// 1) MN�s synapses inside a nucleus
			// 		a) DT over MN 
			// 		b) Noises over MN 
			// 		c) IaIn/IbIn/PS over MN
			// 		d) RC over MN 
			// 		e) MN over RC 
			// ---------------------------------

			for(int m = 0; m < mnNucleus.size(); m++)
			{
				Neuron mnNeu = (Neuron)mnNucleus.get(m);
				
				// --------------
				// a) DTs and MNs
				for(int d = 0; d < nt.size(); d++)
				{
					//System.out.println("nt.size():  " + nt.size());
					Neuron dtNeu = (Neuron)nt.get(d);

					// Place DTs equaly distributed in relation to MN pool
					ConductanceVO g = conf.getSynapseType(dtNeu, mnNeu);
					double connectivity = conf.getSynapticConnectivity(g);
					double rand = Math.random();
					
					if( connectivity < rand || connectivity < 0.01 )
						continue;
					
					//System.out.println("dtNeu.getCd():  " + dtNeu.getCd() + "   g.getCdConductanceType()" + g.getCdConductanceType() + "   g.getDynamicType(): " + g.getDynamicType());
					
					createSynapticConductance(conf, g, dtNeu, mnNeu, 1.0);
				}
				
				// -----------------
				// b) Noises and MNs
				//System.out.println("Noise size: " + noises.size());
				if( noises != null )
				{
					
					for(int d = 0; d < noises.size(); d++)
					{
						Neuron noise = (Neuron)noises.get(d);
						
				    	if( !noise.getType().equals( ReMoto.NoiseExcitatoryMN ) && !noise.getType().equals( ReMoto.NoiseInhibitoryMN ) )
				    		continue;

			    		ConductanceVO g = conf.getSynapseType(noise, mnNeu);
						double connectivity = conf.getSynapticConnectivity(g);
						
						if( connectivity < 0.01 )
							continue;
						
						// b) Synapses Noises over MN 
						// Make synapse only between MN and its respectively noise
						//if( noise.getIndex() == ((Motoneuron)mnNeu).getIndex() )
						if( d == m )
						{							
							createSynapticConductance(conf, g, noise, mnNeu, connectivity);
						}
					}
				}
				
				// --------------------
				// c) IaIn/IbIn/gII and MNs 
				for(int ins = 0; ins < in.size(); ins++)
				{
					neu = (Neuron)in.get(ins);

					// RC synapses are considered bellow
					if( neu.getType().equals( ReMoto.RC ) )
						continue;

					// Place Ins equaly distributed in relation to MN pool
					ConductanceVO g = conf.getSynapseType(neu, mnNeu);
					double connectivity = conf.getSynapticConnectivity(g);
					double rand = Math.random();
					
					if( connectivity < rand || connectivity < 0.01 )
						continue;

					createSynapticConductance(conf, g, neu, mnNeu, 1);
				}

				// ------------------------------------------------------------------
				// RCs and MNs: all RCs and MNs of any nucleus can change connections
				for(int r = 0; r < rc.size(); r++)
				{
					Neuron rcNeu = (Neuron)rc.get(r);

					// ----------------------
					// d) Synapses RC over MN 

					ConductanceVO g = conf.getSynapseType(rcNeu, mnNeu);
					double connectivity = conf.getSynapticConnectivity(g);
					double distance = Math.abs( rcNeu.getXPosition() - mnNeu.getXPosition() );
					double rand = Math.random();
					
					if( connectivity > 0.01 && rand < connectivity)
					{
						double weight = conf.getMiscellaneous( ReMoto.decliningRCtoMN ) / (conf.getMiscellaneous( ReMoto.decliningRCtoMN ) + distance * distance);
						
						
						
						if( weight >= 0.01 )
						{
							createSynapticConductance(conf, g, rcNeu, mnNeu, weight);
							
							//if( m == 400 )
							//	System.out.println( distance + "\t" + weight );

							//if( r == 100 )
							//	System.out.println( m + "\t" + weight );
						}
					}
	
					// ----------------------
					// e) Synapses MN over RC  

					g = conf.getSynapseType(mnNeu, rcNeu);
					connectivity = conf.getSynapticConnectivity(g);
					rand = Math.random();
					
					if( connectivity > 0.01 && rand < connectivity)
					{
						double weight = conf.getMiscellaneous( ReMoto.decliningMNtoRC ) / (conf.getMiscellaneous( ReMoto.decliningMNtoRC ) + distance * distance);

						if( weight >= 0.01 )
						{
							createSynapticConductance(conf, g, mnNeu, rcNeu, weight);
							
							//System.out.println( m + "\t" + r + "\t" + weight );
	
							//if( m == 400 )
							//	System.out.println( distance + "\t" + weight );
						}
					}
				}
			}
			
			// ---------------------------------
			// 2) Noises over RC 
			// ---------------------------------

			for(int r = 0; r < rcNucleus.size(); r++)
			{
				Neuron rcNeu = (Neuron)rcNucleus.get(r);
				
				// teste de gMaxTot
				// System.out.println( ((SpinalNeuron)rcNeu).getDendExcitSynapses().getCd() + "\t" + ((SpinalNeuron)rcNeu).getDendExcitSynapses().getGMaxTot() );
	
				// --------------
				// Noises and RCs
				
				if( noises != null )
				{
					for(int d = 0; d < noises.size(); d++)
					{
						Neuron noise = (Neuron)noises.get(d);
						
				    	if( !noise.getType().equals( ReMoto.NoiseExcitatoryRC ) && !noise.getType().equals( ReMoto.NoiseInhibitoryRC ) )
				    		continue;

			    		ConductanceVO g = conf.getSynapseType(noise, rcNeu);
						double connectivity = conf.getSynapticConnectivity(g);
						
						if( connectivity < 0.01 )
							continue;
						
						// Synapses Noises over RC 
						// Make synapse only between RC and its respectively noise
						if( noise.getIndex() == ((Interneuron)rcNeu).getIndex() )
						{
							System.out.println(noise.getIndex() + "  " + ((Interneuron)rcNeu).getIndex() );
							createSynapticConductance(conf, g, noise, rcNeu, connectivity);
						}
					}
				}
			}
			
			// ---------------------------------
			// 3) Other Ins synapses
			// ---------------------------------

			for(int i = 0; i < inNucleus.size(); i++)
			{
				Neuron inNeu = (Neuron)inNucleus.get(i);

				// DTs over Ins
				for(int d = 0; d < nt.size(); d++)
				{
					Neuron dtNeu = (Neuron)nt.get(d);

					// Place DTs equaly distributed in relation to In pool
					ConductanceVO g = conf.getSynapseType(dtNeu, inNeu);
					double connectivity = conf.getSynapticConnectivity(g);
					double rand = Math.random();
					
					if( connectivity < rand || connectivity < 0.01 )
						continue;
					
					createSynapticConductance(conf, g, dtNeu, inNeu, 1.0);
				}

				// Ins over Ins
				for(int ins = 0; ins < in.size(); ins++)
				{
					neu = (Neuron)in.get(ins);

					ConductanceVO g = conf.getSynapseType(neu, inNeu);
					double connectivity = conf.getSynapticConnectivity(g);
					double rand = Math.random();
					
					if( connectivity < rand || connectivity < 0.01 )
						continue;
					
					createSynapticConductance(conf, g, neu, inNeu, 1.0);
				}
			}
		}
		
		// --------------------------------------
		// 4) Ia's/Ib's/II's synapses among the nuclei
		// 		a) Ia over MN 
		// 		b) Ia over IaIn
		//		c) Ia over gII
		//		d) Ib over MN
		// 		e) Ib over IbIn
		//		f) II over MN
		//		g) II over IaIn
		//		h) II over gII
		// --------------------------------------

		// The loop begins with the index of the first regular nucleus
		for(int y = 1; y < neurons.length; y++)
		{
			List iaNucleus = new ArrayList();
			List ibNucleus = new ArrayList();
			List iiNucleus = new ArrayList();

			// Collect neurons of this nucleus and put on the lists
			for(int n = 0; n < neurons[y].length; n++)
			{
				Neuron neu = neurons[y][n];
				
				if( neu.getType().equals( ReMoto.Ia ) )
					iaNucleus.add( neu );
				else if( neu.getType().equals( ReMoto.Ib ) )
					ibNucleus.add( neu );
				else if( neu.getType().equals( ReMoto.II) )
					iiNucleus.add( neu );
			}
			
			// There is no synapse to create
			if( iaNucleus.size() == 0 && ibNucleus.size() == 0 && iiNucleus.size() == 0 )
				continue;

			// For the nucleus y, make synapses of afferences over other cells, in all nucleus
			for(int x = 1; x < neurons.length; x++)
			{
				List mnNucleus = new ArrayList();
				List iaInNucleus = new ArrayList();
				List ibInNucleus = new ArrayList();
				List gIINucleus = new ArrayList();

				// Collect neurons of this nucleus and put on the lists
				for(int n = 0; n < neurons[x].length; n++)
				{
					Neuron neu = neurons[x][n];
					
					if( neu.getCategory().equals( ReMoto.MN ) )
						mnNucleus.add( neu );
					else if( neu.getType().equals( ReMoto.IaIn ) )
						iaInNucleus.add( neu );
					else if( neu.getType().equals( ReMoto.IbIn ) )
						ibInNucleus.add( neu );
					else if( neu.getType().equals( ReMoto.gII ) )
						gIINucleus.add( neu );
				}
				
				// There is no synapse to create
				if( mnNucleus.size() == 0 && iaInNucleus.size() == 0 && ibInNucleus.size() == 0 && gIINucleus.size() == 0 )
					continue;

				for(int ia = 0; ia < iaNucleus.size(); ia++)
				{
					Neuron iafNeu = (Neuron)iaNucleus.get(ia);
		
					// ------------------------
					// a) Synapses: Ia over MNs
					for(int a = 0; a < mnNucleus.size(); a++)
					{
						Neuron mnNeu = (Neuron)mnNucleus.get(a);

						ConductanceVO g = conf.getSynapseType(iafNeu, mnNeu);
						double connectivity = conf.getSynapticConnectivity(g);
						double rand = Math.random();
						
						if( connectivity <= rand )
							continue;

						createSynapticConductance(conf, g, iafNeu, mnNeu, 1);
					}
		
					// ------------------------
					// b) Synapses Ia over IaIn
					for(int a = 0; a < iaInNucleus.size(); a++)
					{
						Neuron iaInNeu = (Neuron)iaInNucleus.get(a);

						ConductanceVO g = conf.getSynapseType(iafNeu, iaInNeu);
						double connectivity = conf.getSynapticConnectivity(g);
						double rand = Math.random();
						
						if( connectivity < rand )
							continue;

						createSynapticConductance(conf, g, iafNeu, iaInNeu, 1);
					}
					
					// ------------------------
					// c) Synapses Ia over gII
					for(int a = 0; a < gIINucleus.size(); a++)
					{
						Neuron gIINeu = (Neuron)gIINucleus.get(a);

						ConductanceVO g = conf.getSynapseType(iafNeu, gIINeu);
						double connectivity = conf.getSynapticConnectivity(g);
						double rand = Math.random();
						
						if( connectivity < rand )
							continue;

						createSynapticConductance(conf, g, iafNeu, gIINeu, 1);
					}
				}


				for(int ib = 0; ib < ibNucleus.size(); ib++)
				{
					Neuron ibNeu = (Neuron)ibNucleus.get(ib);
		
					// ------------------------
					// d) Synapses: Ib over MNs
					for(int mn = 0; mn < mnNucleus.size(); mn++)
					{
						Neuron mnNeu = (Neuron)mnNucleus.get(mn);
						
						ConductanceVO g = conf.getSynapseType(ibNeu, mnNeu);
						double connectivity = conf.getSynapticConnectivity(g);
						double rand = Math.random();
						
						if( connectivity <= rand )
							continue;

						createSynapticConductance(conf, g, ibNeu, mnNeu, 1);
					}

					// ------------------------
					// e) Synapses Ib over IbIn
					for(int ibin = 0; ibin < ibInNucleus.size(); ibin++)
					{
						Neuron ibInNeu = (Neuron)ibInNucleus.get(ibin);
						
						ConductanceVO g = conf.getSynapseType(ibNeu, ibInNeu);
						double connectivity = conf.getSynapticConnectivity(g);
						double rand = Math.random();
						
						if( connectivity < rand )
							continue;

						createSynapticConductance(conf, g, ibNeu, ibInNeu, 1);
					}
				}
				
				for(int ii = 0; ii < iiNucleus.size(); ii++)
				{
					Neuron iiNeu = (Neuron)iiNucleus.get(ii);
		
					// ------------------------
					// f) Synapses: II over MNs
					for(int mn = 0; mn < mnNucleus.size(); mn++)
					{
						Neuron mnNeu = (Neuron)mnNucleus.get(mn);
						
						ConductanceVO g = conf.getSynapseType(iiNeu, mnNeu);
						double connectivity = conf.getSynapticConnectivity(g);
						double rand = Math.random();
						
						if( connectivity <= rand )
							continue;

						createSynapticConductance(conf, g, iiNeu, mnNeu, 1);
					}

					// ------------------------
					// g) Synapses II over IaIn
					for(int iain = 0; iain < iaInNucleus.size(); iain++)
					{
						Neuron iaInNeu = (Neuron)iaInNucleus.get(iain);
						
						ConductanceVO g = conf.getSynapseType(iiNeu, iaInNeu);
						double connectivity = conf.getSynapticConnectivity(g);
						double rand = Math.random();
						
						if( connectivity < rand )
							continue;

						createSynapticConductance(conf, g, iiNeu, iaInNeu, 1);
					}
					
					// ------------------------
					// h) Synapses II over gIIn
					for(int gii = 0; gii < gIINucleus.size(); gii++)
					{
						Neuron gIINeu = (Neuron)gIINucleus.get(gii);
						
						ConductanceVO g = conf.getSynapseType(iiNeu, gIINeu);
						double connectivity = conf.getSynapticConnectivity(g);
						double rand = Math.random();
						
						if( connectivity < rand )
							continue;

						createSynapticConductance(conf, g, iiNeu, gIINeu, 1);
					}
				}
				
			}
		}
	}

		
	public void createSynapticConductance(Configuration conf, ConductanceVO gType, Neuron neuronPre, Neuron neuronPos, double weight)
	{
		if( gType == null )
			return;
		
		if( gType.isActive() == false )
			return;
		
		// ??? teste para saber o numero de sinapses de uma simulacao de larga escala
		//numSinapses++;
		
		// Set dynamics type (depressing, facilitating or none)  ????
		DynamicVO dynamics = conf.getDynamicType(gType.getCdConductanceType(), gType.getCdNucleusPre(), gType.getCdNucleus());
		gType.setDynamics( dynamics );
		
		String cd = neuronPre.getCdNucleus() + neuronPre.getCd();
		
		//System.out.println("cd: " + cd);
		//System.out.println("cd: " + cd + "    gType.getDynamicType(): " + gType.getDynamicType());
		
		// Verify synapse polarity and location (which compartment the synapse is located)
		if( gType.getE() > 0 )
		{
			if( gType.getCompartment() != null && gType.getCompartment().equals( ReMoto.somaCompartment ) )
			{
				((SpinalNeuron)neuronPos).getSomaExcitSynapses().addConductance( gType, weight, cd );
				neuronPre.getTransmittingSynapses().add( ((SpinalNeuron)neuronPos).getSomaExcitSynapses() );
			}
			else
			{
				((SpinalNeuron)neuronPos).getDendExcitSynapses().addConductance( gType, weight, cd );
				neuronPre.getTransmittingSynapses().add( ((SpinalNeuron)neuronPos).getDendExcitSynapses() );
			}
		}
		else
		{
			if( gType.getCompartment() != null && gType.getCompartment().equals( ReMoto.somaCompartment ) )
			{
				((SpinalNeuron)neuronPos).getSomaInhibSynapses().addConductance( gType, weight, cd );
				neuronPre.getTransmittingSynapses().add( ((SpinalNeuron)neuronPos).getSomaInhibSynapses() );
			}
			else
			{
				((SpinalNeuron)neuronPos).getDendInhibSynapses().addConductance( gType, weight, cd );
				neuronPre.getTransmittingSynapses().add( ((SpinalNeuron)neuronPos).getDendInhibSynapses() );
			}
		}
	}


/*
	private void createNeuralColumn(Configuration conf, Neuron neurons[][])
	{
		//1.	Dividiu-se o paralelep�pedo em 3 se��es, a 1a se��o mais rostral, a 2a se��o intermedi�ria e a 3a se��o mais caudal.
		//2.	Na 1a se��o, posicionam-se os MNs LG tipo FF.
		//3.	Na 2a se��o, posicionam-se os MNs LG tipo S e MNs MG-SOL tipo FF
		//4.	Na 3a se��o, posicionam-se os MNs MG-SOL tipo S.
		//5.	MNs LG tipo FR s�o posicionados aleatoriamente na 1a e 2a se��o.
		//6.	MNs MG-SOL tipo FR s�o posicionados aleatoriamente na 2a e 3a se��o.
		//7.	INs LG s�o distribu�dos homogeneamente na 1a e 2a se��o.
		//8.	INs MG-SOL s�o distribu�dos homogeneamente na 2a e 3a se��o.

    	List nuclei = conf.getNuclei();

		int numRegion1 = 0;		// LG
		int numRegion2 = 0;		// LG, MG and SOL
		int numRegion3 = 0;		// MG and SOL
		int numRegion4 = 0;		// TA - S, FR
		int numRegion5 = 0;		// TA - FR, FF
		int maxSliceRegion1 = 0;
		int maxSliceRegion2 = 0;
		int maxSliceRegion3 = 0;
		int maxSliceRegion4 = 0;
		int maxSliceRegion5 = 0;
		
		int cellsPerSlice = (int)conf.getMiscellaneous( ReMoto.cellsPerSlice );
		
    	// ------------------------
	    // Make a loop among nuclei
	    for(int x = 1; x < nuclei.size(); x++)
		{
	    	Nucleus nuc = (Nucleus)nuclei.get(x);
	    	String cdNucleus = nuc.getCd();

			int numS = 0;
			int numFR = 0;
			int numFF = 0;
			int numRc = 0;
			int numInIa = 0;
			int numInIb = 0;
			
			if( conf.getNeuronType(cdNucleus, ReMoto.S) != null && conf.getNeuronType(cdNucleus, ReMoto.S).isActive() )	
				numS = conf.getNeuronType(cdNucleus, ReMoto.S).getQuantity();
			if( conf.getNeuronType(cdNucleus, ReMoto.FR) != null && conf.getNeuronType(cdNucleus, ReMoto.FR).isActive() )	
				numFR = conf.getNeuronType(cdNucleus, ReMoto.FR).getQuantity();
			if( conf.getNeuronType(cdNucleus, ReMoto.FF) != null && conf.getNeuronType(cdNucleus, ReMoto.FF).isActive() )	
				numFF = conf.getNeuronType(cdNucleus, ReMoto.FF).getQuantity();
			if( conf.getNeuronType(cdNucleus, ReMoto.RC) != null && conf.getNeuronType(cdNucleus, ReMoto.RC).isActive() )	
				numRc = conf.getNeuronType(cdNucleus, ReMoto.RC).getQuantity();
			if( conf.getNeuronType(cdNucleus, ReMoto.IaIn) != null && conf.getNeuronType(cdNucleus, ReMoto.IaIn).isActive() )	
				numInIa = conf.getNeuronType(cdNucleus, ReMoto.IaIn).getQuantity();
			if( conf.getNeuronType(cdNucleus, ReMoto.IbIn) != null && conf.getNeuronType(cdNucleus, ReMoto.IbIn).isActive() )	
				numInIb = conf.getNeuronType(cdNucleus, ReMoto.IbIn).getQuantity();
			
			if( cdNucleus.equals( ReMoto.LG ) )
			{
				numRegion1 += numFF + (numFR + numRc + numInIa + numInIb)/2;
				numRegion2 += numS + (numFR + numRc + numInIa + numInIb)/2;
			}
			else if( cdNucleus.equals( ReMoto.MG ) || cdNucleus.equals( ReMoto.SOL ) )
			{
				numRegion2 += numFF + (numFR + numRc + numInIa + numInIb)/2;
				numRegion3 += numS + (numFR + numRc + numInIa + numInIb)/2;
			}
			else if( cdNucleus.equals( ReMoto.TA ) )
			{
				numRegion4 = numFF + (numFR + numRc + numInIa + numInIb)/2;
				numRegion5 = numS + (numFR + numRc + numInIa + numInIb)/2;
			}
		}

		int mod = numRegion1 % cellsPerSlice;
		maxSliceRegion1 = (int)(numRegion1 / cellsPerSlice);
		
		if( mod != 0  )
			maxSliceRegion1++;

		mod = numRegion2 % cellsPerSlice;
		maxSliceRegion2 = maxSliceRegion1 + (int)(numRegion2 / cellsPerSlice);
		
		if( mod != 0  )
			maxSliceRegion2++;

		mod = numRegion3 % cellsPerSlice;
		maxSliceRegion3 = maxSliceRegion2 + (int)(numRegion3 / cellsPerSlice);
		
		if( mod != 0  )
			maxSliceRegion3++;

		// Region 4 and 5 are disconected from the others 
		mod = numRegion4 % cellsPerSlice;
		maxSliceRegion4 = (int)(numRegion4 / cellsPerSlice);
		
		if( mod != 0  )
			maxSliceRegion4++;

		mod = numRegion5 % cellsPerSlice;
		maxSliceRegion5 = maxSliceRegion4 + (int)(numRegion5 / cellsPerSlice);
		
		if( mod != 0  )
			maxSliceRegion5++;


		int sliceRegion1 = 1;
		int sliceRegion2 = maxSliceRegion1 + 1;
		int sliceRegion3 = maxSliceRegion2 + 1;		
		int sliceRegion4 = 1;		
		int sliceRegion5 = maxSliceRegion4 + 1;		

		// The loop begins with the index of the first regular nucleus
		for(int x = 1; x < neurons.length; x++)
		{
			for(int y = 0; y < neurons[x].length; y++)
			{
		    	String cdNucleus = neurons[x][y].getCdNucleus();
		    	String type = neurons[x][y].getType();
		    	
				// Region 1: LG type FF and 50% of LG types FR, IaIn, IbIn and RC
				if( cdNucleus.equals( ReMoto.LG ) && 
					( type.equals( ReMoto.FF ) || (!type.equals( ReMoto.S ) && y % 2 == 0  ) ) )
				{
					neurons[x][y].setSlice( sliceRegion1++ );
					neurons[x][y].setRegion( ReMoto.region1 );

					if( sliceRegion1 > maxSliceRegion1 )
						sliceRegion1 = 1;
				}
				// Region 3: MG-SOL type S and 50% of MG-SOL types FR, IaIn, IbIn and RC
				else if( ( cdNucleus.equals( ReMoto.MG ) || cdNucleus.equals( ReMoto.SOL ) ) &&
						 ( type.equals( ReMoto.S ) || (!type.equals( ReMoto.FF ) && y % 2 == 0  ) ) )
				{
					neurons[x][y].setSlice( sliceRegion3++ );
					neurons[x][y].setRegion( ReMoto.region3 );

					if( sliceRegion3 > maxSliceRegion3 )
						sliceRegion3 = maxSliceRegion2 + 1;
				}
				// Region 2: LG type S, 50% of LG types FR, IaIn, IbIn, RC and MG-SOL type FF and 50% of MG-SOL types FR, IaIn, IbIn and RC
				else if( cdNucleus.equals( ReMoto.LG ) || cdNucleus.equals( ReMoto.MG ) || cdNucleus.equals( ReMoto.SOL ) )
				{
					neurons[x][y].setSlice( sliceRegion2++ );
					neurons[x][y].setRegion( ReMoto.region2 );

					if( sliceRegion2 > maxSliceRegion2 )
						sliceRegion2 = maxSliceRegion1 + 1;
				}
				// Region 4: TA type S and 50% of TA types FR, IaIn, IbIn and RC
				else if( cdNucleus.equals( ReMoto.TA ) && 
						 ( type.equals( ReMoto.FF ) || (!type.equals( ReMoto.S ) && y % 2 == 0  ) ) )
				{
					neurons[x][y].setSlice( sliceRegion4++ );
					neurons[x][y].setRegion( ReMoto.region4 );

					if( sliceRegion4 > maxSliceRegion4 )
						sliceRegion4 = 1;
				}
				// Region 5: TA type FF and 50% of TA types FR, IaIn, IbIn and RC
				else if( cdNucleus.equals( ReMoto.TA ) )
				{
					neurons[x][y].setSlice( sliceRegion5++ );
					neurons[x][y].setRegion( ReMoto.region5 );

					if( sliceRegion5 > maxSliceRegion5 )
						sliceRegion5 = maxSliceRegion4 + 1;
				}
			}
		}
	}
*/

}
