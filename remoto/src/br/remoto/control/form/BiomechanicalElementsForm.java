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

public class BiomechanicalElementsForm extends ActionForm
{
	private static final long serialVersionUID = 1L;
	
	private String cdJoint;
	private String cdJointModel;
	private String cdMuscleModel;
	
	private boolean solActive;
	private boolean mgActive;
	private boolean lgActive;
	private boolean taActive;
	private boolean fdiActive;
	private boolean spiActive;
	private boolean fcuActive;
	private boolean ecrlActive;
	
	
	
    public BiomechanicalElementsForm()
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

	public String getCdMuscleModel() {
		return cdMuscleModel;
	}

	public void setCdMuscleModel(String cdMuscleModel) {
		this.cdMuscleModel = cdMuscleModel;
	}

	public boolean isSolActive() {
		return solActive;
	}

	public void setSolActive(boolean solActive) {
		this.solActive = solActive;
	}

	public boolean isMgActive() {
		return mgActive;
	}

	public void setMgActive(boolean mgActive) {
		this.mgActive = mgActive;
	}

	public boolean isLgActive() {
		return lgActive;
	}

	public void setLgActive(boolean lgActive) {
		this.lgActive = lgActive;
	}

	public boolean isTaActive() {
		return taActive;
	}

	public void setTaActive(boolean taActive) {
		this.taActive = taActive;
	}

	public boolean isFdiActive() {
		return fdiActive;
	}

	public void setFdiActive(boolean fdiActive) {
		this.fdiActive = fdiActive;
	}

	public boolean isSpiActive() {
		return spiActive;
	}

	public void setSpiActive(boolean spiActive) {
		this.spiActive = spiActive;
	}

	public boolean isFcuActive() {
		return fcuActive;
	}

	public void setFcuActive(boolean fcuActive) {
		this.fcuActive = fcuActive;
	}

	public boolean isEcrlActive() {
		return ecrlActive;
	}

	public void setEcrlActive(boolean ecrlActive) {
		this.ecrlActive = ecrlActive;
	}
    
    
	
}
