/* -------------------------------
 /* -------------------------------
 * ServletDemo2ChartGenerator.java
 * -------------------------------
 * (C) Copyright 2002-2004, by Object Refinery Limited.
 *
 */

package br.remoto.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.date.SerialDate;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import br.remoto.spindle_simulator.Constant;
import br.remoto.spindle_simulator.Input;
import br.remoto.spindle_simulator.RampAndHold;
import br.remoto.spindle_simulator.Sinusoidal;
import br.remoto.spindle_simulator.Spindle;
import br.remoto.spindle_simulator.Triangular;
import br.remoto.util.ButterworthBilinear;
import br.remoto.GTO.GTOConstantInput;
import br.remoto.GTO.GTORampAndHoldInput;
import br.remoto.GTO.GTOSinusoidalInput;
import br.remoto.GTO.GTOTriangularInput;
import br.remoto.GTO.GolgiTendonOrgan;
import br.remoto.model.ReMoto;
import br.remoto.model.Simulation;

public class PlotGTOSinusoidalInput extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
		
	Runtime runtime = Runtime.getRuntime();
		
	String type;
	
	int index;
	
    public PlotGTOSinusoidalInput(){}

    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	
	    	OutputStream out = response.getOutputStream();
	        	    	
	    	try {
	    		type 				= request.getParameter("type");
	    		
	    		String str_index 	= request.getParameter("index_input");
	    		
	    		index 	= Integer.valueOf(str_index);
	    		
	    		System.out.println("ploting:  " + "sinusoidal" + " of index: " + str_index);	    		
	    		 
	    		JFreeChart chart = null;
	    		
	    		if (type.equals("InputConfig") || type.equals("InputMini")) {
	    			
	    				chart = createSinusoidalInputChart();
	    				    			
	    		}
	    			    		
	    		if (chart != null) {
		    		response.setContentType("image/png");
		    		if (type.equals("InputConfig"))	ChartUtilities.writeChartAsPNG(out, chart, 800, 600);
		    		if (type.equals("InputMini"))	ChartUtilities.writeChartAsPNG(out, chart, 200, 150);
		    		response.getOutputStream().flush();
		    		//System.out.println("generating chart");
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
    
    
    

    private JFreeChart createSinusoidalInputChart() {

    	
    	double[] timeVector;
    	double[] stretchVector;
    	double min = 0.0, max = 0.0, x = 0.0, y = 0.0;
    	int num_steps;
    	
    	GTOSinusoidalInput sin= (GTOSinusoidalInput)GTOSuper.GTO_Inputs.get( index );

    	sin.SimulateInput();
    	
    	num_steps = sin.getNum_steps();
    	
    	timeVector = sin.getTimeVector();
    	
    	stretchVector = sin.getTensionVector();
    	   	
    	ValueAxis xAxis = new NumberAxis ("Time (s)");
    	ValueAxis yAxis = new NumberAxis ("");
    	XYSeries xySeries = new XYSeries ("Fiber tension (N)");
    	
    	
    	min = stretchVector[0];
    	max = stretchVector[0];
    	
    	for (int i = 0; i <= num_steps; i++){
    		x = timeVector[i];
    		y = stretchVector[i];
    		if (y < min) min = y;
    		if (y > max) max = y;
    		xySeries.add(x, y);
    	}
    	
    	if (min < 0) min = 0.0; 
    	
    	xAxis.setRangeWithMargins(0.99 * timeVector[0], 1.01 * timeVector[num_steps]);
    	yAxis.setRangeWithMargins(0.99 * min, 1.01 * max);
    	
    	
    	
    	XYSeriesCollection xyDataset = new XYSeriesCollection (xySeries);
    	
    	// Create XYPlot
    	XYPlot xyPlot = new XYPlot (xyDataset, xAxis, yAxis, new StandardXYItemRenderer(StandardXYItemRenderer.LINES));
    	
    	JFreeChart chart = new JFreeChart("", xyPlot);
    	
    	timeVector = null;
    	stretchVector = null;
    	xyDataset = null;
    	xyPlot = null;
    	
    	System.gc();
    	
    	return chart;
    }
    
    
    
}
