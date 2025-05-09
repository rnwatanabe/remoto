package br.remoto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import br.remoto.model.Neuron.Neuron;
import br.remoto.model.vo.ConductanceVO;
import br.remoto.model.vo.DynamicVO;
import br.remoto.model.vo.JointVO;
import br.remoto.model.vo.MiscellaneousVO;
import br.remoto.model.vo.MotorUnitVO;
import br.remoto.model.vo.MuscleVO;
import br.remoto.model.vo.NerveVO;
import br.remoto.model.vo.Nucleus;
import br.remoto.model.vo.ResultVO;
import br.remoto.model.vo.Sample;
import br.remoto.model.vo.GeneralVO;
import br.remoto.model.vo.NeuronVO;



public class Configuration implements Serializable
{
	private static final long serialVersionUID = 1L;

	private GeneralVO general = new GeneralVO();
	private ResultVO result = new ResultVO();

	private boolean changedConfiguration;
	private int muDistribution;
	
	private List miscellaneous;
	private List joints;
	private List nuclei;
	private List categories;
	private List neuronTypes;
	private List dynamicTypes;
	private List motorunitsTypes;
	private List nerves;
	private List muscles;
	private List descendingCommands;
	private List injectedCurrents;
	private List nerveStimulations;
	
	private List biomechanicalInputs;
	

	private Hashtable synapseTypes;
	private Hashtable marcovTypes;
	private Hashtable muPresetPosition;

	private String cdNucleus;
	private String cdJoint;
	private String cdJointModel;
	private String cdMuscleModel;
	private String cdSpindleModel;
	private String cdGtoModel;
	private String cdEMGModel;
	
	private boolean isActiveAnkleExtensorMNs;
	private boolean isActiveAnkleExtensorINs;
	private boolean isActiveAnkleExtensorSAs;
	private boolean isActiveAnkleFlexorMNs;
	private boolean isActiveAnkleFlexorINs;
	private boolean isActiveAnkleFlexorSAs;
	
	private int numOfSubplots;
	//private int numOfSuperposedPlots;
	
	private List[] nmMuscles;
	private List[] nmSubplots;
	private List[] nmCdNeurons;
	private List[] nmCdSpecification;
	private List[] yLabels;
	private List[] legendLabels;
	

	private String cdNeuron;
	private String plot;
	private String cdType;
	private String cdNeuralType;
	
	private String recruitmentOrderFES;
	
	private double jointAngle;
	private double kneeAngle;
	
	private double jointVelocity;
	private double gammaDynamic;
	private double gammaStatic;
	private double primaryBag1Gain;
	private double primaryBag2AndChainGain;
	private double secondaryBag2AndChainGain;
	
	private double decimationFrequency;
	
	private double initialRecruitmentThresholdIa;
	private double finalRecruitmentThresholdIa;
	private double initialRecruitmentThresholdII;
	private double finalRecruitmentThresholdII;
	private double initialRecruitmentThresholdIb;
	private double finalRecruitmentThresholdIb;
	
	private double percentageOfMVC; 
	
	public Configuration()
	{
		changedConfiguration = true;
		
	}
	
	
	public List getNuclei(String layer) 
	{
		List nuclei = getNuclei();
		List ret = new ArrayList();
		
		for(int x = 0; x < nuclei.size(); x++)
		{
			Nucleus nucleus = (Nucleus)nuclei.get(x);
			
			if( layer == null || nucleus.getLayer().equals( layer ) )
				ret.add( nucleus );
		}
		
		return ret;
	}

	
	public Nucleus getNucleus(String cdNucleus) 
	{
		List nuclei = getNuclei();
		Nucleus nucleus = null;
		
		for(int x = 0; x < nuclei.size(); x++)
		{
			nucleus = (Nucleus)nuclei.get(x);
			
			if( nucleus.getCd().equals( cdNucleus ) )
				return nucleus;
		}
		//System.out.println("nucleus: " + nucleus);
		return nucleus;
	}

	
	public Nucleus[] getArrayNuclei()
	{
		Nucleus[] ret = new Nucleus[ nuclei.size() ]; 
		
		for(int x = 0; x < nuclei.size(); x++)
		{
			ret[x] = (Nucleus)nuclei.get(x);
		}
		
		return ret;
	}
	
	
	public String getCategory(String neuronType) 
	{
		String category = "";
		
		for(int i = 0; i < neuronTypes.size(); i++)
		{
			NeuronVO neu = (NeuronVO)neuronTypes.get(i);
			
			if( neuronType.equals( neu.getType() ) )
				return neu.getCategory();
		}
		
		return category;
	}

	
	public NeuronVO getNeuronType(String cdNucleus, String type) 
	{
		for(int i = 0; i < neuronTypes.size(); i++)
		{
			NeuronVO neu = (NeuronVO)neuronTypes.get(i);
			
			if( neu.getType().equals( type ) && 
				( cdNucleus == null || cdNucleus.equals( neu.getCdNucleus() ) ) )
			{
				return neu;
			}
		}
		
		return null;
	}



		
	public List getNeuronTypes(String cdNucleus, String category) 
		{
			List list = new ArrayList();
			List SList = new ArrayList();
			List FRList = new ArrayList();
			List FFList = new ArrayList();
			List IaInList = new ArrayList();
			List IbInList = new ArrayList();
			List RCList = new ArrayList();
			List gIIList = new ArrayList();
			List IaList = new ArrayList();
			List IbList = new ArrayList();
			List IIList = new ArrayList();
			/*
			System.out.println("neuronTypes: " + neuronTypes);
			
			for(int i = 0; i < neuronTypes.size(); i++)
			{
				NeuronVO neu = (NeuronVO)neuronTypes.get(i);
				
				System.out.println("neu.getCategoryAndType(): " + neu.getCategoryAndType() + " --- neu.getCdNucleus(): " + neu.getCdNucleus());
				
			}
			*/
			if(category.equals("MN")){
				for(int i = 0; i < neuronTypes.size(); i++)
				{
					NeuronVO neu = (NeuronVO)neuronTypes.get(i);
					
					if( ( category == null || neu.getCategory().equals( category ) ) && 
							( cdNucleus == null || cdNucleus.equals( neu.getCdNucleus() ) ) )
					{						
						if(neu.getType().equals( "S" ))	SList.add(neu); 
						else if(neu.getType().equals( "FR" ))	FRList.add(neu); 
						else if(neu.getType().equals( "FF" ))	FFList.add(neu); 
					}
				}
				
				for(int i = 0; i < SList.size(); i++)
				{
					NeuronVO neu = (NeuronVO)SList.get(i);
					list.add(neu); 
				}
				for(int i = 0; i < FRList.size(); i++)
				{
					NeuronVO neu = (NeuronVO)FRList.get(i);
					list.add(neu); 
				}
				for(int i = 0; i < FFList.size(); i++)
				{
					NeuronVO neu = (NeuronVO)FFList.get(i);
					list.add(neu); 
				}
			}
			else if(category.equals("IN")){
				for(int i = 0; i < neuronTypes.size(); i++)
				{
					NeuronVO neu = (NeuronVO)neuronTypes.get(i);
					
					if( ( category == null || neu.getCategory().equals( category ) ) && 
							( cdNucleus == null || cdNucleus.equals( neu.getCdNucleus() ) ) )
					{
						if(neu.getType().equals( "IaIn" ))	IaInList.add(neu); 
						else if(neu.getType().equals( "IbIn" ))	IbInList.add(neu); 
						else if(neu.getType().equals( "RC" ))	RCList.add(neu); 
						else if(neu.getType().equals( "gII" )) gIIList.add(neu);
					}
				}
				for(int i = 0; i < IaInList.size(); i++)
				{
					NeuronVO neu = (NeuronVO)IaInList.get(i);
					list.add(neu); 
				}
				for(int i = 0; i < IbInList.size(); i++)
				{
					NeuronVO neu = (NeuronVO)IbInList.get(i);
					list.add(neu); 
				}
				for(int i = 0; i < RCList.size(); i++)
				{
					NeuronVO neu = (NeuronVO)RCList.get(i);
					list.add(neu); 
				}
				for(int i = 0; i < gIIList.size(); i++)
				{
					NeuronVO neu = (NeuronVO)gIIList.get(i);
					list.add(neu);
				}
			}
			else if(category.equals("AF")){
				for(int i = 0; i < neuronTypes.size(); i++)
				{
					NeuronVO neu = (NeuronVO)neuronTypes.get(i);
					
					if( ( category == null || neu.getCategory().equals( category ) ) && 
						( cdNucleus == null || cdNucleus.equals( neu.getCdNucleus() ) ) )
					{
						if(neu.getType().equals( "Ia" ))	IaList.add(neu); 
						else if(neu.getType().equals( "Ib" ))	IbList.add(neu);
						else if(neu.getType().equals( "II" )) IIList.add(neu);
					}
				}
				for(int i = 0; i < IaList.size(); i++)
				{
					NeuronVO neu = (NeuronVO)IaList.get(i);
					list.add(neu); 
				}
				for(int i = 0; i < IbList.size(); i++)
				{
					NeuronVO neu = (NeuronVO)IbList.get(i);
					list.add(neu); 
				}
				for(int i = 0; i < IIList.size(); i++)
				{
					NeuronVO neu = (NeuronVO)IIList.get(i);
					list.add(neu);					
				}
			}
			else{
				for(int i = 0; i < neuronTypes.size(); i++)
				{
					NeuronVO neu = (NeuronVO)neuronTypes.get(i);
					
					if( ( category == null || neu.getCategory().equals( category ) ) && 
						( cdNucleus == null || cdNucleus.equals( neu.getCdNucleus() ) ) )
					{
						list.add(neu); 
					}
				}
			}
			return list;
		}



	
	public NeuronVO getCompleteNeuronType(String cdNucleus, String type) 
	{
		for(int i = 0; i < neuronTypes.size(); i++)
		{
			NeuronVO neu = (NeuronVO)neuronTypes.get(i);
			
			if( neu.getType().equals( type ) && 
				( cdNucleus == null || cdNucleus.equals( neu.getCdNucleus() ) ) )
			{
				// Markov model
				ConductanceVO gNaM = getMarcovType(cdNucleus, ReMoto.gNaM + "-" + type, ReMoto.excitatory);
				ConductanceVO gNaH = getMarcovType(cdNucleus, ReMoto.gNaH + "-" + type, ReMoto.excitatory);
				ConductanceVO gKN = getMarcovType(cdNucleus,  ReMoto.gKN  + "-" + type, ReMoto.inhibitory);
				ConductanceVO gKQ = getMarcovType(cdNucleus,  ReMoto.gKQ  + "-" + type, ReMoto.inhibitory);
				ConductanceVO gCaP = getMarcovType(cdNucleus,  ReMoto.gCaP  + "-" + type, ReMoto.excitatory);
				
				neu.setGNaMVO( new ConductanceVO( gNaM ) );
				neu.setGNaHVO( new ConductanceVO( gNaH ) );
				neu.setGKNVO( new ConductanceVO( gKN ) );
				neu.setGKQVO( new ConductanceVO( gKQ ) );
				neu.setGCaPVO( new ConductanceVO( gCaP ) );

				return neu;
			}
		}
		
		return null;
	}

	
	public void setListNeuronTypes(List neuronTypes) 
	{
		for(int i = 0; i < neuronTypes.size(); i++)
		{
			NeuronVO neuType = (NeuronVO)neuronTypes.get(i);
			
			for(int j = 0; j < this.neuronTypes.size(); j++)
			{
				NeuronVO neuThis = (NeuronVO)this.neuronTypes.get(j);
				
				if( neuType.getType().equals( neuThis.getType() ) && 
					neuType.getCdNucleus().equals( neuThis.getCdNucleus() ) )
				{
					this.neuronTypes.set(j, neuType);
					break;
				}
			}
		}
	}

	
	public void setQuantityNeuronType(String cdNucleus, String type, int quantity) 
	{
		getNeuronType(cdNucleus, type).setQuantity( quantity );
		getNeuronType(cdNucleus, type).setActive( true );
	}

    
	public MotorUnitVO getMotorUnitType(String cdNucleus, String type) 
	{
		for(int i = 0; i < motorunitsTypes.size(); i++)
		{
			MotorUnitVO fb = (MotorUnitVO)motorunitsTypes.get(i);
			
			if( cdNucleus.equals( fb.getCdNucleus() ) &&
				type.equals( fb.getType() ) )
			{
				return fb;
			}
		}
		
		return null;
	}
	
	
	public List getMotorUnitTypes(String cdNucleus) 
	{
		List list = new ArrayList();
		List Slist = new ArrayList();
		List FRlist = new ArrayList();
		List FFlist = new ArrayList();
		
		
		for(int i = 0; i < motorunitsTypes.size(); i++)
		{
			MotorUnitVO fb = (MotorUnitVO)motorunitsTypes.get(i);
			
			if( cdNucleus.equals( fb.getCdNucleus() ) )
			{
				if(fb.getType().equals( "S" ))	Slist.add(fb); 
				else if(fb.getType().equals( "FR" ))	FRlist.add(fb); 
				else if(fb.getType().equals( "FF" ))	FFlist.add(fb); 
			}
		}
		
		for(int i = 0; i < Slist.size(); i++)
		{
			MotorUnitVO fb = (MotorUnitVO)Slist.get(i);
			list.add(fb); 
		}
		for(int i = 0; i < FRlist.size(); i++)
		{
			MotorUnitVO fb = (MotorUnitVO)FRlist.get(i);
			list.add(fb); 
		}
		for(int i = 0; i < FFlist.size(); i++)
		{
			MotorUnitVO fb = (MotorUnitVO)FFlist.get(i);
			list.add(fb); 
		}
		return list;
	}

    
	public void setListMotorUnitTypes(List muTypes) 
	{
		for(int i = 0; i < muTypes.size(); i++)
		{
			MotorUnitVO mu = (MotorUnitVO)muTypes.get(i);
			
			for(int j = 0; j < this.motorunitsTypes.size(); j++)
			{
				MotorUnitVO muThis = (MotorUnitVO)this.motorunitsTypes.get(j);
				
				if( mu.getType().equals( muThis.getType() ) && 
					mu.getCdNucleus().equals( muThis.getCdNucleus() ) )
				{
					this.motorunitsTypes.set(j, mu);
					break;
				}
			}
		}
	}

	
	public void setEMGParameters(List muTypes) 
	{
		for(int i = 0; i < muTypes.size(); i++)
		{
			MotorUnitVO mu = (MotorUnitVO)muTypes.get(i);
			
			for(int j = 0; j < this.motorunitsTypes.size(); j++)
			{
				MotorUnitVO muThis = (MotorUnitVO)this.motorunitsTypes.get(j);
				
				if( mu.getType().equals( muThis.getType() ) && 
					mu.getCdNucleus().equals( muThis.getCdNucleus() ) )
				{
					muThis.setAmpEMG1(mu.getAmpEMG1());
					muThis.setAmpEMG2(mu.getAmpEMG2());
					muThis.setLambdaEMG1(mu.getLambdaEMG1());
					muThis.setLambdaEMG2(mu.getLambdaEMG2());
					
					this.motorunitsTypes.set(j, muThis);
					break;
				}
			}
		}
	}
	
	
	public void setTwitchParameters(List muTypes) 
	{
		for(int i = 0; i < muTypes.size(); i++)
		{
			MotorUnitVO mu = (MotorUnitVO)muTypes.get(i);
			
			for(int j = 0; j < this.motorunitsTypes.size(); j++)
			{
				MotorUnitVO muThis = (MotorUnitVO)this.motorunitsTypes.get(j);
				
				if( mu.getType().equals( muThis.getType() ) && 
					mu.getCdNucleus().equals( muThis.getCdNucleus() ) )
				{
					mu.setAmpEMG1(muThis.getAmpEMG1());
					mu.setAmpEMG2(muThis.getAmpEMG2());
					mu.setLambdaEMG1(muThis.getLambdaEMG1());
					mu.setLambdaEMG2(muThis.getLambdaEMG2());
					
					this.motorunitsTypes.set(j, mu);
					break;
				}
			}
		}
	}
	
	
	public ConductanceVO getSynapseType(Neuron neuronPre, Neuron neuronPos) 
	{
		ConductanceVO g = (ConductanceVO)synapseTypes.get( neuronPre.getCdNucleus() + 
														   neuronPos.getCdNucleus() +  
														   neuronPre.getCategory() + " " + neuronPre.getType() + 
														   "-" +
														   neuronPos.getCategory() + " " + neuronPos.getType() );
		
		return g;
	}

	
	public ConductanceVO getSynapseType(String cdNucleusPre, String cdNucleus, String cdConductanceType)
	{
		ConductanceVO g = (ConductanceVO)synapseTypes.get( cdNucleusPre + cdNucleus + cdConductanceType );
		
		return g;
	}

	
	public List getSynapticConductances(String cdNucleus, String pre, String pos) 
	{
		List list = new ArrayList();
		
		Hashtable active = new Hashtable();

		// Verify active neuronTypes 
		for(int i = 0; i < neuronTypes.size(); i++)
		{
			NeuronVO nt = (NeuronVO)neuronTypes.get(i);
			
			active.put( nt.getCategoryType() + " " + nt.getCdNucleus(), new Boolean(nt.isActive()) ); 
		}
		
		Iterator it = synapseTypes.values().iterator();

		while( it.hasNext() )
		{
			ConductanceVO g = (ConductanceVO)it.next();
			
			if( ( pre.equals( ReMoto.ALL ) || pre.equals( ReMoto.ACTIVE ) ) &&
				( g.getCdConductanceType().lastIndexOf( pos ) > 0  || pos.equals( ReMoto.ALL ) || pos.equals( ReMoto.ACTIVE ) ) &&
				( cdNucleus == null || cdNucleus.equals( g.getCdNucleus() ) ) )
			{
        		Boolean statePre = (Boolean)active.get(g.getPreSimple() + " " + g.getCdNucleusPre());
        		Boolean statePos = (Boolean)active.get(g.getPos() + " " + g.getCdNucleus());
				
        		if( ( pre.equals( ReMoto.ALL ) || (pre.equals( ReMoto.ACTIVE ) && statePre != null && statePre.equals(Boolean.TRUE)) ) &&
					( g.getPos().indexOf( pos ) == 0 || pos.equals( ReMoto.ALL ) || (pos.equals( ReMoto.ACTIVE ) && statePos != null && statePos.equals(Boolean.TRUE)) ) )
				{
        			// Set synaptic dynamics type (depressing, facilitating or none)
        			DynamicVO vo = getDynamicType(g.getCdConductanceType(), g.getCdNucleusPre(), g.getCdNucleus());
       				g.setDynamics( vo );
        			
        			list.add(g);
				}
			}
		}
		
		Collections.sort(list);
		
		return list;
	}
	
	
	public void setSynapseTypes(List conductances)
	{
		if( synapseTypes == null )
			synapseTypes = new Hashtable();
		
		for(int i = 0; i < conductances.size(); i++)
		{
			ConductanceVO g = (ConductanceVO)conductances.get(i);
			
			synapseTypes.put(g.getCdNucleusPre() + g.getCdNucleus() + g.getCdConductanceType(), g);
		}
	}
	

	public void setSynapseType(String cdConductance, ConductanceVO g)
	{
		synapseTypes.put(g.getCdNucleusPre() + g.getCdNucleus() + g.getCdConductanceType(), g);
	}

	
	public double getSynapticConnectivity(ConductanceVO g) 
	{
		if( g != null )
			return g.getConnectivity() / 100.0;
		else
			return 0;
	}
	
	
	public List getSynapticDynamics(String cdNucleus) 
	{
		List list = new ArrayList();
		
		Iterator it = marcovTypes.values().iterator();

		while( it.hasNext() )
		{
			ConductanceVO g = (ConductanceVO)it.next();
			
			if( ( cdNucleus == null || cdNucleus.equals( "" ) || cdNucleus.equals( g.getCdNucleus() ) ) &&
				g.getCdConductanceType().indexOf( "g" ) != 0 )
			{
				list.add(g);
			}
		}
		
		Collections.sort(list);
		
		return list;
	}
	

	public ConductanceVO getMarcovType(String cdNucleus, String type, String polarityType)
	{
		ConductanceVO g = (ConductanceVO)marcovTypes.get( cdNucleus + type + polarityType );
		
		return g;
	}

	
	public void setListMarkovTypes(List conductances)
	{
		for(int i = 0; i < conductances.size(); i++)
		{
			ConductanceVO g = (ConductanceVO)conductances.get(i);
			
			marcovTypes.put(g.getCdNucleus() + g.getCdConductanceType() + g.getPolarity(), g);
		}
	}

	
	public List getNeuronIonicConductances(String cdNucleus, String cdNeuronType) 
	{
		List list = new ArrayList();
		
		Iterator it = marcovTypes.values().iterator();

		while( it.hasNext() )
		{
			ConductanceVO g = (ConductanceVO)it.next();
			
			if( g.getCdNucleus().equals( cdNucleus ) && 
				g.getCdConductanceType().indexOf( "-" + cdNeuronType ) > 0 )
			{
				list.add(g);
			}
		}
		
		return list;
	}
	
	
	public void setNeuronIonicConductances(List conductances)
	{
		for(int i = 0; i < conductances.size(); i++)
		{
			ConductanceVO g = (ConductanceVO)conductances.get(i);
			
			marcovTypes.put(g.getCdNucleus() + g.getCdConductanceType() + g.getPolarity(), g);
		}
	}

	
	public List getInjectedCurrents(String cdNucleus)
	{
		List list = new ArrayList();
		
		for(int i = 0; i < injectedCurrents.size(); i++)
		{
			Current current = (Current)injectedCurrents.get(i); 
			
			if( cdNucleus.equals( current.getCdNucleus() ) )
			{
				list.add( current );
			}
		}
		
		return list;
	}
	
	
	public Current getInjectedCurrent(String cdNucleus, String type)
	{
		for(int i = 0; i < injectedCurrents.size(); i++)
		{
			Current current = (Current)injectedCurrents.get(i); 
			
			if( cdNucleus.equals( current.getCdNucleus() ) && 
				type.equals( current.getCdNeuronType() ) )
			{
				return current;
			}
		}
		
		return null;
	}
	
	
	public void setListInjectedCurrents(List listCurrents) 
	{
		for(int i = 0; i < listCurrents.size(); i++)
		{
			Current current = (Current)listCurrents.get(i);
			
			for(int j = 0; j < injectedCurrents.size(); j++)
			{
				Current currentThis = (Current)injectedCurrents.get(j);
				
				if( current.getCdNeuronType().equals( currentThis.getCdNeuronType() ) && 
					current.getCdNucleus().equals( currentThis.getCdNucleus() ) )
				{
					injectedCurrents.set(j, current);
					break;
				}
			}
		}
	}

	
	
	public List getBiomechanicalInputs(String cdNucleus)
	{
		List list = new ArrayList();
		
		for(int i = 0; i < biomechanicalInputs.size(); i++)
		{
			BiomechanicalInput biomechanicalInput = (BiomechanicalInput)biomechanicalInputs.get(i); 
			
			if( cdNucleus.equals( biomechanicalInput.getCdNucleus() ) )
			{
				list.add( biomechanicalInput );
			}
		}
		
		return list;
	}
	
	
	public BiomechanicalInput getBiomechanicalInput(String cdNucleus, String type)
	{
		for(int i = 0; i < biomechanicalInputs.size(); i++)
		{
			BiomechanicalInput biomechanicalInput = (BiomechanicalInput)biomechanicalInputs.get(i); 
			
			if( cdNucleus.equals( biomechanicalInput.getCdNucleus() ) && 
				type.equals( biomechanicalInput.getCdNeuronType() ) )
			{
				return biomechanicalInput;
			}
		}
		
		return null;
	}
	
	
	public void setListBiomechanicalInputs(List listBiomechanicalInputs) 
	{
		for(int i = 0; i < listBiomechanicalInputs.size(); i++)
		{
			BiomechanicalInput biomechanicalInput = (BiomechanicalInput)listBiomechanicalInputs.get(i);
			
			for(int j = 0; j < biomechanicalInputs.size(); j++)
			{
				BiomechanicalInput biomechanicalInputThis = (BiomechanicalInput)biomechanicalInputs.get(j);
				
				if( biomechanicalInput.getCdNeuronType().equals( biomechanicalInputThis.getCdNeuronType() ) && 
						biomechanicalInput.getCdNucleus().equals( biomechanicalInputThis.getCdNucleus() ) )
				{
					biomechanicalInputs.set(j, biomechanicalInput);
					break;
				}
			}
		}
	}
	
	
	
    
	public DynamicVO getDynamicType( String cdConductanceType, String cdNucleusPre, String cdNucleus )
	{
		
		DynamicVO vo = null;
		
		for(int i = 0; i < dynamicTypes.size(); i++)
		{
			vo = (DynamicVO)dynamicTypes.get(i);
			
			/*
			System.out.println("in Configuration.java: 		at getDynamicType:		vo.getCdConductanceType(): " + vo.getCdConductanceType() +
																				"   vo.getDynamicType(): " + vo.getDynamicType());
			*/
			
			
			if( vo.getCdConductanceType().equals( cdConductanceType ) &&
				vo.getCdNucleusPre().equals( cdNucleusPre ) &&  
				vo.getCdNucleus().equals( cdNucleus )  )
			{
				return vo;
			}
			
		}
		
		// Code added by Vitor Chaud in March 28th, 2014
		// The following code was added in order to avoid mistakes associated with problems fetching information from the DB
			
		vo.setCdConductanceType(cdConductanceType);
		vo.setCdNucleus(cdNucleus);
		vo.setCdNucleusPre(cdNucleusPre);
		vo.setDynamicType(ReMoto.noDynamics);
		
		return vo;
	}
	
	
	public void setDynamicType(DynamicVO vo)
	{
		boolean found = false;
		
		for(int i = 0; i < dynamicTypes.size(); i++)
		{
			DynamicVO voList = (DynamicVO)dynamicTypes.get(i);
			
			if( vo.getCdConductanceType().equals( voList.getCdConductanceType() ) &&
				vo.getCdNucleusPre().equals( voList.getCdNucleusPre() ) &&  
				vo.getCdNucleus().equals( voList.getCdNucleus() )  )
			{
				dynamicTypes.set(i, vo);
				found = true;
				break;
			}
		}
		
		if( found == false )
		{
			dynamicTypes.add(vo);
		}
	}
	
	
	public NerveVO getNerve(String cdNerve) 
	{
		NerveVO ret = null;
		
		//System.out.println("a");
		
		for(int i = 0; i < nerves.size(); i++)
		{
			
			//System.out.println("b");
			
			ret = (NerveVO)nerves.get(i);
			
			//System.out.println("c");
			
			if( ret.getCdNerve().equals( cdNerve ) )
			{
				//System.out.println("d");
				
				return ret;
			}
			
			//System.out.println("e");
		}

		//System.out.println("f");
		
		return ret;
	}
	
	
	
	
	
	public ModulatingSignal getDescendingCommand( String cdNeuronType, String cdNucleus )
	{
		ModulatingSignal signal = null;
		
		for(int i =0; i < descendingCommands.size(); i++)
		{
			ModulatingSignal listSignal = (ModulatingSignal)descendingCommands.get(i);
			
			if( listSignal.getCdNeuronType().equals( cdNeuronType ) && 
				listSignal.getCdNucleus().equals( cdNucleus ) )
			{
				signal = listSignal;
				break;
			}
		}
		
		if( signal == null )
		{
			signal = new ModulatingSignal(cdNeuronType, cdNucleus);
		}
		
		return signal;
	}

	
	public double getMiscellaneous(String property)
	{
		for(int i = 0; i < miscellaneous.size(); i++)
		{
			MiscellaneousVO vo = (MiscellaneousVO)miscellaneous.get(i);
			
			if( vo.getProperty().equals( property ) )
			{
				return vo.getValue();
			}
		}

		return 0;
	}
	

	public void setMiscellaneous(String property, double value)
	{
		for(int i = 0; i < miscellaneous.size(); i++)
		{
			MiscellaneousVO vo = (MiscellaneousVO)miscellaneous.get(i);
			
			if( vo.getProperty().equals( property ) )
			{
				vo.setValue( value );
			}
		}
	}
	
	
	
	

		
	public List getListSamples() 
	{
		List list = new ArrayList();
		
		list.add( new Sample( Double.toString(1.0/getStep()), 1.0/getStep() ) );
		list.add( new Sample( Double.toString(0.4/getStep()), 0.4/getStep() ) );
		list.add( new Sample( Double.toString(0.2/getStep()), 0.2/getStep() ) );
		
		return list;
	}
	
	
	public void placeMns(String cdNucleus)
	{
		

	}

	
	// ------------------------
	// -- Simulation properties
	
    public GeneralVO getGeneral() {
		return general;
	}
    
	public void setGeneral(GeneralVO vo) 
	{
		this.general = vo;
	}
	
	public double getTFin() {
		return general.getTFin();
	}
	
	public void setTFin(double fin) 
	{
		general.setTFin( fin );
	}
	
	//testing
	public void setStep(double step) 
	{
		general.setStep( step );
	}
	
	public double getStep()
	{
		//return getMiscellaneous( ReMoto.step );
		//testing
		return general.getStep();
	}

	public String getDescription() {
		return general.getDescription();
	}
	
	public void setDescription(String description) 
	{
		general.setDescription( description );
	}
	
	public String getName() {
		return general.getName();
	}
	
	public void setName(String name) 
	{
		general.setName( name );
	}
	
	public int getId() {
		return general.getId();
	}
	
	public void setId(int idConfiguration) 
	{
		general.setId( idConfiguration );
	}
	
	public String getCdUser() {
		return general.getCdUser();
	}
	
	public void setCdUser(String cdUser) 
	{
		general.setCdUser( cdUser );
	}
	
	
	// Always true: no more used
	public boolean isMerge() {
		return true;
	}
	
	// Always true: no more used
	public void setMerge(boolean merge) 
	{
		general.setMerge( true );
	}
	
	
	public boolean isKeepProperties() {
		return general.isKeepProperties();
	}

	public void setKeepProperties(boolean keepProperties) {
		general.setKeepProperties( keepProperties );
	}

	public boolean isStoreResults() {
		return general.isStoreResults();
	}

	public void setStoreResults(boolean store) {
		general.setStoreResults( store );
	}

	public boolean isStoreSignals() {
		return general.isStoreSignals();
	}

	public void setStoreSignals(boolean store) {
		general.setStoreSignals( store );
	}


	// --------------------
	// -- Common properties
	
	public List getNeuronTypes() {
		return neuronTypes;
	}
	
	public void setNeuronTypes(List neurons) 
	{
		this.neuronTypes = neurons;
	}
	
	public List getMotorunitsTypes() {
		return motorunitsTypes;
	}
	
	public void setMotorunitsTypes(List muTypes) 
	{
		this.motorunitsTypes = muTypes;
	}

	public List getCategories() {
		return categories;
	}

	public void setCategories(List categories) 
	{
		this.categories = categories;
	}

	public ResultVO getResult() {
		return result;
	}
	
	public void setResult(ResultVO result) 
	{
		this.result = result;
	}

	public List getNuclei() {
		return nuclei;
	}
	
	public void setNuclei(List nucleus) 
	{
		this.nuclei = nucleus;
	}

	public List getNerves() {
		
		NerveVO vo = null;
		
		ArrayList jointNerves = new ArrayList();
		
		for(int i = 0; i < nerves.size(); i++)
		{
			vo = (NerveVO)nerves.get(i);
			
			if( vo.getCdJoint().equals( cdJoint ) )
			{
				jointNerves.add(vo);
			}
		}
		return jointNerves;
	}
	
	public List getAllActiveSynapticConductances() 
	{
		List list = new ArrayList();
		
		String pre = ReMoto.ACTIVE;
		String pos = ReMoto.ACTIVE;
		
		Hashtable active = new Hashtable();

		// Verify active neuronTypes 
		for(int i = 0; i < neuronTypes.size(); i++)
		{
			NeuronVO nt = (NeuronVO)neuronTypes.get(i);
			
			active.put( nt.getCategoryType() + " " + nt.getCdNucleus(), new Boolean(nt.isActive()) ); 
		}
		
		Iterator it = synapseTypes.values().iterator();

		while( it.hasNext() )
		{
			ConductanceVO g = (ConductanceVO)it.next();
			
			if( ( pre.equals( ReMoto.ALL ) || pre.equals( ReMoto.ACTIVE ) ) &&
				( g.getCdConductanceType().lastIndexOf( pos ) > 0  || pos.equals( ReMoto.ALL ) || pos.equals( ReMoto.ACTIVE ) ))
			{
        		Boolean statePre = (Boolean)active.get(g.getPreSimple() + " " + g.getCdNucleusPre());
        		Boolean statePos = (Boolean)active.get(g.getPos() + " " + g.getCdNucleus());
				
        		if( ( pre.equals( ReMoto.ALL ) || (pre.equals( ReMoto.ACTIVE ) && statePre != null && statePre.equals(Boolean.TRUE)) ) &&
					( g.getPos().indexOf( pos ) == 0 || pos.equals( ReMoto.ALL ) || (pos.equals( ReMoto.ACTIVE ) && statePos != null && statePos.equals(Boolean.TRUE)) ) )
				{
        			// Set synaptic dynamics type (depressing, facilitating or none)
        			DynamicVO vo = getDynamicType(g.getCdConductanceType(), g.getCdNucleusPre(), g.getCdNucleus());
       				g.setDynamics( vo );
        			
        			list.add(g);
				}
			}
		}
		
		Collections.sort(list);
		
		return list;
	}
	
	public List getAllNerves() {
		return nerves;
	}

	public void setNerves(List nerves) 
	{
		this.nerves = nerves;
	}

	public List getInjectedCurrents() {
		return injectedCurrents;
	}
	
	public void setInjectedCurrents(List injectedCurrents) 
	{
		this.injectedCurrents = injectedCurrents;
	}

	public List getNerveStimulations() {
		return nerveStimulations;
	}
	
	public void setNerveStimulations(List nerveStimulations) 
	{
		this.nerveStimulations = nerveStimulations;
	}
	
	public List getDescendingCommands() {
		return descendingCommands;
	}
	
	public void setDescendingCommands(List modulations) 
	{
		this.descendingCommands = modulations;
	}

	public List getDynamicTypes() 
	{
		return dynamicTypes;
	}

	public void setDynamicTypes(List dynamicTypes) 
	{
		this.dynamicTypes = dynamicTypes;
	}

	public Hashtable getSynapseTypes() {
		return synapseTypes;
	}
	
	public void setSynapseTypes(Hashtable conductanceTypes) 
	{
		this.synapseTypes = conductanceTypes;
	}

	public Hashtable getMarcovTypes() {
		return marcovTypes;
	}
	
	public void setMarcovTypes(Hashtable markovTypes) 
	{
		this.marcovTypes = markovTypes;
	}

	public List getMiscellaneous() {
		return miscellaneous;
	}
	
	public void setMiscellaneous(List miscellaneous) 
	{
		this.miscellaneous = miscellaneous;
	}


	public boolean isChangedConfiguration() {
		return changedConfiguration;
	}


	public void setChangedConfiguration(boolean configurationChanged) {
		this.changedConfiguration = configurationChanged;
	}


	public int getMuDistribution() {
		return muDistribution;
	}


	public void setMuDistribution(int muDistribution) {
		this.muDistribution = muDistribution;
	}


	public Hashtable getMuPresetPosition() {
		return muPresetPosition;
	}


	public void setMuPresetPosition(Hashtable muPresetPosition) {
		this.muPresetPosition = muPresetPosition;
	}
	
	public String getCdNucleus() {
		return this.cdNucleus;
	}
	
	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}


	public String getCdJoint() {
		return cdJoint;
	}


	public void setCdJoint(String cdJoint) {
		
		System.out.println(" Setting cdJoint ");
		
		this.cdJoint = cdJoint;
	}

	
	public String getCdMuscleModel() {
		return cdMuscleModel;
	}


	public void setCdMuscleModel(String cdMuscleModel) {
		this.cdMuscleModel = cdMuscleModel;
	}

	public boolean isActiveAnkleExtensorMNs() {
		return isActiveAnkleExtensorMNs;
	}


	public void setActiveAnkleExtensorMNs(boolean isActiveAnkleExtensorMNs) {
		this.isActiveAnkleExtensorMNs = isActiveAnkleExtensorMNs;
	}


	public boolean isActiveAnkleExtensorINs() {
		return isActiveAnkleExtensorINs;
	}


	public void setActiveAnkleExtensorINs(boolean isActiveAnkleExtensorINs) {
		this.isActiveAnkleExtensorINs = isActiveAnkleExtensorINs;
	}


	public boolean isActiveAnkleExtensorSAs() {
		return isActiveAnkleExtensorSAs;
	}


	public void setActiveAnkleExtensorSAs(boolean isActiveAnkleExtensorSAs) {
		this.isActiveAnkleExtensorSAs = isActiveAnkleExtensorSAs;
	}


	public boolean isActiveAnkleFlexorMNs() {
		return isActiveAnkleFlexorMNs;
	}


	public void setActiveAnkleFlexorMNs(boolean isActiveAnkleFlexorMNs) {
		this.isActiveAnkleFlexorMNs = isActiveAnkleFlexorMNs;
	}


	public boolean isActiveAnkleFlexorINs() {
		return isActiveAnkleFlexorINs;
	}


	public void setActiveAnkleFlexorINs(boolean isActiveAnkleFlexorINs) {
		this.isActiveAnkleFlexorINs = isActiveAnkleFlexorINs;
	}


	public boolean isActiveAnkleFlexorSAs() {
		return isActiveAnkleFlexorSAs;
	}


	public void setActiveAnkleFlexorSAs(boolean isActiveAnkleFlexorSAs) {
		this.isActiveAnkleFlexorSAs = isActiveAnkleFlexorSAs;
	}


	public List getBiomechanicalInputs() {
		return biomechanicalInputs;
	}


	public void setBiomechanicalInputs(List biomechanicalInputs) {
		this.biomechanicalInputs = biomechanicalInputs;
	}


	public String getCdSpindleModel() {
		return cdSpindleModel;
	}


	public void setCdSpindleModel(String cdSpindleModel) {
		this.cdSpindleModel = cdSpindleModel;
	}


	public String getCdGtoModel() {
		return cdGtoModel;
	}


	public void setCdGtoModel(String cdGtoModel) {
		this.cdGtoModel = cdGtoModel;
	}


	public String getCdJointModel() {
		return cdJointModel;
	}


	public void setCdJointModel(String cdJointModel) {
		this.cdJointModel = cdJointModel;
	}


	public String getCdEMGModel() {
		return cdEMGModel;
	}


	public void setCdEMGModel(String cdEMGModel) {
		this.cdEMGModel = cdEMGModel;
	}



	/*
	public String[][] getCdPlots() {
		return cdPlots;
	}

	public void setCdPlots(String[][] cdPlots) {
		this.cdPlots = cdPlots;
	}
	*/
	
	public int getNumOfSubplots() {
		return numOfSubplots;
	}

	public void setNumOfSubplots(int numOfSubplots) {
		this.numOfSubplots = numOfSubplots;
	}
	/*
	public int getNumOfSuperposedPlots() {
		return numOfSuperposedPlots;
	}

	public void setNumOfSuperposedPlots(int numOfSuperposedPlots) {
		this.numOfSuperposedPlots = numOfSuperposedPlots;
	}
	*/


	public String getCdNeuron() {
		return cdNeuron;
	}

	public void setCdNeuron(String cdNeuron) {
		this.cdNeuron = cdNeuron;
	}
	
	public List[] getNmSubplots() {
		return nmSubplots;
	}

	public void setNmSubplots(List[] nmSubplots) {
		this.nmSubplots = nmSubplots;
	}


	public List[] getNmCdNeurons() {
		return nmCdNeurons;
	}


	public void setNmCdNeurons(List[] nmCdNeurons) {
		this.nmCdNeurons = nmCdNeurons;
	}



	public List[] getyLabels() {
		return yLabels;
	}


	public void setyLabels(List[] yLabels) {
		this.yLabels = yLabels;
	}


	public List[] getLegendLabels() {
		return legendLabels;
	}


	public void setLegendLabels(List[] legendLabels) {
		this.legendLabels = legendLabels;
	}


	public String getPlot() {
		return plot;
	}


	public void setPlot(String plot) {
		this.plot = plot;
	}


	public String getCdType() {
		return cdType;
	}


	public void setCdType(String cdType) {
		this.cdType = cdType;
	}


	public String getCdNeuralType() {
		return cdNeuralType;
	}


	public void setCdNeuralType(String cdNeuralType) {
		this.cdNeuralType = cdNeuralType;
	}


	public List[] getNmCdSpecification() {
		return nmCdSpecification;
	}


	public void setNmCdSpecification(List[] nmCdSpecification) {
		this.nmCdSpecification = nmCdSpecification;
	}


	public List getMuscles() {
		return muscles;
	}


	public void setMuscles(List muscles) {
		this.muscles = muscles;
	}
	
	public MuscleVO getMuscle(String cd, String modelType){
		
		MuscleVO vo;
		
		for(int i = 0; i < muscles.size(); i++){
			vo = (MuscleVO) muscles.get(i);
			
			if(vo.getCdMuscle().equals(cd)){
				return vo;
			}
		}
		
		return null;
	}
	
	public void setMuscle(String cd, String modelType, MuscleVO newVO){
		
		for(int i = 0; i < muscles.size(); i++){
			MuscleVO vo = (MuscleVO) muscles.get(i);
			
			if(vo.getCdMuscle().equals(cd)){
				muscles.set(i, newVO);
			}
		}
	}


	public String getRecruitmentOrderFES() {
		return recruitmentOrderFES;
	}


	public void setRecruitmentOrderFES(String recruitmentOrderFES) {
		this.recruitmentOrderFES = recruitmentOrderFES;
	}


	public List getJoints() {
		return joints;
	}


	public void setJoints(List joints) {
		this.joints = joints;
	}
	
	public JointVO getJointVO (){
		
		for(int i = 0; i < joints.size(); i++){
			
			JointVO vo = (JointVO) joints.get(i);
			if(vo.getCd().equals(cdJoint)) return vo;
		}
		
		/*
		JointVO teste = new JointVO();
		teste.setCd("ankle");
		teste.setName("Ankle");
		teste.setInd(0);
		teste.setNumMotorNuclei(4);
		teste.setNumNerves(2);
		teste.setNumNuclei(6);
		
		return teste;
		*/
		return null;
	}


	public List[] getNmMuscles() {
		return nmMuscles;
	}


	public void setNmMuscles(List[] nmMuscles) {
		this.nmMuscles = nmMuscles;
	}


	public double getJointAngle() {
		return jointAngle;
	}


	public void setJointAngle(double d) {
		System.out.println("setJointAngle: " + d);
		this.jointAngle = d;
	}


	public double getKneeAngle() {
		return kneeAngle;
	}


	public void setKneeAngle(double d) {
		this.kneeAngle = d;
	}

	public double getpercentageOfMVC() {
		return percentageOfMVC;
	}


	public void setpercentageOfMVC(double percentageOfMVC) {
		this.percentageOfMVC = percentageOfMVC;
	}

	public double getJointVelocity() {
		return jointVelocity;
	}


	public void setJointVelocity(double jointVelocity) {
		this.jointVelocity = jointVelocity;
	}


	public double getGammaDynamic() {
		return gammaDynamic;
	}


	public void setGammaDynamic(double gammaDynamic) {
		this.gammaDynamic = gammaDynamic;
	}


	public double getGammaStatic() {
		return gammaStatic;
	}


	public void setGammaStatic(double gammaStatic) {
		this.gammaStatic = gammaStatic;
	}


	public double getPrimaryBag1Gain() {
		return primaryBag1Gain;
	}


	public void setPrimaryBag1Gain(double primaryBag1Gain) {
		this.primaryBag1Gain = primaryBag1Gain;
	}


	public double getPrimaryBag2AndChainGain() {
		return primaryBag2AndChainGain;
	}


	public void setPrimaryBag2AndChainGain(double primaryBag2AndChainGain) {
		this.primaryBag2AndChainGain = primaryBag2AndChainGain;
	}
	
	public double getSecondaryBag2AndChainGain() {
		return secondaryBag2AndChainGain;
	}


	public void setSecondaryBag2AndChainGain(double secondaryBag2AndChainGain) {
		this.secondaryBag2AndChainGain = secondaryBag2AndChainGain;
	}

	public double getDecimationFrequency() {
		return decimationFrequency;
	}


	public void setDecimationFrequency(double decimationFrequency) {
		this.decimationFrequency = decimationFrequency;
	}


	public double getInitialRecruitmentThresholdIa() {
		return initialRecruitmentThresholdIa;
	}


	public void setInitialRecruitmentThresholdIa(
			double initialRecruitmentThresholdIa) {
		this.initialRecruitmentThresholdIa = initialRecruitmentThresholdIa;
	}


	public double getFinalRecruitmentThresholdIa() {
		return finalRecruitmentThresholdIa;
	}


	public void setFinalRecruitmentThresholdIa(double finalRecruitmentThresholdIa) {
		this.finalRecruitmentThresholdIa = finalRecruitmentThresholdIa;
	}


	public double getInitialRecruitmentThresholdII() {
		return initialRecruitmentThresholdII;
	}


	public void setInitialRecruitmentThresholdII(
			double initialRecruitmentThresholdII) {
		this.initialRecruitmentThresholdII = initialRecruitmentThresholdII;
	}


	public double getFinalRecruitmentThresholdII() {
		return finalRecruitmentThresholdII;
	}


	public void setFinalRecruitmentThresholdII(double finalRecruitmentThresholdII) {
		this.finalRecruitmentThresholdII = finalRecruitmentThresholdII;
	}


	public double getInitialRecruitmentThresholdIb() {
		return initialRecruitmentThresholdIb;
	}


	public void setInitialRecruitmentThresholdIb(
			double initialRecruitmentThresholdIb) {
		this.initialRecruitmentThresholdIb = initialRecruitmentThresholdIb;
	}


	public double getFinalRecruitmentThresholdIb() {
		return finalRecruitmentThresholdIb;
	}


	public void setFinalRecruitmentThresholdIb(double finalRecruitmentThresholdIb) {
		this.finalRecruitmentThresholdIb = finalRecruitmentThresholdIb;
	}
	
	
}