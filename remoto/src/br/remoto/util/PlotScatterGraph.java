package br.remoto.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.RangeType;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



public class PlotScatterGraph 
{

	static Font sans = new Font("SansSerif", Font.PLAIN, 15);
	static Font sansB = new Font("SansSerif", Font.BOLD, 19);
	Font times_17 = new Font("Times New Roman", Font.PLAIN, 17);
	Font arial_15 = new Font("Arial", Font.PLAIN, 15);
	
	public static void generate(XYSeriesCollection dataset, OutputStream out, String title, String xLabel, String yLabel, int chartHeigth, int chartWidth, String graphicType) 
	{
		NumberAxis xAxis = new NumberAxis ();
		NumberAxis yAxis = new NumberAxis ();
    	
		XYDotRenderer renderer = new XYDotRenderer();
        
    	
        
        xAxis.setAutoRange(true);

    	xAxis.setAutoRangeIncludesZero(false);
    	
    	xAxis.setTickLabelsVisible(true);
    	xAxis.setTickLabelFont(sans);
    	xAxis.setLabelFont(sansB);
    	xAxis.setLabelPaint(Color.gray.darker());
    	xAxis.setLabel(xLabel);
    	
    	
        if(graphicType.equals("neuronPositions")){
        	renderer.setDotWidth(10);
            renderer.setDotHeight(10);
            
            yAxis.setRange(0, 4);
    		yAxis.setVisible(false);
    	}
    	else{
    		renderer.setDotWidth(2);
            renderer.setDotHeight(2);
            renderer.setSeriesPaint(0, Color.red.darker().darker());
            
            
    		yAxis.setRangeType( RangeType.POSITIVE );
        	//yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        	yAxis.setNumberFormatOverride( new DecimalFormat("0") );
            yAxis.setAutoRange( true );
            yAxis.setAutoRangeMinimumSize( 11 );
        	yAxis.setTickLabelFont(sans);
        	yAxis.setLabelFont(sansB);
        	yAxis.setLabelPaint(Color.gray.darker());
        	yAxis.setLabel(yLabel);
        	yAxis.setRangeType( RangeType.POSITIVE );
    	}

        XYPlot xyPlot = new XYPlot((XYSeriesCollection) dataset, xAxis, yAxis, renderer);
        
        xyPlot.setDomainPannable(true);
    	xyPlot.setRangePannable(true);
		
    	
    	
    	
    	
    	
    	
    	xyPlot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
    	
    	JFreeChart chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 23), xyPlot, true);

        
    	LegendTitle legend = chart.getLegend();
    	
    	if(graphicType.equals("neuronPositions")){
    		legend.setItemFont(new Font("SansSerif", Font.PLAIN, 15));
    		legend.setItemPaint(Color.gray.darker().darker());
    	}
    	else{
    		legend.setItemFont(new Font("SansSerif", Font.PLAIN, 19));
    		legend.setItemPaint(Color.gray.darker().darker());
    	}
		
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
	
}