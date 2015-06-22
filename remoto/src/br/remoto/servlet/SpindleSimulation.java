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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.remoto.spindle_simulator.Spindle;


public class SpindleSimulation extends SpindleSuper {
	
	public static HashMap spindle_simulations = new HashMap();	
	public static HashMap inputs = new HashMap();	
	
	public SpindleSimulation() {
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		PrintWriter out = new PrintWriter(response.getWriter());
		try {
			
			getHiddenParams(request);
			
			Spindle spindle = new Spindle(gamma_static, gamma_dynamic, input_type);
			
			spindle.Simulation();
			
			response.setContentType("text/html");
			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>Spindle Simulation Results</TITLE>");
			out.println("</HEAD>");
			out.println("<BODY BGCOLOR=#FAFFFF>");
			
			out.println("<table width=100% border=0>");
			out.println("<TR><TH width=100%><H1>Spindle Simulation Results</H1><BR></TH></TR>");
			out.println("<BR>");
			out.println("</table>");

			out.println("<table width=100% border=1>");
			out.println("<TR><TD bgcolor=" + green2 + "width=20%>&nbsp;&nbsp;Please choose a graph:");
			
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleResults\" METHOD=POST>");	
			
			inputChecked 		= (param.equals("input") 		? " CHECKED" : "");
			fusimotorChecked 	= (param.equals("fusimotor") 	? " CHECKED" : "");
			tensionChecked 		= (param.equals("tension") 		? " CHECKED" : "");
			primaryChecked 		= (param.equals("primary") 		? " CHECKED" : "");
			secondaryChecked 	= (param.equals("secondary") 	? " CHECKED" : "");
			
			out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"radio\" 	NAME=\"chart\" 				VALUE=\"input\"" 		+ inputChecked 		+ "> Input (fascicle stretch)");
			out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"radio\" 	NAME=\"chart\" 				VALUE=\"fusimotor\"" 	+ fusimotorChecked 	+ "> Fusimotor Activity");
			out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"radio\" 	NAME=\"chart\" 				VALUE=\"tension\"" 		+ tensionChecked 	+ "> Intrafusal Fiber Tension");
			out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"radio\" 	NAME=\"chart\" 				VALUE=\"primary\"" 		+ primaryChecked 	+ "> Primary Afferent Activity");
			out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"radio\" 	NAME=\"chart\" 				VALUE=\"secondary\"" 	+ secondaryChecked 	+ "> Secondary Afferent Activity");
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			out.println("<input type=\"hidden\" 	NAME = \"input_type\" 		VALUE = " + input_type 		+ " >");
			
			out.println("<BR>");out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" VALUE=\"Plot Result\">");
			out.println("</FORM>");
			
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleStart\" METHOD=POST>");
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			out.println("<input type=\"hidden\" 	NAME = \"input_type\" 		VALUE = " + input_type 		+ " >");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" VALUE=\"Make a new simulation\">");
			out.println("</FORM>");
			
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleExportResults?type=" + param + "\" METHOD=POST>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" VALUE=\"Export result to a text file\">");
			out.println("</FORM>");
			
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleExportResultAsFigure?param=" + param + "&input_type=" + input_type + "\" METHOD=POST>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" VALUE=\"Export result as png figure\">");
			out.println("</FORM>");
			
			out.println("<P>");
			
			out.println("<TD align=\"center\"><APPLET CODEBASE=\"http://remoto.leb.usp.br/remoto\"");
			out.println("ARCHIVE=\"AppletChartGeneratorSpindle.jar, jfreechart-1.0.13.jar,jcommon-1.0.16.jar\"");
			out.println("CODE=br.remoto.applet.AppletChartGeneratorSpindle.class");
			out.println("width=800 height=600");
			out.println("ALT=\"You should see an applet, not this text.\">");
			out.println("<PARAM name=\"param\" value=" + param + ">");
			out.println("</APPLET>");
			
			out.println("<TD bgcolor=" + green2 +  "width=20%>");
			out.println("&nbsp;&nbsp;Interative Graph Commands:");
			out.println("<BR>");out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Mouse click and hold + move down and right:");
			out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("-> ZOOM IN selected region");
			
			out.println("<BR>");
			out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Mouse click and hold + move up and left:");
			out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("-> AUTO-RANGE");
			
			out.println("<BR>");
			out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Hold CTRL + Mouse click, hold and move:");
			out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("-> Drag graph");
			
			out.println("<BR>");
			out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println(" Mouse right-click:");
			out.println("<BR>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("-> Display POP-UP options");
			
					
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