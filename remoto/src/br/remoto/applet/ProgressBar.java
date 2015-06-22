package br.remoto.applet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;


public class ProgressBar extends JApplet {
		
	int i = 0;
	
	URL servlet = null;
	URL finishURL = null;
	
	URLConnection servletConnection = null;
	ObjectInputStream inputFromServlet = null;
	ObjectOutputStream outputToServlet = null;
	
	String cdSimulation=null;
	String status;
	int receivedSimStatus = 0;
	
	int percentage=0;

	private JProgressBar progressBar;
    
    public ProgressBar() {}

	public void init(){
		
		cdSimulation = getParameter("cdSimulation");
		status = getParameter("status");
		
		progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        
        Color bg = new Color(219,235,238);
        Color bg2 = new Color(245,249,250);
        Color fg = new Color(65,128,141);
        
        Font sansB = new Font("SansSerif", Font.BOLD, 20);
        
        progressBar.setBackground(bg);
        progressBar.setForeground(fg);
        
        progressBar.setPreferredSize(new Dimension(300,40));
        
        progressBar.setFont(sansB);
        
        progressBar.setStringPainted(true); 
        
        if(status.equals("starting"))progressBar.setIndeterminate(true);
        else  progressBar.setIndeterminate(false);
        
        JPanel panel = new JPanel();
        panel.add(progressBar);
        
        panel.setBackground(bg2);
        
		getContentPane().add(panel);
		
		new ProgressBar.DataGenerator().start();
	}
	
	class DataGenerator extends Timer implements ActionListener {
		
		DataGenerator() {
			super(300, null);
			addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent event) {
						
			try
			{
				communicateWithServlet(receivedSimStatus);
			} 
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (receivedSimStatus == 2){
				
				String url = getCodeBase() + "/results.do?opt=chart_img";
				
		        try {
					finishURL = new URL(url);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				getAppletContext().showDocument(finishURL);
			}
			
		}
	}
	
	
	private void communicateWithServlet(int statusSim) throws ClassNotFoundException, IOException {
		
		String url = getCodeBase() + "/servlet/ProgressBarServlet";
		
		servlet = new URL(url);
		
		servletConnection = servlet.openConnection();
		
		// inform the connection that we will accept input
		servletConnection.setDoInput(true);
		servletConnection.setDoOutput(true);
		
		// Don't use a cached version of URL connection.
		servletConnection.setUseCaches (false);
		servletConnection.setDefaultUseCaches (false);
		
		// Specify the content type that we will send binary data
		servletConnection.setRequestProperty("Content-Type", "application/octet-stream");
		
		outputToServlet = new ObjectOutputStream(servletConnection.getOutputStream());
		
		outputToServlet.writeUTF(cdSimulation);
		outputToServlet.flush();
		outputToServlet.close();
		
		inputFromServlet 			= new ObjectInputStream(servletConnection.getInputStream());
		percentage 					= (int) inputFromServlet.readInt();
		receivedSimStatus 			= (int) inputFromServlet.readInt();
		
		
		if(status.equals("starting"))progressBar.setValue(0);
        else  progressBar.setValue(percentage);
		
		inputFromServlet.close();
	}
	
}