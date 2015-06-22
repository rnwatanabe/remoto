package br.remoto.model.Musculotendon.Tendon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import br.remoto.model.Configuration;
import br.remoto.model.MotorUnit;
import br.remoto.model.ReMoto;
import br.remoto.util.Sample;
import br.remoto.util.Signal;



public class TendonSuperClass implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	protected Sample sampler1;
	protected Sample sampler2;
	
	protected Configuration conf;
	
	public TendonSuperClass(Configuration conf)
	{
		this.conf = conf;
	}
	
}