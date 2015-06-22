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


public class SpindleExportResultAsFigure extends SpindleSuper {
	
	
	public SpindleExportResultAsFigure() {

	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		PrintWriter out = new PrintWriter(response.getWriter());
		
		try {

			//getHiddenParams(request);

			input_type 		= ((ServletRequest) request).getParameter("input_type");		
			param 			= ((ServletRequest) request).getParameter("param");
			
			//System.out.println("input_type:" + input_type);
			//System.out.println("param:" + param);
			
			response.setContentType("text/html");
			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>Spindle Simulation Results</TITLE>");
			out.println("</HEAD>");
			out.println("<BODY BGCOLOR=#FAFFFF>");
			
					
			
			
			if (param.equals("input") || param.equals("fusimotor"))
				out.println("<TD><IMG SRC=\"PlotSpindleGraphs?type=" + param + "&input_type=" +  input_type +  "\" BORDER=1 WIDTH=800 HEIGHT=600/>");
			else{
				out.println("<TD><IMG SRC=\"PlotSpindleGraphs?type=" + param + "&input_type=" +  input_type + "\" BORDER=1 WIDTH=800 HEIGHT=400/>");
				out.println("<TR><TD><IMG SRC=\"PlotSpindleGraphs?type=" + "inputBottom" + "&input_type=" +  input_type + "\" BORDER=1 WIDTH=800 HEIGHT=300/>");
			}
			
			
			
			
			out.println("</BODY>");
			out.println("</HTML>");
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
}