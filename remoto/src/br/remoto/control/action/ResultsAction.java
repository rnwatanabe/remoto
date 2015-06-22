
package br.remoto.control.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.remoto.control.form.ResultsForm;
import br.remoto.model.Configuration;
import br.remoto.model.ReMoto;
import br.remoto.model.ResultDisplay;
import br.remoto.model.Simulation;
import br.remoto.model.Neuron.Interneuron;
import br.remoto.model.Neuron.Motoneuron;
import br.remoto.model.Neuron.NeuralTract;
import br.remoto.model.Neuron.Neuron;
import br.remoto.model.Neuron.SensoryFiber;
import br.remoto.model.Neuron.SpinalNeuron;
import br.remoto.model.vo.CdNeuronVO;
import br.remoto.model.vo.CollectionVO;
import br.remoto.model.vo.ConductanceVO;
import br.remoto.model.vo.GraphicVO;
import br.remoto.model.vo.MuscleVO;
import br.remoto.model.vo.ResultVO;
import br.remoto.model.vo.SubplotVO;
import br.remoto.util.Coordenate;
import br.remoto.util.FFT;



public class ResultsAction extends MainAction
{
	/**
	 * Busca a lista de eventos de um determinado dia na base de dados.
	 * Se n�o houver eventos para a data especificada, retorna um formul�rio em branco.
	 */
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		ActionForward af = null;
		ResultsForm resultsForm = (ResultsForm) form;
		Configuration conf = getConfiguration(req);
		String opt = resultsForm.getOpt();
		String action = (String)req.getParameter("action");
		String task = resultsForm.getTask();
		
		// task is the type of data stored (i.e. spike times, firing rate..)
		// opt is the operation to be performed (i.e generate graphic, export data..)
		
		try
		{
			if( conf == null )
			{
				af = map.findForward( "noConfiguration" );
				req.setAttribute("msg", "No configuration set.");
				
				return af;
			}

			Simulation sim = getSimulation(req, conf.getId());
			
			if( sim == null || sim.getStatus() == Simulation.SIM_IDLE )
			{
				// Try to recover simulation, if it is stored in disk
				sim = recoverStoredSimulation( req, conf.getId() );

				if( sim == null || sim.getStatus() == Simulation.SIM_IDLE )
				{
					af = map.findForward( "noConfiguration" );
					req.setAttribute("msg", "You must perform a simulation in order to see results.");
					
					return af;
				}
			}
			
			
			req.getSession().setAttribute("start", sim.getStart());
			req.getSession().setAttribute("finish", sim.getFinish());
			req.getSession().setAttribute("duration",  String.valueOf( sim.getConfiguration().getTFin() ) );
			req.getSession().setAttribute("step",  String.valueOf( sim.getConfiguration().getStep() ) );
			
			if( "change".equals( action ) )
			{
				
				setFormProperties(resultsForm, sim, conf, req);
				setConfProperties(conf, resultsForm);
				req.getSession().setAttribute("task", task);
				req.getSession().setAttribute("opt", opt);
				
				af = map.findForward( "results" );
			}
			else if( ReMoto.file.equals( opt ))
			{
				
				ResultDisplay results = new ResultDisplay(conf);
				
				setConfProperties(conf, resultsForm);
				req.getSession().setAttribute("task", task);
				
				res.setContentType("text/plain");

				// Do not store cache
				res.setHeader("Content-disposition", "inline; filename=result.txt");
				res.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
				res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
				res.setHeader("Cache-Control", "post-check=0, pre-check=0");
				res.setHeader("Pragma", "no-cache");
				
				results.generateResults(sim, res.getOutputStream() );
				
				res.getOutputStream().flush();
				
				results = null;
				System.gc();
				
				return null;
			}
			else if( ReMoto.chart.equals( opt )) 
			{
				
				setConfProperties(conf, resultsForm);
				req.getSession().setAttribute("task", task);

				String cdSimulation = getCdSimulation(req, conf.getId());
				req.setAttribute( "cdSimulation", cdSimulation );
				req.getSession().setAttribute("opt", opt);
				

				// Chart will be drawn in ServletChartGenerator
				af = map.findForward( "chart" );
			}
			else if( ReMoto.chart_img.equals( opt )) 
			{
				setConfProperties(conf, resultsForm);
				
				String cdSimulation = getCdSimulation(req, conf.getId());
				req.setAttribute( "cdSimulation", cdSimulation );
				req.getSession().setAttribute("opt", opt);

				setFormProperties(resultsForm, sim, conf, req);
				
				af = map.findForward( "results" );
			}
			else if( ReMoto.chart_img_new.equals( opt )) 
			{
				setConfProperties(conf, resultsForm);
				//req.getSession().setAttribute("task", task);
				
				String cdSimulation = getCdSimulation(req, conf.getId());
				req.setAttribute( "cdSimulation", cdSimulation );
				req.getSession().setAttribute("opt", opt);
				
				setFormProperties(resultsForm, sim, conf, req);
				
				af = map.findForward( "chart_img_new" );
			}
			else if( ReMoto.summary.equals( opt ) ) 
			{
				ResultDisplay results = new ResultDisplay(conf);
				List summ = results.generateSummary(sim);
				
				results = null;
				System.gc();
				
				req.setAttribute( "summ", summ );
				req.setAttribute( "duration", Double.toString( conf.getTFin() ) );
				
				
				af = map.findForward( "summary" );
			}
			else 
			{
				
				task = (String)req.getSession().getAttribute( "task" );
				
				if( task == null || "".equals( task ) || ReMoto.force.equals( task ) )
					conf.getResult().setTask( ReMoto.force );
				
				setFormProperties(resultsForm, sim, conf, req);
				
				af = map.findForward( "results" );
			}
		}
		catch (Exception e) 
		{
			System.out.println( e.getMessage() );
		}
		
		return af;
	}
	
	
	@SuppressWarnings("unchecked")
	private void setConfProperties(Configuration conf, ResultsForm resultsForm)
	{
		
		String plot = resultsForm.getCdGraphicType();
		if(plot == null) plot = "muscleForce";
		resultsForm.setTask(plot);
		resultsForm.setCdGraphicType(plot);
		
		System.out.println("graphicType: " + resultsForm.getCdGraphicType());
		//System.out.println("cdSpecificationA: " + resultsForm.getCdSpecification());
		//resultsForm.setCdSpecification("atTerminal");
		
		if(conf.getCdType() != null){
			if(!conf.getCdType().equals("biomechanical") &&
					!conf.getCdType().equals("neural")){
				resultsForm.setNumSubplots(1);
			}
		}
		
		
		
		if(resultsForm.getCdType() == null){
			
			resultsForm.setCdType("biomecanical");
			
			resultsForm.setWithEMGfiltering(true);
			resultsForm.setWithEMGnoise(true);
			resultsForm.setWithEMGattenuation(true);
		}
		if(resultsForm.getCdNeuralType() == null) 		resultsForm.setCdNeuralType("mns");
		if(resultsForm.getCdNeuron() == null) 			resultsForm.setCdNeuron("All MUs");
		if(resultsForm.getCdSpecification() == null || resultsForm.getCdSpecification().equals("")){
			if(resultsForm.getCdGraphicType().equals("conductance")){
				resultsForm.setCdSpecification("gNa");
			}
			else if(resultsForm.getCdGraphicType().equals("spikeTimes")){
				resultsForm.setCdSpecification("atTerminal");
			}
			else
				resultsForm.setCdSpecification("---");
		}
		if(resultsForm.getCdMuscle() == null) 			resultsForm.setCdMuscle("SOL");
		if(resultsForm.getCdSubplot() == null) 			resultsForm.setCdSubplot("1");
		if(resultsForm.getCdAnalysis() == null) 		resultsForm.setCdAnalysis("parameters");
		
		
		if(	conf.getCdType() 						!= null &&
			conf.getResult().getCdMuscle() 			!= null &&
			conf.getCdNeuralType() 					!= null &&
			conf.getCdNeuron() 						!= null &&
			conf.getResult().getCdSpecification() 	!= null &&
			conf.getPlot() 							!= null &&
			conf.getResult().getTitleLabel()		!= null &&
			conf.getResult().getxLabel()			!= null &&
			conf.getResult().getyLabel()			!= null &&
			conf.getResult().getLegendLabel()		!= null
			){
			
			
			//System.out.println("conf.getResult().getCdSpecification(): " + conf.getResult().getCdSpecification());
			//System.out.println("resultsForm.getCdSpecification(): " + resultsForm.getCdSpecification());
			
			
			
			if(	conf.getCdType().equals(						resultsForm.getCdType()) &&
				conf.getResult().getCdMuscle().equals(			resultsForm.getCdMuscle()) &&
				conf.getCdNeuralType().equals(					resultsForm.getCdNeuralType()) &&
				conf.getCdNeuron().equals(						resultsForm.getCdNeuron()) &&
				conf.getResult().getCdSpecification().equals(	resultsForm.getCdSpecification()) &&
				conf.getPlot().equals(							plot)){
				
				if(	!conf.getResult().getTitleLabel().equals(resultsForm.getTitleLabel()) ||
					!conf.getResult().getxLabel().equals(resultsForm.getxLabel()) ||
					!conf.getResult().getyLabel().equals(resultsForm.getyLabel()) ||
					!conf.getResult().getLegendLabel().equals(resultsForm.getLegendLabel())){
					
					conf.getResult().setTitleLabel(resultsForm.getTitleLabel());
					conf.getResult().setxLabel(resultsForm.getxLabel());
					conf.getResult().setyLabel(resultsForm.getyLabel());
					conf.getResult().setLegendLabel(resultsForm.getLegendLabel());
				}
				
				
			}
			else{
				
				String cdNeuron = resultsForm.getCdNeuron();
				String cdMuscle = resultsForm.getCdMuscle();
				String cdSpecification = resultsForm.getCdSpecification();
				String cdJoint = conf.getCdJoint();
					
				//System.out.println("cdSpecificationB: " + resultsForm.getCdSpecification());
				
				if(plot.equals("jointAngle")){
					
					resultsForm.setTitleLabel("Joint angle");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Angle [deg]");
					resultsForm.setLegendLabel(cdJoint);
				}
				else if(plot.equals("jointVelocity")){
					
					resultsForm.setTitleLabel("Angular Velocity");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Angular Velocity [deg/s]");
					resultsForm.setLegendLabel(cdJoint);
				}
				else if(plot.equals("jointCenterMass")){
					
					resultsForm.setTitleLabel("Center of Mass");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("CoM [mm]");
					resultsForm.setLegendLabel(cdJoint);
				}
				else if(plot.equals("jointDisturbance")){
					
					resultsForm.setTitleLabel("Disturbance");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Torque [N.m]");
					resultsForm.setLegendLabel(cdJoint);
				}
				else if(plot.equals("jointCenterPressure")){
					
					resultsForm.setTitleLabel("Center of Pressure");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("CoP [mm]");
					resultsForm.setLegendLabel(cdJoint);
				}
				else if(plot.equals("muscleLength")){
					
					resultsForm.setTitleLabel("Muscle length");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Length [m]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("muscleVelocity")){
					
					resultsForm.setTitleLabel("Muscle velocity");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Velocity [m/s]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("muscleAcceleration")){
					
					resultsForm.setTitleLabel("Muscle acceleration");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Acceleration [m/s^2]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("activationNormSType")){
					
					resultsForm.setTitleLabel("Muscle activation - S-type");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Activation [normalized]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("activationNormFType")){
					
					resultsForm.setTitleLabel("Muscle activation -F-type");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Activation [normalized]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("activationNorm")){
					
					resultsForm.setTitleLabel("Total muscle activation");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Activation [normalized]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("twitchPeak")){
					
					resultsForm.setTitleLabel("Motor-unit twitch peak");
					resultsForm.setxLabel("MU Index");
					resultsForm.setyLabel("Peak amplitude [N]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("contractionTime")){
					
					resultsForm.setTitleLabel("Motor-unit contraction time");
					resultsForm.setxLabel("MU Index");
					resultsForm.setyLabel("Contraction time [ms]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("halfRelaxation")){
					
					resultsForm.setTitleLabel("Motor-unit half-relaxation time");
					resultsForm.setxLabel("MU Index");
					resultsForm.setyLabel("Half-relaxation time [ms]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("amplitudeMUAP")){
					
					resultsForm.setTitleLabel("Amplitude of motor-unit action potential");
					resultsForm.setxLabel("MU Index");
					resultsForm.setyLabel("MUAP amplitude [mV]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals(ReMoto.muscleLengthNorm)){
					
					resultsForm.setTitleLabel("Muscle length");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Length [normalized]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("pennationAngle")){
					
					resultsForm.setTitleLabel("Pennation angle");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Pennation angle [deg]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals(ReMoto.forceParallelElement)){
					
					resultsForm.setTitleLabel("Muscle force (parallel element)");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Force [normalized]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals(ReMoto.forceViscousElement)){
					
					resultsForm.setTitleLabel("Muscle force (viscous element)");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Force [normalized]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals(ReMoto.forceActiveSType)){
					
					resultsForm.setTitleLabel("Muscle force (active S-type)");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Force [normalized]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals(ReMoto.forceActiveFType)){
					
					resultsForm.setTitleLabel("Muscle force (active F-type)");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Force [normalized]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("muscleForce")){
					
					resultsForm.setTitleLabel("Muscle force");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Force [N]");
					resultsForm.setLegendLabel(cdMuscle + " - " + cdNeuron);
				}
				else if(plot.equals("tendonLength")){
	
					resultsForm.setTitleLabel("Tendon length");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Length [m]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("tendonForce")){
					
					resultsForm.setTitleLabel("Tendon force");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Force [N]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("normalizedForce")){
					
					resultsForm.setTitleLabel("Normalized musculotendon force");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Force [% MVC]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("EMG")){
					
					resultsForm.setTitleLabel("EMG");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("EMG [mV]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("meanFiringRate")){
					
					resultsForm.setTitleLabel("Mean Firing Rate - " + cdNeuron);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel(cdNeuron + " [Hz]");
					resultsForm.setLegendLabel("Mean Firing Rate " + cdNeuron);
				}
				else if(plot.equals("spikeTimes")){
					
					resultsForm.setTitleLabel("Spike times - " + cdNeuron + " - " + cdSpecification);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Index");
					resultsForm.setLegendLabel("Spikes - " + cdNeuron);
				}
				else if(plot.equals("firingRate")){
					
					resultsForm.setTitleLabel("Instantaneous firing rate - " + cdNeuron);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("IFR [Hz]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("ISI")){
					
					resultsForm.setTitleLabel("Interspike interval (ISI) - " + cdNeuron);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Interspike interval (ISI) [ms]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("stimulusRate")){
					
					resultsForm.setTitleLabel("Normalized stimulus rate (CT/ISI) - " + cdNeuron);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Stimulus rate");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("stimulusRateVsForce")){
					
					resultsForm.setTitleLabel("Stimulus rate VS Force - " + cdNeuron);
					resultsForm.setxLabel("Stimulus Rate [normalized]");
					resultsForm.setyLabel("Force [N]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("histogram")){
					
					resultsForm.setTitleLabel("Histogram - " + cdNeuron);
					resultsForm.setxLabel("Mean ISI [ms]");
					resultsForm.setyLabel("Number of elements in each bin");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("somaPotential")){
					
					resultsForm.setTitleLabel("Soma potential - " + cdNeuron);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Membrane potential [mV]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("dendritePotential")){
					
					resultsForm.setTitleLabel("Dendrite potential - " + cdNeuron);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Membrane potential [mV]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("conductance")){
					
					resultsForm.setTitleLabel("Conductance - " + cdSpecification);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Conductance [nS]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("injectedCurrent")){
					
					resultsForm.setTitleLabel("Injected Current");
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Current [nA]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("recruitmentThresholds")){
					
					resultsForm.setTitleLabel("Thresholds - " + cdNeuron);
					resultsForm.setxLabel("Index");
					
					if(cdNeuron.equals("All MNs")){
						resultsForm.setyLabel("Threshold [mV]");
					}
					else{
						resultsForm.setyLabel("Threshold");
					}
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("rheobase")){
					
					resultsForm.setTitleLabel("Rheobase - " + cdNeuron);
					resultsForm.setxLabel("Index");
					resultsForm.setyLabel("Rheobase [nA]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("thresholdVsInputConductance")){
					
					resultsForm.setTitleLabel("Threshold VS Input Conductance - " + cdNeuron);
					resultsForm.setxLabel("Input Conductance [uS]");
					resultsForm.setyLabel("Threshold [mV]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("axonConductionVelocity")){
					
					resultsForm.setTitleLabel("Axon Conduction Velocity - " + cdNeuron);
					resultsForm.setxLabel("Index");
					resultsForm.setyLabel("Velocity [m/s]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("MUsPosition")){
					
					resultsForm.setTitleLabel("MU position within muscle cross-section - " + cdNeuron);
					resultsForm.setxLabel("Width [mm]");
					resultsForm.setyLabel("Depth [mm]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("somaticInputResistance")){
					
					resultsForm.setTitleLabel("Somatic Input Resistance - " + cdNeuron);
					resultsForm.setxLabel("Index");
					resultsForm.setyLabel("Resistance [Mohm]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("axonThresholds")){
					
					resultsForm.setTitleLabel("Axon Threshold - " + cdNeuron);
					resultsForm.setxLabel("Index");
					resultsForm.setyLabel("Threshold [mA]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("neuronPositions")){
					
					resultsForm.setTitleLabel("Neuron position - " + cdNeuron);
					resultsForm.setxLabel("Index");
					resultsForm.setyLabel("Position (caudal-rostral)");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("VsxVdot")){
					
					resultsForm.setTitleLabel("VsxVdot - " + cdNeuron);
					resultsForm.setxLabel("V [mV]");
					resultsForm.setyLabel("dV/dt [mV/s]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("meanISI")){
					
					resultsForm.setTitleLabel("ISI mean value - " + cdNeuron);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("ISI [ms]");
					resultsForm.setLegendLabel(cdNeuron);
				}
				else if(plot.equals("jointTorque")){
					
					resultsForm.setTitleLabel("Joint torque - " + cdJoint);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Torque [Nm]");
					resultsForm.setLegendLabel(cdJoint);
				}
				else if(plot.equals("jointMuscleTorque")){
					
					resultsForm.setTitleLabel("Joint muscle torque - " + cdJoint);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Torque [Nm]");
					resultsForm.setLegendLabel(cdJoint);
				}
				else if(plot.equals("jointGravTorque")){
					
					resultsForm.setTitleLabel("Gravitational torque - " + cdJoint);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Torque [Nm]");
					resultsForm.setLegendLabel(cdJoint);
				}
				else if(plot.equals("jointPassiveTorque")){
					
					resultsForm.setTitleLabel("Passive joint torque - " + cdJoint);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Torque [Nm]");
					resultsForm.setLegendLabel(cdJoint);
				}
				else if(plot.equals("jointAngleVsJointTorque")){
					
					resultsForm.setTitleLabel("Joint impedance - " + cdJoint);
					resultsForm.setxLabel("Angle [deg]");
					resultsForm.setyLabel("Torque [Nm]");
					resultsForm.setLegendLabel(cdJoint);
				}
				else if(plot.equals("jointAngleVsVelocity")){
					
					resultsForm.setTitleLabel("Phase portrait - " + cdJoint);
					resultsForm.setxLabel("Angle [deg]");
					resultsForm.setyLabel("Angular Velocity [deg/s]");
					resultsForm.setLegendLabel(cdJoint);
				}
				else if(plot.equals("musculotendonLength")){
					
					resultsForm.setTitleLabel("Musculotendon Length - " + cdMuscle);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Length [m]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("musculotendonMementArm")){
					
					resultsForm.setTitleLabel("Moment Arm - " + cdMuscle);
					resultsForm.setxLabel("Time [ms]");
					resultsForm.setyLabel("Moment arm [m]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("MTLengthVSTendonForce")){
									
					resultsForm.setTitleLabel("MTLengthVSTendonForce - " + cdMuscle);
					resultsForm.setxLabel("MT Length [m]");
					resultsForm.setyLabel("TendonForce [N]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("tendonLengthVSTendonForce")){
					
					resultsForm.setTitleLabel("TendonLengthVSTendonForce - " + cdMuscle);
					resultsForm.setxLabel("Tendon Length [m]");
					resultsForm.setyLabel("TendonForce [N]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				else if(plot.equals("muscleLengthVSTendonForce")){
					
					resultsForm.setTitleLabel("MuscleLengthVSTendonForce - " + cdMuscle);
					resultsForm.setxLabel("Muscle Length [m]");
					resultsForm.setyLabel("TendonForce [N]");
					resultsForm.setLegendLabel(cdMuscle);
				}
				
				if(conf.getResult().getCdAnalysis().equals("fft")){
					
					resultsForm.setTitleLabel("FFT");
					resultsForm.setxLabel("Frequency [Hz]");
					resultsForm.setyLabel("Absolute value of the amplitude");
					resultsForm.setLegendLabel(plot);
				}
			}
		}
		
		
		
		
		//String cdNeuron = resultsForm.getCdNeuron();
		
		if(resultsForm.getTitleLabel() == null)resultsForm.setTitleLabel("Muscle force");
		if(resultsForm.getxLabel() == null) resultsForm.setxLabel("Time [ms]");
		if(resultsForm.getyLabel() == null) resultsForm.setyLabel("Force [N]");
		if(resultsForm.getLegendLabel() == null) resultsForm.setLegendLabel("SOL");
		
		
		
		
		
		if(resultsForm.getNumSubplots() == 0) resultsForm.setNumSubplots(1);
		if(resultsForm.getChartHeigth() == 0 || resultsForm.getChartHeigth() > 1000) resultsForm.setChartHeigth(400);
		if(resultsForm.getChartWidth() == 0|| resultsForm.getChartWidth() > 500) resultsForm.setChartWidth(500);
		
		
		
		if(resultsForm.getTitleSize() == 0) resultsForm.setTitleSize(20);
		if(resultsForm.getTitleSize() > 50) resultsForm.setTitleSize(50);
		if(resultsForm.getxLabelFontSize() == 0) resultsForm.setxLabelFontSize(19);
		if(resultsForm.getxLabelFontSize() > 50) resultsForm.setxLabelFontSize(50);
		if(resultsForm.getyLabelFontSize() == 0) resultsForm.setyLabelFontSize(15);
		if(resultsForm.getyLabelFontSize() > 50) resultsForm.setyLabelFontSize(50);
		if(resultsForm.getxLabelNumberSize() == 0) resultsForm.setxLabelNumberSize(15);
		if(resultsForm.getxLabelNumberSize() > 50) resultsForm.setxLabelNumberSize(50);
		if(resultsForm.getyLabelNumberSize() == 0) resultsForm.setyLabelNumberSize(15);
		if(resultsForm.getyLabelNumberSize() > 50) resultsForm.setyLabelNumberSize(50);
		if(resultsForm.getLegendFontSize() == 0) resultsForm.setLegendFontSize(17);
		if(resultsForm.getLegendFontSize() > 50) resultsForm.setLegendFontSize(50);
		//if(resultsForm.isDarker() == false) resultsForm.setDarker(true);
		if(resultsForm.getGraphColor() == null) resultsForm.setGraphColor("red");
		
		//resultsForm.setAdvancedSettings(true);
		
		if(resultsForm.getXini() == 0) resultsForm.setXini(-99999);
		if(resultsForm.getXfin() == 0) resultsForm.setXfin(99999);
		if(resultsForm.getYini() == 0) resultsForm.setYini(-99999);
		if(resultsForm.getYfin() == 0) resultsForm.setYfin(99999);
		
		if(resultsForm.getXiniFFT() == 0) resultsForm.setXiniFFT(-99999);
		if(resultsForm.getXfinFFT() == 0) resultsForm.setXfinFFT(99999);
		if(resultsForm.getYiniFFT() == 0) resultsForm.setYiniFFT(-99999);
		if(resultsForm.getYfinFFT() == 0) resultsForm.setYfinFFT(99999);
		
		if(resultsForm.getFcLow() == 0)	resultsForm.setFcLow( conf.getResult().getFcLow() );
		if(resultsForm.getFcHigh() == 0)	resultsForm.setFcHigh( conf.getResult().getFcHigh() );
		
		
		
		
		resultsForm.setSamplingRateFFT((int) (1000 / conf.getStep()));
		
		if(resultsForm.getNumOfPointsFFT() == 0){
			resultsForm.setNumOfPointsFFT(FFT.nextPow2((int) (conf.getTFin() / conf.getStep())));
		}
		
		int numPointsFFT = FFT.nextPow2(resultsForm.getNumOfPointsFFT());
		resultsForm.setNumOfPointsFFT(numPointsFFT);
		
		
		//System.out.println("plots: " + plots + "   task: " + resultsForm.getTask());
		
		ResultVO result = new ResultVO();
		Coordenate coord = new Coordenate(); 
		Coordenate coordFFT = new Coordenate(); 
		
		coord.setXini( resultsForm.getXini() );
		coord.setXfin( resultsForm.getXfin() );
		coord.setYini( resultsForm.getYini() );
		coord.setYfin( resultsForm.getYfin() );
		
		coordFFT.setXini( resultsForm.getXiniFFT() );
		coordFFT.setXfin( resultsForm.getXfinFFT() );
		coordFFT.setYini( resultsForm.getYiniFFT() );
		coordFFT.setYfin( resultsForm.getYfinFFT() );
		
		if( resultsForm.getXini() > 0 || resultsForm.getXfin() < ReMoto.T_MAX ||
			resultsForm.getYini() > -ReMoto.Y_MAX || resultsForm.getYfin() < ReMoto.Y_MAX )
		{
			coord.setActive( true );
			coordFFT.setActive( true );
		}

		
		
		
		
		
		
		int cdSubplot = Integer.parseInt(resultsForm.getCdSubplot());
		int numSubplots = resultsForm.getNumSubplots();
		
		
		List[] nmMuscles 			= new List[numSubplots];
		List[] nmSubplots 			= new List[numSubplots];
		List[] nmCdNeurons 			= new List[numSubplots];
		List[] nmCdSpecification 	= new List[numSubplots];
		List[] yLabels 				= new List[numSubplots];
		List[] legendLabels 		= new List[numSubplots];
		
		//System.out.println("RESULTS ACTION");
		
		List[] aux1, aux2, aux3, aux4, aux5, aux6;
		
		if(conf.getNmSubplots() == null){
			
			aux1 = new List[numSubplots];
			aux2 = new List[numSubplots];
			aux3 = new List[numSubplots];
			aux4 = new List[numSubplots];
			aux5 = new List[numSubplots];
			aux6 = new List[numSubplots];
			
			for (int k = 0; k < numSubplots; k++){
				
				aux1[k] = new ArrayList();
				aux1[k].add("");
				aux2[k] = new ArrayList();
				aux2[k].add("");
				aux3[k] = new ArrayList();
				aux3[k].add("");
				aux4[k] = new ArrayList();
				aux4[k].add("");
				aux5[k] = new ArrayList();
				aux5[k].add("");
				aux6[k] = new ArrayList();
				aux6[k].add("");
			}
		}
		else if(numSubplots != conf.getNmSubplots().length){
			
			aux1 = new List[numSubplots];
			aux2 = new List[numSubplots];
			aux3 = new List[numSubplots];
			aux4 = new List[numSubplots];
			aux5 = new List[numSubplots];
			aux6 = new List[numSubplots];
			
			for (int k = 0; k < numSubplots; k++){
				
				
				if(conf.getNmSubplots().length > k){
					aux1[k] = conf.getNmSubplots()[k];
				}
				else{
					aux1[k] = new ArrayList();
					aux1[k].add("");
				}
				if(conf.getNmCdNeurons().length > k){
					aux2[k] = conf.getNmCdNeurons()[k];
				}
				else{
					aux2[k] = new ArrayList();
					aux2[k].add("");
				}
				if(conf.getNmCdSpecification().length > k){
					aux3[k] = conf.getNmCdSpecification()[k];
				}
				else{
					aux3[k] = new ArrayList();
					aux3[k].add("");
				}
				if(conf.getyLabels().length > k){
					aux4[k] = conf.getyLabels()[k];
				}
				else{
					aux4[k] = new ArrayList();
					aux4[k].add("");
				}
				if(conf.getLegendLabels().length > k){
					aux5[k] = conf.getLegendLabels()[k];
				}
				else{
					aux5[k] = new ArrayList();
					aux5[k].add("");
				}
				if(conf.getNmMuscles().length > k){
					aux6[k] = conf.getNmMuscles()[k];
				}
				else{
					aux6[k] = new ArrayList();
					aux6[k].add("");
				}
			}
		}
		else{
			aux1 = conf.getNmSubplots();
			aux2 = conf.getNmCdNeurons();
			aux3 = conf.getNmCdSpecification();
			aux4 = conf.getyLabels();
			aux5 = conf.getLegendLabels();
			aux6 = conf.getNmMuscles();
		}
		
		for (int k = 0; k < numSubplots; k++){
			
			nmSubplots[k] 			= new ArrayList();
			nmCdNeurons[k] 			= new ArrayList();
			nmCdSpecification[k] 	= new ArrayList();
			yLabels[k] 				= new ArrayList();
			legendLabels[k] 		= new ArrayList();
			nmMuscles[k] 			= new ArrayList();
			
			//System.out.println("aux1[" + k + "].size(): " + aux1[k].size());
			
			if(aux1[k].size() > 0 && 
					aux2[k].size() > 0 && 
					aux3[k].size() > 0 && 
					aux4[k].size() > 0 && 
					aux5[k].size() > 0 &&
					aux6[k].size() > 0 &&
					k != (cdSubplot - 1)){
				
				//System.out.println("k: " + k);
				
				nmSubplots[k].add(			aux1[k].get(0));
				nmCdNeurons[k].add(			aux2[k].get(0));
				nmCdSpecification[k].add(	aux3[k].get(0));
				yLabels[k].add(				aux4[k].get(0));
				legendLabels[k].add(		aux5[k].get(0));
				nmMuscles[k].add(			aux6[k].get(0));
			}
		}
		
		
		nmSubplots			[cdSubplot - 1].add(	plot);
		nmCdNeurons			[cdSubplot - 1].add(	resultsForm.getCdNeuron());
		nmCdSpecification	[cdSubplot - 1].add(	resultsForm.getCdSpecification());
		yLabels				[cdSubplot - 1].add(	resultsForm.getyLabel());
		legendLabels		[cdSubplot - 1].add(	resultsForm.getLegendLabel());
		nmMuscles			[cdSubplot - 1].add(	resultsForm.getCdMuscle());


		
		if(resultsForm.getCdType().equals("spikesMNs")){
			numSubplots = 3;
			resultsForm.setNumSubplots(numSubplots);
			
			nmSubplots 				= new List[numSubplots];
			nmCdNeurons 			= new List[numSubplots];
			nmCdSpecification 		= new List[numSubplots];
			yLabels 				= new List[numSubplots];
			legendLabels 			= new List[numSubplots];
			nmMuscles	 			= new List[numSubplots];
			
			for(int k = 0; k < numSubplots; k++){
				nmSubplots[k] 		= new ArrayList();
				nmCdNeurons[k] 		= new ArrayList();
				nmCdSpecification[k] = new ArrayList();
				yLabels[k] 			= new ArrayList();
				legendLabels[k] 	= new ArrayList();
				nmMuscles[k] 		= new ArrayList();
			}
			
			nmSubplots[0].add(			"spikeTimes");
			nmCdNeurons[0].add(			"All MNs");
			nmCdSpecification[0].add(	"atTerminal");
			yLabels[0].add(				"MNs SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
			
			nmSubplots[1].add(			"spikeTimes");
			nmCdNeurons[1].add(			"All MNs");
			nmCdSpecification[1].add(	"atTerminal");
			yLabels[1].add(				"MNs MG");
			legendLabels[1].add(		"MG");
			nmMuscles[1].add(			"MG");
			
			nmSubplots[2].add(			"spikeTimes");
			nmCdNeurons[2].add(			"All MNs");
			nmCdSpecification[2].add(	"atTerminal");
			yLabels[2].add(				"MNs LG");
			legendLabels[2].add(		"LG");
			nmMuscles[2].add(			"LG");
			
		}
		
		
		if(resultsForm.getCdType().equals("spikesIas")){
			numSubplots = 6;
			resultsForm.setNumSubplots(numSubplots);
			
			nmSubplots 				= new List[numSubplots];
			nmCdNeurons 			= new List[numSubplots];
			nmCdSpecification 		= new List[numSubplots];
			yLabels 				= new List[numSubplots];
			legendLabels 			= new List[numSubplots];
			nmMuscles	 			= new List[numSubplots];
			
			for(int k = 0; k < numSubplots; k++){
				nmSubplots[k] 		= new ArrayList();
				nmCdNeurons[k] 		= new ArrayList();
				nmCdSpecification[k] = new ArrayList();
				yLabels[k] 			= new ArrayList();
				legendLabels[k] 	= new ArrayList();
				nmMuscles[k] 		= new ArrayList();
			}
			
			
			nmSubplots[0].add(			"spikeTimes");
			nmCdNeurons[0].add(			"Ia");
			nmCdSpecification[0].add(	"atTerminal");
			yLabels[0].add(				"Ia SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
			
			nmSubplots[1].add(			"meanFiringRate");
			nmCdNeurons[1].add(			"Ia");
			nmCdSpecification[1].add(	"atTerminal");
			yLabels[1].add(				"Ia SOL");
			legendLabels[1].add(		"SOL");
			nmMuscles[1].add(			"SOL");
			
			nmSubplots[2].add(			"spikeTimes");
			nmCdNeurons[2].add(			"Ia");
			nmCdSpecification[2].add(	"atTerminal");
			yLabels[2].add(				"Ia MG");
			legendLabels[2].add(		"MG");
			nmMuscles[2].add(			"MG");
			
			nmSubplots[3].add(			"meanFiringRate");
			nmCdNeurons[3].add(			"Ia");
			nmCdSpecification[3].add(	"atTerminal");
			yLabels[3].add(				"Ia MG");
			legendLabels[3].add(		"MG");
			nmMuscles[3].add(			"MG");
			
			nmSubplots[4].add(			"spikeTimes");
			nmCdNeurons[4].add(			"Ia");
			nmCdSpecification[4].add(	"atTerminal");
			yLabels[4].add(				"Ia LG");
			legendLabels[4].add(		"LG");
			nmMuscles[4].add(			"LG");
			
			nmSubplots[5].add(			"meanFiringRate");
			nmCdNeurons[5].add(			"Ia");
			nmCdSpecification[5].add(	"atTerminal");
			yLabels[5].add(				"Ia LG");
			legendLabels[5].add(		"LG");
			nmMuscles[5].add(			"LG");
		}
		
		
		if(resultsForm.getCdType().equals("forces")){
			numSubplots = 4;
			resultsForm.setNumSubplots(numSubplots);
			
			nmSubplots 				= new List[numSubplots];
			nmCdNeurons 			= new List[numSubplots];
			nmCdSpecification 		= new List[numSubplots];
			yLabels 				= new List[numSubplots];
			legendLabels 			= new List[numSubplots];
			nmMuscles	 			= new List[numSubplots];
			
			for(int k = 0; k < numSubplots; k++){
				nmSubplots[k] 		= new ArrayList();
				nmCdNeurons[k] 		= new ArrayList();
				nmCdSpecification[k] = new ArrayList();
				yLabels[k] 			= new ArrayList();
				legendLabels[k] 	= new ArrayList();
				nmMuscles[k] 		= new ArrayList();
			}
			
			
			nmSubplots[0].add(			"muscleForce");
			nmCdNeurons[0].add(			"All MUs");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"Force SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
			
			nmSubplots[1].add(			"muscleForce");
			nmCdNeurons[1].add(			"All MUs");
			nmCdSpecification[1].add(	"");
			yLabels[1].add(				"Force MG");
			legendLabels[1].add(		"MG");
			nmMuscles[1].add(			"MG");
			
			nmSubplots[2].add(			"muscleForce");
			nmCdNeurons[2].add(			"All MUs");
			nmCdSpecification[2].add(	"");
			yLabels[2].add(				"Force LG");
			legendLabels[2].add(		"LG");
			nmMuscles[2].add(			"LG");
			
			nmSubplots[3].add(			"muscleForce");
			nmCdNeurons[3].add(			"All MUs");
			nmCdSpecification[3].add(	"");
			yLabels[3].add(				"Force TA");
			legendLabels[3].add(		"TA");
			nmMuscles[3].add(			"TA");
		}
		
		if(resultsForm.getCdType().equals("lengths")){
			numSubplots = 8;
			resultsForm.setNumSubplots(numSubplots);
			
			nmSubplots 				= new List[numSubplots];
			nmCdNeurons 			= new List[numSubplots];
			nmCdSpecification 		= new List[numSubplots];
			yLabels 				= new List[numSubplots];
			legendLabels 			= new List[numSubplots];
			nmMuscles	 			= new List[numSubplots];
			
			for(int k = 0; k < numSubplots; k++){
				nmSubplots[k] 		= new ArrayList();
				nmCdNeurons[k] 		= new ArrayList();
				nmCdSpecification[k] = new ArrayList();
				yLabels[k] 			= new ArrayList();
				legendLabels[k] 	= new ArrayList();
				nmMuscles[k] 		= new ArrayList();
			}
			
			
			nmSubplots[0].add(			"muscleLengthNorm");
			nmCdNeurons[0].add(			"");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"Muscle Length SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
			
			nmSubplots[1].add(			"tendonLength");
			nmCdNeurons[1].add(			"");
			nmCdSpecification[1].add(	"");
			yLabels[1].add(				"Tendon Length SOL");
			legendLabels[1].add(		"SOL");
			nmMuscles[1].add(			"SOL");
			
			nmSubplots[2].add(			"muscleLengthNorm");
			nmCdNeurons[2].add(			"");
			nmCdSpecification[2].add(	"");
			yLabels[2].add(				"Muscle Length MG");
			legendLabels[2].add(		"MG");
			nmMuscles[2].add(			"MG");
			
			nmSubplots[3].add(			"tendonLength");
			nmCdNeurons[3].add(			"");
			nmCdSpecification[3].add(	"");
			yLabels[3].add(				"Tendon Length MG");
			legendLabels[3].add(		"MG");
			nmMuscles[3].add(			"MG");
			
			nmSubplots[4].add(			"muscleLengthNorm");
			nmCdNeurons[4].add(			"");
			nmCdSpecification[4].add(	"");
			yLabels[4].add(				"Muscle Length LG");
			legendLabels[4].add(		"LG");
			nmMuscles[4].add(			"LG");
			
			nmSubplots[5].add(			"tendonLength");
			nmCdNeurons[5].add(			"");
			nmCdSpecification[5].add(	"");
			yLabels[5].add(				"Tendon Length LG");
			legendLabels[5].add(		"LG");
			nmMuscles[5].add(			"LG");
			
			nmSubplots[6].add(			"muscleLengthNorm");
			nmCdNeurons[6].add(			"");
			nmCdSpecification[6].add(	"");
			yLabels[6].add(				"Muscle Length TA");
			legendLabels[6].add(		"TA");
			nmMuscles[6].add(			"TA");
			
			nmSubplots[7].add(			"tendonLength");
			nmCdNeurons[7].add(			"");
			nmCdSpecification[7].add(	"");
			yLabels[7].add(				"Tendon Length TA");
			legendLabels[7].add(		"TA");
			nmMuscles[7].add(			"TA");
		}
		
		
		if(resultsForm.getCdType().equals("general")){
			numSubplots = 5;
			resultsForm.setNumSubplots(numSubplots);
			
			nmSubplots 				= new List[numSubplots];
			nmCdNeurons 			= new List[numSubplots];
			nmCdSpecification 		= new List[numSubplots];
			yLabels 				= new List[numSubplots];
			legendLabels 			= new List[numSubplots];
			nmMuscles	 			= new List[numSubplots];
			
			for(int k = 0; k < numSubplots; k++){
				nmSubplots[k] 		= new ArrayList();
				nmCdNeurons[k] 		= new ArrayList();
				nmCdSpecification[k] = new ArrayList();
				yLabels[k] 			= new ArrayList();
				legendLabels[k] 	= new ArrayList();
				nmMuscles[k] 		= new ArrayList();
			}
			
			
			nmSubplots[0].add(			"EMG");
			nmCdNeurons[0].add(			"");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"EMG SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
			
			nmSubplots[1].add(			"EMG");
			nmCdNeurons[1].add(			"");
			nmCdSpecification[1].add(	"");
			yLabels[1].add(				"EMG MG");
			legendLabels[1].add(		"MG");
			nmMuscles[1].add(			"MG");
			
			nmSubplots[2].add(			"EMG");
			nmCdNeurons[2].add(			"");
			nmCdSpecification[2].add(	"");
			yLabels[2].add(				"EMG LG");
			legendLabels[2].add(		"LG");
			nmMuscles[2].add(			"LG");
			
			nmSubplots[3].add(			"jointTorque");
			nmCdNeurons[3].add(			"");
			nmCdSpecification[3].add(	"");
			yLabels[3].add(				"Torque");
			legendLabels[3].add(		"Ankle");
			nmMuscles[3].add(			"SOL");
			
			nmSubplots[4].add(			"jointAngle");
			nmCdNeurons[4].add(			"");
			nmCdSpecification[4].add(	"");
			yLabels[4].add(				"Angle");
			legendLabels[4].add(		"Ankle");
			nmMuscles[4].add(			"SOL");
		}
		
		if(resultsForm.getCdType().equals("generalForSoleus")){
			numSubplots = 8;
			resultsForm.setNumSubplots(numSubplots);
			
			nmSubplots 				= new List[numSubplots];
			nmCdNeurons 			= new List[numSubplots];
			nmCdSpecification 		= new List[numSubplots];
			yLabels 				= new List[numSubplots];
			legendLabels 			= new List[numSubplots];
			nmMuscles	 			= new List[numSubplots];
			
			for(int k = 0; k < numSubplots; k++){
				nmSubplots[k] 		= new ArrayList();
				nmCdNeurons[k] 		= new ArrayList();
				nmCdSpecification[k] = new ArrayList();
				yLabels[k] 			= new ArrayList();
				legendLabels[k] 	= new ArrayList();
				nmMuscles[k] 		= new ArrayList();
			}
			
			nmSubplots[0].add(			"EMG");
			nmCdNeurons[0].add(			"");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"EMG SOL");
			legendLabels[0].add(		"SOL");
			nmMuscles[0].add(			"SOL");
			
			nmSubplots[1].add(			"spikeTimes");
			nmCdNeurons[1].add(			"All MNs");
			nmCdSpecification[1].add(	"atTerminal");
			yLabels[1].add(				"MNs SOL");
			legendLabels[1].add(		"SOL");
			nmMuscles[1].add(			"SOL");
			
			nmSubplots[2].add(			"spikeTimes");
			nmCdNeurons[2].add(			"Ia");
			nmCdSpecification[2].add(	"atTerminal");
			yLabels[2].add(				"Ia SOL");
			legendLabels[2].add(		"SOL");
			nmMuscles[2].add(			"SOL");
			
			nmSubplots[3].add(			"meanFiringRate");
			nmCdNeurons[3].add(			"Ia");
			nmCdSpecification[3].add(	"atTerminal");
			yLabels[3].add(				"Ia SOL");
			legendLabels[3].add(		"SOL");
			nmMuscles[3].add(			"SOL");
			
			nmSubplots[4].add(			"muscleLengthNorm");
			nmCdNeurons[4].add(			"");
			nmCdSpecification[4].add(	"");
			yLabels[4].add(				"Muscle Length SOL");
			legendLabels[4].add(		"SOL");
			nmMuscles[4].add(			"SOL");
			
			nmSubplots[5].add(			"muscleForce");
			nmCdNeurons[5].add(			"All MUs");
			nmCdSpecification[5].add(	"");
			yLabels[5].add(				"Force SOL");
			legendLabels[5].add(		"SOL");
			nmMuscles[5].add(			"SOL");
			
			nmSubplots[6].add(			"jointTorque");
			nmCdNeurons[6].add(			"");
			nmCdSpecification[6].add(	"");
			yLabels[6].add(				"Torque");
			legendLabels[6].add(		"Ankle");
			nmMuscles[6].add(			"SOL");
			
			nmSubplots[7].add(			"jointAngle");
			nmCdNeurons[7].add(			"");
			nmCdSpecification[7].add(	"");
			yLabels[7].add(				"Angle");
			legendLabels[7].add(		"Ankle");
			nmMuscles[7].add(			"SOL");
		}
		
		if(resultsForm.getCdType().equals("generalForMG")){
			numSubplots = 8;
			resultsForm.setNumSubplots(numSubplots);
			
			nmSubplots 				= new List[numSubplots];
			nmCdNeurons 			= new List[numSubplots];
			nmCdSpecification 		= new List[numSubplots];
			yLabels 				= new List[numSubplots];
			legendLabels 			= new List[numSubplots];
			nmMuscles	 			= new List[numSubplots];
			
			for(int k = 0; k < numSubplots; k++){
				nmSubplots[k] 		= new ArrayList();
				nmCdNeurons[k] 		= new ArrayList();
				nmCdSpecification[k] = new ArrayList();
				yLabels[k] 			= new ArrayList();
				legendLabels[k] 	= new ArrayList();
				nmMuscles[k] 		= new ArrayList();
			}
			
			nmSubplots[0].add(			"EMG");
			nmCdNeurons[0].add(			"");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"EMG MG");
			legendLabels[0].add(		"MG");
			nmMuscles[0].add(			"MG");
			
			nmSubplots[1].add(			"spikeTimes");
			nmCdNeurons[1].add(			"All MNs");
			nmCdSpecification[1].add(	"atTerminal");
			yLabels[1].add(				"MNs MG");
			legendLabels[1].add(		"MG");
			nmMuscles[1].add(			"MG");
			
			nmSubplots[2].add(			"spikeTimes");
			nmCdNeurons[2].add(			"Ia");
			nmCdSpecification[2].add(	"atTerminal");
			yLabels[2].add(				"Ia MG");
			legendLabels[2].add(		"MG");
			nmMuscles[2].add(			"MG");
			
			nmSubplots[3].add(			"meanFiringRate");
			nmCdNeurons[3].add(			"Ia");
			nmCdSpecification[3].add(	"atTerminal");
			yLabels[3].add(				"Ia MG");
			legendLabels[3].add(		"MG");
			nmMuscles[3].add(			"MG");
			
			nmSubplots[4].add(			"muscleLengthNorm");
			nmCdNeurons[4].add(			"");
			nmCdSpecification[4].add(	"");
			yLabels[4].add(				"Muscle Length MG");
			legendLabels[4].add(		"MG");
			nmMuscles[4].add(			"MG");
			
			nmSubplots[5].add(			"muscleForce");
			nmCdNeurons[5].add(			"All MUs");
			nmCdSpecification[5].add(	"");
			yLabels[5].add(				"Force MG");
			legendLabels[5].add(		"MG");
			nmMuscles[5].add(			"MG");
			
			nmSubplots[6].add(			"jointTorque");
			nmCdNeurons[6].add(			"");
			nmCdSpecification[6].add(	"");
			yLabels[6].add(				"Torque");
			legendLabels[6].add(		"Ankle");
			nmMuscles[6].add(			"MG");
			
			nmSubplots[7].add(			"jointAngle");
			nmCdNeurons[7].add(			"");
			nmCdSpecification[7].add(	"");
			yLabels[7].add(				"Angle");
			legendLabels[7].add(		"Ankle");
			nmMuscles[7].add(			"MG");
		}
		
		if(resultsForm.getCdType().equals("generalForLG")){
			numSubplots = 8;
			resultsForm.setNumSubplots(numSubplots);
			
			nmSubplots 				= new List[numSubplots];
			nmCdNeurons 			= new List[numSubplots];
			nmCdSpecification 		= new List[numSubplots];
			yLabels 				= new List[numSubplots];
			legendLabels 			= new List[numSubplots];
			nmMuscles	 			= new List[numSubplots];
			
			for(int k = 0; k < numSubplots; k++){
				nmSubplots[k] 		= new ArrayList();
				nmCdNeurons[k] 		= new ArrayList();
				nmCdSpecification[k] = new ArrayList();
				yLabels[k] 			= new ArrayList();
				legendLabels[k] 	= new ArrayList();
				nmMuscles[k] 		= new ArrayList();
			}
			
			nmSubplots[0].add(			"EMG");
			nmCdNeurons[0].add(			"");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"EMG LG");
			legendLabels[0].add(		"LG");
			nmMuscles[0].add(			"LG");
			
			nmSubplots[1].add(			"spikeTimes");
			nmCdNeurons[1].add(			"All MNs");
			nmCdSpecification[1].add(	"atTerminal");
			yLabels[1].add(				"MNs LG");
			legendLabels[1].add(		"LG");
			nmMuscles[1].add(			"LG");
			
			nmSubplots[2].add(			"spikeTimes");
			nmCdNeurons[2].add(			"Ia");
			nmCdSpecification[2].add(	"atTerminal");
			yLabels[2].add(				"Ia LG");
			legendLabels[2].add(		"LG");
			nmMuscles[2].add(			"LG");
			
			nmSubplots[3].add(			"meanFiringRate");
			nmCdNeurons[3].add(			"Ia");
			nmCdSpecification[3].add(	"atTerminal");
			yLabels[3].add(				"Ia LG");
			legendLabels[3].add(		"LG");
			nmMuscles[3].add(			"LG");
			
			nmSubplots[4].add(			"muscleLengthNorm");
			nmCdNeurons[4].add(			"");
			nmCdSpecification[4].add(	"");
			yLabels[4].add(				"Muscle Length LG");
			legendLabels[4].add(		"LG");
			nmMuscles[4].add(			"LG");
			
			nmSubplots[5].add(			"muscleForce");
			nmCdNeurons[5].add(			"All MUs");
			nmCdSpecification[5].add(	"");
			yLabels[5].add(				"Force LG");
			legendLabels[5].add(		"LG");
			nmMuscles[5].add(			"LG");
			
			nmSubplots[6].add(			"jointTorque");
			nmCdNeurons[6].add(			"");
			nmCdSpecification[6].add(	"");
			yLabels[6].add(				"Torque");
			legendLabels[6].add(		"Ankle");
			nmMuscles[6].add(			"LG");
			
			nmSubplots[7].add(			"jointAngle");
			nmCdNeurons[7].add(			"");
			nmCdSpecification[7].add(	"");
			yLabels[7].add(				"Angle");
			legendLabels[7].add(		"Ankle");
			nmMuscles[7].add(			"LG");
		}
		
		if(resultsForm.getCdType().equals("generalForTA")){
			numSubplots = 8;
			resultsForm.setNumSubplots(numSubplots);
			
			nmSubplots 				= new List[numSubplots];
			nmCdNeurons 			= new List[numSubplots];
			nmCdSpecification 		= new List[numSubplots];
			yLabels 				= new List[numSubplots];
			legendLabels 			= new List[numSubplots];
			nmMuscles	 			= new List[numSubplots];
			
			for(int k = 0; k < numSubplots; k++){
				nmSubplots[k] 		= new ArrayList();
				nmCdNeurons[k] 		= new ArrayList();
				nmCdSpecification[k] = new ArrayList();
				yLabels[k] 			= new ArrayList();
				legendLabels[k] 	= new ArrayList();
				nmMuscles[k] 		= new ArrayList();
			}
			
			nmSubplots[0].add(			"EMG");
			nmCdNeurons[0].add(			"");
			nmCdSpecification[0].add(	"");
			yLabels[0].add(				"EMG TA");
			legendLabels[0].add(		"TA");
			nmMuscles[0].add(			"TA");
			
			nmSubplots[1].add(			"spikeTimes");
			nmCdNeurons[1].add(			"All MNs");
			nmCdSpecification[1].add(	"atTerminal");
			yLabels[1].add(				"MNs TA");
			legendLabels[1].add(		"TA");
			nmMuscles[1].add(			"TA");
			
			nmSubplots[2].add(			"spikeTimes");
			nmCdNeurons[2].add(			"Ia");
			nmCdSpecification[2].add(	"atTerminal");
			yLabels[2].add(				"Ia TA");
			legendLabels[2].add(		"TA");
			nmMuscles[2].add(			"TA");
			
			nmSubplots[3].add(			"meanFiringRate");
			nmCdNeurons[3].add(			"Ia");
			nmCdSpecification[3].add(	"atTerminal");
			yLabels[3].add(				"Ia TA");
			legendLabels[3].add(		"TA");
			nmMuscles[3].add(			"TA");
			
			nmSubplots[4].add(			"muscleLengthNorm");
			nmCdNeurons[4].add(			"");
			nmCdSpecification[4].add(	"");
			yLabels[4].add(				"Muscle Length TA");
			legendLabels[4].add(		"TA");
			nmMuscles[4].add(			"TA");
			
			nmSubplots[5].add(			"muscleForce");
			nmCdNeurons[5].add(			"All MUs");
			nmCdSpecification[5].add(	"");
			yLabels[5].add(				"Force TA");
			legendLabels[5].add(		"TA");
			nmMuscles[5].add(			"TA");
			
			nmSubplots[6].add(			"jointTorque");
			nmCdNeurons[6].add(			"");
			nmCdSpecification[6].add(	"");
			yLabels[6].add(				"Torque");
			legendLabels[6].add(		"Ankle");
			nmMuscles[6].add(			"TA");
			
			nmSubplots[7].add(			"jointAngle");
			nmCdNeurons[7].add(			"");
			nmCdSpecification[7].add(	"");
			yLabels[7].add(				"Angle");
			legendLabels[7].add(		"Ankle");
			nmMuscles[7].add(			"TA");
		}
		
		
		conf.setNmSubplots(			nmSubplots);
		conf.setNmCdNeurons(		nmCdNeurons);
		conf.setNmCdSpecification(	nmCdSpecification);
		conf.setyLabels(			yLabels);
		conf.setLegendLabels(		legendLabels);
		conf.setNmMuscles(			nmMuscles);
		
		/*
		for(int k = 0; k < numSubplots; k++){
			System.out.println("aux1[" + k +"]: " + aux1[k]);
			System.out.println("nmSubplots[" + k +"]: " + nmSubplots[k]);
		}
		*/
		
		
		result.setTitleLabel(resultsForm.getTitleLabel());
		result.setxLabel(resultsForm.getxLabel());
		result.setyLabel(resultsForm.getyLabel());
		result.setLegendLabel(resultsForm.getLegendLabel());
		
		result.setCoordenate( coord );
		result.setCoordenateFFT( coordFFT );
		
		result.setOpt( resultsForm.getOpt() );
		//result.setCdNucleus( resultsForm.getCdNucleus() );
		result.setCdMuscle(resultsForm.getCdMuscle());
		conf.setCdNeuron(resultsForm.getCdNeuron());
		result.setTask( resultsForm.getTask() );
		conf.setNumOfSubplots( resultsForm.getNumSubplots() );
		
		
		
		
		conf.setPlot(plot);
		conf.setCdType(resultsForm.getCdType());
		conf.setCdNeuralType(resultsForm.getCdNeuralType());
		
		
		result.setCdSpikes( resultsForm.getCdSpikes() );
		result.setCdFiringRate( resultsForm.getCdFiringRate() );
		result.setCdHistogram( resultsForm.getCdHistogram() );
		result.setCdProperties( resultsForm.getCdProperties() );
		result.setCdEMG( resultsForm.getCdEMG() );
		result.setCdForce( resultsForm.getCdForce() );
		result.setCdSignal( resultsForm.getCdSignal() );
		result.setCdSpecification( resultsForm.getCdSpecification() );
		result.setWhichSignal( resultsForm.getWhichSignal() );
		
		result.setSample( resultsForm.getSample() );
		result.setFcLow( resultsForm.getFcLow() );
		result.setFcHigh( resultsForm.getFcHigh() );
		result.setFilter( resultsForm.getFilter() );
		result.setWithEMGfiltering( resultsForm.isWithEMGfiltering() );
		result.setWithEMGnoise( resultsForm.isWithEMGnoise() );
		result.setWithEMGattenuation( resultsForm.isWithEMGattenuation() );
		
		
		
		if( resultsForm.getNumberOfBins() < 1 )
			resultsForm.setNumberOfBins( ReMoto.numberOfBins );

		result.setNumberOfBins( resultsForm.getNumberOfBins() );

		
		// Minimum frequency
		if( result.getFcLow() < 0.01 )
			result.setFcLow( 0.01 );
		
		
		result.setChartHeigth(resultsForm.getChartHeigth());
		result.setChartWidth(resultsForm.getChartWidth());
		
		result.setTitleSize(resultsForm.getTitleSize());
		result.setxLabelFontSize(resultsForm.getxLabelFontSize());
		result.setyLabelFontSize(resultsForm.getyLabelFontSize());
		result.setxLabelNumberSize(resultsForm.getxLabelNumberSize());
		result.setyLabelNumberSize(resultsForm.getyLabelNumberSize());
		result.setLegendFontSize(resultsForm.getLegendFontSize());
		result.setGraphColor(resultsForm.getGraphColor());
		result.setAdvancedSettings(resultsForm.isAdvancedSettings());
		result.setDarker(resultsForm.isDarker());
		result.setRandomColors(resultsForm.isRandomColors());
		result.setAnalysis(resultsForm.isAnalysis());
		
		
		result.setCdAnalysis(resultsForm.getCdAnalysis());
		result.setNumOfPoints(resultsForm.isNumOfPoints());
		result.setMean(resultsForm.isMean());
		result.setVariance(resultsForm.isVariance());
		result.setStd(resultsForm.isStd());
		result.setCV(resultsForm.isCV());
		result.setMin(resultsForm.isMin());
		result.setMax(resultsForm.isMax());
		
		result.setPeriodicSignal(resultsForm.isPeriodicSignal());
		
		result.setSamplingRateFFT(resultsForm.getSamplingRateFFT());
		result.setNumOfPointsFFT(resultsForm.getNumOfPointsFFT());
		
		
		result.setCdSubplot(resultsForm.getCdSubplot());
		conf.setResult( result );
		
	}
	
	
	private void setFormProperties(ResultsForm resultsForm, Simulation sim, Configuration conf, HttpServletRequest req)
	{
		
		if(resultsForm.getChartHeigth() == 0) resultsForm.setChartHeigth(500);
		if(resultsForm.getChartWidth() == 0) resultsForm.setChartWidth(400);
		
		resultsForm.setCdJointType( conf.getCdJoint() );
		if(resultsForm.getCdJointType() == null) resultsForm.setCdJointType("ankle");
		
		
		List nuclei = conf.getNuclei( ReMoto.MNs );
		nuclei.addAll( conf.getNuclei( ReMoto.INs ) );
		nuclei.addAll( conf.getNuclei( ReMoto.DTs ) );
		
		/*
		String cdNucleus = resultsForm.getCdNucleus();
		if(resultsForm.getCdNucleus() == null) resultsForm.setCdNucleus("SOL");
		cdNucleus = resultsForm.getCdNucleus();
		conf.setCdNucleus(cdNucleus);
		*/
		
		/*
		// Set default value
		if( cdNucleus == null )
		{
			
			cdNucleus = conf.getCdNucleus();
			
		}
		else{
			
			conf.setCdNucleus(resultsForm.getCdNucleus());
			req.getSession().setAttribute("cdNucleus", cdNucleus);
		}
		*/
		
		
		/*
		if( cdNucleus.equals( ReMoto.DT ) )
		{
			NeuralTract allCM_ext = new NeuralTract();
			allCM_ext.setCd( ReMoto.ALL_CM_ext );
			
			NeuralTract allCM_flex = new NeuralTract();
			allCM_flex.setCd( ReMoto.ALL_CM_flex );
			
			NeuralTract allRBE_ext = new NeuralTract();
			allRBE_ext.setCd( ReMoto.ALL_RBE_ext );
			
			NeuralTract allRBE_flex = new NeuralTract();
			allRBE_flex.setCd( ReMoto.ALL_RBE_flex );
			
			NeuralTract allRBI_ext = new NeuralTract();
			allRBI_ext.setCd( ReMoto.ALL_RBI_ext );
			
			NeuralTract allRBI_flex = new NeuralTract();
			allRBI_flex.setCd( ReMoto.ALL_RBI_flex );
			
			NeuralTract cm_ext = new NeuralTract();
			cm_ext.setCd( ReMoto.CM_TR_ext );
			
			NeuralTract cm_flex = new NeuralTract();
			cm_flex.setCd( ReMoto.CM_TR_flex );
			
			NeuralTract rbe_ext = new NeuralTract();
			rbe_ext.setCd( ReMoto.RBE_TR_ext );
			
			NeuralTract rbe_flex = new NeuralTract();
			rbe_flex.setCd( ReMoto.RBE_TR_flex );
			
			NeuralTract rbi_ext = new NeuralTract();
			rbi_ext.setCd( ReMoto.RBI_TR_ext );
			
			NeuralTract rbi_flex = new NeuralTract();
			rbi_flex.setCd( ReMoto.RBI_TR_flex );
			
			resultsForm.getElementsSpikes().add(allCM_ext);
			resultsForm.getElementsSpikes().add(allCM_flex);
			resultsForm.getElementsSpikes().add(allRBE_ext);
			resultsForm.getElementsSpikes().add(allRBE_flex);
			resultsForm.getElementsSpikes().add(allRBI_ext);
			resultsForm.getElementsSpikes().add(allRBI_flex);
			
			resultsForm.getElementsFiringRate().add(allCM_ext);
			resultsForm.getElementsFiringRate().add(allCM_flex);
			resultsForm.getElementsFiringRate().add(allRBE_ext);
			resultsForm.getElementsFiringRate().add(allRBE_flex);
			resultsForm.getElementsFiringRate().add(allRBI_ext);
			resultsForm.getElementsFiringRate().add(allRBI_flex);

			resultsForm.getElementsHistogram().add(allCM_ext);
			resultsForm.getElementsHistogram().add(allCM_flex);
			resultsForm.getElementsHistogram().add(allRBE_ext);
			resultsForm.getElementsHistogram().add(allRBE_flex);
			resultsForm.getElementsHistogram().add(allRBI_ext);
			resultsForm.getElementsHistogram().add(allRBI_flex);

			resultsForm.getElementsTracts().add( cm_ext );
			resultsForm.getElementsTracts().add( cm_flex );
			resultsForm.getElementsTracts().add( rbe_ext );
			resultsForm.getElementsTracts().add( rbe_flex );
			resultsForm.getElementsTracts().add( rbi_ext );
			resultsForm.getElementsTracts().add( rbi_flex );
		}
		else if( cdNucleus.equals( ReMoto.SOL ) || cdNucleus.equals( ReMoto.MG ) || cdNucleus.equals( ReMoto.LG ) || cdNucleus.equals( ReMoto.TA ) )
		{
			// Set labels
			Motoneuron allMN = new Motoneuron();
			allMN.setCd( ReMoto.ALL_MN );

			Motoneuron allMNsoma = new Motoneuron();
			allMNsoma.setCd( ReMoto.ALL_MN_soma );

			Motoneuron allMNendPlate = new Motoneuron();
			allMNendPlate.setCd( ReMoto.ALL_MN_end_plate );

			Motoneuron allMN_S = new Motoneuron();
			allMN_S.setCd( ReMoto.ALL_MN_S );

			Motoneuron allMN_FR = new Motoneuron();
			allMN_FR.setCd( ReMoto.ALL_MN_FR );

			Motoneuron allMN_FF = new Motoneuron();
			allMN_FF.setCd( ReMoto.ALL_MN_FF );

			SensoryFiber allIaSynapses = new SensoryFiber();
			allIaSynapses.setCd( ReMoto.ALL_Ia_terminal );
			
			SensoryFiber allIaStimulusPoint = new SensoryFiber();
			allIaStimulusPoint.setCd( ReMoto.ALL_Ia_stimulus_point );
			
			SensoryFiber allIbSynapses = new SensoryFiber();
			allIbSynapses.setCd( ReMoto.ALL_Ib_terminal );
			
			SensoryFiber allIbStimulusPoint = new SensoryFiber();
			allIbStimulusPoint.setCd( ReMoto.ALL_Ib_stimulus_point );

			SensoryFiber allIa = new SensoryFiber();
			allIa.setCd( ReMoto.ALL_Ia );
			
			SensoryFiber allIb = new SensoryFiber();
			allIb.setCd( ReMoto.ALL_Ib );

			Motoneuron allMU = new Motoneuron();
			allMU.setCd( ReMoto.ALL_MU );
			
			ArrayList condArray = new ArrayList();
			condArray.add( new ConductanceVO(ReMoto.gNa, cdNucleus, cdNucleus) );
			condArray.add( new ConductanceVO(ReMoto.gKf, cdNucleus, cdNucleus) );
			condArray.add( new ConductanceVO(ReMoto.gKs, cdNucleus, cdNucleus) );
			condArray.add( new ConductanceVO(ReMoto.gCa, cdNucleus, cdNucleus) );
			condArray.add( new ConductanceVO(ReMoto.gExcDend, cdNucleus, cdNucleus) );
			condArray.add( new ConductanceVO(ReMoto.gInibDend, cdNucleus, cdNucleus) );
			condArray.add( new ConductanceVO(ReMoto.gExcSoma, cdNucleus, cdNucleus) );
			condArray.add( new ConductanceVO(ReMoto.gInibSoma, cdNucleus, cdNucleus) );

			// Set ElementsSpikes
			resultsForm.getElementsSpikes().add(allMNsoma);
			resultsForm.getElementsSpikes().add(allMNendPlate);
			resultsForm.getElementsSpikes().add(allIaSynapses);
			resultsForm.getElementsSpikes().add(allIaStimulusPoint);
			resultsForm.getElementsSpikes().add(allIbSynapses);
			resultsForm.getElementsSpikes().add(allIbStimulusPoint);
			resultsForm.getElementsSpikes().addAll( sim.getNeuronsStoredSignals(cdNucleus, ReMoto.MN) );
				
			// Set ElementsFiringRate
			resultsForm.getElementsFiringRate().add(allMN);
			resultsForm.getElementsFiringRate().addAll( sim.getNeuronsStoredSignals(cdNucleus, ReMoto.MN) );
			// rcisi@19jul2008 - commented for avoid bug: no results showed 
			//resultsForm.getElementsFiringRate().add(allIa);
			//resultsForm.getElementsFiringRate().add(allIb);

			// Set ElementsHistogram
			resultsForm.getElementsHistogram().add(allMN);
			resultsForm.getElementsHistogram().add(allMN_S);
			resultsForm.getElementsHistogram().add(allMN_FR);
			resultsForm.getElementsHistogram().add(allMN_FF);
			resultsForm.getElementsHistogram().add(allIa);
			resultsForm.getElementsHistogram().add(allIb);
			resultsForm.getElementsHistogram().addAll( sim.getNeuronsStoredSignals(cdNucleus, ReMoto.MN) );
			
			// Set ElementsEMG
			resultsForm.getElementsEMG().add(allMU);
			resultsForm.getElementsEMG().addAll( sim.getNeuronsStoredSignals(cdNucleus, ReMoto.MN) );

			// Set ElementsForce
			resultsForm.getElementsForce().add(allMU);
			resultsForm.getElementsForce().addAll( sim.getNeuronsStoredSignals(cdNucleus, ReMoto.MN) );

			// Set ElementsProperties
			resultsForm.getElementsProperties().add(allMN);
			resultsForm.getElementsProperties().add(allMU);
			
			// Set ElementsSignal
			resultsForm.setElementsSignal( sim.getNeuronsStoredSignals(cdNucleus, ReMoto.MN) );
			
			// Set ElementsConductance
			resultsForm.setElementsConductance( condArray );
		}
		else // Interneurons pools
		{
			// Set labels
			Interneuron allIN = new Interneuron();
			allIN.setCd( ReMoto.ALL_IN );

			Interneuron allRC = new Interneuron();
			allRC.setCd( ReMoto.ALL_RC );

			Interneuron allIaIn = new Interneuron();
			allIaIn.setCd( ReMoto.ALL_IaIn );

			Interneuron allIbIn = new Interneuron();
			allIbIn.setCd( ReMoto.ALL_IbIn );

			ArrayList condArray = new ArrayList();
			condArray.add( new ConductanceVO(ReMoto.gNa, cdNucleus, cdNucleus) );
			condArray.add( new ConductanceVO(ReMoto.gKf, cdNucleus, cdNucleus) );
			condArray.add( new ConductanceVO(ReMoto.gKs, cdNucleus, cdNucleus) );
			condArray.add( new ConductanceVO(ReMoto.gExcDend, cdNucleus, cdNucleus) );
			condArray.add( new ConductanceVO(ReMoto.gInibDend, cdNucleus, cdNucleus) );

			// Set ElementsSpikes
			resultsForm.getElementsSpikes().add(allRC);
			resultsForm.getElementsSpikes().add(allIaIn);
			resultsForm.getElementsSpikes().add(allIbIn);
			resultsForm.getElementsSpikes().addAll( sim.getNeuronsStoredSignals(cdNucleus, ReMoto.IN) );
				
			// Set ElementsFiringRate
			resultsForm.getElementsFiringRate().addAll( sim.getNeuronsStoredSignals(cdNucleus, ReMoto.IN) );

			// Set ElementsHistogram
			resultsForm.getElementsHistogram().addAll( sim.getNeuronsStoredSignals(cdNucleus, ReMoto.IN) );
			
			// Set ElementsProperties
			resultsForm.getElementsProperties().add(allIN);
			
			// Set ElementsSignal
			resultsForm.getElementsSignal().addAll( sim.getNeuronsStoredSignals(cdNucleus, ReMoto.IN) );
			
			// Set ElementsConductance
			resultsForm.setElementsConductance( condArray );
		}
		*/
		
		
		// Set ResultTypes
		
		Motoneuron allMU = new Motoneuron();
		allMU.setCd( ReMoto.ALL_MU );
		
		//resultsForm.getElementsEMG().add(allMU);
		//resultsForm.getElementsEMG().addAll( sim.getNeuronsStoredSignals(resultsForm.getCdMuscle(), ReMoto.MN) );

		
		int numOfPlots = 0;
		int numOfCdNeurons = 0;
		int numOfCdSpecifications = 0;
		int numOfMuscles = 0;
		CollectionVO resultTypes[] = null;
		CollectionVO cdNeurons[] = null;
		CollectionVO cdSpecifications[] = null;
		CollectionVO muscles[] = null;
		CollectionVO subplots[] = null;
		
		//System.out.println("resultsForm.getCdType(): " + resultsForm.getCdType());
		
		if(resultsForm.getCdType().equals("neural")){
			
			if(resultsForm.getCdNeuralType().equals("afs")){
				numOfPlots = 6;
				resultTypes = new CollectionVO[numOfPlots];
				
				resultTypes[0] = new CollectionVO("meanFiringRate", "Mean Firing Rate");
				resultTypes[1] = new CollectionVO("spikeTimes", "Spike Times");
				resultTypes[2] = new CollectionVO("histogram", "ISI Histogram");
				resultTypes[3] = new CollectionVO("axonConductionVelocity", "Axon Conduction Velocity");
				resultTypes[4] = new CollectionVO("axonThresholds", "Axon Thresholds");
				resultTypes[5] = new CollectionVO("recruitmentThresholds", " Recruitment Threshold");
				
			}
			else if(resultsForm.getCdNeuralType().equals("dts")){
				numOfPlots = 4;
				resultTypes = new CollectionVO[numOfPlots];
				
				resultTypes[0] = new CollectionVO("meanISI", "Mean ISI");
				resultTypes[1] = new CollectionVO("firingRate", "Firing Rate");
				resultTypes[2] = new CollectionVO("spikeTimes", "Spike Times");
				resultTypes[3] = new CollectionVO("histogram", "ISI Histogram");
				
			}
			else if(resultsForm.getCdNeuralType().equals("ins")){
				numOfPlots = 7;
				resultTypes = new CollectionVO[numOfPlots];
				
				resultTypes[0] = new CollectionVO("somaPotential", "Soma potential");
				resultTypes[1] = new CollectionVO("conductance", "Conductance");
				resultTypes[2] = new CollectionVO("injectedCurrent", "Injected Current");
				resultTypes[3] = new CollectionVO("spikeTimes", "Spike Times");
				resultTypes[4] = new CollectionVO("firingRate", "Instantaneous Firing Rate (IFR)");
				resultTypes[5] = new CollectionVO("ISI", "Interspike Interval (ISI)");
				resultTypes[6] = new CollectionVO("histogram", "ISI Histogram");	
				
				
			}
			else if(resultsForm.getCdNeuralType().equals("mns")){
				numOfPlots = 18;
				resultTypes = new CollectionVO[numOfPlots];
				
				resultTypes[0] = new CollectionVO("spikeTimes", "Spike Times");
				resultTypes[1] = new CollectionVO("firingRate", "Instantaneous Firing Rate (IFR)");
				resultTypes[2] = new CollectionVO("ISI", "Interspike Interval (ISI)");
				resultTypes[3] = new CollectionVO("histogram", "ISI Histogram");
				resultTypes[4] = new CollectionVO("somaPotential", "Soma potential");
				resultTypes[5] = new CollectionVO("dendritePotential", "Dendrite potential");
				resultTypes[6] = new CollectionVO("conductance", "Conductance");
				resultTypes[7] = new CollectionVO("injectedCurrent", "Injected Current");
				resultTypes[8] = new CollectionVO("recruitmentThresholds", " Recruitment Threshold");
				resultTypes[9] = new CollectionVO("axonConductionVelocity", "Axon Conduction Velocity");
				resultTypes[10] = new CollectionVO("somaticInputResistance", "Somatic Input Resistance");
				resultTypes[11] = new CollectionVO("rheobase", "Rheobase");
				resultTypes[12] = new CollectionVO("axonThresholds", "Axon Thresholds");
				resultTypes[13] = new CollectionVO("neuronPositions", "Neuron Positions");
				resultTypes[14] = new CollectionVO("VsxVdot", "Vs x Vdot");
				resultTypes[15] = new CollectionVO("stimulusRate", "Normalized Stimulus Rate");
				resultTypes[16] = new CollectionVO("stimulusRateVsForce", "Stimulus Rate VS Force");
				resultTypes[17] = new CollectionVO("thresholdVsInputConductance", "Threshold VS Input Conductance");
				
			}
			
		}
		else if (resultsForm.getCdType().equals("biomechanical")){
			numOfPlots = 37;
			resultTypes = new CollectionVO[numOfPlots];
			
			resultTypes[0] = new CollectionVO("muscleLength", "Muscle Length");
			resultTypes[1] = new CollectionVO("muscleVelocity", "Muscle Velocity");
			resultTypes[2] = new CollectionVO("muscleAcceleration", "Muscle Acceleration");
			
			resultTypes[3] = new CollectionVO("activationNormSType", "Muscle Activation - S Type");
			resultTypes[4] = new CollectionVO("activationNormFType", "Muscle Activation - F Type");
			resultTypes[5] = new CollectionVO("activationNorm", "Total Muscle Activation");
			
			resultTypes[6] = new CollectionVO("twitchPeak", "Twitch Peak");
			resultTypes[7] = new CollectionVO("contractionTime", "Contraction Time");
			resultTypes[8] = new CollectionVO("halfRelaxation", "Half-relaxation time");
			resultTypes[9] = new CollectionVO("amplitudeMUAP", "MUAP Amplitude");
			
			resultTypes[10] = new CollectionVO(ReMoto.muscleLengthNorm, "Muscle Normalized Length");
			resultTypes[11] = new CollectionVO(ReMoto.forceParallelElement, "Muscle Parallel Element Force");
			resultTypes[12] = new CollectionVO(ReMoto.forceViscousElement, "Muscle Viscous Element Force");
			resultTypes[13] = new CollectionVO(ReMoto.forceActiveSType, "Muscle S Type Active Force");
			resultTypes[14] = new CollectionVO(ReMoto.forceActiveFType, "Muscle F Type Active Force");
			
			resultTypes[15] = new CollectionVO("muscleForce", "Muscle Force");
			resultTypes[16] = new CollectionVO("tendonLength", "Tendon Length");
			resultTypes[17] = new CollectionVO("tendonForce", "Tendon Force");
			
			resultTypes[18] = new CollectionVO("normalizedForce", "Normalized Musculotendon Force");
			
			resultTypes[19] = new CollectionVO("EMG", "EMG");
			resultTypes[20] = new CollectionVO("jointAngle", "Joint Angle");
			resultTypes[21] = new CollectionVO("jointVelocity", "Joint Angular Velocity");
			resultTypes[22] = new CollectionVO("jointCenterMass", "Center of Mass");
			resultTypes[23] = new CollectionVO("jointCenterPressure", "Center of Pressure");
			resultTypes[24] = new CollectionVO("jointTorque", "Joint Torque");
			resultTypes[25] = new CollectionVO("jointMuscleTorque", "Joint Muscle Torque");
			resultTypes[26] = new CollectionVO("jointGravTorque", "Gravitational Torque");
			resultTypes[27] = new CollectionVO("jointPassiveTorque", "Passive Joint Torque");
			resultTypes[28] = new CollectionVO("jointAngleVsJointTorque", "Joint Angle VS Joint Torque");
			resultTypes[29] = new CollectionVO("jointDisturbance", "Disturbance");
			resultTypes[30] = new CollectionVO("MUsPosition", "Motor Unit Position");
			
			resultTypes[31] = new CollectionVO("musculotendonLength", "Musculotendon Length");
			resultTypes[32] = new CollectionVO("musculotendonMomentArm", "Musculotendon Moment Arm");
			
			resultTypes[33] = new CollectionVO("MTLengthVSTendonForce", "MT Length VS Tendon Force");
			resultTypes[34] = new CollectionVO("tendonLengthVSTendonForce", "Tendon Length VS Tendon Force");
			resultTypes[35] = new CollectionVO("muscleLengthVSTendonForce", "Muscle Length VS Tendon Force");

			resultTypes[36] = new CollectionVO("pennationAngle", "Pennation angle");
		}
		
		
		
		
		for(int i = 0; i < numOfPlots; i++)
			resultsForm.getResultTypes().add(resultTypes[i]);
		
		
		if(resultsForm.getCdType().equals("neural")){
			if(resultsForm.getCdNeuralType().equals("afs")){
				numOfCdNeurons = 3;
				cdNeurons = new CollectionVO[numOfCdNeurons];
				
				cdNeurons[0] = new CollectionVO("Ia", "All Ia afferents");
				cdNeurons[1] = new CollectionVO("II", "All II afferents");
				cdNeurons[2] = new CollectionVO("Ib", "All Ib afferents");
				
			}
			else if(resultsForm.getCdNeuralType().equals("dts")){
				numOfCdNeurons = 6;
				cdNeurons = new CollectionVO[numOfCdNeurons];
				
				cdNeurons[0] = new CollectionVO("CM_ext", "CM_ext");
				cdNeurons[1] = new CollectionVO("CM_flex", "CM_flex");
				cdNeurons[2] = new CollectionVO("ExcINs_ext", "ExcINs_ext");
				cdNeurons[3] = new CollectionVO("ExcINs_flex", "ExcINs_flex");
				cdNeurons[4] = new CollectionVO("InhINs_ext", "InhINs_ext");
				cdNeurons[5] = new CollectionVO("InhINs_flex", "InhINs_flex");
			}
			else if(resultsForm.getCdNeuralType().equals("ins")){
				
				List cdINList = new ArrayList();
				
				List storedINs = sim.getNeuronsStoredSignals(resultsForm.getCdNucleus(), ReMoto.IN);
				
				Neuron neu = null;
				
				if(resultsForm.getTask().equals("spikeTimes") || 
						resultsForm.getTask().equals("firingRate") ||
						resultsForm.getTask().equals("ISI")
					){
					
					Interneuron allRCs = new Interneuron();
					allRCs.setCd( "renshaw" );

					Interneuron allIaIns = new Interneuron();
					allIaIns.setCd( "IaIN" );

					Motoneuron allIbIns = new Motoneuron();
					allIbIns.setCd( "IbIN" );

					Motoneuron allgII = new Motoneuron();
					allgII.setCd( "gII" );
					
					cdINList.add(allRCs);
					cdINList.add(allIaIns);
					cdINList.add(allIbIns);
					cdINList.add(allgII);
					
					cdINList.addAll(storedINs);
				}
				else{
					Interneuron allRCs = new Interneuron();
					allRCs.setCd( "renshaw" );

					Interneuron allIaIns = new Interneuron();
					allIaIns.setCd( "IaIN" );

					Motoneuron allIbIns = new Motoneuron();
					allIbIns.setCd( "IbIN" );

					Motoneuron allgII = new Motoneuron();
					allgII.setCd( "gII" );
					
					cdINList.add(allRCs);
					cdINList.add(allIaIns);
					cdINList.add(allIbIns);
					cdINList.add(allgII);
					
					cdINList.addAll(storedINs);
				}
				
				numOfCdNeurons = cdINList.size();
				cdNeurons = new CollectionVO[numOfCdNeurons];
				
				for(int k = 0; k < cdINList.size(); k++){
					neu = (Neuron) cdINList.get(k);
					cdNeurons[k] = new CollectionVO(neu.getCd(), neu.getCd());
				}
				
				
			}
			else if(resultsForm.getCdNeuralType().equals("mns")){
				
				List cdNeuronList = new ArrayList();
				
				List storedNeurons = sim.getNeuronsStoredSignals(resultsForm.getCdMuscle(), ReMoto.MN);
				
				Neuron neu = null;
				
				if(resultsForm.getTask().equals("spikeTimes") || 
						resultsForm.getTask().equals("firingRate") ||
						resultsForm.getTask().equals("ISI")
					){
					
					Motoneuron allMNs = new Motoneuron();
					allMNs.setCd( "All MNs" );

					Motoneuron allMNsS = new Motoneuron();
					allMNsS.setCd( "All MNs S" );

					Motoneuron allMNsFR = new Motoneuron();
					allMNsFR.setCd( "All MNs FR" );

					Motoneuron allMNsFF = new Motoneuron();
					allMNsFF.setCd( "All MNs FF" );
					
					cdNeuronList.add(allMNs);
					cdNeuronList.add(allMNsS);
					cdNeuronList.add(allMNsFR);
					cdNeuronList.add(allMNsFF);
					
					cdNeuronList.addAll(storedNeurons);
				}
				else{
					Motoneuron allMNs = new Motoneuron();
					allMNs.setCd( "All MNs" );

					Motoneuron allMNsS = new Motoneuron();
					allMNsS.setCd( "All MNs S" );

					Motoneuron allMNsFR = new Motoneuron();
					allMNsFR.setCd( "All MNs FR" );

					Motoneuron allMNsFF = new Motoneuron();
					allMNsFF.setCd( "All MNs FF" );
					
					cdNeuronList.add(allMNs);
					cdNeuronList.add(allMNsS);
					cdNeuronList.add(allMNsFR);
					cdNeuronList.add(allMNsFF);
					
					cdNeuronList.addAll(storedNeurons);
				}
				
				numOfCdNeurons = cdNeuronList.size();
				cdNeurons = new CollectionVO[numOfCdNeurons];
				
				for(int k = 0; k < cdNeuronList.size(); k++){
					neu = (Neuron) cdNeuronList.get(k);
					cdNeurons[k] = new CollectionVO(neu.getCd(), neu.getCd());
				}
			}
		}
		else if (resultsForm.getCdType().equals("biomechanical")){
			
			
			List cdNeuronList = new ArrayList();
			List storedNeurons = sim.getNeuronsStoredSignals(resultsForm.getCdMuscle(), ReMoto.MN);
			Neuron neu = null;
			
			Motoneuron allMUs = new Motoneuron();
			allMUs.setCd( "All MUs" );

			Motoneuron allMUsS = new Motoneuron();
			allMUsS.setCd( "All MUs S" );

			Motoneuron allMUsFR = new Motoneuron();
			allMUsFR.setCd( "All MUs FR" );

			Motoneuron allMUsFF = new Motoneuron();
			allMUsFF.setCd( "All MUs FF" );
			
			cdNeuronList.add(allMUs);
			cdNeuronList.add(allMUsS);
			cdNeuronList.add(allMUsFR);
			cdNeuronList.add(allMUsFF);
			
			cdNeuronList.addAll(storedNeurons);
		
			numOfCdNeurons = cdNeuronList.size();
			cdNeurons = new CollectionVO[numOfCdNeurons];
			
			for(int k = 0; k < cdNeuronList.size(); k++){
				neu = (Neuron) cdNeuronList.get(k);
				cdNeurons[k] = new CollectionVO(neu.getCd(), neu.getCd());
			}
			
		}
		
		for(int i = 0; i < numOfCdNeurons; i++){
			resultsForm.getCdNeurons().add(cdNeurons[i]);
		}
			
		
		if(resultsForm.getTask().equals("conductance")){
			numOfCdSpecifications = 8;
			cdSpecifications = new CollectionVO[numOfCdSpecifications];
			
			cdSpecifications[0] = new CollectionVO("gNa", "gNa");
			cdSpecifications[1] = new CollectionVO("gKf", "gKf");
			cdSpecifications[2] = new CollectionVO("gKs", "gKs");
			cdSpecifications[3] = new CollectionVO("gCa", "gCa");
			cdSpecifications[4] = new CollectionVO("gExc-dend", "gExc-dend");
			cdSpecifications[5] = new CollectionVO("gInhi-dend", "gInhi-dend");
			cdSpecifications[6] = new CollectionVO("gExc-soma", "gExc-soma");
			cdSpecifications[7] = new CollectionVO("gInhi-soma", "gInhi-soma");
		}
		else {
			numOfCdSpecifications = 2;
			cdSpecifications = new CollectionVO[numOfCdSpecifications];
			
			//System.out.println("oi");
			
			if(resultsForm.getCdNeuralType().equals("mns")){
				cdSpecifications[0] = new CollectionVO("atSoma", "at Soma");
				cdSpecifications[1] = new CollectionVO("atTerminal", "at Terminal");
			}
			else if(resultsForm.getCdNeuralType().equals("afs")){
				cdSpecifications[0] = new CollectionVO("atStimulusPoint", "at Stimulus Point");
				cdSpecifications[1] = new CollectionVO("atTerminal", "at Terminal");
			}
			else{
				cdSpecifications[0] = new CollectionVO("atSoma", "at Soma");
				cdSpecifications[1] = new CollectionVO("atTerminal", "at Terminal");
			}
			
		}
		
		for(int i = 0; i < numOfCdSpecifications; i++){
			resultsForm.getCdSpecifications().add(cdSpecifications[i]);
		}
		
		
		if(resultsForm.getCdJointType().equals("ankle")){
			numOfMuscles = 4;
			muscles = new CollectionVO[numOfMuscles];
			
			muscles[0] = new CollectionVO("SOL", "Soleus");
			muscles[1] = new CollectionVO("MG", "Medial Gastrocnemius");
			muscles[2] = new CollectionVO("LG", "Lateral Gastrocnemius");
			muscles[3] = new CollectionVO("TA", "Tibial Anterior");
		}
		else if(resultsForm.getCdJointType().equals("mcp")){
			numOfMuscles = 2;
			muscles = new CollectionVO[numOfMuscles];
			
			muscles[0] = new CollectionVO("FDI", "First Dorsal Interosseus");
			muscles[1] = new CollectionVO("SPI", "Second Palmar Interosseus");
		}
		else if(resultsForm.getCdJointType().equals("wrist")){
			numOfMuscles = 2;
			muscles = new CollectionVO[numOfMuscles];
			
			muscles[0] = new CollectionVO("ECRL", "Extensor Carpi Radialis Longus");
			muscles[1] = new CollectionVO("FCU", "Flexor Carpi Ulnaris");
		}
		
		for(int i = 0; i < numOfMuscles; i++){
			resultsForm.getMuscles().add(muscles[i]);
		}
		
			
		
		for(int i = 0; i < resultsForm.getNumSubplots(); i++){
			resultsForm.getSubplots().add(new SubplotVO(i + 1, "Subplot " + String.valueOf(i + 1)));
		}
			
		/*
		String cdNeuron = resultsForm.getCdNeuron();
		
		if(conf.getResult().getTask().equals("meanFiringRate")){
			resultsForm.setTitleLabel("T Mean Firing Rate " + cdNeuron);
			resultsForm.setxLabel("T Time [ms]");
			resultsForm.setyLabel("T " + cdNeuron + " [Hz]");
			resultsForm.setLegendLabel("T Mean Firing Rate " + cdNeuron);
		}
		*/
		
		resultsForm.setTitleLabel(conf.getResult().getTitleLabel());
		resultsForm.setxLabel(conf.getResult().getxLabel());
		
		
			
		// Set ListSamples
		resultsForm.setListSamples( conf.getListSamples() );

		//resultsForm.setCdNucleus( cdNucleus );
		resultsForm.setNuclei( nuclei );
		resultsForm.setTask( conf.getResult().getTask() );
		resultsForm.setCdSpikes( conf.getResult().getCdSpikes() );
		resultsForm.setCdFiringRate( conf.getResult().getCdFiringRate() );
		resultsForm.setCdHistogram( conf.getResult().getCdHistogram() );
		resultsForm.setCdEMG( conf.getResult().getCdEMG() );
		resultsForm.setCdForce( conf.getResult().getCdForce() );
		resultsForm.setCdSignal( conf.getResult().getCdSignal() );
		resultsForm.setCdProperties( conf.getResult().getCdProperties() );
		//resultsForm.setCdConductance( conf.getResult().getCdConductance() );
		resultsForm.setWhichSignal( conf.getResult().getWhichSignal() );
		resultsForm.setSample( conf.getResult().getSample() );
		resultsForm.setFcLow( conf.getResult().getFcLow() );
		resultsForm.setFcHigh( conf.getResult().getFcHigh() );
		resultsForm.setFilter( conf.getResult().getFilter() );
		
		if(conf.getResult().getFilter().equals("noFiltering")){
			resultsForm.setWithEMGfiltering(false);
		}
		else resultsForm.setWithEMGfiltering(true);
		//resultsForm.setWithEMGfiltering( conf.getResult().isWithEMGfiltering() );
		resultsForm.setWithEMGnoise( conf.getResult().isWithEMGnoise() );
		resultsForm.setWithEMGattenuation( conf.getResult().isWithEMGattenuation() );
		
		
		if( conf.getResult().getNumberOfBins() < 1 )
			conf.getResult().setNumberOfBins( ReMoto.numberOfBins );
		
		resultsForm.setNumberOfBins( conf.getResult().getNumberOfBins() );
		
		resultsForm.setXfin( conf.getResult().getCoordenate().getXfin() );
		resultsForm.setXini( conf.getResult().getCoordenate().getXini() );
		resultsForm.setYini( conf.getResult().getCoordenate().getYini() );
		resultsForm.setYfin( conf.getResult().getCoordenate().getYfin() );
		
		resultsForm.setXfinFFT( conf.getResult().getCoordenateFFT().getXfin() );
		resultsForm.setXiniFFT( conf.getResult().getCoordenateFFT().getXini() );
		resultsForm.setYiniFFT( conf.getResult().getCoordenateFFT().getYini() );
		resultsForm.setYfinFFT( conf.getResult().getCoordenateFFT().getYfin() );
		
		resultsForm.setSamplingRateFFT(conf.getResult().getSamplingRateFFT());
		resultsForm.setNumOfPointsFFT(conf.getResult().getNumOfPointsFFT());
		
		resultsForm.setCdJointType( conf.getCdJoint() );
		
		resultsForm.setChartHeigth( conf.getResult().getChartHeigth() );
		resultsForm.setChartWidth( conf.getResult().getChartWidth() );
	}
	
}
