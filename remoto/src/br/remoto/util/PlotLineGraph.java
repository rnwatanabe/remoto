package br.remoto.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.OutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



public class PlotLineGraph 
{

	static Font sans = new Font("SansSerif", Font.PLAIN, 15);
	static Font sansB = new Font("SansSerif", Font.BOLD, 19);
	Font times_17 = new Font("Times New Roman", Font.PLAIN, 17);
	Font arial_15 = new Font("Arial", Font.PLAIN, 15);
	
	public static void generate(XYSeriesCollection dataset, OutputStream out, String title, String xLabel, String yLabel, int chartHeigth, int chartWidth) 
	{
		NumberAxis xAxis = new NumberAxis ();
		NumberAxis yAxis = new NumberAxis ();
    	
    	XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);  
    	XYPlot xyPlot = new XYPlot((XYSeriesCollection) dataset, xAxis, yAxis, renderer);
    	renderer.setSeriesPaint(0, Color.red.darker());
    	renderer.setSeriesStroke(0, new BasicStroke(1.3f));
    	
    	//XYSeries serie = dataset.getSeries(0);
    	//System.out.println("serie.getMaxX(): " + serie.getMaxX());
    	
    	
    	xyPlot.setDomainPannable(true);
    	xyPlot.setRangePannable(true);
		
    	xAxis.setAutoRange(true);
    	
    	xAxis.setAutoRangeIncludesZero(false);
    	
    	xAxis.setTickLabelsVisible(true);
    	xAxis.setTickLabelFont(sans);
    	xAxis.setLabelFont(sansB);
    	xAxis.setLabelPaint(Color.gray.darker());
    	xAxis.setLabel(xLabel);
    	
    	yAxis.setAutoRangeIncludesZero(false);
    	yAxis.setTickLabelFont(sans);
    	yAxis.setLabelFont(sansB);
    	yAxis.setLabelPaint(Color.gray.darker());
    	yAxis.setLabel(yLabel);
    	
    	JFreeChart chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 23), xyPlot, true);
    	
		LegendTitle legend = chart.getLegend();
		legend.setItemFont(new Font("SansSerif", Font.PLAIN, 19));
		legend.setItemPaint(Color.gray.darker().darker());
		
    	chart.setBackgroundPaint(Color.white);
    	
    	try 
		{
	    	ChartUtilities.writeChartAsJPEG(out, chart, chartWidth, chartHeigth);		
		} 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
	}
	
	
	public static void generate(XYSeriesCollection dataset, OutputStream out, String title, String xLabel, String yLabel, int chartHeigth, int chartWidth, int numSeries) 
	{
		NumberAxis xAxis = new NumberAxis ();
		NumberAxis yAxis = new NumberAxis ();
    	
    	XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);  
    	XYPlot xyPlot = new XYPlot((XYSeriesCollection) dataset, xAxis, yAxis, renderer);
    	
    	for(int i = 0; i <= numSeries; i++){
    		renderer.setSeriesStroke(i, new BasicStroke(1.3f));
    	}
    	
    	xyPlot.setDomainPannable(true);
    	xyPlot.setRangePannable(true);
		
    	xAxis.setAutoRange(true);
    	
    	xAxis.setAutoRangeIncludesZero(false);
    	
    	xAxis.setTickLabelsVisible(true);
    	xAxis.setTickLabelFont(sans);
    	xAxis.setLabelFont(sansB);
    	xAxis.setLabelPaint(Color.gray.darker());
    	xAxis.setLabel(xLabel);
    	
    	yAxis.setAutoRangeIncludesZero(false);
    	yAxis.setTickLabelFont(sans);
    	yAxis.setLabelFont(sansB);
    	yAxis.setLabelPaint(Color.gray.darker());
    	yAxis.setLabel(yLabel);
    	
    	JFreeChart chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD,17), xyPlot, true);
    	
		LegendTitle legend = chart.getLegend();
		legend.setItemFont(new Font("SansSerif", Font.PLAIN, 17));
		legend.setItemPaint(Color.gray.darker().darker());
		
    	chart.setBackgroundPaint(Color.white);
    	
    	try 
		{
	    	ChartUtilities.writeChartAsJPEG(out, chart, chartHeigth, chartWidth);		
		} 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
	}
	
}