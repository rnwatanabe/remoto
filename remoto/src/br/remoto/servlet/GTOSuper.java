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

import br.remoto.GTO.GTOConstantInput;
import br.remoto.GTO.GTOInput;
import br.remoto.GTO.GTORampAndHoldInput;
import br.remoto.GTO.GTOSinusoidalInput;
import br.remoto.GTO.GTOTriangularInput;
import br.remoto.GTO.GolgiTendonOrgan;
import br.remoto.spindle_simulator.Input;
import br.remoto.spindle_simulator.RampAndHold;
import br.remoto.spindle_simulator.Sinusoidal;
import br.remoto.spindle_simulator.Spindle;


public class GTOSuper extends HttpServlet {
	
	PrintWriter out;
	
	HttpServlet req;
	
	String input_type;	
	
	String param;
	
	String str_numSfibers;
	String str_numFRfibers;
	String str_numFFfibers;
	
	String fiberID;
	static String fiberID_aux;
	
	String initial_time;
	String time_step;
	String final_time;
	
	String start_time;
	String end_time;
	String start_time_sin;
	String end_time_sin;
	
	String initial_value;
	String final_value ;
	
	String amplitude;
	String frequency;
	String phase;
	String bias;
	
	String constant_value;
	
	String isRandom;
	String isRandom_aux;
	String stddev;
	String LPcutoff;
	String isRandomChecked;
	
	String changeInput;
	
	int numSfibers;
	int numFRfibers;
	int numFFfibers;
	
	int numTotalFibers;
			
		
	public static HashMap GTO_simulations = new HashMap();
	public static HashMap GTO_Inputs;
	

	public GTOSuper() {}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {}
	
	
	
	protected void sendHiddenParams(PrintWriter out){
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"str_numSfibers\" 	VALUE = " + str_numSfibers 			+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"str_numFRfibers\" 	VALUE = " + str_numFRfibers 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"str_numFFfibers\" 	VALUE = " + str_numFFfibers 		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"initial_time\" 	VALUE = " + initial_time 	+ " >");
		out.println("<input TYPE=\"hidden\" 	NAME = \"time_step\" 		VALUE = " + time_step 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"final_time\" 		VALUE = " + final_time 		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"start_time\"		VALUE = " + start_time 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"end_time\" 		VALUE = " + end_time 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"start_time_sin\"	VALUE = " + start_time_sin 	+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"end_time_sin\" 	VALUE = " + end_time_sin	+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"constant_value\" 	VALUE = " + constant_value 	+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"initial_value\" 	VALUE = " + initial_value 	+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"final_value\" 		VALUE = " + final_value 	+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"amplitude\" 		VALUE = " + amplitude 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"frequency\" 		VALUE = " + frequency 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"phase\" 			VALUE = " + phase	 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"bias\" 			VALUE = " + bias	 		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"stddev\" 			VALUE = " + stddev	 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"LPcutoff\" 		VALUE = " + LPcutoff 		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"isRandom\" 		VALUE = " + isRandom 		+ "	>");
		
		out.println("<input type=\"hidden\" 	NAME = \"input_type\" 		VALUE = " + input_type 		+ " >");
		out.println("<input type=\"hidden\" 	NAME = \"index_input\" 		VALUE = " + calculateIndexInput(fiberID_aux)		+ " >");
		
		out.println("<input type=\"hidden\" 	NAME = \"changeInput\" 		VALUE = " + "" 		+ " >");
	}
	
	protected void getHiddenParams(HttpServletRequest request){

		str_numSfibers 			= ((ServletRequest) request).getParameter("str_numSfibers");	
		str_numFRfibers 		= ((ServletRequest) request).getParameter("str_numFRfibers");	
		str_numFFfibers 		= ((ServletRequest) request).getParameter("str_numFFfibers");	
		
		initial_time 	= ((ServletRequest) request).getParameter("initial_time");
		time_step 		= ((ServletRequest) request).getParameter("time_step");
		final_time 		= ((ServletRequest) request).getParameter("final_time");
		
		start_time 		= ((ServletRequest) request).getParameter("start_time");
		end_time 		= ((ServletRequest) request).getParameter("end_time");
		start_time_sin 	= ((ServletRequest) request).getParameter("start_time_sin");
		end_time_sin 	= ((ServletRequest) request).getParameter("end_time_sin");
		
		constant_value 	= ((ServletRequest) request).getParameter("constant_value");
		
		initial_value 	= ((ServletRequest) request).getParameter("initial_value");
		final_value 	= ((ServletRequest) request).getParameter("final_value");
		
		amplitude		= ((ServletRequest) request).getParameter("amplitude");
		frequency 		= ((ServletRequest) request).getParameter("frequency");
		phase			= ((ServletRequest) request).getParameter("phase");
		bias 			= ((ServletRequest) request).getParameter("bias");
		
		stddev 			= ((ServletRequest) request).getParameter("stddev");
		LPcutoff 		= ((ServletRequest) request).getParameter("LPcutoff");
				
		isRandom_aux = request.getParameter("isRandom");
		
		if (isRandom_aux != null)  isRandom	= isRandom_aux;
		else	isRandom = "";
		
		isRandomChecked = (isRandom.equals("is_random") ? " CHECKED" : "");
		
		
		input_type 			= request.getParameter("input_type");
	}
	
	
	protected void showInputConfig(String input, String ID, PrintWriter out){
		
		if(input.equals("constant")){
			
						
			out.println("<FORM ACTION=\"/remoto/servlet/GTOConfig\" METHOD=POST>");
				
			out.println("<p>&nbsp;&nbsp;<b>" + ID + " Input setup (Constant tension): </b></p>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Constant value [N]:&nbsp; 				<input NAME = \"constant_value\" 	SIZE = 6   VALUE = " + constant_value + "	><br/><br/>");		
			
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"checkbox\" 	NAME=\"isRandom\" 	VALUE= \"is_random\"" + isRandomChecked +  ">"); 
			out.println("Apply gaussian random noise:");
			out.println("<br>");
			out.println("<br>");
				
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Standard deviation [N]:				<input NAME = \"stddev\" 		SIZE = 6     VALUE = " 	+ stddev 	+ "	><br/>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Low pass cutoff frequence [Hz]:		<input NAME = \"LPcutoff\"  	SIZE = 5	VALUE = " 	+ LPcutoff 	+ "	><br/>");
			
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"checkbox\" 	NAME=\"changeInput\" 	VALUE= \"all\"" +  ">"); 
			out.println("Apply this input for all fibers");
			out.println("<br>");
			
			out.println("<br>");
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			
			out.println("<input TYPE=\"hidden\" 	NAME = \"fiberID\" 			VALUE = " + ID 	+ "	>");
			
			out.println("<input TYPE=\"hidden\" 	NAME = \"changeInput\" 		VALUE = " + ID 	+ "	>");
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			
			out.println("<input type = \"submit\" value = \"Apply\"/>");
						
			out.println("</form>");
			
			
			/*
			out.println("<FORM ACTION=\"/remoto/servlet/GTOConfig\" METHOD=POST>");
			
			out.println("<input TYPE=\"hidden\" 	NAME = \"fiberID\" 			VALUE = " + ID 	+ "	>");
			
			out.println("<input TYPE=\"hidden\" 	NAME = \"changeInput\" 		VALUE = " + "all" 	+ "	>");
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			
			out.println("<input type = \"submit\" value = \"Apply generated input for all fibers\"/>");
						
			out.println("</form>");
			*/
			
			out.println("<br>");
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
		}
		
		
		
		if(input.equals("ramp-and-hold")){
			
			
			out.println("<FORM ACTION=\"/remoto/servlet/GTOConfig\" METHOD=POST>");
				
			out.println("<p>&nbsp;&nbsp;<b>" + ID + " Input setup (Ramp and hold tension): </b></p>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("Start time [s]:&nbsp;&nbsp;&nbsp;&nbsp; 		<input NAME = \"start_time\"	SIZE = 6   VALUE = " + start_time + "		><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("End time [s]:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	<input NAME = \"end_time\" 		SIZE = 6   VALUE = " + end_time + "		><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Initial value [N]: 							<input NAME = \"initial_value\" SIZE = 6   VALUE = " + initial_value + "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Final value [N]:&nbsp; 						<input NAME = \"final_value\" 	SIZE = 6   VALUE = " + final_value + "		><br/><br/>");
			
				
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"checkbox\" 	NAME=\"isRandom\" 	VALUE= \"is_random\"" + isRandomChecked +  ">"); 
			out.println("Apply gaussian random noise:");
			out.println("<br>");
			out.println("<br>");

			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Standard deviation [N]:				<input NAME = \"stddev\" 		SIZE = 6   VALUE = " 	+ stddev + "	><br/>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Low pass cutoff frequence [Hz]:		<input NAME = \"LPcutoff\"  	SIZE = 5	VALUE = " 	+ LPcutoff 	+ "	><br/>");
							
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"checkbox\" 	NAME=\"changeInput\" 	VALUE= \"all\"" +  ">"); 
			out.println("Apply this input for all fibers");
			out.println("<br>");
			
			out.println("<br>");
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");

			out.println("<input TYPE=\"hidden\" 	NAME = \"fiberID\" 			VALUE = " + ID 	+ "	>");
			
			out.println("<input TYPE=\"hidden\" 	NAME = \"changeInput\" 		VALUE = " + ID 	+ "	>");
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			
			out.println("<input type = \"submit\" value = \"Apply\"/>");
						
			out.println("</form>");
			
			
			
			/*
			out.println("<FORM ACTION=\"/remoto/servlet/GTOConfig\" METHOD=POST>");
			
			out.println("<input TYPE=\"hidden\" 	NAME = \"fiberID\" 			VALUE = " + ID 	+ "	>");
			
			out.println("<input TYPE=\"hidden\" 	NAME = \"changeInput\" 		VALUE = " + "all" 	+ "	>");
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			
			out.println("<input type = \"submit\" value = \"Apply generated input for all fibers\"/>");
						
			out.println("</form>");
			*/
			
			out.println("<br>");
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
		}
		
		
		
		if(input.equals("sinusoidal")){
			
			
			out.println("<FORM ACTION=\"/remoto/servlet/GTOConfig\" METHOD=POST>");
				
			out.println("<p>&nbsp;&nbsp;<b>" + ID + " Input setup (Sinusoidal tension): </b></p>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("Start time [s]:&nbsp;&nbsp;&nbsp;&nbsp; 		<input NAME = \"start_time_sin\"	SIZE = 6   VALUE = " + start_time_sin + "		><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("End time [s]:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	<input NAME = \"end_time_sin\" 		SIZE = 6   VALUE = " + end_time_sin + "		><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Amplitude [N]: 								<input NAME = \"amplitude\"		SIZE = 6   VALUE = " + amplitude + "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Frequency [Hz]:&nbsp; 							<input NAME = \"frequency\" 	SIZE = 6   VALUE = " + frequency + "		><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Phase [degrees]: 							<input NAME = \"phase\" 			SIZE = 6   VALUE = " + phase + "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Bias [N]:&nbsp; 								<input NAME = \"bias\" 			SIZE = 6   VALUE = " + bias + "		><br/><br/>");
			
				
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"checkbox\" 	NAME=\"isRandom\" 	VALUE= \"is_random\"" + isRandomChecked +  ">"); 
			out.println("Apply gaussian random noise:");
			out.println("<br>");
			out.println("<br>");

			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Standard deviation [N]:				<input NAME = \"stddev\" 		SIZE = 6   VALUE = " 	+ stddev + "	><br/>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Low pass cutoff frequence [Hz]:		<input NAME = \"LPcutoff\"  	SIZE = 5	VALUE = " 	+ LPcutoff 	+ "	><br/>");
			
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"checkbox\" 	NAME=\"changeInput\" 	VALUE= \"all\"" +  ">"); 
			out.println("Apply this input for all fibers");
			out.println("<br>");
			
			out.println("<br>");
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");

			out.println("<input TYPE=\"hidden\" 	NAME = \"fiberID\" 			VALUE = " + ID 	+ "	>");
			
			out.println("<input TYPE=\"hidden\" 	NAME = \"changeInput\" 		VALUE = " + ID 	+ "	>");
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			
			out.println("<input type = \"submit\" value = \"Apply\"/>");
						
			out.println("</form>");
			
			
			
			/*
			out.println("<FORM ACTION=\"/remoto/servlet/GTOConfig\" METHOD=POST>");
			
			out.println("<input TYPE=\"hidden\" 	NAME = \"fiberID\" 			VALUE = " + ID 	+ "	>");
			
			out.println("<input TYPE=\"hidden\" 	NAME = \"changeInput\" 		VALUE = " + "all" 	+ "	>");
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			
			out.println("<input type = \"submit\" value = \"Apply generated input for all fibers\"/>");
						
			out.println("</form>");
			*/
			
			out.println("<br>");
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
		}
		
		
		
		if(input.equals("triangular")){
			
			
			out.println("<FORM ACTION=\"/remoto/servlet/GTOConfig\" METHOD=POST>");
				
			out.println("<p>&nbsp;&nbsp;<b>" + ID + " Input setup (Triangular tension): </b></p>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("Start time [s]:&nbsp;&nbsp;&nbsp;&nbsp; 		<input NAME = \"start_time\"	SIZE = 6   VALUE = " + start_time + "		><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("End time [s]:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	<input NAME = \"end_time\" 		SIZE = 6   VALUE = " + end_time + "		><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Initial value [N]: 							<input NAME = \"initial_value\" SIZE = 6   VALUE = " + initial_value + "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Final value [N]:&nbsp; 						<input NAME = \"final_value\" 	SIZE = 6   VALUE = " + final_value + "		><br/><br/>");
							
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"checkbox\" 	NAME=\"isRandom\" 	VALUE= \"is_random\"" + isRandomChecked +  ">"); 
			out.println("Apply gaussian random noise:");
			out.println("<br>");
			out.println("<br>");

			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Standard deviation [N]:				<input NAME = \"stddev\" 		SIZE = 6   VALUE = " 	+ stddev + "	><br/>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Low pass cutoff frequence [Hz]:		<input NAME = \"LPcutoff\"  	SIZE = 5	VALUE = " 	+ LPcutoff 	+ "	><br/>");
			
			
			
			
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"checkbox\" 	NAME=\"changeInput\" 	VALUE= \"all\"" +  ">"); 
			out.println("Apply this input for all fibers");
			out.println("<br>");
			
			
			
			
			
			out.println("<br>");
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");

			out.println("<input TYPE=\"hidden\" 	NAME = \"fiberID\" 			VALUE = " + ID 	+ "	>");
			
			out.println("<input TYPE=\"hidden\" 	NAME = \"changeInput\" 		VALUE = " + ID 	+ "	>");
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
				
			out.println("<input type = \"submit\" value = \"Apply\"/>");
						
			out.println("</form>");
			
			
			
			/*
			out.println("<FORM ACTION=\"/remoto/servlet/GTOConfig\" METHOD=POST>");
			
			out.println("<input TYPE=\"hidden\" 	NAME = \"fiberID\" 			VALUE = " + ID 	+ "	>");
			
			out.println("<input TYPE=\"hidden\" 	NAME = \"changeInput\" 		VALUE = " + "all" 	+ "	>");
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			
			out.println("<input type = \"submit\" value = \"Apply generated input for all fibers\"/>");
						
			out.println("</form>");
			*/
			
			
			
			out.println("<br>");
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
		}
	}
	
	protected void showInputOptions(String inputType){
		
		String aux = inputType;
		
		if(fiberID.equals(fiberID_aux)) aux = input_type;
		
		if(aux.equals("constant")){
			out.println("<OPTION>	constant		</OPTION>");
			out.println("<OPTION>	ramp-and-hold	</OPTION>");
			out.println("<OPTION>	sinusoidal		</OPTION>");
			out.println("<OPTION>	triangular		</OPTION>");
		}
		else if(aux.equals("ramp-and-hold")){
			out.println("<OPTION>	ramp-and-hold	</OPTION>");
			out.println("<OPTION>	constant		</OPTION>");
			out.println("<OPTION>	sinusoidal		</OPTION>");
			out.println("<OPTION>	triangular		</OPTION>");
		}
		else if(aux.equals("sinusoidal")){
			out.println("<OPTION>	sinusoidal		</OPTION>");
			out.println("<OPTION>	constant		</OPTION>");
			out.println("<OPTION>	ramp-and-hold	</OPTION>");
			out.println("<OPTION>	triangular		</OPTION>");
		}
		else if(aux.equals("triangular")){
			out.println("<OPTION>	triangular		</OPTION>");
			out.println("<OPTION>	constant		</OPTION>");
			out.println("<OPTION>	ramp-and-hold	</OPTION>");
			out.println("<OPTION>	sinusoidal		</OPTION>");
		}
	}
	
	
	protected int calculateIndexInput(String ID){
		int index = 0;
		
		for (int i = 1; i <= numTotalFibers; i++){
			
			String aux = "#" + i + "_S";
			if(aux.equals(ID)){
				index = i;
			}
			
			aux = "#" + i + "_FR";
			if(aux.equals(ID)){
				index = i + numSfibers;
			}
			
			aux = "#" + i + "_FF";
			if(aux.equals(ID)){
				index = i + numSfibers + numFRfibers;
			}
		}
		return index;
	}
	
	
	protected void changeInput(String ID){
		int index;
		//GTOInput input;
		
		//System.out.println(ID);
		
		index = this.calculateIndexInput(ID);
		
		//System.out.println(getInputType("#4_FF"));
		
		//input = (GTOInput) GTO_Inputs.get(index);
		//input.showInputType();
		//GTO_Inputs.remove(index);
		//input = null;
		
		if(input_type.equals("constant")){
			GTOConstantInput inputObj = new GTOConstantInput(initial_time,time_step,final_time,
					constant_value,
					isRandom, stddev, LPcutoff);
			
			GTO_Inputs.put(index, inputObj);
			//GTOInputsVector[index] = inputObj;
		}
		else if(input_type.equals("ramp-and-hold")){
			GTORampAndHoldInput inputObj = new GTORampAndHoldInput(initial_time,time_step,final_time,
					start_time,end_time,
					initial_value,final_value,
					isRandom, stddev, LPcutoff);
			
			GTO_Inputs.put(index, inputObj);
			//GTOInputsVector[index] = inputObj;
		}
		else if(input_type.equals("sinusoidal")){
			GTOSinusoidalInput inputObj = new GTOSinusoidalInput(initial_time,time_step,final_time,
					start_time_sin,end_time_sin,
					amplitude, frequency, phase, bias,
					isRandom, stddev, LPcutoff);
			
			GTO_Inputs.put(index, inputObj);
			//GTOInputsVector[index] = inputObj;
		}
		else if(input_type.equals("triangular")){
			GTOTriangularInput inputObj = new GTOTriangularInput(initial_time,time_step,final_time,
					start_time,end_time,
					initial_value,final_value,
					isRandom, stddev, LPcutoff);
			
			GTO_Inputs.put(index, inputObj);
			//GTOInputsVector[index] = inputObj;
		}
		//System.out.println(getInputType("#4_FF"));
		//input = (GTOInput) GTO_Inputs.get(calculateIndexInput(ID));
		//input.showInputType();
	}
	
	
	
	protected String getInputType(String ID){
		
		int index;
		GTOInput input;
		
		index = this.calculateIndexInput(ID);
		
		input = (GTOInput)GTO_Inputs.get(index);
		//input = GTOInputsVector[index];
		
		return input.getInputType();
		
	}
	
	
	protected void showInputTypeAllFibers(){
		
		GTOInput input;
		
		for (int i = 1; i <= numTotalFibers; i++){
			input = (GTOInput) GTO_Inputs.get(i);
			//input = GTOInputsVector[i];
			input.showInputType();
		}
		
	}
	
	//protected abstract String getFiberID_aux();
	
	protected void showGraphForInput(String input){
		if(input.equals("constant")) 	out.println("<IMG SRC=\"PlotGTOConstantInput?type=" + "InputMini" +   "&index_input=" +  calculateIndexInput(fiberID) + "\" BORDER=1 WIDTH=200 HEIGHT=150/>");
		else if(input.equals("ramp-and-hold")) 	out.println("<IMG SRC=\"PlotGTORampAndHoldInput?type=" + "InputMini" +   "&index_input=" +  calculateIndexInput(fiberID) + "\" BORDER=1 WIDTH=200 HEIGHT=150/>");
		else if(input.equals("sinusoidal")) 	out.println("<IMG SRC=\"PlotGTOSinusoidalInput?type=" + "InputMini" +   "&index_input=" +  calculateIndexInput(fiberID) + "\" BORDER=1 WIDTH=200 HEIGHT=150/>");
		else if(input.equals("triangular")) 	out.println("<IMG SRC=\"PlotGTOTriangularInput?type=" + "InputMini" +   "&index_input=" +  calculateIndexInput(fiberID) + "\" BORDER=1 WIDTH=200 HEIGHT=150/>");
	}
	
	
	protected void showConfigOptions(int i, String fiber_type, PrintWriter out){
		
		//fiberID = "#" + String.valueOf(i) + "_" + fiber_type;

		out.println("<TR><TD align=\"center\">");
		
		out.println("<FORM ACTION=\"/remoto/servlet/GTOConfig\" METHOD=POST>");
		
		out.println("<BR>&nbsp;&nbsp;#" + i + " " + fiber_type + " fiber: &nbsp;&nbsp;  tension input&nbsp;&nbsp;");

		out.println("<SELECT NAME=\"input_type\">");
		showInputOptions(getInputType(fiberID));
		out.println("</SELECT>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"fiberID\" 			VALUE = " + fiberID 			+ "	>");
		
		out.println("&nbsp;&nbsp;");
		
		this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
		
		out.println("<INPUT TYPE=\"submit\" VALUE=\"Configure\"></INPUT>");
				
		out.println("</form>");
	
	}
	
	protected void showGTOPage(String servletApplyForm, PrintWriter out){
		
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>The Golgi Tendon Organ Model</TITLE>");
		out.println("</HEAD>");
		
		
		out.println("<BODY BGCOLOR=#FAFFFF>");
		out.println("<div class=Section1>");
				
		out.println("<table border=1>");
		
		out.println("<TR><TD  width = 400 align=\"center\"><b>GTO configuration</b><BR></TD></TR>");
		
		out.println("<TR><TD><BR><b>&nbsp;Enter the number of muscle fibers (by fiber type)<BR>");
		out.println("&nbsp;that insert into the GTO:<BR><BR></b>");
		
		out.println("<FORM ACTION=\"/remoto/servlet/GTOStart\" METHOD=POST>");

		out.println("&nbsp;&nbsp;&nbsp;&nbsp;Slow (S): 				<input NAME = \"str_numSfibers\"		SIZE = 6 	VALUE = " + str_numSfibers 	+ "><br/>");
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;Fast resistant (FR): 	<input NAME = \"str_numFRfibers\"		SIZE = 6 	VALUE = " + str_numFRfibers + "><br/>");
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;Fast fatigable (FF): 	<input NAME = \"str_numFFfibers\"		SIZE = 6 	VALUE = " + str_numFFfibers + "><br/><br/>");
		
		this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
		
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		
		
		out.println("<input type = \"submit\" value = \"Confirm\"/>");
					
		out.println("</form></TD>");
		
		
		//out.println("<TD width=1000 colspan=2 rowspan= " + String.valueOf(numTotalFibers + 1) + "><BR><b>&nbsp;Simulation time setup: <BR><BR></b>");
		out.println("<TD width=330 colspan=1><BR><b>&nbsp;Simulation time setup: <BR><BR></b>");
		
		out.println("<FORM ACTION=\"/remoto/servlet/" + servletApplyForm + "\" METHOD=POST>");
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("Initial time [s]: 								<input NAME = \"initial_time\" 		SIZE = 6	VALUE = " + initial_time 	+ " ><br/>");
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("Time step [s]:&nbsp; 							<input NAME = \"time_step\" 		SIZE = 6	VALUE = " + time_step 		+ "	><br/>");
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("Final time [s]:&nbsp; 							<input NAME = \"final_time\" 		SIZE = 6	VALUE = " + final_time 		+ "	><br/><br/>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"fiberID\" 			VALUE = " + fiberID 			+ "	>");
		
		this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
		
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;");out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
		
		out.println("<input type = \"submit\" value = \"Apply\"/>");
		out.println("</FORM></TD>");
		
		
		
		out.println("<TD width=600 colspan=1 align=\"center\"><BR><b>&nbsp;Simulation: <BR><BR></b>");
		
		out.println("<FORM ACTION=\"/remoto/servlet/GTOConfig2\" METHOD=POST>");		
		out.println("<input TYPE=\"hidden\" 	NAME = \"fiberID\" 			VALUE = " + fiberID 			+ "	>");
		this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
		out.println("<input type = \"submit\" value = \"Generate inputs\"/>");		
		out.println("</FORM>");
		
		if(servletApplyForm == "GTOConfig2"){
			out.println("<FORM ACTION=\"/remoto/servlet/GTOSimulation\" METHOD=POST>");
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			out.println("<input type = \"submit\" value = \"Start Simulation\"/>");		
			out.println("</FORM>");
		}
		out.println("</TD></TR>");
		
		out.println("</TD></TR>");
	
	}
	
	
}