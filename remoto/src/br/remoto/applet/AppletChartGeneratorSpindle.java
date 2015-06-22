
package br.remoto.applet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JApplet;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import org.jfree.chart.plot.CombinedDomainXYPlot;



public class AppletChartGeneratorSpindle extends JApplet {
		
	double[] timeVector;
	double[] stretchVector;
	double[] IaVector;
	double[] IIVector;
	double[] tensionBag1Vector;
	double[] tensionBag2Vector;
	double[] tensionChainVector;
	double[] f_dynamic_bag1Vector;
	double[] f_static_bag2Vector;
	double[] f_static_chainVector;
	
	double[][] dataConcat;
	
	int i = 0;
	
	double data = 0;
	URL servlet = null;
	URLConnection servletConnection = null;
	ObjectInputStream inputFromServlet = null;
	ObjectOutputStream outputToServlet = null;
	
	String graphType;
	String input_type;
	
	Font sans = new Font("SansSerif", Font.PLAIN, 15);
	Font sansB = new Font("SansSerif", Font.BOLD, 19);
	Font times_17 = new Font("Times New Roman", Font.PLAIN, 17);
	Font arial_15 = new Font("Arial", Font.PLAIN, 15);
	
	public AppletChartGeneratorSpindle() {}
	
	public void init(){
		
		try {
			communicateWithServlet();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		graphType = getParameter("param");
    	
		plotGraph();
		
	}
	
	private void communicateWithServlet() throws ClassNotFoundException, IOException {
		
		//servlet = new URL( "http://remoto.leb.usp.br/remoto/servlet/SpindleSendResultsToApplet2" );
		
		String url = getCodeBase() + "/servlet/SpindleSendResultsToApplet2";
		
		servlet = new URL(url);
		
		servletConnection = servlet.openConnection();
		
		// inform the connection that we will accept input
		servletConnection.setDoInput(true);
		servletConnection.setDoOutput(true);
		
		// Don't use a cached version of URL connection.
		servletConnection.setUseCaches (false);
		servletConnection.setDefaultUseCaches (false);
		
		// Specify the content type that we will send binary data
		servletConnection.setRequestProperty("Content-Type", "application/octet-stream");
		
		outputToServlet = new ObjectOutputStream(servletConnection.getOutputStream());
		outputToServlet.writeDouble(12);
		outputToServlet.flush();
		outputToServlet.close();
		
		inputFromServlet = new ObjectInputStream(servletConnection.getInputStream());
		//data = (double) inputFromServlet.readDouble();
		dataConcat = (double[][]) inputFromServlet.readObject();
		
		timeVector 				= dataConcat[0];
		stretchVector 			= dataConcat[1];
		IaVector 				= dataConcat[2];
		IIVector 				= dataConcat[3];
		tensionBag1Vector 		= dataConcat[4];
		tensionBag2Vector 		= dataConcat[5];
		tensionChainVector 		= dataConcat[6];
		f_dynamic_bag1Vector 	= dataConcat[7];
		f_static_bag2Vector 	= dataConcat[8];
		f_static_chainVector 	= dataConcat[9];
	}
	
	
	private void graphConfig(String title, NumberAxis xAxis, NumberAxis yAxis, XYPlot xyPlot){
		
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
    	
    	ChartPanel chartPanel = new ChartPanel(chart, 800, 600, 800, 600, 800, 600, false, true, false, false, true, true, false);
    		
    	//chartPanel.setPopupMenu(null);
    	
    	chartPanel.setMouseZoomable(true);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setZoomAroundAnchor(false);
        chartPanel.setMouseWheelEnabled(true);
        
		getContentPane().add(chartPanel);
	}
	
	private void graphConfigCombined(String title, NumberAxis xAxis, NumberAxis yAxis1, NumberAxis yAxis2, XYPlot xyPlot){
		xyPlot.setDomainPannable(true);
    	xyPlot.setRangePannable(true);
		
    	xAxis.setAutoRange(true);
    	xAxis.setLowerMargin(0.0);
    	xAxis.setTickLabelsVisible(true);
    	xAxis.setTickLabelFont(sans);
    	xAxis.setLabelFont(sansB);
    	xAxis.setLabelPaint(Color.gray.darker());
    	
    	yAxis1.setAutoRangeIncludesZero(false);
    	yAxis1.setTickLabelFont(sans);
    	yAxis1.setLabelFont(sansB);
    	yAxis1.setLabelPaint(Color.gray.darker());
    	
    	yAxis2.setAutoRangeIncludesZero(false);
    	yAxis2.setTickLabelFont(sans);
    	yAxis2.setLabelFont(sansB);
    	yAxis2.setLabelPaint(Color.gray.darker());
    	
    	JFreeChart chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 23), xyPlot, true);
    	
		LegendTitle legend = chart.getLegend();
		legend.setItemFont(new Font("SansSerif", Font.PLAIN, 19));
		legend.setItemPaint(Color.gray.darker().darker());
		
    	chart.setBackgroundPaint(Color.white);
    	
    	ChartPanel chartPanel = new ChartPanel(chart, 800, 600, 800, 600, 800, 600, false, true, false, false, true, true, false);
    		
    	//chartPanel.setPopupMenu(null);
    	
    	chartPanel.setMouseZoomable(true);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setZoomAroundAnchor(false);
        chartPanel.setMouseWheelEnabled(true);
        
		getContentPane().add(chartPanel);
	}
	
	private void plotGraph(){
		
		
		if (graphType.equals("primary")){
			
			NumberAxis timeAxis = new NumberAxis ("Time (s)");
			NumberAxis IaAxis = new NumberAxis ("Ia Activity (pps)");
	    	NumberAxis stretchAxis = new NumberAxis ("Fascicle Length (L0)");
	    	
	    	XYSeries stretch_Series = new XYSeries ("Muscle Fascicle Length");
	    	XYSeries Ia_Series = new XYSeries ("Primary Afferent Activity");
	    	
	    	for (int i = 0; i < IaVector.length; i++){
	    		stretch_Series.add(timeVector[i], stretchVector[i]);
	    		Ia_Series.add(timeVector[i], IaVector[i]);
	    	}
	    	
	    	
	    	XYSeriesCollection stretchXYDataset = new XYSeriesCollection ();
	    	stretchXYDataset.addSeries(stretch_Series);
	    	
	    	XYSeriesCollection IaXYDataset = new XYSeriesCollection ();
	    	IaXYDataset.addSeries(Ia_Series);
	    	
	    	XYItemRenderer stretchRenderer = new XYLineAndShapeRenderer(true, false);  
	    	XYPlot stretchXYPlot = new XYPlot(stretchXYDataset, timeAxis, stretchAxis, stretchRenderer);
	    	stretchRenderer.setSeriesPaint(0, Color.red.darker());
	    	stretchRenderer.setSeriesStroke(0, new BasicStroke(1.3f));
	    	
	    	XYItemRenderer IaRenderer = new XYLineAndShapeRenderer(true, false);
	    	XYPlot IaXYPlot = new XYPlot(IaXYDataset, timeAxis, IaAxis, IaRenderer);
	    	IaRenderer.setSeriesPaint(0, Color.blue.darker());
	    	IaRenderer.setSeriesStroke(0, new BasicStroke(1.3f));
			
	    	CombinedDomainXYPlot  plot = new CombinedDomainXYPlot (timeAxis);
			
			plot.add(IaXYPlot);
			plot.add(stretchXYPlot);
			
			graphConfigCombined("Primary Afferent Activity", timeAxis, stretchAxis, IaAxis, plot);
	    	
		}
		
		
		
		
		if (graphType.equals("secondary")){
			
			
			NumberAxis timeAxis = new NumberAxis ("Time (s)");
			NumberAxis IIAxis = new NumberAxis ("II Activity (pps)");
	    	NumberAxis stretchAxis = new NumberAxis ("Fascicle Length (L0)");
	    	
	    	XYSeries stretch_Series = new XYSeries ("Muscle Fascicle Length");
	    	XYSeries II_Series = new XYSeries ("Secondary Afferent Activity");
	    	
	    	for (int i = 0; i < IIVector.length; i++){
	    		stretch_Series.add(timeVector[i], stretchVector[i]);
	    		II_Series.add(timeVector[i], IIVector[i]);
	    	}
	    	
	    	XYSeriesCollection stretchXYDataset = new XYSeriesCollection ();
	    	stretchXYDataset.addSeries(stretch_Series);
	    	
	    	XYSeriesCollection IIXYDataset = new XYSeriesCollection ();
	    	IIXYDataset.addSeries(II_Series);
	    	
	    	XYItemRenderer stretchRenderer = new XYLineAndShapeRenderer(true, false);  
	    	XYPlot stretchXYPlot = new XYPlot(stretchXYDataset, timeAxis, stretchAxis, stretchRenderer);
	    	stretchRenderer.setSeriesPaint(0, Color.red.darker());
	    	stretchRenderer.setSeriesStroke(0, new BasicStroke(1.3f));
	    	
	    	XYItemRenderer IIRenderer = new XYLineAndShapeRenderer(true, false);
	    	XYPlot IIXYPlot = new XYPlot(IIXYDataset, timeAxis, IIAxis, IIRenderer);
	    	IIRenderer.setSeriesPaint(0, Color.blue.darker());
	    	IIRenderer.setSeriesStroke(0, new BasicStroke(1.3f));
			
	    		    	

	    	CombinedDomainXYPlot  plot = new CombinedDomainXYPlot (timeAxis);
			
			plot.add(IIXYPlot);
			plot.add(stretchXYPlot);
			
			graphConfigCombined("Secondary Afferent Activity", timeAxis, stretchAxis, IIAxis, plot);
	    	
	    	
		}
		
		
		
		if (graphType.equals("tension")){
			
			
			NumberAxis timeAxis = new NumberAxis ("Time (s)");
			NumberAxis tensionAxis = new NumberAxis ("Intrafusal Fiber Tension (F)");
	    	NumberAxis stretchAxis = new NumberAxis ("Fascicle Length (L0)");
	    	
	    	XYSeries stretch_Series = new XYSeries ("Muscle Fascicle Length");
	    	XYSeries bag1_Series = new XYSeries ("Bag 1 Fiber");
	    	XYSeries bag2_Series = new XYSeries ("Bag 2 Fiber");
	    	XYSeries chain_Series = new XYSeries ("Chain Fiber");
	    	
	    	for (int i = 0; i < tensionBag1Vector.length; i++){
	    		stretch_Series.add(timeVector[i], stretchVector[i]);
	    		bag1_Series.add(timeVector[i], tensionBag1Vector[i]);
	    		bag2_Series.add(timeVector[i], tensionBag2Vector[i]);
	    		chain_Series.add(timeVector[i], tensionChainVector[i]);
	    	}
	    	
	    	XYSeriesCollection stretchXYDataset = new XYSeriesCollection ();
	    	stretchXYDataset.addSeries(stretch_Series);
	    	
	    	XYSeriesCollection tensionXYDataset = new XYSeriesCollection ();
	    	tensionXYDataset.addSeries(bag1_Series);
	    	tensionXYDataset.addSeries(bag2_Series);
	    	tensionXYDataset.addSeries(chain_Series);
	    	
	    	XYItemRenderer stretchRenderer = new XYLineAndShapeRenderer(true, false);  
	    	XYPlot stretchXYPlot = new XYPlot(stretchXYDataset, timeAxis, stretchAxis, stretchRenderer);
	    	stretchRenderer.setSeriesPaint(0, Color.red.darker());
	    	stretchRenderer.setSeriesStroke(0, new BasicStroke(1.3f));
	    	
	    	XYItemRenderer tensionRenderer = new XYLineAndShapeRenderer(true, false);
	    	XYPlot IIXYPlot = new XYPlot(tensionXYDataset, timeAxis, tensionAxis, tensionRenderer);
	    	tensionRenderer.setSeriesPaint(0, Color.magenta.darker());
	    	tensionRenderer.setSeriesPaint(1, Color.blue.darker());
	    	tensionRenderer.setSeriesPaint(2, Color.green.darker());
	    	tensionRenderer.setSeriesStroke(0, new BasicStroke(1.3f));
	    	tensionRenderer.setSeriesStroke(1, new BasicStroke(1.3f));
	    	tensionRenderer.setSeriesStroke(2, new BasicStroke(1.3f));
	    		    	
	    	CombinedDomainXYPlot  plot = new CombinedDomainXYPlot (timeAxis);
			
			plot.add(IIXYPlot);
			plot.add(stretchXYPlot);
			
			
			graphConfigCombined("Intrafusal Fiber Tension", timeAxis, stretchAxis, tensionAxis, plot);
	    	
		}
		
		
		
		if (graphType.equals("input")){
			
			
			NumberAxis timeAxis = new NumberAxis ("Time (s)");
			NumberAxis stretchAxis = new NumberAxis ("Fascicle Length (L0)");
	    	
			XYSeries stretch_Series = new XYSeries ("Muscle Fascicle Length");
	    	    	    	
			for (int i = 0; i < stretchVector.length; i++){
				stretch_Series.add(timeVector[i], stretchVector[i]);
	    	}
			
			
			
	    	XYSeriesCollection stretchXYDataset = new XYSeriesCollection ();
	    	stretchXYDataset.addSeries(stretch_Series);
	   
	    	XYItemRenderer stretchRenderer = new XYLineAndShapeRenderer(true, false);  
	    	XYPlot stretchXYPlot = new XYPlot(stretchXYDataset, timeAxis, stretchAxis, stretchRenderer);
	    	stretchRenderer.setSeriesPaint(0, Color.red.darker());
	    	stretchRenderer.setSeriesStroke(0, new BasicStroke(1.3f));
	    	
	    	graphConfig("Fascicle Length", timeAxis, stretchAxis, stretchXYPlot);
			
		}


		if (graphType.equals("fusimotor")){
			
			NumberAxis timeAxis = new NumberAxis ("Time (s)");
			NumberAxis fusimotorAxis = new NumberAxis ("Fusimotor Activation (normalized)");
	    	
			XYSeries bag1_fusimotor_Series = new XYSeries ("Bag 1 Fiber");
			XYSeries bag2_fusimotor_Series = new XYSeries ("Bag 2 Fiber");
			XYSeries chain_fusimotor_Series = new XYSeries ("Chain Fiber");
	    	    	    	
			for (int i = 0; i < timeVector.length; i++){
				bag1_fusimotor_Series.add(timeVector[i], f_dynamic_bag1Vector[i]);
				bag2_fusimotor_Series.add(timeVector[i], f_static_bag2Vector[i]);
				chain_fusimotor_Series.add(timeVector[i], f_static_chainVector[i]);
	    	}
	    	
	    	
	    	XYSeriesCollection fusimotorXYDataset = new XYSeriesCollection ();
	    	fusimotorXYDataset.addSeries(bag1_fusimotor_Series);
	    	fusimotorXYDataset.addSeries(bag2_fusimotor_Series);
	    	fusimotorXYDataset.addSeries(chain_fusimotor_Series);
	    	
	   
	    	XYItemRenderer fusimotorRenderer = new XYLineAndShapeRenderer(true, false);  
	    	XYPlot fusimotorXYPlot = new XYPlot(fusimotorXYDataset, timeAxis, fusimotorAxis, fusimotorRenderer);
	    	fusimotorRenderer.setSeriesPaint(0, Color.red.darker());
	    	fusimotorRenderer.setSeriesPaint(1, Color.blue.darker());
	    	fusimotorRenderer.setSeriesPaint(2, Color.green.darker());
	    	fusimotorRenderer.setSeriesStroke(0, new BasicStroke(1.3f));
	    	fusimotorRenderer.setSeriesStroke(1, new BasicStroke(1.3f));
	    	fusimotorRenderer.setSeriesStroke(2, new BasicStroke(1.3f));
			
	    	graphConfig("Fusimotor Activation", timeAxis, fusimotorAxis, fusimotorXYPlot);
		}

	}
	
}