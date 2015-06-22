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

import br.remoto.model.vo.NeuronVO;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CopyOfInputForm extends ActionForm
{
	private static final long serialVersionUID = 1L;

	private List noises;
	private List inputs;
	
	
	//Modified by Vitor
	//private String task;
	private List modulations;

    public CopyOfInputForm()
	{
		super();
		//System.out.println("Creating inputForm");
		noises = new ArrayList();
		inputs = new ArrayList();
		modulations = new ArrayList();
	}

	public void reset(ActionMapping map, HttpServletRequest req)
	{
		super.reset(map, req);
		
		noises = new ArrayList();
        inputs = new ArrayList();
        modulations = new ArrayList();
	}
	
	public ActionErrors validate(ActionMapping map, HttpServletRequest req)
	{
		return super.validate(map, req);
	}
    
    public NeuronVO getNeuronType(int index)
    {
        while(inputs.size() <= index)
        {
            inputs.add(new NeuronVO());
        }

        return (NeuronVO) inputs.get(index);
    }

    public NeuronVO getNoise(int index)
    {
    	while(noises.size() <= index)
    	{
    		noises.add(new NeuronVO());
    	}

    	return (NeuronVO) noises.get(index);
	}

	public List getInputs() {
		return inputs;
	}

	public void setInputs(List inputs) {
		this.inputs = inputs;
	}

	public List getNoises() {
		return noises;
	}

	public void setNoises(List noises) {
		this.noises = noises;
	}
    
	//Modified by Vitor
	/*
	public String getGraphicTask() {
		return task;
	}

	public void setGraphicTask(String task) {
		this.task = task;
	}
	*/
	
	public List getModulations() {
		return modulations;
	}

	public void setModulations(List modulations) {
		this.modulations = modulations;
	}
}
