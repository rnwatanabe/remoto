
package br.remoto.applet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.JApplet;

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


public class AppletChartGenerator extends JApplet {
		
int i = 0;
	
	URL servlet = null;
	URLConnection servletConnection = null;
	ObjectInputStream inputFromServlet = null;
	ObjectOutputStream outputToServlet = null;
	
	String graphType;
	String input_type;
	String cdSimulation;
	
	String task;
	
	XYSeriesCollection receivedDataSet;
	XYSeriesCollection receivedDataSet2;
	
	//HistogramDataset receivedDataSetHistogram;
	HistogramDataset receivedHistogramDataSet = new HistogramDataset();
	
	String title;
	String XLabel;
	String YLabel;
	String YLabel2;
	
	Font sans = new Font("SansSerif", Font.PLAIN, 15);
	Font sansB = new Font("SansSerif", Font.BOLD, 19);
	Font times_17 = new Font("Times New Roman", Font.PLAIN, 17);
	Font arial_15 = new Font("Arial", Font.PLAIN, 15);
	
	int applet_height = 500;
	int applet_width = 700;
	
	
	public AppletChartGenerator() {}
	
	public void init(){
		
		
		cdSimulation = getParameter("cdSimulation");
		
		
		try {
			communicateWithServlet();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		plotGraph();
		
	}
	
	
	
	
	private void communicateWithServlet() throws ClassNotFoundException, IOException {
			
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
		outputToServlet.close();
		
		
		
		inputFromServlet 			= new ObjectInputStream(servletConnection.getInputStream());
		task 						= (String) inputFromServlet.readUTF();
		
		if(!task.equals("noData")){
			if(	task.equals("signal") 	||
				task.equals("force") 	||
				task.equals("EMG") 		||
				task.equals("spikes") 	||
				task.equals("firingRate")){
				receivedDataSet 			= (XYSeriesCollection) inputFromServlet.readObject();
			}
			else if(task.equals("histogram")){
				//receivedDataSetHistogram 	= (HistogramDataset) inputFromServlet.readObject();
				receivedHistogramDataSet 	= (HistogramDataset) inputFromServlet.readObject();
				
				/*
				double ad[] = new double[1000];
				Random random = new Random(0xbc614eL);
				for (int i = 0; i < 1000; i++)
					ad[i] = random.nextGaussian();
				
				receivedHistogramDataSet.addSeries("H1", ad, 100);
				*/
				
			}
			else if(task.equals("spikesAndForce")||
					task.equals("spikesAndEMG")){
				receivedDataSet 			= (XYSeriesCollection) inputFromServlet.readObject();
				receivedDataSet2 			= (XYSeriesCollection) inputFromServlet.readObject();
			}
			
			title 						= (String) inputFromServlet.readUTF();
			XLabel 						= (String) inputFromServlet.readUTF();
			YLabel 						= (String) inputFromServlet.readUTF();
			YLabel2 					= (String) inputFromServlet.readUTF();
		}
		inputFromServlet.close();
		
	}
	
	
	private void plotLineGraph(String isThereData){
		
		if(isThereData.equals("noData")){
			
			NumberAxis xAxis = new NumberAxis ();
			NumberAxis yAxis = new NumberAxis ();
			XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);  
			XYSeriesCollection dataset = new XYSeriesCollection();
			
			XYPlot xyPlot = new XYPlot(dataset, xAxis, yAxis, renderer);
			
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
	    	
			JFreeChart chart = new JFreeChart("There is no data to generate the selected graphic", new Font("SansSerif", Font.BOLD, 23), xyPlot, true);
			chart.setBackgroundPaint(Color.white);
			
			ChartPanel chartPanel = new ChartPanel(chart, applet_width, applet_height, applet_width, applet_height, applet_width, applet_height, false, true, false, false, true, true, false);
    		
			getContentPane().add(chartPanel);
		}
		else{		
			NumberAxis xAxis = new NumberAxis ();
			NumberAxis yAxis = new NumberAxis ();
	    	
	    	XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);  
	    	XYPlot xyPlot = new XYPlot((XYSeriesCollection) receivedDataSet, xAxis, yAxis, renderer);
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
	
	
	
	private void plotScatterGraph(){
		
		NumberAxis xAxis = new NumberAxis ();
		NumberAxis yAxis = new NumberAxis ();
		
		XYDotRenderer renderer = new XYDotRenderer();
        renderer.setDotWidth(3);
        renderer.setDotHeight(3);
        renderer.setSeriesPaint(0, Color.red.darker().darker());
        
        XYPlot xyPlot = new XYPlot((XYSeriesCollection) receivedDataSet, xAxis, yAxis, renderer);
        
        xyPlot.setDomainPannable(true);
    	xyPlot.setRangePannable(true);
		
    	xAxis.setAutoRange(true);
    	xAxis.setLowerMargin(0.0);
    	xAxis.setTickLabelsVisible(true);
    	xAxis.setTickLabelFont(sans);
    	xAxis.setLabelFont(sansB);
    	xAxis.setLabelPaint(Color.gray.darker());
    	xAxis.setLabel(XLabel);
    	
    	yAxis.setRangeType( RangeType.POSITIVE );
    	//yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    	yAxis.setNumberFormatOverride( new DecimalFormat("0") );
        yAxis.setAutoRange( true );
        yAxis.setAutoRangeMinimumSize( 11 );
    	yAxis.setTickLabelFont(sans);
    	yAxis.setLabelFont(sansB);
    	yAxis.setLabelPaint(Color.gray.darker());
    	yAxis.setLabel(YLabel);
    	yAxis.setRangeType( RangeType.POSITIVE );
    	
    	xyPlot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
    	
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

	
	private void plotHistogramGraph(){
		
		
		ValueAxis xAxis = new NumberAxis ();
		NumberAxis yAxis = new NumberAxis ();
    	
		XYBarRenderer xybarrenderer = new XYBarRenderer ();
		xybarrenderer.setDrawBarOutline(false);
		xybarrenderer.setBarPainter(new StandardXYBarPainter());
		xybarrenderer.setShadowVisible(false);
    	XYPlot xyPlot = new XYPlot((HistogramDataset) receivedHistogramDataSet, xAxis, yAxis, xybarrenderer);
    	
    	xyPlot.setDomainPannable(true);
    	xyPlot.setRangePannable(true);
    	
    	//xAxis.setAutoRange(true);
    	xAxis.setLowerMargin(0.0);
    	xAxis.setTickLabelsVisible(true);
    	xAxis.setTickLabelFont(sans);
    	xAxis.setLabelFont(sansB);
    	xAxis.setLabelPaint(Color.gray.darker());
    	xAxis.setLabel(XLabel);
    	
    	yAxis.setRangeType( RangeType.POSITIVE );
    	yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    	yAxis.setAutoRangeIncludesZero(false);
    	yAxis.setTickLabelFont(sans);
    	yAxis.setLabelFont(sansB);
    	yAxis.setLabelPaint(Color.gray.darker());
    	yAxis.setLabel(YLabel);
    	yAxis.setRangeType( RangeType.POSITIVE );
    	
    	
		JFreeChart chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 23), xyPlot, true);
		
		chart.removeLegend();
		
		chart.setBackgroundPaint(Color.white);
		
    	ChartPanel chartPanel = new ChartPanel(chart, applet_width, applet_height, applet_width, applet_height, applet_width, applet_height, false, true, false, false, true, true, false);
    		
    	chartPanel.setMouseZoomable(true);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setZoomAroundAnchor(false);
        
		getContentPane().add(chartPanel);
		
	}

	
	private void plotCombinedGraph(){
		
		NumberAxis xAxis = new NumberAxis ();
		NumberAxis yAxis1 = new NumberAxis ();
		NumberAxis yAxis2 = new NumberAxis ();
    	
		XYDotRenderer renderer1 = new XYDotRenderer();
		renderer1.setDotWidth(3);
		renderer1.setDotHeight(3);
		renderer1.setSeriesPaint(0, Color.red.darker().darker());
        XYPlot xyPlot1 = new XYPlot((XYSeriesCollection) receivedDataSet, xAxis, yAxis1, renderer1);
        xyPlot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
    	
    	XYItemRenderer renderer2 = new XYLineAndShapeRenderer(true, false);  
    	XYPlot xyPlot2 = new XYPlot((XYSeriesCollection) receivedDataSet2, xAxis, yAxis2, renderer2);
    	renderer2.setSeriesPaint(0, Color.red.darker());
    	renderer2.setSeriesStroke(0, new BasicStroke(1.3f));
    	
    	CombinedDomainXYPlot  plot = new CombinedDomainXYPlot (xAxis);
		
		plot.add(xyPlot2, 2);
		plot.add(xyPlot1, 3);
		
		plot.setDomainPannable(true);
		plot.setRangePannable(true);
		    	
    	xAxis.setAutoRange(true);
    	xAxis.setLowerMargin(0.0);
    	xAxis.setTickLabelsVisible(true);
    	xAxis.setTickLabelFont(sans);
    	xAxis.setLabelFont(sansB);
    	xAxis.setLabelPaint(Color.gray.darker());
    	xAxis.setLabel(XLabel);
    	
    	yAxis1.setRangeType( RangeType.POSITIVE );
    	yAxis1.setAutoRangeIncludesZero(false);
    	yAxis1.setTickLabelFont(sans);
    	yAxis1.setLabelFont(sansB);
    	yAxis1.setLabelPaint(Color.gray.darker());
    	yAxis1.setLabel(YLabel);
    	yAxis1.setRangeType( RangeType.POSITIVE );
    	
    	yAxis2.setAutoRangeIncludesZero(false);
    	yAxis2.setTickLabelFont(sans);
    	yAxis2.setLabelFont(sansB);
    	yAxis2.setLabelPaint(Color.gray.darker());
    	yAxis2.setLabel(YLabel2);
    	
    	
    	JFreeChart chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 23), plot, true);


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

	
	private void plotGraph(){
		
		if(task.equals("noData")) 														plotLineGraph("noData");
		else if(task.equals("signal") || task.equals("force") || task.equals("EMG")) 	plotLineGraph("");
		else if(task.equals("spikes") || task.equals("firingRate")) 					plotScatterGraph();
		else if(task.equals("histogram")) 												plotHistogramGraph();
		else if(task.equals("spikesAndForce") || task.equals("spikesAndEMG")) 			plotCombinedGraph();
		else plotLineGraph("noData");
	}
	
}