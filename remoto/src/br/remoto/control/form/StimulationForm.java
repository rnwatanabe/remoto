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
public class StimulationForm extends ActionForm
{
	private static final long serialVersionUID = 1L;
	private List nerves;
	
	private String recruitmentOrderFES;

    public StimulationForm()
	{
		super();
		nerves = new ArrayList();
	}

	public void reset(ActionMapping map, HttpServletRequest req)
	{
		super.reset(map, req);
        nerves = new ArrayList();
	}
	
	public ActionErrors validate(ActionMapping map, HttpServletRequest req)
	{
		return super.validate(map, req);
	}
    
    public Nerve getNerve(int index)
    {
    	while(nerves.size() <= index)
    	{
    		nerves.add(new Nerve());
    	}

    	return (Nerve) nerves.get(index);
	}
    
	public List getNerves() {
		return nerves;
	}

	public void setNerves(List nerves) {
		this.nerves = nerves;
	}

	public String getRecruitmentOrderFES() {
		return recruitmentOrderFES;
	}

	public void setRecruitmentOrderFES(String recruitmentOrderFES) {
		this.recruitmentOrderFES = recruitmentOrderFES;
	}

	
	
}
