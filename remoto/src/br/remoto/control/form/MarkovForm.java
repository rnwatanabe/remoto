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
public class MarkovForm extends ActionForm
{
	private static final long serialVersionUID = 1L;

	private List conductances;
	private List nuclei;
	private String cdNucleus;
	private String option;
	private String cdNeuron;

	private String cdJoint;

    public MarkovForm()
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
    
    public ConductanceVO getConducType(int index){
        /*
        * See the solution tip in: http://www.laliluna.de/dynamic.html
        * 
        * HS 12.09.2004
        * as long as the arraylist is to short, add entries (solution for struts design problem)
        * import logic:iterator or nested:iterator has to use apple as index !! or you will easily face a arrayindexoutufbounds error
        * */
           while(conductances.size() <= index)
           {
        	   conductances.add(new ConductanceVO());
           }

           return (ConductanceVO) conductances.get(index);
       }

	public List getConductances() {
		return conductances;
	}

	public void setConductances(List conductances) {
		this.conductances = conductances;
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

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getCdNeuron() {
		return cdNeuron;
	}

	public void setCdNeuron(String cdNeuron) {
		this.cdNeuron = cdNeuron;
	}

	public String getCdJoint() {
		return cdJoint;
	}

	public void setCdJoint(String cdJoint) {
		this.cdJoint = cdJoint;
	}

}
