
package br.remoto.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;
import br.remoto.model.Simulation;

public class ProgressBarServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	ObjectOutputStream outputToApplet;
	ObjectInputStream inputFromApplet;
	
	protected Configuration conf;
	
	String cdSimulation;
	String status;
	
	int percentage=0;
	
    public ProgressBarServlet(){}
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		try {
						
			inputFromApplet = new ObjectInputStream(request.getInputStream());
			cdSimulation = (String)inputFromApplet.readUTF();
			inputFromApplet.close();
			
			//System.out.println("cdSimulation: " + cdSimulation);
			
			if(cdSimulation!=null && !cdSimulation.equals("vazio")){
				Simulation sim = (Simulation)ReMoto.simulations.get( cdSimulation );
				
				Configuration conf = sim.getConfiguration();
				
				if(sim.getStatus() == Simulation.SIM_RUNNING){
						percentage = sim.getPercentageInt();
				}
				
				outputToApplet = new ObjectOutputStream(response.getOutputStream());

				outputToApplet.writeInt(percentage);
				outputToApplet.writeInt(sim.getStatus());
				outputToApplet.flush();
				
				outputToApplet.close();
			}
			
		}
		catch (Exception e) {
			System.err.println(e.toString());
		}
    }
   
}
