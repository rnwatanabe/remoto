package br.remoto.model.vo;

import java.io.Serializable;
import java.util.List;

import br.remoto.model.ReMoto;
import br.remoto.util.Coordenate;

public class ResultVO implements Serializable 
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
	
	private double sample;
	private double fcLow;
	private double fcHigh;
	private double binWidth;
	private int numberOfBins;
	
	private boolean withEMGfiltering;
	private boolean withEMGnoise;
	private boolean withEMGattenuation;
	
	private String filter;
	private String cdNucleus;
	private String cdMuscle;
	
	private int chartHeigth;
	private int chartWidth;
	
	private boolean advancedSettings;
	
	private int titleSize;
	private int xLabelFontSize;
	private int yLabelFontSize;
	private int xLabelNumberSize;
	private int yLabelNumberSize;
	private int legendFontSize;
	
	private String titleLabel;
	private String xLabel;
	private String yLabel;
	private String legendLabel;

	private String cdSubplot;
	
	private String graphColor;
	private boolean darker;
	private boolean randomColors;
	private boolean analysis;
	private String opt;
	private Coordenate coordenate = new Coordenate();
	private Coordenate coordenateFFT = new Coordenate();
	
	private String cdAnalysis;
	private boolean numOfPoints;
	private boolean mean;
	private boolean variance;
	private boolean std;
	private boolean CV;
	private boolean min;
	private boolean max;
	
	private double samplingRateFFT;
	private int numOfPointsFFT;
	
	private boolean periodicSignal;
	
	public ResultVO()
	{
		// Default values
		fcLow = 50;
		fcHigh = 1000;
		filter = ReMoto.FirstSecond;
		whichSignal = ReMoto.Vs;
		withEMGfiltering = true;
		withEMGnoise = true;
		withEMGattenuation = true;
	}
	
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
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
	public String getFilter() 
	{
		if( filter == null )
			filter = ReMoto.FirstSecond;
		
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	public Coordenate getCoordenate() {
		return coordenate;
	}
	public void setCoordenate(Coordenate coordenate) {
		this.coordenate = coordenate;
	}
	public String getWhichSignal() 
	{
		if( whichSignal == null )
			whichSignal = ReMoto.Vs;
		
		return whichSignal;
	}
	public void setWhichSignal(String whichVm) {
		this.whichSignal = whichVm;
	}
	
	public String getCdHistogram() {
		return cdHistogram;
	}
	
	
	public String getCdNucleus() {
		return cdNucleus;
	}

	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}
	
	
	public void setCdHistogram(String cdHistogram) {
		this.cdHistogram = cdHistogram;
	}

	public double getBinWidth() {
		return binWidth;
	}

	public void setBinWidth(double binWidth) {
		this.binWidth = binWidth;
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
	
	public boolean isWithEMGattenuation() {
		return withEMGattenuation;
	}

	public void setWithEMGattenuation(boolean withEMGattenuation) {
		this.withEMGattenuation = withEMGattenuation;
	}
	
	public String getCdMuscle() {
		return cdMuscle;
	}

	public void setCdMuscle(String cdMuscle) {
		this.cdMuscle = cdMuscle;
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

	public boolean isAdvancedSettings() {
		return advancedSettings;
	}

	public void setAdvancedSettings(boolean advancedSettings) {
		this.advancedSettings = advancedSettings;
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

	public String getCdSubplot() {
		return cdSubplot;
	}

	public void setCdSubplot(String cdSubplot) {
		this.cdSubplot = cdSubplot;
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

	public Coordenate getCoordenateFFT() {
		return coordenateFFT;
	}

	public void setCoordenateFFT(Coordenate coordenateFFT) {
		this.coordenateFFT = coordenateFFT;
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

	public String getCdSpecification() {
		return cdSpecification;
	}

	public void setCdSpecification(String cdSpecification) {
		this.cdSpecification = cdSpecification;
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
