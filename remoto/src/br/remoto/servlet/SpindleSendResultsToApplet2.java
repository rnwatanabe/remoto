

/**********************************
   	NÃO SENDO UTILIZADO
***********************************/





package br.remoto.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.remoto.spindle_simulator.Input;
import br.remoto.spindle_simulator.Spindle;


/**********************************
	NÃO SENDO UTILIZADO
***********************************/


public class SpindleSendResultsToApplet2 extends SpindleSuper {
	
	Spindle spindle = (Spindle)SpindleSimulation.spindle_simulations.get( 13 );
	
	double data = 13;
	double received = 0;
	
	double[][] dataConcat = new double[10][]; 
	
	ObjectOutputStream outputToApplet;
	ObjectInputStream inputFromApplet;
	
	public SpindleSendResultsToApplet2() {}
	
	public void getData(HttpServletRequest request, double data){
		
		try {
			
			inputFromApplet = new ObjectInputStream(request.getInputStream());
			
			//System.out.println("Sending data to applet");
			
			received = (double)inputFromApplet.readDouble();
			
			inputFromApplet.close();
			
			//System.out.println("Data transmission complete");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void sendData(HttpServletResponse response){
				
		try {
			
			outputToApplet = new ObjectOutputStream(response.getOutputStream());
			
			Input input = null;
	    	
	    	input= (Input) SpindleSimulation.inputs.get( 13 );
			
	    	input.SimulateInput();
	    	
			
			spindle = (Spindle)SpindleSimulation.spindle_simulations.get( 13 );
						
			dataConcat[0] = spindle.getTimeVector();
			dataConcat[1] = input.getStretchVector();
			dataConcat[2] = spindle.getIaVector();
			dataConcat[3] = spindle.getIIVector();
			dataConcat[4] = spindle.getTension_bag1Vector();
			dataConcat[5] = spindle.getTension_bag2Vector();
			dataConcat[6] = spindle.getTension_chainVector();
			dataConcat[7] = spindle.getF_dynamic_bag1Vector();
			dataConcat[8] = spindle.getF_static_bag2Vector();
			dataConcat[9] = spindle.getF_static_chainVector();
			
			outputToApplet.writeObject(dataConcat);		
			outputToApplet.flush();
			outputToApplet.close();
			
			//System.out.println("Data transmission complete2");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		try {
			sendData(response);
		}
		catch (Exception e) {
			System.err.println(e.toString());
		}
	}
}