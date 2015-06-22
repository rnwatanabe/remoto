package br.remoto.model.vo;

import java.io.Serializable;


public class SubplotVO implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private int cd;
	private String name;
	
	public SubplotVO(int i, String name){
		this.cd = i;
		this.name = name;
	}
	
	public int getCd() {
		return cd;
	}
	public void setCd(int cd) {
		this.cd = cd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
