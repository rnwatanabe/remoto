package br.remoto.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.OutputStream;
import java.text.DecimalFormat;

import javax.swing.Renderer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.RangeType;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;


public class PlotCombinedGraph
{
	static Font sans = new Font("SansSerif", Font.PLAIN, 15);
	static Font sansB = new Font("SansSerif", Font.BOLD, 19);
	static Font sansBXAxisLabel;
	static Font sansBYAxisLabel;
	static Font sansXAxisNumber;
	static Font sansYAxisNumber;
	static Font sansB_small = new Font("SansSerif", Font.BOLD, 17);
	Font times_17 = new Font("Times New Roman", Font.PLAIN, 17);
	Font arial_15 = new Font("Arial", Font.PLAIN, 15);
	
	public static void generate(XYSeriesCollection dataset1, XYSeriesCollection dataset2, OutputStream out, String title, String xLabel, String yLabel1, String yLabel2, int chartHeigth, int chartWidth) 
    {
		NumberAxis xAxis = new NumberAxis ();
		NumberAxis yAxis1 = new NumberAxis ();
		NumberAxis yAxis2 = new NumberAxis ();
    	
		
		XYDotRenderer renderer1 = new XYDotRenderer();
		renderer1.setDotWidth(3);
		renderer1.setDotHeight(3);
		renderer1.setSeriesPaint(0, Color.red.darker().darker());
        XYPlot xyPlot1 = new XYPlot((XYSeriesCollection) dataset2, xAxis, yAxis1, renderer1);
        xyPlot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
    	
    	XYItemRenderer renderer2 = new XYLineAndShapeRenderer(true, false);  
    	XYPlot xyPlot2 = new XYPlot((XYSeriesCollection) dataset1, xAxis, yAxis2, renderer2);
    	renderer2.setSeriesPaint(0, Color.red.darker());
    	renderer2.setSeriesStroke(0, new BasicStroke(1.3f));
    	
    	CombinedDomainXYPlot  plot = new CombinedDomainXYPlot (xAxis);
		
    	
		plot.add(xyPlot2, 2);
		plot.add(xyPlot1, 3);
		
		plot.setDomainPannable(true);
		plot.setRangePannable(true);
		    	
		
    	xAxis.setAutoRange(true);

    	xAxis.setAutoRangeIncludesZero(false);
    	
    	xAxis.setTickLabelsVisible(true);
    	xAxis.setTickLabelFont(sans);
    	xAxis.setLabelFont(sansB);
    	xAxis.setLabelPaint(Color.gray.darker());
    	xAxis.setLabel(xLabel);
    	
    	yAxis1.setRangeType( RangeType.POSITIVE );
    	yAxis1.setAutoRangeIncludesZero(false);
    	yAxis1.setTickLabelFont(sans);
    	yAxis1.setLabelFont(sansB);
    	yAxis1.setLabelPaint(Color.gray.darker());
    	yAxis1.setLabel(yLabel1);
    	yAxis1.setRangeType( RangeType.POSITIVE );
    	
    	yAxis2.setAutoRangeIncludesZero(false);
    	yAxis2.setTickLabelFont(sans);
    	yAxis2.setLabelFont(sansB);
    	yAxis2.setLabelPaint(Color.gray.darker());
    	yAxis2.setLabel(yLabel2);
    	
    	
    	JFreeChart chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 23), plot, true);


		LegendTitle legend = chart.getLegend();
		legend.setItemFont(new Font("SansSerif", Font.PLAIN, 19));
		legend.setItemPaint(Color.gray.darker().darker());
		
    	chart.setBackgroundPaint(Color.white);

	    try 
		{
	    	ChartUtilities.writeChartAsJPEG( out, chart, chartWidth, chartHeigth);
	    } 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
    }
	
	
	public static void generate(XYSeriesCollection[] datasets, OutputStream out, String title, String xLabel, String yLabel[], int chartWidth, int chartHeigth, int numOfSubplots, int xAxisRange,
								int titleSize, int xLabelFontSize, int yLabelFontSize, int xLabelNumberSize, int yLabelNumberSize, int legendFontSize, String graphColor,
								boolean darker, boolean randomColors, String graphicType[], boolean analysis, boolean store, String path) 
    {
		NumberAxis xAxis = new NumberAxis ();
    	
		NumberAxis[] yAxis = new NumberAxis[numOfSubplots];
		XYPlot[] xyPlot = new XYPlot[numOfSubplots];
		
		sansBXAxisLabel = new Font("SansSerif", Font.BOLD, xLabelFontSize);
		sansBYAxisLabel = new Font("SansSerif", Font.BOLD, yLabelFontSize);
		sansXAxisNumber = new Font("SansSerif", Font.PLAIN, xLabelNumberSize);
		sansYAxisNumber = new Font("SansSerif", Font.PLAIN, yLabelNumberSize);
		
		Color color = null;
		Color[] colors = null;
		
		float r;
		float g;
		float b;
		
		
		
		
		if(randomColors == true){
			colors = new Color[numOfSubplots];
			
		}
		else{
			
			if(graphColor.equals("red")) 			color = Color.red;
			else if(graphColor.equals("blue"))		color = Color.blue;
			else if(graphColor.equals("green"))		color = Color.green;
			else if(graphColor.equals("yellow"))	color = Color.yellow;
			else if(graphColor.equals("black"))		color = Color.black;
			else if(graphColor.equals("cyan"))		color = Color.cyan;
			else color = Color.red;
			
			if(darker == true)	color = color.darker();
		}
		
		
		AbstractRenderer[] renderer = new AbstractRenderer[numOfSubplots];  
		
    	
    	CombinedDomainXYPlot  plot = new CombinedDomainXYPlot (xAxis);
    	
    	for(int k = 0; k < numOfSubplots; k++){
    		
    		if(graphicType[k].equals("line")){
				renderer[k] = (AbstractRenderer) new XYLineAndShapeRenderer(true, false); 
			}
			else if(graphicType[k].equals("scatter")){
				renderer[k] = (AbstractRenderer) new XYDotRenderer(); 
			}
    		
    		
    		if (randomColors == true){
    			r = (float) Math.random();
    			g = (float) Math.random();
    			b = (float) Math.random();
    			colors[k] = new Color(r, g, b);
    			if(darker == true)	colors[k] = colors[k].darker();
    			
        		((AbstractRenderer) renderer[k]).setSeriesPaint(0, colors[k]);
            	((AbstractRenderer) renderer[k]).setSeriesStroke(0, new BasicStroke(1.3f));
    		}
    		else{
    			
        		((AbstractRenderer) renderer[k]).setSeriesPaint(0, color);
            	((AbstractRenderer) renderer[k]).setSeriesStroke(0, new BasicStroke(1.3f));
            	
    		}
    		
    		//((AbstractRenderer) renderer[k]).setBaseLegendTextPaint(Color.blue);
    		//((AbstractRenderer) renderer[k]).setAutoPopulateSeriesFillPaint(false);
    		((AbstractRenderer) renderer[k]).setAutoPopulateSeriesPaint(false);
    		((AbstractRenderer) renderer[k]).setBaseFillPaint(Color.blue);
    		//((AbstractRenderer) renderer[k]).setSeriesItemLabelsVisible(4, false);
    		//((AbstractRenderer) renderer[k]).setSeriesItemLabelsVisible(2, false);
    		//((AbstractRenderer) renderer[k]).setSeriesCreateEntities(4, false);
    		//((AbstractRenderer) renderer[k]).setSeriesVisible(4, false);
    		((AbstractRenderer) renderer[k]).setSeriesVisibleInLegend(1, false);
    		((AbstractRenderer) renderer[k]).setSeriesVisibleInLegend(2, false);
    		
    		
    		yAxis[k] = new NumberAxis();
    		yAxis[k].setAutoRangeIncludesZero(false);
        	yAxis[k].setTickLabelFont(sansYAxisNumber);
        	yAxis[k].setLabelFont(sansBYAxisLabel);
        	yAxis[k].setLabelPaint(Color.gray.darker());
        	yAxis[k].setLabel(yLabel[k]);
        	
        	
        	
        	if(graphicType[k].equals("line")){
        		xyPlot[k] = new XYPlot((XYSeriesCollection) datasets[k], xAxis, yAxis[k], (XYItemRenderer) renderer[k]);
			}
			else if(graphicType[k].equals("scatter")){
				((XYDotRenderer) renderer[k]).setDotWidth(4);
				((XYDotRenderer) renderer[k]).setDotHeight(4);
				xyPlot[k] = new XYPlot((XYSeriesCollection) datasets[k], xAxis, yAxis[k], (XYDotRenderer) renderer[k]);
			}
        	
    		
    		
			
    		plot.add(xyPlot[k], 1);
    	}
		
		plot.setDomainPannable(true);
		plot.setRangePannable(true);
		
		
		    	
    	//xAxis.setAutoRange(true);
		//xAxis.setRangeWithMargins(0, xAxisRange);
    	
    	xAxis.setAutoRangeIncludesZero(false);
    	//xAxis.setUpperBound(xAxisRange);
    	//xAxis.setUpperMargin(0.5);
    	
    	xAxis.setTickLabelsVisible(true);
    	xAxis.setTickLabelFont(sansXAxisNumber);
    	xAxis.setLabelFont(sansBXAxisLabel);
    	xAxis.setLabelPaint(Color.gray.darker());
    	xAxis.setLabel(xLabel);
    	
    	
    	
    	JFreeChart chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, titleSize), plot, true);

    	
		LegendTitle legend = chart.getLegend();
		
		legend.setItemFont(new Font("SansSerif", Font.PLAIN, legendFontSize));
		legend.setItemPaint(Color.gray.darker().darker());
		
		//System.out.println("legend.getID(): " + legend.getID());
		
		
    	chart.setBackgroundPaint(Color.white);

	    try 
		{
	    	ChartUtilities.writeChartAsJPEG( out, chart, chartWidth, chartHeigth);
	    } 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
    }
	
	

	// Método usado apenas para o Nerve Stimulation
	public static void generate(XYSeriesCollection dataset1, XYSeriesCollection dataset2, OutputStream out, String title, String xLabel, String yLabel1, String yLabel2, int chartHeigth, int chartWidth, int numSeries) 
    {
		NumberAxis xAxis = new NumberAxis ();
		NumberAxis yAxis1 = new NumberAxis ();
		NumberAxis yAxis2 = new NumberAxis ();
    	
		XYItemRenderer renderer1 = new XYLineAndShapeRenderer(true, false);  

		
        XYPlot xyPlot1 = new XYPlot((XYSeriesCollection) dataset1, xAxis, yAxis1, renderer1);
        xyPlot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
    	
    	XYItemRenderer renderer2 = new XYLineAndShapeRenderer(true, false);  
    	XYPlot xyPlot2 = new XYPlot((XYSeriesCollection) dataset2, xAxis, yAxis2, renderer2);
    	//renderer2.setSeriesPaint(0, Color.red.darker());
    	//renderer2.setSeriesStroke(0, new BasicStroke(1.3f));
    	
    	for(int i = 0; i <= numSeries; i++){
    		renderer1.setSeriesStroke(i, new BasicStroke(1.3f));
    		renderer2.setSeriesStroke(i, new BasicStroke(1.3f));
    	}
    	
    	renderer1.setSeriesPaint(0, Color.red);
    	renderer1.setSeriesPaint(1, Color.blue);
    	renderer2.setSeriesPaint(0, Color.green.darker());
    	renderer2.setSeriesPaint(1, Color.yellow.darker());
    	
    	CombinedDomainXYPlot  plot = new CombinedDomainXYPlot (xAxis);
		
    	plot.add(xyPlot1, 3);
		plot.add(xyPlot2, 2);
		
		
		plot.setDomainPannable(true);
		plot.setRangePannable(true);
		    	
		
    	xAxis.setAutoRange(true);

    	xAxis.setAutoRangeIncludesZero(false);
    	
    	xAxis.setTickLabelsVisible(true);
    	xAxis.setTickLabelFont(sans);
    	xAxis.setLabelFont(sansB);
    	xAxis.setLabelPaint(Color.gray.darker());
    	xAxis.setLabel(xLabel);
    	
    	//yAxis1.setRangeType( RangeType.POSITIVE );
    	yAxis1.setAutoRangeIncludesZero(false);
    	yAxis1.setTickLabelFont(sans);
    	yAxis1.setLabelFont(sansB_small);
    	yAxis1.setLabelPaint(Color.gray.darker());
    	yAxis1.setLabel(yLabel1);
    	//yAxis1.setRangeType( RangeType.POSITIVE );
    	
    	yAxis2.setAutoRangeIncludesZero(true);
    	yAxis2.setTickLabelFont(sans);
    	yAxis2.setLabelFont(sansB_small);
    	yAxis2.setLabelPaint(Color.gray.darker());
    	yAxis2.setLabel(yLabel2);
    	
    	
    	JFreeChart chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 23), plot, true);


		LegendTitle legend = chart.getLegend();
		legend.setItemFont(new Font("SansSerif", Font.PLAIN, 19));
		legend.setItemPaint(Color.gray.darker().darker());
		
    	chart.setBackgroundPaint(Color.white);

	    try 
		{
	    	ChartUtilities.writeChartAsJPEG( out, chart, chartHeigth, chartWidth);
	    } 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
    }

}

