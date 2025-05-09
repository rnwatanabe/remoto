package br.remoto.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import br.remoto.model.BiomechanicalInput;
import br.remoto.model.Configuration;
import br.remoto.model.Current;
import br.remoto.model.ModulatingSignal;
import br.remoto.model.Nerve;
import br.remoto.model.ReMoto;
import br.remoto.model.vo.JointVO;
import br.remoto.model.vo.MiscellaneousVO;
import br.remoto.model.vo.MuscleVO;
import br.remoto.model.vo.NerveVO;
import br.remoto.model.vo.Category;
import br.remoto.model.vo.ConductanceVO;
import br.remoto.model.vo.DynamicVO;
import br.remoto.model.vo.MotorUnitVO;
import br.remoto.model.vo.Nucleus;
import br.remoto.model.vo.GeneralVO;
import br.remoto.model.vo.NeuronVO;
import br.remoto.model.vo.User;
import br.remoto.util.Point;


public class ConfigurationDAO extends BasicDAO
{
	private Configuration conf;
	
	public Configuration getConfiguration(int idConfiguration)
	{
		//System.out.println("ConfigurationDAO.getConfiguration()");
		
		connect();
    	
		conf = new Configuration();
		
		conf.setJoints( loadJoints() );
    	conf.setNuclei( loadNucleus() );
    	conf.setCategories( loadCategories() );
    	conf.setGeneral( loadConfiguration(idConfiguration) );
    	conf.setNerves( loadNerves(idConfiguration) );
    	conf.setDescendingCommands( loadDescendingCommands(idConfiguration) );
    	conf.setInjectedCurrents( loadInjectedCurrents(idConfiguration) );
    	conf.setSynapseTypes( loadSynapseTypes(idConfiguration) );
    	conf.setMarcovTypes( loadMarcovTypes(idConfiguration) );
    	conf.setDynamicTypes( loadDynamicTypes(idConfiguration) );
    	conf.setNeuronTypes( loadNeuronTypes(idConfiguration) );
    	conf.setMotorunitsTypes( loadMotorunitsTypes(idConfiguration) );
    	conf.setMiscellaneous( loadMiscellaneous(idConfiguration) );
    	conf.setMuPresetPosition( loadMuPresetPosition() );

    	conf.setBiomechanicalInputs(loadBiomechanicalInputs(idConfiguration));
    	conf.setMuscles(loadMuscles(idConfiguration));
    	
    	loadInterfaceParameters(idConfiguration);
    	
    	//Provis�rio
    	conf.setCdJoint("ankle");
    	conf.setCdMuscleModel(ReMoto.SOCDS);
    	conf.setCdJointModel("isometric");
    	conf.setCdSpindleModel(ReMoto.spindleModelMileusnic);
    	conf.setCdGtoModel(ReMoto.lumpedGtoModelCrago);
    	conf.setCdEMGModel(ReMoto.emgModelHermiteRodriguez);
    	
    	//conf.setBag1Gain(20000 / 1);
    	//conf.setBag2AndChainGain(10000 / 1);
    	//conf.setSecondaryBag2AndChainGain(7250 / 1);
    	
        conf.setPrimaryBag1Gain(7000);
        conf.setPrimaryBag2AndChainGain(3800);
        conf.setSecondaryBag2AndChainGain(3000);
    	
    	conf.setDecimationFrequency(20000);
    	
    	conf.setMuDistribution(ReMoto.muDistributionPreset);
    	conf.setRecruitmentOrderFES("linear");
    	
    	//conf.getResult().setWithEMGnoise(true);
    	//conf.getResult().setWithEMGattenuation(true);
    	
    	
    	conf.setInitialRecruitmentThresholdIa(0);
    	conf.setFinalRecruitmentThresholdIa(50);
    	conf.setInitialRecruitmentThresholdII(0);
    	conf.setFinalRecruitmentThresholdII(50);
    	conf.setInitialRecruitmentThresholdIb(0);
    	conf.setFinalRecruitmentThresholdIb(50);
    	
    	conf.setpercentageOfMVC(0.15);
    	
    	disconnect();
    	
    	return conf;
	}
    

	public boolean saveConfiguration(Configuration conf, User user)
	{
    	try
    	{
    		//System.out.println("ConfigurationDAO.saveConfiguration()");
    		
    		connect();
       		
       		this.conf = conf;
	    	
       		
	    	
	    	if( prepareToSave() == false ) return false; 
	    	if( storeConfiguration(user) == false ) return false;
	    	
	    	if( storeInterfaceParameters() == false ) return false;
	    	if( storeMuscles() == false ) return false;
	    	
	    	if( storeNerves() == false ) return false;
	    	if( storeMotorunitsTypes() == false ) return false;
	    	if( storeNeuronTypes() == false ) return false;
	    	if( storeSynapseTypes() == false ) return false;
	    	if( storeMarcovTypes() == false ) return false;
	    	if( storeDynamicTypes() == false ) return false;
	    	if( storeInjectedCurrents() == false ) return false;
	    	if( storeMiscellaneous() == false ) return false;
	    	
	    	if( storeNothing() == false ) return false;
	    	
	    	con.commit();
	    	
	    	disconnect();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	return true;
	}
    

	public boolean deleteConfiguration(int idConfiguration)
	{
    	try
    	{
       		connect();
	    	
			st = con.createStatement();

			st.executeUpdate("delete from interface_parameters where id_configuration = " + idConfiguration);
			st.executeUpdate("delete from hill_type_musculotendon_parameters where id_configuration = " + idConfiguration);
			
	    	st.executeUpdate("delete from injected_current where id_configuration = " + idConfiguration);
			st.executeUpdate("delete from descending_commands where id_configuration = " + idConfiguration);
			st.executeUpdate("delete from markov_type where id_configuration = " + idConfiguration);
			st.executeUpdate("delete from synaptic_dynamics where id_configuration = " + idConfiguration);
			st.executeUpdate("delete from conductance_type where id_configuration = " + idConfiguration);
			st.executeUpdate("delete from neuron_type where id_configuration = " + idConfiguration);
			st.executeUpdate("delete from nerve where id_configuration = " + idConfiguration);
			st.executeUpdate("delete from motorunit_type where id_configuration = " + idConfiguration);
			st.executeUpdate("delete from miscellaneous where id_configuration = " + idConfiguration);
			st.executeUpdate("delete from configuration where id_configuration = " + idConfiguration);
			
			st.close(); 
	    	
			con.commit();
	    	
	    	disconnect();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	return true;
	}
	
	
	private boolean prepareToSave()
	{
    	try
    	{
	    	if( conf.getId() == -1 )
	    	{
            	String sql = "select max(id_configuration)" +
      		  		  		 " from configuration";

            	st = con.createStatement();
            	rs = st.executeQuery(sql);
            	
            	if( rs.next() )
            	{
            		conf.setId( rs.getInt(1) + 1 );
            	}

            	rs.close();
    			st.close(); 
	    	}
	    	else
	    	{
	    		deleteConfiguration(conf.getId());
	    	}
		}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	if( con == null )
       		connect();

		return true;
	}
	
    
	public List loadConfigurations(String cdUser)
	{
    	connect();
    	
    	List configurations = new ArrayList();
    	
    	try
    	{
    		// -----------------------------------------
			// Load availables configurations for that user

    		String sql =  "select id_configuration, nm_configuration, ds_configuration," +
		    			  " tfin, step, merge, cd_user, keep_properties, store_results, store_signals" +
		    			  " from configuration" +
		    			  " where (cd_user = '" + cdUser + "'" +
		    			  " or cd_user = 'guest')" +
		    			  " order by nm_configuration";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
        		GeneralVO general = new GeneralVO();
        		
        		general.setId( rs.getInt(1) );
        		general.setName( rs.getString(2) );
        		general.setDescription( rs.getString(3) );
        		general.setTFin( rs.getDouble(4) );
        		general.setStep( rs.getDouble(5) );
        		general.setMerge( rs.getBoolean(6) );
        		general.setCdUser( rs.getString(7) );
        		general.setKeepProperties( rs.getBoolean(8) );
        		general.setStoreResults( rs.getBoolean(9) );
        		general.setStoreSignals( rs.getBoolean(10) );
        		
        		configurations.add( general );
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	disconnect();
    	
    	return configurations;
	}
	 
    public GeneralVO loadConfiguration(int idConfiguration)
	{
		GeneralVO general = new GeneralVO();
		
    	try
    	{
    		// -----------------------------------------
			// Load availables configurations for that user

    		String sql =  "select id_configuration, nm_configuration, ds_configuration, tfin, step, " +
		    			  " merge, cd_user, keep_properties, store_results, store_signals" +
		    			  " from configuration" +
		    			  " where id_configuration = " + idConfiguration +
		    			  " order by nm_configuration";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	if( rs.next() )
        	{
        		general.setId( rs.getInt(1) );
        		general.setName( rs.getString(2) );
        		general.setDescription( rs.getString(3) );
        		general.setTFin( rs.getDouble(4) );
        		general.setStep( rs.getDouble(5) );
        		general.setMerge( rs.getBoolean(6) );
        		general.setCdUser( rs.getString(7) );
        		general.setKeepProperties( rs.getBoolean(8) );
        		general.setStoreResults( rs.getBoolean(9) );
        		general.setStoreSignals( rs.getBoolean(10) );
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return general;
	}
    
    private List loadJoints()
	{
    	List list = new ArrayList();
    	
    	try
    	{
    		String sql = "select cd_joint, nm_joint, num_nuclei, index" +
    					 " from joints order by index";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
            	JointVO vo = new JointVO();
            	
            	vo.setCd( rs.getString(1) );
            	vo.setName( rs.getString(2) );
            	vo.setNumNuclei( rs.getInt(3) );
            	vo.setInd( rs.getInt(4) );
            	
        		list.add( vo );
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return list;
	}
    
    private List loadNucleus()
	{
    	List list = new ArrayList();
    	
    	try
    	{
    		
    		/*
    		String sql = "select cd_nucleus, nm_nucleus, layer, index" +
    					 " from nucleus order by index";
    		*/
    		
    		
    		String sql = "select cd_nucleus, nm_nucleus, layer, index, joint" +
			 " from nucleus order by index";
			
        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
            	Nucleus nucleus = new Nucleus();
            	
            	nucleus.setCd( rs.getString(1) );
            	nucleus.setName( rs.getString(2) );
            	nucleus.setLayer( rs.getString(3) );
            	nucleus.setInd( rs.getInt(4) );
            	nucleus.setJoint( rs.getString(5) );
            	
        		list.add( nucleus );
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return list;
	}

	
    private List loadCategories()
	{
    	List list = new ArrayList();
    	
    	try
    	{
    		String sql = "select cd_category, nm_category" +
    					 " from category" +
    					 " order by nm_category desc";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
            	Category category = new Category();
            	
            	category.setCdCategory( rs.getString(1) );
            	category.setName( rs.getString(2) );

        		list.add( category );
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return list;
	}


	private List loadInjectedCurrents(int idConfiguration)
	{
		List list = new ArrayList();
    	
    	try
    	{
    		String sql = "select imax, cd_nucleus, cd_neuron_type," +
    					 " cd_signal, tini, tfin, amp, freq, width," +
    					 " compartment, active, index, delay" +
    					 " from injected_current" +
    					 " where id_configuration = " + idConfiguration +
    					 " order by cd_nucleus, index";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
        		Current inj = new Current();
        		
        		inj.setImax( rs.getDouble(1) );
        		inj.setCdNucleus( rs.getString(2) );
        		inj.setCdNeuronType( rs.getString(3) );

				inj.setCdSignal( rs.getString(4) );
				inj.setIni( rs.getDouble(5) );
				inj.setFin( rs.getDouble(6) );
				inj.setAmp( rs.getDouble(7) );
				inj.setFreq( rs.getDouble(8) );
				inj.setWidth( rs.getDouble(9) );
        		
        		inj.setCompartment( rs.getString(10) );
        		inj.setActive( rs.getBoolean(11) );
        		inj.setIndex( rs.getInt(12) );
        		inj.setDelay( rs.getDouble(13));
        		inj.setModFreq( 1);
        		inj.setModFactor( 1);
        		
        		// Created by Vitor 05/06/11
        		
        		ModulatingSignal signal = new ModulatingSignal();
        		
        		signal.setCdSignal(inj.getCdSignal());
        		signal.setTini(inj.getIni());
        		signal.setTfin(inj.getFin());
        		signal.setAmp(inj.getAmp());
        		signal.setFreq(inj.getFreq());
        		signal.setWidth(inj.getWidth());
        		signal.setDelay(inj.getDelay());
        		signal.setModFreq(inj.getModFreq());
        		signal.setModFactor(inj.getModFactor());
        		
        		inj.setSignal(signal);
        		
        		list.add( inj );
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return list;
	}
	
	


	private List loadDescendingCommands(int idConfiguration)
	{
		List list = new ArrayList();
    	
        try
    	{
    		String sql = "select cd_signal, cd_nucleus, cd_neuron_type," +
    					 " mod_type, tini, tfin, amp, freq, width, delay" +
    					 " from descending_commands" +
    					 " where id_configuration = " + idConfiguration +
    					 " order by cd_nucleus";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
        		ModulatingSignal signal = new ModulatingSignal();
        		
        		signal.setCdSignal( rs.getString(1) );
        		signal.setCdNucleus( rs.getString(2) );
        		signal.setCdNeuronType( rs.getString(3) );
        		signal.setModType( rs.getString(4) );
        		signal.setTini( rs.getDouble(5) );
        		signal.setTfin( rs.getDouble(6) );
        		signal.setAmp( rs.getDouble(7) );
        		signal.setFreq( rs.getDouble(8) );
        		signal.setWidth( rs.getDouble(9) );
        		signal.setDelay( rs.getDouble(10) );
        		signal.setModFreq( 1);
        		signal.setModFactor( 0.5);
        		
        		list.add( signal );
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return list;
	}
    
    
    private List loadSynapseTypes(int idConfiguration)
	{
    	List list = new ArrayList();
    	
    	try
    	{
    		String sql = "select cd_conductance_type, cd_nucleus, cd_nucleus_pre," +
    					 " E, gmax, connectivity, compartment, active, delay, index" +
    					 " from conductance_type" +
    					 " where id_configuration = " + idConfiguration +
  		  		  		 " order by compartment, index";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
            	ConductanceVO g = new ConductanceVO();
            	
            	g.setCdConductanceType( rs.getString(1) );
            	g.setCdNucleus( rs.getString(2) );
            	g.setCdNucleusPre( rs.getString(3) );
            	g.setE( rs.getDouble(4) );
            	g.setGmax( rs.getDouble(5) );
            	g.setConnectivity( rs.getDouble(6) );
            	g.setCompartment( rs.getString(7) );
            	g.setActive( rs.getBoolean(8) );
            	g.setDelay( rs.getDouble(9) );
            	g.setIndex( rs.getInt(10) );

            	//System.out.println("g.getCompartment: " + g.getCompartment());
        		list.add( g );
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return list;
	}
    
    
    private Hashtable loadMarcovTypes(int idConfiguration)
	{
    	Hashtable hash = new Hashtable();
    	
    	try
    	{
    		String sql = "select cd_markov_type, cd_nucleus," +
    					 " E, gmax, tpeak, tmax, alpha, beta, index" +
    					 " from markov_type" +
    					 " where id_configuration = " + idConfiguration +
  		  		  		 " order by index";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
        		ConductanceVO g = new ConductanceVO();
            	
            	g.setCdConductanceType( rs.getString(1) );
            	g.setCdNucleus( rs.getString(2) );
            	g.setE( rs.getDouble(3) );
            	g.setGmax( rs.getDouble(4) );
            	g.setTpeak( rs.getDouble(5) );
            	g.setTmax( rs.getDouble(6) );
            	g.setAlpha( rs.getDouble(7) );
            	g.setBeta( rs.getDouble(8) );
            	g.setIndex( rs.getInt(9) );

            	/*
            	if(g.getCdConductanceType().equals("gCaP-S") || g.getCdConductanceType().equals("gCaP-FR") || g.getCdConductanceType().equals("gCaP-FF") || g.getCdConductanceType().equals("gCaP-RC") || g.getCdConductanceType().equals("gCaP-IaIn") || g.getCdConductanceType().equals("gCaP-IbIn")){
            		System.out.println("g.getCdConductanceType(): " + g.getCdConductanceType());
	    			System.out.println("conf.getId(): " 			+ conf.getId());
	    			System.out.println("g.getCdNucleus(): " 		+ g.getCdNucleus());
	    			System.out.println("g.getE(): " 				+ g.getE());
	    			System.out.println("g.getGmax(): " 				+ g.getGmax());
	    			System.out.println("g.getTpeak(): " 			+ g.getTpeak());
	    			System.out.println("g.getTmax(): " 				+ g.getTmax());
	    			System.out.println("g.getAlpha(): " 			+ g.getAlpha());
	    			System.out.println("g.getBeta()): " 			+ g.getBeta());
	    			System.out.println("g.getIndex(): " 			+ g.getIndex());
            	}
	            */	
    			
    			
        		hash.put( g.getCdNucleus() + g.getCdConductanceType() + g.getPolarity(), g);
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return hash;
	}
    
    
    private List loadDynamicTypes(int idConfiguration)
	{
    	List list = new ArrayList();
    	
    	try
    	{
    		String sql = "select cd_nucleus, cd_nucleus_pre," +
    					 " cd_conductance_type, dynamic_type, tau, variation" +
    					 " from synaptic_dynamics" +
    					 " where id_configuration = " + idConfiguration;

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
        		DynamicVO vo = new DynamicVO();
            	
        		vo.setCdNucleus( rs.getString(1) );
        		vo.setCdNucleusPre( rs.getString(2) );
        		vo.setCdConductanceType( rs.getString(3) );
        		vo.setDynamicType( rs.getString(4) );
        		vo.setTau( rs.getDouble(5) );
        		vo.setVariation( rs.getDouble(6) );

        		list.add( vo );
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return list;
	}
    
    private List loadNeuronTypes(int idConfiguration)
	{
    	List list = new ArrayList();
    	
    	try
    	{
    		
       		String sql = "select cd_neuron_type, cd_category, cd_nucleus, threshold1, threshold2," +
			 " cm, ri, rm_dend1, rm_dend2, rm_soma1, rm_soma2, d_dend1, d_dend2, " +
			 " l_dend1, l_dend2, d_soma1, d_soma2, l_soma1, l_soma2, total_area," +
		     " distribution, mean, std, axon_velocity1, axon_velocity2," +
			 " axon_threshold1, axon_threshold2, quantity, cd_nerve, active, index, thresholdca1, thresholdca2" +
			 " from neuron_type" +
			 " where id_configuration = " + idConfiguration +
			 " order by cd_nucleus, index";

    		
        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
        		NeuronVO neu = new NeuronVO();
            	
        		neu.setType( rs.getString(1) );
        		neu.setCategory( rs.getString(2) );
        		neu.setCdNucleus( rs.getString(3) );
        		neu.setThreshold1( rs.getDouble(4) );
        		neu.setThreshold2( rs.getDouble(5) );
        		neu.setCm( rs.getDouble(6) );
        		neu.setRi( rs.getDouble(7) );
        		neu.setRmDend1( rs.getDouble(8) );
        		neu.setRmDend2( rs.getDouble(9) );
        		neu.setRmSoma1( rs.getDouble(10) );
        		neu.setRmSoma2( rs.getDouble(11) );
    			neu.setDdend1( rs.getDouble(12) );
    			neu.setDdend2( rs.getDouble(13) );
    			neu.setLdend1( rs.getDouble(14) );
    			neu.setLdend2( rs.getDouble(15) );
    			neu.setDsoma1( rs.getDouble(16) );
    			neu.setDsoma2( rs.getDouble(17) );
        		neu.setLsoma1( rs.getDouble(18) );
        		neu.setLsoma2( rs.getDouble(19) );
        		neu.setTotalArea( rs.getDouble(20) );
    			
    			neu.setDistribution( rs.getString(21) );
    			neu.setMean( rs.getDouble(22) );
    			neu.setOrder( (int) rs.getDouble(23) );
    			neu.setAxonVelocity1( rs.getDouble(24) );
    			neu.setAxonVelocity2( rs.getDouble(25) );
    			neu.setAxonThreshold1( rs.getDouble(26) );
    			neu.setAxonThreshold2( rs.getDouble(27) );
    			
    			neu.setQuantity( rs.getInt(28) );
    			neu.setCdNerve( rs.getString(29) );
    			neu.setActive( rs.getBoolean(30) );
    			neu.setIndex( rs.getInt(31) );
    			neu.setThresholdCa1( rs.getDouble(32) );
    			neu.setThresholdCa2( rs.getDouble(33) );
    			
    			ModulatingSignal signal = conf.getDescendingCommand( neu.getType(), neu.getCdNucleus() );

    			if( signal != null )
    			{
        			neu.setCdSignal( signal.getCdSignal() );
        			neu.setAmp( signal.getAmp() );
        			neu.setFreq( signal.getFreq() );
        			neu.setIni( signal.getTini() );
        			neu.setFin( signal.getTfin() );
        			neu.setModType( signal.getModType() );
        			neu.setWidth( signal.getWidth() );
        			neu.setModFreq( signal.getModFreq() );
        			neu.setModFactor( signal.getModFactor() );
    			}

    			list.add( neu );
    			
    			
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return list;
	}


    
    
    private List loadMotorunitsTypes(int idConfiguration)
	{
    	List list = new ArrayList();
    	
    	try
    	{
    		String sql = "select cd_motorunit_type, cd_nucleus, twitch_tension1," +
    					 " twitch_tension2, tetanic_tension1, tetanic_tension2," +
    					 " tau_force1, tau_force2, amp_emg1, amp_emg2, dur_emg1," +
    					 " dur_emg2" +
    					 " from motorunit_type" +
    					 " where id_configuration = " + idConfiguration +
  		  		  		 " order by cd_motorunit_type, index";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
            	MotorUnitVO mu = new MotorUnitVO();
            	
            	mu.setType( rs.getString(1) );
            	mu.setCdNucleus( rs.getString(2) );
            	mu.setTwitchTension1( rs.getDouble(3) );
            	mu.setTwitchTension2( rs.getDouble(4) );
            	mu.setMaxTension1( rs.getDouble(5) );
            	mu.setMaxTension2( rs.getDouble(6) );
        		mu.setContractionTime1( rs.getDouble(7) );
        		mu.setContractionTime2( rs.getDouble(8) );
        		mu.setAmpEMG1( rs.getDouble(9) );
        		mu.setAmpEMG2( rs.getDouble(10) );
        		mu.setLambdaEMG1( rs.getDouble(11) );
        		mu.setLambdaEMG2( rs.getDouble(12) );
        		
        		// PROVIS�RIO
        		
        		mu.setTwitchTension1Raikova(rs.getDouble(3));
        		mu.setTwitchTension2Raikova(rs.getDouble(4));
    			mu.setContractionTime1Raikova(rs.getDouble(7));
        		mu.setContractionTime2Raikova(rs.getDouble(8));
        		
        		
        		// Par�metros para o MG, por enquanto utilizados para todos os m�sculos
        		/*
        		if(mu.getType().equals("S")){
        			mu.setHalfRelaxationTimeRaikova1(202);
            		mu.setHalfRelaxationTimeRaikova2(182);
            		mu.setb1(0.55);
            		mu.setb2(0.45);
            		mu.settwTet1(3.73);
            		mu.settwTet2(4.52);
            		mu.setbRaikova1(1.3);
            		mu.setbRaikova2(1.25);
            		mu.settwTetRaikova1(1.75);
            		mu.settwTetRaikova2(1.80);
        		}
        		else if(mu.getType().equals("FR")){
        			mu.setHalfRelaxationTimeRaikova1(133.5);
            		mu.setHalfRelaxationTimeRaikova2(95.5);
            		mu.setb1(0.45);
            		mu.setb2(0.35);
            		mu.settwTet1(4.52);
            		mu.settwTet2(5.77);
            		mu.setbRaikova1(1.25);
            		mu.setbRaikova2(0.5);
            		mu.settwTetRaikova1(1.80);
            		mu.settwTetRaikova2(4.08);
        		}
        		else if(mu.getType().equals("FF")){
        			mu.setContractionTime1Raikova(55);
            		mu.setContractionTime2Raikova(25);
        			mu.setHalfRelaxationTimeRaikova1(115);
            		mu.setHalfRelaxationTimeRaikova2(65);
            		mu.setb1(0.35);
            		mu.setb2(0.3);
            		mu.settwTet1(5.77);
            		mu.settwTet2(6.72);
            		mu.setbRaikova1(0.5);
            		mu.setbRaikova2(0.55);
            		mu.settwTetRaikova1(4.08);
            		mu.settwTetRaikova2(3.73);
        		}
        		*/
        		if(mu.getCdNucleus().equals(ReMoto.SOL)){
                    if(mu.getType().equals("S")){
                        mu.setHalfRelaxationTimeRaikova1(310);
                        mu.setHalfRelaxationTimeRaikova2(260);
                        mu.setb1(0.7);
                        mu.setb2(0.5);
                        mu.settwTet1(2.97);
                        mu.settwTet2(4.08);
                        mu.setbRaikova1(1.5);
                        mu.setbRaikova2(1);
                        mu.settwTetRaikova1(1.5746);
                        mu.settwTetRaikova2(2.164);
                        
                        /*
                        mu.setbRaikova1(0.25);
                        mu.setbRaikova2(0.25);
                        mu.settwTetRaikova1(8.0386);
                        mu.settwTetRaikova2(8.0386);
                        */
                        /*
                        mu.setbRaikova1(0.91);
                        mu.setbRaikova2(0.91);
                        mu.settwTetRaikova1(8);
                        mu.settwTetRaikova2(8);
                        */
                    }
                    else if(mu.getType().equals("FR")){
                        mu.setHalfRelaxationTimeRaikova1(245);
                        mu.setHalfRelaxationTimeRaikova2(180);
                        mu.setb1(0.5);
                        mu.setb2(0.3);
                        mu.settwTet1(4.08);
                        mu.settwTet2(6.71);
                        mu.setbRaikova1(1);
                        mu.setbRaikova2(0.7);
                        mu.settwTetRaikova1(2.164);
                        mu.settwTetRaikova2(2.973);
                        
                        /*
                        mu.setbRaikova1(0.25);
                        mu.setbRaikova2(0.25);
                        mu.settwTetRaikova1(8.0386);
                        mu.settwTetRaikova2(8.0386);
                        */
                        /*
                        mu.setbRaikova1(0.91);
                        mu.setbRaikova2(0.91);
                        mu.settwTetRaikova1(2.3474);
                        mu.settwTetRaikova2(2.3474);
                        */
                    }
                    else if(mu.getType().equals("FF")){
                        mu.setContractionTime1Raikova(96);
                        mu.setContractionTime2Raikova(84);
                        mu.setHalfRelaxationTimeRaikova1(170);
                        mu.setHalfRelaxationTimeRaikova2(125);
                        mu.setb1(0.3);
                        mu.setb2(0.23);
                        mu.settwTet1(6.71);
                        mu.settwTet2(8.73);
                        mu.setbRaikova1(0.7);
                        mu.setbRaikova2(0.69);
                        mu.settwTetRaikova1(2.973);
                        mu.settwTetRaikova2(3.013);
                        
                        /*
                        mu.setbRaikova1(0.25);
                        mu.setbRaikova2(0.25);
                        mu.settwTetRaikova1(8.0386);
                        mu.settwTetRaikova2(8.0386);
                        */
                        /*
                        mu.setbRaikova1(0.91);
                        mu.setbRaikova2(0.91);
                        mu.settwTetRaikova1(2.3474);
                        mu.settwTetRaikova2(2.3474);
                        */
                    }
                }
                else if(mu.getCdNucleus().equals(ReMoto.TA)) {
                    if(mu.getType().equals("S")){
                        mu.setHalfRelaxationTimeRaikova1(202);
                        mu.setHalfRelaxationTimeRaikova2(182);
                        mu.setb1(1);
                        mu.setb2(0.85);
                        mu.settwTet1(2.16);
                        mu.settwTet2(2.49);
                        mu.setbRaikova1(1.3);
                        mu.setbRaikova2(1.25);
                        mu.settwTetRaikova1(1.75);
                        mu.settwTetRaikova2(1.8);
                    }
                    else if(mu.getType().equals("FR")){
                        mu.setHalfRelaxationTimeRaikova1(133.5);
                        mu.setHalfRelaxationTimeRaikova2(95.5);
                        mu.setb1(0.85);
                        mu.setb2(0.48);
                        mu.settwTet1(2.49);
                        mu.settwTet2(4.25);
                        mu.setbRaikova1(1.25);
                        mu.setbRaikova2(0.5);
                        mu.settwTetRaikova1(1.8);
                        mu.settwTetRaikova2(4.08);
                    }
                    else if(mu.getType().equals("FF")){
                        mu.setContractionTime1Raikova(55);
                        mu.setContractionTime2Raikova(25);
                        mu.setHalfRelaxationTimeRaikova1(115);
                        mu.setHalfRelaxationTimeRaikova2(65);
                        mu.setb1(0.48);
                        mu.setb2(0.43);
                        mu.settwTet1(4.25);
                        mu.settwTet2(4.72);
                        /*mu.setbRaikova1(0.5);
                        mu.setbRaikova2(0.55);
                        mu.settwTetRaikova1(4.08);
                        mu.settwTetRaikova2(3.73);
                        */
                        mu.setbRaikova1(0.25);
                        mu.setbRaikova2(0.25);
                        mu.settwTetRaikova1(8.0386);
                        mu.settwTetRaikova2(8.0386);
                    }
                }
                else{
                    if(mu.getType().equals("S")){
                        mu.setHalfRelaxationTimeRaikova1(202);
                        mu.setHalfRelaxationTimeRaikova2(182);
                        mu.setb1(1);
                        mu.setb2(0.85);
                        mu.settwTet1(2.16);
                        mu.settwTet2(2.49);
                        mu.setbRaikova1(2);
                        mu.setbRaikova2(1.7);
                        mu.settwTetRaikova1(1.313);
                        mu.settwTetRaikova2(1.447);
                        
                        /*
                        mu.setbRaikova1(0.25);
                        mu.setbRaikova2(0.25);
                        mu.settwTetRaikova1(8.0386);
                        mu.settwTetRaikova2(8.0386);
                        */
                        /*
                        mu.setbRaikova1(0.91);
                        mu.setbRaikova2(0.91);
                        mu.settwTetRaikova1(2.3474);
                        mu.settwTetRaikova2(2.3474);
                        */
                    }
                    else if(mu.getType().equals("FR")){
                        mu.setHalfRelaxationTimeRaikova1(133.5);
                        mu.setHalfRelaxationTimeRaikova2(95.5);
                        mu.setb1(0.85);
                        mu.setb2(0.48);
                        mu.settwTet1(2.49);
                        mu.settwTet2(4.25);
                        mu.setbRaikova1(1.7);
                        mu.setbRaikova2(1.1);
                        mu.settwTetRaikova1(1.447);
                        mu.settwTetRaikova2(1.998);
                        
                        /*
                        mu.setbRaikova1(0.25);
                        mu.setbRaikova2(0.25);
                        mu.settwTetRaikova1(8.0386);
                        mu.settwTetRaikova2(8.0386);
                        */
                        /*
                        mu.setbRaikova1(0.91);
                        mu.setbRaikova2(0.91);
                        mu.settwTetRaikova1(2.3474);
                        mu.settwTetRaikova2(2.3474);
                        */
                    }
                    else if(mu.getType().equals("FF")){
                        mu.setContractionTime1Raikova(55);
                        mu.setContractionTime2Raikova(25);
                        mu.setHalfRelaxationTimeRaikova1(115);
                        mu.setHalfRelaxationTimeRaikova2(65);
                        mu.setb1(0.48);
                        mu.setb2(0.43);
                        mu.settwTet1(4.25);
                        mu.settwTet2(4.72);
                        mu.setbRaikova1(1.1);
                        mu.setbRaikova2(1);
                        mu.settwTetRaikova1(1.998);
                        mu.settwTetRaikova2(2.164);
                        /*
                        mu.setbRaikova1(0.25);
                        mu.setbRaikova2(0.25);
                        mu.settwTetRaikova1(8.0386);
                        mu.settwTetRaikova2(8.0386);
                        */
                        /*
                        mu.setbRaikova1(0.91);
                        mu.setbRaikova2(0.91);
                        mu.settwTetRaikova1(2.3474);
                        mu.settwTetRaikova2(2.3474);
                        */
                    }
                }
        		
        		/*
        		if(mu.getCdNucleus().equals(ReMoto.SOL)){
        			if(mu.getType().equals("S")){
            			mu.setHalfRelaxationTimeRaikova1(202);
                		mu.setHalfRelaxationTimeRaikova2(182);
                		mu.setb1(0.78);
                		mu.setb2(0.32);
                		mu.settwTet1(2.69);
                		mu.settwTet2(6.31);
            		}
            		else if(mu.getType().equals("FR")){
            			mu.setHalfRelaxationTimeRaikova1(133.5);
                		mu.setHalfRelaxationTimeRaikova2(95.5);
                		mu.setb1(0.32);
                		mu.setb2(0.18);
                		mu.settwTet1(6.31);
                		mu.settwTet2(11.14);
            		}
            		else if(mu.getType().equals("FF")){
            			mu.setContractionTime1Raikova(55);
                		mu.setContractionTime2Raikova(25);
            			mu.setHalfRelaxationTimeRaikova1(115);
                		mu.setHalfRelaxationTimeRaikova2(65);
                		mu.setb1(0.18);
                		mu.setb2(0.17);
                		mu.settwTet1(11.14);
                		mu.settwTet2(11.79);
            		}
        		}
        		else if(mu.getCdNucleus().equals(ReMoto.TA)) {
        			if(mu.getType().equals("S")){
            			mu.setHalfRelaxationTimeRaikova1(202);
                		mu.setHalfRelaxationTimeRaikova2(182);
                		mu.setb1(0.78);
                		mu.setb2(0.32);
                		mu.settwTet1(2.69);
                		mu.settwTet2(6.31);
            		}
            		else if(mu.getType().equals("FR")){
            			mu.setHalfRelaxationTimeRaikova1(133.5);
                		mu.setHalfRelaxationTimeRaikova2(95.5);
                		mu.setb1(0.32);
                		mu.setb2(0.18);
                		mu.settwTet1(6.31);
                		mu.settwTet2(11.14);
            		}
            		else if(mu.getType().equals("FF")){
            			mu.setContractionTime1Raikova(55);
                		mu.setContractionTime2Raikova(25);
            			mu.setHalfRelaxationTimeRaikova1(115);
                		mu.setHalfRelaxationTimeRaikova2(65);
                		mu.setb1(0.18);
                		mu.setb2(0.17);
                		mu.settwTet1(11.14);
                		mu.settwTet2(11.79);
            		}
        		}
        		else{
        			if(mu.getType().equals("S")){
            			mu.setHalfRelaxationTimeRaikova1(202);
                		mu.setHalfRelaxationTimeRaikova2(182);
                		mu.setb1(0.78);
                		mu.setb2(0.32);
                		mu.settwTet1(2.69);
                		mu.settwTet2(6.31);
            		}
            		else if(mu.getType().equals("FR")){
            			mu.setHalfRelaxationTimeRaikova1(133.5);
                		mu.setHalfRelaxationTimeRaikova2(95.5);
                		mu.setb1(0.32);
                		mu.setb2(0.18);
                		mu.settwTet1(6.31);
                		mu.settwTet2(11.14);
            		}
            		else if(mu.getType().equals("FF")){
            			mu.setContractionTime1Raikova(55);
                		mu.setContractionTime2Raikova(25);
            			mu.setHalfRelaxationTimeRaikova1(115);
                		mu.setHalfRelaxationTimeRaikova2(65);
                		mu.setb1(0.18);
                		mu.setb2(0.17);
                		mu.settwTet1(11.14);
                		mu.settwTet2(11.79);
            		}
        		}
        		*/
        		
        		list.add( mu );
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return list;
	}
    
    
    private List loadNerves(int idConfiguration)
	{
    	List list = new ArrayList();
    	
    	try
    	{
    		String sql = "select cd_nerve, cd_joint, active," +
    					 " stimulus_spinal_entry, stimulus_end_plate, index, start, stop, peak, frequency," +
    					 " cd_signal, tini, tfin, amp, freq, width, delay" +
    					 " from nerve" +
    					 " where id_configuration = " + idConfiguration +
    					 " order by index";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
        		NerveVO nerve = new NerveVO();
            	
            	nerve.setCdNerve( rs.getString(1) );
            	nerve.setCdJoint( rs.getString(2) );
        		nerve.setActive( rs.getBoolean(3) );
        		nerve.setStimulusSpinalEntry( rs.getDouble(4) );
        		nerve.setStimulusEndPlate( rs.getDouble(5) );
        		nerve.setIndex( rs.getInt(6) );
        		
        		nerve.setTini( rs.getDouble(7) );
        		nerve.setTfin( rs.getDouble(8) );
        		nerve.setAmp( rs.getDouble(9) );
        		nerve.setFreq( rs.getDouble(10) );
        		
        		nerve.setCdSignal( rs.getString(11) );
        		nerve.setModulating_tini( rs.getDouble(12) );
        		nerve.setModulating_tfin( rs.getDouble(13) );
        		nerve.setModulating_amp( rs.getDouble(14) );
        		nerve.setModulating_freq( rs.getDouble(15) );
        		nerve.setWidth( rs.getDouble(16) );
        		nerve.setDelay( rs.getDouble(17));
        		nerve.setModFreq( 1);
        		nerve.setModFactor(0.5);
        		
        		ModulatingSignal signal = new ModulatingSignal();
        		
        		signal.setCdSignal(nerve.getCdSignal());
        		signal.setTini(nerve.getTini());
        		signal.setTfin(nerve.getTfin());
        		signal.setAmp(nerve.getAmp());
        		signal.setFreq(nerve.getFreq());
        		signal.setWidth(nerve.getWidth());
        		signal.setDelay(nerve.getDelay());
        		signal.setModFreq(nerve.getModFreq());
        		signal.setModFactor(nerve.getModFactor());
        		
        		nerve.setSignal(signal);
        		
        		list.add( nerve );
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return list;
	}
    
    
    
    //Provis�rio
    
    private List loadMuscles(int idConfiguration)
	{
    	List list = new ArrayList();
    	
    	try
    	{
    		
    		String sql = "select cd_muscle, optimal_length," +
    					 " viscosity_coeficient, elastic_coef_parallel, c_t, k_t, lr_t," +
    					 " pennation_angle, slack_tendon_length," +
    					 " maximum_muscle_force, muscle_mass, index" +
    					 " from hill_type_musculotendon_parameters" +
    					 " where id_configuration = " + idConfiguration +
    					 " order by index";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
        		MuscleVO vo = new MuscleVO();
        		
        		vo.setCdMuscle(rs.getString(1));
        		vo.setOptimalLength(rs.getDouble(2));
        		vo.setViscosityCoeficient(rs.getDouble(3));
        		vo.setElasticCoeficientOfParallelElement(rs.getDouble(4));
        		vo.setC_T(rs.getDouble(5));
        		vo.setK_T(rs.getDouble(6));
        		vo.setLR_T(rs.getDouble(7));
        		vo.setPennationAngle(rs.getDouble(8));
        		vo.setSlackTendonLength(rs.getDouble(9));
        		vo.setMaximumMuscleForce(rs.getDouble(10));
        		vo.setMuscleMass(rs.getDouble(11));
        		vo.setIndex(rs.getInt(12));
        		
        		list.add( vo );
        	}

        	rs.close();
			st.close(); 
			
    	    /*
    		MuscleVO muscle = new MuscleVO();
    		
    		muscle.setCdMuscle("SOL");
    		muscle.setOptimalLength(0.03);
    		muscle.setViscosityCoeficient(225);
    		muscle.setElasticCoeficientOfParallelElement(1500);
    		muscle.setPennationAngle(25);
    		muscle.setSlackTendonLength(0.268);
    		muscle.setMaximumMuscleForce(2839);
    		muscle.setMuscleMass(0.602);
    		
    		list.add( muscle );
    		
    		muscle.setCdMuscle("MG");
    		muscle.setOptimalLength(0.045);
    		muscle.setViscosityCoeficient(225);
    		muscle.setElasticCoeficientOfParallelElement(1500);
    		muscle.setPennationAngle(17);
    		muscle.setSlackTendonLength(0.408);
    		muscle.setMaximumMuscleForce(1113);
    		muscle.setMuscleMass(0.247);
    		
    		list.add( muscle );
    		
    		muscle.setCdMuscle("ML");
    		muscle.setOptimalLength(0.064);
    		muscle.setViscosityCoeficient(225);
    		muscle.setElasticCoeficientOfParallelElement(1500);
    		muscle.setPennationAngle(8);
    		muscle.setSlackTendonLength(0.385);
    		muscle.setMaximumMuscleForce(488);
    		muscle.setMuscleMass(0.138);
    		
    		list.add( muscle );
    		
    		muscle.setCdMuscle("TA");
    		muscle.setOptimalLength(0.03);
    		muscle.setViscosityCoeficient(225);
    		muscle.setElasticCoeficientOfParallelElement(1500);
    		muscle.setPennationAngle(25);
    		muscle.setSlackTendonLength(0.268);
    		muscle.setMaximumMuscleForce(2839);
    		muscle.setMuscleMass(0.602);
    		
    		list.add( muscle );
    		*/
    		
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return list;
	}
    
    
    private boolean storeMuscles()
	{
    	List list = conf.getMuscles();
    	
    	try
    	{
    		String sql = "insert into hill_type_musculotendon_parameters (" +
    					 " id_configuration, cd_muscle, optimal_length," +
    					 " viscosity_coeficient, elastic_coef_parallel," +
    					 " c_t, k_t, lr_t," +
    					 " pennation_angle, slack_tendon_length," +
    					 " maximum_muscle_force, muscle_mass, index)" +
    					 " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    		pst = con.prepareStatement(sql);
        	
    		
        	for(int i = 0; i < list.size(); i++)
        	{
            	MuscleVO muscle = (MuscleVO)list.get(i);
            	
            	pst.setInt(1, conf.getId());
            	pst.setString(2, muscle.getCdMuscle());
            	pst.setDouble(3, muscle.getOptimalLength());
            	pst.setDouble(4, muscle.getViscosityCoeficient());
            	pst.setDouble(5, muscle.getElasticCoeficientOfParallelElement());
            	pst.setDouble(6, muscle.getC_T());
            	pst.setDouble(7, muscle.getK_T());
            	pst.setDouble(8, muscle.getLR_T());
            	pst.setDouble(9, muscle.getPennationAngle());
            	pst.setDouble(10, muscle.getSlackTendonLength());
            	pst.setDouble(11, muscle.getMaximumMuscleForce());
            	pst.setDouble(12, muscle.getMuscleMass());
            	pst.setInt(13, muscle.getIndex());
	        	
            	pst.executeUpdate();
        	}

        	pst.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	return true;
	}
    
    private boolean storeNothing()
	{
    	List list = conf.getMuscles();
    	
    	try
    	{
    		String sql = "insert into nothing (" +
    					 " id_configuration, cd_muscle, optimal_length," +
    					 " viscosity_coeficient, elastic_coef_parallel," +
    					 " c_t, k_t, lr_t," +
    					 " pennation_angle, slack_tendon_length," +
    					 " maximum_muscle_force, muscle_mass, index)" +
    					 " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    		pst = con.prepareStatement(sql);
        	
    		
        	for(int i = 0; i < list.size(); i++)
        	{
            	MuscleVO muscle = (MuscleVO)list.get(i);
            	
            	pst.setInt(1, conf.getId());
            	pst.setString(2, muscle.getCdMuscle());
            	pst.setDouble(3, muscle.getOptimalLength());
            	pst.setDouble(4, muscle.getViscosityCoeficient());
            	pst.setDouble(5, muscle.getElasticCoeficientOfParallelElement());
            	pst.setDouble(6, muscle.getC_T());
            	pst.setDouble(7, muscle.getK_T());
            	pst.setDouble(8, muscle.getLR_T());
            	pst.setDouble(9, muscle.getPennationAngle());
            	pst.setDouble(10, muscle.getSlackTendonLength());
            	pst.setDouble(11, muscle.getMaximumMuscleForce());
            	pst.setDouble(12, muscle.getMuscleMass());
            	pst.setInt(13, muscle.getIndex());
	        	
            	pst.executeUpdate();
        	}

        	pst.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	return true;
	}
    
    private List loadBiomechanicalInputs(int idConfiguration)
	{
    	/*
    	List list = new ArrayList();
    	
    	BiomechanicalInputVO biomechanicalInput = new BiomechanicalInputVO();
		
    	biomechanicalInput.setAmp( 0 );
    	biomechanicalInput.setFreq( 0 );
		
    	biomechanicalInput.setImax( 0 );
    	biomechanicalInput.setCdNucleus( ReMoto.SOL );

    	biomechanicalInput.setCdSignal( ReMoto.ramp );
    	biomechanicalInput.setIni( 0 );
    	biomechanicalInput.setFin( 0 );
    	biomechanicalInput.setAmp( 0 );
    	biomechanicalInput.setFreq( 0 );
    	biomechanicalInput.setWidth( 0 );
		
    	biomechanicalInput.setActive( true );
    	biomechanicalInput.setIndex( 1 );
    	biomechanicalInput.setDelay( 0 );
		
		ModulatingSignal signal = new ModulatingSignal();
		
		signal.setCdSignal(biomechanicalInput.getCdSignal());
		signal.setTini(biomechanicalInput.getIni());
		signal.setTfin(biomechanicalInput.getFin());
		signal.setAmp(biomechanicalInput.getAmp());
		signal.setFreq(biomechanicalInput.getFreq());
		signal.setWidth(biomechanicalInput.getWidth());
		signal.setDelay(biomechanicalInput.getDelay());
		
		biomechanicalInput.setSignal(signal);
		
		list.add( biomechanicalInput );
    	
    	return list;
    	*/
    	
    	List list = new ArrayList();
    	
    	try
    	{
    		String sql = "select imax, cd_nucleus, cd_neuron_type," +
    					 " cd_signal, tini, tfin, amp, freq, width," +
    					 " compartment, active, index, delay" +
    					 " from injected_current" +
    					 " where id_configuration = " + idConfiguration +
    					 " order by cd_nucleus, index";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
        		BiomechanicalInput biomechanicalInput = new BiomechanicalInput();
        		
        		biomechanicalInput.setImax( rs.getDouble(1) );
        		biomechanicalInput.setCdNucleus( rs.getString(2) );
        		biomechanicalInput.setCdNeuronType( rs.getString(3) );

        		biomechanicalInput.setCdSignal( rs.getString(4) );
        		biomechanicalInput.setIni( rs.getDouble(5) );
        		biomechanicalInput.setFin( rs.getDouble(6) );
        		biomechanicalInput.setAmp( rs.getDouble(7) );
        		biomechanicalInput.setFreq( rs.getDouble(8) );
        		biomechanicalInput.setWidth( rs.getDouble(9) );
        		
        		biomechanicalInput.setCompartment( rs.getString(10) );
        		biomechanicalInput.setActive( rs.getBoolean(11) );
        		biomechanicalInput.setIndex( rs.getInt(12) );
        		biomechanicalInput.setDelay( rs.getDouble(13));
        		biomechanicalInput.setModFreq( 1);
        		biomechanicalInput.setModFactor( 0.5);
        		
        		//System.out.println("loading biomechanicalInputs: Imax: " + rs.getDouble(1));
        		
        		// Created by Vitor 05/06/11
        		
        		ModulatingSignal signal = new ModulatingSignal();
        		
        		signal.setCdSignal(biomechanicalInput.getCdSignal());
        		signal.setTini(biomechanicalInput.getIni());
        		signal.setTfin(biomechanicalInput.getFin());
        		signal.setAmp(biomechanicalInput.getAmp());
        		signal.setFreq(biomechanicalInput.getFreq());
        		signal.setWidth(biomechanicalInput.getWidth());
        		signal.setDelay(biomechanicalInput.getDelay());
        		signal.setModFreq(biomechanicalInput.getModFreq());
        		signal.setModFactor(biomechanicalInput.getModFactor());
        		
        		biomechanicalInput.setSignal(signal);
        		
        		list.add( biomechanicalInput );
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return list;
	}
    
    
    
    private List loadMiscellaneous(int idConfiguration)
	{
    	List list = new ArrayList();
    	
    	try
    	{
    		String sql = "select property, description, value, index, division" +
    					 " from miscellaneous" +
    					 " where id_configuration = " + idConfiguration +
    					 " order by index";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
        		MiscellaneousVO vo = new MiscellaneousVO();
            	
        		vo.setProperty( rs.getString(1) );
        		vo.setDescription( rs.getString(2) );
        		vo.setValue( rs.getDouble(3) );
        		vo.setIndex( rs.getInt(4) );
        		vo.setDivision( rs.getBoolean(5) );

        		list.add( vo );
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return list;
	}
    

    private Hashtable loadMuPresetPosition()
	{
    	Hashtable hash = new Hashtable();
    	
    	try
    	{
    		String sql = "select cd_nucleus, cd_mu, position_y, position_z" +
    					 " from mu_position";

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
        		Point point = new Point();
            	
        		String cdNucleus = rs.getString(1);
        		String cd = rs.getString(2);
        		
        		point.setY( rs.getDouble(3) );
        		point.setZ( rs.getDouble(4) );

        		hash.put( cdNucleus + cd, point);
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	return hash;
	}
    
    
    private void loadInterfaceParameters(int idConfiguration)
	{
    	List list = new ArrayList();
    	
    	try
    	{
    		String sql = "select cd_nucleus" +
    					 " from interface_parameters" +
    					 " where id_configuration = " + idConfiguration ;

        	st = con.createStatement();
        	rs = st.executeQuery(sql);
        	
        	while( rs.next() )
        	{
            	//System.out.println("cd_nucleus: " + rs.getString(1));
            	conf.setCdNucleus(rs.getString(1));
        	}

        	rs.close();
			st.close(); 
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	//return list;
	}
    
    
    private boolean  storeInterfaceParameters()
	{
    	try
    	{
    		int idConfiguration = conf.getId();
    		
	    	String sql = "insert into interface_parameters" +
			 " (id_configuration, cd_nucleus)" +
			 " values (?, ?)";
	
			pst = con.prepareStatement(sql);
			
			pst.setInt(1, idConfiguration);
			pst.setString(2, conf.getCdNucleus());
			
			pst.executeUpdate();
			pst.close();
    	}
    	catch (Exception e) 
    	{
    		System.out.println(e.getMessage());
    		return false;
		}
    	
    	return true;
	}
    
    
    
    
    private boolean storeConfiguration(User user)
	{
    	try
    	{
    		int idConfiguration = conf.getId();
    		Calendar cal = Calendar.getInstance();
    		Date date = new Date(cal.getTimeInMillis());
    		
    		String sql = "insert into configuration (id_configuration, nm_configuration, ds_configuration, " +
    					 " cd_user, dt_configuration, tfin, step, merge, keep_properties, store_results, store_signals) values " +
    					 "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        	pst = con.prepareStatement(sql);
        	
        	pst.setInt(1, idConfiguration);
        	pst.setString(2, conf.getName());
        	pst.setString(3, conf.getDescription());
        	pst.setString(4, user.getCdUser());
        	pst.setDate(5, date);
        	pst.setDouble(6, conf.getTFin());
        	pst.setDouble(7, conf.getStep());
        	pst.setBoolean(8, conf.isMerge());
        	pst.setBoolean(9, conf.isKeepProperties());
        	pst.setBoolean(10, conf.isStoreResults());
			pst.setBoolean(11, conf.isStoreSignals());
			
        	pst.executeUpdate();
        	pst.close(); 
    	}
    	catch (Exception e) 
    	{
    		System.out.println(e.getMessage());
    		return false;
		}
    	
    	return true;
	}
    
	
	private boolean storeInjectedCurrents()
	{
        List list = conf.getInjectedCurrents();
    	
    	try
    	{
    		String sql = "insert into injected_current (id_configuration, cd_nucleus, cd_neuron_type," +
    					 " cd_signal, tini, tfin, amp, freq, width," +
    					 " compartment, imax, active, index, delay) " +
    					 " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        	pst = con.prepareStatement(sql);

        	for(int i = 0; i < list.size(); i++)
        	{
        		Current inj = (Current)list.get(i);
        		
	        	pst.setInt(1, conf.getId());
	        	pst.setString(2, inj.getCdNucleus() );
	        	pst.setString(3, inj.getCdNeuronType() );
	        	
	        	pst.setString(4, inj.getCdSignal() );
	        	pst.setDouble(5, inj.getIni());
	        	pst.setDouble(6, inj.getFin());
	        	pst.setDouble(7, inj.getAmp());
	        	pst.setDouble(8, inj.getFreq());
	        	pst.setDouble(9, inj.getWidth());
	        	
	        	pst.setString(10, inj.getCompartment() );
	        	pst.setDouble(11, inj.getImax());
	        	pst.setBoolean(12, inj.isActive());
	        	pst.setInt(13, inj.getIndex());
	        	pst.setDouble(14, inj.getDelay());
	        	
	        	pst.executeUpdate();
        	}

        	pst.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	return true;
	}
	
	
	
	private boolean storeDescendingCommands(ModulatingSignal signal)
	{
        try
    	{
    		String sql = "insert into descending_commands (id_configuration, cd_nucleus, cd_neuron_type," +
    					 " cd_signal, mod_type, tini, tfin, amp, freq, width, delay) " +
    					 " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    		PreparedStatement ps = con.prepareStatement(sql);

    		ps.setInt(1, conf.getId());
    		ps.setString(2, signal.getCdNucleus() );
    		ps.setString(3, signal.getCdNeuronType() );
    		ps.setString(4, signal.getCdSignal() );
    		ps.setString(5, signal.getModType() );
    		ps.setDouble(6, signal.getTini());
    		ps.setDouble(7, signal.getTfin());
    		ps.setDouble(8, signal.getAmp());
    		ps.setDouble(9, signal.getFreq());
    		ps.setDouble(10, signal.getWidth());    		
    		ps.setDouble(11, signal.getDelay());
        	
    		ps.executeUpdate();

    		ps.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	return true;
	}
    
    
    private boolean storeSynapseTypes()
	{
    	try
    	{
	    	int idConfiguration = conf.getId();
	    	Hashtable hash = conf.getSynapseTypes();
	
			Iterator it = hash.values().iterator();

			while( it.hasNext() )
			{
				ConductanceVO g = (ConductanceVO)it.next();
	        	
	    		storeConductanceType(g, idConfiguration);
	    	}
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	return true;
	}
    
    
    private void storeConductanceType(ConductanceVO g, int idConfiguration) throws Exception
	{
		String sql = "insert into conductance_type" +
					 " (cd_conductance_type, id_configuration, cd_nucleus, cd_nucleus_pre," +
					 " gmax, E, connectivity, compartment, active, delay, index)" +
					 " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		pst = con.prepareStatement(sql);
    	
		
    	pst.setString(1, g.getCdConductanceType());
    	pst.setDouble(2, idConfiguration);
    	pst.setString(3, g.getCdNucleus());
    	pst.setString(4, g.getCdNucleusPre());
    	pst.setDouble(5, g.getGmax());
    	pst.setDouble(6, g.getE());
    	pst.setDouble(7, g.getConnectivity());
    	pst.setString(8, g.getCompartment());
    	pst.setBoolean(9, g.isActive());
    	pst.setDouble(10, g.getDelay());
    	pst.setInt(11, g.getIndex());

    	pst.executeUpdate();
    	pst.close();
	}
    
    
    private boolean storeMarcovTypes() throws Exception
	{
    	Hashtable hash = conf.getMarcovTypes();
    	
    	try
    	{
			String sql = "insert into markov_type" +
						 " (cd_markov_type, id_configuration, cd_nucleus," +
						 " E, gmax, tpeak, tmax, alpha, beta, index)" +
						 " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
    		pst = con.prepareStatement(sql);
        	
    		Iterator it = hash.values().iterator();

    		while( it.hasNext() )
    		{
    			ConductanceVO g = (ConductanceVO)it.next();
            	
				pst.setString(1, g.getCdConductanceType());
				pst.setDouble(2, conf.getId());
				pst.setString(3, g.getCdNucleus());
				pst.setDouble(4, g.getE());
				pst.setDouble(5, g.getGmax());
				pst.setDouble(6, g.getTpeak());
				pst.setDouble(7, g.getTmax());
				pst.setDouble(8, g.getAlpha());
				pst.setDouble(9, g.getBeta());
				pst.setInt(10, g.getIndex());

            	pst.executeUpdate();
        	}

        	pst.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	return true;
	}
    
    
    private boolean storeDynamicTypes() throws Exception
	{
    	List list = conf.getDynamicTypes();
    	
    	try
    	{
			String sql = "insert into synaptic_dynamics" +
						 " (id_configuration, cd_nucleus, cd_nucleus_pre," +
						 " cd_conductance_type, dynamic_type, tau, variation)" +
						 " values (?, ?, ?, ?, ?, ?, ?)";
	
    		pst = con.prepareStatement(sql);
        	
        	for(int i = 0; i < list.size(); i++)
        	{
        		DynamicVO vo = (DynamicVO)list.get(i);
            	
				pst.setInt(1, conf.getId());
				pst.setString(2, vo.getCdNucleus());
				pst.setString(3, vo.getCdNucleusPre());
				pst.setString(4, vo.getCdConductanceType());
				pst.setString(5, vo.getDynamicType());
				pst.setDouble(6, vo.getTau());
				pst.setDouble(7, vo.getVariation());

            	pst.executeUpdate();
        	}

        	pst.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	return true;
	}

    
    private boolean storeNeuronTypes()
	{
    	List list = conf.getNeuronTypes();
    	NeuronVO neu = null;
    	
    	try
    	{
        	String sql = "insert into neuron_type (id_configuration, cd_neuron_type, cd_category, cd_nucleus, threshold1, threshold2," + 
    					 "rm_dend1, rm_dend2, rm_soma1, rm_soma2, d_dend1, d_dend2," +
    					 "l_dend1, l_dend2, d_soma1, d_soma2, l_soma1, l_soma2, total_area," +
    					 "distribution, quantity, mean, std, axon_velocity1, axon_velocity2," +
    					 "axon_threshold1, axon_threshold2, cm, ri, cd_nerve, index, active, thresholdca1, thresholdca2)" +
    					 "values (?, ?, ?, ?, ?, ?, " +
    					 "		  ?, ?, ?, ?, ?, ?, " +
    					 "	      ?, ?, ?, ?, ?, ?, ?, " +
    					 "		  ?, ?, ?, ?, ?, ?, " +
    					 "        ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    		pst = con.prepareStatement(sql);
        	
        	for(int i = 0; i < list.size(); i++)
        	{
        		neu = (NeuronVO)list.get(i);
            	
            	pst.setInt(1, conf.getId());
            	pst.setString(2, neu.getType());
            	pst.setString(3, neu.getCategory());
            	pst.setString(4, neu.getCdNucleus());
            	
            	pst.setDouble(5, neu.getThreshold1());
            	pst.setDouble(6, neu.getThreshold2());
            	pst.setDouble(7, neu.getRmDend1());
            	pst.setDouble(8, neu.getRmDend2());
            	pst.setDouble(9, neu.getRmSoma1());
            	pst.setDouble(10, neu.getRmSoma2());
            	pst.setDouble(11, neu.getDdend1());
            	pst.setDouble(12, neu.getDdend2());
            	pst.setDouble(13, neu.getLdend1());
            	pst.setDouble(14, neu.getLdend2());
            	pst.setDouble(15, neu.getDsoma1());
            	pst.setDouble(16, neu.getDsoma2());
            	pst.setDouble(17, neu.getLsoma1());
            	pst.setDouble(18, neu.getLsoma2());
            	pst.setDouble(19, neu.getTotalArea());
            	pst.setString(20, neu.getDistribution());
            	pst.setInt(21, neu.getQuantity());
            	pst.setDouble(22, neu.getMean());
            	pst.setDouble(23, neu.getOrder());
            	pst.setDouble(24, neu.getAxonVelocity1());
            	pst.setDouble(25, neu.getAxonVelocity2());
            	pst.setDouble(26, neu.getAxonThreshold1());
            	pst.setDouble(27, neu.getAxonThreshold2());
            	pst.setDouble(28, neu.getCm());
            	pst.setDouble(29, neu.getRi());
            	pst.setString(30, neu.getCdNerve());
            	
            	pst.setInt(31, neu.getIndex());
            	pst.setBoolean(32, neu.isActive());
            	
            	pst.setDouble(33, neu.getThresholdCa1());
          	    pst.setDouble(34, neu.getThresholdCa2());
            	
    	    	pst.executeUpdate();

            	if( neu.getCategory().equals( ReMoto.TR ) )
            	{
                	ModulatingSignal signal = new ModulatingSignal();

                	signal.setCdNucleus( neu.getCdNucleus() );
                	signal.setCdNeuronType( neu.getType() );
        			signal.setCdSignal( neu.getCdSignal() );
                	signal.setAmp( neu.getAmp() );
        			signal.setFreq( neu.getFreq() );
        			signal.setTini( neu.getIni() );
        			signal.setTfin( neu.getFin() );
        			signal.setModType( neu.getModType() );
        			signal.setWidth( neu.getWidth() );
        			signal.setDelay( neu.getDelay() );

        			storeDescendingCommands(signal); 
            	}
        	}

        	pst.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage() + " - " + neu.getType());
    		return false;
    	}
		
    	return true;
	}
    
    
    private boolean storeMotorunitsTypes()
	{
    	List list = conf.getMotorunitsTypes();
    	
    	try
    	{
    		String sql = "insert into motorunit_type (id_configuration, cd_motorunit_type, cd_nucleus," +
    					 " twitch_tension1, twitch_tension2, tetanic_tension1, tetanic_tension2," +
    					 " tau_force1, tau_force2, amp_emg1, amp_emg2, dur_emg1, dur_emg2) values" +
    					 "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    		pst = con.prepareStatement(sql);
        	
        	for(int i = 0; i < list.size(); i++)
        	{
            	MotorUnitVO mu = (MotorUnitVO)list.get(i);
            	
            	pst.setInt(1, conf.getId());
            	pst.setString(2, mu.getType());
            	pst.setString(3, mu.getCdNucleus());
            	pst.setDouble(4, mu.getTwitchTension1());
            	pst.setDouble(5, mu.getTwitchTension2());
            	pst.setDouble(6, mu.getMaxTension1());
            	pst.setDouble(7, mu.getMaxTension2());
            	pst.setDouble(8, mu.getContractionTime1());
            	pst.setDouble(9, mu.getContractionTime2());
            	pst.setDouble(10, mu.getAmpEMG1());
            	pst.setDouble(11, mu.getAmpEMG2());
            	pst.setDouble(12, mu.getLambdaEMG1());
            	pst.setDouble(13, mu.getLambdaEMG2());

            	pst.executeUpdate();
        	}

        	pst.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	return true;
	}
    
    private boolean storeNerves()
	{
    	List list = conf.getAllNerves();
    	
    	try
    	{
    		String sql = "insert into nerve (id_configuration, cd_nerve, cd_joint, active," +
    					 " stimulus_spinal_entry, stimulus_end_plate, index, start, stop, peak, frequency," +
    					 " cd_signal, tini, tfin, amp, freq, width, delay)" +
    					 " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    		pst = con.prepareStatement(sql);
        	
        	for(int i = 0; i < list.size(); i++)
        	{
            	NerveVO nerve = (NerveVO)list.get(i);
            	
            	pst.setInt(1, conf.getId());
            	pst.setString(2, nerve.getCdNerve());
            	pst.setString(3, nerve.getCdJoint());
            	pst.setBoolean(4, nerve.isActive());
            	pst.setDouble(5, nerve.getStimulusSpinalEntry());
            	pst.setDouble(6, nerve.getStimulusEndPlate());
            	pst.setInt(7, nerve.getIndex());
            	
            	pst.setDouble(8, nerve.getTini());
            	pst.setDouble(9, nerve.getTfin());
            	pst.setDouble(10, nerve.getAmp());
            	pst.setDouble(11, nerve.getFreq());
            	
            	pst.setString(12, nerve.getCdSignal() );
	        	pst.setDouble(13, nerve.getModulating_tini());
	        	pst.setDouble(14, nerve.getModulating_tfin());
	        	pst.setDouble(15, nerve.getModulating_amp());
	        	pst.setDouble(16, nerve.getModulating_freq());
	        	pst.setDouble(17, nerve.getWidth());
	        	pst.setDouble(18, nerve.getDelay());
	        	
            	pst.executeUpdate();
        	}

        	pst.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	return true;
	}

    
	private boolean storeMiscellaneous()
	{
        List list = conf.getMiscellaneous();

    	try
    	{
    		String sql = "insert into miscellaneous (id_configuration, property, description, value, index, division)" +
    					 " values (?, ?, ?, ?, ?, ?)";

        	pst = con.prepareStatement(sql);

        	for(int i = 0; i < list.size(); i++)
        	{
        		MiscellaneousVO vo = (MiscellaneousVO)list.get(i);
        		
        		System.out.println("Storing: " + vo.getProperty());
        		
	        	pst.setInt(1, conf.getId());
	        	pst.setString(2, vo.getProperty());
	        	pst.setString(3, vo.getDescription());
	        	pst.setDouble(4, vo.getValue());
	        	pst.setInt(5, vo.getIndex());
	        	pst.setBoolean(6, vo.isDivision());
	        	
	        	pst.executeUpdate();
        	}

        	pst.close();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    		return false;
    	}
    	
    	return true;
	}

    
}
