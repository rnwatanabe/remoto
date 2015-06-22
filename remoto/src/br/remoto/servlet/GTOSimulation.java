/* -------------------------------
 * ServletDemo2ChartGenerator.java
 * -------------------------------
 * (C) Copyright 2002-2004, by Object Refinery Limited.
 *
 */

package br.remoto.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.remoto.GTO.GolgiTendonOrgan;
import br.remoto.spindle_simulator.Input;
import br.remoto.spindle_simulator.RampAndHold;
import br.remoto.spindle_simulator.Sinusoidal;
import br.remoto.spindle_simulator.Spindle;


public class GTOSimulation extends GTOSuper {
	
	
	public GTOSimulation() {

	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		PrintWriter out = new PrintWriter(response.getWriter());
		try {
			
			getHiddenParams(request);
					
			this.numSfibers		= (int) Double.parseDouble(str_numSfibers);
			this.numFRfibers	= (int) Double.parseDouble(str_numFRfibers);
			this.numFFfibers	= (int) Double.parseDouble(str_numFFfibers);
			
			
			numTotalFibers = numSfibers + numFRfibers + numFFfibers;
			
			GolgiTendonOrgan gto = new GolgiTendonOrgan(input_type);
			
			gto.Simulation();
			
			
			response.setContentType("text/html");
			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>Spindle Simulation Results</TITLE>");
			out.println("</HEAD>");
			out.println("<BODY BGCOLOR=#FAFFFF>");
			
			out.println("<table border=0>");
			out.println("<TR><TH width=1333><H1>GTO Simulation Results</H1><BR></TH></TR>");
			out.println("<BR>");
			out.println("</table>");

			out.println("<table border=1>");
			out.println("<TR><TD rowspan=2 width=333>&nbsp;&nbsp;Please select an option:");
			out.println("<BR>");out.println("<BR>");out.println("<BR>");
						
			out.println("<FORM ACTION=\"/remoto/servlet/GTOStart\" METHOD=POST>");
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			out.println("<input type=\"hidden\" 	NAME = \"input_type\" 		VALUE = " + input_type 		+ " >");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" VALUE=\"Make a new simulation\">");
			out.println("</FORM>");
			
			
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleExportResults\" METHOD=POST>");
			out.println("<FORM ACTION=\"/remoto/servlet/GTOExportResults METHOD=POST>");
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" VALUE=\"Export result to a text file\">");
			out.println("</FORM>");
			
			out.println("<P>");
						
			out.println("<TD><IMG SRC=\"PlotGTOResult?graph_type=" + "Result"  + "&numFibers=" + numTotalFibers  + "\" BORDER=1 WIDTH=1000 HEIGHT=400/>");
			out.println("<TR><TD><IMG SRC=\"PlotGTOResult?graph_type=" + "inputBottom" + "&numFibers=" + numTotalFibers  + "\" BORDER=1 WIDTH=1000 HEIGHT=300/>");
			
			
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