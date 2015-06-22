

package br.remoto.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import br.remoto.GTO.GTOConstantInput;
import br.remoto.GTO.GTOInput;
import br.remoto.GTO.GTORampAndHoldInput;
import br.remoto.GTO.GTOSinusoidalInput;
import br.remoto.GTO.GTOTriangularInput;


public class GTOConfig extends GTOSuper {
	
	public GTOConfig() {}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
				
		out = new PrintWriter(response.getWriter());
				
		try {
		
			getHiddenParams(request);
			
			String changeInput 	= request.getParameter("changeInput");
			
			fiberID 			= request.getParameter("fiberID");	
			fiberID_aux = fiberID;
			
			System.out.println("fiberID: " + fiberID);
			System.out.println("changeInput: " + changeInput);
			
			this.numSfibers		= (int) Double.parseDouble(str_numSfibers);
			this.numFRfibers	= (int) Double.parseDouble(str_numFRfibers);
			this.numFFfibers	= (int) Double.parseDouble(str_numFFfibers);
						
			numTotalFibers = numSfibers + numFRfibers + numFFfibers;
			
			
			if(changeInput.equals(fiberID_aux) || changeInput.equals("all"))	this.changeInput(fiberID_aux);
												
			response.setContentType("text/html");
						
			showGTOPage("GTOConfig", out);
			
						
			for (int i = 1; i <= numSfibers; i++){
				
				fiberID = "#" + String.valueOf(i) + "_S";
				
				if(changeInput.equals("all"))this.changeInput(fiberID);
				
				showConfigOptions(i, "S", out);
				
				out.println("</TD>");
				
				if (i == 1){
					out.println("<td width = 300 rowspan= " + numTotalFibers + ">");
					this.showInputConfig(input_type, fiberID_aux, out);
					out.println("</td>");
					out.println("<td rowspan= " + numTotalFibers + ">");
					out.println("<IMG SRC=\"PlotGTOGraphs?graph_type=" + "InputConfig" +  "&input_type=" +  getInputType(fiberID_aux) + "&index_input=" +  calculateIndexInput(fiberID_aux) + "\" BORDER=1 WIDTH=800 HEIGHT=600/>");
				}
				
				out.println("</TR>");
			}
			
			
			for (int i = 1; i <= numFRfibers; i++){
				
				fiberID = "#" + String.valueOf(i) + "_FR";
				
				if(changeInput.equals("all"))this.changeInput(fiberID);
				
				showConfigOptions(i, "FR", out);
								
				out.println("</TD>");
				
				if (i == 1 && numSfibers == 0){
					out.println("<td width = 300 rowspan= " + numTotalFibers + ">");
					this.showInputConfig(input_type, fiberID_aux, out);
					out.println("</td>");
					out.println("<td rowspan= " + numTotalFibers + ">");
					out.println("<IMG SRC=\"PlotGTOGraphs?graph_type=" + "InputConfig" +  "&input_type=" +  getInputType(fiberID_aux) + "&index_input=" +  calculateIndexInput(fiberID_aux) + "\" BORDER=1 WIDTH=800 HEIGHT=600/>");
				}
				
				out.println("</TR>");
			}
			
			
			for (int i = 1; i <= numFFfibers; i++){
				
				fiberID = "#" + String.valueOf(i) + "_FF";
				
				if(changeInput.equals("all"))this.changeInput(fiberID);
				
				showConfigOptions(i, "FF", out);
				
				
				out.println("</TD>");
				
				if (i == 1 && numSfibers == 0 && numFRfibers == 0){
					out.println("<td width = 300 rowspan= " + numTotalFibers + ">");
					this.showInputConfig(input_type, fiberID_aux, out);
					out.println("</td>");
					out.println("<td rowspan= " + numTotalFibers + ">");
					out.println("<IMG SRC=\"PlotGTOGraphs?graph_type=" + "InputConfig" +  "&input_type=" +  getInputType(fiberID_aux) + "&index_input=" +  calculateIndexInput(fiberID_aux) + "\" BORDER=1 WIDTH=800 HEIGHT=600/>");
				}
				
				out.println("</TR>");
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