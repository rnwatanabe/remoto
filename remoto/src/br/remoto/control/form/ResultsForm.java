/*
 * Created on 01/10/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package br.remoto.control.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ResultsForm extends ActionForm
{
	private static final long serialVersionUID = 1L;

	private String task;
	
	private String cdEMG;
	private String cdForce;
	private String cdSignal;
	private String cdSpecification;
	private String cdSpikes;
	private String cdFiringRate;
	private String cdHistogram;
	private String cdProperties;
	private String whichSignal;
	
	private String cdSimulation = "vazio";
	
	private double xini;
	private double xfin;
	private double yini;
	private double yfin;
	
	private double xiniFFT;
	private double xfinFFT;
	private double yiniFFT;
	private double yfinFFT;
	
	private double samplingRateFFT;
	private int numOfPointsFFT;

	boolean periodicSignal;
	
	private double fcLow;
	private double fcHigh;
	private double sample;
	private int numberOfBins;
	
	private boolean withEMGfiltering;
	private boolean withEMGnoise;
	private boolean withEMGattenuation;
	
	private String filter;
	private String opt;
	
	private List elementsProperties 	= new ArrayList();
	private List listSamples 			= new ArrayList();
	
	private List resultTypes 			= new ArrayList();
	private List cdNeurons 				= new ArrayList();
	private List cdSpecifications		= new ArrayList();
	private List muscles 				= new ArrayList();
	private List subplots 				= new ArrayList();
	
	private List nuclei;
	private String cdNucleus;
	private String cdType;
	private String cdJointType;
	private String cdNeuralType;
	private String cdNeuron;
	private String cdGraphicType;
	private String cdMuscle;
	private String cdSubplot;
	
	private int numSubplots;
	private boolean holdOn;
	private boolean advancedSettings;
	private boolean analysis;
	
	private int chartHeigth;
	private int chartWidth;
	
	private int titleSize;
	private int xLabelFontSize;
	private int yLabelFontSize;
	private int xLabelNumberSize;
	private int yLabelNumberSize;
	private int legendFontSize;
	private String graphColor;
	private boolean darker;
	private boolean randomColors;
	
	
	private String titleLabel;
	private String xLabel;
	private String yLabel;
	private String legendLabel;
	
	private String cdAnalysis;
	private boolean numOfPoints;
	private boolean mean;
	private boolean variance;
	private boolean std;
	private boolean CV;
	private boolean min;
	private boolean max;
	
    public ResultsForm()
	{
		super();
	}

	public void reset(ActionMapping map, HttpServletRequest req)
	{
		super.reset(map, req);
	}
	
	public ActionErrors validate(ActionMapping map, HttpServletRequest req)
	{
		return super.validate(map, req);
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public double getXfin() {
		return xfin;
	}

	public void setXfin(double xfin) {
		this.xfin = xfin;
	}

	public double getXini() {
		return xini;
	}

	public void setXini(double xini) {
		this.xini = xini;
	}

	public double getYfin() {
		return yfin;
	}

	public void setYfin(double yfin) {
		this.yfin = yfin;
	}

	public double getYini() {
		return yini;
	}

	public void setYini(double yini) {
		this.yini = yini;
	}

	public String getCdEMG() {
		return cdEMG;
	}

	public void setCdEMG(String cdEMG) {
		this.cdEMG = cdEMG;
	}

	public String getCdForce() {
		return cdForce;
	}

	public void setCdForce(String cdForce) {
		this.cdForce = cdForce;
	}

	public String getCdSpikes() {
		return cdSpikes;
	}

	public void setCdSpikes(String cdSpikes) {
		this.cdSpikes = cdSpikes;
	}

	public String getCdSignal() {
		return cdSignal;
	}

	public void setCdSignal(String cdVm) {
		this.cdSignal = cdVm;
	}
	
	
	
	public double getSample() {
		return sample;
	}

	public void setSample(double sample) {
		this.sample = sample;
	}

	public List getListSamples() {
		return listSamples;
	}

	public void setListSamples(List listSamples) {
		this.listSamples = listSamples;
	}

	public double getFcHigh() {
		return fcHigh;
	}

	public void setFcHigh(double fcHigh) {
		this.fcHigh = fcHigh;
	}

	public double getFcLow() {
		return fcLow;
	}

	public void setFcLow(double fcLow) {
		this.fcLow = fcLow;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	public String getCdNucleus() {
		return cdNucleus;
	}

	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}

	public List getNuclei() {
		return nuclei;
	}

	public void setNuclei(List nuclei) {
		this.nuclei = nuclei;
	}

	public String getWhichSignal() {
		return whichSignal;
	}

	public void setWhichSignal(String whichVm) {
		this.whichSignal = whichVm;
	}
	/*
	public List getElementsConductance() {
		return elementsConductance;
	}

	public void setElementsConductance(List elementsConductance) {
		this.elementsConductance = elementsConductance;
	}
	*/
	public String getCdSpecification() {
		return cdSpecification;
	}

	public void setCdSpecification(String cdSpecification) {
		this.cdSpecification = cdSpecification;
	}

	public String getCdHistogram() {
		return cdHistogram;
	}

	public void setCdHistogram(String cdHistogram) {
		this.cdHistogram = cdHistogram;
	}

	public int getNumberOfBins() {
		return numberOfBins;
	}

	public void setNumberOfBins(int numberOfBins) {
		this.numberOfBins = numberOfBins;
	}
	
	public boolean isWithEMGnoise() {
		return withEMGnoise;
	}

	public void setWithEMGnoise(boolean withEMGnoise) {
		this.withEMGnoise = withEMGnoise;
	}
	
	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getCdFiringRate() {
		return cdFiringRate;
	}

	public void setCdFiringRate(String cdFiringRate) {
		this.cdFiringRate = cdFiringRate;
	}
	
	public String getCdProperties() {
		return cdProperties;
	}

	public void setCdProperties(String cdProperties) {
		this.cdProperties = cdProperties;
	}

	public List getElementsProperties() {
		return elementsProperties;
	}

	public void setElementsProperties(List elementsProperties) {
		this.elementsProperties = elementsProperties;
	}
	
	public boolean isWithEMGattenuation() {
		return withEMGattenuation;
	}

	public void setWithEMGattenuation(boolean withEMGattenuation) {
		this.withEMGattenuation = withEMGattenuation;
	}
	
	
	public String getCdSimulation() {
		return cdSimulation;
	}

	public void setCdSimulation(String cdSimulation) {
		this.cdSimulation = cdSimulation;
	}
	
	public String getCdType() {
		return cdType;
	}

	public void setCdType(String cdType) {
		this.cdType = cdType;
	}

	public String getCdJointType() {
		return cdJointType;
	}

	public void setCdJointType(String cdJointType) {
		this.cdJointType = cdJointType;
	}

	public String getCdNeuralType() {
		return cdNeuralType;
	}

	public void setCdNeuralType(String cdNeuralType) {
		this.cdNeuralType = cdNeuralType;
	}

	public String getCdGraphicType() {
		return cdGraphicType;
	}

	public void setCdGraphicType(String cdGraphicType) {
		this.cdGraphicType = cdGraphicType;
	}

	public List getResultTypes() {
		return resultTypes;
	}

	public void setResultTypes(List resultTypes) {
		this.resultTypes = resultTypes;
	}

	public String getCdNeuron() {
		return cdNeuron;
	}

	public void setCdNeuron(String cdNeuron) {
		this.cdNeuron = cdNeuron;
	}

	public List getCdNeurons() {
		return cdNeurons;
	}

	public void setCdNeurons(List cdNeurons) {
		this.cdNeurons = cdNeurons;
	}

	public String getCdMuscle() {
		return cdMuscle;
	}

	public void setCdMuscle(String cdMuscle) {
		
		if(cdNeuralType != null){
			if(cdNeuralType.equals("ins")){
				
				if(cdMuscle.equals("SOL") || cdMuscle.equals("SOL") || cdMuscle.equals("SOL"))
					setCdNucleus("IN_ext");
				else if(cdMuscle.equals("TA"))
					setCdNucleus("IN_flex");
			}
		}
		
		this.cdMuscle = cdMuscle;
	}

	public List getMuscles() {
		return muscles;
	}

	public void setMuscles(List muscles) {
		this.muscles = muscles;
	}

	public int getNumSubplots() {
		return numSubplots;
	}

	public void setNumSubplots(int numSubplots) {
		this.numSubplots = numSubplots;
	}

	public List getSubplots() {
		return subplots;
	}

	public void setSubplots(List subplots) {
		this.subplots = subplots;
	}

	public String getCdSubplot() {
		return cdSubplot;
	}

	public void setCdSubplot(String cdSubplot) {
		this.cdSubplot = cdSubplot;
	}

	public boolean isHoldOn() {
		return holdOn;
	}

	public void setHoldOn(boolean holdOn) {
		this.holdOn = holdOn;
	}

	public int getChartHeigth() {
		return chartHeigth;
	}

	public void setChartHeigth(int chartHeigth) {
		this.chartHeigth = chartHeigth;
	}

	public int getChartWidth() {
		return chartWidth;
	}

	public void setChartWidth(int chartWidth) {
		this.chartWidth = chartWidth;
	}

	public boolean isAdvancedSettings() {
		return advancedSettings;
	}

	public void setAdvancedSettings(boolean advancedSettings) {
		this.advancedSettings = advancedSettings;
	}

	public int getTitleSize() {
		return titleSize;
	}

	public void setTitleSize(int titleSize) {
		this.titleSize = titleSize;
	}

	public int getxLabelFontSize() {
		return xLabelFontSize;
	}

	public void setxLabelFontSize(int xLabelFontSize) {
		this.xLabelFontSize = xLabelFontSize;
	}

	public int getyLabelFontSize() {
		return yLabelFontSize;
	}

	public void setyLabelFontSize(int yLabelFontSize) {
		this.yLabelFontSize = yLabelFontSize;
	}

	public int getLegendFontSize() {
		return legendFontSize;
	}

	public void setLegendFontSize(int legendFontSize) {
		this.legendFontSize = legendFontSize;
	}

	public String getGraphColor() {
		return graphColor;
	}

	public void setGraphColor(String graphColor) {
		this.graphColor = graphColor;
	}

	public boolean isDarker() {
		return darker;
	}

	public void setDarker(boolean darker) {
		this.darker = darker;
	}

	public int getxLabelNumberSize() {
		return xLabelNumberSize;
	}

	public void setxLabelNumberSize(int xLabelNumberSize) {
		this.xLabelNumberSize = xLabelNumberSize;
	}

	public int getyLabelNumberSize() {
		return yLabelNumberSize;
	}

	public void setyLabelNumberSize(int yLabelNumberSize) {
		this.yLabelNumberSize = yLabelNumberSize;
	}

	

	

	public boolean isRandomColors() {
		return randomColors;
	}

	public void setRandomColors(boolean randomColors) {
		this.randomColors = randomColors;
	}

	public String getTitleLabel() {
		return titleLabel;
	}

	public void setTitleLabel(String titleLabel) {
		this.titleLabel = titleLabel;
	}

	public String getxLabel() {
		return xLabel;
	}

	public void setxLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	public String getyLabel() {
		return yLabel;
	}

	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	public String getLegendLabel() {
		return legendLabel;
	}

	public void setLegendLabel(String legendLabel) {
		this.legendLabel = legendLabel;
	}

	public List getCdSpecifications() {
		return cdSpecifications;
	}

	public void setCdSpecifications(List cdSpecifications) {
		this.cdSpecifications = cdSpecifications;
	}

	public boolean isAnalysis() {
		return analysis;
	}

	public void setAnalysis(boolean analysis) {
		this.analysis = analysis;
	}

	public String getCdAnalysis() {
		return cdAnalysis;
	}

	public void setCdAnalysis(String cdAnalysis) {
		this.cdAnalysis = cdAnalysis;
	}

	public boolean isNumOfPoints() {
		return numOfPoints;
	}

	public void setNumOfPoints(boolean numOfPoints) {
		this.numOfPoints = numOfPoints;
	}

	public boolean isMean() {
		return mean;
	}

	public void setMean(boolean mean) {
		this.mean = mean;
	}

	public boolean isVariance() {
		return variance;
	}

	public void setVariance(boolean variance) {
		this.variance = variance;
	}

	public boolean isStd() {
		return std;
	}

	public void setStd(boolean std) {
		this.std = std;
	}

	public boolean isCV() {
		return CV;
	}

	public void setCV(boolean cV) {
		CV = cV;
	}

	public double getXiniFFT() {
		return xiniFFT;
	}

	public void setXiniFFT(double xiniFFT) {
		this.xiniFFT = xiniFFT;
	}

	public double getXfinFFT() {
		return xfinFFT;
	}

	public void setXfinFFT(double xfinFFT) {
		this.xfinFFT = xfinFFT;
	}

	public double getYiniFFT() {
		return yiniFFT;
	}

	public void setYiniFFT(double yiniFFT) {
		this.yiniFFT = yiniFFT;
	}

	public double getYfinFFT() {
		return yfinFFT;
	}

	public void setYfinFFT(double yfinFFT) {
		this.yfinFFT = yfinFFT;
	}

	public double getSamplingRateFFT() {
		return samplingRateFFT;
	}

	public void setSamplingRateFFT(double samplingRateFFT) {
		this.samplingRateFFT = samplingRateFFT;
	}

	public int getNumOfPointsFFT() {
		return numOfPointsFFT;
	}

	public void setNumOfPointsFFT(int numOfPointsFFT) {
		this.numOfPointsFFT = numOfPointsFFT;
	}
	
	public boolean isPeriodicSignal() {
		return periodicSignal;
	}

	public void setPeriodicSignal(boolean periodicSignal) {
		this.periodicSignal = periodicSignal;
	}

	public boolean isMin() {
		return min;
	}

	public void setMin(boolean min) {
		this.min = min;
	}

	public boolean isMax() {
		return max;
	}

	public void setMax(boolean max) {
		this.max = max;
	}

	public boolean isWithEMGfiltering() {
		return withEMGfiltering;
	}

	public void setWithEMGfiltering(boolean withEMGfiltering) {
		this.withEMGfiltering = withEMGfiltering;
	}

	

}
