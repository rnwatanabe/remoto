package br.remoto.util;

import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;
import br.remoto.model.vo.ConductanceVO;
import br.remoto.model.vo.NeuronVO;


public class ElectroCalculation 
{

	public double calcGCoupling(double ri, double lDend, double dDend, double lSoma, double dSoma)
	{
		double pi = Math.PI;
		double rAxisSoma = (ri * lSoma)/(pi * Math.pow((dSoma/2),2));
		double rAxisDend = (ri * lDend)/(pi * Math.pow((dDend/2),2));

		double ret = (1e6 * 2) / (rAxisDend + rAxisSoma);
		
		return ret;
	}
	
	
	public double calcGLeak(double area, double rm)
	{
		double ret = (1e6 * area) / rm;
		
		return ret;
	}
	
	
	public double calcRn(double gCoupling, double gLeak1, double gLeak2)
	{
		double ret = 1 / (gLeak1 + (gLeak2 * gCoupling)/(gLeak2 + gCoupling));
		
		return ret;
	}
	
	
	public double calcTauMemb(double cm, double rmSoma, double rmDend, double areaSoma, double areaDend)
	{
		cm = cm / 1e3;
		 
		double aTot = areaSoma + areaDend;
		double RmSeff = 1/( (1/rmSoma)*(areaSoma/aTot) + (1/rmDend)*(areaDend/aTot) );

		double ret = cm * RmSeff;
		
		return ret;
	}
	
	
	public double calcCapacitance(double cm, double area)
	{
		cm = cm * 1e3;

		double ret = cm * area;
		
		return ret;
	}

	
	public NeuronVO getPattern(Configuration conf, NeuronVO reference, NeuronVO referenceS, NeuronVO referenceFF, double bias)
	{
		NeuronVO pattern = new NeuronVO();
    	
		//pattern.setRheobase1( Bias.gradual(reference.getRheobase1(), reference.getRheobase2(), bias ) );
		pattern.setThreshold1( Bias.gradual(reference.getThreshold1(), reference.getThreshold2(), bias ) );
		pattern.setThresholdCa1( Bias.gradual(reference.getThresholdCa1(), reference.getThresholdCa2(), bias ) );
		pattern.setRmSoma1( Bias.gradual(reference.getRmSoma1(), reference.getRmSoma2(), bias ) );
		pattern.setRmDend1( Bias.gradual(reference.getRmDend1(), reference.getRmDend2(), bias ) );
		pattern.setRi( reference.getRi() );
		pattern.setCm( reference.getCm() );

		pattern.setDsoma1( Bias.gradual(reference.getDsoma1(), reference.getDsoma2(), bias ) / 1e4 );
		pattern.setDdend1( Bias.gradual(reference.getDdend1(), reference.getDdend2(), bias ) / 1e4 );
		pattern.setLsoma1( Bias.gradual(reference.getLsoma1(), reference.getLsoma2(), bias ) / 1e4 );
		pattern.setLdend1( Bias.gradual(reference.getLdend1(), reference.getLdend2(), bias ) / 1e4 );

		String distribution = ReMoto.gradualGaussian;
		
		if( distribution.equals( ReMoto.uniform ) )
		{
			pattern.setAxonThreshold1( Bias.uniform(referenceS.getAxonThreshold1(), referenceFF.getAxonThreshold2() ) );
			pattern.setAxonVelocity1( Bias.uniform(referenceS.getAxonVelocity1(), referenceFF.getAxonVelocity2() ) );
		}
		else if( distribution.equals( ReMoto.gradualGaussian ) )
		{
			pattern.setAxonThreshold1( Bias.gradualGaussian(reference.getAxonThreshold1(), reference.getAxonThreshold2(), bias, conf.getMiscellaneous( ReMoto.axonThresholdSTD ) ) );
			pattern.setAxonVelocity1( Bias.gradualGaussian(reference.getAxonVelocity1(), reference.getAxonVelocity2(), bias, conf.getMiscellaneous( ReMoto.axonVelocitySTD ) ) );
		}
		else
		{
			pattern.setAxonThreshold1( Bias.gradual(reference.getAxonThreshold1(), reference.getAxonThreshold2(), bias ) );
			pattern.setAxonVelocity1( Bias.gradual(reference.getAxonVelocity1(), reference.getAxonVelocity2(), bias ) );
		}
		
		//System.out.println( pattern.getAxonThreshold1() );
		
		pattern.setGNaMVO( new ConductanceVO( reference.getGNaMVO() ) );
		pattern.setGNaHVO( new ConductanceVO( reference.getGNaHVO() ) );
		pattern.setGKNVO( new ConductanceVO( reference.getGKNVO() ) );
		pattern.setGKQVO( new ConductanceVO( reference.getGKQVO() ) );
		pattern.setGCaPVO( new ConductanceVO( reference.getGCaPVO() ) );
		
		return pattern;
	}
	
	
	// Set proper MN properties considering its type and position inner the pool (bias)
	public NeuronVO getPatternMerged(Configuration conf, NeuronVO reference, NeuronVO referenceS, NeuronVO referenceFR, NeuronVO referenceFF, double bias)
	{
		NeuronVO pattern = new NeuronVO();

		double dSoma1 = 0, dSoma2 = 0;
		double dDend1 = 0, dDend2 = 0;
		double lSoma1 = 0, lSoma2 = 0;
		double lDend1 = 0, lDend2 = 0;
		double rmSoma1 = 0, rmSoma2 = 0;
		double rmDend1 = 0, rmDend2 = 0;
		//double rheobase1 = 0, rheobase2 = 0;
		double threshold1 = 0, threshold2 = 0;
		double thresholdCa1 = 0, thresholdCa2 = 0;
		double axonVelocity1 = 0, axonVelocity2 = 0;
		double axonThreshold1 = 0, axonThreshold2 = 0;
		
		double gNaMVO_Gmax1 = 0, gNaMVO_Gmax2 = 0;
		double gNaHVO_Gmax1 = 0, gNaHVO_Gmax2 = 0;
		double gKNVO_Gmax1 = 0, gKNVO_Gmax2 = 0;
		double gKQVO_Gmax1 = 0, gKQVO_Gmax2 = 0;
		double gCaPVO_Gmax1 = 0, gCaPVO_Gmax2 = 0;
		
		double gNaMVO_Alpha1 = 0, gNaMVO_Alpha2 = 0;
		double gNaHVO_Alpha1 = 0, gNaHVO_Alpha2 = 0;
		double gKNVO_Alpha1 = 0, gKNVO_Alpha2 = 0;
		double gKQVO_Alpha1 = 0, gKQVO_Alpha2 = 0;
		double gCaPVO_Alpha1 = 0, gCaPVO_Alpha2 = 0;
		
		double gNaMVO_Beta1 = 0, gNaMVO_Beta2 = 0;
		double gNaHVO_Beta1 = 0, gNaHVO_Beta2 = 0;
		double gKNVO_Beta1 = 0, gKNVO_Beta2 = 0;
		double gKQVO_Beta1 = 0, gKQVO_Beta2 = 0;
		double gCaPVO_Beta1 = 0, gCaPVO_Beta2 = 0;
		
		
		if( reference.getType().equals( ReMoto.S ) )
		{
			dSoma1 = referenceS.getDsoma1();
			dDend1 = referenceS.getDdend1();
			lSoma1 = referenceS.getLsoma1();
			lDend1 = referenceS.getLdend1();
			rmSoma1 = referenceS.getRmSoma1();
			rmDend1 = referenceS.getRmDend1();
			//rheobase1 = referenceS.getRheobase1(); 
			threshold1 = referenceS.getThreshold1(); 
			thresholdCa1 = referenceS.getThresholdCa1();
			axonVelocity1 = referenceS.getAxonVelocity1(); 
			axonThreshold1 = referenceS.getAxonThreshold1(); 
			
			gNaMVO_Gmax1 = referenceS.getGNaMVO().getGmax();
			gNaHVO_Gmax1 = referenceS.getGNaHVO().getGmax();
			gKNVO_Gmax1 = referenceS.getGKNVO().getGmax();
			gKQVO_Gmax1 = referenceS.getGKQVO().getGmax();
			gCaPVO_Gmax1 = referenceS.getGCaPVO().getGmax();
			
			gNaMVO_Alpha1 = referenceS.getGNaMVO().getAlpha();
			gNaHVO_Alpha1 = referenceS.getGNaHVO().getAlpha();
			gKNVO_Alpha1 = referenceS.getGKNVO().getAlpha();
			gKQVO_Alpha1 = referenceS.getGKQVO().getAlpha();
			gCaPVO_Alpha1 = referenceS.getGCaPVO().getAlpha();
			
			gNaMVO_Beta1 = referenceS.getGNaMVO().getBeta();
			gNaHVO_Beta1 = referenceS.getGNaHVO().getBeta();
			gKNVO_Beta1 = referenceS.getGKNVO().getBeta();
			gKQVO_Beta1 = referenceS.getGKQVO().getBeta();
			gCaPVO_Beta1 = referenceS.getGCaPVO().getBeta();

			dSoma2 = ( referenceS.getDsoma2() + referenceFR.getDsoma1() ) / 2;
			dDend2 = ( referenceS.getDdend2() + referenceFR.getDdend1() ) / 2;
			lSoma2 = ( referenceS.getLsoma2() + referenceFR.getLsoma1() ) / 2;
			lDend2 = ( referenceS.getLdend2() + referenceFR.getLdend1() ) / 2;
			rmSoma2 = ( referenceS.getRmSoma2() + referenceFR.getRmSoma1() ) / 2;
			rmDend2 = ( referenceS.getRmDend2() + referenceFR.getRmDend1() ) / 2;
			//rheobase2 = ( referenceS.getRheobase2() + referenceFR.getRheobase1() ) / 2;
			threshold2 = ( referenceS.getThreshold2() + referenceFR.getThreshold1() ) / 2;
			thresholdCa2 = ( referenceS.getThresholdCa2() + referenceFR.getThresholdCa1() ) / 2;
			axonVelocity2 = ( referenceS.getAxonVelocity2() + referenceFR.getAxonVelocity1() ) / 2; 
			axonThreshold2 = ( referenceS.getAxonThreshold2() + referenceFR.getAxonThreshold1() ) / 2; 
			
			gNaMVO_Gmax2 = ( referenceS.getGNaMVO().getGmax() + referenceFR.getGNaMVO().getGmax() ) / 2;
			gNaHVO_Gmax2 = ( referenceS.getGNaHVO().getGmax() + referenceFR.getGNaHVO().getGmax() ) / 2;
			gKNVO_Gmax2 = ( referenceS.getGKNVO().getGmax() + referenceFR.getGKNVO().getGmax() ) / 2;
			gKQVO_Gmax2 = ( referenceS.getGKQVO().getGmax() + referenceFR.getGKQVO().getGmax() ) / 2;
			gCaPVO_Gmax2 = ( referenceS.getGCaPVO().getGmax() + referenceFR.getGCaPVO().getGmax() ) / 2;
			
			gNaMVO_Alpha2 = ( referenceS.getGNaMVO().getAlpha() + referenceFR.getGNaMVO().getAlpha() ) / 2;
			gNaHVO_Alpha2 = ( referenceS.getGNaHVO().getAlpha() + referenceFR.getGNaHVO().getAlpha() ) / 2;
			gKNVO_Alpha2 = ( referenceS.getGKNVO().getAlpha() + referenceFR.getGKNVO().getAlpha() ) / 2;
			gKQVO_Alpha2 = ( referenceS.getGKQVO().getAlpha() + referenceFR.getGKQVO().getAlpha() ) / 2;
			gCaPVO_Alpha2 = ( referenceS.getGCaPVO().getAlpha() + referenceFR.getGCaPVO().getAlpha() ) / 2;
			
			gNaMVO_Beta2 = ( referenceS.getGNaMVO().getBeta() + referenceFR.getGNaMVO().getBeta() ) / 2;
			gNaHVO_Beta2 = ( referenceS.getGNaHVO().getBeta() + referenceFR.getGNaHVO().getBeta() ) / 2;
			gKNVO_Beta2 = ( referenceS.getGKNVO().getBeta() + referenceFR.getGKNVO().getBeta() ) / 2;
			gKQVO_Beta2 = ( referenceS.getGKQVO().getBeta() + referenceFR.getGKQVO().getBeta() ) / 2;
			gCaPVO_Beta2 = ( referenceS.getGCaPVO().getBeta() + referenceFR.getGCaPVO().getBeta() ) / 2;
		} 
		else if( reference.getType().equals( ReMoto.FR ) )
		{
			dSoma1 = ( referenceS.getDsoma2() + referenceFR.getDsoma1() ) / 2;
			dDend1 = ( referenceS.getDdend2() + referenceFR.getDdend1() ) / 2;
			lSoma1 = ( referenceS.getLsoma2() + referenceFR.getLsoma1() ) / 2;
			lDend1 = ( referenceS.getLdend2() + referenceFR.getLdend1() ) / 2;
			rmSoma1 = ( referenceS.getRmSoma2() + referenceFR.getRmSoma1() ) / 2;
			rmDend1 = ( referenceS.getRmDend2() + referenceFR.getRmDend1() ) / 2;
			//rheobase1 = ( referenceS.getRheobase2() + referenceFR.getRheobase1() ) / 2;
			threshold1 = ( referenceS.getThreshold2() + referenceFR.getThreshold1() ) / 2; 
			thresholdCa1 = ( referenceS.getThresholdCa2() + referenceFR.getThresholdCa1() ) / 2; 
			axonVelocity1 = ( referenceS.getAxonVelocity2() + referenceFR.getAxonVelocity1() ) / 2; 
			axonThreshold1 = ( referenceS.getAxonThreshold2() + referenceFR.getAxonThreshold1() ) / 2; 
			
			gNaMVO_Gmax1 = ( referenceS.getGNaMVO().getGmax() + referenceFR.getGNaMVO().getGmax() ) / 2;
			gNaHVO_Gmax1 = ( referenceS.getGNaHVO().getGmax() + referenceFR.getGNaHVO().getGmax() ) / 2;
			gKNVO_Gmax1 = ( referenceS.getGKNVO().getGmax() + referenceFR.getGKNVO().getGmax() ) / 2;
			gKQVO_Gmax1 = ( referenceS.getGKQVO().getGmax() + referenceFR.getGKQVO().getGmax() ) / 2;
			gCaPVO_Gmax1 = ( referenceS.getGCaPVO().getGmax() + referenceFR.getGCaPVO().getGmax() ) / 2;
			
			gNaMVO_Alpha1 = ( referenceS.getGNaMVO().getAlpha() + referenceFR.getGNaMVO().getAlpha() ) / 2;
			gNaHVO_Alpha1 = ( referenceS.getGNaHVO().getAlpha() + referenceFR.getGNaHVO().getAlpha() ) / 2;
			gKNVO_Alpha1 = ( referenceS.getGKNVO().getAlpha() + referenceFR.getGKNVO().getAlpha() ) / 2;
			gKQVO_Alpha1 = ( referenceS.getGKQVO().getAlpha() + referenceFR.getGKQVO().getAlpha() ) / 2;
			gCaPVO_Alpha1 = ( referenceS.getGCaPVO().getAlpha() + referenceFR.getGCaPVO().getAlpha() ) / 2;
			
			gNaMVO_Beta1 = ( referenceS.getGNaMVO().getBeta() + referenceFR.getGNaMVO().getBeta() ) / 2;
			gNaHVO_Beta1 = ( referenceS.getGNaHVO().getBeta() + referenceFR.getGNaHVO().getBeta() ) / 2;
			gKNVO_Beta1 = ( referenceS.getGKNVO().getBeta() + referenceFR.getGKNVO().getBeta() ) / 2;
			gKQVO_Beta1 = ( referenceS.getGKQVO().getBeta() + referenceFR.getGKQVO().getBeta() ) / 2;
			gCaPVO_Beta1 = ( referenceS.getGCaPVO().getBeta() + referenceFR.getGCaPVO().getBeta() ) / 2;

			dSoma2 = ( referenceFR.getDsoma2() + referenceFF.getDsoma1() ) / 2;
			dDend2 = ( referenceFR.getDdend2() + referenceFF.getDdend1() ) / 2;
			lSoma2 = ( referenceFR.getLsoma2() + referenceFF.getLsoma1() ) / 2;
			lDend2 = ( referenceFR.getLdend2() + referenceFF.getLdend1() ) / 2;
			rmSoma2 = ( referenceFR.getRmSoma2() + referenceFF.getRmSoma1() ) / 2;
			rmDend2 = ( referenceFR.getRmDend2() + referenceFF.getRmDend1() ) / 2;
			//rheobase2 = ( referenceFR.getRheobase2() + referenceFF.getRheobase1() ) / 2;
			threshold2 = ( referenceFR.getThreshold2() + referenceFF.getThreshold1() ) / 2;
			thresholdCa2 = ( referenceFR.getThresholdCa2() + referenceFF.getThresholdCa1() ) / 2;
			axonVelocity2 = ( referenceFR.getAxonVelocity2() + referenceFF.getAxonVelocity1() ) / 2;
			axonThreshold2 = ( referenceFR.getAxonThreshold2() + referenceFF.getAxonThreshold1() ) / 2;
			
			gNaMVO_Gmax2 = ( referenceFR.getGNaMVO().getGmax() + referenceFF.getGNaMVO().getGmax() ) / 2;
			gNaHVO_Gmax2 = ( referenceFR.getGNaHVO().getGmax() + referenceFF.getGNaHVO().getGmax() ) / 2;
			gKNVO_Gmax2 = ( referenceFR.getGKNVO().getGmax() + referenceFF.getGKNVO().getGmax() ) / 2;
			gKQVO_Gmax2 = ( referenceFR.getGKQVO().getGmax() + referenceFF.getGKQVO().getGmax() ) / 2;
			gCaPVO_Gmax2 = ( referenceFR.getGCaPVO().getGmax() + referenceFF.getGCaPVO().getGmax() ) / 2;
			
			gNaMVO_Alpha2 = ( referenceFR.getGNaMVO().getAlpha() + referenceFF.getGNaMVO().getAlpha() ) / 2;
			gNaHVO_Alpha2 = ( referenceFR.getGNaHVO().getAlpha() + referenceFF.getGNaHVO().getAlpha() ) / 2;
			gKNVO_Alpha2 = ( referenceFR.getGKNVO().getAlpha() + referenceFF.getGKNVO().getAlpha() ) / 2;
			gKQVO_Alpha2 = ( referenceFR.getGKQVO().getAlpha() + referenceFF.getGKQVO().getAlpha() ) / 2;
			gCaPVO_Alpha2 = ( referenceFR.getGCaPVO().getAlpha() + referenceFF.getGCaPVO().getAlpha() ) / 2;
		
			gNaMVO_Beta2 = ( referenceFR.getGNaMVO().getBeta() + referenceFF.getGNaMVO().getBeta() ) / 2;
			gNaHVO_Beta2 = ( referenceFR.getGNaHVO().getBeta() + referenceFF.getGNaHVO().getBeta() ) / 2;
			gKNVO_Beta2 = ( referenceFR.getGKNVO().getBeta() + referenceFF.getGKNVO().getBeta() ) / 2;
			gKQVO_Beta2 = ( referenceFR.getGKQVO().getBeta() + referenceFF.getGKQVO().getBeta() ) / 2;
			gCaPVO_Beta2 = ( referenceFR.getGCaPVO().getBeta() + referenceFF.getGCaPVO().getBeta() ) / 2;

		}
		else if( reference.getType().equals( ReMoto.FF ) )
		{
			dSoma1 = ( referenceFR.getDsoma2() + referenceFF.getDsoma1() ) / 2;
			dDend1 = ( referenceFR.getDdend2() + referenceFF.getDdend1() ) / 2;
			lSoma1 = ( referenceFR.getLsoma2() + referenceFF.getLsoma1() ) / 2;
			lDend1 = ( referenceFR.getLdend2() + referenceFF.getLdend1() ) / 2;
			rmSoma1 = ( referenceFR.getRmSoma2() + referenceFF.getRmSoma1() ) / 2;
			rmDend1 = ( referenceFR.getRmDend2() + referenceFF.getRmDend1() ) / 2;
			//rheobase1 = ( referenceFR.getRheobase2() + referenceFF.getRheobase1() ) / 2;
			threshold1 = ( referenceFR.getThreshold2() + referenceFF.getThreshold1() ) / 2;
			thresholdCa1 = ( referenceFR.getThresholdCa2() + referenceFF.getThresholdCa1() ) / 2;
			axonVelocity1 = ( referenceFR.getAxonVelocity2() + referenceFF.getAxonVelocity1() ) / 2;
			axonThreshold1 = ( referenceFR.getAxonThreshold2() + referenceFF.getAxonThreshold1() ) / 2;
			
			gNaMVO_Gmax1 = ( referenceFR.getGNaMVO().getGmax() + referenceFF.getGNaMVO().getGmax() ) / 2;
			gNaHVO_Gmax1 = ( referenceFR.getGNaHVO().getGmax() + referenceFF.getGNaHVO().getGmax() ) / 2;
			gKNVO_Gmax1 = ( referenceFR.getGKNVO().getGmax() + referenceFF.getGKNVO().getGmax() ) / 2;
			gKQVO_Gmax1 = ( referenceFR.getGKQVO().getGmax() + referenceFF.getGKQVO().getGmax() ) / 2;
			gCaPVO_Gmax1 = ( referenceFR.getGCaPVO().getGmax() + referenceFF.getGCaPVO().getGmax() ) / 2;
			
			gNaMVO_Alpha1 = ( referenceFR.getGNaMVO().getAlpha() + referenceFF.getGNaMVO().getAlpha() ) / 2;
			gNaHVO_Alpha1 = ( referenceFR.getGNaHVO().getAlpha() + referenceFF.getGNaHVO().getAlpha() ) / 2;
			gKNVO_Alpha1 = ( referenceFR.getGKNVO().getAlpha() + referenceFF.getGKNVO().getAlpha() ) / 2;
			gKQVO_Alpha1 = ( referenceFR.getGKQVO().getAlpha() + referenceFF.getGKQVO().getAlpha() ) / 2;
			gCaPVO_Alpha1 = ( referenceFR.getGCaPVO().getAlpha() + referenceFF.getGCaPVO().getAlpha() ) / 2;
			
			gNaMVO_Beta1 = ( referenceFR.getGNaMVO().getBeta() + referenceFF.getGNaMVO().getBeta() ) / 2;
			gNaHVO_Beta1 = ( referenceFR.getGNaHVO().getBeta() + referenceFF.getGNaHVO().getBeta() ) / 2;
			gKNVO_Beta1 = ( referenceFR.getGKNVO().getBeta() + referenceFF.getGKNVO().getBeta() ) / 2;
			gKQVO_Beta1 = ( referenceFR.getGKQVO().getBeta() + referenceFF.getGKQVO().getBeta() ) / 2;
			gCaPVO_Beta1 = ( referenceFR.getGCaPVO().getBeta() + referenceFF.getGCaPVO().getBeta() ) / 2;

			dSoma2 = referenceFF.getDsoma2();
			dDend2 = referenceFF.getDdend2();
			lSoma2 = referenceFF.getLsoma2();
			lDend2 = referenceFF.getLdend2();
			rmSoma2 = referenceFF.getRmSoma2();
			rmDend2 = referenceFF.getRmDend2();
			//rheobase2 = referenceFF.getRheobase2();
			threshold2 = referenceFF.getThreshold2();
			thresholdCa2 = referenceFF.getThresholdCa2();
			axonVelocity2 = referenceFF.getAxonVelocity2();
			axonThreshold2 = referenceFF.getAxonThreshold2();
			
			gNaMVO_Gmax2 = referenceFF.getGNaMVO().getGmax();
			gNaHVO_Gmax2 = referenceFF.getGNaHVO().getGmax();
			gKNVO_Gmax2 = referenceFF.getGKNVO().getGmax();
			gKQVO_Gmax2 = referenceFF.getGKQVO().getGmax();
			gCaPVO_Gmax2 = referenceFF.getGCaPVO().getGmax();
			
			gNaMVO_Alpha2 = referenceFF.getGNaMVO().getAlpha();
			gNaHVO_Alpha2 = referenceFF.getGNaHVO().getAlpha();
			gKNVO_Alpha2 = referenceFF.getGKNVO().getAlpha();
			gKQVO_Alpha2 = referenceFF.getGKQVO().getAlpha();
			gCaPVO_Alpha2 = referenceFF.getGCaPVO().getAlpha();
			
			gNaMVO_Beta2 = referenceFF.getGNaMVO().getBeta();
			gNaHVO_Beta2 = referenceFF.getGNaHVO().getBeta();
			gKNVO_Beta2 = referenceFF.getGKNVO().getBeta();
			gKQVO_Beta2 = referenceFF.getGKQVO().getBeta();
			gCaPVO_Beta2 = referenceFF.getGCaPVO().getBeta();
		}
		
		String distribution = ReMoto.gradualGaussian;
		
		if( ReMoto.uniform.equals( distribution ) )
		{
			pattern.setAxonThreshold1( Bias.uniform(referenceFF.getAxonThreshold2(), referenceS.getAxonThreshold1()) );
			pattern.setAxonVelocity1( Bias.uniform(referenceFF.getAxonVelocity2(), referenceS.getAxonVelocity1() ) );
		}
		else if( distribution.equals( ReMoto.gradualGaussian ) )
		{
			pattern.setAxonThreshold1( Bias.gradualGaussian(axonThreshold1, axonThreshold2, bias, conf.getMiscellaneous( ReMoto.axonThresholdSTD ) ) );
			pattern.setAxonVelocity1( Bias.gradualGaussian(axonVelocity1, axonVelocity2, bias, conf.getMiscellaneous( ReMoto.axonVelocitySTD ) ) );
		}
		else
		{
			pattern.setAxonThreshold1( Bias.gradual(axonThreshold1, axonThreshold2, bias ) );
			pattern.setAxonVelocity1( Bias.gradual(axonVelocity1, axonVelocity2, bias ) );
		}

		//pattern.setRheobase1( Bias.gradual(rheobase1, rheobase2, bias ) );
		pattern.setThreshold1( Bias.gradual(threshold1, threshold2, bias ) );
		pattern.setThresholdCa1( Bias.gradual(thresholdCa1, thresholdCa2, bias ) );
		pattern.setRmSoma1( Bias.gradual(rmSoma1, rmSoma2, bias ) );
		pattern.setRmDend1( Bias.gradual(rmDend1, rmDend2, bias ) );

		pattern.setDsoma1( Bias.gradual(dSoma1, dSoma2, bias ) / 1e4 );
		pattern.setDdend1( Bias.gradual(dDend1, dDend2, bias ) / 1e4 );
		pattern.setLsoma1( Bias.gradual(lSoma1, lSoma2, bias ) / 1e4 );
		pattern.setLdend1( Bias.gradual(lDend1, lDend2, bias ) / 1e4 );

		pattern.setRi( reference.getRi() );
		pattern.setCm( reference.getCm() );

		pattern.setGNaMVO( new ConductanceVO( reference.getGNaMVO() ) );
		pattern.setGNaHVO( new ConductanceVO( reference.getGNaHVO() ) );
		pattern.setGKNVO( new ConductanceVO( reference.getGKNVO() ) );
		pattern.setGKQVO( new ConductanceVO( reference.getGKQVO() ) );
		pattern.setGCaPVO( new ConductanceVO( reference.getGCaPVO() ) );

		pattern.getGNaMVO().setGmax( Bias.gradual(gNaMVO_Gmax1, gNaMVO_Gmax2, bias ) );
		pattern.getGNaHVO().setGmax( Bias.gradual(gNaHVO_Gmax1, gNaHVO_Gmax2, bias ) );
		pattern.getGKNVO().setGmax( Bias.gradual(gKNVO_Gmax1, gKNVO_Gmax2, bias ) );
		pattern.getGKQVO().setGmax( Bias.gradual(gKQVO_Gmax1, gKQVO_Gmax2, bias ) );
		pattern.getGCaPVO().setGmax( Bias.gradual(gCaPVO_Gmax1, gCaPVO_Gmax2, bias ) );
		
		pattern.getGNaMVO().setAlpha( Bias.gradual(gNaMVO_Alpha1, gNaMVO_Alpha2, bias ) );
		pattern.getGNaHVO().setAlpha( Bias.gradual(gNaHVO_Alpha1, gNaHVO_Alpha2, bias ) );
		pattern.getGKNVO().setAlpha( Bias.gradual(gKNVO_Alpha1, gKNVO_Alpha2, bias ) );
		pattern.getGKQVO().setAlpha( Bias.gradual(gKQVO_Alpha1, gKQVO_Alpha2, bias ) );
		pattern.getGCaPVO().setAlpha( Bias.gradual(gCaPVO_Alpha1, gCaPVO_Alpha2, bias ) );
		
		pattern.getGNaMVO().setBeta( Bias.gradual(gNaMVO_Beta1, gNaMVO_Beta2, bias ) );
		pattern.getGNaHVO().setBeta( Bias.gradual(gNaHVO_Beta1, gNaHVO_Beta2, bias ) );
		pattern.getGKNVO().setBeta( Bias.gradual(gKNVO_Beta1, gKNVO_Beta2, bias ) );
		pattern.getGKQVO().setBeta( Bias.gradual(gKQVO_Beta1, gKQVO_Beta2, bias ) );
		pattern.getGCaPVO().setBeta( Bias.gradual(gCaPVO_Beta1, gCaPVO_Beta2, bias ) );
		
		return pattern;
	}
	
}
