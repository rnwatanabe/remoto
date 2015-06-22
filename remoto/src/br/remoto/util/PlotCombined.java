package br.remoto.util;

import java.awt.Color;
import java.io.OutputStream;
import java.text.DecimalFormat;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.RangeType;
import org.jfree.data.xy.XYSeriesCollection;


public class PlotCombined
{

	public static void generate(XYSeriesCollection dataset1, XYSeriesCollection dataset2, OutputStream out, String title, String xLabel, String yLabel1, String yLabel2) 
    {
        // Create subplot 1
        
        XYItemRenderer renderer1 = new StandardXYItemRenderer();

        NumberAxis rangeAxis1 = new NumberAxis( yLabel1 );
        XYPlot subplot1 = new XYPlot(dataset1, null, rangeAxis1, renderer1);
        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        
        // Create subplot 2
        
        XYDotRenderer renderer2 = new XYDotRenderer();
        renderer2.setDotWidth(2);
        renderer2.setDotHeight(2);
        renderer2.setSeriesPaint( 0, new Color(150, 0, 0) );

        NumberAxis rangeAxis2 = new NumberAxis( yLabel2 );
        rangeAxis2.setNumberFormatOverride( new DecimalFormat("0") );
        rangeAxis2.setAutoRange( true );
        rangeAxis2.setAutoRangeMinimumSize( 10 );
        rangeAxis2.setRangeType( RangeType.POSITIVE );

        XYPlot subplot2 = new XYPlot(dataset2, null, rangeAxis2, renderer2);
        subplot2.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

        // Parent plot...
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis( xLabel ));
        plot.setGap(10.0);
        
        // Add the subplots...
        plot.add(subplot1, 1);
        plot.add(subplot2, 2);
        plot.setOrientation(PlotOrientation.VERTICAL);

        // return a new chart containing the overlaid plot...
        JFreeChart chart = new JFreeChart( title, JFreeChart.DEFAULT_TITLE_FONT, plot, true );

	    try 
		{
	    	ChartUtilities.writeChartAsJPEG( out, chart, 600, 600 );
	    } 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
    }

	/*
	public static void generate(XYSeriesCollection dataset1, XYZDataset dataset2, OutputStream out, String title, String xLabel, String yLabel1, String yLabel2) 
    {
        // Create subplot 1
        
        XYItemRenderer renderer1 = new StandardXYItemRenderer();

        NumberAxis rangeAxis1 = new NumberAxis( yLabel1 );
        XYPlot subplot1 = new XYPlot(dataset1, null, rangeAxis1, renderer1);
        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        
        // Create subplot 2
        
        XYBubbleRenderer renderer2 = new XYBubbleRenderer();
	    renderer2.setSeriesPaint(0, new Color(200, 0, 0));
        renderer2.setItemLabelsVisible(true);
        renderer2.setPositiveItemLabelPosition(new ItemLabelPosition( ItemLabelAnchor.CENTER, TextAnchor.CENTER));

        NumberAxis rangeAxis2 = new NumberAxis( yLabel2 );
        rangeAxis2.setAutoRangeIncludesZero(false);

        XYPlot subplot2 = new XYPlot(dataset2, null, rangeAxis2, renderer2);
        subplot2.setForegroundAlpha(1);

        // Parent plot...
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis( xLabel ));
        plot.setGap(10.0);
        
        // Add the subplots...
        plot.add(subplot1, 1);
        plot.add(subplot2, 2);
        plot.setOrientation(PlotOrientation.VERTICAL);

        // return a new chart containing the overlaid plot...
        JFreeChart chart = new JFreeChart( title, JFreeChart.DEFAULT_TITLE_FONT, plot, true );

	    try 
		{
	    	ChartUtilities.writeChartAsJPEG( out, chart, 600, 600 );
	    } 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
    }
    */

}

