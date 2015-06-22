
package br.remoto.servlet;


/*
* (C) Copyright 2002-2005, by Object Refinery Limited.
*/




import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JApplet;
import javax.swing.Timer;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**********************************
	NÃO SENDO UTILIZADO
***********************************/


public class Applet1 extends JApplet {
	
	/** Time series for total memory used. */
	private TimeSeries function;
	private XYSeries Ia_Series;
	
	/** Time series for free memory. */
	//private TimeSeries free;
	
	/**
	* Creates a new instance.
	*/
	
	//double[] timeVector;
	//double[] IaVector;
	double[] dataConcat;
	
	//int num_steps;
	int i = 0;
	
	double data = 0;
	double time = 0;
	double Ia = 0;
	
	URL servlet = null;
	URLConnection servletConnection = null;
	ObjectInputStream inputFromServlet = null;
	ObjectOutputStream outputToServlet = null;
	
	
	
	public Applet1() {
		
		
		
    	ValueAxis xAxis = new NumberAxis ("Time (s)");
    	ValueAxis yAxis = new NumberAxis ("Primary Afferent Activity (pps)");
    	
    	Ia_Series = new XYSeries ("Primary Afferent");
    	    	    	
    	XYSeriesCollection xyDataset = new XYSeriesCollection ();
    	xyDataset.addSeries(Ia_Series);
    	
    	// Create XYPlot
    	
    	XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
    	XYPlot xyPlot = new XYPlot(xyDataset, xAxis, yAxis, renderer);
    	
    	renderer.setSeriesPaint(0, Color.red);
		renderer.setSeriesStroke(0, new BasicStroke(1.5f));
		
		
    	xAxis.setAutoRange(true);
    	//xAxis.setLowerMargin(0.0);
    	//xAxis.setUpperMargin(0.0);
    	xAxis.setTickLabelsVisible(true);
    	yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		
    	JFreeChart chart = new JFreeChart("Primary Afferent Activity (Ia)", xyPlot);
    	
    	chart.setBackgroundPaint(Color.white);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPopupMenu(null);
		getContentPane().add(chartPanel);
		
		
		
		
		
		
		
		
		new Applet1.DataGenerator().start();	
	}
	
		
	
	
	private void communicateWithServlet() throws ClassNotFoundException, IOException {
		
		servlet = new URL( "http://localhost:16969/remoto/servlet/SpindleSendResultsToApplet" );
		
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
		dataConcat = (double[]) inputFromServlet.readObject();
		time = dataConcat[0];
		Ia = dataConcat[1];
		
		
	}
	

	class DataGenerator extends Timer implements ActionListener {
		
		DataGenerator() {
			super(1, null);
			addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent event) {
			
			
			try {
				communicateWithServlet();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//data = timeVector[i++];
			//addData(Ia);
			
			Ia_Series.add(time, Ia);
			
		}
	}
}