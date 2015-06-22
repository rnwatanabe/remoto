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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.remoto.spindle_simulator.Constant;
import br.remoto.spindle_simulator.RampAndHold;
import br.remoto.spindle_simulator.Sinusoidal;
import br.remoto.spindle_simulator.Triangular;


public class SpindleSuper extends HttpServlet {
	
	PrintWriter out;
	
	HttpServlet req;
		
	String param;
	String input_type;	
	
	String initial_time;
	String time_step;
	String final_time;
	
	String constant_value;
	
	String start_time;
	String end_time;
	String ramp_velocity;
	
	String start_time_sin;
	String end_time_sin;
	
	String start_time_triang;
	String end_time_triang;
	
	String initial_value;
	String final_value ;
	
	String amplitude;
	String frequency;
	String phase;
	String bias;
	
	
	String gamma_static;
	String gamma_dynamic;
	
	String isRandom;
	String stddev;
	String LPcutoff;
	String isRandomChecked;
	
	String inputChecked;
	String fusimotorChecked;
	String tensionChecked;
	String primaryChecked;
	String secondaryChecked;
	
	String green1 = " #faffff ";
	String green2 = " #E6F7EC ";
	
	public SpindleSuper() {}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
	}
	
	protected void sendHiddenParams(PrintWriter out){
		
		out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 			VALUE=\"input\" CHECKED>"); 
		out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 			VALUE=\"fusimotor\">"); 
		out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 			VALUE=\"tension\">"); 
		out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 			VALUE=\"primary\">"); 
		out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 			VALUE=\"secondary\">");
		
		//out.println("<input type=\"hidden\" 	NAME = \"input_type\" 		VALUE = " + input_type 		+ " >");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"initial_time\" 	VALUE = " + initial_time 	+ " >");
		out.println("<input TYPE=\"hidden\" 	NAME = \"time_step\" 		VALUE = " + time_step 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"final_time\" 		VALUE = " + final_time 		+ "	>");

		out.println("<input TYPE=\"hidden\" 	NAME = \"start_time\"		VALUE = " + start_time 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"ramp_velocity\" 	VALUE = " + ramp_velocity 	+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"start_time_sin\"	VALUE = " + start_time_sin 	+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"end_time_sin\" 	VALUE = " + end_time_sin	+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"start_time_triang\"	VALUE = " + start_time_triang 	+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"end_time_triang\" 		VALUE = " + end_time_triang		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"constant_value\" 	VALUE = " + constant_value 	+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"initial_value\" 	VALUE = " + initial_value 	+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"final_value\" 		VALUE = " + final_value 	+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"amplitude\" 		VALUE = " + amplitude 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"frequency\" 		VALUE = " + frequency 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"phase\" 			VALUE = " + phase	 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"bias\" 			VALUE = " + bias	 		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"gamma_static\" 	VALUE = " + gamma_static 	+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"gamma_dynamic\" 	VALUE = " + gamma_dynamic 	+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"stddev\" 			VALUE = " + stddev	 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"LPcutoff\" 		VALUE = " + LPcutoff 		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"isRandom\" 		VALUE = " + isRandom 		+ "	>");
	}
	
	
	protected void resetParameterValues(PrintWriter out){
		
				
		out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 				VALUE=\"input\" CHECKED>"	); 
		out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 				VALUE=\"fusimotor\">"		); 
		out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 				VALUE=\"tension\">"			); 
		out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 				VALUE=\"primary\">"			); 
		out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 				VALUE=\"secondary\">"		);
		
		out.println("<input type=\"hidden\" 	NAME = \"input_type\" 			VALUE = " + input_type 	+ " >");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"initial_time\" 		VALUE = " + "0.0" 		+ " >");
		out.println("<input TYPE=\"hidden\" 	NAME = \"time_step\" 			VALUE = " + "0.001" 	+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"final_time\" 			VALUE = " + "3.0" 		+ "	>");

		out.println("<input TYPE=\"hidden\" 	NAME = \"start_time\"			VALUE = " + "1.0" 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"ramp_velocity\" 		VALUE = " + "0.11" 		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"start_time_sin\"		VALUE = " + "0.0" 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"end_time_sin\" 		VALUE = " + "3.0"		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"start_time_triang\"	VALUE = " + "1.0" 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"end_time_triang\" 		VALUE = " + "2.0"		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"constant_value\" 		VALUE = " + "0.95" 		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"initial_value\" 		VALUE = " + "0.95" 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"final_value\" 			VALUE = " + "1.08" 		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"amplitude\" 			VALUE = " + "0.012" 	+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"frequency\" 			VALUE = " + "1" 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"phase\" 				VALUE = " + "90"	 	+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"bias\" 				VALUE = " + "0.95"	 	+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"gamma_static\" 		VALUE = " + "50.0" 		+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"gamma_dynamic\" 		VALUE = " + "50.0" 		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"stddev\" 				VALUE = " + "0.001"	 	+ "	>");
		out.println("<input TYPE=\"hidden\" 	NAME = \"LPcutoff\" 			VALUE = " + "10.0" 		+ "	>");
		
		out.println("<input TYPE=\"hidden\" 	NAME = \"isRandom\" 			VALUE = " + "is_random" + "	>");
		
		
	}


	
	protected void getHiddenParams(HttpServletRequest request){

		param 				= ((ServletRequest) request).getParameter("chart");
		input_type 			= ((ServletRequest) request).getParameter("input_type");		
						
		initial_time 		= ((ServletRequest) request).getParameter("initial_time");
		time_step 			= ((ServletRequest) request).getParameter("time_step");
		final_time 			= ((ServletRequest) request).getParameter("final_time");
		
		start_time 			= ((ServletRequest) request).getParameter("start_time");
		ramp_velocity		= ((ServletRequest) request).getParameter("ramp_velocity");
		
		start_time_sin 		= ((ServletRequest) request).getParameter("start_time_sin");
		end_time_sin 		= ((ServletRequest) request).getParameter("end_time_sin");
		
		start_time_triang 	= ((ServletRequest) request).getParameter("start_time_triang");
		end_time_triang 	= ((ServletRequest) request).getParameter("end_time_triang");
		
		constant_value 		= ((ServletRequest) request).getParameter("constant_value");
		
		initial_value 		= ((ServletRequest) request).getParameter("initial_value");
		final_value 		= ((ServletRequest) request).getParameter("final_value");
		
		amplitude			= ((ServletRequest) request).getParameter("amplitude");
		frequency 			= ((ServletRequest) request).getParameter("frequency");
		phase				= ((ServletRequest) request).getParameter("phase");
		bias 				= ((ServletRequest) request).getParameter("bias");
		
		
		gamma_static 		= ((ServletRequest) request).getParameter("gamma_static");
		gamma_dynamic 		= ((ServletRequest) request).getParameter("gamma_dynamic");
		
		isRandom = "";
		if (request.getParameter("isRandom") != null)  isRandom	= request.getParameter("isRandom");
		
		stddev 			= request.getParameter("stddev");
		LPcutoff 		= request.getParameter("LPcutoff");
		isRandomChecked = (isRandom.equals("is_random") ? " CHECKED" : "");
		
	}
		
		
	protected void showInputConfig(String input, PrintWriter out){
		
		if(input_type.equals("constant")){
			
			new Constant(initial_time,time_step,final_time,
							constant_value,
							isRandom, stddev, LPcutoff);
			
			out.println("<TD bgcolor=" + green2 + "width=20% rowspan=3><BR>&nbsp;&nbsp;&nbsp;<b>Simulation time setup: </b>");
			out.println("<BR/>");out.println("<BR/>");
			
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleStart\" METHOD=POST>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Initial time [s]: 								<input NAME = \"initial_time\" 		SIZE = 6	VALUE = " + initial_time 	+ " ><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Time step [s]:&nbsp; 							<input NAME = \"time_step\" 		SIZE = 6	VALUE = " + time_step 		+ "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Final time [s]:&nbsp; 							<input NAME = \"final_time\" 		SIZE = 6	VALUE = " + final_time 		+ "	><br/><br/>");
				
			out.println("<p>&nbsp;&nbsp;<b>Input setup (Constant stretch): </b></p>");

			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("(L0 is the optimal fascicle length)");
			out.println("<br>");
			out.println("<br>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Constant value [L0]:&nbsp; 					<input NAME = \"constant_value\" 	SIZE = 6	VALUE = " + constant_value + "	><br/><br/>");
			
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("Gamma static [pps]:&nbsp;&nbsp;&nbsp;&nbsp;	<input NAME = \"gamma_static\" 		SIZE = 6	VALUE = " + gamma_static + "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Gamma dynamic [pps]: 							<input NAME = \"gamma_dynamic\" 	SIZE = 6	VALUE = " + gamma_dynamic + "	><br/>");			
			
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"checkbox\" 	NAME=\"isRandom\" 	VALUE= \"is_random\"" + isRandomChecked +  ">"); 
			out.println("Apply gaussian random noise:");
			out.println("<br>");
			out.println("<br>");
				
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("Standard deviation [L0]:				<input NAME = \"stddev\" 		SIZE = 6	VALUE = " 	+ stddev 	+ "	><br/>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("Low pass cutoff frequency [Hz]:		<input NAME = \"LPcutoff\"  	SIZE = 6	VALUE = " 	+ LPcutoff 	+ "	><br/>");
			
			
			
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			out.println("<input type=\"hidden\" 	NAME = \"input_type\" 		VALUE = " + input_type 		+ " >");
			
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			
			out.println("<input type = \"submit\" value = \"Apply\"/>");
			out.println("</form>");
			
			
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleStart\" METHOD=POST>");
			this.resetParameterValues(out); //warning: the order this function is called is important.
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<input type = \"submit\" value = \"Reset to original values\"/>");
			out.println("</form>");
			
			
			
			out.println("</TD>");
		}




		
		if(input_type.equals("ramp-and-hold")){
			
			
			new RampAndHold(initial_time,time_step,final_time,
					start_time,ramp_velocity,
					initial_value,final_value,
					isRandom, stddev, LPcutoff);


			out.println("<TD bgcolor=" + green2 + "width=20% rowspan=3><BR>&nbsp;&nbsp;&nbsp;<b>Simulation time setup: </b>");
			out.println("<BR/>");out.println("<BR/>");
			
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleStart\" METHOD=POST>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Initial time [s]: 								<input NAME = \"initial_time\" 	SIZE = 6	VALUE = " + initial_time 	+ " ><br/>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Time step [s]:&nbsp; 							<input NAME = \"time_step\" 	SIZE = 6	VALUE = " + time_step 		+ "	><br/>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Final time [s]:&nbsp; 							<input NAME = \"final_time\" 	SIZE = 6	VALUE = " + final_time 		+ "	><br/><br/>");
				
			out.println("<p>&nbsp;&nbsp;<b>Input setup (Ramp-and-hold stretch): </b></p>");
							
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("(L0 is the optimal fascicle length)");
			out.println("<br>");
			out.println("<br>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("Start time [s]:&nbsp;&nbsp;			 				<input NAME = \"start_time\"	SIZE = 6	VALUE = " + start_time + "		><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			//out.println("End time [s]:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	<input NAME = \"end_time\" 		VALUE = " + end_time + "		><br/>");
			out.println("Ramp velocity [L0/s]: 								 	<input NAME = \"ramp_velocity\" SIZE = 6	VALUE = " + ramp_velocity + "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Initial value [L0]: 									<input NAME = \"initial_value\" SIZE = 6	VALUE = " + initial_value + "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Final value [L0]:&nbsp; 								<input NAME = \"final_value\" 	SIZE = 6	VALUE = " + final_value + "		><br/><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("Gamma static [pps]:&nbsp;&nbsp;&nbsp;&nbsp;			<input NAME = \"gamma_static\" 	SIZE = 6	VALUE = " + gamma_static + "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Gamma dynamic [pps]: 									<input NAME = \"gamma_dynamic\" SIZE = 6	VALUE = " + gamma_dynamic + "	><br/>");
				
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"checkbox\" 	NAME=\"isRandom\" 	VALUE= \"is_random\"" + isRandomChecked +  ">"); 
			out.println("Apply gaussian random noise:");
			out.println("<br>");
			out.println("<br>");

			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Standard deviation [L0]:	<input NAME = \"stddev\" 		SIZE = 6	VALUE = " 	+ stddev + "	><br/>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("Low pass cutoff frequency [Hz]:		<input NAME = \"LPcutoff\"  	SIZE = 6	VALUE = " 	+ LPcutoff 	+ "	><br/>");
									
			
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			out.println("<input type=\"hidden\" 	NAME = \"input_type\" 		VALUE = " + input_type 		+ " >");
			
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<input type = \"submit\" value = \"Apply\"/>");
			
			out.println("</form>");
			
			
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleStart\" METHOD=POST>");
			this.resetParameterValues(out); //warning: the order this function is called is important.
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<input type = \"submit\" value = \"Reset to original values\"/>");
			
			out.println("</form>");
			
			
			
			
			out.println("</TD>");
		}
		
		
		
		if(input_type.equals("sinusoidal")){
			
			new Sinusoidal(initial_time,time_step,final_time,
							start_time_sin,end_time_sin,
							amplitude, frequency, phase, bias,
							isRandom, stddev, LPcutoff);
			
			out.println("<TD bgcolor=" + green2 + "width=20% rowspan=3><BR>&nbsp;&nbsp;&nbsp;<b>Simulation time setup: </b>");
			out.println("<BR/>");out.println("<BR/>");
			
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleStart\" METHOD=POST>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Initial time [s]: 								<input NAME = \"initial_time\" 	SIZE = 6	VALUE = " + initial_time 	+ " ><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Time step [s]:&nbsp; 							<input NAME = \"time_step\" 	SIZE = 6	VALUE = " + time_step 		+ "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Final time [s]:&nbsp; 							<input NAME = \"final_time\" 	SIZE = 6	VALUE = " + final_time 		+ "	><br/><br/>");
				
			out.println("<p>&nbsp;&nbsp;<b>Input setup (Sinusoidal stretch): </b></p>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("(L0 is the optimal fascicle length)");
			out.println("<br>");
			out.println("<br>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("Start time [s]:&nbsp;&nbsp;&nbsp;&nbsp; 		<input NAME = \"start_time_sin\"	SIZE = 6	VALUE = " + start_time_sin + "		><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("End time [s]:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	<input NAME = \"end_time_sin\" 		SIZE = 6	VALUE = " + end_time_sin + "		><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Amplitude [L0]: 								<input NAME = \"amplitude\"		SIZE = 6	VALUE = " + amplitude + "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Frequency [Hz]:&nbsp; 							<input NAME = \"frequency\" 	SIZE = 6	VALUE = " + frequency + "		><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Phase [degrees]: 							<input NAME = \"phase\" 		SIZE = 6	VALUE = " + phase + "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Bias [L0]:&nbsp; 								<input NAME = \"bias\" 			SIZE = 6	VALUE = " + bias + "		><br/><br/>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("Gamma static [pps]:&nbsp;&nbsp;&nbsp;&nbsp;	<input NAME = \"gamma_static\" 	SIZE = 6	VALUE = " + gamma_static + "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Gamma dynamic [pps]: 							<input NAME = \"gamma_dynamic\" SIZE = 6	VALUE = " + gamma_dynamic + "	><br/>");
				
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"checkbox\" 	NAME=\"isRandom\" 	VALUE= \"is_random\"" + isRandomChecked +  ">"); 
			out.println("Apply gaussian random noise:");
			out.println("<br>");
			out.println("<br>");

			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Standard deviation [L0]:	<input NAME = \"stddev\" 		SIZE = 6	VALUE = " 	+ stddev + "	><br/>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Low pass cutoff frequency [Hz]:		<input NAME = \"LPcutoff\"  	SIZE = 6	VALUE = " 	+ LPcutoff 	+ "	><br/>");
			
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			out.println("<input type=\"hidden\" 	NAME = \"input_type\" 		VALUE = " + input_type 		+ " >");
			
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			
			out.println("<input type = \"submit\" value = \"Apply\"/>");
			
			
			out.println("</form>");
			
			
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleStart\" METHOD=POST>");
			this.resetParameterValues(out); //warning: the order this function is called is important.
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<input type = \"submit\" value = \"Reset to original values\"/>");
			out.println("</form>");
			
			
			out.println("</TD>");
		}
		
		
		
		if(input_type.equals("triangular")){
			
			
			new Triangular(initial_time,time_step,final_time,
					start_time,end_time_triang,
					initial_value,final_value,
					isRandom, stddev, LPcutoff);


			out.println("<TD bgcolor=" + green2 + "width=20% rowspan=3><BR>&nbsp;&nbsp;&nbsp;<b>Simulation time setup: </b>");
			out.println("<BR/>");out.println("<BR/>");
			
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleStart\" METHOD=POST>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Initial time [s]: 								<input NAME = \"initial_time\" 	SIZE = 6	VALUE = " + initial_time 	+ " ><br/>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Time step [s]:&nbsp; 							<input NAME = \"time_step\" 	SIZE = 6	VALUE = " + time_step 		+ "	><br/>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Final time [s]:&nbsp; 							<input NAME = \"final_time\" 	SIZE = 6	VALUE = " + final_time 		+ "	><br/><br/>");
				
			out.println("<p>&nbsp;&nbsp;<b>Input setup (Triangular stretch): </b></p>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("(L0 is the optimal fascicle length)");
			out.println("<br>");
			out.println("<br>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("Start time [s]:&nbsp;&nbsp;&nbsp;&nbsp; 		<input NAME = \"start_time_triang\"		SIZE = 6	VALUE = " + start_time_triang + "		><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("End time [s]:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	<input NAME = \"end_time_triang\" 		SIZE = 6	VALUE = " + end_time_triang + "		><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Initial value [L0]: 							<input NAME = \"initial_value\" SIZE = 6	VALUE = " + initial_value + "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Final value [L0]:&nbsp; 						<input NAME = \"final_value\" 	SIZE = 6	VALUE = " + final_value + "		><br/><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");	
			out.println("Gamma static [pps]:&nbsp;&nbsp;&nbsp;&nbsp;	<input NAME = \"gamma_static\" 	SIZE = 6	VALUE = " + gamma_static + "	><br/>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Gamma dynamic [pps]: 							<input NAME = \"gamma_dynamic\" SIZE = 6	VALUE = " + gamma_dynamic + "	><br/>");
				
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<INPUT TYPE=\"checkbox\" 	NAME=\"isRandom\" 	VALUE= \"is_random\"" + isRandomChecked +  ">"); 
			out.println("Apply gaussian random noise:");
			out.println("<br>");
			out.println("<br>");

			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Standard deviation [L0]:	<input NAME = \"stddev\" 		SIZE = 6	VALUE = " 	+ stddev + "	><br/>");
			
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Low pass cutoff frequency [Hz]:		<input NAME = \"LPcutoff\"  	SIZE = 6	VALUE = " 	+ LPcutoff 	+ "	><br/>");
									
			this.sendHiddenParams(out); //warning: the order this function is called is important. Must be called just before the form submit
			out.println("<input type=\"hidden\" 	NAME = \"input_type\" 		VALUE = " + input_type 		+ " >");
			
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			
			out.println("<input type = \"submit\" value = \"Apply\"/>");
			
			
			out.println("</form>");
			
			
			out.println("<FORM ACTION=\"/remoto/servlet/SpindleStart\" METHOD=POST>");
			this.resetParameterValues(out); //warning: the order this function is called is important.
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<input type = \"submit\" value = \"Reset to original values\"/>");
			out.println("</form>");
			
			
			out.println("</TD>");
		}
	}


	protected void showAllParameters(){

		System.out.println("input_type: " + input_type);
		
		System.out.println("initial_time: " + initial_time);
		System.out.println("time_step: " + time_step);
		System.out.println("final_time: " + final_time);
		
		System.out.println("start_time: " + start_time);
		System.out.println("ramp_velocity: " + ramp_velocity);
		
		System.out.println("start_time_sin: " + start_time_sin);
		System.out.println("end_time_sin: " + end_time_sin);
		
		System.out.println("start_time_triang: " + start_time_triang);
		System.out.println("end_time_triang: " + end_time_triang);
		
		System.out.println("constant_value: " + constant_value);
		
		System.out.println("initial_value: " + initial_value);
		System.out.println("final_value: " + final_value);
		
		System.out.println("amplitude: " + amplitude);
		System.out.println("frequency: " + frequency);
		System.out.println("phase: " + phase);
		System.out.println("bias: " + bias);
		
		System.out.println("gamma_static: " + gamma_static);
		System.out.println("gamma_dynamic: " + gamma_dynamic);
		
		System.out.println("isRandom: " + isRandom);
		
		System.out.println("stddev: " + stddev);
		System.out.println("LPcutoff: " + LPcutoff);
		System.out.println("isRandomChecked: " + isRandomChecked);
		
		System.out.println("param: " + param);
	}
	
	protected void showAllParameters2(){
		
		System.out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 			VALUE=\"input\" CHECKED>"); 
		System.out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 			VALUE=\"fusimotor\">"); 
		System.out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 			VALUE=\"tension\">"); 
		System.out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 			VALUE=\"primary\">"); 
		System.out.println("<INPUT TYPE=\"hidden\" 	NAME = \"chart\" 			VALUE=\"secondary\">");
		
		System.out.println("<input type=\"hidden\" 	NAME = \"input_type\" 		VALUE = " + input_type 		+ " >");
		
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"initial_time\" 	VALUE = " + initial_time 	+ " >");
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"time_step\" 		VALUE = " + time_step 		+ "	>");
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"final_time\" 		VALUE = " + final_time 		+ "	>");

		System.out.println("<input TYPE=\"hidden\" 	NAME = \"start_time\"		VALUE = " + start_time 		+ "	>");
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"ramp_velocity\" 	VALUE = " + ramp_velocity 	+ "	>");
		
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"start_time_sin\"	VALUE = " + start_time_sin 	+ "	>");
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"end_time_sin\" 	VALUE = " + end_time_sin	+ "	>");
		
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"start_time_triang\"	VALUE = " + start_time_triang 	+ "	>");
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"end_time_triang\" 		VALUE = " + end_time_triang		+ "	>");
		
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"constant_value\" 	VALUE = " + constant_value 	+ "	>");
		
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"initial_value\" 	VALUE = " + initial_value 	+ "	>");
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"final_value\" 		VALUE = " + final_value 	+ "	>");
		
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"amplitude\" 		VALUE = " + amplitude 		+ "	>");
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"frequency\" 		VALUE = " + frequency 		+ "	>");
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"phase\" 			VALUE = " + phase	 		+ "	>");
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"bias\" 			VALUE = " + bias	 		+ "	>");
		
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"gamma_static\" 	VALUE = " + gamma_static 	+ "	>");
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"gamma_dynamic\" 	VALUE = " + gamma_dynamic 	+ "	>");
		
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"stddev\" 			VALUE = " + stddev	 		+ "	>");
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"LPcutoff\" 		VALUE = " + LPcutoff 		+ "	>");
		
		System.out.println("<input TYPE=\"hidden\" 	NAME = \"isRandom\" 		VALUE = " + isRandom 		+ "	>");
	}
	
	protected void showInputOptions(String input_type){
		
		if(input_type.equals("constant")){
			out.println("<OPTION>	constant		</OPTION>");
			out.println("<OPTION>	ramp-and-hold	</OPTION>");
			out.println("<OPTION>	sinusoidal		</OPTION>");
			out.println("<OPTION>	triangular		</OPTION>");
		}
		else if(input_type.equals("ramp-and-hold")){
			out.println("<OPTION>	ramp-and-hold	</OPTION>");
			out.println("<OPTION>	constant		</OPTION>");
			out.println("<OPTION>	sinusoidal		</OPTION>");
			out.println("<OPTION>	triangular		</OPTION>");
		}
		else if(input_type.equals("sinusoidal")){
			out.println("<OPTION>	sinusoidal		</OPTION>");
			out.println("<OPTION>	constant		</OPTION>");
			out.println("<OPTION>	ramp-and-hold	</OPTION>");
			out.println("<OPTION>	triangular		</OPTION>");
		}
		else if(input_type.equals("triangular")){
			out.println("<OPTION>	triangular		</OPTION>");
			out.println("<OPTION>	constant		</OPTION>");
			out.println("<OPTION>	ramp-and-hold	</OPTION>");
			out.println("<OPTION>	sinusoidal		</OPTION>");
		}
		
		
	}
	
	
	
}