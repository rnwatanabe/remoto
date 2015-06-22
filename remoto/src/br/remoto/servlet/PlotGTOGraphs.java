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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


import br.remoto.GTO.GTOConstantInput;
import br.remoto.GTO.GTOInput;
import br.remoto.GTO.GTORampAndHoldInput;
import br.remoto.GTO.GTOSinusoidalInput;
import br.remoto.GTO.GTOTriangularInput;
import br.remoto.GTO.GolgiTendonOrgan;


public class PlotGTOGraphs extends GTOSuper 
{
	private static final long serialVersionUID = 1L;
	
	
	GolgiTendonOrgan gto 	= (GolgiTendonOrgan)GTOConfig.GTO_simulations.get( 13 );
	
	//GolgiTendonOrgan gto 	= (GolgiTendonOrgan)gtosVector[0];
	
	
	Runtime runtime = Runtime.getRuntime();
		
	String graph_type;
	String input;
	int index;

    public PlotGTOGraphs(){}

    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	
	    	OutputStream out = response.getOutputStream();
	        
	    	gto 	= (GolgiTendonOrgan)GTOConfig.GTO_simulations.get( 13 );
	    	//gto 	= (GolgiTendonOrgan)gtosVector[0];
	    	
	    	
	    	try {
	    		graph_type 				= request.getParameter("graph_type");
	    		input 				= request.getParameter("input_type");
	    		String str_index 	= request.getParameter("index_input");
	    		index 	= Integer.valueOf(str_index);
	    		fiberID_aux 		=  GTOConfig.fiberID_aux;
	    		//System.out.println(request.getParameter("fiberID"));
	    		
	    		System.out.println("ploting:  " + input + " of index: " + str_index);
	    		//System.out.println("FIBERID:  " + GTOConfig.fiberID_aux);
	    		
	    		JFreeChart chart = null;
	    		
	    		if (graph_type.equals("InputConfig") || graph_type.equals("InputMini")) {
	    			
	    			if(input.equals("constant")){
	    				chart = createInputChart();
	    				//chart = createConstantInputChart();
	    				//System.out.println("creating constant chart");
	    			}
	    			if(input.equals("ramp-and-hold")){
	    				
	    				chart = createInputChart();
	    				//System.out.println("creating ramp-and-hold chart");
	    			}
	    			if(input.equals("sinusoidal")){
	    				
	    				chart = createInputChart();
	    				//chart = createSinusoidalInputChart();
	    				//System.out.println("creating sinusoidal chart");
	    			}
	    			if(input.equals("triangular")){
	    				chart = createInputChart();
	    				
	    				//chart = createTriangularInputChart();
	    				//System.out.println("creating triangular chart");
	    			}
	    			
	    		}
	    		
	    		
	    		if (chart != null) {
		    		response.setContentType("image/png");
		    		if (graph_type.equals("InputConfig"))	ChartUtilities.writeChartAsPNG(out, chart, 800, 600);
		    		//if (type.equals("InputMini"))	ChartUtilities.writeChartAsPNG(out, chart, 200, 150);
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
    
    
    
    
    private JFreeChart createInputChart() {

    	
    	double[] timeVector;
    	double[] tensionVector;
    	
    	//System.out.println(index);
    	
    	double min = 0.0, max = 0.0, x = 0.0, y = 0.0;
    	int num_steps;
    	
    	
    	GTOInput input= (GTOInput) GTOSuper.GTO_Inputs.get( index );
    	//GTOConstantInput constant = (GTOConstantInput) GTOInputsVector[index];
    	
    	input.SimulateInput();
    	
    	num_steps = input.getNum_steps();

    	timeVector = input.getTimeVector();
    	tensionVector = input.getTensionVector();
    	
    	
    	ValueAxis xAxis = new NumberAxis ("Time (s)");
    	ValueAxis yAxis = new NumberAxis ("Fiber tension (N)");
    	XYSeries xySeries = new XYSeries ("Fiber tension (N)");
    	
    	
    	min = tensionVector[0];
    	max = tensionVector[0];
    	
    	for (int i = 0; i <= num_steps; i++){
    		
    		x = timeVector[i];
    		y = tensionVector[i];
    		if (y < min) min = y;
    		if (y > max) max = y;
    		xySeries.add(x, y);
    	}
    	
    	if (min < 0) min = 0.0; 
    	
    	xAxis.setRangeWithMargins(0.99 * timeVector[0], 1.01 * timeVector[num_steps]);
    	yAxis.setRangeWithMargins(0.99 * min, 1.01 * max);
    	
    	XYSeriesCollection xyDataset = new XYSeriesCollection (xySeries);

    	XYPlot xyPlot = new XYPlot (xyDataset, xAxis, yAxis, new StandardXYItemRenderer(StandardXYItemRenderer.LINES));
    	
    	JFreeChart chart = new JFreeChart(fiberID_aux + "  Input", xyPlot);
    	//JFreeChart chart = new JFreeChart("", xyPlot);
    	
    	
    	timeVector = null;
    	tensionVector = null;
    	xyDataset = null;
    	xyPlot = null;
    	
    	System.gc();
    	
    	return chart;
    }



    
    
    private JFreeChart createConstantInputChart() {

    	
    	double[] timeVector;
    	double[] tensionVector;
    	
    	//System.out.println(index);
    	
    	double min = 0.0, max = 0.0, x = 0.0, y = 0.0;
    	int num_steps;
    	
    	
    	GTOConstantInput constant= (GTOConstantInput)GTOSuper.GTO_Inputs.get( index );
    	//GTOConstantInput constant = (GTOConstantInput) GTOInputsVector[index];
    	
    	constant.SimulateInput();
    	
    	num_steps = constant.getNum_steps();

    	timeVector = constant.getTimeVector();
    	tensionVector = constant.getTensionVector();
    	
    	
    	ValueAxis xAxis = new NumberAxis ("Time (s)");
    	ValueAxis yAxis = new NumberAxis ("Fiber tension (N)");
    	XYSeries xySeries = new XYSeries ("Fiber tension (N)");
    	
    	
    	min = tensionVector[0];
    	max = tensionVector[0];
    	
    	for (int i = 0; i <= num_steps; i++){
    		
    		x = timeVector[i];
    		y = tensionVector[i];
    		if (y < min) min = y;
    		if (y > max) max = y;
    		xySeries.add(x, y);
    	}
    	
    	if (min < 0) min = 0.0; 
    	
    	xAxis.setRangeWithMargins(0.99 * timeVector[0], 1.01 * timeVector[num_steps]);
    	yAxis.setRangeWithMargins(0.99 * min, 1.01 * max);
    	
    	XYSeriesCollection xyDataset = new XYSeriesCollection (xySeries);

    	XYPlot xyPlot = new XYPlot (xyDataset, xAxis, yAxis, new StandardXYItemRenderer(StandardXYItemRenderer.LINES));
    	
    	JFreeChart chart = new JFreeChart(fiberID_aux + "  Input", xyPlot);
    	//JFreeChart chart = new JFreeChart("", xyPlot);
    	
    	
    	timeVector = null;
    	tensionVector = null;
    	xyDataset = null;
    	xyPlot = null;
    	
    	System.gc();
    	
    	return chart;
    }
    
    
    
    
    
       
    
    
    
    
    
    
    
    
    private JFreeChart createRampInputChart() {

    	//System.out.println("HELLO");
    	
    	double[] timeVector;
    	double[] stretchVector;
    	double min = 0.0, max = 0.0, x = 0.0, y = 0.0;
    	int num_steps;
    	
    	GTORampAndHoldInput ramp= (GTORampAndHoldInput)GTOSuper.GTO_Inputs.get( index );
    	//GTORampAndHoldInput ramp = (GTORampAndHoldInput) GTOInputsVector[index];
    	
    	ramp.SimulateInput();
    	
    	num_steps = ramp.getNum_steps();

    	timeVector = ramp.getTimeVector();
    	stretchVector = ramp.getTensionVector();
 
    	ValueAxis xAxis = new NumberAxis ("Time (s)");
    	ValueAxis yAxis = new NumberAxis ("Fiber tension (N)");
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

    	XYPlot xyPlot = new XYPlot (xyDataset, xAxis, yAxis, new StandardXYItemRenderer(StandardXYItemRenderer.LINES));
    	
    	JFreeChart chart = new JFreeChart(fiberID_aux + "  Input", xyPlot);
    	
    	timeVector = null;
    	stretchVector = null;
    	xyDataset = null;
    	xyPlot = null;
    	
    	System.gc();
    	
    	return chart;
    }


    private JFreeChart createSinusoidalInputChart() {

    	
    	double[] timeVector;
    	double[] stretchVector;
    	double min = 0.0, max = 0.0, x = 0.0, y = 0.0;
    	int num_steps;
    	
    	GTOSinusoidalInput sin= (GTOSinusoidalInput)GTOSuper.GTO_Inputs.get( index );
    	//GTOSinusoidalInput sin = (GTOSinusoidalInput) GTOInputsVector[index];

    	sin.SimulateInput();
    	
    	num_steps = sin.getNum_steps();
    	
    	timeVector = sin.getTimeVector();
    	
    	stretchVector = sin.getTensionVector();
    	   	
    	ValueAxis xAxis = new NumberAxis ("Time (s)");
    	ValueAxis yAxis = new NumberAxis ("Fiber tension (N)");
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
    	
    	JFreeChart chart = new JFreeChart(fiberID_aux + "  Input", xyPlot);
    	
    	timeVector = null;
    	stretchVector = null;
    	xyDataset = null;
    	xyPlot = null;
    	
    	System.gc();
    	
    	return chart;
    }
    
    
    
private JFreeChart createTriangularInputChart() {

    	
    	double[] timeVector;
    	double[] stretchVector;
    	double min = 0.0, max = 0.0, x = 0.0, y = 0.0;
    	int num_steps;
    	
    	GTOTriangularInput triang= (GTOTriangularInput)GTOSuper.GTO_Inputs.get( index );
    	//GTOTriangularInput triang = (GTOTriangularInput) GTOInputsVector[index];
    	
    	triang.SimulateInput();
    	
    	num_steps = triang.getNum_steps();

    	timeVector = triang.getTimeVector();
    	stretchVector = triang.getTensionVector();
 
    	ValueAxis xAxis = new NumberAxis ("Time (s)");
    	ValueAxis yAxis = new NumberAxis ("Fiber tension (N)");
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

    	XYPlot xyPlot = new XYPlot (xyDataset, xAxis, yAxis, new StandardXYItemRenderer(StandardXYItemRenderer.LINES));
    	
    	JFreeChart chart = new JFreeChart(fiberID_aux + "  Input", xyPlot);
    	
    	timeVector = null;
    	stretchVector = null;
    	xyDataset = null;
    	xyPlot = null;
    	
    	System.gc();
    	
    	return chart;
    }


    


    
    private JFreeChart createPrimaryChart() {
    	
    	double[] timeVector;
    	double[] IbVector;
    	
    	int num_steps;
    	

    	timeVector = gto.getTimeVector();

    	IbVector = gto.getIbVector();
    	double min = 0.0, max = 0.0, x = 0.0, y = 0.0;
    	
    	
    	num_steps = gto.getNum_steps();
    	 
    	ValueAxis xAxis = new NumberAxis ("Time (s)");
    	ValueAxis yAxis = new NumberAxis ("Primary Afferent Activity (pps)");
    	
    	XYSeries Ia_Series = new XYSeries ("Primary Afferent");
    	
    	
    	  	
    	
    	min = IbVector[0];
    	max = IbVector[0];
    	
    	for (int i = 0; i <= num_steps; i++){
    		x = timeVector[i];
    		y = IbVector[i];
    		if (y < min) min = y;
    		if (y > max) max = y;
    		Ia_Series.add(x, y);
    	}
    	
    	if (min < 0) min = 0.0; 
    	
    	xAxis.setRangeWithMargins(0.99 * timeVector[0], 1.01 * timeVector[num_steps]);
    	yAxis.setRange(0.99 * min, 1.01 * max);
    	
    	
    	XYSeriesCollection xyDataset = new XYSeriesCollection ();
    	xyDataset.addSeries(Ia_Series);
    	
    	// Create XYPlot
    	XYPlot xyPlot = new XYPlot (xyDataset, xAxis, yAxis, new StandardXYItemRenderer(StandardXYItemRenderer.LINES));
    	
    	JFreeChart chart = new JFreeChart("Primary Afferent Activity (Ib)", xyPlot);
    	
    	timeVector = null;
    	IbVector = null;
    	xyDataset = null;
    	xyPlot = null;
    	
    	System.gc();
    	
    	return chart;
    }
    
    
    
    
}