package br.remoto.util;

import java.awt.Color;
import java.io.File;
import java.io.OutputStream;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.RangeType;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class PlotScatter
{

	public static void generate(XYSeriesCollection dataset, OutputStream out, String title, String xLabel, String yLabel) 
    {
        XYDotRenderer renderer = new XYDotRenderer();
        renderer.setDotWidth(2);
        renderer.setDotHeight(2);
        renderer.setSeriesPaint( 0, new Color(150, 0, 0) );

        NumberAxis xAxis = new NumberAxis( xLabel );
        NumberAxis yAxis = new NumberAxis( yLabel );
        
        yAxis.setNumberFormatOverride( new DecimalFormat("0") );
        yAxis.setAutoRange( true );
        yAxis.setAutoRangeMinimumSize( 11 );
        yAxis.setRangeType( RangeType.POSITIVE );
        
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        plot.setOrientation(PlotOrientation.VERTICAL);

        // return a new chart containing the overlaid plot...
        JFreeChart chart = new JFreeChart( title, JFreeChart.DEFAULT_TITLE_FONT, plot, true );
	    
	    try 
		{
	    	ChartUtilities.writeChartAsJPEG( out, chart, 600, 450 );
	    } 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
    }

	public static void generate(XYSeries serie, String fileName, String title, String xLabel, String yLabel) 
    {
	    //cria o coleção de séries e adiciona as séries
	    XYSeriesCollection dataset = new XYSeriesCollection();
	    dataset.addSeries( serie );
        
        JFreeChart grafico = ChartFactory.createScatterPlot( title, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, false, false, false);
	    
	    try 
		{
	    	ChartUtilities.saveChartAsJPEG( new File( fileName ), grafico, 600, 450 );
	    } 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
    }
	
	public static void generate(XYSeriesCollection dataset, String fileName, String title, String xLabel, String yLabel) 
    {
	    
        JFreeChart grafico = ChartFactory.createScatterPlot( title, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, false, false, false);
	    
	    try 
		{
	    	ChartUtilities.saveChartAsJPEG( new File( fileName ), grafico, 600, 450 );
	    } 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
    }

}

