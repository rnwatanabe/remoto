

package br.remoto.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import br.remoto.GTO.GTOConstantInput;



public class GTOConfig2 extends GTOSuper {
	
	public GTOConfig2() {}
	
	//protected String getFiberID_aux(){
	//	return fiberID_aux;
	//}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
				
		out = new PrintWriter(response.getWriter());
				
		try {
		
			getHiddenParams(request);
			
			fiberID = request.getParameter("fiberID");	
			
			if(fiberID == null) fiberID_aux = fiberID;
			else fiberID_aux = "#1_S";

			
			this.numSfibers		= (int) Double.parseDouble(str_numSfibers);
			this.numFRfibers	= (int) Double.parseDouble(str_numFRfibers);
			this.numFFfibers	= (int) Double.parseDouble(str_numFFfibers);
			
			
			numTotalFibers = numSfibers + numFRfibers + numFFfibers;
			
			
			response.setContentType("text/html");
			
			
			showGTOPage("GTOConfig2", out);
			
			
						
			
			
			for (int i = 1; i <= numSfibers; i++){
				
				fiberID = "#" + String.valueOf(i) + "_S";
				
				showConfigOptions(i, "S", out);
				
				showGraphForInput(getInputType(fiberID));

				out.println("</TD>");
				
				if (i == 1){
					out.println("<th colspan=2 rowspan= " + 3 + ">");
					out.println("<IMG SRC=\"PlotGTOGraphs?graph_type=" + "InputConfig" +  "&input_type=" +  getInputType(fiberID_aux) + "&index_input=" +  calculateIndexInput(fiberID_aux) + "\" BORDER=1 WIDTH=800 HEIGHT=600/>");
				}
				
				out.println("</TR>");
			}
			
			
			for (int i = 1; i <= numFRfibers; i++){
				
				fiberID = "#" + String.valueOf(i) + "_FR";
				
				showConfigOptions(i, "FR", out);
				
				showGraphForInput(getInputType(fiberID));

				out.println("</TD>");
				
				if (i == 1 && numSfibers == 0){
					out.println("<th colspan=2 rowspan= " + 3 + ">");
					out.println("<IMG SRC=\"PlotGTOGraphs?graph_type=" + "InputConfig" +  "&input_type=" +  getInputType(fiberID_aux) + "&index_input=" +  calculateIndexInput(fiberID_aux) + "\" BORDER=1 WIDTH=800 HEIGHT=600/>");
				}
				
				out.println("</TR>");
			}
			
			
			for (int i = 1; i <= numFFfibers; i++){
				
				fiberID = "#" + String.valueOf(i) + "_FF";
				
				showConfigOptions(i, "FF", out);
				
				showGraphForInput(getInputType(fiberID));
				
				out.println("</TD>");
				
				if (i == 1 && numSfibers == 0 && numFRfibers == 0){
					out.println("<th colspan=2 rowspan= " + 3 + ">");
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