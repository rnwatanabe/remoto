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

import br.remoto.model.vo.MiscellaneousVO;
import br.remoto.model.vo.NeuronVO;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NetworkForm extends ActionForm
{
	private static final long serialVersionUID = 1L;

	private List neurons;
	private String cdJoint;
	private boolean activeAnkleExtensorMNs;
	private boolean activeAnkleExtensorINs;
	private boolean activeAnkleExtensorSAs;
	private boolean activeAnkleFlexorMNs;
	private boolean activeAnkleFlexorINs;
	private boolean activeAnkleFlexorSAs;
	
	//private List miscellaneous;
	
	private double xIniSOL;
	private double xEndSOL;
	private double xIniMG;
	private double xEndMG;
	private double xIniLG;
	private double xEndLG;
	private double xIniTA;
	private double xEndTA;
	private double xIniIN_ext;
	private double xEndIN_ext;
	private double xIniIN_flex;
	private double xEndIN_flex;
	
	
    public NetworkForm()
	{
		super();
	
		neurons = new ArrayList();
	}

	public void reset(ActionMapping map, HttpServletRequest req)
	{
		super.reset(map, req);
		
		neurons = new ArrayList();
	}
	
	public ActionErrors validate(ActionMapping map, HttpServletRequest req)
	{
		return super.validate(map, req);
	}
    
    public NeuronVO getNucleus(int index)
    {
       while(neurons.size() <= index)
       {
    	   neurons.add(new NeuronVO());
       }

       return (NeuronVO) neurons.get(index);
	}

	public List getNeurons() {
		return neurons;
	}

	public void setNeurons(List neurons) {
		this.neurons = neurons;
	}

	public String getCdJoint() {
		return cdJoint;
	}

	public void setCdJoint(String cdJoint) {
		this.cdJoint = cdJoint;
	}

	public boolean isActiveAnkleExtensorMNs() {
		return activeAnkleExtensorMNs;
	}

	public void setActiveAnkleExtensorMNs(boolean activeAnkleExtensorMNs) {
		this.activeAnkleExtensorMNs = activeAnkleExtensorMNs;
	}

	public boolean isActiveAnkleExtensorINs() {
		return activeAnkleExtensorINs;
	}

	public void setActiveAnkleExtensorINs(boolean activeAnkleExtensorINs) {
		this.activeAnkleExtensorINs = activeAnkleExtensorINs;
	}

	public boolean isActiveAnkleExtensorSAs() {
		return activeAnkleExtensorSAs;
	}

	public void setActiveAnkleExtensorSAs(boolean activeAnkleExtensorSAs) {
		this.activeAnkleExtensorSAs = activeAnkleExtensorSAs;
	}

	public boolean isActiveAnkleFlexorMNs() {
		return activeAnkleFlexorMNs;
	}

	public void setActiveAnkleFlexorMNs(boolean activeAnkleFlexorMNs) {
		this.activeAnkleFlexorMNs = activeAnkleFlexorMNs;
	}

	public boolean isActiveAnkleFlexorINs() {
		return activeAnkleFlexorINs;
	}

	public void setActiveAnkleFlexorINs(boolean activeAnkleFlexorINs) {
		this.activeAnkleFlexorINs = activeAnkleFlexorINs;
	}

	public boolean isActiveAnkleFlexorSAs() {
		return activeAnkleFlexorSAs;
	}

	public void setActiveAnkleFlexorSAs(boolean activeAnkleFlexorSAs) {
		this.activeAnkleFlexorSAs = activeAnkleFlexorSAs;
	}


	public double getxIniSOL() {
		return xIniSOL;
	}

	public void setxIniSOL(double xIniSOL) {
		this.xIniSOL = xIniSOL;
	}

	public double getxEndSOL() {
		return xEndSOL;
	}

	public void setxEndSOL(double xEndSOL) {
		this.xEndSOL = xEndSOL;
	}

	public double getxIniMG() {
		return xIniMG;
	}

	public void setxIniMG(double xIniMG) {
		this.xIniMG = xIniMG;
	}

	public double getxEndMG() {
		return xEndMG;
	}

	public void setxEndMG(double xEndMG) {
		this.xEndMG = xEndMG;
	}

	public double getxIniLG() {
		return xIniLG;
	}

	public void setxIniLG(double xIniLG) {
		this.xIniLG = xIniLG;
	}

	public double getxEndLG() {
		return xEndLG;
	}

	public void setxEndLG(double xEndLG) {
		this.xEndLG = xEndLG;
	}

	public double getxIniTA() {
		return xIniTA;
	}

	public void setxIniTA(double xIniTA) {
		this.xIniTA = xIniTA;
	}

	public double getxEndTA() {
		return xEndTA;
	}

	public void setxEndTA(double xEndTA) {
		this.xEndTA = xEndTA;
	}

	public double getxIniIN_ext() {
		return xIniIN_ext;
	}

	public void setxIniIN_ext(double xIniIN_ext) {
		this.xIniIN_ext = xIniIN_ext;
	}

	public double getxEndIN_ext() {
		return xEndIN_ext;
	}

	public void setxEndIN_ext(double xEndIN_ext) {
		this.xEndIN_ext = xEndIN_ext;
	}

	public double getxIniIN_flex() {
		return xIniIN_flex;
	}

	public void setxIniIN_flex(double xIniIN_flex) {
		this.xIniIN_flex = xIniIN_flex;
	}

	public double getxEndIN_flex() {
		return xEndIN_flex;
	}

	public void setxEndIN_flex(double xEndIN_flex) {
		this.xEndIN_flex = xEndIN_flex;
	}

}
