package br.remoto.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.OutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.RangeType;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



public class PlotBarGraph 
{

	static Font sans = new Font("SansSerif", Font.PLAIN, 15);
	static Font sansB = new Font("SansSerif", Font.BOLD, 19);
	Font times_17 = new Font("Times New Roman", Font.PLAIN, 17);
	Font arial_15 = new Font("Arial", Font.PLAIN, 15);
	
	public static void generate(DefaultCategoryDataset dataset, OutputStream out, String title, String xLabel, String yLabel) 
	{
		CategoryAxis xAxis = new CategoryAxis ();
		NumberAxis yAxis = new NumberAxis ();
    	
    	BarRenderer renderer = new BarRenderer();  
    	CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
    	
    	plot.setRangePannable(true);
		
    	//xAxis.setAutoRange(true);
    	xAxis.setLowerMargin(0.0);
    	xAxis.setTickLabelsVisible(true);
    	xAxis.setTickLabelFont(sans);
    	xAxis.setLabelFont(sansB);
    	xAxis.setLabelPaint(Color.gray.darker());
    	xAxis.setLabel(xLabel);
    	
    	yAxis.setRangeType( RangeType.POSITIVE );
    	yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    	yAxis.setAutoRangeIncludesZero(false);
    	yAxis.setTickLabelFont(sans);
    	yAxis.setLabelFont(sansB);
    	yAxis.setLabelPaint(Color.gray.darker());
    	yAxis.setLabel(yLabel);
    	yAxis.setRangeType( RangeType.POSITIVE );
    	
    	
		JFreeChart chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 23), plot, true);
		
		LegendTitle legend = chart.getLegend();
		legend.setItemFont(new Font("SansSerif", Font.PLAIN, 19));
		legend.setItemPaint(Color.gray.darker().darker());
		
		chart.setBackgroundPaint(Color.white);
    	
    	try 
		{
	    	ChartUtilities.writeChartAsJPEG(out, chart, 700, 500);		
		} 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
	}
	
}