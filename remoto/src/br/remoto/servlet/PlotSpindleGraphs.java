/* -------------------------------
 /* -------------------------------
 * ServletDemo2ChartGenerator.java
 * -------------------------------
 * (C) Copyright 2002-2004, by Object Refinery Limited.
 *
 */

package br.remoto.servlet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


import br.remoto.spindle_simulator.Input;
import br.remoto.spindle_simulator.Spindle;

public class PlotSpindleGraphs extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	Spindle spindle 		= (Spindle)SpindleSimulation.spindle_simulations.get( 13 );
	
	Runtime runtime = Runtime.getRuntime();
	
	String input_type;
	
	Font sans = new Font("SansSerif", Font.PLAIN, 15);
	Font sansB = new Font("SansSerif", Font.BOLD, 19);
	
    public PlotSpindleGraphs(){}

    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	OutputStream out = response.getOutputStream();
        
    	spindle = (Spindle)SpindleSimulation.spindle_simulations.get( 13 );
    	
    	try {
    		String graph_type = request.getParameter("type");
    		input_type = request.getParameter("input_type");

    		
    		JFreeChart chart = null;
    		
    		if (graph_type.equals("input") || graph_type.equals("inputBottom") || graph_type.equals("InputStart")) {
    			
    			chart = createInputChart();
    			
    		}
    		else if (graph_type.equals("fusimotor")) {
        		chart = createFusimotorChart();
        	}
    		else if (graph_type.equals("tension")) {
    		chart = createTensionChart();
    		}
    		else if (graph_type.equals("primary")) {
        		chart = createPrimaryChart();
        	}
    		else if (graph_type.equals("secondary")) {
    		chart = createSecondaryChart();
    		}
    		if (chart != null) {
    		response.setContentType("image/png");
    		
    		if(graph_type.equals("InputStart"))ChartUtilities.writeChartAsPNG(out, chart, 800, 600);
    		
    		if (graph_type.equals("input") || graph_type.equals("fusimotor")) ChartUtilities.writeChartAsPNG(out, chart, 800, 600);
    		else{
    			if (graph_type.equals("inputBottom"))ChartUtilities.writeChartAsPNG(out, chart, 800, 300);
    			else ChartUtilities.writeChartAsPNG(out, chart, 800, 400);
    		}
    		
    		}
    	}
        catch (Exception e) 
        {
            System.err.println(e.toString());
        }
        finally 
        {
            out.close();
        }
    }
    
    
    
    private JFreeChart graphConfig(String title, NumberAxis xAxis, NumberAxis yAxis, XYPlot xyPlot){
		xyPlot.setDomainPannable(true);
    	xyPlot.setRangePannable(true);
		
    	xAxis.setAutoRange(true);
    	xAxis.setLowerMargin(0.0);
    	xAxis.setTickLabelsVisible(true);
    	xAxis.setTickLabelFont(sans);
    	xAxis.setLabelFont(sansB);
    	xAxis.setLabelPaint(Color.gray.darker());
    	
    	yAxis.setAutoRangeIncludesZero(false);
    	yAxis.setTickLabelFont(sans);
    	yAxis.setLabelFont(sansB);
    	yAxis.setLabelPaint(Color.gray.darker());
    	
    	JFreeChart chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 23), xyPlot, true);
    	
		LegendTitle legend = chart.getLegend();
		legend.setItemFont(new Font("SansSerif", Font.PLAIN, 19));
		legend.setItemPaint(Color.gray.darker().darker());
		
    	chart.setBackgroundPaint(Color.white);
    	
		return chart;
	}
	
    private JFreeChart createInputChart() {

    	double[] timeVector;
    	double[] stretchVector;
    	    	    	
    	Input input = null;
    	
    	if(input_type.equals("constant"))		input= (Input) SpindleSimulation.inputs.get( 0 );
		if(input_type.equals("ramp-and-hold"))	input= (Input) SpindleSimulation.inputs.get( 1 );
		if(input_type.equals("sinusoidal"))		input= (Input) SpindleSimulation.inputs.get( 2 );
		if(input_type.equals("triangular"))		input= (Input) SpindleSimulation.inputs.get( 3 );
    	
    	input.SimulateInput();
    	
    	timeVector = input.getTimeVector();
    	stretchVector = input.getStretchVector();
    	
    	
    	NumberAxis xAxis = new NumberAxis ("Time (s)");
    	NumberAxis yAxis = new NumberAxis ("Fascicle length (L0)");
    	XYSeries xySeries = new XYSeries ("Fascicle length (L0)");
    	    	    	
    	for (int i = 0; i < timeVector.length; i++){
    		xySeries.add(timeVector[i], stretchVector[i]);
    	}
    	    	
    	XYSeriesCollection xyDataset = new XYSeriesCollection (xySeries);
    	
    	XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
    	XYPlot xyPlot = new XYPlot(xyDataset, xAxis, yAxis, renderer);
    	renderer.setSeriesPaint(0, Color.red.darker());
    	renderer.setSeriesStroke(0, new BasicStroke(1.3f));
    	
    	
    	return graphConfig("Fascicle Length", xAxis, yAxis, xyPlot);
    }
    
    
    

    
    private JFreeChart createFusimotorChart() {
    	
    	double[] timeVector;
    	double[] f_dynamic_bag1Vector;
    	double[] f_static_bag2Vector;
    	double[] f_static_chainVector;
    	    
    	timeVector = spindle.getTimeVector();
    	f_dynamic_bag1Vector = spindle.getF_dynamic_bag1Vector();
    	f_static_bag2Vector = spindle.getF_static_bag2Vector();
    	f_static_chainVector = spindle.getF_static_chainVector();
    	
    	 
    	NumberAxis xAxis = new NumberAxis ("Time (s)");
    	NumberAxis yAxis = new NumberAxis ("Fusimotor Activation (normalized)");
    	
    	XYSeries bag1_series = new XYSeries ("Bag 1 fiber");
    	XYSeries bag2_series = new XYSeries ("Bag 2 fiber");
    	XYSeries chain_series = new XYSeries ("Chain fiber");
    	
    	
    	for (int i = 0; i < timeVector.length; i++){
    		bag1_series.add(timeVector[i], f_dynamic_bag1Vector[i]);
    		bag2_series.add(timeVector[i], f_static_bag2Vector[i]);
    		chain_series.add(timeVector[i], f_static_chainVector[i]);
    	}
    	
    	XYSeriesCollection xyDataset = new XYSeriesCollection ();
    	xyDataset.addSeries(bag1_series);
    	xyDataset.addSeries(bag2_series);
    	xyDataset.addSeries(chain_series);
    	
    	
    	XYItemRenderer fusimotorRenderer = new XYLineAndShapeRenderer(true, false);  
    	XYPlot xyPlot = new XYPlot(xyDataset, xAxis, yAxis, fusimotorRenderer);
    	fusimotorRenderer.setSeriesPaint(0, Color.red.darker());
    	fusimotorRenderer.setSeriesPaint(1, Color.blue.darker());
    	fusimotorRenderer.setSeriesPaint(2, Color.green.darker());
    	fusimotorRenderer.setSeriesStroke(0, new BasicStroke(1.3f));
    	fusimotorRenderer.setSeriesStroke(1, new BasicStroke(1.3f));
    	fusimotorRenderer.setSeriesStroke(2, new BasicStroke(1.3f));
		
    	
    	return graphConfig("Fusimotor Activation", xAxis, yAxis, xyPlot);
    }

    

    private JFreeChart createTensionChart() {
	
    	double[] timeVector;
    	double[] tension_bag1Vector;
    	double[] tension_bag2Vector;
    	double[] tension_chainVector;
    	
    	timeVector = spindle.getTimeVector();
    	tension_bag1Vector = spindle.getTension_bag1Vector();
    	tension_bag2Vector = spindle.getTension_bag2Vector();
    	tension_chainVector = spindle.getTension_chainVector();
    	
    	 
    	NumberAxis xAxis = new NumberAxis ("Time (s)");
    	NumberAxis yAxis = new NumberAxis ("Intrafusal Fiber Tension (Force unit)");
    	
    	XYSeries bag1_Series = new XYSeries ("Bag1 Tension");
    	XYSeries bag2_Series = new XYSeries ("Bag2 Tension");
    	XYSeries chain_Series = new XYSeries ("Chain Tension");
    	
    	
    	for (int i = 0; i < timeVector.length; i++){
    		bag1_Series.add(timeVector[i], tension_bag1Vector[i]);
    		bag2_Series.add(timeVector[i], tension_bag2Vector[i]);
    		chain_Series.add(timeVector[i], tension_chainVector[i]);
    		
    	}
    	 
    	XYSeriesCollection xyDataset = new XYSeriesCollection ();
    	xyDataset.addSeries(bag1_Series);
    	xyDataset.addSeries(bag2_Series);
    	xyDataset.addSeries(chain_Series);

    	XYItemRenderer tensionRenderer = new XYLineAndShapeRenderer(true, false);
    	XYPlot xyPlot = new XYPlot(xyDataset, xAxis, yAxis, tensionRenderer);
    	tensionRenderer.setSeriesPaint(0, Color.magenta.darker());
    	tensionRenderer.setSeriesPaint(1, Color.blue.darker());
    	tensionRenderer.setSeriesPaint(2, Color.green.darker());
    	tensionRenderer.setSeriesStroke(0, new BasicStroke(1.3f));
    	tensionRenderer.setSeriesStroke(1, new BasicStroke(1.3f));
    	tensionRenderer.setSeriesStroke(2, new BasicStroke(1.3f));
    		   
    	
    	return graphConfig("Intrafusal Fiber Tension", xAxis, yAxis, xyPlot);
    }



    
    private JFreeChart createPrimaryChart() {
    	
    	double[] timeVector;
    	double[] IaVector;
    	
    	timeVector = spindle.getTimeVector();
    	IaVector = spindle.getIaVector();
    	
    	NumberAxis xAxis = new NumberAxis ("Time (s)");
    	NumberAxis yAxis = new NumberAxis ("Ia Activity (pps)");
    	
    	XYSeries Ia_Series = new XYSeries ("Ia Afferent");
    	    	
    	for (int i = 0; i < timeVector.length; i++){
    		Ia_Series.add(timeVector[i], IaVector[i]);
    	}
    	
    	
    	XYSeriesCollection xyDataset = new XYSeriesCollection ();
    	xyDataset.addSeries(Ia_Series);
    	
    	XYItemRenderer IaRenderer = new XYLineAndShapeRenderer(true, false);
    	XYPlot xyPlot = new XYPlot(xyDataset, xAxis, yAxis, IaRenderer);
    	IaRenderer.setSeriesPaint(0, Color.blue.darker());
    	IaRenderer.setSeriesStroke(0, new BasicStroke(1.3f));
		
    		    	
    	return graphConfig("Primary Afferent Activity", xAxis, yAxis, xyPlot);
    }
    
    
    
    

	private JFreeChart createSecondaryChart() {
		
    	double[] timeVector;
    	double[] IIVector;
    	
    	timeVector = spindle.getTimeVector();
    	IIVector = spindle.getIIVector();
    	 
    	NumberAxis xAxis = new NumberAxis ("Time (s)");
    	NumberAxis yAxis = new NumberAxis ("II Activity (pps)");
    	XYSeries xySeries = new XYSeries ("II Afferent");
    	
    	for (int i = 0; i < timeVector.length; i++){
    		xySeries.add(timeVector[i], IIVector[i]);
    	}
    	
    	XYSeriesCollection xyDataset = new XYSeriesCollection (xySeries);
    	
    	XYItemRenderer IIRenderer = new XYLineAndShapeRenderer(true, false);
    	XYPlot xyPlot = new XYPlot(xyDataset, xAxis, yAxis, IIRenderer);
    	IIRenderer.setSeriesPaint(0, Color.blue.darker());
    	IIRenderer.setSeriesStroke(0, new BasicStroke(1.3f));
		
    		    	
    	return graphConfig("Secondary Afferent Activity", xAxis, yAxis, xyPlot);
	}
    
    
}
