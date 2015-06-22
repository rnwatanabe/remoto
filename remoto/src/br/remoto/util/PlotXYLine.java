package br.remoto.util;

import java.awt.Color;
import java.io.File;
import java.io.OutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 * Gera um gráfico de série XY.
 */

public class PlotXYLine 
{

	public static void generate(XYSeriesCollection dataset, OutputStream out, String title, String xLabel, String yLabel) 
	{
	    //cria a coleção de séries e adiciona as séries
	    //XYSeriesCollection dataset = new XYSeriesCollection();
	    //dataset.addSeries( serie );
	    
	    //cria o gráfico e grava a imagem em disco
	    JFreeChart grafico = ChartFactory.createXYLineChart( title, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, false, false );
	    //grafico.setBackgroundPaint(Color.white);
	    try 
		{
	    	ChartUtilities.writeChartAsJPEG(out, grafico, 600, 450);		
		} 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
	}

	public static void generate(XYSeries serie, String fileName, String title, String xLabel, String yLabel) 
	{
	    //cria a coleção de séries e adiciona as séries
	    XYSeriesCollection dataset = new XYSeriesCollection();
	    dataset.addSeries( serie );
	    
	    //cria o gráfico e grava a imagem em disco
	    JFreeChart grafico = ChartFactory.createXYLineChart( title, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, false, false );
	    //grafico.setBackgroundPaint(Color.white);
	    
	    try 
		{
	    	ChartUtilities.saveChartAsJPEG( new File( fileName ), grafico, 600, 450 );
	    } 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
	}

	public static void generate(XYSeriesCollection dataset, String fileName, String title, String xLabel, String yLabel) 
	{
	    //cria o gráfico e grava a imagem em disco
	    JFreeChart grafico = ChartFactory.createXYLineChart( title, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, false, false );
	    //grafico.setBackgroundPaint(Color.white);
	    try 
		{
	    	ChartUtilities.saveChartAsJPEG( new File( fileName ), grafico, 600, 450 );
	    } 
	    catch( Exception e ) 
		{
    		System.out.println(e.getMessage());
	    }
	}
}