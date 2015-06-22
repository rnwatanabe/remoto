package br.remoto.util;


import java.io.OutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarPlot 
{
	
	public static void generate(DefaultCategoryDataset dataset, OutputStream out, String title, String xLabel, String yLabel) 
	{
		JFreeChart chart = ChartFactory.createBarChart(
							title, // chart title
							xLabel, // domain axis label
							yLabel, // range axis label
							dataset, // data
							PlotOrientation.VERTICAL,
							true, // include legend
							true, // tooltips?
							false // URLs?
							);
	    
	    try 
		{
	    	ChartUtilities.writeChartAsJPEG(out, chart, 600, 450);		
		} 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
	}
}
