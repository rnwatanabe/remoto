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

import br.remoto.model.vo.Electrotonic;
import br.remoto.model.vo.NeuronVO;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NeuronForm extends ActionForm
{
	private static final long serialVersionUID = 1L;

	private List motoneurons;
	private List interneurons;
	private List sensories;
	private List electrotonics;

	private List nuclei;
	private String cdNucleus;
	private String cdJoint;
	
	
	private double neuromodulationLevel;
	private double CVThresholdCa;
	
	
	// Always true: no more used
	private boolean merge;
	

    public NeuronForm()
	{
		super();
	
		motoneurons = new ArrayList();
		interneurons = new ArrayList();
		sensories = new ArrayList();
		electrotonics = new ArrayList();
	}

	public void reset(ActionMapping map, HttpServletRequest req)
	{
		super.reset(map, req);
		
		motoneurons = new ArrayList();
		interneurons = new ArrayList();
		sensories = new ArrayList();
		electrotonics = new ArrayList();
	}
	
	public ActionErrors validate(ActionMapping map, HttpServletRequest req)
	{
		return super.validate(map, req);
	}
    
    public NeuronVO getMnType(int index)
    {
       while(motoneurons.size() <= index)
       {
    	   motoneurons.add(new NeuronVO());
       }

       return (NeuronVO) motoneurons.get(index);
	}

	public NeuronVO getInType(int index)
	{
        while(interneurons.size() <= index)
        {
        	interneurons.add(new NeuronVO());
        }

        return (NeuronVO) interneurons.get(index);
    }

	public NeuronVO getSfType(int index)
	{
        while(sensories.size() <= index)
        {
        	sensories.add(new NeuronVO());
        }

        return (NeuronVO) sensories.get(index);
    }

	public Electrotonic getElType(int index)
	{
        while(electrotonics.size() <= index)
        {
        	electrotonics.add(new NeuronVO());
        }

        return (Electrotonic) electrotonics.get(index);
    }

	public List getMotoneurons() {
		return motoneurons;
	}

	public void setMotoneurons(List neurons) {
		this.motoneurons = neurons;
	}

	public List getInterneurons() {
		return interneurons;
	}

	public void setInterneurons(List ins) {
		this.interneurons = ins;
	}

	public String getCdNucleus() {
		return cdNucleus;
	}

	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}

	public List getNuclei() {
		return nuclei;
	}

	public void setNuclei(List nuclei) {
		this.nuclei = nuclei;
	}

	public List getSensories() {
		return sensories;
	}

	public void setSensories(List sensories) {
		this.sensories = sensories;
	}

	public List getElectrotonics() {
		return electrotonics;
	}

	public void setElectrotonics(List electrotonics) {
		this.electrotonics = electrotonics;
	}

	public boolean isMerge() {
		return merge;
	}

	public void setMerge(boolean merge) {
		this.merge = merge;
	}

	public String getCdJoint() {
		return cdJoint;
	}

	public void setCdJoint(String cdJoint) {
		this.cdJoint = cdJoint;
	}

	public double getNeuromodulationLevel() {
		return neuromodulationLevel;
	}

	public void setNeuromodulationLevel(double neuromodulationLevel) {
		this.neuromodulationLevel = neuromodulationLevel;
	}

	public double getCVThresholdCa() {
		return CVThresholdCa;
	}

	public void setCVThresholdCa(double cVThresholdCa) {
		CVThresholdCa = cVThresholdCa;
	}

}
