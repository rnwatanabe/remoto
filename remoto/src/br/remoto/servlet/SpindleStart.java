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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class SpindleStart extends SpindleSuper {
	
	public SpindleStart() {}
		
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
				
		out = new PrintWriter(response.getWriter());
		
		try {
			
			getHiddenParams(request);
						
			
			response.setContentType("text/html");
			
			
			out.println("<html xmlns:v=\"urn:schemas-microsoft-com:vml\"");
			out.println("xmlns:o=\"urn:schemas-microsoft-com:office:office\"");
			out.println("xmlns:w=\"urn:schemas-microsoft-com:office:word\"");
			out.println("xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\"");
			out.println("xmlns=\"http://www.w3.org/TR/REC-html40\">");
			
			out.println("<HEAD>");
			out.println("<TITLE>The Muscle Spindle Simulator</TITLE>");
			out.println("</HEAD>");
			
			out.println("<body bgcolor=\"#faffff\" lang=PT-BR link=\"#006600\" vlink=\"#006600\">");
			
			out.println("<table width=100% height=10% border=0>");
			out.println("<TR><TH width=100%><H1>The Muscle Spindle Simulator</H1></TR>");
			out.println("</table>");

			out.println("<table width=100% height=90% border=1>");
			out.println("<TR><TH bgcolor=" + green2 + "width=20% height=10%>Select the type of stretch to be applied:");
			
			out.println("<br>");
			out.println("<br>");
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleStart\" METHOD=POST>");
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			
			out.println("&nbsp;&nbsp;&nbsp;<SELECT NAME=\"input_type\">");
			showInputOptions(input_type);
			out.println("</SELECT>");
			
			out.println("<INPUT TYPE=\"submit\" VALUE=\"Confirm\"></INPUT>");
			out.println("</FORM>");
			out.println("</TH>");
			
			
			showInputConfig(input_type, out);
			

			out.println("<TD align=\"center\" rowspan=3>");
			out.println("<IMG SRC=\"PlotSpindleGraphs?type=" + "InputStart" +  "&input_type=" +  input_type +  "\" BORDER=1 WIDTH=640 HEIGHT=480/>");
			out.println("</TD></TR>");
			
			
			out.println("<TR><TD align=\"center\" height=10%>");
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleSimulation\" METHOD=POST>");
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			out.println("<input type=\"hidden\" 	NAME = \"input_type\" 		VALUE = " + input_type 		+ " >");
			out.println("<br>");
			out.println("<INPUT SIZE=30 TYPE=\"submit\" VALUE=\"Start Simulation\"></INPUT>");
			out.println("</FORM>");
			out.println("</TD></TR>");
			
			
			out.println("<TR><TD height=80%>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Obs: if the graph is different from what you");
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println(" expected, please try to choose different");
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println(" parameter values (e.g., time step) because ");
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("an incompatibility may have arisen.");
			out.println("</TD></TR>");
			
			

			out.println("</table>");
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