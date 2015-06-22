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

import br.remoto.model.vo.ConductanceVO;


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ConductanceForm extends ActionForm
{
	private static final long serialVersionUID = 1L;

	private List conductances;
	private List types;
	private List nuclei;
	private String cdNucleus;
	private String type;
	private String cdJoint;
	

    public ConductanceForm()
	{
		super();
	
		conductances = new ArrayList();
	}

	public void reset(ActionMapping map, HttpServletRequest req)
	{
		super.reset(map, req);
		
		conductances = new ArrayList();
	}
	
	public ActionErrors validate(ActionMapping map, HttpServletRequest req)
	{
		return super.validate(map, req);
	}
    
    public ConductanceVO getConducType(int index)
    {
    	while(conductances.size() <= index)
        {
    		conductances.add(new ConductanceVO());
        }

    	return (ConductanceVO) conductances.get(index);
	}

	public String getCdNucleus() {
		return cdNucleus;
	}

	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}

	public List getConductances() {
		return conductances;
	}

	public void setConductances(List conductances) {
		this.conductances = conductances;
	}

	public List getNuclei() {
		return nuclei;
	}

	public void setNuclei(List nuclei) {
		this.nuclei = nuclei;
	}

	public List getTypes() {
		return types;
	}

	public void setTypes(List types) {
		this.types = types;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCdJoint() {
		return cdJoint;
	}

	public void setCdJoint(String cdJoint) {
		this.cdJoint = cdJoint;
	}
    
}
