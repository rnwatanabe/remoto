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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.remoto.GTO.GTOConstantInput;
import br.remoto.GTO.GTOInput;
import br.remoto.GTO.GolgiTendonOrgan;
import br.remoto.spindle_simulator.Constant;
import br.remoto.spindle_simulator.Input;
import br.remoto.spindle_simulator.RampAndHold;
import br.remoto.spindle_simulator.Sinusoidal;
import br.remoto.spindle_simulator.Spindle;
import br.remoto.spindle_simulator.Triangular;



public class GTOStart extends GTOSuper {
	
	public GTOStart() {}
	
	//protected String getFiberID_aux(){
	//	return fiberID_aux;
	//}
		
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
				
		out = new PrintWriter(response.getWriter());
				
		try {
			
			getHiddenParams(request);
			
			this.numSfibers		= (int) Double.parseDouble(str_numSfibers);
			this.numFRfibers	= (int) Double.parseDouble(str_numFRfibers);
			this.numFFfibers	= (int) Double.parseDouble(str_numFFfibers);
			
			numTotalFibers = numSfibers + numFRfibers + numFFfibers;
			
			//System.out.println("numTotalFibers: " 	+ numTotalFibers);
			
						
			GTO_Inputs 				= new HashMap(numTotalFibers);	
			//GTOInputsVector = new GTOInput [numTotalFibers];
			//gtosVector = new GolgiTendonOrgan[1];
			
			for(int i = 1; i <= numTotalFibers; i++){
				
				GTOConstantInput inputObj = new GTOConstantInput(initial_time,time_step,final_time,
						constant_value,
						isRandom, stddev, LPcutoff);
				
				//GTOInputsVector[i] = inputObj;
				GTO_Inputs.put(i, inputObj);
				//System.out.println("Criando cte input obj");
			}
			
			
			response.setContentType("text/html");
			
			showGTOPage("GTOStart", out);
			
			for (int i = 1; i <= numSfibers; i++){
				fiberID = "#" + String.valueOf(i) + "_S";
				showConfigOptions(i, "S", out);
				out.println("</TD>");
				out.println("</TR>");
			}
			
			
			for (int i = 1; i <= numFRfibers; i++){
				fiberID = "#" + String.valueOf(i) + "_FR";
				showConfigOptions(i, "FR", out);
				out.println("</TD>");
				out.println("</TR>");
			}
			
			
			for (int i = 1; i <= numFFfibers; i++){
				fiberID = "#" + String.valueOf(i) + "_FF";
				showConfigOptions(i, "FF", out);
				out.println("</TD>");
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