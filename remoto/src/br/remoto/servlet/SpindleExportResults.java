/* -------------------------------
 * ServletDemo2ChartGenerator.java
 * -------------------------------
 * (C) Copyright 2002-2004, by Object Refinery Limited.
 *
 */

package br.remoto.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.remoto.spindle_simulator.Spindle;


public class SpindleExportResults extends SpindleSuper {
		
	Spindle spindle = (Spindle)SpindleSimulation.spindle_simulations.get( 13 );
	
	public SpindleExportResults() {}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		
		spindle = (Spindle)SpindleSimulation.spindle_simulations.get( 13 );
		
		PrintWriter out = new PrintWriter(response.getWriter());
		try {
		
			param 	= ((ServletRequest) request).getParameter("type");
						
			response.setContentType("text/plain");
			
			// Do not store cache
			response.setHeader("Content-disposition", "inline; filename=result.txt");
			response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			response.setHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			
			//System.gc();
			
			
			//this.generateFileResult_Ia(out);
			
			
			//System.out.println(param);
			
			if (param.equals("input")) 		this.generateFileResult(out, spindle.getStretchVector());
			if (param.equals("fusimotor")) 	this.generateFileResult(out, spindle.getF_dynamic_bag1Vector()	, spindle.getF_static_bag2Vector()	, spindle.getF_static_chainVector());
			if (param.equals("tension")) 	this.generateFileResult(out, spindle.getTension_bag1Vector()	, spindle.getTension_bag2Vector()	, spindle.getTension_chainVector());
			if (param.equals("primary")) 	this.generateFileResult(out, spindle.getIaVector());
			if (param.equals("secondary")) 	this.generateFileResult(out, spindle.getIIVector());
			
			
			out.flush();
			out.close();
			}
			catch (Exception e) {
			System.err.println(e.toString());
			}
			finally {
			out.close();
		}
	}
	
	/*
	private void generateFileResult_Ia(PrintWriter out){
		double[] timeVector;
		double[] IaVector;
		int num_steps;
		
		timeVector = spindle.getTimeVector();
		IaVector = spindle.getIaVector();
		
		num_steps = spindle.getNum_steps();
		
		for (int i = 0; i <= num_steps; i++){
			out.print((float)timeVector[i]);
			out.print('\t');
			out.print((float)IaVector[i]);
			out.println();
		}
	}
	*/
	
	
	private void generateFileResult(PrintWriter out, double[]output){
		double[] timeVector;
		int num_steps;
		
		timeVector = spindle.getTimeVector();
		
		num_steps = spindle.getNum_steps();
		
		for (int i = 0; i <= num_steps; i++){
			out.print((float)timeVector[i]);
			out.print('\t');
			out.print((float)output[i]);
			out.println();
		}
	}
	
	
	private void generateFileResult(PrintWriter out, double[]output1, double[]output2, double[]output3){
		double[] timeVector;
		int num_steps;
		
		timeVector = spindle.getTimeVector();
		
		num_steps = spindle.getNum_steps();
		
		for (int i = 0; i <= num_steps; i++){
			//System.out.println(i);
			//System.out.println(timeVector[i]);
			out.print((float)timeVector[i]);
			out.print('\t');
			out.print((float)output1[i]);
			out.print('\t');
			out.print((float)output2[i]);
			out.print('\t');
			out.print((float)output3[i]);
			out.println();
		}
	}
	
	
	
}