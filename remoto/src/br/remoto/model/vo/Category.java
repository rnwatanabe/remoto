package br.remoto.model.vo;

import java.io.Serializable;

public class Category implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String name;
	private String cdCategory;
	
	
	public Category()
	{
	}
	
	public Category(String name, String idCategory)
	{
		setName(name);
		setCdCategory(idCategory);
	}
	
	public String getCdCategory() {
		return cdCategory;
	}
	public void setCdCategory(String category) {
		this.cdCategory = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
