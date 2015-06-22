/*
 * Created on 08/12/2011
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
 * @author vitor chaud
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JointForm extends ActionForm
{
	private static final long serialVersionUID = 1L;
	
	private String cdJoint;
	private String cdJointModel;
	
	private int jointAngle;
	private int kneeAngle;
	
    public JointForm()
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

	public String getCdJoint() {
		return cdJoint;
	}

	public void setCdJoint(String cdJoint) {
		this.cdJoint = cdJoint;
	}

	public String getCdJointModel() {
		return cdJointModel;
	}

	public void setCdJointModel(String cdJointModel) {
		this.cdJointModel = cdJointModel;
	}

	public int getJointAngle() {
		return jointAngle;
	}

	public void setJointAngle(int jointAngle) {
		this.jointAngle = jointAngle;
	}

	public int getKneeAngle() {
		return kneeAngle;
	}

	public void setKneeAngle(int kneeAngle) {
		this.kneeAngle = kneeAngle;
	}

	
    
	
}
