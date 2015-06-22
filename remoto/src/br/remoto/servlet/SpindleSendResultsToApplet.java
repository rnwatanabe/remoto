/* -------------------------------
 * ServletDemo2ChartGenerator.java
 * -------------------------------
 * (C) Copyright 2002-2004, by Object Refinery Limited.
 *
 */

package br.remoto.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.remoto.spindle_simulator.Spindle;

public class SpindleSendResultsToApplet extends SpindleSuper {
	
	Spindle spindle 		= (Spindle)SpindleSimulation.spindle_simulations.get( 13 );
	
	int i = 0;
	double data = 13;
	double received = 0;
	double[] dataVecIa = spindle.getIaVector();
	double[] dataVecTime = spindle.getTimeVector();
	double[] dataConcat = new double[2]; 
	
	int VecSize = dataVecTime.length;
	
	ObjectOutputStream outputToApplet;
	ObjectInputStream inputFromApplet;
	
	public SpindleSendResultsToApplet() {

	}
	
	public void getData(HttpServletRequest request, double data){
		
		try {
			
			inputFromApplet = new ObjectInputStream(request.getInputStream());
			
			System.out.println("Sending data to applet");
			
			received = (double)inputFromApplet.readDouble();
			
			inputFromApplet.close();
			
			System.out.println("Data transmission complete");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void sendData(HttpServletResponse response){
				
		try {
			outputToApplet = new ObjectOutputStream(response.getOutputStream());
			
			//System.out.println("Sending data to applet");
			
			//outputToApplet.writeDouble(data);
			
			outputToApplet.writeObject(dataConcat);
			
			outputToApplet.flush();
			
			outputToApplet.close();
			
			//System.out.println("Data transmission complete");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		
		try {
			
			if (i < VecSize){
				dataConcat[0] = dataVecTime[i];
				dataConcat[1] = dataVecIa[i++];
				//data = dataVecIa[i++];
				//System.out.println("dataConcat[0]:" + dataConcat[0]);
			}

			//getData(request, received);
			//sendData(response, dataVec);
			sendData(response);
			//System.out.println("Data:" + data);
			//data++;
		}
		catch (Exception e) {
			System.err.println(e.toString());
		}
	}
}