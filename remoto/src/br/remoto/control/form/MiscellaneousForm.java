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


/**
 * @author rrcisi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MiscellaneousForm extends ActionForm
{
	private static final long serialVersionUID = 1L;

	private List miscellaneous;
	private boolean storeSignals;
	private boolean keepProperties;
	

    public MiscellaneousForm()
	{
		super();
	}

	public void reset(ActionMapping map, HttpServletRequest req)
	{
		super.reset(map, req);
		
		miscellaneous = new ArrayList();
	}
	
	public ActionErrors validate(ActionMapping map, HttpServletRequest req)
	{
		return super.validate(map, req);
	}
    
    public MiscellaneousVO getProp(int index)
    {
       while(miscellaneous.size() <= index)
       {
    	   miscellaneous.add(new MiscellaneousVO());
       }

       return (MiscellaneousVO) miscellaneous.get(index);
	}

	public List getMiscellaneous() {
		return miscellaneous;
	}

	public void setMiscellaneous(List miscellaneous) {
		this.miscellaneous = miscellaneous;
	}

	public boolean isStoreSignals() {
		return storeSignals;
	}

	public void setStoreSignals(boolean storeSignals) {
		this.storeSignals = storeSignals;
	}

	public boolean isKeepProperties() {
		return keepProperties;
	}

	public void setKeepProperties(boolean keepProperties) {
		this.keepProperties = keepProperties;
	}
    
}
