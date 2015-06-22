
package br.remoto.applet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.Timer;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.RangeType;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import org.jfree.chart.plot.CombinedDomainXYPlot;




public class Teste extends JApplet {
		
int i = 0;
	
	URL servlet = null;
	URLConnection servletConnection = null;
	ObjectInputStream inputFromServlet = null;
	ObjectOutputStream outputToServlet = null;
	
	String graphType;
	String input_type;
	String cdSimulation;
	
	String task;
	
	int numSamples = 2;
	double[] dataConcat;
	
	
	String isFirstTime = "yes";;
	
	XYSeries xySerie;
	
	
	String title 	= "";
	String XLabel 	= "";
	String YLabel 	= "";
	String YLabel2 	= "YLabel2";
	
	
	Font sans = new Font("SansSerif", Font.PLAIN, 15);
	Font sansB = new Font("SansSerif", Font.BOLD, 19);
	Font times_17 = new Font("Times New Roman", Font.PLAIN, 17);
	Font arial_15 = new Font("Arial", Font.PLAIN, 15);
	
	int applet_height = 500;
	int applet_width = 700;
	
	
	public Teste() {
		
		isFirstTime = "yes";
		
	}
	
	
	public void init(){
		
		isFirstTime = "yes";
		
		xySerie = new XYSeries ("teste");
		
		plotLineGraph("");
		
		new Teste.DataGenerator().start();
	}
	
	
	class DataGenerator extends Timer implements ActionListener {
		
		DataGenerator() {
			super(1, null);
			addActionListener(this);
		}
		
		
		
		public void actionPerformed(ActionEvent event) {
					
			try
			{
				communicateWithServlet();
				
			} 
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(isFirstTime.equals("yes")){
				for(int k=0; k<dataConcat.length; k = k + 2){
					xySerie.add(dataConcat[k],dataConcat[k + 1]);
				}
				isFirstTime = "no";
			}
			else{
				for(int k=0; k<numSamples; k++){
					xySerie.add(dataConcat[k],dataConcat[k + numSamples]);
				}
			}
			
			/*
			for(int k=0; k<numSamples; k++){
				xySerie.add(dataConcat[k],dataConcat[k + numSamples]);
			}
			*/
		}
	}
	
	private void communicateWithServlet() throws ClassNotFoundException, IOException {
		
		cdSimulation = getParameter("cdSimulation");
		
		String url = this.getCodeBase() + "/servlet/ServletChartGeneratorApplet";
		
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
		
		
		outputToServlet.writeUTF(cdSimulation);
		outputToServlet.flush();
		
		outputToServlet.writeUTF(isFirstTime);
		outputToServlet.flush();
		
		
		outputToServlet.close();
		
		
		inputFromServlet 			= new ObjectInputStream(servletConnection.getInputStream());
		
		task 						= (String) inputFromServlet.readUTF();
		
		if(!task.equals("noData")){
			if(	task.equals("signal") 	||task.equals("force")||task.equals("EMG")||task.equals("spikes") ||task.equals("firingRate")){
				/*
				if(isFirstTime.equals("yes")){
					//dataConcat = (double[])  inputFromServlet.readObject();
				}
				else{
					dataConcat = (double[])  inputFromServlet.readObject();
				}
				*/
				dataConcat = (double[])  inputFromServlet.readObject();
				
			}
			
			
			
		}
		inputFromServlet.close();
		
	}
	
	
	private void plotLineGraph(String isThereData){
		
		
			NumberAxis xAxis = new NumberAxis ();
			NumberAxis yAxis = new NumberAxis ();
	    	
	    	XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false); 
	    	
	    	XYSeriesCollection dataset = new XYSeriesCollection(xySerie);
	    	
	    	XYPlot xyPlot = new XYPlot((XYSeriesCollection) dataset, xAxis, yAxis, renderer);
	    	renderer.setSeriesPaint(0, Color.red.darker());
	    	renderer.setSeriesStroke(0, new BasicStroke(1.3f));
	    	
	    
	    	xyPlot.setDomainPannable(true);
	    	xyPlot.setRangePannable(true);
			
	    	xAxis.setAutoRange(true);
	    	xAxis.setLowerMargin(0.0);
	    	xAxis.setTickLabelsVisible(true);
	    	xAxis.setTickLabelFont(sans);
	    	xAxis.setLabelFont(sansB);
	    	xAxis.setLabelPaint(Color.gray.darker());
	    	xAxis.setLabel(XLabel);
	    	
	    	
	    	yAxis.setAutoRangeIncludesZero(false);
	    	yAxis.setTickLabelFont(sans);
	    	yAxis.setLabelFont(sansB);
	    	yAxis.setLabelPaint(Color.gray.darker());
	    	yAxis.setLabel(YLabel);
	    	
	    	
	    	JFreeChart chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 23), xyPlot, true);
	    	
	    	
			LegendTitle legend = chart.getLegend();
			legend.setItemFont(new Font("SansSerif", Font.PLAIN, 19));
			legend.setItemPaint(Color.gray.darker().darker());
			
	    	chart.setBackgroundPaint(Color.white);
	    	
			
	    	ChartPanel chartPanel = new ChartPanel(chart, applet_width, applet_height, applet_width, applet_height, applet_width, applet_height, false, true, false, false, true, true, false);
	    		
	    	
	    	chartPanel.setMouseZoomable(true);
	        chartPanel.setFillZoomRectangle(true);
	        chartPanel.setZoomAroundAnchor(false);
	        
			getContentPane().add(chartPanel);
		
	}
	
}