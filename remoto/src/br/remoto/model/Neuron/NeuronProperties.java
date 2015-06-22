
package br.remoto.model.Neuron;

import java.io.Serializable;


public class NeuronProperties implements Serializable
{
	private static final long serialVersionUID = 1L;

	protected String type;
	protected String category;
	protected String cdNucleus;
	
	protected boolean active;
	protected int index;
	protected int indexStoreVm1;
	protected int indexStoreVm2;
	
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCdNucleus() {
		return cdNucleus;
	}
	public void setCdNucleus(String cdNucleus) {
		this.cdNucleus = cdNucleus;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getIndexStoreVm1() {
		return indexStoreVm1;
	}
	public void setIndexStoreVm1(int indexStoreVm1) {
		this.indexStoreVm1 = indexStoreVm1;
	}
	public int getIndexStoreVm2() {
		return indexStoreVm2;
	}
	public void setIndexStoreVm2(int indexStoreVm2) {
		this.indexStoreVm2 = indexStoreVm2;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	
}
