package br.remoto.model;

import java.util.HashMap;

public class ReMoto 
{
	// ------
	// Nuclei
	
	public static final String DT = "DT"; 
	public static final String IN_ext = "IN_ext";
	public static final String IN_flex = "IN_flex";
	public static final String MG = "MG"; 
	public static final String LG = "LG"; 
	public static final String SOL= "SOL"; 
	public static final String TA = "TA"; 

	public static final int indDT = 0; 
	public static final int indIN_ext_ankle = 1; 
	public static final int indIN_flex_ankle = 2; 
	public static final int indSOL = 3; 
	public static final int indMG = 4; 
	public static final int indLG = 5; 
	public static final int indTA = 6;
	public static final int indFDI = 7; 
	public static final int indSPI = 8; 
	public static final int indIN_ext_mcp = 9; 
	public static final int indIN_flex_mcp = 10; 
	public static final int indECRL = 11; 
	public static final int indFCU = 12; 
	public static final int indIN_ext_wrist = 13; 
	public static final int indIN_flex_wrist = 14;
	
	//public static final int numberOfNuclei = 15; 
	//public static final int numberOfMotorNuclei = 8; 
	//public static final int numberOfJoints = 3; 
	
	//public static final int numberOfNuclei = 7; 
	//public static final int numberOfMotorNuclei = 4; 
	//public static final int numberOfJoints = 1; 
	

	// ------
	// Muscle models
	
	public static final String SOCDS = "SOCDS"; 
	public static final String Raikova = "raikova"; 
	public static final String Hill = "hill"; 
	public static final String DistributionMoments = "distributionMoments"; 
	
	
	// ------
	// Muscle spindle models
	
	public static final String spindleModelMileusnic = "spindleModelMileusnic"; 
	public static final String spindleModelProchazka = "spindleModelProchazka"; 
	
	
	// ------
	// Golgi Tendon Organ models
	
	public static final String lumpedGtoModelCrago = "lumpedGtoModelCrago"; 
	
	
	// ------
	// EMG models
	
	public static final String emgModelHermiteRodriguez = "emgModelHermiteRodriguez"; 
	
	
	// -----
	// Layer
	
	public static final String DTs = "DTs"; 
	public static final String MNs = "MNs"; 
	public static final String INs = "INs"; 

	
	// ------
	// Nerves
	
	//public static final int numberOfNerves = 2; 
	
	public static final String PTN = "PTN";
	public static final String CPN = "CPN";
	
	
	// ----------
	// Categories
	
	public static final String MN = "MN";		// motoneuron
	public static final String IN = "IN";		// interneuron 
	public static final String TR = "TR";		// neural tract
	public static final String AF = "AF";		// afferent sensory fiber 
	public static final String FB = "FB";		// muscle fiber 	
	public static final String Noise = "Noise";	// noise 

	// ------------
	// Neuron Types
	
	public static final String S = "S"; 
	public static final String FR = "FR"; 
	public static final String FF = "FF";
	public static final String F = "F"; 

	public static final String RC = "RC"; 
	public static final String IaIn = "IaIn"; 
	public static final String IbIn = "IbIn";
	public static final String gII = "gII";

	public static final String Ia = "Ia"; 
	public static final String II = "II"; 
	public static final String Ib = "Ib"; 

	//Do not modify (used in comparisons)
	public static final String CM_ext = "CM_ext"; 
	public static final String RBE_ext = "RBE_ext"; 
	public static final String RBI_ext = "RBI_ext"; 
	
	//Do not modify (used in comparisons)
	public static final String CM_flex = "CM_flex"; 
	public static final String RBE_flex = "RBE_flex"; 
	public static final String RBI_flex = "RBI_flex"; 

	public static final String NoiseExcitatoryMN = "excitatory_MN";
	public static final String NoiseInhibitoryMN = "inhibitory_MN";
	public static final String NoiseExcitatoryRC = "excitatory_RC";
	public static final String NoiseInhibitoryRC = "inhibitory_RC";

	public static final String PDController = "PDController"; 

	// -----------------
	// Neuron type names
	
	//Modified by Vitor 05/05/11
	
	public static final String completeName_CM_ext = "Direct corticomotor (CM_ext)"; 
	public static final String completeName_RBE_ext = "Indirect through excitatory interneurons (ExcINs_ext)"; 
	public static final String completeName_RBI_ext = "Indirect through inhibitory interneurons (InhINs_ext)"; 
	public static final String completeName_CM_flex = "Direct corticomotor (CM_flex)"; 
	public static final String completeName_RBE_flex = "Indirect through excitatory interneurons (ExcINs_flex)"; 
	public static final String completeName_RBI_flex = "Indirect through inhibitory interneurons (InhINs_flex)"; 
	
	
	public static final String completeName_NoiseExcitatoryMN = "Excitatory noise on MNs"; 
	public static final String completeName_NoiseInhibitoryMN = "Inhibitory noise on MNs"; 
	public static final String completeName_NoiseExcitatoryRC = "Excitatory noise on RCs"; 
	public static final String completeName_NoiseInhibitoryRC = "Inhibitory noise on RCs"; 
	
	public static final String name_CM_ext = "CM_ext"; 
	public static final String name_RBE_ext = "ExcINs_ext"; 
	public static final String name_RBI_ext = "InhINs_ext"; 
	public static final String name_CM_flex = "CM_flex"; 
	public static final String name_RBE_flex = "ExcINs_flex"; 
	public static final String name_RBI_flex = "InhINs_flex"; 
	public static final String name_NoiseExcitatoryMN = "Noise excitatory"; 
	public static final String name_NoiseInhibitoryMN = "Noise inhibitory"; 
	public static final String name_NoiseExcitatoryRC = "Noise excitatory"; 
	public static final String name_NoiseInhibitoryRC = "Noise inhibitory"; 

	// ------------------------
	// Neural tracts properties
	
	public static final String poisson = "P";
	public static final String gaussian = "G";

	// ---------------
	// Axon properties
	
	public static final String gradual = "G";
	public static final String uniform = "U";
	public static final String gradualGaussian = "GG";

	// -------------------
	// Synaptic properties
	
	public static final String depressing = "D";
	public static final String facilitating = "F";
	public static final String noDynamics = "N";
	public final static int NO_DYNAMICS = 0;
	public final static int DEPRESSING = 1;
	public final static int FACILITATING = 2;
	public static final String excitatory = "excitatory";
	public static final String inhibitory = "inhibitory";

	// -----------------------
	// Conductances properties
	
	public static final String both = "B";
	public static final String ionic = "I";
	public static final String synaptic = "S";
	public static final String gNa = "gNa";
	public static final String gK = "gK";
	public static final String gKs = "gKs";
	public static final String gKf = "gKf";
	public static final String gNaM = "gNaM";
	public static final String gNaH = "gNaH";
	public static final String gKN = "gKN";
	public static final String gKQ = "gKQ";
	public static final String gCa = "gCa";
	public static final String gCaP = "gCaP";
	public static final String gExcDend = "gExc-dend";
	public static final String gInibDend = "gInib-dend";
	public static final String gExcSoma = "gExc-soma";
	public static final String gInibSoma = "gInib-soma";

	// ------------------
	// Results properties

	public static final String file = "file";
	public static final String chart = "chart";
	public static final String chart_img = "chart_img";
	public static final String chart_img_new = "chart_img_new";
	public static final String array = "array";
	public static final String summary = "summary";
	public static final String force = "force";
	public static final String length = "length";
	public static final String EMG = "EMG";
	public static final String signal = "signal";
	public static final String histogram = "histogram";
	public static final String firingRate = "firingRate";
	public static final String spikes = "spikes";
	public static final String spikesAndForce = "spikesAndForce";
	public static final String spikesAndEMG = "spikesAndEMG";
	public static final String ThirdThird = "3rd3rd";
	public static final String FirstSecond = "1st2nd";
	public static final String noFiltering = "noFiltering";
	public static final String properties = "properties";
	public static final String IaFiringRate = "IaFiringRate";
	public static final String IIFiringRate = "IIFiringRate";
	public static final String IbFiringRate = "IbFiringRate";
	public static final String muscleLength = "muscleLength";
	public static final String muscleVelocity = "muscleVelocity";
	public static final String muscleAcceleration = "muscleAcceleration";
	
	public static final String activationNorm = "activationNorm";
	public static final String activationNormSType = "activationNormSType";
	public static final String activationNormFType = "activationNormFType";
	
	public static final String muscleLengthNorm = "muscleLengthNorm";
	public static final String forceParallelElement = "forceParallelElement";
	public static final String forceViscousElement = "forceViscousElement";
	public static final String forceActiveSType = "forceActiveSType";
	public static final String forceActiveFType = "forceActiveFType";
	public static final String totalMuscleForce = "totalMuscleForce";
	public static final String tendonLength = "tendonLength";
	public static final String tendonForce = "tendonForce";
	
	
	public static final String ALL_MU = "All motor units";
	public static final String ALL_MN = "All MNs";
	public static final String ALL_MN_S = "All MNs S";
	public static final String ALL_MN_FR = "All MNs FR";
	public static final String ALL_MN_FF = "All MNs FF";
	public static final String ALL_IN = "All INs";
	public static final String ALL_RC = "All RCs";
	public static final String ALL_IaIn = "All IaIns";
	public static final String ALL_IbIn = "All IbIns";
	public static final String ALL_gII =  "All gII";
	public static final String ALL_MN_soma = "All MNs (at soma)";
	public static final String ALL_MN_end_plate = "All MNs (at end-plate)";
	public static final String ALL_Ia = "All Ia";
	public static final String ALL_Ib = "All Ib";
	public static final String ALL_II = "All II";
	public static final String ALL_Ia_stimulus_point = "All Ia (at stimulus point)";
	public static final String ALL_Ia_terminal = "All Ia (at terminals)";
	public static final String ALL_Ib_stimulus_point = "All Ib (at stimulus point)";
	public static final String ALL_Ib_terminal = "All Ib (at terminals)";
	public static final String ALL_II_stimulus_point = "All II (at stimulus point)";
	public static final String ALL_II_terminal = "All II (at terminals)";
	public static final String ALL_CM_ext = "All CM extensor axons";
	public static final String ALL_CM_flex = "All CM flexor axons";
	public static final String ALL_RBE_ext = "All exc. INs extensor axons";
	public static final String ALL_RBE_flex = "All exc. INs flexor axons";
	public static final String ALL_RBI_ext = "All Inhib. INs extensor axons";
	public static final String ALL_RBI_flex = "All Inhib. INs flexor axons";
	public static final String CM_TR_ext = "CM ext. ISI mean value";
	public static final String CM_TR_flex = "CM flex. ISI mean value";
	public static final String RBE_TR_ext = "Excit. INs ext. ISI mean value";
	public static final String RBE_TR_flex = "Excit. INs flex. ISI mean value";
	public static final String RBI_TR_ext = "Inhib. INs ext. ISI mean value";
	public static final String RBI_TR_flex = "Inhib. INs flex. ISI mean value";

	// ------------------
	// General properties

	public static final int idGuestUser = 1;
	public static final String cdGuestUser = "guest";
	public static final String nmGuestUser = "Guest user";
	public static final String ALL = "all";
	public static final String ACTIVE = "active";
	public static final String somaCompartment = "S";
	public static final String dendriteCompartment = "D";
	
	// ------------
	// Signal names
	
	public static final String Vd = "Vd";
	public static final String Vs = "Vs";
	public static final String inj = "inj"; // injected current
	public static final String MMV = "MMV"; // modulated mean value
	public static final String none = "none";
	public static final String constant = "constant";
	public static final String sine = "sine";
	public static final String square = "square";
	public static final String ramp = "ramp";
	public static final String custom = "custom";
	public static final String random = "random";
	public static final String triangle =  "triangle";
	public static final String trapezoid = "trapezoid";
	public static final String stochasticGamma = "stochastic";
	

	// ------------------------
	// Miscellaneous properties
	
	public static final String gammaCa = "gammaCa";
	public static final String thresholdSTD = "thresholdSTD";
	public static final String thresholdCaSTD = "thresholdCaSTD";
	public static final String mnSomaRefractoryPeriod = "mnSomaRefractoryPeriod";
	public static final String mnAxonRefractoryPeriod = "mnAxonRefractoryPeriod";
	public static final String inRcSomaRefractoryPeriod = "inRcSomaRefractoryPeriod";
	public static final String inIaSomaRefractoryPeriod = "inIaSomaRefractoryPeriod";
	public static final String inIbSomaRefractoryPeriod = "inIbSomaRefractoryPeriod";
	public static final String gIISomaRefractoryPeriod = "gIISomaRefractoryPeriod";
	public static final String afAxonRefractoryPeriod = "afAxonRefractoryPeriod";
	//public static final String reobaseSTD = "reobaseSTD";
	public static final String axonThresholdSTD = "axonThresholdSTD";
	public static final String axonVelocitySTD = "axonVelocitySTD";
	public static final String decliningRCtoMN = "decliningRCtoMN";
	public static final String decliningMNtoRC = "decliningMNtoRC";
	public static final String cellsPerSlice = "cellsPerSlice";
	public static final String interSliceDistance = "interSliceDistance";
	public static final String step = "step";
	public static final String keepProperties = "keepProperties";
	public static final String latencyExcitatory = "latencyExcitatory";
	public static final String latencyInhibitory = "latencyInhibitory";
	public static final String xIniSOL = "xIniSOL";
	public static final String xIniMG = "xIniMG";
	public static final String xIniLG = "xIniLG";
	public static final String xIniTA = "xIniTA";
	public static final String xEndSOL = "xEndSOL";
	public static final String xEndMG = "xEndMG";
	public static final String xEndLG = "xEndLG";
	public static final String xEndTA = "xEndTA";
	public static final String xIniExt = "xIniExt";
	public static final String xEndExt = "xEndExt";
	public static final String xIniFlex = "xIniFlex";
	public static final String xEndFlex = "xEndFlex";
	public static final String thickness = "thickness";
	public static final String skinLayer = "skinLayer";
	public static final String tauAttenuation = "tauAttenuation";
	public static final String cteEnlargement = "cteEnlargement";
	public static final String noiseEMG = "noiseEMG";
	public static final String spontaneousRC_mean = "spontaneousRC_mean";
	public static final String spontaneousRC_cv = "spontaneousRC_cv";	

	// -------------------
	// Simulator constants
	
	public static final double E0 = 0.0;
	public static final double P0 = -16.0;
	public static final double EsE = 120.0;
	public static final double EsI = P0;

	public static final double pulseWidth = 1.0;
	public static final double minRefractoryPeriod = 1.0;
	public static final double minStep = 0.0001;
	public static final double minISImean = 0.1;
	public static final int maxAmpCurrent = 1000;
	public static final int maxNeuronsPerType = 1000;
	public static final int minCellsPerSlice = 1;

	
	public static final int region1 = 1;	// LG: S, FR and Ins
	public static final int region2 = 2;	// LG: FR, FF and MG-SOL: S, FR and Ins
	public static final int region3 = 3;	// MG-SOL: FR, FF and Ins 
	public static final int region4 = 4;	// TA: S, FR and Ins 
	public static final int region5 = 5;	// TA: FR, FF and Ins 
	
	public static final int muDistributionPreset = 1; 
	public static final int muDistributionRandom = 2; 
	public static final int muDistributionLinear = 3; 
	
	public static final int numberOfBins = 50;

	public static final double T_MAX = 500000;
	public static final double T_MAX_NO_SIGNALS = 600000;
	public static final double Y_MAX = 1000000;
	public static final double T_DEFAULT = 2000;
	public static final double T_TOLERANCE = 0.001;
	


	// -------------------
	// Simulator variables
	
	public static String path;
	public static HashMap simulations = new HashMap();
	
	
	// -------------------
	// Hill muscle parameters
	
	/*
	public static final double b_SOL_I = 5;
	public static final double b_SOL_II = 6.4;
	public static final double b_MG_I = 31;
	public static final double b_MG_II = 5;
	public static final double b_LG_I = 48;
	public static final double b_LG_II = 6.4;
	public static final double b_TA_I = 30;
	public static final double b_TA_II = 6.4;
	*/
	
	//public static final double timeScale = 0.1;
	
	public static final double eps0_M = 0.50;
	
	public static final double maximumVelocitySType = -7.88;
	public static final double maximumVelocityFType = -9.15;
	
	public static final double av0_SType = -4.7;
	public static final double av1_SType = 8.41;
	public static final double av2_SType = -5.34;
	public static final double bv_SType = 0.35;
	public static final double av0_FType = -1.53;
	public static final double av1_FType = 0;
	public static final double av2_FType = 0;
	public static final double bv_FType = 0.69;
	public static final double cv0_SType = 5.88;
	public static final double cv1_SType = 0;
	public static final double cv0_FType = -5.70;
	public static final double cv1_FType = 9.18;
	
	// Parameters for force-length relationship (Cheng 2000)
	
	public static final double B_SType = 2.3;
	public static final double w_SType = 1.12;
	public static final double p_SType = 1.62;
	
	public static final double B_FType = 1.55;
	public static final double w_FType = 0.75;
	public static final double p_FType = 2.12;
}
