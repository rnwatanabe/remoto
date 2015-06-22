/*
 * Created on 01/10/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package br.remoto.control.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.remoto.model.Current;
import br.remoto.model.Nerve;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SpindleSimulatorConfForm extends ActionForm
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
	private List elementsStretchType;

    public SpindleSimulatorConfForm()
	{
		super();
		
	}

	public void reset(ActionMapping map, HttpServletRequest req)
	{
		super.reset(map, req);
	}
	
	public ActionErrors validate(ActionMapping map, HttpServletRequest req)
	{
		return super.validate(map, req);
	}
    
	public String getStretchType(){
		return stretchType;
	}
	
	public List getElementsStretchType(){
		return elementsStretchType;
	}
	
	public void setElementStretchType(String element){
		elementsStretchType.add(element);
	}
	
	
	
}
