package br.remoto.model.vo;

import java.io.Serializable;


public class GraphicVO implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private String cd;
	private String name;
	
	public GraphicVO(String cd, String name){
		this.cd = cd;
		this.name = name;
	}
	
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
