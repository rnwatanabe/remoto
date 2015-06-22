package br.remoto.spindle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import br.remoto.model.Neuron.Neuron;
import br.remoto.model.vo.ConductanceVO;
import br.remoto.model.vo.DynamicVO;
import br.remoto.model.vo.MiscellaneousVO;
import br.remoto.model.vo.MotorUnitVO;
import br.remoto.model.vo.NerveVO;
import br.remoto.model.vo.Nucleus;
import br.remoto.model.vo.ResultVO;
import br.remoto.model.vo.Sample;
import br.remoto.model.vo.GeneralVO;
import br.remoto.model.vo.NeuronVO;


public class SpindleSimulatorConfiguration implements Serializable
{
	private static final long serialVersionUID = 1L;


	private String initial_time		= "0.0";
	private String time_step		= "0.001";
	private String final_time		= "3.0";
	private String start_time		= "1.0";
	private String constant_value 	= "0.95";
	private String initial_value 	= "0.95";
	private String final_value		= "1.08";
	private String ramp_velocity	= "0.11" ;
	private String start_time_sin	= "0.0";
	private String end_time_sin		= "3.0";
	private String start_time_triang = "1.0";
	private String end_time_triang 	= "2.0";
	private String amplitude 		= "0.012";
	private String frequency		= "1";
	private String phase			= "90";
	private String bias				= "0.95";
	private String gamma_static 	= "50.0";
	private String gamma_dynamic	= "50.0";
	private String isRandom			= "is_random";
	private String stddev 			= "0.001" ;
	private String LPcutoff			= "10.0";
	
	private String stretchType;
	
	public SpindleSimulatorConfiguration()
	{
		//changedConfiguration = true;
		
	}
	
	public String getStretchType(){
		return this.stretchType;
	}
	
	public void setStretchType(String type){
		this.stretchType = type;
	}
	
}