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
public class SynapseForm extends ActionForm
{
	private static final long serialVersionUID = 1L;

	private List conductances;
	private List typesPre;
	private List typesPos;
	private String typePre;
	private String typePos;

	private List nuclei;
	private String cdNucleus;
	private String synapseType;
	private String cdJoint;

    public SynapseForm()
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

	public String getTypePos() {
		return typePos;
	}

	public void setTypePos(String typePos) {
		this.typePos = typePos;
	}

	public List getTypesPos() {
		return typesPos;
	}

	public void setTypesPos(List typesPos) {
		this.typesPos = typesPos;
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

	public String getSynapseType() {
		return synapseType;
	}

	public void setSynapseType(String synapseType) {
		this.synapseType = synapseType;
	}

	public String getTypePre() {
		return typePre;
	}

	public void setTypePre(String typePre) {
		this.typePre = typePre;
	}

	public List getTypesPre() {
		return typesPre;
	}

	public void setTypesPre(List typesPre) {
		this.typesPre = typesPre;
	}

	public String getCdJoint() {
		return cdJoint;
	}

	public void setCdJoint(String cdJoint) {
		this.cdJoint = cdJoint;
	}

}
